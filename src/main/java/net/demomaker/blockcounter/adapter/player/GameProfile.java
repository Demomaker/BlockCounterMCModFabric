package net.demomaker.blockcounter.adapter.player;

import java.util.UUID;

public record GameProfile(com.mojang.authlib.GameProfile gameProfile) {

  public UUID getId() {
    return gameProfile().id();
  }

  public String getName() {
    return gameProfile().name();
  }
}
