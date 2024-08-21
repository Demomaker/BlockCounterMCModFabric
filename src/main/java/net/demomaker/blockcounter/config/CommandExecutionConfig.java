package net.demomaker.blockcounter.config;

import net.demomaker.blockcounter.algorithm.Algorithm;
import net.demomaker.blockcounter.command.config.CommandConfigs;

public interface CommandExecutionConfig {
  String getIdentifier();
  Algorithm getAlgorithm();
  CommandConfigs getCommandConfigs();
  CommandExecutionConfig setCommandConfigs(CommandConfigs newCommandConfigs);

}
