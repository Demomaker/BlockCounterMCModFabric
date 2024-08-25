package net.demomaker.blockcounter.command.implementation;

import static net.demomaker.blockcounter.adapter.book.BookWriter.detectPlayersWithBookAndQuillAndWrite;

import net.demomaker.blockcounter.adapter.entity.CommandBlockBlockEntity;
import net.demomaker.blockcounter.adapter.entity.CommandBlockMinecartEntity;
import net.demomaker.blockcounter.adapter.servercommand.ServerCommand;
import net.demomaker.blockcounter.adapter.servercommand.ServerCommandContext;
import net.demomaker.blockcounter.adapter.entity.ServerPlayerEntity;
import net.demomaker.blockcounter.adapter.world.ServerWorld;
import net.demomaker.blockcounter.adapter.math.Vec3d;
import net.demomaker.blockcounter.blockentity.BlockEntries;
import net.demomaker.blockcounter.command.config.CommandConfig;
import net.demomaker.blockcounter.command.config.SourceType;
import net.demomaker.blockcounter.identity.CommandBlockConfig;
import net.demomaker.blockcounter.identity.CommandExecutionConfig;
import net.demomaker.blockcounter.identity.CommandExecutionConfigResolver;
import net.demomaker.blockcounter.identity.PlayerConfig;
import net.demomaker.blockcounter.entity.EntityResolver;
import net.demomaker.blockcounter.adapter.book.BookWriter;
import net.demomaker.blockcounter.util.ResultMessageCreator;

public class BasicCommand extends ServerCommand {
  public static final String FIRST_POSITION_ARGUMENT_NAME = "first_position";
  public static final String SECOND_POSITION_ARGUMENT_NAME = "second_position";
  public static final String BLOCK_ARGUMENT_NAME = "block_name";

  protected Vec3d getPositionOfEntityFromContext(ServerCommandContext context) {
    ServerPlayerEntity playerEntity = EntityResolver.getPlayerFromContext(context);
    if(!playerEntity.isNull()) {
      return playerEntity.getPos();
    }
    CommandBlockBlockEntity commandBlockBlockEntity = new CommandBlockBlockEntity(EntityResolver.getCommandBlockFromContext(context));
    if(!commandBlockBlockEntity.isNull()) {
      return commandBlockBlockEntity.getPos().toCenterPos();
    }
    CommandBlockMinecartEntity commandBlockMinecartEntity = new CommandBlockMinecartEntity(EntityResolver.getCommandBlockMinecartFromContext(context));
    if(!commandBlockMinecartEntity.isNull()) {
      return commandBlockMinecartEntity.getPos();
    }

    return Vec3d.ZERO;
  }

  protected ServerWorld getServerWorldFromContext(ServerCommandContext context) {
    return new ServerWorld(context.getSource().getWorld());
  }

  public int countBlocks(ServerCommandContext context, CommandConfig commandConfig) {
    try {
      CommandExecutionConfig currentCommandExecutionConfig = CommandExecutionConfigResolver.getConfigFromContext(context);
      BlockEntries result = currentCommandExecutionConfig.getAlgorithm().execute(commandConfig);
      String fullPageMessage = ResultMessageCreator.createMessage(result);
      String oneLinerMessage = ResultMessageCreator.createOneLiner(result);
      boolean wroteInABook = false;
      if(currentCommandExecutionConfig instanceof PlayerConfig) {
        wroteInABook = BookWriter.Write(commandConfig.writableBook, fullPageMessage);
      }
      if(currentCommandExecutionConfig instanceof CommandBlockConfig) {
        detectPlayersWithBookAndQuillAndWrite(context, fullPageMessage);
      }

      if(currentCommandExecutionConfig.getCommandConfigs().getSourceType() == SourceType.COMMAND_BLOCK) {
        context.sendFeedback(oneLinerMessage, false);
      }
      else {
        if(!wroteInABook) {
          context.sendFeedback(fullPageMessage, false);
        }
      }
    }
    catch(Exception e) {
      context.sendFeedback(e.getMessage(), false);
    }
    return 0;
  }
}
