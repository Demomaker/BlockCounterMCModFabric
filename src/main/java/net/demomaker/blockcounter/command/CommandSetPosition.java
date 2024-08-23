package net.demomaker.blockcounter.command;

import static net.minecraft.server.command.CommandManager.argument;

import com.mojang.brigadier.CommandDispatcher;
import net.demomaker.blockcounter.command.config.CommandConfigs;
import net.demomaker.blockcounter.command.config.SetPositionCommandConfig;
import net.demomaker.blockcounter.config.CommandExecutionConfig;
import net.demomaker.blockcounter.config.CommandExecutionConfigResolver;
import net.demomaker.blockcounter.entity.EntityResolver;
import net.demomaker.blockcounter.facade.ServerCommand;
import net.demomaker.blockcounter.facade.ServerCommandContext;
import net.demomaker.blockcounter.main.BlockCounter;
import net.demomaker.blockcounter.util.FeedbackSender;
import net.demomaker.blockcounter.util.ModObjects;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.demomaker.blockcounter.facade.Item;
import net.demomaker.blockcounter.facade.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.demomaker.blockcounter.facade.ServerCommandSource;
import net.demomaker.blockcounter.facade.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.demomaker.blockcounter.facade.Vec3d;

public class CommandSetPosition extends BasicCommand {
  public static final String COMMAND_NAME = "setposition";
  public static com.mojang.brigadier.builder.LiteralArgumentBuilder<net.minecraft.server.command.ServerCommandSource> getServerCommandFormat(
      CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, ServerCommand CountBlocksCommand, ServerCommand CountBlocksWithoutItemArgumentCommand) {
    return CommandManager.literal(BlockCounter.MOD_ID).then(
        dispatcher.register(CommandManager.literal(CommandSetPosition.COMMAND_NAME)
            .requires(cs -> cs.hasPermissionLevel(0))
            .executes(CountBlocksWithoutItemArgumentCommand)
            .then(argument(BLOCK_ARGUMENT_NAME, ItemStackArgumentType.itemStack(registryAccess))
            .requires(cs -> cs.hasPermissionLevel(0))
            .executes(CountBlocksCommand)
        ))
    );
  }

    @Override
  public int run(ServerCommandContext context) {
    ItemStackArgument itemArgument = ItemStackArgumentType.getItemStackArgument(context, BLOCK_ARGUMENT_NAME);
    Item item = new Item(itemArgument.getItem());
    return countBlocks(context, item);
  }

  public int countBlocks(ServerCommandContext context, Item item) {
    try {
      CommandExecutionConfig currentCommandExecutionConfig = CommandExecutionConfigResolver.getConfigFromContext(context);
      SetPositionCommandConfig setPositionCommandConfig = currentCommandExecutionConfig.getCommandConfigs()
          .getSetPositionCommandConfig();
      setPositionCommandConfig.serverWorld = getServerWorldFromContext(context);
      setPositionCommandConfig.itemFilter = item;

      BlockPos firstPosition = currentCommandExecutionConfig.getCommandConfigs().getSetPositionCommandConfig().firstPosition;
      BlockPos secondPosition = currentCommandExecutionConfig.getCommandConfigs().getSetPositionCommandConfig().secondPosition;
      ItemStack bookAndQuil = EntityResolver.getBookAndQuillFromContext(context);

      Vec3d entityPosition = getPositionOfEntityFromContext(context);
      int x = MathHelper.floor(entityPosition.getX());
      int y = MathHelper.floor(entityPosition.getY());
      int z = MathHelper.floor(entityPosition.getZ());

      if (firstPosition == null) {
        firstPosition = new BlockPos(x, y, z);
        setFirstPosition(currentCommandExecutionConfig, firstPosition, bookAndQuil);
      }
      else if (secondPosition == null) {
        secondPosition = new BlockPos(x, y, z);
        setSecondPosition(currentCommandExecutionConfig, secondPosition, bookAndQuil);
      }

      String positionWord = (secondPosition == null) ? "first" : "second";
      String chatMessage = "Set "
          + positionWord
          + " position at : "
          + "("
          + "x: " + x
          + ", y: " + y
          + ", z: " + z
          + ")";
      FeedbackSender.send(context, chatMessage);

      if(secondPosition != null) {
        int result = super.countBlocks(context, currentCommandExecutionConfig.getCommandConfigs()
            .getSetPositionCommandConfig());
        setFirstPosition(currentCommandExecutionConfig, null, null);
        setSecondPosition(currentCommandExecutionConfig, null, null);;
        return result;
      }
    }
    catch(Exception e) {
      FeedbackSender.send(context, e.getMessage());
      return e.hashCode();
    }
    return 0;
  }

  protected void setFirstPosition(CommandExecutionConfig commandExecutionConfig, BlockPos position, ItemStack bookAndQuil) {
    SetPositionCommandConfig currentSetPositionCommandConfig = commandExecutionConfig.getCommandConfigs().getSetPositionCommandConfig();
    currentSetPositionCommandConfig.firstPosition = position;
    currentSetPositionCommandConfig.writableBook = bookAndQuil;
    ModObjects.commandExecutionConfigs.setConfig(commandExecutionConfig.setCommandConfigs(new CommandConfigs(currentSetPositionCommandConfig)));
  }

  protected void setSecondPosition(CommandExecutionConfig commandExecutionConfig, BlockPos position, ItemStack bookAndQuil) {
    SetPositionCommandConfig currentSetPositionCommandConfig = commandExecutionConfig.getCommandConfigs().getSetPositionCommandConfig();
    currentSetPositionCommandConfig.secondPosition = position;
    currentSetPositionCommandConfig.writableBook = bookAndQuil;
    ModObjects.commandExecutionConfigs.setConfig(commandExecutionConfig.setCommandConfigs(new CommandConfigs(currentSetPositionCommandConfig)));
  }
}
