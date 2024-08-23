package net.demomaker.blockcounter.facade;

public class Vec3d {

  public static final Vec3d ZERO = new Vec3d(net.minecraft.util.math.Vec3d.ZERO);
  protected net.minecraft.util.math.Vec3d vec3d;
  public Vec3d(net.minecraft.util.math.Vec3d vec3d) {
    this.vec3d = vec3d;
  }

  public boolean isNull() {
    return vec3d == null;
  }

  public double getX() {
    return this.vec3d.getX();
  }

  public double getY() {
    return this.vec3d.getY();
  }

  public double getZ() {
    return this.vec3d.getZ();
  }
}
