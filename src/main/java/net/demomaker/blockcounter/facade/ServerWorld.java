package net.demomaker.blockcounter.facade;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;

public class ServerWorld {
  protected net.minecraft.server.world.ServerWorld serverWorld;
  public ServerWorld(net.minecraft.server.world.ServerWorld serverWorld) {
    this.serverWorld = serverWorld;
  }

  public ServerWorld(ServerWorld serverWorld) {
    this.serverWorld = serverWorld.serverWorld;
  }

  public BlockState getBlockState(BlockPos blockPos) {
    return serverWorld.getBlockState(blockPos.blockPos);
  }

  public BlockEntity getBlockEntity(BlockPos blockPos) {
    return serverWorld.getBlockEntity(blockPos.blockPos);
  }
}
