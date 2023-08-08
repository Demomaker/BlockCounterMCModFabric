package net.demomaker.blockcounter.common;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.demomaker.blockcounter.util.UserMessageSender;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.server.command.ServerCommandSource;

import static net.demomaker.blockcounter.common.CommandCountBlocks.ALGORITHM;

public class CommandCountBlocksWithoutItemArgument implements Command<ServerCommandSource> {
    @Override
    public int run(CommandContext<ServerCommandSource> context) {
        ALGORITHM.setServerWorld(context.getSource().getWorld());
        new BlockCounterUtil(new UserMessageSender(context.getSource()), ALGORITHM)
                .setFirstPosition(BlockPosArgumentType.getBlockPos(context, CommandCountBlocks.FIRST_POSITION_ARGUMENT_NAME))
                .setSecondPosition(BlockPosArgumentType.getBlockPos(context, CommandCountBlocks.SECOND_POSITION_ARGUMENT_NAME))
                .count();
        return 0;
    }
}
