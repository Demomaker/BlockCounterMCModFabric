package net.demomaker.blockcounter.adapter.entity;

import com.mojang.authlib.GameProfile;
import net.demomaker.blockcounter.adapter.math.Vec3d;
import net.demomaker.blockcounter.adapter.item.ItemStack;

public class ServerPlayerEntity extends Entity {
  private final net.minecraft.server.network.ServerPlayerEntity serverPlayerEntity;
  public ServerPlayerEntity(net.minecraft.server.network.ServerPlayerEntity serverPlayerEntity) {
    super(serverPlayerEntity);
    this.serverPlayerEntity = serverPlayerEntity;
  }

  public boolean isNull() {
    return getServerPlayerEntity() == null;
  }

  public Vec3d getPos() {
    return new Vec3d(getServerPlayerEntity().getPos());
  }

  public GameProfile getGameProfile() {
    return this.getServerPlayerEntity().getGameProfile();
  }

  public ItemStack getMainHandStack() {
    return new ItemStack(this.getServerPlayerEntity().getMainHandStack());
  }

  public ItemStack getOffHandStack() {
    return new ItemStack(this.getServerPlayerEntity().getOffHandStack());
  }

  public net.minecraft.server.network.ServerPlayerEntity getServerPlayerEntity() {
    return serverPlayerEntity;
  }
}
