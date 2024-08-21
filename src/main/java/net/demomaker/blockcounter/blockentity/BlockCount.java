package net.demomaker.blockcounter.blockentity;

public class BlockCount {

  private Integer count = 1;

  public BlockCount increment() {
    count++;
    return this;
  }

  @Override
  public String toString() {
    return count.toString();
  }

  public Integer getValue() { return count; }
}
