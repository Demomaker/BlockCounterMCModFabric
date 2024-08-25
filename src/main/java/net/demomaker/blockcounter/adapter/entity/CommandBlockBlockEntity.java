package net.demomaker.blockcounter.adapter.entity;

import net.demomaker.blockcounter.adapter.block.BlockPos;

public class CommandBlockBlockEntity extends BlockEntity {
  private final net.minecraft.block.entity.CommandBlockBlockEntity commandBlockBlockEntity;
  public CommandBlockBlockEntity(net.minecraft.block.entity.CommandBlockBlockEntity commandBlockBlockEntity) {
    super(commandBlockBlockEntity);
    this.commandBlockBlockEntity = commandBlockBlockEntity;
  }

  public CommandBlockBlockEntity(CommandBlockBlockEntity commandBlockFromContext) {
    super(commandBlockFromContext.getCommandBlockBlockEntity());
    this.commandBlockBlockEntity = commandBlockFromContext.getCommandBlockBlockEntity();
  }

  public CommandBlockBlockEntity() {
    super(null);
    this.commandBlockBlockEntity = null;
  }

  public static CommandBlockBlockEntity from(BlockEntity blockEntity) {
    return new CommandBlockBlockEntity((net.minecraft.block.entity.CommandBlockBlockEntity) blockEntity.getBlockEntity());
  }

  public BlockPos getPos() {
    if(getCommandBlockBlockEntity() == null) {
      return new BlockPos();
    }
    return new BlockPos(getCommandBlockBlockEntity().getPos());
  }

  public boolean isNull() {
    return getCommandBlockBlockEntity() == null;
  }

  public net.minecraft.block.entity.CommandBlockBlockEntity getCommandBlockBlockEntity() {
    return commandBlockBlockEntity;
  }
}
