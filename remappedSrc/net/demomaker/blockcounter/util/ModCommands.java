package net.demomaker.blockcounter.util;

import com.mojang.brigadier.CommandDispatcher;
import net.demomaker.blockcounter.common.CommandCountBlocks;
import net.demomaker.blockcounter.common.CommandSetPosition;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.CommandManager.RegistrationEnvironment;
import net.minecraft.server.command.ServerCommandSource;

public class ModCommands {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, RegistrationEnvironment environment) {
        dispatcher.register(
                CommandManager.literal(BlockCounter.MOD_ID).then(
                    CommandCountBlocks.register(dispatcher, registryAccess, environment)
                )
        );

        dispatcher.register(
            CommandManager.literal(BlockCounter.MOD_ID).then(
                CommandSetPosition.register(dispatcher, registryAccess, environment)
            )
        );
    }

}
