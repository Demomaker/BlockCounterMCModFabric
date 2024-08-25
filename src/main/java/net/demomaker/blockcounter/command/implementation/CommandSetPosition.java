package net.demomaker.blockcounter.command.implementation;

import net.demomaker.blockcounter.adapter.block.BlockPos;
import net.demomaker.blockcounter.adapter.item.Item;
import net.demomaker.blockcounter.adapter.item.ItemStack;
import net.demomaker.blockcounter.adapter.servercommand.ServerCommand;
import net.demomaker.blockcounter.adapter.servercommand.ServerCommandContext;
import net.demomaker.blockcounter.adapter.servercommand.ServerCommandRegistryAccess;
import net.demomaker.blockcounter.adapter.math.Vec3d;
import net.demomaker.blockcounter.adapter.argumentbuilder.ServerCommandArgumentBuilder;
import net.demomaker.blockcounter.command.config.CommandConfigs;
import net.demomaker.blockcounter.command.config.SetPositionCommandConfig;
import net.demomaker.blockcounter.identity.CommandExecutionConfig;
import net.demomaker.blockcounter.identity.CommandExecutionConfigResolver;
import net.demomaker.blockcounter.entity.EntityResolver;
import net.demomaker.blockcounter.util.ModObjects;
import net.demomaker.blockcounter.adapter.math.MathHelper;
import net.demomaker.blockcounter.util.TranslationText;

public class CommandSetPosition extends BasicCommand {
  public static final String COMMAND_NAME = "setposition";
  public static ServerCommandArgumentBuilder getDefaultServerCommandFormat(ServerCommandRegistryAccess registryAccess, ServerCommand CountBlocksCommand, ServerCommand CountBlocksWithoutItemArgumentCommand) {
    return new ServerCommandArgumentBuilder()
        .beginCommand(CommandSetPosition.COMMAND_NAME, CountBlocksWithoutItemArgumentCommand)
        .addItemStackArgument(CommandSetPosition.BLOCK_ARGUMENT_NAME, CountBlocksCommand, registryAccess)
        .endCommand();
  }

  public static ServerCommandArgumentBuilder getTranslatedServerCommandFormat(
      ServerCommandRegistryAccess registryAccess,
      CommandSetPosition CountBlocksCommand,
      CommandSetPositionWithoutItemArgument CountBlocksWithoutItemArgumentCommand) {
    TranslationText.TRANSLATED_TO_DEFAULT_MAP.put(TranslationText.commandSetPosition.getString(), COMMAND_NAME);

    return new ServerCommandArgumentBuilder()
        .beginCommand(TranslationText.commandSetPosition.getString(), CountBlocksWithoutItemArgumentCommand)
        .addItemStackArgument(TranslationText.commandArgumentBlockName.getString(), CountBlocksCommand, registryAccess)
        .endCommand();
  }

  @Override
  public int run(ServerCommandContext context) {
    Item item = new Item(null);
    try {
      item = new Item(ItemStack.getArgument(context, BLOCK_ARGUMENT_NAME).getItem());
    } catch (Exception ignored) {}

    try {
      if (item.isNull()) {
        item = new Item(ItemStack.getArgument(context, TranslationText.commandArgumentBlockName.getString()).getItem());
      }
    } catch (Exception ignored) {}
    return countBlocks(context, item, null);
  }

  public int countBlocks(ServerCommandContext context, Item item, BlockPos blockPos) {
    try {
      CommandExecutionConfig currentCommandExecutionConfig = CommandExecutionConfigResolver.getConfigFromContext(context);
      SetPositionCommandConfig setPositionCommandConfig = currentCommandExecutionConfig.getCommandConfigs()
          .getSetPositionCommandConfig();
      setPositionCommandConfig.serverWorld = getServerWorldFromContext(context);
      setPositionCommandConfig.itemFilter = item;

      BlockPos firstPosition = currentCommandExecutionConfig.getCommandConfigs().getSetPositionCommandConfig().firstPosition;
      BlockPos secondPosition = currentCommandExecutionConfig.getCommandConfigs().getSetPositionCommandConfig().secondPosition;
      ItemStack bookAndQuil = EntityResolver.getBookAndQuillFromContext(context);

      BlockPos additionalPosition = blockPos;
      if(additionalPosition == null) {
        Vec3d entityPosition = getPositionOfEntityFromContext(context);
        int x = MathHelper.floor(entityPosition.getX());
        int y = MathHelper.floor(entityPosition.getY());
        int z = MathHelper.floor(entityPosition.getZ());
        additionalPosition = new BlockPos(x, y, z);
      }


      if (firstPosition == null) {
        firstPosition = additionalPosition;
        setFirstPosition(currentCommandExecutionConfig, firstPosition, bookAndQuil);
      }
      else if (secondPosition == null) {
        secondPosition = additionalPosition;
        setSecondPosition(currentCommandExecutionConfig, secondPosition, bookAndQuil);
      }

      String positionWord = (secondPosition == null) ? TranslationText.commandArgumentFirstPosition.getString() : TranslationText.commandArgumentSecondPosition.getString();
      String chatMessage =
          positionWord
          + " : "
          + "("
          + "x: " + additionalPosition.getX()
          + ", y: " + additionalPosition.getY()
          + ", z: " + additionalPosition.getZ()
          + ")";
      context.sendFeedback(chatMessage, false);

      if(secondPosition != null) {
        int result = super.countBlocks(context, currentCommandExecutionConfig.getCommandConfigs()
            .getSetPositionCommandConfig());
        setFirstPosition(currentCommandExecutionConfig, null, null);
        setSecondPosition(currentCommandExecutionConfig, null, null);
        return result;
      }
    }
    catch(Exception e) {
      context.sendFeedback(e.getMessage(), false);
      return e.hashCode();
    }
    return 0;
  }

  protected void setFirstPosition(CommandExecutionConfig commandExecutionConfig, BlockPos position, ItemStack bookAndQuil) {
    SetPositionCommandConfig currentSetPositionCommandConfig = commandExecutionConfig.getCommandConfigs().getSetPositionCommandConfig();
    currentSetPositionCommandConfig.firstPosition = position;
    currentSetPositionCommandConfig.writableBook = bookAndQuil;
    CommandConfigs newCommandConfigs = new CommandConfigs(currentSetPositionCommandConfig);
    newCommandConfigs.setSourceType(commandExecutionConfig.getCommandConfigs().getSourceType());
    ModObjects.commandExecutionConfigs.setConfig(commandExecutionConfig.setCommandConfigs(newCommandConfigs));
  }

  protected void setSecondPosition(CommandExecutionConfig commandExecutionConfig, BlockPos position, ItemStack bookAndQuil) {
    SetPositionCommandConfig currentSetPositionCommandConfig = commandExecutionConfig.getCommandConfigs().getSetPositionCommandConfig();
    currentSetPositionCommandConfig.secondPosition = position;
    currentSetPositionCommandConfig.writableBook = bookAndQuil;
    CommandConfigs newCommandConfigs = new CommandConfigs(currentSetPositionCommandConfig);
    newCommandConfigs.setSourceType(commandExecutionConfig.getCommandConfigs().getSourceType());
    ModObjects.commandExecutionConfigs.setConfig(commandExecutionConfig.setCommandConfigs(newCommandConfigs));
  }
}
