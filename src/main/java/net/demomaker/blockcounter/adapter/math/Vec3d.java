package net.demomaker.blockcounter.adapter.math;

public record Vec3d(net.minecraft.util.math.Vec3d vec3d) {

  public static final Vec3d ZERO = new Vec3d(net.minecraft.util.math.Vec3d.ZERO);

  public boolean isNull() {
    return vec3d() == null;
  }

  public double getX() {
    return this.vec3d().getX();
  }

  public double getY() {
    return this.vec3d().getY();
  }

  public double getZ() {
    return this.vec3d().getZ();
  }
}
