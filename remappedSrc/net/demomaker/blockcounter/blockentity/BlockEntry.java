package net.demomaker.blockcounter.blockentity;

public class BlockEntry {
  private String nameAndCountSeparator = " : ";
  private ItemName itemName;
  private BlockCount blockCount;
  public BlockEntry(ItemName itemName, BlockCount blockCount) {
    this.itemName = itemName;
    this.blockCount = blockCount;
  }

  @Override
  public String toString() {
    return itemName.getString() + nameAndCountSeparator + blockCount.toString();
  }
}
