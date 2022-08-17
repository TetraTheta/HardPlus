package xyz.tetratheta.hardplus.module;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import xyz.tetratheta.hardplus.util.HPPerm;
import xyz.tetratheta.hardplus.util.HPPlayer;

import java.util.Set;

public class SharpCut implements Listener {
  Set<Material> sword = Set.of(
      Material.WOODEN_SWORD,
      Material.STONE_SWORD,
      Material.IRON_SWORD,
      Material.DIAMOND_SWORD,
      Material.NETHERITE_SWORD
  );
  Set<Material> axe = Set.of(
      Material.WOODEN_AXE,
      Material.STONE_AXE,
      Material.IRON_AXE,
      Material.DIAMOND_AXE,
      Material.NETHERITE_AXE
  );

  boolean checkJump;
  boolean checkRun;
  boolean checkSword;
  boolean checkAxe;
  boolean checkShear;

  public SharpCut(boolean checkJump, boolean checkRun, boolean checkSword, boolean checkAxe, boolean checkShear) {
    this.checkJump = checkJump;
    this.checkRun = checkRun;
    this.checkSword = checkSword;
    this.checkAxe = checkAxe;
    this.checkShear  = checkShear;
  }

  @EventHandler
  public void onPlayerJump(PlayerJumpEvent e) {
    if (!checkJump) return;
    if (!HPPlayer.checkPermGameMode(e.getPlayer(), HPPerm.SHARP_CUT.value)) return;

    runCheck(e.getPlayer());
  }

  @EventHandler
  public void onPlayerRun(PlayerToggleSprintEvent e) {
    if (!checkRun || !e.isSprinting()) return;
    if (!HPPlayer.checkPermGameMode(e.getPlayer(), HPPerm.SHARP_CUT.value)) return;

    runCheck(e.getPlayer());
  }

  private void runCheck(Player p) {
    Material mainHand = p.getInventory().getItemInMainHand().getType();
    Material offHand = p.getInventory().getItemInOffHand().getType();

    if (checkSword) {
      if (sword.contains(mainHand) || sword.contains(offHand)) {
        HPPlayer.hurtCactus(p, 1);
      }
    }
    if (checkAxe) {
      if (axe.contains(mainHand) || axe.contains(offHand)) {
        HPPlayer.hurtCactus(p, 1);
      }
    }
    if (checkShear) {
      if (mainHand.equals(Material.SHEARS) || offHand.equals(Material.SHEARS)) {
        HPPlayer.hurtCactus(p, 1);
      }
    }
  }
}
