package net.demomaker.blockcounter.facade;

public class ServerCommandArgumentBuilder {
  /*public static com.mojang.brigadier.builder.LiteralArgumentBuilder<net.minecraft.server.command.ServerCommandSource> getServerCommandFormat(
      CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, ServerCommand CountBlocksCommand, ServerCommand CountBlocksWithoutItemArgumentCommand) {
    return CommandManager.literal(BlockCounter.MOD_ID).then(
        dispatcher.register(CommandManager.literal(CommandSetPosition.COMMAND_NAME)
            .requires(cs -> cs.hasPermissionLevel(0))
            .executes(CountBlocksWithoutItemArgumentCommand)
            .then(argument(BLOCK_ARGUMENT_NAME, ItemStackArgumentType.itemStack(registryAccess))
                .requires(cs -> cs.hasPermissionLevel(0))
                .executes(CountBlocksCommand)
            ))
    );
  }*/

}
