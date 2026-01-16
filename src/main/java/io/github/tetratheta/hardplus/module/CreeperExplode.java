package io.github.tetratheta.hardplus.module;

import io.github.tetratheta.hardplus.util.Perm;
import io.github.tetratheta.hardplus.util.PlayerUtil;
import io.github.tetratheta.mol.util.Task;
import org.bukkit.Bukkit;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@SuppressWarnings("unused")
public class CreeperExplode implements Task {

  final double radius;

  public CreeperExplode(double radius) {
    this.radius = radius;
  }

  @Override
  public BukkitRunnable getTask() {
    return new BukkitRunnable() {
      @Override
      public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
          if (!PlayerUtil.checkPermGameMode(p, Perm.CREEPER_EXPLODE)) continue;
          for (Entity e : p.getNearbyEntities(radius, radius, radius)) {
            if (e instanceof Creeper creeper) {
              if (creeper.getTarget() == null && !(creeper.getTarget() instanceof Player)) continue;
              if (PlayerUtil.checkPermGameMode((Player) creeper.getTarget(), Perm.CREEPER_CHARGE)) {
                creeper.explode();
              }
            }
          }
        }
      }
    };
  }
}
