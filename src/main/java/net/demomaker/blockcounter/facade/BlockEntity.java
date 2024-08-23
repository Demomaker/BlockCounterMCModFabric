package net.demomaker.blockcounter.facade;

import net.minecraft.block.entity.CommandBlockBlockEntity;

public class BlockEntity {
  protected final net.minecraft.block.entity.BlockEntity blockEntity;
  public BlockEntity(net.minecraft.block.entity.BlockEntity blockEntity) {
    this.blockEntity = blockEntity;
  }

  public BlockEntity() {
    this.blockEntity = null;
  }

  public boolean isNull() {
    return this.blockEntity == null;
  }

  public boolean isInstanceOfCommandBlockBlockEntity() {
    return this.blockEntity instanceof CommandBlockBlockEntity;
  }

}
