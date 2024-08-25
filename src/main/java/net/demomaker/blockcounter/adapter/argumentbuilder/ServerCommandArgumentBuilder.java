package net.demomaker.blockcounter.adapter.argumentbuilder;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.ArrayList;
import java.util.List;
import net.demomaker.blockcounter.adapter.servercommand.ServerCommand;
import net.demomaker.blockcounter.adapter.servercommand.ServerCommandDispatcher;
import net.demomaker.blockcounter.main.BlockCounter;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class ServerCommandArgumentBuilder {
  private final List<CommandDefinition> commands = new ArrayList<>();

  public LiteralArgumentBuilder<ServerCommandSource> toLiteralArgumentBuilder(ServerCommandDispatcher dispatcher) {
    CommandDispatcher<ServerCommandSource> commandDispatcher = dispatcher.commandDispatcher();
    LiteralArgumentBuilder<ServerCommandSource> literalArgumentBuilder = CommandManager.literal(BlockCounter.MOD_ID);
    for (CommandDefinition command : commands) {
      literalArgumentBuilder = literalArgumentBuilder.then(command.toLiteralCommandNode(commandDispatcher));
    }
    return literalArgumentBuilder;
  }

  public LiteralArgumentBuilder<ServerCommandSource> toTranslatedLiteralArgumentBuilder(ServerCommandDispatcher dispatcher) {
    CommandDispatcher<ServerCommandSource> commandDispatcher = dispatcher.commandDispatcher();
    LiteralArgumentBuilder<ServerCommandSource> literalArgumentBuilder = CommandManager.literal(BlockCounter.MOD_ID);
    for (CommandDefinition command : commands) {
      literalArgumentBuilder = literalArgumentBuilder.then(command.toLiteralCommandNode(commandDispatcher));
    }
    return literalArgumentBuilder;
  }

  public CommandDefinition beginCommand(String commandName, ServerCommand serverCommand) {
    CommandDefinition command = new CommandDefinition(this);
    command.name = commandName;
    command.serverCommand = serverCommand;
    this.commands.add(command);
    return command;
  }
}

