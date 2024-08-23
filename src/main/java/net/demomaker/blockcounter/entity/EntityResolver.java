package net.demomaker.blockcounter.entity;

import net.demomaker.blockcounter.facade.BlockEntity;
import net.demomaker.blockcounter.facade.BlockPos;
import net.demomaker.blockcounter.facade.CommandBlockBlockEntity;
import net.demomaker.blockcounter.facade.CommandBlockMinecartEntity;
import net.demomaker.blockcounter.facade.Entity;
import net.demomaker.blockcounter.facade.ItemStack;
import net.demomaker.blockcounter.facade.ServerCommandContext;
import net.demomaker.blockcounter.facade.ServerPlayerEntity;
import net.demomaker.blockcounter.facade.ServerWorld;
import net.demomaker.blockcounter.facade.Vec3d;


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
      return null;
    }
    if (entity.isInstanceOfCommandBlockMinecartEntity()) {
      return CommandBlockMinecartEntity.from(entity);
    }
    return null;
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
