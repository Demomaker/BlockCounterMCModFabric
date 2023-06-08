package net.demomaker.blockcounter.common;

import net.minecraft.util.StringIdentifiable;

public enum DesiredOrigin implements StringIdentifiable {
  HEAD,
  FEET,
  FACING_BLOCK,
  ;

  @Override
  public String asString() {
    return name();
  }
}
