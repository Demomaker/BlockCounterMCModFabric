package net.demomaker.blockcounter.command.config;

public class CommandConfigs {

  private SetPositionCommandConfig setPositionCommandConfig;

  public CommandConfigs(SetPositionCommandConfig setPositionCommandConfig) {
    this.setPositionCommandConfig = setPositionCommandConfig;
  }

  public SetPositionCommandConfig getSetPositionCommandConfig() {
    return this.setPositionCommandConfig;
  }

  public void setSetPositionCommandConfig(SetPositionCommandConfig other) {
    this.setPositionCommandConfig = other;
  }
}
