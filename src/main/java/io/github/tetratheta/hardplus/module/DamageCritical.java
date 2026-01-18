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

  @EventHandler
  public void onPlayerInflictCriticalDamage(EntityDamageByEntityEvent e) {
    // TODO: Find a cleaner way of doing this. For example, using DamageSource instead...
    if (e.getDamager() instanceof AbstractArrow arrow) {
      if (!(arrow.getShooter() instanceof Player p)) return;
      if (!PlayerUtil.checkPermGameMode(p, Perm.DAMAGE_CRITICAL)) return;
      arrow.setCritical(false);
    } else if (e.getDamager() instanceof Player p) {
      if (!PlayerUtil.checkPermGameMode(p, Perm.DAMAGE_CRITICAL)) return;
      // Can't cancel critical status. This will reduce damage, but leave critical particle.
      e.setDamage(e.getDamage() / 3 * 2);
    }
  }
}
