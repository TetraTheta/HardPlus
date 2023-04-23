package io.github.tetratheta.hardplus.util;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class PlayerUtil {
  public static boolean checkPermGameMode(Player player, String permission) {
    if (!(player.getGameMode() == GameMode.SURVIVAL) && !(player.getGameMode() == GameMode.ADVENTURE)) return false;
    return player.hasPermission(permission);
  }
}
