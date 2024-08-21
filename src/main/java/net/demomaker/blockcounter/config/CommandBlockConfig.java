package net.demomaker.blockcounter.config;

import net.demomaker.blockcounter.algorithm.Algorithm;
import net.demomaker.blockcounter.command.config.CommandConfigs;

public class CommandBlockConfig implements CommandExecutionConfig {
  private String name;
  private Algorithm algorithm;
  private CommandConfigs commandConfigs;
  public CommandBlockConfig(String name, Algorithm algorithm, CommandConfigs commandConfigs) {
    this.name = name;
    this.algorithm = algorithm;
    this.commandConfigs = commandConfigs;
  }

  public String getIdentifier() {
    return this.name;
  }

  public Algorithm getAlgorithm() {
    return this.algorithm;
  }

  public CommandConfigs getCommandConfigs() {
    return this.commandConfigs;
  }

  public CommandExecutionConfig setCommandConfigs(CommandConfigs newCommandConfigs) {
    return new CommandBlockConfig(this.name, this.algorithm, newCommandConfigs);
  }
}
