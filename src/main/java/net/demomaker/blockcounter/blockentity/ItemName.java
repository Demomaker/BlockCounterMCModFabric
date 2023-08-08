package net.demomaker.blockcounter.blockentity;

public class ItemName {

  private final String name;

  public ItemName(String name) {
    this.name = name;
  }

  public String getString() {
    return name;
  }

  public boolean equals(String other) {
    return this.name.equals(other);
  }

  public boolean equals(ItemName other) {
    return this.name.equals(other.name);
  }
}
