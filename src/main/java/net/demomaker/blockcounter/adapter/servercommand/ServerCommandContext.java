package net.demomaker.blockcounter.adapter.servercommand;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContextBuilder;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public record ServerCommandContext(
    com.mojang.brigadier.context.CommandContext<ServerCommandSource> commandContext) {

  public ServerCommandSource getSource() {
    return commandContext().getSource();
  }

  public static ServerCommandContext createCommandContext(ServerCommandSource source, String commandName) {
    CommandDispatcher<ServerCommandSource> dispatcher = new CommandDispatcher<>();
    CommandContextBuilder<ServerCommandSource> builder = new CommandContextBuilder<>(
        dispatcher,
        source,
        dispatcher.getRoot(),
        0
    );

    StringReader reader = new StringReader(commandName);
    dispatcher.parse(reader, source);

    return new ServerCommandContext(builder.build(commandName));
  }

  public void sendFeedback(String message, boolean broadcastToOps) {
    this.commandContext().getSource().sendFeedback(() -> Text.of(message), broadcastToOps);
  }
}
