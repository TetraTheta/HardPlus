package io.github.tetratheta.hardplus;

import io.github.tetratheta.mol.plugin.BasePlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

public final class Hardplus extends BasePlugin<HardplusRuntime> {
  public static ComponentLogger logger;
  public static WorldGuardHook worldGuardHook = null;

  @Override
  protected HardplusRuntime createRuntime() {
    return new HardplusRuntime(this);
  }

  @Override
  public void onLoad() {
    logger = getComponentLogger();
    try {
      worldGuardHook = new WorldGuardHook();
    } catch (NoClassDefFoundError ignored) {
      logger.info(Component.text("Optional dependency [WorldGuard] is not found. You can ignore this message."));
    }
  }
}
