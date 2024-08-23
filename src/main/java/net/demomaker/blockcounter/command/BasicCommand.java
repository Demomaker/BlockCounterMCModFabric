package net.demomaker.blockcounter.command;

import static net.demomaker.blockcounter.util.BookWriter.detectPlayersWithBookAndQuillAndWrite;

import net.demomaker.blockcounter.facade.ServerCommand;
import net.demomaker.blockcounter.facade.ServerCommandContext;
import net.demomaker.blockcounter.command.config.CommandConfig;
import net.demomaker.blockcounter.config.CommandBlockConfig;
import net.demomaker.blockcounter.config.CommandExecutionConfig;
import net.demomaker.blockcounter.config.CommandExecutionConfigResolver;
import net.demomaker.blockcounter.config.PlayerConfig;
import net.demomaker.blockcounter.entity.EntityResolver;
import net.demomaker.blockcounter.util.BookWriter;
import net.demomaker.blockcounter.util.FeedbackSender;
import net.demomaker.blockcounter.util.ResultMessageCreator;
import net.demomaker.blockcounter.facade.CommandBlockBlockEntity;
import net.demomaker.blockcounter.facade.CommandBlockMinecartEntity;
import net.demomaker.blockcounter.facade.ServerCommandSource;
import net.demomaker.blockcounter.facade.ServerPlayerEntity;
import net.demomaker.blockcounter.facade.ServerWorld;
import net.demomaker.blockcounter.facade.Vec3d;

public class BasicCommand extends ServerCommand<ServerCommandSource> {
  public static final String FIRST_POSITION_ARGUMENT_NAME = "first_position";
  public static final String SECOND_POSITION_ARGUMENT_NAME = "second_position";
  public static final String BLOCK_ARGUMENT_NAME = "block_name";

  public BasicCommand(ServerCommand<ServerCommandSource> command) {
    super(command);
  }

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
  public int run(ServerCommandContext commandContext) {
    return 0;
  }
}
