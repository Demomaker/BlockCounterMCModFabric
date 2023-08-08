package net.demomaker.blockcounter.algorithm;

public class CountingBoundaries {

  public int smallestX;
  public int smallestY;
  public int smallestZ;
  public int highestX;
  public int highestY;
  public int highestZ;

  public CountingBoundaries(int smallestX, int smallestY, int smallestZ, int highestX, int highestY,
      int highestZ) {
    this.smallestX = smallestX;
    this.smallestY = smallestY;
    this.smallestZ = smallestZ;
    this.highestX = highestX;
    this.highestY = highestY;
    this.highestZ = highestZ;
  }
}
