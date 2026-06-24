package io.github.tetratheta.hardplus.module;

import org.bukkit.scheduler.BukkitRunnable;

/// Provides the repeating task owned by a runtime-registered HardPlus module.
public interface ScheduledModule {
  BukkitRunnable getTask();
}
