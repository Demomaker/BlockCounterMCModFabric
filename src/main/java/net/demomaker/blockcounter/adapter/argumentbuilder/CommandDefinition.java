package net.demomaker.blockcounter.adapter.argumentbuilder;

import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.ArrayList;
import java.util.List;
import net.demomaker.blockcounter.adapter.servercommand.ServerCommand;
import net.demomaker.blockcounter.adapter.servercommand.ServerCommandRegistryAccess;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.command.permission.Permission;
import net.minecraft.command.permission.PermissionLevel;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

public class CommandDefinition {

  protected String name;
  protected ServerCommand serverCommand = null;
  protected List<CommandArgument<?>> commandArguments = new ArrayList<>();
  private final ServerCommandArgumentBuilder parent;
  public CommandDefinition(ServerCommandArgumentBuilder parent) {
    this.parent = parent;
  }

  public CommandDefinition addBlockPosArgument(String name, ServerCommand serverCommand) {
    CommandArgument<BlockPosArgumentType> commandArgument = new CommandArgument<>();
    commandArgument.name = name;
    commandArgument.type = BlockPosArgumentType.blockPos();
    commandArgument.serverCommand = serverCommand;
    commandArguments.add(commandArgument);
    return this;
  }

  public CommandDefinition addItemStackArgument(String name, ServerCommand serverCommand,
      ServerCommandRegistryAccess serverCommandRegistryAccess) {
    CommandArgument<ItemStackArgumentType> commandArgument = new CommandArgument<>();
    commandArgument.name = name;
    commandArgument.type = ItemStackArgumentType.itemStack(serverCommandRegistryAccess.getCommandRegistryAccess());
    commandArgument.serverCommand = serverCommand;
    commandArguments.add(commandArgument);
    return this;
  }

  public CommandDefinition addStringArgument(String name, ServerCommand serverCommand) {
    CommandArgument<StringArgumentType> commandArgument = new CommandArgument<>();
    commandArgument.name = name;
    commandArgument.type = StringArgumentType.string();
    commandArgument.serverCommand = serverCommand;
    commandArguments.add(commandArgument);
    return this;
  }

  public ServerCommandArgumentBuilder endCommand() {
    return this.parent;
  }

  protected LiteralCommandNode<ServerCommandSource> toLiteralCommandNode(
      CommandDispatcher<ServerCommandSource> commandDispatcher) {
    LiteralArgumentBuilder<ServerCommandSource> literalCommand = CommandManager.literal(name).requires(cs -> cs.getPermissions().hasPermission(new Permission.Level(PermissionLevel.ALL)));
    if(serverCommand != null) {
      literalCommand.requires(cs -> cs.getPermissions().hasPermission(new Permission.Level(PermissionLevel.ALL))).executes(serverCommand.getCommand());
    }

    if(commandArguments.size() != 0) {
      ArgumentBuilder<ServerCommandSource, ?> argumentBuilder = argument(commandArguments.getLast().name, (ArgumentType<?>) commandArguments.getLast().type);
      if(commandArguments.getLast().serverCommand != null) {
        argumentBuilder.requires(cs -> cs.getPermissions().hasPermission(new Permission.Level(PermissionLevel.ALL))).executes(commandArguments.getLast().serverCommand.getCommand());
      }
      for (int i = commandArguments.size() - 2; i >= 0; i--) {
        var commandArgument = commandArguments.get(i);
        ArgumentBuilder<ServerCommandSource, ?> subArgumentBuilder = argument(commandArgument.name, (ArgumentType<?>) commandArgument.type);
        if(commandArgument.serverCommand != null) {
          subArgumentBuilder.requires(cs -> cs.getPermissions().hasPermission(new Permission.Level(PermissionLevel.ALL))).executes(commandArgument.serverCommand.getCommand());
        }

        subArgumentBuilder.then(argumentBuilder);
        argumentBuilder = subArgumentBuilder;
      }

      literalCommand.then(argumentBuilder);
    }

    return commandDispatcher.register(literalCommand);
  }
}
