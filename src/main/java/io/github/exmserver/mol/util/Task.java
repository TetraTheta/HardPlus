package io.github.exmserver.mol.util;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * Helper interface for class that creates {@code BukkitRunnable} which produces {@code BukkitTask}.
 */
@SuppressWarnings("unused")
public interface Task {
  /**
   * @return Returns {@code BukkitRunnable} which can return {@code BukkitTask} with {@code BukkitRunnable#getTask()}
   */
  BukkitRunnable getTask();
}
