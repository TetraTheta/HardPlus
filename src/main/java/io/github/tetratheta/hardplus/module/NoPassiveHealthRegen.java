package io.github.tetratheta.hardplus.module;

import io.github.tetratheta.hardplus.util.Perm;
import io.github.tetratheta.hardplus.util.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent.Action;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NoPassiveHealthRegen implements Listener {
  final List<String> denyList;
  final Set<Player> playerList;

  public NoPassiveHealthRegen(List<String> list) {
    this.denyList = list;
    this.playerList = new HashSet<>();
  }

  @EventHandler
  public void onPlayerEffectChange(EntityPotionEffectEvent e) {
    if (!(e.getEntity() instanceof Player p)) return;
    if (!PlayerUtil.checkPermGameMode(p, Perm.NO_PASSIVE_HEALTH_REGEN)) return;

    if (e.getModifiedType() == PotionEffectType.REGENERATION) {
      Action action = e.getAction();
      if (e.getCause() == Cause.BEACON && (action == Action.ADDED || action == Action.CHANGED)) {
        playerList.add(p);
      } else {
        playerList.remove(p);
      }
    }
  }

  @EventHandler
  public void onPlayerHealthRegen(EntityRegainHealthEvent e) {
    if (!(e.getEntity() instanceof Player p)) return;
    if (!PlayerUtil.checkPermGameMode(p, Perm.NO_PASSIVE_HEALTH_REGEN)) return;
    RegainReason reason = e.getRegainReason();
    denyList.forEach(i -> {
      try {
        // Valid values: BEACON, MAGIC, MAGIC_REGEN, REGEN, SATIATED
        String upper = i.toUpperCase();
        switch (upper) {
          case "BEACON": {
            // BEACON: When a player is healed over time by a beacon's Regeneration effect
            if (reason == RegainReason.MAGIC_REGEN && playerList.contains(p)) {
              e.setAmount(0);
              e.setCancelled(true);
            }
            break;
          }
          case "MAGIC": {
            // MAGIC: When a player is healed by a potion or spell
            if (reason == RegainReason.MAGIC) {
              e.setAmount(0);
              e.setCancelled(true);
            }
            break;
          }
          case "MAGIC_REGEN": {
            // MAGIC_REGEN: When a player is healed over time by a potion or spell
            if (reason == RegainReason.MAGIC_REGEN) {
              e.setAmount(0);
              e.setCancelled(true);
            }
            break;
          }
          case "REGEN": {
            // REGEN: When a player regains health from regenerating due to Peaceful mode (difficulty=0)
            // Should I block it?
            if (reason == RegainReason.REGEN) {
              e.setAmount(0);
              e.setCancelled(true);
            }
            break;
          }
          case "SATIATED": {
            // When a player regains health from regenerating due to their hunger being satisfied
            if (reason == RegainReason.SATIATED) {
              e.setAmount(0);
              e.setCancelled(true);
            }
            break;
          }
        }
      } catch (IllegalArgumentException ignored) {
      }
    });
  }
}
