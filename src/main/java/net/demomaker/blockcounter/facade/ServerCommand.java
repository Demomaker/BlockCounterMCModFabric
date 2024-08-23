package net.demomaker.blockcounter.facade;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;

public class ServerCommand {
  protected final com.mojang.brigadier.Command<ServerCommandSource> command;
  public ServerCommand(ServerCommand command) { this.command = command.command; }
  public int run(ServerCommandContext commandContext) throws CommandSyntaxException {
    return command.run(commandContext.commandContext);
  }
}
