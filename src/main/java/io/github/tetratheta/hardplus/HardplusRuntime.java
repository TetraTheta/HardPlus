package io.github.tetratheta.hardplus;

import io.github.tetratheta.hardplus.module.ColdDamage;
import io.github.tetratheta.hardplus.module.CreeperCharge;
import io.github.tetratheta.hardplus.module.CreeperExplode;
import io.github.tetratheta.hardplus.module.DamageCritical;
import io.github.tetratheta.hardplus.module.DamageGive;
import io.github.tetratheta.hardplus.module.DamageTake;
import io.github.tetratheta.hardplus.module.DangerousNetherChest;
import io.github.tetratheta.hardplus.module.FastAir;
import io.github.tetratheta.hardplus.module.FireForever;
import io.github.tetratheta.hardplus.module.HungerDebuff;
import io.github.tetratheta.hardplus.module.LavaDeath;
import io.github.tetratheta.hardplus.module.MobTarget;
import io.github.tetratheta.hardplus.module.NoFireResistance;
import io.github.tetratheta.hardplus.module.NoPassiveHealthRegen;
import io.github.tetratheta.hardplus.module.NoSweep;
import io.github.tetratheta.hardplus.module.NoWater;
import io.github.tetratheta.hardplus.module.ScheduledModule;
import io.github.tetratheta.hardplus.module.SharpCut;
import io.github.tetratheta.hardplus.module.UncookedPoison;
import io.github.tetratheta.hardplus.module.WitherSkeletonBow;
import io.github.tetratheta.hardplus.util.DmgMod;
import io.github.tetratheta.mol.config.BaseConfig;
import io.github.tetratheta.mol.plugin.PluginRuntime;
import java.util.List;

/// Wires configuration-backed modules and Bukkit resources for one HardPlus runtime.
public final class HardplusRuntime extends PluginRuntime {
  private static final String COLD_DAMAGE = "module.cold-damage.";
  private static final String CREEPER_CHARGE = "module.creeper-charge.";
  private static final String CREEPER_EXPLODE = "module.creeper-explode.";
  private static final String DAMAGE_CRITICAL = "module.damage-critical.";
  private static final String DAMAGE_GIVE = "module.damage-give.";
  private static final String DAMAGE_TAKE = "module.damage-take.";
  private static final String DANGEROUS_NETHER_CHEST = "module.dangerous-nether-chest.";
  private static final String FAST_AIR = "module.fast-air.";
  private static final String FIRE_FOREVER = "module.fire-forever.";
  private static final String HUNGER_DEBUFF = "module.hunger-debuff.";
  private static final String LAVA_DEATH = "module.lava-death.";
  private static final String MOB_TARGET = "module.mob-target.";
  private static final String NO_FIRE_RESISTANCE = "module.no-fire-resistance.";
  private static final String NO_PASSIVE_HEALTH_REGEN = "module.no-passive-health-regen.";
  private static final String NO_SWEEP = "module.no-sweep.";
  private static final String NO_WATER = "module.no-water.";
  private static final String SHARP_CUT = "module.sharp-cut.";
  private static final String UNCOOKED_POISON = "module.uncooked-poison.";
  private static final String WITHER_SKELETON_BOW = "module.wither-skeleton-bow.";
  private final BaseConfig config;
  private final Hardplus plugin;

  public HardplusRuntime(Hardplus plugin) {
    super(plugin);
    this.plugin = plugin;
    config = new BaseConfig(plugin) {};
    initialize();
  }

