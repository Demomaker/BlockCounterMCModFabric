package net.demomaker.blockcounter.identity;

import java.util.UUID;
import net.demomaker.blockcounter.adapter.player.GameProfile;
import net.demomaker.blockcounter.algorithm.Algorithm;
import net.demomaker.blockcounter.command.config.CommandConfigs;

public class PlayerConfig implements CommandExecutionConfig {
  private final GameProfile gameProfile;
  private final Algorithm algorithm;
  private final CommandConfigs commandConfigs;
  public PlayerConfig(GameProfile gameProfile, Algorithm algorithm, CommandConfigs commandConfigs) {
    this.gameProfile = gameProfile;
    this.algorithm = algorithm;
    this.commandConfigs = commandConfigs;
  }

  public String getIdentifier() {
    UUID id = gameProfile.getId();
    if(id != null) {
      return id.toString();
    }
    return gameProfile.getName();
  }

  public Algorithm getAlgorithm() { return algorithm; }

  public CommandConfigs getCommandConfigs() {
    return commandConfigs;
  }

  public CommandExecutionConfig setCommandConfigs(CommandConfigs newCommandConfigs) {
    return new PlayerConfig(gameProfile, algorithm, newCommandConfigs);
  }
}

