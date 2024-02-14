package io.github.tetratheta.hardplus.module;

import io.github.tetratheta.hardplus.util.Perm;
import io.github.tetratheta.hardplus.util.PlayerUtil;
import io.github.tetratheta.mol.util.Task;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

@SuppressWarnings("unused")
public class MobTarget implements Task {
  final double range;

  public MobTarget(double range) {
    this.range = range;
  }

  @Override
  public BukkitRunnable getTask() {
    return new BukkitRunnable() {
      @Override
      public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
          if (!PlayerUtil.checkPermGameMode(p, Perm.MOB_TARGET.value)) continue;
          if (canBypass(p)) continue;
          for (Entity e : p.getNearbyEntities(range, range, range)) {
            if (e instanceof Monster monster) {
              monster.setTarget(p);
              monster.getPathfinder().moveTo(p);
            }
          }
        }
      }
    };
  }

  private boolean canBypass(Player p) {
    return p.hasPotionEffect(PotionEffectType.INVISIBILITY) && !hasArmor(p);
  }

  private boolean hasArmor(Player p) {
    return !(p.getInventory().getHelmet() == null &&
        p.getInventory().getChestplate() == null &&
        p.getInventory().getLeggings() == null &&
        p.getInventory().getBoots() == null);
  }
}
