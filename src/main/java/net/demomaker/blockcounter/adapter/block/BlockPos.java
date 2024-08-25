package net.demomaker.blockcounter.adapter.block;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
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

  public static BlockPos getBlockPos(ServerCommandContext context, String argumentName)
      throws CommandSyntaxException {
    return new BlockPos(BlockPosArgumentType.getBlockPos(context.commandContext(), argumentName));
  }

  public static BlockPos ofFloored(Vec3d position) {
    int flooredX = (int) Math.floor(position.getX());
    int flooredY = (int) Math.floor(position.getY());
    int flooredZ = (int) Math.floor(position.getZ());
    net.minecraft.util.math.BlockPos blockPos1 = new net.minecraft.util.math.BlockPos(flooredX, flooredY, flooredZ);
    return new BlockPos(blockPos1);
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
    return this.getBlockPos() == null ? Vec3d.ZERO : new Vec3d(getBlockCenterPos(this).vec3d());
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

  private static Vec3d getBlockCenterPos(BlockPos pos) {
    double x = pos.getX() + 0.5;
    double y = pos.getY() + 0.5;
    double z = pos.getZ() + 0.5;
    return new Vec3d(new net.minecraft.util.math.Vec3d(x, y, z));
  }
}
