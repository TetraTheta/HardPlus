package io.github.tetratheta.mol.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/// Provides base configuration methods, with useful methods which automatically creates corresponding config path
/// if the path is not present.
@SuppressWarnings("unused")
public abstract class BaseConfig {
  /// List of `BukkitTask` which are registered by your plugin
  public final Set<BukkitTask> tasks;
  private final Logger logger;
  private final File configPath;
  private final JavaPlugin plugin;
  private FileConfiguration config;

  /// Prepare essential fields of `BaseConfig`.
  ///
  /// Example:
  /// <pre>
  /// public YourConfiguration(YourPlugin provided) {
  ///   super(plugin);
  ///   // Do your own stuff
  /// }
  /// </pre>
  ///
  /// @param provided Your plugin instance which extends `JavaPlugin`
  public BaseConfig(JavaPlugin provided) {
    plugin = provided;
    plugin.reloadConfig();
    logger = plugin.getLogger();
    config = plugin.getConfig();
    configPath = new File(plugin.getDataFolder(), "config.yml");
    tasks = new HashSet<>();
  }

  /// Initialize your configuration. This is DIY(Do It Yourself) method you have to implement.
  ///
  /// This should involve with registering event listeners, creating new `BukkitTask`s etc.
  public abstract void initialize();

  /// Terminates configurations.
  ///
  /// This will unregister all registered event listeners and cancel all registered tasks.
  /// After this, you can safely save your config.
  public void terminate() {
    unregisterAllListeners();
    for (BukkitTask task : tasks) {
      task.cancel();
    }
  }

  /// Get config value as `boolean` from given path or default value if the path does not present.\
  ///
  /// This will create the path with given default value if the path doesn't exist.
  ///
  /// @param path Config path to get value from
  /// @param def  Default value if the path doesn't exist
  /// @return Config value as `boolean`
  public boolean getBoolean(String path, boolean def) {
    if (config.isSet(path)) {
      return config.getBoolean(path, def);
    } else {
      config.set(path, def);
      return def;
    }
  }

  /// Get config value as `double` from given path or default value if the path does not present, with minimum/maximum value.
  ///
  /// This will create the path with given default value if the path doesn't exist.
  ///
  /// You can set minimum value and maximum value of the value. If the value of the path is outside the boundary,
  /// it will be force-set to nearest boundary value.
  ///
  /// @param path Config path to get value from
  /// @param def  Default value if the path doesn't exist.
  /// @param min  Minimum value
  /// @param max  Maximum value
  /// @return Config value as `double`
  public double getDouble(String path, double def, double min, double max) {
    if (config.isSet(path)) {
      double value = config.getDouble(path, def);
      if (value >= max || value <= min) {
        config.set(path, def);
        return def;
      } else {
        return value;
      }
    } else {
      config.set(path, def);
      return def;
    }
  }

  /// Get config value as `double` from given path with default if the path does not present,
  /// with default minimum value as `0` and maximum value as `100`.
  ///
  /// This will create the path with given default value if the path doesn't exist.
  ///
  /// If the value of the path is outside the boundary, it will be force-set to nearest boundary value.
  ///
  /// @param path Config path to get value from
  /// @param def  Default value if the path doesn't exist.
  /// @return Config value as `double`
  public double getDouble(String path, double def) {
    return getDouble(path, def, 0, 100);
  }

  /// Get config value as `int` from given path with default value if the path does not present, with minimum/maximum value.
  ///
  /// This will create the path with given default value if the path doesn't exist.
  ///
  /// You can set minimum value and maximum value of the value. If the value of the path is outside the boundary,
  /// it will be force-set to nearest boundary value.
  ///
  /// @param path Config path to get value from
  /// @param def  Default value if the path doesn't exist.
  /// @param min  Minimum value
  /// @param max  Maximum value
  /// @return Config value as `int`
  public int getInt(String path, int def, int min, int max) {
    if (config.isSet(path)) {
      int value = config.getInt(path, def);
      if (value >= max || value <= min) {
        config.set(path, def);
        return def;
      } else {
        return value;
      }
    } else {
      config.set(path, def);
      return def;
    }
  }

  /// Get config value as `int` from given path with default if the path does not present,
  /// with default minimum value as `0` and maximum value as `100`.
  ///
  /// This will create the path with given default value if the path doesn't exist.
  ///
  /// If the value of the path is outside the boundary, it will be force-set to nearest boundary value.
  ///
  /// @param path Config path to get value from
  /// @param def  Default value if the path doesn't exist.
  /// @return Config value as `int`
  public int getInt(String path, int def) {
    return getInt(path, def, 0, 100);
  }

  /// Get config value as `List` from given path with default if the path does not present.
  ///
  /// This will create the path with given default value if the path doesn't exist.
  ///
  /// @param path Config path to get value from
  /// @param def  Default value if the path doesn't exist
  /// @return Config value as `List`
  public List<String> getStringList(String path, List<String> def) {
    if (config.isSet(path)) {
      return config.getStringList(path);
    } else {
      config.set(path, def);
      return def;
    }
  }

  /// Get config value as `long` from given path with default if the path does not present, with minimum/maximum value.
  ///
  /// This will create the path with given default value if the path doesn't exist.
  ///
  /// You can set minimum value and maximum value of the value. If the value of the path is outside of the boundary,
  /// it will be force-set to nearest boundary value.
  ///
  /// @param path Config path to get value from
  /// @param def  Default value if the path doesn't exist.
  /// @param min  Minimum value
  /// @param max  Maximum value
  /// @return Config value as `long`
  public long getLong(String path, long def, long min, long max) {
    if (config.isSet(path)) {
      long value = config.getLong(path, def);
      if (value >= max || value <= min) {
        config.set(path, def);
        return def;
      } else {
        return value;
      }
    } else {
      config.set(path, def);
      return def;
    }
  }

  /// Get config value as `long` from given path with default if the path does not present,
  /// with default minimum value as `0` and maximum value as `100`.
  ///
  /// This will create the path with given default value if the path doesn't exist.
  ///
  /// If the value of the path is outside the boundary, it will be force-set to nearest boundary value.
  ///
  /// @param path Config path to get value from
  /// @param def  Default value if the path doesn't exist.
  /// @return Config value as `long`
  public long getLong(String path, long def) {
    return getLong(path, def, 0, 100);
  }

  /// Get config value as `String` from given path or default value if the path does not present.
  ///
  /// This will create the path with given default value if the path doesn't exist.
  ///
  /// @param path Config path to get value from
  /// @param def  Default value if the path doesn't exist.
  /// @return Config value as `String`
  public String getString(String path, String def) {
    if (config.isSet(path)) {
      return config.getString(path, def);
    } else {
      config.set(path, def);
      return def;
    }
  }

  /// Registers all event listeners of single class provided.
  ///
  /// @param listener a class which implements `Listener`
  public void registerListeners(@NotNull Listener listener) {
    plugin.getServer().getPluginManager().registerEvents(listener, plugin);
  }

  /// Unregisters all event listeners of single class provided.
  ///
  /// @param listener a class which implements `Listener`
  public void unregisterListeners(@NotNull Listener listener) {
    HandlerList.unregisterAll(listener);
  }

  /// Unregisters all event listeners of this plugin
  public void unregisterAllListeners() {
    HandlerList.unregisterAll(plugin);
  }

  /// Saves current plugin configuration to file. Call this method inside of your plugin's `onDisable()`.
  public void saveConfig() {
    try {
      config.save(configPath);
      config = plugin.getConfig();
    } catch (IOException e) {
      logger.severe("Failed to save configuration file! - " + e.getLocalizedMessage());
    }
  }
}
