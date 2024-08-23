package net.demomaker.blockcounter.command.config;

import net.demomaker.blockcounter.facade.Item;
import net.demomaker.blockcounter.facade.ItemStack;
import net.demomaker.blockcounter.facade.BlockPos;
import net.demomaker.blockcounter.facade.ServerWorld;

public class CommandConfig {
  public BlockPos firstPosition;
  public BlockPos secondPosition;
  public Item itemFilter;
  public ItemStack writableBook;
  public ServerWorld serverWorld;
  public CommandConfig(BlockPos firstPosition, BlockPos secondPosition, Item itemFilter, ItemStack writableBook, ServerWorld serverWorld) {
    this.firstPosition = firstPosition;
    this.secondPosition = secondPosition;
    this.itemFilter = itemFilter;
    this.serverWorld = serverWorld;
    this.writableBook = writableBook;
  }
}
