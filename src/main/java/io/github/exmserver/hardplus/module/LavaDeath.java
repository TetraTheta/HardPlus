package io.github.exmserver.hardplus.module;

import io.github.exmserver.hardplus.Hardplus;
import io.github.exmserver.hardplus.util.HPPerm;
import io.github.exmserver.hardplus.util.HPPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@SuppressWarnings("unused")
public class LavaDeath implements Listener {
  final Hardplus plugin;

  public LavaDeath(Hardplus hardplus) {
    this.plugin = hardplus;
  }

  @EventHandler
  public void onLava(EntityDamageEvent e) {
    if (!(e.getEntity() instanceof Player p)) return;
    if (!HPPlayer.checkPermGameMode(p, HPPerm.LAVA_DEATH.value)) return;

    if (e.getCause().equals(EntityDamageEvent.DamageCause.LAVA)) {
      Bukkit.getScheduler().runTask(plugin, () -> p.setNoDamageTicks(0));
    }
  }
}