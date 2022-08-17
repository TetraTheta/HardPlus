package xyz.tetratheta.hardplus;

import org.bukkit.scheduler.BukkitRunnable;

public interface Task {
  public BukkitRunnable getTask();
}
