package net.demomaker.blockcounter.command;

import net.demomaker.blockcounter.facade.Item;
import net.demomaker.blockcounter.facade.ServerCommand;
import net.demomaker.blockcounter.facade.ServerCommandContext;

public class CommandCountBlocksWithoutItemArgument extends CommandCountBlocks {

    public CommandCountBlocksWithoutItemArgument(ServerCommand command) {
        super(command);
    }

    @Override
    public int run(ServerCommandContext context) {
        return super.countBlocks(context, (Item) null);
    }
}
