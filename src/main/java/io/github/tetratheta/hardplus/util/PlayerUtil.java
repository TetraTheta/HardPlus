package io.github.tetratheta.hardplus.util;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import io.github.tetratheta.hardplus.Hardplus;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class PlayerUtil {
  public static boolean checkPermGameMode(Player player, Perm permission) {
    if (!(player.getGameMode() == GameMode.SURVIVAL) && !(player.getGameMode() == GameMode.ADVENTURE)) return false;

    boolean test = player.hasPermission(permission.value);
    if (Hardplus.worldGuardHook != null) {
      test |= Hardplus.worldGuardHook.checkFlag(player, permission.flagName());
    }

    return test;
  }
}
