package net.demomaker.blockcounter.command.config;

import net.demomaker.blockcounter.adapter.item.Item;
import net.demomaker.blockcounter.adapter.item.ItemStack;
import net.demomaker.blockcounter.adapter.world.ServerWorld;
import net.demomaker.blockcounter.adapter.block.BlockPos;

public class SetPositionCommandConfig extends CommandConfig {

  public SetPositionCommandConfig(BlockPos firstPosition, BlockPos secondPosition,
      Item itemFilter, ItemStack bookAndQuill, ServerWorld serverWorld) {
    super(firstPosition, secondPosition, itemFilter, bookAndQuill, serverWorld);
  }
}
