package net.demomaker.blockcounter.util;

import net.demomaker.blockcounter.common.CommandCountBlocks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.server.command.CommandManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
public class BlockCounter implements ModInitializer {

    public static final String MOD_ID = "blockcounter";

    @Override
    public void onInitialize() {
        CommandRegistry.INSTANCE.register(false, ModCommands::register);
    }
}
