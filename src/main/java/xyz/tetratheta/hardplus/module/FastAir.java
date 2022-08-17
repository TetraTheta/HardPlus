package xyz.tetratheta.hardplus.module;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityAirChangeEvent;
import xyz.tetratheta.hardplus.util.HPPerm;
import xyz.tetratheta.hardplus.util.HPPlayer;

import java.util.HashMap;
import java.util.Map;

public class FastAir implements Listener {
  int modifier;
  Map<Player, Integer> previousValue = new HashMap<>();

  public FastAir(int modifier) {
    this.modifier = modifier;
  }

  @EventHandler
  public void onPlayerAirDrops(EntityAirChangeEvent e) {
    if (!(e.getEntity() instanceof Player p)) return;
    if (!HPPlayer.checkPermGameMode(p, HPPerm.FAST_AIR.value)) return;

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
