package io.github.tetratheta.hardplus.util;

public enum Perm {
  COLD_DAMAGE("hardplus.cold-damage"),
  CREEPER_CHARGE("hardplus.creeper-charge"),
  CREEPER_EXPLODE("hardplus.creeper-explode"),
  DAMAGE_CRITICAL("hardplus.damage-critical"),
  DAMAGE_GIVE("hardplus.damage-give"),
  DAMAGE_TAKE("hardplus.damage-take"),
  DANGEROUS_NETHER_CHEST("hardplus.dangerous-nether-chest"),
  FAST_AIR("hardplus.fast-air"),
  FIRE_FOREVER("hardplus.fire-forever"),
  HUNGER_DEBUFF("hardplus.hunger-debuff"),
  LAVA_DEATH("hardplus.lava-death"),
  MOB_TARGET("hardplus.mob-target"),
  NO_FIRE_RESISTANCE("hardplus.no-fire-resistance"),
  NO_SWEEP("hardplus.no-sweep"),
  NO_WATER("hardplus.no-water"),
  SHARP_CUT("hardplus.sharp-cut"),
  UNCOOKED_POISON("hardplus.uncooked-poison"),
  WITHER_SKELETON_BOW("hardplus.wither-skeleton-bow");
  public final String value;

  Perm(String value) { this.value = value; }
}
