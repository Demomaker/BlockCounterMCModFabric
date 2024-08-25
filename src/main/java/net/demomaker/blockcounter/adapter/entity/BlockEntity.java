package net.demomaker.blockcounter.adapter.entity;

import net.minecraft.block.entity.CommandBlockBlockEntity;

public class BlockEntity {
  private final net.minecraft.block.entity.BlockEntity blockEntity;
  public BlockEntity(net.minecraft.block.entity.BlockEntity blockEntity) {
    this.blockEntity = blockEntity;
  }

  public BlockEntity() {
    this.blockEntity = null;
  }

  public boolean isNull() {
    return this.getBlockEntity() == null;
  }

  public boolean isInstanceOfCommandBlockBlockEntity() {
    return this.getBlockEntity() instanceof CommandBlockBlockEntity;
  }

  public net.minecraft.block.entity.BlockEntity getBlockEntity() {
    return blockEntity;
  }
}
