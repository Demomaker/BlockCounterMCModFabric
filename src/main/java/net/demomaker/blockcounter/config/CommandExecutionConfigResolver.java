package net.demomaker.blockcounter.config;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.context.CommandContext;
import net.demomaker.blockcounter.algorithm.Algorithm;
import net.demomaker.blockcounter.command.config.CommandConfigs;
import net.demomaker.blockcounter.command.config.SetPositionCommandConfig;
import net.demomaker.blockcounter.entity.EntityResolver;
import net.demomaker.blockcounter.util.ModObjects;
import net.minecraft.block.entity.CommandBlockBlockEntity;
import net.minecraft.entity.vehicle.CommandBlockMinecartEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class CommandExecutionConfigResolver {
  public static CommandBlockConfig getCommandBlockConfigFromContext(CommandContext<ServerCommandSource> context)
      throws Exception {
    CommandBlockBlockEntity commandBlockBlockEntity = EntityResolver.getCommandBlockFromContext(context);
    CommandBlockMinecartEntity commandBlockMinecartEntity = EntityResolver.getCommandBlockMinecartFromContext(context);

    if(commandBlockBlockEntity == null && commandBlockMinecartEntity == null) {
      throw new Exception("command was not executed by a command block");
    }

    CommandBlockConfig tempCommandBlockConfig = new CommandBlockConfig(context.getSource().getName(), null, null);
    CommandBlockConfig result = (CommandBlockConfig) ModObjects.commandExecutionConfigs.getConfig(tempCommandBlockConfig);

    if(result != null) {
      return result;
    }

    CommandBlockConfig newCommandBlockConfig = new CommandBlockConfig(context.getSource().getName(), new Algorithm(), new CommandConfigs(new SetPositionCommandConfig(null, null, null, null, context.getSource().getWorld())));
    ModObjects.commandExecutionConfigs.addConfig(newCommandBlockConfig);
    return (CommandBlockConfig) ModObjects.commandExecutionConfigs.getConfig(newCommandBlockConfig);
  }

  public static PlayerConfig getPlayerConfigFromContext(CommandContext<ServerCommandSource> context)
      throws Exception {
    ServerPlayerEntity player = EntityResolver.getPlayerFromContext(context);
    if(player == null) {
      throw new Exception("command was not executed by a player");
    }
    GameProfile gameProfile = player.getGameProfile();
    PlayerConfig tempPlayerConfig = new PlayerConfig(gameProfile, null, null);
    return (PlayerConfig) ModObjects.commandExecutionConfigs.getConfig(tempPlayerConfig);
  }

  public static CommandExecutionConfig getConfigFromContext(CommandContext<ServerCommandSource> context) throws Exception {
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
