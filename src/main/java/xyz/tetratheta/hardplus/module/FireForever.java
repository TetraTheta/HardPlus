package xyz.tetratheta.hardplus.module;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import xyz.tetratheta.hardplus.util.HPPerm;
import xyz.tetratheta.hardplus.util.HPPlayer;

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
