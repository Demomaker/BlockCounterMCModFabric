package net.demomaker.blockcounter.main;

import com.mojang.brigadier.CommandDispatcher;
import net.demomaker.blockcounter.adapter.entity.ServerPlayerEntity;
import net.demomaker.blockcounter.adapter.player.GameProfile;
import net.demomaker.blockcounter.adapter.servercommand.ServerCommandDispatcher;
import net.demomaker.blockcounter.adapter.servercommand.ServerCommandRegistryAccess;
import net.demomaker.blockcounter.algorithm.Algorithm;
import net.demomaker.blockcounter.command.config.CommandConfigs;
import net.demomaker.blockcounter.command.config.SetPositionCommandConfig;
import net.demomaker.blockcounter.command.implementation.CommandCountBlocks;
import net.demomaker.blockcounter.command.implementation.CommandCountBlocksWithoutItemArgument;
import net.demomaker.blockcounter.command.implementation.CommandSetPosition;
import net.demomaker.blockcounter.command.implementation.CommandSetPositionWithoutItemArgument;
import net.demomaker.blockcounter.identity.PlayerConfig;
import net.demomaker.blockcounter.util.ModObjects;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager.RegistrationEnvironment;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ModCommands {
    private static long lastExecutionTime = 0;
    private static final long COOLDOWN_PERIOD = 1000; // 5 seconds
    public static final CommandCountBlocks COMMAND_COUNT_BLOCKS = new CommandCountBlocks();
    public static final CommandCountBlocksWithoutItemArgument COMMAND_COUNT_BLOCKS_WITHOUT_ITEM_ARGUMENT = new CommandCountBlocksWithoutItemArgument();
    public static final CommandSetPosition COMMAND_SET_POSITION = new CommandSetPosition();
    public static final CommandSetPositionWithoutItemArgument COMMAND_SET_POSITION_WITHOUT_ITEM_ARGUMENT = new CommandSetPositionWithoutItemArgument();
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, RegistrationEnvironment ignoredEnvironment) {
        dispatcher.register(CommandCountBlocks.getServerCommandFormat(new ServerCommandRegistryAccess(registryAccess), COMMAND_COUNT_BLOCKS, COMMAND_COUNT_BLOCKS_WITHOUT_ITEM_ARGUMENT).toLiteralArgumentBuilder(new ServerCommandDispatcher(dispatcher)));
        dispatcher.register(CommandSetPosition.getServerCommandFormat(new ServerCommandRegistryAccess(registryAccess), COMMAND_SET_POSITION, COMMAND_SET_POSITION_WITHOUT_ITEM_ARGUMENT).toLiteralArgumentBuilder(new ServerCommandDispatcher(dispatcher)));
    }

    public static void disconnect(ServerPlayNetworkHandler handler, MinecraftServer ignoredServer) {
        PlayerConfig wantedPlayerConfig = new PlayerConfig(new GameProfile(handler.getPlayer().getGameProfile()), null, null);
        ModObjects.commandExecutionConfigs.getConfig(wantedPlayerConfig).getAlgorithm().stop();
        ModObjects.commandExecutionConfigs.removeConfig(wantedPlayerConfig);
    }

    public static void join(ServerPlayNetworkHandler serverPlayNetworkHandler, PacketSender ignoredPacketSender, MinecraftServer ignoredMinecraftServer) {
        PlayerConfig newPlayerConfig = new PlayerConfig(new GameProfile(serverPlayNetworkHandler.getPlayer().getGameProfile()), new Algorithm(), new CommandConfigs(new SetPositionCommandConfig(null, null, null, null, null)));
        ModObjects.commandExecutionConfigs.addConfig(newPlayerConfig);
    }

    public static ActionResult blockLeftClick(PlayerEntity playerEntity, World world, Hand hand, BlockPos blockPos, Direction direction) {
        long currentTime = System.currentTimeMillis();

        if (world.isClient && hand == Hand.MAIN_HAND && currentTime - lastExecutionTime >= COOLDOWN_PERIOD) {
            lastExecutionTime = currentTime;

            CommandSetPositionWithoutItemArgument.executeFrom((ClientPlayerEntity) playerEntity, new net.demomaker.blockcounter.adapter.block.BlockPos(blockPos));
        }

        return ServerPlayerEntity.getFrom(ModObjects.minecraftServer, playerEntity.getUuid()).isHoldingBook() ? ActionResult.SUCCESS : ActionResult.PASS;
    }
}
