package net.demomaker.blockcounter.adapter.servercommand;

import net.minecraft.server.command.ServerCommandSource;

public class ServerCommand {
  private final com.mojang.brigadier.Command<ServerCommandSource> command;
  public ServerCommand() { this.command = context -> run(new ServerCommandContext(context)); }

  public int run(ServerCommandContext commandContext) {
    return 0;
  }
  public com.mojang.brigadier.Command<ServerCommandSource> getCommand() {
    return this.command;
  }
}
