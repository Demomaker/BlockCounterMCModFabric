package net.demomaker.blockcounter.common;

import static net.demomaker.blockcounter.common.CommandCountBlocks.ALGORITHM;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.demomaker.blockcounter.common.CommandCountBlocks;
import net.demomaker.blockcounter.util.ResultMessageCreator;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class CommandCountBlocksWithoutItemArgument implements Command<ServerCommandSource> {
    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        BlockPos firstPosition = BlockPosArgumentType.getBlockPos(context, CommandCountBlocks.FIRST_POSITION_ARGUMENT_NAME);
        BlockPos secondPosition = BlockPosArgumentType.getBlockPos(context, CommandCountBlocks.SECOND_POSITION_ARGUMENT_NAME);
        ALGORITHM.setServerWorld(context.getSource().getWorld());
        String chatMessage = ResultMessageCreator.createMessage(ALGORITHM.GetStringContainingAllBlockCountsFor(firstPosition, secondPosition, null));
        context.getSource().sendFeedback(Text.of(chatMessage), false);
        return 0;
    }
}
