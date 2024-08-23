package net.demomaker.blockcounter.command.config;

import net.demomaker.blockcounter.facade.Item;
import net.demomaker.blockcounter.facade.ItemStack;
import net.demomaker.blockcounter.facade.ServerWorld;
import net.demomaker.blockcounter.facade.BlockPos;

public class SetPositionCommandConfig extends CommandConfig {

  public SetPositionCommandConfig(BlockPos firstPosition, BlockPos secondPosition,
      Item itemFilter, ItemStack bookAndQuill, ServerWorld serverWorld) {
    super(firstPosition, secondPosition, itemFilter, bookAndQuill, serverWorld);
  }
}
