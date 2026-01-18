package io.github.tetratheta.mol.util;

import org.bukkit.scheduler.BukkitRunnable;

/// Helper interface for class that creates `BukkitRunnable` which produces `BukkitTask`.
@SuppressWarnings("unused")
public interface Task {
  /// @return Returns `BukkitRunnable` which can return `BukkitTask` with `BukkitRunnable#getTask()`
  BukkitRunnable getTask();
}
