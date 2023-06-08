package net.demomaker.blockcounter.common;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.demomaker.blockcounter.util.ResultMessageCreator;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.item.Item;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.CommandManager.RegistrationEnvironment;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.*;
public class CommandCountBlocks implements Command<ServerCommandSource> {

    private static final CommandCountBlocks CMD = new CommandCountBlocks();
    private static final CommandCountBlocksWithoutItemArgument CMD_WITHOUT_ITEM = new CommandCountBlocksWithoutItemArgument();
    private static final String COMMAND_NAME = "countblocks";

    public static final Algorithm ALGORITHM = new Algorithm();
    public static final String FIRST_POSITION_ARGUMENT_NAME = "first_position";
    public static final String SECOND_POSITION_ARGUMENT_NAME = "second_position";
    public static final String BLOCK_ARGUMENT_NAME = "block_name";

    public static LiteralCommandNode register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, RegistrationEnvironment environment) {
        return dispatcher.register(CommandManager.literal(COMMAND_NAME)
                .then(argument(FIRST_POSITION_ARGUMENT_NAME, BlockPosArgumentType.blockPos())
                        .then(argument(SECOND_POSITION_ARGUMENT_NAME, BlockPosArgumentType.blockPos())
                                .requires(cs -> cs.hasPermissionLevel(0))
                                .executes(CMD_WITHOUT_ITEM)
                                .then(argument(BLOCK_ARGUMENT_NAME, ItemStackArgumentType.itemStack(registryAccess))
                                        .requires(cs -> cs.hasPermissionLevel(0))
                                        .executes(CMD)
                                )
                        )
                ));
    }

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        try {
            BlockPos firstPosition = BlockPosArgumentType.getBlockPos(context, FIRST_POSITION_ARGUMENT_NAME);
            BlockPos secondPosition = BlockPosArgumentType.getBlockPos(context, SECOND_POSITION_ARGUMENT_NAME);
            ItemStackArgument itemArgument = ItemStackArgumentType.getItemStackArgument(context, BLOCK_ARGUMENT_NAME);
            Item item = itemArgument.getItem();
            ALGORITHM.setServerWorld(context.getSource().getWorld());
            String chatMessage = ResultMessageCreator.createMessage(ALGORITHM.GetStringContainingAllBlockCountsFor(firstPosition, secondPosition, item));
            context.getSource().sendFeedback(Text.of(chatMessage), false);
        }
        catch(Exception e) {
            context.getSource().sendFeedback(Text.of(e.toString()), false);
        }
        return 0;
    }
}
