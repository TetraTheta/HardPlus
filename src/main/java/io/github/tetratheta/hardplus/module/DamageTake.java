package io.github.tetratheta.hardplus.module;

import io.github.tetratheta.hardplus.util.DmgMod;
import io.github.tetratheta.hardplus.util.Perm;
import io.github.tetratheta.hardplus.util.PlayerUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.AbstractSkeleton;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

@SuppressWarnings("unused")
public class DamageTake implements Listener {
  DmgMod dmgMod;

  public DamageTake(DmgMod dmgMod) {
    this.dmgMod = dmgMod;
  }

  // TODO: Ensure HardPlus player will loss 1 health
  @EventHandler
  public void onPlayerTakeDamage(EntityDamageEvent e) {
    if (!(e.getEntity() instanceof Player p)) return;
    if (!PlayerUtil.checkPermGameMode(p, Perm.DAMAGE_TAKE)) return;
    // Start a hell of if else
    if (e instanceof EntityDamageByEntityEvent ee) {
      // Attacker is entity
      switch (ee.getDamager()) {
        case Player player ->
          // Attacker is player with melee
          e.setDamage(e.getDamage() * dmgMod.PLAYER);
        case AbstractArrow eeArrow -> {
          // Attacker is an arrow
          switch (eeArrow.getShooter()) {
            case Player player ->
              // Attacker is player with arrow
              e.setDamage(e.getDamage() * dmgMod.PLAYER);
            case AbstractSkeleton abstractSkeleton ->
              // Attacker is skeleton with arrow
              e.setDamage(e.getDamage() * dmgMod.SKELETON);
            case null, default ->
              // Consider leftover as ranged attack
              e.setDamage(e.getDamage() * dmgMod.MELEE);
          }
        }
        case Mob mob -> {
          if (mob instanceof Monster monster) {
            // Attacker is monster
            switch (monster) {
              case AbstractSkeleton abstractSkeleton -> e.setDamage(e.getDamage() * dmgMod.SKELETON);
              case Enderman enderman -> e.setDamage(e.getDamage() * dmgMod.ENDERMAN);
              case Zombie zombie -> e.setDamage(e.getDamage() * dmgMod.ZOMBIE);
              default -> e.setDamage(e.getDamage() * dmgMod.MOB_HOSTILE);
            }
          } else {
            // Attacker is animal (or other passive mob)
            e.setDamage(e.getDamage() * dmgMod.MOB_PASSIVE);
          }
        }
        default -> {
        }
      }
    } else if (e instanceof EntityDamageByBlockEvent eb) {
      // AVAILABLE: LAVA, MAGMA_BLOCK, CACTUS, CAMPFIRE
      // UNAVAILABLE: POWDER_SNOW, FIRE, FALLING_STALACTITES, FALLING_ANVIL
      Block block = eb.getDamager();
      e.setDamage(e.getDamage() * dmgMod.ENVIRONMENT);
    } else if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
      e.setDamage(e.getDamage() * dmgMod.FALL);
    } else if (e.getCause().equals(EntityDamageEvent.DamageCause.MAGIC)) {
      e.setDamage(e.getDamage() * dmgMod.MAGIC);
    } else {
      e.setDamage(e.getDamage() * dmgMod.DEFAULT);
    }
  }
}
