package net.demomaker.blockcounter.adapter.servercommand;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;

public record ServerCommandDispatcher(CommandDispatcher<ServerCommandSource> commandDispatcher) {

}
