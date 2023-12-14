package net.demomaker.blockcounter.command.config;

import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class CommandConfig {
  public BlockPos firstPosition;
  public BlockPos secondPosition;
  public Item itemFilter;
  public ServerWorld serverWorld;
  public CommandConfig(BlockPos firstPosition, BlockPos secondPosition, Item itemFilter, ServerWorld serverWorld) {
    this.firstPosition = firstPosition;
    this.secondPosition = secondPosition;
    this.itemFilter = itemFilter;
    this.serverWorld = serverWorld;
  }
}