  private void initialize() {
    // Cold Damage
    if (config.getBoolean(COLD_DAMAGE + "enable", true)) {
      long delay = config.getLong(COLD_DAMAGE + "timer.delay", 10);
      long period = config.getLong(COLD_DAMAGE + "timer.period", 10);
      ColdDamage coldDamage = new ColdDamage();
      registerListener(coldDamage);
      registerScheduledModule(coldDamage, delay, period);
    }
    // Creeper Charge
    if (config.getBoolean(CREEPER_CHARGE + "enable", true)) {
      double radius = config.getDouble(CREEPER_CHARGE + "radius", 16);
      long delay = config.getLong(CREEPER_CHARGE + "timer.delay", 10);
      long period = config.getLong(CREEPER_CHARGE + "timer.period", 10);
      registerScheduledModule(new CreeperCharge(radius), delay, period);
    }
    // Creeper Explode
    if (config.getBoolean(CREEPER_EXPLODE + "enable", true)) {
      double radius = config.getDouble(CREEPER_EXPLODE + "radius", 2.5);
      long delay = config.getLong(CREEPER_EXPLODE + "timer.delay", 10);
      long period = config.getLong(CREEPER_EXPLODE + "timer.period", 10);
      registerScheduledModule(new CreeperExplode(radius), delay, period);
    }
    // Damage Critical
    if (config.getBoolean(DAMAGE_CRITICAL + "enable", true)) registerListener(new DamageCritical());
    // Damage Give
    if (config.getBoolean(DAMAGE_GIVE + "enable", true)) {
      double modifier = config.getDouble(DAMAGE_GIVE + "modifier", 0.4, 0, 1);
      registerListener(new DamageGive(modifier));
    }
    // Damage Take
    if (config.getBoolean(DAMAGE_TAKE + "enable", true)) {
      DmgMod dmgMod = new DmgMod();
      dmgMod.DEFAULT = config.getDouble(DAMAGE_TAKE + "modifier.default", 2.5, 1, 100);
      dmgMod.PLAYER = config.getDouble(DAMAGE_TAKE + "modifier.player", dmgMod.DEFAULT, 1, 100);
      dmgMod.MELEE = config.getDouble(DAMAGE_TAKE + "modifier.melee", dmgMod.DEFAULT, 1, 100);
      dmgMod.FALL = config.getDouble(DAMAGE_TAKE + "modifier.fall", dmgMod.DEFAULT, 1, 100);
      dmgMod.MAGIC = config.getDouble(DAMAGE_TAKE + "modifier.magic", dmgMod.DEFAULT, 1, 100);
      dmgMod.ENVIRONMENT = config.getDouble(DAMAGE_TAKE + "modifier.environment", dmgMod.DEFAULT, 1, 100);
      dmgMod.MOB_PASSIVE = config.getDouble(DAMAGE_TAKE + "modifier.mob.passive", dmgMod.DEFAULT, 1, 100);
      dmgMod.MOB_HOSTILE = config.getDouble(DAMAGE_TAKE + "modifier.mob.hostile.default", dmgMod.DEFAULT, 1, 100);
      dmgMod.ZOMBIE = config.getDouble(DAMAGE_TAKE + "modifier.mob.hostile.zombie", dmgMod.DEFAULT, 1, 100);
      dmgMod.SKELETON = config.getDouble(DAMAGE_TAKE + "modifier.mob.hostile.skeleton", dmgMod.DEFAULT, 1, 100);
      dmgMod.ENDERMAN = config.getDouble(DAMAGE_TAKE + "modifier.mob.hostile.enderman", dmgMod.DEFAULT, 1, 100);
      registerListener(new DamageTake(dmgMod));
    }
    // Dangerous Nether Chest
    if (config.getBoolean(DANGEROUS_NETHER_CHEST + "enable", true)) {
      double radius = config.getDouble(DANGEROUS_NETHER_CHEST + "radius", 32);
      registerListener(new DangerousNetherChest(radius));
    }
    // Fast Air
    if (config.getBoolean(FAST_AIR + "enable", true)) {
      int modifier = config.getInt(FAST_AIR + "modifier", 40);
      registerListener(new FastAir(modifier));
    }
    // Fire Forever
    if (config.getBoolean(FIRE_FOREVER + "enable", true)) registerListener(new FireForever());
    // Hunger Debuff
    if (config.getBoolean(HUNGER_DEBUFF + "enable", true)) {
      int foodLevel = config.getInt(HUNGER_DEBUFF + "food-level", 6, 0, 20);
      registerListener(new HungerDebuff(foodLevel));
    }
    // Lava Death
    if (config.getBoolean(LAVA_DEATH + "enable", true)) registerListener(new LavaDeath(this::runTask));
    // Mob Target
    if (config.getBoolean(MOB_TARGET + "enable", true)) {
      double range = config.getDouble(MOB_TARGET + "range", 16);
      long delay = config.getLong(MOB_TARGET + "timer.delay", 10);
      long period = config.getLong(MOB_TARGET + "timer.period", 10);
      registerScheduledModule(new MobTarget(range), delay, period);
    }
    // No Fire Resistance
    if (config.getBoolean(NO_FIRE_RESISTANCE + "enable", true)) registerListener(new NoFireResistance());
    // No Passive Health Regen
    if (config.getBoolean(NO_PASSIVE_HEALTH_REGEN + "enable", true)) {
      List<String> list = config.getStringList(NO_PASSIVE_HEALTH_REGEN + "blocked-reason", List.of("SATIATED"));
      registerListener(new NoPassiveHealthRegen(list));
    }
    // No Sweep
    if (config.getBoolean(NO_SWEEP + "enable", true)) registerListener(new NoSweep());
    // No Water
    if (config.getBoolean(NO_WATER + "enable", true)) {
      long delay = config.getLong(NO_WATER + "timer.delay", 10);
      long period = config.getLong(NO_WATER + "timer.period", 10);
      NoWater noWater = new NoWater(this::runTask);
      registerListener(noWater);
      registerScheduledModule(noWater, delay, period);
    }
    // Sharp Cut
    if (config.getBoolean(SHARP_CUT + "enable", true)) {
      boolean checkJump = config.getBoolean(SHARP_CUT + "condition.jump", true);
      boolean checkRun = config.getBoolean(SHARP_CUT + "condition.run", true);
      boolean checkSword = config.getBoolean(SHARP_CUT + "check.sword", true);
      boolean checkAxe = config.getBoolean(SHARP_CUT + "check.axe", true);
      boolean checkShear = config.getBoolean(SHARP_CUT + "check.shear", true);
      registerListener(new SharpCut(checkJump, checkRun, checkSword, checkAxe, checkShear));
    }
    // Uncooked Poison
    if (config.getBoolean(UNCOOKED_POISON + "enable", true)) {
      int duration = config.getInt(UNCOOKED_POISON + "duration", 30);
      registerListener(new UncookedPoison(duration));
    }
    // Wither Skeleton Bow
    if (config.getBoolean(WITHER_SKELETON_BOW + "enable", true)) {
      double bowChance = config.getDouble(WITHER_SKELETON_BOW + "spawn-chance", 50);
      int bowDamageLevel = config.getInt(WITHER_SKELETON_BOW + "bow.damage-level", 3);
      int bowKnockbackLevel = config.getInt(WITHER_SKELETON_BOW + "bow.knockback-level", 2);
      int arrowWitherLevel = config.getInt(WITHER_SKELETON_BOW + "arrow.wither-level", 0);
      registerListener(new WitherSkeletonBow(bowChance, bowDamageLevel, bowKnockbackLevel, arrowWitherLevel));
    }
  }

  private void registerScheduledModule(ScheduledModule module, long delay, long period) {
    registerTask(module.getTask().runTaskTimer(plugin, delay, period));
  }

  @Override
  public void terminate() {
    config.saveConfig();
    super.terminate();
  }
}
