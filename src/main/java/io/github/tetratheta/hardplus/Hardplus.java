package io.github.tetratheta.hardplus;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import io.github.tetratheta.hardplus.util.Perm;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class Hardplus extends JavaPlugin {
  HardplusConfig config;

  public static Map<String, StateFlag> worldGuardFlags = new HashMap<>();

  @Override
  public void onLoad() {
    try {
      FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
      for (Perm perm : Perm.values()) {
        StateFlag flag = new StateFlag(perm.flagName(), false);
        registry.register(flag);
        worldGuardFlags.put(perm.flagName(), flag);
      }
    } catch (FlagConflictException ignored) {
      // TODO maybe tell in log? there is no other logging in this plugin, so i dont know
    } catch (NoClassDefFoundError ignored) {
      // WorldGuard not loaded. Ignore.
    }
  }

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
