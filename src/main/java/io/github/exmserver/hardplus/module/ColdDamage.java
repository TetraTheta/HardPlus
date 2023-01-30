package io.github.exmserver.hardplus.module;

import io.github.exmserver.hardplus.util.HPPerm;
import io.github.exmserver.hardplus.util.HPPlayer;
import io.github.exmserver.mol.util.Task;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

@SuppressWarnings("unused")
public class ColdDamage implements Listener, Task {
  final Set<Material> coldItemSet = Set.of(
      Material.ICE,
      Material.PACKED_ICE,
      Material.BLUE_ICE,
      Material.SNOW_BLOCK,
      Material.SNOWBALL
  );

  private boolean hasItem(Player p) {
    boolean hasItem = false;
    for (Material material : coldItemSet) {
      if (p.getInventory().contains(material) || p.getInventory().getItemInOffHand().getType().equals(material)) {
        hasItem = true;
        break;
      }
    }
    return hasItem;
  }

  @Override
  public BukkitRunnable getTask() {
    return new BukkitRunnable() {
      @Override
      public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
          if (HPPlayer.checkPermGameMode(p, HPPerm.COLD_DAMAGE.value) && hasItem(p)) {
            HPPlayer.hurtFreeze(p, 1);
          }
        }
      }
    };
  }
}
