package xyz.tetratheta.hardplus.module;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.tetratheta.hardplus.util.HPPerm;
import xyz.tetratheta.hardplus.util.HPPlayer;

public class HungerDebuff implements Listener {
  final int foodLevel;

  public HungerDebuff(int foodLevel) {
    this.foodLevel = foodLevel;
  }

  @EventHandler
  public void onFoodLevelChange(FoodLevelChangeEvent e) {
    if (!(e.getEntity() instanceof Player p)) return;
    if (!HPPlayer.checkPermGameMode(p, HPPerm.HUNGER_DEBUFF.value)) return;

    if (e.getFoodLevel() < foodLevel) {
      PotionEffect slowness = new PotionEffect(PotionEffectType.SLOW, 3600, 0, true, false);
      PotionEffect miningFatigue = new PotionEffect(PotionEffectType.SLOW_DIGGING, 3600, 1, true, false);
      PotionEffect weakness = new PotionEffect(PotionEffectType.WEAKNESS, 3600, 1, true, false);
      p.addPotionEffect(slowness);
      p.addPotionEffect(miningFatigue);
      p.addPotionEffect(weakness);
    }
  }
}
