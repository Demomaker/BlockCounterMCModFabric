package net.demomaker.blockcounter.facade;

import net.minecraft.entity.vehicle.CommandBlockMinecartEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class Entity {
  protected final net.minecraft.entity.Entity entity;
  public Entity(net.minecraft.entity.Entity entity) {
    this.entity = entity;
  }

  public boolean isNull() {
    return this.entity == null;
  }

  public boolean isInstanceOfPlayerEntity() {
    return this.entity instanceof ServerPlayerEntity;
  }

  public boolean isInstanceOfCommandBlockMinecartEntity() {
    return this.entity instanceof CommandBlockMinecartEntity;
  }
}
