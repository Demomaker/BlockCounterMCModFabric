package net.demomaker.blockcounter.facade;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class ServerCommandSource {
  protected final net.minecraft.server.command.ServerCommandSource serverCommandSource;
  public ServerCommandSource(net.minecraft.server.command.ServerCommandSource serverCommandSource) {
    this.serverCommandSource = serverCommandSource;
  }

  public Vec3d getPosition() {
    return this.serverCommandSource.getPosition();
  }

  public ServerWorld getWorld() {
    return new ServerWorld(this.serverCommandSource.getWorld());
  }

  public ServerPlayerEntity getPlayer() {
    return this.serverCommandSource.getPlayer();
  }

  public String getName() {
    return this.serverCommandSource.getName();
  }

  public MinecraftServer getServer() {
    return this.serverCommandSource.getServer();
  }

  public void sendFeedback(String message, boolean broadcastToOps) {
    this.serverCommandSource.sendFeedback(() -> Text.of(message), broadcastToOps);
  }
}
