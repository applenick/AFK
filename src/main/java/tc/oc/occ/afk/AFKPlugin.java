package tc.oc.occ.afk;

import co.aikar.commands.BukkitCommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AFKPlugin extends JavaPlugin {

  private static AFKPlugin plugin;
  private AFKConfig config;
  private AFKManager manager;
  private BukkitCommandManager commands;

  @Override
  public void onEnable() {
    plugin = this;

    this.saveDefaultConfig();
    this.reloadConfig();

    this.config = new AFKConfig(getConfig());
    this.manager = new AFKManager(config);

    this.getServer().getPluginManager().registerEvents(new AFKListener(manager), this);

    this.commands = new BukkitCommandManager(this);
    this.commands.registerDependency(AFKManager.class, manager);
    this.commands.registerCommand(new AFKCommand());
  }

  public AFKManager getManager() {
    return manager;
  }

  public static AFKPlugin get() {
    return plugin;
  }
}
