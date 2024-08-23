package net.demomaker.blockcounter.facade;

public class CommandBlockMinecartEntity extends Entity {
  protected net.minecraft.entity.vehicle.CommandBlockMinecartEntity commandBlockMinecartEntity;
  public CommandBlockMinecartEntity(net.minecraft.entity.vehicle.CommandBlockMinecartEntity commandBlockMinecartEntity) {
    super(commandBlockMinecartEntity);
    this.commandBlockMinecartEntity = commandBlockMinecartEntity;
  }

  public CommandBlockMinecartEntity(CommandBlockMinecartEntity commandBlockMinecartEntity) {
    super(commandBlockMinecartEntity.commandBlockMinecartEntity);
    this.commandBlockMinecartEntity = commandBlockMinecartEntity.commandBlockMinecartEntity;
  }

  public static CommandBlockMinecartEntity from(Entity entity) {
    return new CommandBlockMinecartEntity((net.minecraft.entity.vehicle.CommandBlockMinecartEntity) entity.entity);
  }

  public Vec3d getPos() {
    return new Vec3d(this.commandBlockMinecartEntity.getPos());
  }

  public boolean isNull() {
    return commandBlockMinecartEntity == null;
  }

}
