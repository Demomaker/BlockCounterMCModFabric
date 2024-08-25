package net.demomaker.blockcounter.adapter.entity;

import net.minecraft.entity.vehicle.CommandBlockMinecartEntity;

public class Entity {
  private final net.minecraft.entity.Entity entity;
  public Entity(net.minecraft.entity.Entity entity) {
    this.entity = entity;
  }

  public boolean isNull() {
    return this.getEntity() == null;
  }

  public boolean isInstanceOfCommandBlockMinecartEntity() {
    return this.getEntity() instanceof CommandBlockMinecartEntity;
  }

  public net.minecraft.entity.Entity getEntity() {
    return entity;
  }
}
