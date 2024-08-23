package net.demomaker.blockcounter.command;

import static net.minecraft.server.command.CommandManager.argument;

import net.demomaker.blockcounter.facade.ServerCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.demomaker.blockcounter.facade.ServerCommandContext;
import net.demomaker.blockcounter.command.config.CommandConfig;
import net.demomaker.blockcounter.entity.EntityResolver;
import net.demomaker.blockcounter.main.BlockCounter;
import net.demomaker.blockcounter.util.FeedbackSender;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.demomaker.blockcounter.facade.Item;
import net.demomaker.blockcounter.facade.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.demomaker.blockcounter.facade.ServerCommandSource;
import net.demomaker.blockcounter.facade.BlockPos;
public class CommandCountBlocks extends BasicCommand {
    public static final String COMMAND_NAME = "countblocks";

    public CommandCountBlocks(ServerCommand command) {
        super(command);
    }

    public static com.mojang.brigadier.builder.LiteralArgumentBuilder<net.demomaker.blockcounter.facade.ServerCommandSource> getServerCommandFormat(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, ServerCommand<ServerCommandSource> CountBlocksCommand, ServerCommand<ServerCommandSource> CountBlocksWithoutItemArgumentCommand) {
        return CommandManager.literal(BlockCounter.MOD_ID).then(
            dispatcher.register(CommandManager.literal(CommandCountBlocks.COMMAND_NAME)
                .then(argument(CommandCountBlocks.FIRST_POSITION_ARGUMENT_NAME, BlockPosArgumentType.blockPos())
                    .then(argument(CommandCountBlocks.SECOND_POSITION_ARGUMENT_NAME, BlockPosArgumentType.blockPos())
                        .requires(cs -> cs.hasPermissionLevel(0))
                        .executes(CountBlocksWithoutItemArgumentCommand)
                        .then(argument(CommandCountBlocks.BLOCK_ARGUMENT_NAME, ItemStackArgumentType.itemStack(registryAccess))
                            .requires(cs -> cs.hasPermissionLevel(0))
                            .executes(CountBlocksCommand)
                        )
                    )
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
            BlockPos firstPosition = BlockPos.getBlockPos(context, FIRST_POSITION_ARGUMENT_NAME);
            BlockPos secondPosition = BlockPos.getBlockPos(context, SECOND_POSITION_ARGUMENT_NAME);
            ItemStack bookAndQuill = EntityResolver.getBookAndQuillFromContext(context);
            return super.countBlocks(context, new CommandConfig(firstPosition, secondPosition, item, bookAndQuill, getServerWorldFromContext(context)));
        }
        catch(Exception e) {
            FeedbackSender.send(context, e.getMessage());
        }
        return 0;
    }
}
