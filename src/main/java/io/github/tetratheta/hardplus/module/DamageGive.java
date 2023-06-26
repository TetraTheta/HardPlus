package io.github.tetratheta.hardplus.module;

import io.github.tetratheta.hardplus.util.Perm;
import io.github.tetratheta.hardplus.util.PlayerUtil;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

@SuppressWarnings("unused")
public class DamageGive implements Listener {
  final double modifier;

  public DamageGive(double modifier) { this.modifier = modifier; }

  @EventHandler
  public void onPlayerInflictDamage(EntityDamageByEntityEvent e) {
    if (e.getDamager() instanceof AbstractArrow arrow) {
      if (!(arrow.getShooter() instanceof Player p)) return;
      if (!PlayerUtil.checkPermGameMode(p, Perm.DAMAGE_GIVE.value)) return;
      e.setDamage(e.getDamage() * modifier);
    } else if (e.getDamager() instanceof Player p) {
      if (!PlayerUtil.checkPermGameMode(p, Perm.DAMAGE_GIVE.value)) return;
      e.setDamage(e.getDamage() * modifier);
    }
  }
}
