package xyz.tetratheta.hardplus.module;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CauldronLevelChangeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.tetratheta.commonlib.Task;
import xyz.tetratheta.hardplus.Hardplus;
import xyz.tetratheta.hardplus.util.HPPerm;
import xyz.tetratheta.hardplus.util.HPPlayer;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class NoWater implements Listener, Task {
  final Set<Biome> netherBiomes = EnumSet.of(
      Biome.NETHER_WASTES,
      Biome.CRIMSON_FOREST,
      Biome.WARPED_FOREST,
      Biome.SOUL_SAND_VALLEY,
      Biome.BASALT_DELTAS
  );
  final Set<Biome> theEndBiomes = EnumSet.of(
      Biome.THE_END,
      Biome.SMALL_END_ISLANDS,
      Biome.END_MIDLANDS,
      Biome.END_HIGHLANDS,
      Biome.END_BARRENS
  );

  final Set<Block> cauldrons = new HashSet<>();

  final Hardplus plugin;

  public NoWater(Hardplus hardplus) {
    this.plugin = hardplus;
  }

  @EventHandler
  public void onPlayerEmptyBucketInTheNether(CauldronLevelChangeEvent e) {
    // The Nether
    if (!(e.getEntity() instanceof Player p)) return;
    if (!HPPlayer.checkPermGameMode(p, HPPerm.NO_WATER.value)) return;
    if (!e.getReason().equals(CauldronLevelChangeEvent.ChangeReason.BUCKET_EMPTY)) return;

    if (netherBiomes.contains(e.getBlock().getBiome())) {
      if (e.getBlock().getType().equals(Material.CAULDRON)) {
        // Only water cauldron has levelled data
        cauldrons.add(e.getBlock());
      }
    }
  }

  @EventHandler
  public void onPlayerEmptyBucketInTheEnd(PlayerBucketEmptyEvent e) {
    // The End
    if (!HPPlayer.checkPermGameMode(e.getPlayer(), HPPerm.NO_WATER.value)) return;
    if (theEndBiomes.contains(e.getBlockClicked().getBiome())) {
      Bukkit.getScheduler().runTask(plugin, () -> {
        PlayerInventory inventory = e.getPlayer().getInventory();
        ItemStack mainHand = inventory.getItemInMainHand();
        ItemStack offHand = inventory.getItemInOffHand();
        if (mainHand.getType().equals(Material.WATER_BUCKET))
          inventory.setItemInMainHand(new ItemStack(Material.BUCKET));
        else if (offHand.getType().equals(Material.WATER_BUCKET))
          inventory.setItemInOffHand(new ItemStack(Material.BUCKET));
      });
      e.setCancelled(true);
    }
  }

  @Override
  public BukkitRunnable getTask() {
    return new BukkitRunnable() {
      @Override
      public void run() {
        for (Block b : cauldrons) {
          if (b.getBlockData() instanceof Levelled levelled) {
            int newLevel = levelled.getLevel() - 1;
            if (newLevel == 0) {
              b.setType(Material.CAULDRON);
              cauldrons.remove(b);
              return;
            }
            levelled.setLevel(newLevel);
            b.setBlockData(levelled);
          }
          if (b.getType().equals(Material.CAULDRON)) cauldrons.remove(b);
        }
      }
    };
  }
}
