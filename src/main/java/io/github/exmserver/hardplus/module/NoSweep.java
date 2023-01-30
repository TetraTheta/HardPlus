package io.github.exmserver.hardplus.module;

import io.github.exmserver.hardplus.util.HPPerm;
import io.github.exmserver.hardplus.util.HPPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

@SuppressWarnings("unused")
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
