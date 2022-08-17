package xyz.tetratheta.hardplus;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Provides configuration base.
 */
public abstract class BaseConfig {
  private final Logger logger;
  private final File configPath;
  private final JavaPlugin plugin;
  private FileConfiguration config;

  public BaseConfig(JavaPlugin provided) {
    plugin = provided;
    plugin.reloadConfig();
    logger = plugin.getLogger();
    config = plugin.getConfig();
    configPath = new File(plugin.getDataFolder(), "config.yml");
  }

  /**
   * Call this at {@code onDisable()}
   */
  protected void saveConfig() {
    try {
      config.save(configPath);
      config = plugin.getConfig();
    } catch (IOException e) {
      logger.severe("Failed to save configuration file! - " + e.getLocalizedMessage());
    }
  }

  public void registerListeners(@NotNull Listener listener) {
    plugin.getServer().getPluginManager().registerEvents(listener, plugin);
  }

  public void unregisterListeners(@NotNull Listener listener) {
    HandlerList.unregisterAll(listener);
  }

  public void unregisterAllListeners() {
    HandlerList.unregisterAll(plugin);
  }

  public boolean getBoolean(String path, boolean def) {
    if (config.isSet(path)) {
      return config.getBoolean(path, def);
    } else {
      config.set(path, def);
      return def;
    }
  }

  public String getString(String path, String def) {
    if (config.isSet(path)) {
      return config.getString(path, def);
    } else {
      config.set(path, def);
      return def;
    }
  }

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

  public double getDouble(String path, double def) {
    return getDouble(path, def, 0, 100);
  }

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

  public long getLong(String path, long def) {
    return getLong(path, def, 0, 100);
  }

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

  public int getInt(String path, int def) {
    return getInt(path, def, 0, 100);
  }
}
