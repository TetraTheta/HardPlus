package xyz.tetratheta.hardplus.module;

import org.bukkit.Bukkit;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.tetratheta.hardplus.Task;
import xyz.tetratheta.hardplus.util.HPPerm;
import xyz.tetratheta.hardplus.util.HPPlayer;

public class CreeperExplode implements Task {

  double radius;

  public CreeperExplode(double radius) {
    this.radius = radius;
  }

  @Override
  public BukkitRunnable getTask() {
    return new BukkitRunnable() {
      @Override
      public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
          if (!HPPlayer.checkPermGameMode(p, HPPerm.CREEPER_EXPLODE.value)) return;
          for (Entity e : p.getNearbyEntities(radius, radius, radius)) {
            if (e instanceof Creeper creeper) { creeper.explode(); }
          }
        }
      }
    };
  }
}
