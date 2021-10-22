package net.demomaker.blockcounter.util;

import com.mojang.brigadier.CommandDispatcher;
import net.demomaker.blockcounter.common.CommandCountBlocks;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class ModCommands {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal(BlockCounter.MOD_ID)
                        .then(CommandCountBlocks.register(dispatcher))
        );
    }

}
