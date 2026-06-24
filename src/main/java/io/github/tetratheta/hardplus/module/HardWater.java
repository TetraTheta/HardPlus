package io.github.tetratheta.hardplus.module;

import io.github.tetratheta.hardplus.util.Perm;
import io.github.tetratheta.hardplus.util.PlayerUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/// Treats high water impacts like fall damage for affected players.
///
/// Idea by JustusJG
@SuppressWarnings("unused")
public class HardWater implements Listener {
  final Map<UUID, Float> lastFallDistance = new HashMap<>();
  final float minimumFallDistance;
  final double modifier;

  public HardWater(float minimumFallDistance, double modifier) {
    this.minimumFallDistance = minimumFallDistance;
    this.modifier = modifier;
  }

  @EventHandler
  public void onPlayerFallOnWater(PlayerMoveEvent e) {
    Player p = e.getPlayer();
    if (!PlayerUtil.checkPermGameMode(p, Perm.HARD_WATER)) return;
    float fallDistance = p.getFallDistance();
    UUID playerId = p.getUniqueId();
    boolean inWater = p.getLocation().getBlock().getType().equals(Material.WATER);
    if (!inWater && fallDistance != 0) {
      // Keep the last non-water fall distance because water resets it before damage is applied.
      lastFallDistance.put(playerId, fallDistance);
    }
    if (!inWater) return;
    // Fall distance gate.
    float storedFallDistance = lastFallDistance.getOrDefault(playerId, 0f);
    if (storedFallDistance < minimumFallDistance) return;
    lastFallDistance.remove(playerId);
    // Omitting the damage location still showed `death.fell.accident.water` in testing.
    DamageSource source = DamageSource.builder(DamageType.FALL).withDamageLocation(p.getLocation()).build();
    double damage = Math.max(0, Math.floor((storedFallDistance - 3) * modifier));
    p.damage(damage, source);
  }
}
