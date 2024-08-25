package net.demomaker.blockcounter.adapter.block;

import net.demomaker.blockcounter.adapter.servercommand.ServerCommandContext;
import net.demomaker.blockcounter.adapter.math.Vec3d;
import net.minecraft.command.argument.BlockPosArgumentType;

public class BlockPos {
  private final net.minecraft.util.math.BlockPos blockPos;
  public BlockPos(net.minecraft.util.math.BlockPos blockPos) {
    this.blockPos = blockPos;
  }

  public BlockPos(int x, int y, int z) {
    this.blockPos = new net.minecraft.util.math.BlockPos(x, y, z);
  }

  public BlockPos() {
    this.blockPos = null;
  }

  public static BlockPos getBlockPos(ServerCommandContext context, String argumentName) {
    return new BlockPos(BlockPosArgumentType.getBlockPos(context.commandContext(), argumentName));
  }

  public static BlockPos ofFloored(Vec3d position) {
    return new BlockPos(net.minecraft.util.math.BlockPos.ofFloored(position.vec3d()));
  }

  public int getX() {
    return this.getBlockPos() == null ? 0 : this.getBlockPos().getX();
  }

  public int getY() {
    return this.getBlockPos() == null ? 0 : this.getBlockPos().getY();
  }

  public int getZ() {
    return this.getBlockPos() == null ? 0 : this.getBlockPos().getZ();
  }

  public Vec3d toCenterPos() {
    return this.getBlockPos() == null ? Vec3d.ZERO : new Vec3d(this.getBlockPos().toCenterPos());
  }

  public net.minecraft.util.math.BlockPos getBlockPos() {
    return blockPos;
  }


  @Override
  public boolean equals(Object o) {
    if(o.getClass() == this.getClass()) {
      BlockPos other = (BlockPos) o;
      return (other.blockPos == null && this.blockPos == null) || (other.blockPos != null && other.blockPos.equals(this.blockPos));
    } else {
      return false;
    }
  }

  public boolean isNull() {
    return this.blockPos == null;
  }
}
