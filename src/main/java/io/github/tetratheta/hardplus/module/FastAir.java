package io.github.tetratheta.hardplus.module;

import io.github.tetratheta.hardplus.util.Perm;
import io.github.tetratheta.hardplus.util.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityAirChangeEvent;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class FastAir implements Listener {
  final int modifier;
  final Map<Player, Integer> previousValue = new HashMap<>();

  public FastAir(int modifier) {
    this.modifier = modifier;
  }

  @EventHandler
  public void onPlayerAirDrops(EntityAirChangeEvent e) {
    if (!(e.getEntity() instanceof Player p)) return;
    if (!PlayerUtil.checkPermGameMode(p, Perm.FAST_AIR)) return;

    if (previousValue.get(p) != null) {
      if (e.getAmount() < previousValue.get(p)) {
        int newAir = e.getAmount() - modifier;
        if (newAir > 0) {
          e.setAmount(newAir);
        }
      }
      previousValue.remove(p);
    } else {
      previousValue.put(p, e.getAmount());
    }
  }
}
