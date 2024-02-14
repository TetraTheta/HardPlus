package io.github.tetratheta.hardplus.module;

import io.github.tetratheta.hardplus.util.Perm;
import io.github.tetratheta.hardplus.util.PlayerUtil;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class DamageCritical implements Listener {
  JavaPlugin plugin;

  public DamageCritical(JavaPlugin plugin) {
    this.plugin = plugin;
  }

  @SuppressWarnings("UnstableApiUsage")
  @EventHandler
  public void onPlayerInflictCriticalDamage(EntityDamageByEntityEvent e) {
    // TODO: Find a cleaner way of doing this. For example, using DamageSource instead...
    if (e.getDamager() instanceof AbstractArrow arrow) {
      if (!(arrow.getShooter() instanceof Player p)) return;
      if (!PlayerUtil.checkPermGameMode(p, Perm.DAMAGE_CRITICAL.value)) return;
      arrow.setCritical(false);
    } else if (e.getDamager() instanceof Player p) {
      if (!PlayerUtil.checkPermGameMode(p, Perm.DAMAGE_CRITICAL.value)) return;
      // Can't cancel critical status. This will reduce damage, but leave critical particle.
      e.setDamage(e.getDamage() / 3 * 2);
    }
    /*
    if (e.isCancelled()) return;
    if (e.isCritical() && e.getDamager() instanceof Player p) {
      if (!PlayerUtil.checkPermGameMode(p, Perm.DAMAGE_CRITICAL.value)) return;
      Bukkit.getScheduler().runTaskLater(plugin, () -> ((Damageable)e.getEntity()).damage(e.getDamage() / 3 * 2, e.getDamageSource()), 1L);
      e.setCancelled(true);
    }
    */
  }
}
