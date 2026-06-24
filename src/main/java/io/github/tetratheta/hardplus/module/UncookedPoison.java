package io.github.tetratheta.hardplus.module;

import io.github.tetratheta.hardplus.util.Perm;
import io.github.tetratheta.hardplus.util.PlayerUtil;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/// Poisons affected players when they eat configured uncooked foods.
@SuppressWarnings("unused")
public class UncookedPoison implements Listener {
  final int duration;
  final Set<Material> uncookedFood = Set.of(
    Material.BEEF, Material.CHICKEN, Material.COD, Material.MUTTON, Material.PORKCHOP, Material.POTATO,
    Material.RABBIT, Material.SALMON
  );

  public UncookedPoison(int duration) {
    // Duration is in second
    this.duration = duration * 20;
  }

  @EventHandler
  public void onPlayerEat(PlayerItemConsumeEvent e) {
    if (!PlayerUtil.checkPermGameMode(e.getPlayer(), Perm.UNCOOKED_POISON)) return;
    if (uncookedFood.contains(e.getItem().getType())) {
      PotionEffect effect = new PotionEffect(PotionEffectType.POISON, duration, 0, true, false);
      e.getPlayer().addPotionEffect(effect);
    }
  }
}
