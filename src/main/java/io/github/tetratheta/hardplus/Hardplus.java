package io.github.tetratheta.hardplus;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.plugin.java.JavaPlugin;

public final class Hardplus extends JavaPlugin {
  public static ComponentLogger logger;
  public static WorldGuardHook worldGuardHook = null;
  HardplusConfig config;

  @Override
  public void onLoad() {
    logger = getComponentLogger();
    try {
      worldGuardHook = new WorldGuardHook();
    } catch (NoClassDefFoundError ignored) {
      logger.info(Component.text("Optional dependency [WorldGuard] is not found. You can ignore this message."));
    }
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
    config.saveConfig();
    config.terminate();
  }

  @Override
  public void onEnable() {
    // Config
    config = new HardplusConfig(this);
  }
}
