package net.demomaker.blockcounter.main;

import net.demomaker.blockcounter.payload.ClipboardPayload;
import net.demomaker.blockcounter.util.ModObjects;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;

// The value here should match an entry in the META-INF/mods.toml file
public class BlockCounter implements ModInitializer {

    public static final String MOD_ID = "blockcounter";

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register(ModCommands::register);
        ServerPlayConnectionEvents.DISCONNECT.register(ModCommands::disconnect);
        ServerPlayConnectionEvents.JOIN.register(ModCommands::join);
        AttackBlockCallback.EVENT.register(ModCommands::blockLeftClick);
        ServerLifecycleEvents.SERVER_STARTED.register(BlockCounter::onServerStart);
        ClientLifecycleEvents.CLIENT_STARTED.register(BlockCounter::onClientStart);
        ClientPlayNetworking.registerGlobalReceiver(ClipboardPayload.CLIPBOARD_PAYLOAD_ID, (client, handler, buf, responseSender) -> {
            MinecraftClient localClient = ModObjects.minecraftClient;
            localClient.keyboard.setClipboard(buf.readString());
        });
    }

    private static void onClientStart(MinecraftClient minecraftClient) {
        ModObjects.minecraftClient = minecraftClient;
    }

    private static void onServerStart(MinecraftServer minecraftServer) {
        ModObjects.minecraftServer = minecraftServer;
    }
}
