package net.demomaker.blockcounter.facade;

import net.minecraft.command.argument.BlockPosArgumentType;

public class BlockPos {
  protected final net.minecraft.util.math.BlockPos blockPos;
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
    return new BlockPos(BlockPosArgumentType.getBlockPos(context.commandContext, argumentName));
  }

  public static BlockPos ofFloored(Vec3d position) {
    return new BlockPos(net.minecraft.util.math.BlockPos.ofFloored(position.vec3d));
  }

  public int getX() {
    return this.blockPos == null ? 0 : this.blockPos.getX();
  }

  public int getY() {
    return this.blockPos == null ? 0 : this.blockPos.getY();
  }

  public int getZ() {
    return this.blockPos == null ? 0 : this.blockPos.getZ();
  }

  public Vec3d toCenterPos() {
    return this.blockPos == null ? Vec3d.ZERO : new Vec3d(this.blockPos.toCenterPos());
  }
}
