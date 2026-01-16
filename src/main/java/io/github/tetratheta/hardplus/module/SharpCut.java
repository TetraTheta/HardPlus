package io.github.tetratheta.hardplus.module;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import io.github.tetratheta.hardplus.util.Perm;
import io.github.tetratheta.hardplus.util.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import java.util.Set;

@SuppressWarnings("unused")
public class SharpCut implements Listener {
  final Set<Material> sword = Set.of(
      Material.WOODEN_SWORD,
      Material.STONE_SWORD,
      Material.IRON_SWORD,
      Material.DIAMOND_SWORD,
      Material.NETHERITE_SWORD
  );
  final Set<Material> axe = Set.of(
      Material.WOODEN_AXE,
      Material.STONE_AXE,
      Material.IRON_AXE,
      Material.DIAMOND_AXE,
      Material.NETHERITE_AXE
  );

  final boolean checkJump;
  final boolean checkRun;
  final boolean checkSword;
  final boolean checkAxe;
  final boolean checkShear;

  public SharpCut(boolean checkJump, boolean checkRun, boolean checkSword, boolean checkAxe, boolean checkShear) {
    this.checkJump = checkJump;
    this.checkRun = checkRun;
    this.checkSword = checkSword;
    this.checkAxe = checkAxe;
    this.checkShear = checkShear;
  }

  @EventHandler
  public void onPlayerJump(PlayerJumpEvent e) {
    if (!checkJump) return;
    if (!PlayerUtil.checkPermGameMode(e.getPlayer(), Perm.SHARP_CUT)) return;

    if (checkPlayer(e.getPlayer())) {
      e.setCancelled(true);
    }
  }

  @EventHandler
  public void onPlayerRun(PlayerToggleSprintEvent e) {
    if (!checkRun || !e.isSprinting()) return;
    if (!PlayerUtil.checkPermGameMode(e.getPlayer(), Perm.SHARP_CUT)) return;

    if (checkPlayer(e.getPlayer())) {
      e.setCancelled(true);
    }
  }

  @SuppressWarnings("UnstableApiUsage")
  private boolean checkPlayer(Player p) {
    Material mainHand = p.getInventory().getItemInMainHand().getType();
    Material offHand = p.getInventory().getItemInOffHand().getType();

    DamageSource source = DamageSource.builder(DamageType.CACTUS).build();

    if (checkSword) {
      if (sword.contains(mainHand) || sword.contains(offHand)) {
        p.damage(1, source);
        return true;
      }
    }
    if (checkAxe) {
      if (axe.contains(mainHand) || axe.contains(offHand)) {
        p.damage(1, source);
        return true;
      }
    }
    if (checkShear) {
      if (mainHand.equals(Material.SHEARS) || offHand.equals(Material.SHEARS)) {
        p.damage(1, source);
        return true;
      }
    }
    return false;
  }
}
