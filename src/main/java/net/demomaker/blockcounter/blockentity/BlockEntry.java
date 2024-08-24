package net.demomaker.blockcounter.blockentity;

public class BlockEntry {
  private final static String nameAndCountSeparator = " : ";
  private final ItemName itemName;
  private final BlockCount blockCount;
  public BlockEntry(ItemName itemName, BlockCount blockCount) {
    this.itemName = itemName;
    this.blockCount = blockCount;
  }

  @Override
  public String toString() {
    return itemName.getString() + nameAndCountSeparator + blockCount.toString();
  }

  public BlockCount getBlockCount() {
    return blockCount;
  }
}
