package net.demomaker.blockcounter.adapter.servercommand;

import net.minecraft.command.CommandRegistryAccess;

public record ServerCommandRegistryAccess(CommandRegistryAccess commandRegistryAccess) {

  public CommandRegistryAccess getCommandRegistryAccess() {
    return this.commandRegistryAccess;
  }
}
