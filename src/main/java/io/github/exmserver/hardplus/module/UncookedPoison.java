package io.github.exmserver.hardplus.module;

import io.github.exmserver.hardplus.util.HPPerm;
import io.github.exmserver.hardplus.util.HPPlayer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Set;

@SuppressWarnings("unused")
public class UncookedPoison implements Listener {
  final Set<Material> uncookedFood = Set.of(
      Material.POTATO,
      Material.BEEF,
      Material.CHICKEN,
      Material.COD,
      Material.MUTTON,
      Material.PORKCHOP,
      Material.RABBIT,
      Material.SALMON
  );

  final int duration;

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
