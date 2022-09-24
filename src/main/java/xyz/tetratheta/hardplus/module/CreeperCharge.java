package xyz.tetratheta.hardplus.module;

import org.bukkit.Bukkit;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.tetratheta.commonlib.Task;
import xyz.tetratheta.hardplus.util.HPPerm;
import xyz.tetratheta.hardplus.util.HPPlayer;

public class CreeperCharge implements Task {
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
          if (!HPPlayer.checkPermGameMode(p, HPPerm.CREEPER_CHARGE.value)) return;
          for (Entity e : p.getNearbyEntities(radius, radius, radius)) {
            if (e instanceof Creeper creeper) { creeper.setPowered(true); }
          }
        }
      }
    };
  }
}
