package net.demomaker.blockcounter.entity;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CommandBlockBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.CommandBlockMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityResolver {
  public static BlockEntity getCommandSourceBlockEntity(CommandContext<ServerCommandSource> context) {
    Vec3d position = context.getSource().getPosition();
    if (position == null)
      return null;
    World world = context.getSource().getWorld();
    return world.getBlockEntity(BlockPos.ofFloored(position));
  }

  public static ServerPlayerEntity getPlayerFromContext(CommandContext<ServerCommandSource> context) {
    return context.getSource().getPlayer();
  }

  public static CommandBlockBlockEntity getCommandBlockFromContext(CommandContext<ServerCommandSource> context) {
    BlockEntity blockEntity = getCommandSourceBlockEntity(context);

    if(blockEntity == null) {
      return null;
    }

    if (blockEntity instanceof CommandBlockBlockEntity) {
      return (CommandBlockBlockEntity) blockEntity;
    }
    return null;
  }

  public static CommandBlockMinecartEntity getCommandBlockMinecartFromContext(CommandContext<ServerCommandSource> context) {
    Entity entity = context.getSource().getEntity();
    if (entity == null) {
      return null;
    }
    if (entity instanceof CommandBlockMinecartEntity) {
      return (CommandBlockMinecartEntity) entity;
    }
    return null;
  }

  public static ItemStack getBookAndQuillFromContext(CommandContext<ServerCommandSource> context) {
    ServerPlayerEntity playerEntity = getPlayerFromContext(context);
    if (playerEntity == null) {
      return null;
    }
    ItemStack mainHandStack = playerEntity.getMainHandStack();
    ItemStack offHandStack = playerEntity.getOffHandStack();
    if(mainHandStack.getItem() == Items.WRITABLE_BOOK) {
      return mainHandStack;
    }
    if(offHandStack.getItem() == Items.WRITABLE_BOOK) {
      return offHandStack;
    }
    return null;
  }
}
