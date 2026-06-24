package io.github.tetratheta.hardplus.module;

import io.github.tetratheta.hardplus.util.Perm;
import io.github.tetratheta.hardplus.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@SuppressWarnings("unused")
public class CreeperCharge implements ScheduledModule {
  final double radius;

  public CreeperCharge(double radius) {
    this.radius = radius;
  }

  @Override
  public BukkitRunnable getTask() {
    return new BukkitRunnable() {
      @Override
      public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
          if (!PlayerUtil.checkPermGameMode(p, Perm.CREEPER_CHARGE)) continue;
          for (Entity e : p.getNearbyEntities(radius, radius, radius)) {
            if (e instanceof Creeper creeper) {
              if (!(creeper.getTarget() instanceof Player target)) continue;
              if (PlayerUtil.checkPermGameMode(target, Perm.CREEPER_CHARGE)) creeper.setPowered(true);
            }
          }
        }
      }
    };
  }
}
