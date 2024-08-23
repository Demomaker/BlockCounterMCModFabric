package net.demomaker.blockcounter.facade;

public class CommandBlockBlockEntity extends BlockEntity {
  protected final net.minecraft.block.entity.CommandBlockBlockEntity commandBlockBlockEntity;
  public CommandBlockBlockEntity(net.minecraft.block.entity.CommandBlockBlockEntity commandBlockBlockEntity) {
    super(commandBlockBlockEntity);
    this.commandBlockBlockEntity = commandBlockBlockEntity;
  }

  public CommandBlockBlockEntity(CommandBlockBlockEntity commandBlockFromContext) {
    super(commandBlockFromContext.commandBlockBlockEntity);
    this.commandBlockBlockEntity = commandBlockFromContext.commandBlockBlockEntity;
  }

  public CommandBlockBlockEntity() {
    super(null);
    this.commandBlockBlockEntity = null;
  }

  public static CommandBlockBlockEntity from(BlockEntity blockEntity) {
    return new CommandBlockBlockEntity((net.minecraft.block.entity.CommandBlockBlockEntity) blockEntity.blockEntity);
  }

  public BlockPos getPos() {
    if(commandBlockBlockEntity == null) {
      return new BlockPos();
    }
    return new BlockPos(commandBlockBlockEntity.getPos());
  }

  public boolean isNull() {
    return commandBlockBlockEntity == null;
  }
}
