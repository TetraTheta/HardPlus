package io.github.tetratheta.hardplus;

import io.github.tetratheta.hardplus.module.*;
import io.github.tetratheta.hardplus.util.DmgMod;
import io.github.tetratheta.mol.util.BaseConfig;

import java.util.List;

public class HardplusConfig extends BaseConfig {
  final Hardplus plugin;

  final String strColdDamage = "module.cold-damage.";
  final String strCreeperCharge = "module.creeper-charge.";
  final String strCreeperExplode = "module.creeper-explode.";
  final String strDamageCritical = "module.damage-critical.";
  final String strDamageGive = "module.damage-give.";
  final String strDamageTake = "module.damage-take.";
  final String strDangerousNetherChest = "module.dangerous-nether-chest.";
  final String strFastAir = "module.fast-air.";
  final String strFireForever = "module.fire-forever.";
  final String strHungerDebuff = "module.hunger-debuff.";
  final String strLavaDeath = "module.lava-death.";
  final String strMobTarget = "module.mob-target.";
  final String strNoFireResistance = "module.no-fire-resistance.";
  final String strNoPassiveHealthRegen = "module.no-passive-health-regen.";
  final String strNoSweep = "module.no-sweep.";
  final String strNoWater = "module.no-water.";
  final String strSharpCut = "module.sharp-cut.";
  final String strUncookedPoison = "module.uncooked-poison.";
  final String strWitherSkeletonBow = "module.wither-skeleton-bow.";

  public HardplusConfig(Hardplus provided) {
    super(provided);
    plugin = provided;
    initialize();
  }

