package io.github.tetratheta.hardplus.module;

import io.github.tetratheta.hardplus.util.Perm;
import io.github.tetratheta.hardplus.util.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@SuppressWarnings("unused")
public class FireForever implements Listener {
  @EventHandler
  public void onPlayerOnFire(EntityDamageEvent e) {
    if (e.getEntity() instanceof Player p) {
      if (!PlayerUtil.checkPermGameMode(p, Perm.FIRE_FOREVER)) return;
      if (e.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK)) {
        if (p.getFireTicks() < 50) p.setFireTicks(1000);
      }
    }
  }
}
