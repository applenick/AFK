package tc.oc.occ.afk;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Dependency;
import co.aikar.commands.annotation.Description;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AFKCommand extends BaseCommand {

  @Dependency private AFKManager manager;

  @CommandAlias("afk")
  @Description("View AFK status")
  @CommandPermission("afk.status")
  public void viewAFK(
      CommandSender sender, @Default("true") boolean staffOnly, @Default("false") boolean viewAll) {
    sender.sendMessage(
        color(
            "&6&m---------------&r &7["
                + (viewAll ? "&2&lLAST MOVEMENTS" : "&c&lAFK USERS")
                + "&7] &6&m---------------"));

    List<Player> afk =
        Bukkit.getOnlinePlayers().stream()
            .filter(p -> staffOnly ? p.hasPermission(manager.getStaffPermission()) : true)
            .filter(p -> viewAll ? true : manager.isAFK(p))
            .collect(Collectors.toList());

    if (afk.isEmpty()) {
      sender.sendMessage(ChatColor.RED + "There are no afk players!");
      return;
    }

    for (Player player : afk) {
      sender.sendMessage(
          player.getDisplayName()
              + " - "
              + formatDuration(manager.getTimeSinceLastMovement(player), ChatColor.GREEN));
    }
  }

  private String color(String message) {
    return ChatColor.translateAlternateColorCodes('&', message);
  }

  private String formatDuration(Duration duration, ChatColor formatColor) {
    StringBuilder sb = new StringBuilder();

    long seconds = duration.getSeconds();

    int day = (int) TimeUnit.SECONDS.toDays(seconds);
    long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
    long minutes = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
    long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);

    if (day > 0) {
      sb.append(formatColor.toString() + day + " ");
      sb.append(ChatColor.GRAY + (day != 1 ? "days " : "day "));
    }

    if (hours > 0) {
      sb.append(formatColor.toString() + hours + " ");
      sb.append(ChatColor.GRAY + (hours != 1 ? "hours " : "hour "));
    }

    if (minutes > 0) {
      sb.append(formatColor.toString() + minutes + " ");
      sb.append(ChatColor.GRAY + (minutes != 1 ? "minutes " : "minute "));
    }

    if (second > 0) {
      sb.append(formatColor.toString() + second + " ");
      sb.append(ChatColor.GRAY + (second != 1 ? "seconds " : "second "));
    }

    if (seconds == 0) {
      sb.append(color("&c0 seconds"));
    }

    return sb.toString();
  }
}
