package net.demomaker.blockcounter.main;

import com.mojang.brigadier.CommandDispatcher;
import net.demomaker.blockcounter.algorithm.Algorithm;
import net.demomaker.blockcounter.command.CommandCountBlocks;
import net.demomaker.blockcounter.command.CommandCountBlocksWithoutItemArgument;
import net.demomaker.blockcounter.command.CommandSetPosition;
import net.demomaker.blockcounter.command.CommandSetPositionWithoutItemArgument;
import net.demomaker.blockcounter.command.config.CommandConfigs;
import net.demomaker.blockcounter.command.config.SetPositionCommandConfig;
import net.demomaker.blockcounter.config.PlayerConfig;
import net.demomaker.blockcounter.util.ModObjects;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager.RegistrationEnvironment;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayNetworkHandler;

public class ModCommands {
    private static final CommandCountBlocks COMMAND_COUNT_BLOCKS = new CommandCountBlocks();
    private static final CommandCountBlocksWithoutItemArgument COMMAND_COUNT_BLOCKS_WITHOUT_ITEM_ARGUMENT = new CommandCountBlocksWithoutItemArgument();
    private static final CommandSetPosition COMMAND_SET_POSITION = new CommandSetPosition();
    private static final CommandSetPositionWithoutItemArgument COMMAND_SET_POSITION_WITHOUT_ITEM_ARGUMENT = new CommandSetPositionWithoutItemArgument();
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, RegistrationEnvironment environment) {
        dispatcher.register(CommandCountBlocks.getServerCommandFormat(dispatcher, registryAccess, COMMAND_COUNT_BLOCKS, COMMAND_COUNT_BLOCKS_WITHOUT_ITEM_ARGUMENT));
        dispatcher.register(CommandSetPosition.getServerCommandFormat(dispatcher, registryAccess, COMMAND_SET_POSITION, COMMAND_SET_POSITION_WITHOUT_ITEM_ARGUMENT));
    }

    public static void disconnect(ServerPlayNetworkHandler handler, MinecraftServer server) {
        PlayerConfig wantedPlayerConfig = new PlayerConfig(handler.getPlayer().getGameProfile(), null, null);
        ModObjects.commandExecutionConfigs.getConfig(wantedPlayerConfig).getAlgorithm().stop();
        ModObjects.commandExecutionConfigs.removeConfig(wantedPlayerConfig);
    }

    public static void join(ServerPlayNetworkHandler serverPlayNetworkHandler,
        PacketSender packetSender, MinecraftServer minecraftServer) {
        PlayerConfig newPlayerConfig = new PlayerConfig(serverPlayNetworkHandler.getPlayer().getGameProfile(), new Algorithm(), new CommandConfigs(new SetPositionCommandConfig(null, null, null, null, null)));
        ModObjects.commandExecutionConfigs.addConfig(newPlayerConfig);
    }
}
