package net.demomaker.blockcounter.identity;

import net.demomaker.blockcounter.adapter.player.GameProfile;
import net.demomaker.blockcounter.algorithm.Algorithm;
import net.demomaker.blockcounter.command.config.CommandConfigs;
import net.demomaker.blockcounter.command.config.SetPositionCommandConfig;
import net.demomaker.blockcounter.command.config.SourceType;
import net.demomaker.blockcounter.entity.EntityResolver;
import net.demomaker.blockcounter.adapter.entity.CommandBlockBlockEntity;
import net.demomaker.blockcounter.adapter.entity.CommandBlockMinecartEntity;
import net.demomaker.blockcounter.adapter.servercommand.ServerCommandContext;
import net.demomaker.blockcounter.adapter.entity.ServerPlayerEntity;
import net.demomaker.blockcounter.adapter.world.ServerWorld;
import net.demomaker.blockcounter.util.ModObjects;

public class CommandExecutionConfigResolver {
  public static CommandBlockConfig getCommandBlockConfigFromContext(ServerCommandContext context)
      throws Exception {
    CommandBlockBlockEntity commandBlockBlockEntity = EntityResolver.getCommandBlockFromContext(context);
    CommandBlockMinecartEntity commandBlockMinecartEntity = EntityResolver.getCommandBlockMinecartFromContext(context);

    if(commandBlockBlockEntity.isNull() && commandBlockMinecartEntity.isNull()) {
      throw new Exception("command was not executed by a command block");
    }

    CommandBlockConfig tempCommandBlockConfig = new CommandBlockConfig(context.getSource().getName(), null, null);
    CommandBlockConfig result = (CommandBlockConfig) ModObjects.commandExecutionConfigs.getConfig(tempCommandBlockConfig);

    if(result != null) {
      return result;
    }

    SetPositionCommandConfig setPositionCommandConfig = new SetPositionCommandConfig(null, null, null, null, new ServerWorld(context.getSource().getWorld()));
    CommandConfigs commandConfigs = new CommandConfigs(setPositionCommandConfig);
    commandConfigs.setSourceType(SourceType.COMMAND_BLOCK);
    CommandBlockConfig newCommandBlockConfig = new CommandBlockConfig(context.getSource().getName(), new Algorithm(), commandConfigs);
    ModObjects.commandExecutionConfigs.addConfig(newCommandBlockConfig);
    return (CommandBlockConfig) ModObjects.commandExecutionConfigs.getConfig(newCommandBlockConfig);
  }

  public static PlayerConfig getPlayerConfigFromContext(ServerCommandContext context)
      throws Exception {
    ServerPlayerEntity player = EntityResolver.getPlayerFromContext(context);
    if(player.isNull()) {
      throw new Exception("command was not executed by a player");
    }
    GameProfile gameProfile = new GameProfile(player.getGameProfile());
    PlayerConfig tempPlayerConfig = new PlayerConfig(gameProfile, null, null);
    return (PlayerConfig) ModObjects.commandExecutionConfigs.getConfig(tempPlayerConfig);
  }

  public static CommandExecutionConfig getConfigFromContext(ServerCommandContext context) throws Exception {
    CommandExecutionConfig commandExecutionConfig = null;

    try {
      commandExecutionConfig = getPlayerConfigFromContext(context);
    }
    catch(Exception ignored) { }

    if(commandExecutionConfig != null) {
      return commandExecutionConfig;
    }

    try {
      commandExecutionConfig = getCommandBlockConfigFromContext(context);
    }
    catch(Exception ignored) { }

    if(commandExecutionConfig != null) {
      return commandExecutionConfig;
    }

    throw new Exception("command was not executed by a player nor a command block");
  }


}
