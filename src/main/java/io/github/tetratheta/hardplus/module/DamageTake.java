package io.github.tetratheta.hardplus.module;

import io.github.tetratheta.hardplus.util.DmgMod;
import io.github.tetratheta.hardplus.util.Perm;
import io.github.tetratheta.hardplus.util.PlayerUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
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
      if (ee.getDamager() instanceof Player) {
        // Attacker is player with melee
        e.setDamage(e.getDamage() * dmgMod.PLAYER);
      } else if (ee.getDamager() instanceof AbstractArrow eeArrow) {
        // Attacker is an arrow
        if (eeArrow.getShooter() instanceof Player) {
          // Attacker is player with arrow
          e.setDamage(e.getDamage() * dmgMod.PLAYER);
        } else if (eeArrow.getShooter() instanceof AbstractSkeleton) {
          // Attacker is skeleton with arrow
          e.setDamage(e.getDamage() * dmgMod.SKELETON);
        } else {
          // Consider leftover as ranged attack
          e.setDamage(e.getDamage() * dmgMod.MELEE);
        }
      } else if (ee.getDamager() instanceof Mob mob) {
        if (mob instanceof Monster monster) {
          // Attacker is monster
          if (monster instanceof AbstractSkeleton) e.setDamage(e.getDamage() * dmgMod.SKELETON);
          else if (monster instanceof Enderman) e.setDamage(e.getDamage() * dmgMod.ENDERMAN);
          else if (monster instanceof Zombie) e.setDamage(e.getDamage() * dmgMod.ZOMBIE);
          else e.setDamage(e.getDamage() * dmgMod.MOB_HOSTILE);
        } else {
          // Attacker is animal (or other passive mob)
          e.setDamage(e.getDamage() * dmgMod.MOB_PASSIVE);
        }
      }
    } else if (e instanceof EntityDamageByBlockEvent eb) {
      // AVAILABLE: LAVA, MAGMA_BLOCK, CACTUS, CAMPFIRE
      // UNAVAILABLE: POWDER_SNOW, FIRE, FALLING_STALACTITES, FALLING_ANVIL
      Block block = eb.getDamager();
      if (block != null) {
        e.setDamage(e.getDamage() * dmgMod.ENVIRONMENT);
      } else {
        e.setDamage(e.getDamage() * dmgMod.ENVIRONMENT);
      }
    } else if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
      e.setDamage(e.getDamage() * dmgMod.FALL);
    } else if (e.getCause().equals(EntityDamageEvent.DamageCause.MAGIC)) {
      e.setDamage(e.getDamage() * dmgMod.MAGIC);
    } else {
      e.setDamage(e.getDamage() * dmgMod.DEFAULT);
    }
  }
}
