package net.demomaker.blockcounter.config;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.demomaker.blockcounter.algorithm.Algorithm;
import net.demomaker.blockcounter.command.config.CommandConfigs;

public class PlayerConfig implements CommandExecutionConfig {
  private GameProfile gameProfile;
  private Algorithm algorithm;
  private CommandConfigs commandConfigs;
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

