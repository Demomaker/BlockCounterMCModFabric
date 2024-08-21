package net.demomaker.blockcounter.config;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutionConfigs {
  private static final Map<String, CommandExecutionConfig> commandExecutionConfigs = new HashMap<>();

  public CommandExecutionConfig getConfig(CommandExecutionConfig other) {
    return commandExecutionConfigs.get(other.getIdentifier());
  }

  public boolean setConfig(CommandExecutionConfig toSet) {
    return commandExecutionConfigs.replace(toSet.getIdentifier(), toSet) != null;
  }

  public boolean addConfig(CommandExecutionConfig toAdd) {
    return commandExecutionConfigs.put(toAdd.getIdentifier(), toAdd) != null;
  }

  public boolean removeConfig(CommandExecutionConfig toRemove) {
    return commandExecutionConfigs.remove(toRemove.getIdentifier()) != null;
  }
}
