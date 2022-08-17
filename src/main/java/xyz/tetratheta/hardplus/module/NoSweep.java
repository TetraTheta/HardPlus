package xyz.tetratheta.hardplus.module;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import xyz.tetratheta.hardplus.util.HPPerm;
import xyz.tetratheta.hardplus.util.HPPlayer;

public class NoSweep implements Listener {
  @EventHandler
  public void onPlayerSweep(EntityDamageByEntityEvent e) {
    if (!(e.getDamager() instanceof Player p)) return;
    if (!HPPlayer.checkPermGameMode(p, HPPerm.NO_SWEEP.value)) return;

    if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)) {
      e.setCancelled(true);
    }
  }
}
