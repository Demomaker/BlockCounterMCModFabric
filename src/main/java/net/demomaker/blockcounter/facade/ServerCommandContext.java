package net.demomaker.blockcounter.facade;

import net.minecraft.server.command.ServerCommandSource;

public class ServerCommandContext {
  protected final com.mojang.brigadier.context.CommandContext<ServerCommandSource> commandContext;
  protected final net.demomaker.blockcounter.facade.ServerCommandSource serverCommandSource;
  public ServerCommandContext(com.mojang.brigadier.context.CommandContext<ServerCommandSource> commandContext) {
    this.commandContext = commandContext;
    this.serverCommandSource = new net.demomaker.blockcounter.facade.ServerCommandSource(commandContext.getSource());
  }

  public ServerCommandSource getSource() {
    return commandContext.getSource();
  }
}
