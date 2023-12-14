package net.demomaker.blockcounter.command;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.demomaker.blockcounter.command.config.CommandConfig;
import net.demomaker.blockcounter.player.PlayerConfig;
import net.demomaker.blockcounter.util.ModObjects;
import net.demomaker.blockcounter.util.ResultMessageCreator;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

public class BasicCommand implements Command<ServerCommandSource> {
  public static final String FIRST_POSITION_ARGUMENT_NAME = "first_position";
  public static final String SECOND_POSITION_ARGUMENT_NAME = "second_position";
  public static final String BLOCK_ARGUMENT_NAME = "block_name";
  protected ServerPlayerEntity getPlayerFromContext(CommandContext<ServerCommandSource> context) {
    return context.getSource().getPlayer();
  }
  protected PlayerConfig getPlayerConfigFromContext(CommandContext<ServerCommandSource> context)
      throws Exception {
    ServerPlayerEntity player = getPlayerFromContext(context);
    if(player == null) {
      throw new Exception("not a player");
    }
    GameProfile gameProfile = player.getGameProfile();
    PlayerConfig tempPlayerConfig = new PlayerConfig(gameProfile, null, null);
    return ModObjects.playerConfigs.getPlayerConfig(tempPlayerConfig);
  }

  protected ServerWorld getServerWorldFromContext(CommandContext<ServerCommandSource> context) {
    return context.getSource().getWorld();
  }

  public int countBlocks(CommandContext<ServerCommandSource> context, CommandConfig commandConfig) {
    try {
      PlayerConfig currentPlayerConfig = getPlayerConfigFromContext(context);
      String chatMessage = ResultMessageCreator.createMessage(currentPlayerConfig.executeAlgorithm(commandConfig));
      context.getSource().sendFeedback(() -> Text.of(chatMessage), false);
    }
    catch(Exception e) {
      context.getSource().sendFeedback(() -> Text.of(e.getMessage()), false);
    }
    return 0;
  }

  @Override
  public int run(CommandContext<ServerCommandSource> commandContext) {
    return 0;
  }
}
