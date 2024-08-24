package net.demomaker.blockcounter.command.implementation;

import net.demomaker.blockcounter.adapter.servercommand.ServerCommandContext;
import net.demomaker.blockcounter.adapter.item.Item;

public class CommandSetPositionWithoutItemArgument extends CommandSetPosition {
  @Override
  public int run(ServerCommandContext context) {
    return super.countBlocks(context, (Item) null);
  }
}
