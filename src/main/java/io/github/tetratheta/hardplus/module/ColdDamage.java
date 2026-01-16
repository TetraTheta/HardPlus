package io.github.tetratheta.hardplus.module;

import io.github.tetratheta.hardplus.util.Perm;
import io.github.tetratheta.hardplus.util.PlayerUtil;
import io.github.tetratheta.mol.util.Task;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
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
      @SuppressWarnings("UnstableApiUsage")
      @Override
      public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
          if (PlayerUtil.checkPermGameMode(p, Perm.COLD_DAMAGE) && hasItem(p)) {
            DamageSource source = DamageSource.builder(DamageType.FREEZE).build();
            p.damage(1, source);
          }
        }
      }
    };
  }
}
