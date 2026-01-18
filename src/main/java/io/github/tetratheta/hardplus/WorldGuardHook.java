package io.github.tetratheta.hardplus;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import io.github.tetratheta.hardplus.util.Perm;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class WorldGuardHook {
  private final Map<String, StateFlag> worldGuardFlags = new HashMap<>();

  public WorldGuardHook() {
    FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
    try {
      for (Perm perm : Perm.values()) {
        StateFlag flag = new StateFlag(perm.flagName(), false);
        registry.register(flag);
        worldGuardFlags.put(perm.flagName(), flag);
      }
    } catch (Exception ignored) {
      // TODO maybe tell in log? there is no other logging in this plugin, so i dont know
    }
  }

  public boolean checkFlag(Player player, String flagName) {
    LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
    RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
    RegionQuery query = container.createQuery();

    StateFlag stateFlag = worldGuardFlags.get(flagName);
    return query.testState(localPlayer.getLocation(), localPlayer, stateFlag);
  }
}
