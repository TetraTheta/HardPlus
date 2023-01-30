package io.github.exmserver.hardplus.module;

import io.github.exmserver.hardplus.util.Perm;
import io.github.exmserver.hardplus.util.PlayerUtil;
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
    if (!netherBiomes.contains(((BlockInventoryHolder) holder).getBlock().getBiome()))
      return; // Return if not in The Nether biomes

    for (Entity entity : p.getNearbyEntities(radius, radius, radius)) {
      // I know this is dumb, but multiple instanceof or didn't work
      if (entity instanceof Piglin piglin) { // Piglin
        piglin.setTarget(p);
        piglin.getPathfinder().moveTo(p);
      } else if (entity instanceof PiglinBrute piglinBrute) { // Piglin Brute
        piglinBrute.setTarget(p);
        piglinBrute.getPathfinder().moveTo(p);
      } else if (entity instanceof PigZombie pigZombie) { // Zombified Piglin
        pigZombie.setTarget(p);
        pigZombie.getPathfinder().moveTo(p);
      } else if (entity instanceof Hoglin hoglin) { // Hoglin
        hoglin.setTarget(p);
        hoglin.getPathfinder().moveTo(p);
      } else if (entity instanceof Zoglin zoglin) { // Zoglin
        zoglin.setTarget(p);
        zoglin.getPathfinder().moveTo(p);
      } else if (entity instanceof Enderman enderman) { // Enderman
        enderman.setTarget(p);
        enderman.getPathfinder().moveTo(p);
      } else if (entity instanceof Skeleton skeleton) { // Skeleton
        skeleton.setTarget(p);
        skeleton.getPathfinder().moveTo(p);
      }
    }
  }
}
