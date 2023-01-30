package io.github.exmserver.hardplus.module;

import io.github.exmserver.hardplus.util.Perm;
import io.github.exmserver.hardplus.util.PlayerUtil;
import io.github.exmserver.mol.util.Task;
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
          if (!PlayerUtil.checkPermGameMode(p, Perm.CREEPER_EXPLODE.value)) return;
          for (Entity e : p.getNearbyEntities(radius, radius, radius)) {
            if (e instanceof Creeper creeper) {
              creeper.explode();
            }
          }
        }
      }
    };
  }
}
