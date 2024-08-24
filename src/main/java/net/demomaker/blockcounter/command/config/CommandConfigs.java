package net.demomaker.blockcounter.command.config;

public class CommandConfigs {

  private final SetPositionCommandConfig setPositionCommandConfig;
  private SourceType sourceType = SourceType.PLAYER;

  public CommandConfigs(SetPositionCommandConfig setPositionCommandConfig) {
    this.setPositionCommandConfig = setPositionCommandConfig;
  }

  public SetPositionCommandConfig getSetPositionCommandConfig() {
    return this.setPositionCommandConfig;
  }

  public SourceType getSourceType() {
    return sourceType;
  }

  public void setSourceType(SourceType value) {
    this.sourceType = value;
  }
}
