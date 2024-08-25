package net.demomaker.blockcounter.blockentity;

import java.util.List;

public class BlockEntries {
  private static final String newLine = "\n";
  private static final String verticalSeperation = ";";
  private final List<BlockEntry> blockEntries;
  public BlockEntries(List<BlockEntry> blockEntries) {
    this.blockEntries = blockEntries;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    for (BlockEntry blockEntry :
        blockEntries) {
      stringBuilder.append(blockEntry.toString()).append(newLine);
    }
    return stringBuilder.toString();
  }

  public String toOneLinerString() {
    StringBuilder stringBuilder = new StringBuilder();
    for (BlockEntry blockEntry :
        blockEntries) {
      stringBuilder.append(blockEntry.toString()).append(verticalSeperation);
    }
    return stringBuilder.toString();
  }

  public Integer calculateTotal() {
    int total = 0;
    for (BlockEntry blockEntry :
    blockEntries) {
      total += blockEntry.getBlockCount().getValue();
    }
    return total;
  }

}
