package net.demomaker.blockcounter.entity;

import net.demomaker.blockcounter.adapter.entity.BlockEntity;
import net.demomaker.blockcounter.adapter.block.BlockPos;
import net.demomaker.blockcounter.adapter.entity.CommandBlockBlockEntity;
import net.demomaker.blockcounter.adapter.entity.CommandBlockMinecartEntity;
import net.demomaker.blockcounter.adapter.entity.Entity;
import net.demomaker.blockcounter.adapter.item.ItemStack;
import net.demomaker.blockcounter.adapter.servercommand.ServerCommandContext;
import net.demomaker.blockcounter.adapter.entity.ServerPlayerEntity;
import net.demomaker.blockcounter.adapter.world.ServerWorld;
import net.demomaker.blockcounter.adapter.math.Vec3d;


public class EntityResolver {
  public static BlockEntity getCommandSourceBlockEntity(ServerCommandContext context) {
    Vec3d position = new Vec3d(context.getSource().getPosition());
    if (position.isNull())
      return new BlockEntity();
    ServerWorld world = new ServerWorld(context.getSource().getWorld());
    return new BlockEntity(world.getBlockEntity(BlockPos.ofFloored(position)));
  }

  public static ServerPlayerEntity getPlayerFromContext(ServerCommandContext context) {
    return new ServerPlayerEntity(context.getSource().getPlayer());
  }

  public static CommandBlockBlockEntity getCommandBlockFromContext(ServerCommandContext context) {
    BlockEntity blockEntity = getCommandSourceBlockEntity(context);

    if(blockEntity.isNull()) {
      return new CommandBlockBlockEntity();
    }

    if (blockEntity.isInstanceOfCommandBlockBlockEntity()) {
      return CommandBlockBlockEntity.from(blockEntity);
    }
    return new CommandBlockBlockEntity();
  }

  public static CommandBlockMinecartEntity getCommandBlockMinecartFromContext(ServerCommandContext context) {
    Entity entity = new Entity(context.getSource().getEntity());
    if (entity.isNull()) {
      return new CommandBlockMinecartEntity();
    }
    if (entity.isInstanceOfCommandBlockMinecartEntity()) {
      return CommandBlockMinecartEntity.from(entity);
    }
    return new CommandBlockMinecartEntity();
  }

  public static ItemStack getBookAndQuillFromContext(ServerCommandContext context) {
    ServerPlayerEntity playerEntity = getPlayerFromContext(context);
    if (playerEntity.isNull()) {
      return null;
    }
    ItemStack mainHandStack = playerEntity.getMainHandStack();
    ItemStack offHandStack = playerEntity.getOffHandStack();
    if(mainHandStack.getItem().isWritableBook()) {
      return mainHandStack;
    }
    if(offHandStack.getItem().isWritableBook()) {
      return offHandStack;
    }
    return null;
  }
}
