package io.github.exmserver.hardplus.util;

import net.minecraft.world.damagesource.DamageSource;
import org.bukkit.entity.Player;

public class NMSPlayer {
  public static void hurtFreeze(Player player, float amount) {
    getNMSPlayer(player).hurt(DamageSource.FREEZE, amount);
  }

  public static void hurtCactus(Player player, float amount) {
    // Change this when version changes
    org.bukkit.craftbukkit.v1_19_R2.event.CraftEventFactory.blockDamage = player.getWorld().getBlockAt(0, 0, 0);
    getNMSPlayer(player).hurt(DamageSource.CACTUS, amount);
  }
  private static net.minecraft.world.entity.player.Player getNMSPlayer(Player player) {
    // Change this when version changes
    return ((org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer) player).getHandle();
  }
}
