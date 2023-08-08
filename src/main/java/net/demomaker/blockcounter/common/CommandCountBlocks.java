package net.demomaker.blockcounter.common;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.demomaker.blockcounter.util.UserMessageSender;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.RegistrationEnvironment;
import static net.minecraft.server.command.CommandManager.argument;
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
    public int run(CommandContext<ServerCommandSource> context) {
        try {
            ALGORITHM.setServerWorld(context.getSource().getWorld());
            new BlockCounterUtil(new UserMessageSender(context.getSource()), ALGORITHM)
                    .setFirstPosition(BlockPosArgumentType.getBlockPos(context, FIRST_POSITION_ARGUMENT_NAME))
                    .setSecondPosition(BlockPosArgumentType.getBlockPos(context, SECOND_POSITION_ARGUMENT_NAME))
                    .setFilterItem(ItemStackArgumentType.getItemStackArgument(context, BLOCK_ARGUMENT_NAME).getItem())
                    .count();
        }
        catch(Exception e) {
            context.getSource().sendFeedback(() -> Text.of(e.getMessage()), false);
        }
        return 0;
    }
}