  public void initialize() {
    // Cold Damage
    if (getBoolean(strColdDamage + "enable", true)) {
      long delay = getLong(strColdDamage + "timer.delay", 10);
      long period = getLong(strColdDamage + "timer.period", 10);

      ColdDamage coldDamage = new ColdDamage();
      registerListeners(coldDamage);
      tasks.add(coldDamage.getTask().runTaskTimer(plugin, delay, period));
    }
    // Creeper Charge
    if (getBoolean(strCreeperCharge + "enable", true)) {
      double radius = getDouble(strCreeperCharge + "radius", 16);
      long delay = getLong(strCreeperCharge + "timer.delay", 10);
      long period = getLong(strCreeperCharge + "timer.period", 10);

      tasks.add(new CreeperCharge(radius).getTask().runTaskTimer(plugin, delay, period));
    }
    // Creeper Explode
    if (getBoolean(strCreeperExplode + "enable", true)) {
      double radius = getDouble(strCreeperExplode + "radius", 2.5);
      long delay = getLong(strCreeperExplode + "timer.delay", 10);
      long period = getLong(strCreeperExplode + "timer.period", 10);

      tasks.add(new CreeperExplode(radius).getTask().runTaskTimer(plugin, delay, period));
    }
    // Damage Critical
    if (getBoolean(strDamageCritical + "enable", true)) {
      registerListeners(new DamageCritical(plugin));
    }
    // Damage Give
    if (getBoolean(strDamageGive + "enable", true)) {
      double modifier = getDouble(strDamageGive + "modifier", 0.4, 0, 1);

      registerListeners(new DamageGive(modifier));
    }
    // Damage Take
    if (getBoolean(strDamageTake + "enable", true)) {
      // this is pure pain :(
      DmgMod dmgMod = new DmgMod();
      dmgMod.DEFAULT = getDouble(strDamageTake + "modifier.default", 2.5, 1, 100);
      dmgMod.PLAYER = getDouble(strDamageTake + "modifier.player", dmgMod.DEFAULT, 1, 100);
      dmgMod.MELEE = getDouble(strDamageTake + "modifier.melee", dmgMod.DEFAULT, 1, 100);
      dmgMod.FALL = getDouble(strDamageTake + "modifier.fall", dmgMod.DEFAULT, 1, 100);
      dmgMod.MAGIC = getDouble(strDamageTake + "modifier.magic", dmgMod.DEFAULT, 1, 100);
      dmgMod.ENVIRONMENT = getDouble(strDamageTake + "modifier.environment", dmgMod.DEFAULT, 1, 100);
      dmgMod.MOB_PASSIVE = getDouble(strDamageTake + "modifier.mob.passive", dmgMod.DEFAULT, 1, 100);
      dmgMod.MOB_HOSTILE = getDouble(strDamageTake + "modifier.mob.hostile.default", dmgMod.DEFAULT, 1, 100);
      dmgMod.ZOMBIE = getDouble(strDamageTake + "modifier.mob.hostile.zombie", dmgMod.DEFAULT, 1, 100);
      dmgMod.SKELETON = getDouble(strDamageTake + "modifier.mob.hostile.skeleton", dmgMod.DEFAULT, 1, 100);
      dmgMod.ENDERMAN = getDouble(strDamageTake + "modifier.mob.hostile.enderman", dmgMod.DEFAULT, 1, 100);

      registerListeners(new DamageTake(dmgMod));
    }
    // Dangerous Nether Chest
    if (getBoolean(strDangerousNetherChest + "enable", true)) {
      double radius = getDouble(strDangerousNetherChest + "radius", 32);

      registerListeners(new DangerousNetherChest(radius));
    }
    // Fast Air
    if (getBoolean(strFastAir + "enable", true)) {
      int modifier = getInt(strFastAir + "modifier", 40);

      registerListeners(new FastAir(modifier));
    }
    // Fire Forever
    if (getBoolean(strFireForever + "enable", true)) {
      registerListeners(new FireForever());
    }
    // Hunger Debuff
    if (getBoolean(strHungerDebuff + "enable", true)) {
      int foodLevel = getInt(strHungerDebuff + "food-level", 6, 0, 20);

      registerListeners(new HungerDebuff(foodLevel));
    }
    // Lava Death
    if (getBoolean(strLavaDeath + "enable", true)) {
      registerListeners(new LavaDeath(plugin));
    }
    // Mob Target
    if (getBoolean(strMobTarget + "enable", true)) {
      double range = getDouble(strMobTarget + "range", 16);
      long delay = getLong(strMobTarget + "timer.delay", 10);
      long period = getLong(strMobTarget + "timer.period", 10);

      tasks.add(new MobTarget(range).getTask().runTaskTimer(plugin, delay, period));
    }
    // No Fire Resistance
    if (getBoolean(strNoFireResistance + "enable", true)) {
      registerListeners(new NoFireResistance());
    }
    // No Passive Health Regen
    if (getBoolean(strNoPassiveHealthRegen + "enable", true)) {
      List<String> list = getStringList(strNoPassiveHealthRegen + "blocked-reason", List.of("SATIATED"));
      registerListeners(new NoPassiveHealthRegen(list));
    }
    // No Sweep
    if (getBoolean(strNoSweep + "enable", true)) {
      registerListeners(new NoSweep());
    }
    // No Water
    if (getBoolean(strNoWater + "enable", true)) {
      long delay = getLong(strNoWater + "timer.delay", 10);
      long period = getLong(strNoWater + "timer.period", 10);

      NoWater noWater = new NoWater(plugin);
      registerListeners(noWater);
      tasks.add(noWater.getTask().runTaskTimer(plugin, delay, period));
    }
    // Sharp Cut
    if (getBoolean(strSharpCut + "enable", true)) {
      boolean checkJump = getBoolean(strSharpCut + "condition.jump", true);
      boolean checkRun = getBoolean(strSharpCut + "condition.run", true);
      boolean checkSword = getBoolean(strSharpCut + "check.sword", true);
      boolean checkAxe = getBoolean(strSharpCut + "check.axe", true);
      boolean checkShear = getBoolean(strSharpCut + "check.shear", true);

      registerListeners(new SharpCut(checkJump, checkRun, checkSword, checkAxe, checkShear));
    }
    // Uncooked Poison
    if (getBoolean(strUncookedPoison + "enable", true)) {
      int duration = getInt(strUncookedPoison + "duration", 30);

      registerListeners(new UncookedPoison(duration));
    }
    // Wither Skeleton Bow
    if (getBoolean(strWitherSkeletonBow + "enable", true)) {
      double bowChance = getDouble(strWitherSkeletonBow + "spawn-chance", 50);
      int bowDamageLevel = getInt(strWitherSkeletonBow + "bow.damage-level", 3);
      int bowKnockbackLevel = getInt(strWitherSkeletonBow + "bow.knockback-level", 2);
      int arrowWitherLevel = getInt(strWitherSkeletonBow + "arrow.wither-level", 0);

      registerListeners(new WitherSkeletonBow(bowChance, bowDamageLevel, bowKnockbackLevel, arrowWitherLevel));
    }
  }
}
