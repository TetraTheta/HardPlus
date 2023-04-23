package io.github.tetratheta.hardplus;

import org.bukkit.plugin.java.JavaPlugin;

public final class Hardplus extends JavaPlugin {
  HardplusConfig config;

  @Override
  public void onEnable() {
    // Config
    config = new HardplusConfig(this);
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
    config.saveConfig();
    config.terminate();
  }
}
