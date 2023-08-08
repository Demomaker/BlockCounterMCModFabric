package net.demomaker.blockcounter.command;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.item.Item;
import net.minecraft.server.command.ServerCommandSource;

public class CommandSetPositionWithoutItemArgument extends CommandSetPosition {
  @Override
  public int run(CommandContext<ServerCommandSource> context) {
    return super.countBlocks(context, (Item) null);
  }
}
