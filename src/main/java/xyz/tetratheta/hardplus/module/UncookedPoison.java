package xyz.tetratheta.hardplus.module;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.tetratheta.hardplus.util.HPPerm;
import xyz.tetratheta.hardplus.util.HPPlayer;

import java.util.Set;

public class UncookedPoison implements Listener {
  Set<Material> uncookedFood = Set.of(
      Material.POTATO,
      Material.BEEF,
      Material.CHICKEN,
      Material.COD,
      Material.MUTTON,
      Material.PORKCHOP,
      Material.RABBIT,
      Material.SALMON
  );

  int duration;

  public UncookedPoison(int duration) {
    // Duration is in second
    this.duration = duration * 20;
  }

  @EventHandler
  public void onPlayerEat(PlayerItemConsumeEvent e) {
    if (!HPPlayer.checkPermGameMode(e.getPlayer(), HPPerm.UNCOOKED_POISON.value)) return;

    if (uncookedFood.contains(e.getItem().getType())) {
      PotionEffect effect = new PotionEffect(PotionEffectType.POISON, duration, 0, true, false);
      e.getPlayer().addPotionEffect(effect);
    }
  }
}
