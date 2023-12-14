package net.demomaker.blockcounter.player;

import java.util.HashMap;
import java.util.Map;

public class PlayerConfigs {
  private final Map<String, PlayerConfig> playerConfigs = new HashMap<>();

  public PlayerConfig getPlayerConfig(PlayerConfig other) {
    return playerConfigs.get(other.getId());
  }

  public boolean setPlayerConfig(PlayerConfig toSet) {
    return playerConfigs.replace(toSet.getId(), toSet) != null;
  }

  public boolean addPlayerConfig(PlayerConfig toAdd) {
    return playerConfigs.put(toAdd.getId(), toAdd) != null;
  }

  public boolean removePlayerConfig(PlayerConfig toRemove) {
    return playerConfigs.remove(toRemove.getId()) != null;
  }
}
