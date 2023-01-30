package io.github.exmserver.hardplus.module;

import io.github.exmserver.hardplus.util.HPPerm;
import io.github.exmserver.hardplus.util.HPPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@SuppressWarnings("unused")
public class FireForever implements Listener {
  @EventHandler
  public void onPlayerOnFire(EntityDamageEvent e) {
    if (e.getEntity() instanceof Player p) {
      if (!HPPlayer.checkPermGameMode(p, HPPerm.FIRE_FOREVER.value)) return;
      if (e.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK)) {
        if (p.getFireTicks() < 50) p.setFireTicks(1000);
      }
    }
  }
}
