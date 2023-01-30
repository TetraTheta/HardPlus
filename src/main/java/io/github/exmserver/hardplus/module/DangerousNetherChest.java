package io.github.exmserver.hardplus.module;

import io.github.exmserver.hardplus.util.Perm;
import io.github.exmserver.hardplus.util.PlayerUtil;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.block.Barrel;
import org.bukkit.block.Biome;
import org.bukkit.block.Chest;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.InventoryHolder;

import java.util.EnumSet;
import java.util.Set;

@SuppressWarnings("unused")
public class DangerousNetherChest implements Listener {
  final Set<Biome> netherBiomes = EnumSet.of(
      Biome.NETHER_WASTES,
      Biome.CRIMSON_FOREST,
      Biome.WARPED_FOREST,
      Biome.SOUL_SAND_VALLEY,
      Biome.BASALT_DELTAS
  );
  final double radius;

  public DangerousNetherChest(double radius) {
    this.radius = radius;
  }

  @EventHandler
  public void onPlayerOpenChest(InventoryOpenEvent e) {
    if (!(e.getPlayer() instanceof Player p)) return; // Return if it is not player
    if (!PlayerUtil.checkPermGameMode(p, Perm.DANGEROUS_NETHER_CHEST.value)) return; // Return if no permission
    InventoryHolder holder = e.getInventory().getHolder();
    if (!(holder instanceof Chest) && !(holder instanceof Barrel)) return; // Return if not is Chest or Barrel
    if (!netherBiomes.contains(((BlockInventoryHolder) holder).getBlock().getBiome())) return; // Return if not in The Nether biomes

    for (Entity entity : p.getNearbyEntities(radius, radius, radius)) {
      // I know this is dumb, but multiple instanceof or didn't work
      if (entity instanceof Piglin piglin) {
        Bukkit.getLogger().info("Piglin is angry at " + PlainTextComponentSerializer.plainText().serialize(p.displayName()));
        piglin.setTarget(p); // Piglin
        piglin.getPathfinder().moveTo(p);
      } else if (entity instanceof PiglinBrute piglinBrute) {
        Bukkit.getLogger().info("Piglin Brute is angry at " + PlainTextComponentSerializer.plainText().serialize(p.displayName()));
        piglinBrute.setTarget(p); // Piglin Brute
        piglinBrute.getPathfinder().moveTo(p);
      } else if (entity instanceof PigZombie pigZombie) {
        Bukkit.getLogger().info("Zombified Piglin is angry at " + PlainTextComponentSerializer.plainText().serialize(p.displayName()));
        pigZombie.setTarget(p); // Zombified Piglin
        pigZombie.getPathfinder().moveTo(p);
      } else if (entity instanceof Hoglin hoglin) {
        Bukkit.getLogger().info("Hoglin is angry at " + PlainTextComponentSerializer.plainText().serialize(p.displayName()));
        hoglin.setTarget(p); // Hoglin
        hoglin.getPathfinder().moveTo(p);
      } else if (entity instanceof Zoglin zoglin) {
        Bukkit.getLogger().info("Zoglin is angry at " + PlainTextComponentSerializer.plainText().serialize(p.displayName()));
        zoglin.setTarget(p); // Zoglin
        zoglin.getPathfinder().moveTo(p);
      } else if (entity instanceof Enderman enderman) {
        Bukkit.getLogger().info("Enderman is angry at " + PlainTextComponentSerializer.plainText().serialize(p.displayName()));
        enderman.setTarget(p); // Enderman
        enderman.getPathfinder().moveTo(p);
      } else if (entity instanceof Skeleton skeleton) {
        Bukkit.getLogger().info("Skeleton is angry at " + PlainTextComponentSerializer.plainText().serialize(p.displayName()));
        skeleton.setTarget(p); // Skeleton
        skeleton.getPathfinder().moveTo(p);
      }
    }
  }
}
