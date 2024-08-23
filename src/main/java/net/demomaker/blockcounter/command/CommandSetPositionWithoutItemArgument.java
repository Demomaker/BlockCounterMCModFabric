package net.demomaker.blockcounter.command;

import net.demomaker.blockcounter.facade.ServerCommandContext;
import net.demomaker.blockcounter.facade.Item;

public class CommandSetPositionWithoutItemArgument extends CommandSetPosition {
  @Override
  public int run(ServerCommandContext context) {
    return super.countBlocks(context, (Item) null);
  }
}
