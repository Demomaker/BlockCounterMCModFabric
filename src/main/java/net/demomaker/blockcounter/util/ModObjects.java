package net.demomaker.blockcounter.util;

import net.demomaker.blockcounter.identity.CommandExecutionConfigs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;

public class ModObjects {
  public static CommandExecutionConfigs commandExecutionConfigs = new CommandExecutionConfigs();
  public static MinecraftServer minecraftServer = null;
  public static MinecraftClient minecraftClient = null;
}
