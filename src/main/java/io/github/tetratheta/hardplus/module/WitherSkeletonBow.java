package io.github.tetratheta.hardplus.module;

import io.github.tetratheta.hardplus.util.Perm;
import io.github.tetratheta.hardplus.util.PlayerUtil;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/// Arms Wither Skeletons with bows for HP player targets, then swaps back to stone swords for other targets.
@SuppressWarnings("unused")
public class WitherSkeletonBow implements Listener {
  private static final double HP_PLAYER_SPAWN_RADIUS = 16;
  final int arrowWitherLevel;
  final int bowDamageLevel;
  final int bowKnockbackLevel;
  final double bowWSSpawnChance;
  final NamespacedKey mobKey = new NamespacedKey("hardplus", "wither-skeleton-bow");
  final Random random = new Random();

  public WitherSkeletonBow(double bowSpawnChance, int arrowDamageLevel, int arrowKnockbackLevel, int arrowWitherLevel) {
    this.bowWSSpawnChance = bowSpawnChance;
    this.bowDamageLevel = arrowDamageLevel;
    this.bowKnockbackLevel = arrowKnockbackLevel;
    this.arrowWitherLevel = arrowWitherLevel;
  }

  @EventHandler
  public void onWitherSkeletonShoot(EntityShootBowEvent e) {
    if (!(e.getEntity() instanceof WitherSkeleton witherSkeleton)) return;
    if (!isBowWitherSkeleton(witherSkeleton)) return;
    if (!isHPPlayer(witherSkeleton.getTarget())) {
      e.setCancelled(true);
      setWeapon(witherSkeleton, false);
      return;
    }
    if (!(e.getProjectile() instanceof Arrow arrow)) return;
    arrow.addCustomEffect(new PotionEffect(PotionEffectType.WITHER, 800, arrowWitherLevel), true);
    e.setProjectile(arrow);
  }

  @EventHandler
  public void onWitherSkeletonSpawn(CreatureSpawnEvent e) {
    if (!(e.getEntity() instanceof WitherSkeleton witherSkeleton)) return;
    if (!shouldArmWithBow()) return;
    markAsBowWitherSkeleton(witherSkeleton);
    if (!hasNearbyHPPlayer(witherSkeleton.getLocation())) return;
    setWeapon(witherSkeleton, true);
  }

  private boolean hasNearbyHPPlayer(Location location) {
    for (Entity entity : location.getNearbyEntities(HP_PLAYER_SPAWN_RADIUS, HP_PLAYER_SPAWN_RADIUS, HP_PLAYER_SPAWN_RADIUS)) {
      if (isHPPlayer(entity)) return true;
    }
    return false;
  }

  private void markAsBowWitherSkeleton(WitherSkeleton witherSkeleton) {
    witherSkeleton.getPersistentDataContainer().set(mobKey, PersistentDataType.BYTE, (byte) 1);
  }

  private boolean shouldArmWithBow() {
    return random.nextDouble() * 100 < bowWSSpawnChance;
  }

  @EventHandler
  public void onWitherSkeletonTarget(EntityTargetLivingEntityEvent e) {
    if (!(e.getEntity() instanceof WitherSkeleton witherSkeleton)) return;
    if (isBowWitherSkeleton(witherSkeleton) && isHPPlayer(e.getTarget())) {
      setWeapon(witherSkeleton, true);
      return;
    }
    if (isBowWitherSkeleton(witherSkeleton)) setWeapon(witherSkeleton, false);
  }

  @SuppressWarnings("BooleanMethodIsAlwaysInverted")
  private boolean isBowWitherSkeleton(WitherSkeleton witherSkeleton) {
    Byte value = witherSkeleton.getPersistentDataContainer().get(mobKey, PersistentDataType.BYTE);
    return value != null && value.equals((byte) 1);
  }

  private boolean isHPPlayer(Entity entity) {
    return entity instanceof Player player && PlayerUtil.checkPermGameMode(player, Perm.WITHER_SKELETON_BOW);
  }

  private void setWeapon(WitherSkeleton witherSkeleton, boolean useBow) {
    EntityEquipment equipment = witherSkeleton.getEquipment();
    if (equipment.getItemInMainHand().isEmpty()) return;
    equipment.setItemInMainHand(useBow ? createBow() : new ItemStack(Material.STONE_SWORD));
    equipment.setItemInMainHandDropChance(0);
  }

  private ItemStack createBow() {
    ItemStack bow = new ItemStack(Material.BOW);
    ItemMeta bowMeta = bow.getItemMeta();
    bowMeta.addEnchant(Enchantment.POWER, bowDamageLevel, true);
    bowMeta.addEnchant(Enchantment.FLAME, 1, true);
    bowMeta.addEnchant(Enchantment.PUNCH, bowKnockbackLevel, true);
    bow.setItemMeta(bowMeta);
    return bow;
  }
}
