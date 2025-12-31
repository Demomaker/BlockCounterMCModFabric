package net.demomaker.blockcounter.adapter.entity;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.demomaker.blockcounter.adapter.item.ItemStack;
import net.demomaker.blockcounter.adapter.math.Vec3d;
import net.minecraft.server.MinecraftServer;

public class ServerPlayerEntity extends Entity {
  private final net.minecraft.server.network.ServerPlayerEntity serverPlayerEntity;
  private ServerPlayerEntity() {
    super(null);
    this.serverPlayerEntity = null;
  }
  public ServerPlayerEntity(net.minecraft.server.network.ServerPlayerEntity serverPlayerEntity) {
    super(serverPlayerEntity);
    this.serverPlayerEntity = serverPlayerEntity;
  }

  public static ServerPlayerEntity getFrom(MinecraftServer server, UUID playerUUID) {
    if (server == null) {
      return new ServerPlayerEntity();
    }
    net.minecraft.server.network.ServerPlayerEntity serverPlayer = server.getPlayerManager().getPlayer(playerUUID);
    if (serverPlayer == null) {
      return new ServerPlayerEntity();
    }
    return new ServerPlayerEntity(serverPlayer);
  }

  public boolean isNull() {
    return getServerPlayerEntity() == null;
  }

  public Vec3d getPos() {
    if(isNull()) {
      return Vec3d.ZERO;
    }

    return new Vec3d(getServerPlayerEntity().getEntityPos());
  }

  public GameProfile getGameProfile() {
    if(isNull()) {
      return new GameProfile(UUID.randomUUID(), "");
    }
    return this.getServerPlayerEntity().getGameProfile();
  }

  public ItemStack getMainHandStack() {
    if(isNull()) {
      return new ItemStack(net.minecraft.item.ItemStack.EMPTY);
    }
    return new ItemStack(this.getServerPlayerEntity().getMainHandStack());
  }

  public ItemStack getOffHandStack() {
    if(isNull()) {
      return new ItemStack(net.minecraft.item.ItemStack.EMPTY);
    }
    return new ItemStack(this.getServerPlayerEntity().getOffHandStack());
  }

  public net.minecraft.server.network.ServerPlayerEntity getServerPlayerEntity() {
    return serverPlayerEntity;
  }

  public boolean isHoldingBook() {
    ItemStack mainHandStack = getMainHandStack();
    ItemStack offHandStack = getOffHandStack();

    return mainHandStack.getItem().isWritableBook() || offHandStack.getItem().isWritableBook();
  }
}
