package io.github.tetratheta.hardplus.module;

import io.github.tetratheta.hardplus.util.Perm;
import io.github.tetratheta.hardplus.util.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffectType;

@SuppressWarnings("unused")
public class NoFireResistance implements Listener {
  @EventHandler
  public void onFireResistanceAdded(EntityPotionEffectEvent e) {
    if (!(e.getEntity() instanceof Player p)) return;
    if (!PlayerUtil.checkPermGameMode(p, Perm.NO_FIRE_RESISTANCE.value)) return;

    if (e.getAction().equals(EntityPotionEffectEvent.Action.ADDED)) {
      if (e.getNewEffect() != null && e.getNewEffect().getType().equals(PotionEffectType.FIRE_RESISTANCE)) {
        e.setCancelled(true);
      }
    }
  }
}
