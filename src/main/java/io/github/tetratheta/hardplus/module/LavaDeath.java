package io.github.tetratheta.hardplus.module;

import io.github.tetratheta.hardplus.util.Perm;
import io.github.tetratheta.hardplus.util.PlayerUtil;
import java.util.function.Consumer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@SuppressWarnings("unused")
public class LavaDeath implements Listener {
  final Consumer<Runnable> runTask;

  public LavaDeath(Consumer<Runnable> runTask) {
    this.runTask = runTask;
  }

  @EventHandler
  public void onLava(EntityDamageEvent e) {
    if (!(e.getEntity() instanceof Player p)) return;
    if (!PlayerUtil.checkPermGameMode(p, Perm.LAVA_DEATH)) return;
    if (e.getCause().equals(EntityDamageEvent.DamageCause.LAVA)) runTask.accept(() -> p.setNoDamageTicks(0));
  }
}
