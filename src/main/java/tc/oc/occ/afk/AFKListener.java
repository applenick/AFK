package tc.oc.occ.afk;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class AFKListener implements Listener {

  private final AFKManager manager;

  public AFKListener(AFKManager manager) {
    this.manager = manager;
  }

  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onMove(PlayerMoveEvent event) {
    boolean wasPitchYawMoved =
        event.getFrom().getYaw() != event.getTo().getYaw()
            || event.getFrom().getPitch() != event.getTo().getPitch();

    if (!wasPitchYawMoved) return;
    manager.logActivity(event.getPlayer());
  }

  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onChatEvent(AsyncPlayerChatEvent event) {
    manager.logActivity(event.getPlayer());
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onCommand(PlayerCommandPreprocessEvent event) {
    manager.logActivity(event.getPlayer());
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onJoin(PlayerJoinEvent event) {
    manager.logActivity(event.getPlayer());
  }
}
