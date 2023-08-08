package net.demomaker.blockcounter.blockentity;

import java.util.List;

public class BlockEntries {
  private final String newLine = "\n";
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

}
