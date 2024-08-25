package net.demomaker.blockcounter.command.implementation;

import net.demomaker.blockcounter.adapter.item.Item;
import net.demomaker.blockcounter.adapter.servercommand.ServerCommandContext;

public class CommandCountBlocksWithoutItemArgument extends CommandCountBlocks {

    @Override
    public int run(ServerCommandContext context) {
        return super.countBlocks(context, (Item) null);
    }
}
