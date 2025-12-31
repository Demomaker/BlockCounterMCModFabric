package net.demomaker.blockcounter.adapter.entity;

import net.demomaker.blockcounter.adapter.math.Vec3d;

public class CommandBlockMinecartEntity extends Entity {
  private final net.minecraft.entity.vehicle.CommandBlockMinecartEntity commandBlockMinecartEntity;

  public CommandBlockMinecartEntity() {
    super(null);
    this.commandBlockMinecartEntity = null;
  }

  public CommandBlockMinecartEntity(net.minecraft.entity.vehicle.CommandBlockMinecartEntity commandBlockMinecartEntity) {
    super(commandBlockMinecartEntity);
    this.commandBlockMinecartEntity = commandBlockMinecartEntity;
  }

  public CommandBlockMinecartEntity(CommandBlockMinecartEntity commandBlockMinecartEntity) {
    super(commandBlockMinecartEntity.getCommandBlockMinecartEntity());
    this.commandBlockMinecartEntity = commandBlockMinecartEntity.getCommandBlockMinecartEntity();
  }

  public static CommandBlockMinecartEntity from(Entity entity) {
    return new CommandBlockMinecartEntity((net.minecraft.entity.vehicle.CommandBlockMinecartEntity) entity.getEntity());
  }

  public Vec3d getPos() {
    return new Vec3d(this.getCommandBlockMinecartEntity().getEntityPos());
  }

  public boolean isNull() {
    return getCommandBlockMinecartEntity() == null;
  }

  public net.minecraft.entity.vehicle.CommandBlockMinecartEntity getCommandBlockMinecartEntity() {
    return commandBlockMinecartEntity;
  }
}
