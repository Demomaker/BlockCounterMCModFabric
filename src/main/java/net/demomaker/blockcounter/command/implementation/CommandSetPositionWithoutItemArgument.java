package net.demomaker.blockcounter.command.implementation;

import net.demomaker.blockcounter.adapter.entity.ServerPlayerEntity;
import net.demomaker.blockcounter.adapter.servercommand.ServerCommandContext;
import net.demomaker.blockcounter.adapter.item.Item;
import net.demomaker.blockcounter.main.ModCommands;
import net.demomaker.blockcounter.util.ModObjects;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.demomaker.blockcounter.adapter.block.BlockPos;

public class CommandSetPositionWithoutItemArgument extends CommandSetPosition {
  public static int executeFrom(ClientPlayerEntity player, BlockPos blockPos) {
    ServerPlayerEntity serverPlayerEntity = ServerPlayerEntity.getFrom(ModObjects.minecraftServer, player.getUuid());
    if(serverPlayerEntity.isNull()) {
      return -1;
    }

    return executeFrom(serverPlayerEntity.getServerPlayerEntity(), blockPos);
  }

  public static int executeFrom(net.minecraft.server.network.ServerPlayerEntity player, BlockPos blockPos) {
    ServerPlayerEntity serverPlayerEntity = new ServerPlayerEntity(player);
    if(serverPlayerEntity.isNull()) {
      return -1;
    }
    ServerCommandSource commandSource = serverPlayerEntity.getServerPlayerEntity().getCommandSource();
    if (serverPlayerEntity.isHoldingBook()) {
      return ModCommands.COMMAND_SET_POSITION_WITHOUT_ITEM_ARGUMENT.countBlocks(ServerCommandContext.createCommandContext(commandSource, CommandSetPositionWithoutItemArgument.COMMAND_NAME), blockPos);
    }

    return -1;
  }
  @Override
  public int run(ServerCommandContext context) {
    return super.countBlocks(context, (Item) null, null);
  }

  public int countBlocks(ServerCommandContext context, BlockPos blockPos) {
    return super.countBlocks(context, (Item) null, blockPos);
  }
}
