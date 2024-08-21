package net.demomaker.blockcounter.command;

import static net.minecraft.server.command.CommandManager.argument;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.demomaker.blockcounter.command.config.CommandConfig;
import net.demomaker.blockcounter.entity.EntityResolver;
import net.demomaker.blockcounter.main.BlockCounter;
import net.demomaker.blockcounter.util.FeedbackSender;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;
public class CommandCountBlocks extends BasicCommand {
    public static final String COMMAND_NAME = "countblocks";

    public static com.mojang.brigadier.builder.LiteralArgumentBuilder<net.minecraft.server.command.ServerCommandSource> getServerCommandFormat(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, Command<ServerCommandSource> CountBlocksCommand, Command<ServerCommandSource> CountBlocksWithoutItemArgumentCommand) {
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
    public int run(CommandContext<ServerCommandSource> context) {
        ItemStackArgument itemArgument = ItemStackArgumentType.getItemStackArgument(context, BLOCK_ARGUMENT_NAME);
        Item item = itemArgument.getItem();
        return countBlocks(context, item);
    }

    public int countBlocks(CommandContext<ServerCommandSource> context, Item item) {
        try {
            BlockPos firstPosition = BlockPosArgumentType.getBlockPos(context, FIRST_POSITION_ARGUMENT_NAME);
            BlockPos secondPosition = BlockPosArgumentType.getBlockPos(context, SECOND_POSITION_ARGUMENT_NAME);
            ItemStack bookAndQuill = EntityResolver.getBookAndQuillFromContext(context);
            return super.countBlocks(context, new CommandConfig(firstPosition, secondPosition, item, bookAndQuill, getServerWorldFromContext(context)));
        }
        catch(Exception e) {
            FeedbackSender.send(context, e.getMessage());
        }
        return 0;
    }
}
