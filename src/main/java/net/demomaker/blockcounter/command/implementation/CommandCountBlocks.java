package net.demomaker.blockcounter.command.implementation;

import net.demomaker.blockcounter.adapter.block.BlockPos;
import net.demomaker.blockcounter.adapter.item.Item;
import net.demomaker.blockcounter.adapter.item.ItemStack;
import net.demomaker.blockcounter.adapter.servercommand.ServerCommand;
import net.demomaker.blockcounter.adapter.servercommand.ServerCommandContext;
import net.demomaker.blockcounter.adapter.argumentbuilder.ServerCommandArgumentBuilder;
import net.demomaker.blockcounter.command.config.CommandConfig;
import net.demomaker.blockcounter.entity.EntityResolver;
import net.demomaker.blockcounter.util.TranslationText;

public class CommandCountBlocks extends BasicCommand {
    public static final String COMMAND_NAME = "countblocks";

    public static ServerCommandArgumentBuilder getDefaultServerCommandFormat(ServerCommand CountBlocksCommand, ServerCommand CountBlocksWithoutItemArgumentCommand) {
        return new ServerCommandArgumentBuilder()
            .beginCommand(CommandCountBlocks.COMMAND_NAME, null)
            .addBlockPosArgument(CommandCountBlocks.FIRST_POSITION_ARGUMENT_NAME, null)
            .addBlockPosArgument(CommandCountBlocks.SECOND_POSITION_ARGUMENT_NAME, CountBlocksWithoutItemArgumentCommand)
            .addItemStackArgument(CommandCountBlocks.BLOCK_ARGUMENT_NAME, CountBlocksCommand)
            .endCommand();
    }

    public static ServerCommandArgumentBuilder getTranslatedServerCommandFormat(
        CommandCountBlocks CountBlocksCommand,
        CommandCountBlocksWithoutItemArgument CountBlocksWithoutItemArgumentCommand) {
        TranslationText.TRANSLATED_TO_DEFAULT_MAP.put(TranslationText.commandCountBlocks.getString(), COMMAND_NAME);

        return new ServerCommandArgumentBuilder()
            .beginCommand(TranslationText.commandCountBlocks.getString(), null)
            .addBlockPosArgument(TranslationText.commandArgumentFirstPosition.getString(), null)
            .addBlockPosArgument(TranslationText.commandArgumentSecondPosition.getString(), CountBlocksWithoutItemArgumentCommand)
            .addItemStackArgument(TranslationText.commandArgumentBlockName.getString(), CountBlocksCommand)
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

        return countBlocks(context, item);
    }

    public int countBlocks(ServerCommandContext context, Item item) {
        BlockPos firstPosition = new BlockPos(null);
        BlockPos secondPosition = new BlockPos(null);
        try {
            firstPosition = BlockPos.getBlockPos(context, FIRST_POSITION_ARGUMENT_NAME);
            secondPosition = BlockPos.getBlockPos(context, SECOND_POSITION_ARGUMENT_NAME);
        } catch (Exception ignored) {}

        try {
            if(secondPosition.isNull()) {
                firstPosition = BlockPos.getBlockPos(context, TranslationText.commandArgumentFirstPosition.getString());
                secondPosition = BlockPos.getBlockPos(context, TranslationText.commandArgumentSecondPosition.getString());
            }
        } catch (Exception ignored) {}

        try {
            ItemStack bookAndQuill = EntityResolver.getBookAndQuillFromContext(context);
            return super.countBlocks(context, new CommandConfig(firstPosition, secondPosition, item, bookAndQuill, getServerWorldFromContext(context)));
        }
        catch(Exception e) {
            context.sendFeedback(e.getMessage(), false);
        }
        return 0;
    }
}
