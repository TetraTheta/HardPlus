package xyz.tetratheta.hardplus.module;

import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import xyz.tetratheta.hardplus.util.HPPerm;
import xyz.tetratheta.hardplus.util.HPPlayer;

public class DamageTake implements Listener {
  final double defModifier; final double playerModifier; final double rangedModifier;
  final double fallModifier; final double magicModifier; final double envModifier;
  final double passiveMobModifier; final double hostileMobDefModifier; final double zombieModifier;
  final double skeletonModifier; final double endermanModifier;

  public DamageTake(
      double defModifier, double playerModifier, double rangedModifier,
      double fallModifier, double magicModifier, double envModifier,
      double passiveMobModifier, double hostileMobDefModifier, double zombieModifier,
      double skeletonModifier, double endermanModifier) {
    this.defModifier = defModifier;
    this.playerModifier = playerModifier;
    this.rangedModifier = rangedModifier;
    this.fallModifier = fallModifier;
    this.magicModifier = magicModifier;
    this.envModifier = envModifier;
    this.passiveMobModifier = passiveMobModifier;
    this.hostileMobDefModifier = hostileMobDefModifier;
    this.zombieModifier = zombieModifier;
    this.skeletonModifier = skeletonModifier;
    this.endermanModifier = endermanModifier;
  }

  @EventHandler
  public void onPlayerTakeDamage(EntityDamageEvent e) {
    if (!(e.getEntity() instanceof Player p)) return;
    if (!HPPlayer.checkPermGameMode(p, HPPerm.DAMAGE_TAKE.value)) return;
    // Start a hell of if else
    if (e instanceof EntityDamageByEntityEvent ee) {
      // Attacker is entity
      if (ee.getDamager() instanceof Player) {
        // Attacker is player with melee
        e.setDamage(e.getDamage() * playerModifier);
      } else if (ee.getDamager() instanceof AbstractArrow eeArrow) {
        // Attacker is an arrow
        if (eeArrow.getShooter() instanceof Player) {
          // Attacker is player with arrow
          e.setDamage(e.getDamage() * playerModifier);
        } else if (eeArrow.getShooter() instanceof AbstractSkeleton) {
          // Attacker is skeleton with arrow
          e.setDamage(e.getDamage() * skeletonModifier);
        } else {
          // Consider leftover as ranged attack
          e.setDamage(e.getDamage() * rangedModifier);
        }
      } else if (ee.getDamager() instanceof Mob mob) {
        if (mob instanceof Monster monster) {
          // Attacker is monster
          if (monster instanceof AbstractSkeleton) e.setDamage(e.getDamage() * skeletonModifier);
          else if (monster instanceof Enderman) e.setDamage(e.getDamage() * endermanModifier);
          else if (monster instanceof Zombie) e.setDamage(e.getDamage() * zombieModifier);
          else e.setDamage(e.getDamage() * hostileMobDefModifier);
        } else {
          // Attacker is animal (or other passive mob)
          e.setDamage(e.getDamage() * passiveMobModifier);
        }
      }
    } else if (e instanceof EntityDamageByBlockEvent eb) {
      // AVAILABLE: LAVA, MAGMA_BLOCK, CACTUS, CAMPFIRE
      // UNAVAILABLE: POWDER_SNOW, FIRE, FALLING_STALACTITES, FALLING_ANVIL
      Block block = eb.getDamager();
      if (block != null) {
        e.setDamage(e.getDamage()  * envModifier);
      } else {
        e.setDamage(e.getDamage()  * envModifier);
      }
    } else if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
      e.setDamage(e.getDamage() * fallModifier);
    } else if (e.getCause().equals(EntityDamageEvent.DamageCause.MAGIC)) {
      e.setDamage(e.getDamage() * magicModifier);
    } else {
      e.setDamage(e.getDamage() * defModifier);
    }
  }
}
