package net.demomaker.blockcounter.main;

import net.demomaker.blockcounter.payload.ClipboardPayload;
import net.demomaker.blockcounter.util.ModObjects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;

// The value here should match an entry in the META-INF/mods.toml file
public class BlockCounter implements ModInitializer {

    public static final String MOD_ID = "blockcounter";

    @Override
    public void onInitialize() {
        initializeServer();

        if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            initializeClient();
        }
    }

    public void initializeServer() {
        CommandRegistrationCallback.EVENT.register(ModCommands::register);
        ServerPlayConnectionEvents.DISCONNECT.register(ModCommands::disconnect);
        ServerPlayConnectionEvents.JOIN.register(ModCommands::join);
        AttackBlockCallback.EVENT.register(ModCommands::blockLeftClick);
        ServerLifecycleEvents.SERVER_STARTED.register(BlockCounter::onServerStart);
    }

    @Environment(EnvType.CLIENT)
    public void initializeClient() {
        ClientLifecycleEvents.CLIENT_STARTED.register(BlockCounter::onClientStart);
        ClientPlayNetworking.registerGlobalReceiver(ClipboardPayload.CLIPBOARD_PAYLOAD_ID, (client, handler, buf, responseSender) -> {
            MinecraftClient localClient = ModObjects.minecraftClient;
            if(localClient == null) {
                return;
            }
            localClient.keyboard.setClipboard(buf.readString());
        });
    }

    @Environment(EnvType.CLIENT)
    private static void onClientStart(MinecraftClient minecraftClient) {
        ModObjects.minecraftClient = minecraftClient;
    }

    private static void onServerStart(MinecraftServer minecraftServer) {
        ModObjects.minecraftServer = minecraftServer;
    }
}
