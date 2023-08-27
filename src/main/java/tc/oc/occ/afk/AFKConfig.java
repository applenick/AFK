package tc.oc.occ.afk;

import org.bukkit.configuration.Configuration;

public class AFKConfig {

  private String timeout;
  private String staffPerm;

  public AFKConfig(Configuration config) {
    reload(config);
  }

  public String getDuration() {
    return timeout;
  }

  public String getStaffPermission() {
    return staffPerm;
  }

  public void reload(Configuration config) {
    this.timeout = config.getString("timeout");
    this.staffPerm = config.getString("staff");
  }
}
