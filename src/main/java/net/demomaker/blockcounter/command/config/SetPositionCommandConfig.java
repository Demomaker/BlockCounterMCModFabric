package net.demomaker.blockcounter.command.config;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class SetPositionCommandConfig extends CommandConfig {

  public SetPositionCommandConfig(BlockPos firstPosition, BlockPos secondPosition,
      Item itemFilter, ItemStack bookAndQuill, ServerWorld serverWorld) {
    super(firstPosition, secondPosition, itemFilter, bookAndQuill, serverWorld);
  }
}
