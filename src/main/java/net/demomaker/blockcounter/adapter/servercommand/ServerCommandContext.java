package net.demomaker.blockcounter.adapter.servercommand;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public record ServerCommandContext(
    com.mojang.brigadier.context.CommandContext<ServerCommandSource> commandContext) {

  public ServerCommandSource getSource() {
    return commandContext().getSource();
  }

  public void sendFeedback(String message, boolean broadcastToOps) {
    this.commandContext().getSource().sendFeedback(() -> Text.of(message), broadcastToOps);
  }
}
