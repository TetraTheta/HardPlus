package io.github.tetratheta.hardplus.util;

import net.minecraft.world.damagesource.DamageSource;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.entity.Player;

public class NMSPlayer {
  // Let's hope that Paper provides proper damage API stuff
  public static void hurtFreeze(Player player, float amount) {
    DamageSource freeze = getNMSPlayer(player).level.damageSources().freeze();
    getNMSPlayer(player).hurt(freeze, amount);
    CraftEventFactory.blockDamage = null;
  }

  public static void hurtCactus(Player player, float amount) {
    // Change this when version changes
    CraftEventFactory.blockDamage = player.getWorld().getBlockAt(0, 0, 0);
    DamageSource cactus = getNMSPlayer(player).level.damageSources().cactus();
    getNMSPlayer(player).hurt(cactus, amount);
    CraftEventFactory.blockDamage = null;
  }
  private static net.minecraft.world.entity.player.Player getNMSPlayer(Player player) {
    // Change this when version changes
    return ((org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer) player).getHandle();
  }
}
