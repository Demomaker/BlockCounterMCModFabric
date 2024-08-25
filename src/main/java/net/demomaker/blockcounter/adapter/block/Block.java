package net.demomaker.blockcounter.adapter.block;

import net.minecraft.item.Item;

public record Block(net.minecraft.block.Block block) {

  public Item asItem() {
    return block().asItem();
  }
}
