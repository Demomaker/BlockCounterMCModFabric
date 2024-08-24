package net.demomaker.blockcounter.command.implementation;

import net.demomaker.blockcounter.adapter.block.BlockPos;
import net.demomaker.blockcounter.adapter.item.Item;
import net.demomaker.blockcounter.adapter.item.ItemStack;
import net.demomaker.blockcounter.adapter.servercommand.ServerCommand;
import net.demomaker.blockcounter.adapter.servercommand.ServerCommandContext;
import net.demomaker.blockcounter.adapter.servercommand.ServerCommandRegistryAccess;
import net.demomaker.blockcounter.adapter.argumentbuilder.ServerCommandArgumentBuilder;
import net.demomaker.blockcounter.command.config.CommandConfig;
import net.demomaker.blockcounter.entity.EntityResolver;

public class CommandCountBlocks extends BasicCommand {
    public static final String COMMAND_NAME = "countblocks";

    public static ServerCommandArgumentBuilder getServerCommandFormat(ServerCommandRegistryAccess registryAccess, ServerCommand CountBlocksCommand, ServerCommand CountBlocksWithoutItemArgumentCommand) {
        return new ServerCommandArgumentBuilder()
            .beginCommand(CommandCountBlocks.COMMAND_NAME, null)
            .addBlockPosArgument(CommandCountBlocks.FIRST_POSITION_ARGUMENT_NAME, null)
            .addBlockPosArgument(CommandCountBlocks.SECOND_POSITION_ARGUMENT_NAME, CountBlocksWithoutItemArgumentCommand)
            .addItemStackArgument(CommandCountBlocks.BLOCK_ARGUMENT_NAME, CountBlocksCommand, registryAccess)
            .endCommand();
    }

    @Override
    public int run(ServerCommandContext context) {
        Item item = new Item(ItemStack.getArgument(context, BLOCK_ARGUMENT_NAME).getItem());
        return countBlocks(context, item);
    }

    public int countBlocks(ServerCommandContext context, Item item) {
        try {
            BlockPos firstPosition = BlockPos.getBlockPos(context, FIRST_POSITION_ARGUMENT_NAME);
            BlockPos secondPosition = BlockPos.getBlockPos(context, SECOND_POSITION_ARGUMENT_NAME);
            ItemStack bookAndQuill = EntityResolver.getBookAndQuillFromContext(context);
            return super.countBlocks(context, new CommandConfig(firstPosition, secondPosition, item, bookAndQuill, getServerWorldFromContext(context)));
        }
        catch(Exception e) {
            context.sendFeedback(e.getMessage(), false);
        }
        return 0;
    }
}
