package xyz.tetratheta.hardplus.util;

import net.minecraft.world.damagesource.DamageSource;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class HPPlayer {
  public static void hurtFreeze(Player player, float amount) {
    getNMSPlayer(player).hurt(DamageSource.FREEZE, amount);
  }

  public static void hurtCactus(Player player, float amount) {
    // Change this when version changes
    org.bukkit.craftbukkit.v1_19_R1.event.CraftEventFactory.blockDamage = player.getWorld().getBlockAt(0, 0, 0);
    getNMSPlayer(player).hurt(DamageSource.CACTUS, amount);
  }

  public static boolean checkPermGameMode(Player player, String permission) {
    if (!(player.getGameMode() == GameMode.SURVIVAL) && !(player.getGameMode() == GameMode.ADVENTURE)) return false;
    return player.hasPermission(permission);
  }

  private static net.minecraft.world.entity.player.Player getNMSPlayer(Player player) {
    // Change this when version changes
    return ((org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer) player).getHandle();
  }
}
