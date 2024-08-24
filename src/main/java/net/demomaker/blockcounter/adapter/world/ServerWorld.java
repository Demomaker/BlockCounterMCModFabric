package net.demomaker.blockcounter.adapter.world;

import net.demomaker.blockcounter.adapter.block.BlockPos;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;

public record ServerWorld(net.minecraft.server.world.ServerWorld serverWorld) {

  public BlockState getBlockState(BlockPos blockPos) {
    return serverWorld.getBlockState(blockPos.getBlockPos());
  }

  public BlockEntity getBlockEntity(BlockPos blockPos) {
    return serverWorld.getBlockEntity(blockPos.getBlockPos());
  }
}
