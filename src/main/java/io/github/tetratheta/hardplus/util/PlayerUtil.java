package io.github.tetratheta.hardplus.util;

import io.github.tetratheta.hardplus.Hardplus;
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
