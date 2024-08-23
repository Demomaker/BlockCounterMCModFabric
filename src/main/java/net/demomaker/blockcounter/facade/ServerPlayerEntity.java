package net.demomaker.blockcounter.facade;

import com.mojang.authlib.GameProfile;

public class ServerPlayerEntity extends Entity {
  protected final net.minecraft.server.network.ServerPlayerEntity serverPlayerEntity;
  public ServerPlayerEntity(net.minecraft.server.network.ServerPlayerEntity serverPlayerEntity) {
    super(serverPlayerEntity);
    this.serverPlayerEntity = serverPlayerEntity;
  }

  public boolean isNull() {
    return serverPlayerEntity == null;
  }

  public Vec3d getPos() {
    return new Vec3d(serverPlayerEntity.getPos());
  }

  public GameProfile getGameProfile() {
    return this.serverPlayerEntity.getGameProfile();
  }

  public ItemStack getMainHandStack() {
    return new ItemStack(this.serverPlayerEntity.getMainHandStack());
  }

  public ItemStack getOffHandStack() {
    return new ItemStack(this.serverPlayerEntity.getOffHandStack());
  }
}
