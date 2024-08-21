package net.demomaker.blockcounter.command;

import static net.demomaker.blockcounter.util.BookWriter.detectPlayersWithBookAndQuillAndWrite;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.demomaker.blockcounter.command.config.CommandConfig;
import net.demomaker.blockcounter.config.CommandBlockConfig;
import net.demomaker.blockcounter.config.CommandExecutionConfig;
import net.demomaker.blockcounter.config.CommandExecutionConfigResolver;
import net.demomaker.blockcounter.config.PlayerConfig;
import net.demomaker.blockcounter.entity.EntityResolver;
import net.demomaker.blockcounter.util.BookWriter;
import net.demomaker.blockcounter.util.FeedbackSender;
import net.demomaker.blockcounter.util.ResultMessageCreator;
import net.minecraft.block.entity.CommandBlockBlockEntity;
import net.minecraft.entity.vehicle.CommandBlockMinecartEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public class BasicCommand implements Command<ServerCommandSource> {
  public static final String FIRST_POSITION_ARGUMENT_NAME = "first_position";
  public static final String SECOND_POSITION_ARGUMENT_NAME = "second_position";
  public static final String BLOCK_ARGUMENT_NAME = "block_name";

  protected Vec3d getPositionOfEntityFromContext(CommandContext<ServerCommandSource> context) {
    ServerPlayerEntity playerEntity = EntityResolver.getPlayerFromContext(context);
    if(playerEntity != null) {
      return playerEntity.getPos();
    }
    CommandBlockBlockEntity commandBlockBlockEntity = EntityResolver.getCommandBlockFromContext(context);
    if(commandBlockBlockEntity != null) {
      return commandBlockBlockEntity.getPos().toCenterPos();
    }
    CommandBlockMinecartEntity commandBlockMinecartEntity = EntityResolver.getCommandBlockMinecartFromContext(context);
    if(commandBlockMinecartEntity != null) {
      return commandBlockMinecartEntity.getPos();
    }

    return Vec3d.ZERO;
  }

  protected ServerWorld getServerWorldFromContext(CommandContext<ServerCommandSource> context) {
    return context.getSource().getWorld();
  }

  public int countBlocks(CommandContext<ServerCommandSource> context, CommandConfig commandConfig) {
    try {
      CommandExecutionConfig currentCommandExecutionConfig = CommandExecutionConfigResolver.getConfigFromContext(context);
      String chatMessage = ResultMessageCreator.createMessage(currentCommandExecutionConfig.getAlgorithm().execute(commandConfig));
      if(currentCommandExecutionConfig instanceof PlayerConfig) {
        BookWriter.Write(commandConfig.writableBook, chatMessage);
      }
      if(currentCommandExecutionConfig instanceof CommandBlockConfig) {
        detectPlayersWithBookAndQuillAndWrite(context, chatMessage);
      }
      FeedbackSender.send(context, chatMessage);
    }
    catch(Exception e) {
      FeedbackSender.send(context, e.getMessage());
    }
    return 0;
  }

  @Override
  public int run(CommandContext<ServerCommandSource> commandContext) {
    return 0;
  }
}
