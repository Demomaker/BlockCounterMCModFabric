package net.demomaker.blockcounter.player;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.demomaker.blockcounter.algorithm.Algorithm;
import net.demomaker.blockcounter.blockentity.BlockEntries;
import net.demomaker.blockcounter.command.config.CommandConfig;
import net.demomaker.blockcounter.command.config.CommandConfigs;

public class PlayerConfig {
  private GameProfile gameProfile;
  private Algorithm algorithm;
  private CommandConfigs commandConfigs;
  public PlayerConfig(GameProfile gameProfile, Algorithm algorithm, CommandConfigs commandConfigs) {
    this.gameProfile = gameProfile;
    this.algorithm = algorithm;
    this.commandConfigs = commandConfigs;
  }

  public String getId() {
    UUID id = gameProfile.getId();
    if(id != null) {
      return id.toString();
    }
    return gameProfile.getName();
  }

  public BlockEntries executeAlgorithm(CommandConfig commandConfig) throws Exception {
    algorithm.setServerWorld(commandConfig.serverWorld);
    return algorithm.GetBlockEntriesByCount(commandConfig.firstPosition, commandConfig.secondPosition, commandConfig.itemFilter);
  }

  public void stopAlgorithm() {
    algorithm.stop();
  }

  public CommandConfigs getCommandConfigs() {
    return commandConfigs;
  }

  public void setCommandConfigs(CommandConfigs commandConfigs) {
    this.commandConfigs = commandConfigs;
  }
}

