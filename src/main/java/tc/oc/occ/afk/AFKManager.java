package tc.oc.occ.afk;

import com.google.common.collect.Maps;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class AFKManager implements Listener {

  private Map<UUID, Instant> lastActive;
  private final AFKConfig config;

  public AFKManager(AFKConfig config) {
    this.config = config;
    this.lastActive = Maps.newHashMap();
  }

  public Duration getTimeout() {
    Duration timeout = Duration.ofMinutes(10); // Default to 10 mins
    try {
      timeout = Duration.parse(config.getDuration());
    } catch (DateTimeParseException e) {
      // Ignore error
    }
    return timeout;
  }

  public String getStaffPermission() {
    return config.getStaffPermission();
  }

  public Duration getTimeSinceLastMovement(Player player) {
    if (player == null) return Duration.ZERO;
    if (lastActive.get(player.getUniqueId()) == null) return Duration.ZERO;
    Instant lastActivity = lastActive.get(player.getUniqueId());
    return Duration.between(lastActivity, Instant.now());
  }

  public boolean isAFK(Player player) {
    Instant lastActivity = lastActive.get(player.getUniqueId());
    if (lastActivity == null) return false;
    Duration timeSince = Duration.between(lastActivity, Instant.now());
    return getTimeout().minus(timeSince).isNegative();
  }

  public void logActivity(Player player) {
    this.lastActive.put(player.getUniqueId(), Instant.now());
  }
}
