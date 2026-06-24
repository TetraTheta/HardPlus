package io.github.tetratheta.hardplus.module;

import io.github.tetratheta.hardplus.util.Perm;
import io.github.tetratheta.hardplus.util.PlayerUtil;
import java.util.Set;
import org.bukkit.block.Barrel;
import org.bukkit.block.Biome;
import org.bukkit.block.Chest;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Hoglin;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Piglin;
import org.bukkit.entity.PiglinBrute;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zoglin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.InventoryHolder;

/// Makes Nether mobs target affected players who open chests or barrels in Nether biomes.
@SuppressWarnings("unused")
public class DangerousNetherChest implements Listener {
  final Set<Biome> netherBiomes = Set.of(Biome.BASALT_DELTAS, Biome.CRIMSON_FOREST, Biome.NETHER_WASTES, Biome.SOUL_SAND_VALLEY, Biome.WARPED_FOREST);
  final double radius;

  public DangerousNetherChest(double radius) {
    this.radius = radius;
  }

  @EventHandler
  public void onPlayerOpenChest(InventoryOpenEvent e) {
    // Return if it is not player
    if (!(e.getPlayer() instanceof Player p)) return;
    // Return if no permission
    if (!PlayerUtil.checkPermGameMode(p, Perm.DANGEROUS_NETHER_CHEST)) return;
    InventoryHolder holder = e.getInventory().getHolder();
    // Return if not is Chest or Barrel
    if (!(holder instanceof Chest) && !(holder instanceof Barrel)) return;
    // Return if not in The Nether biomes
    if (!netherBiomes.contains(((BlockInventoryHolder) holder).getBlock().getBiome())) return;
    for (Entity entity : p.getNearbyEntities(radius, radius, radius)) {
      // I know this is dumb, but multiple instanceof or didn't work
      switch (entity) {
        case Piglin piglin -> {
          piglin.setTarget(p);
          piglin.getPathfinder().moveTo(p);
        }
        case PiglinBrute piglinBrute -> {
          piglinBrute.setTarget(p);
          piglinBrute.getPathfinder().moveTo(p);
        }
        case PigZombie pigZombie -> {
          pigZombie.setTarget(p);
          pigZombie.getPathfinder().moveTo(p);
        }
        case Hoglin hoglin -> {
          hoglin.setTarget(p);
          hoglin.getPathfinder().moveTo(p);
        }
        case Zoglin zoglin -> {
          zoglin.setTarget(p);
          zoglin.getPathfinder().moveTo(p);
        }
        case Enderman enderman -> {
          enderman.setTarget(p);
          enderman.getPathfinder().moveTo(p);
        }
        case Skeleton skeleton -> {
          skeleton.setTarget(p);
          skeleton.getPathfinder().moveTo(p);
        }
        case null, default -> {}
      }
    }
  }
}
