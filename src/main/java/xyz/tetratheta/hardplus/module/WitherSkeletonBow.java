package xyz.tetratheta.hardplus.module;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.tetratheta.hardplus.util.HPPerm;

import java.util.Objects;
import java.util.Random;

public class WitherSkeletonBow implements Listener {
  final double bowWSSpawnChance;
  final int bowDamageLevel;
  final int bowKnockbackLevel;
  final int arrowWitherLevel;
  final Random random = new Random();

  NamespacedKey key = new NamespacedKey("hardplus", "wither-skeleton-arrow");

  public WitherSkeletonBow(double bowSpawnChance, int arrowDamageLevel, int arrowKnockbackLevel,
                           int arrowWitherLevel) {
    this.bowWSSpawnChance = bowSpawnChance;
    this.bowDamageLevel = arrowDamageLevel;
    this.bowKnockbackLevel = arrowKnockbackLevel;

    this.arrowWitherLevel = arrowWitherLevel;
  }

  @EventHandler
  public void onWitherSkeletonSpawn(CreatureSpawnEvent e) {
    if (!(e.getEntity() instanceof WitherSkeleton)) return;
    if (random.nextDouble() * 100 >= bowWSSpawnChance) return;

    EntityEquipment mobInventory = e.getEntity().getEquipment();
    if (mobInventory == null) return;
    ItemStack bow = new ItemStack(Material.BOW);
    ItemMeta bowMeta = bow.getItemMeta();
    bowMeta.addEnchant(Enchantment.ARROW_DAMAGE, bowDamageLevel, true);
    bowMeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
    bowMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, bowKnockbackLevel, true);
    bow.setItemMeta(bowMeta);
    mobInventory.setItemInMainHand(bow);
    mobInventory.setItemInMainHandDropChance(0);
  }

  @EventHandler
  public void onWitherSkeletonShoot(EntityShootBowEvent e) {
    if (!(e.getEntity() instanceof WitherSkeleton)) return;
    if (!(e.getProjectile() instanceof Arrow arrow)) return;

    arrow.addCustomEffect(new PotionEffect(PotionEffectType.WITHER, 800, arrowWitherLevel), true);
    arrow.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
    e.setProjectile(arrow);
  }

  @EventHandler
  public void onNonHPPlayerHit(EntityDamageByEntityEvent e) {
    // We can't use HPPlayer#checkPermGameMode here
    if (!(e.getDamager() instanceof Arrow arrow)) return;
    if (e.getEntity() instanceof Player player && !player.hasPermission(HPPerm.WITHER_SKELETON_BOW.value)) {
      Byte value = arrow.getPersistentDataContainer().get(key, PersistentDataType.BYTE);
      if (Objects.isNull(value)) return;
      if (value.equals((byte) 1)) {
        // Remove Wither effect
        arrow.clearCustomEffects();
        // Decrease increased damage with Power
        double originalDamage = e.getDamage();
        double newDamage = originalDamage - (originalDamage * 0.25 * (bowDamageLevel + 1));
        e.setDamage(newDamage);
        // Since Wither Skeleton shoots flamed arrow, we do not catch fire of non-HP player.
      }
    }
  }
}
