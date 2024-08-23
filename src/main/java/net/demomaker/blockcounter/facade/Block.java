package net.demomaker.blockcounter.facade;

import net.minecraft.item.Item;

public class Block {

  protected net.minecraft.block.Block block;
  public Block(net.minecraft.block.Block block) {
    this.block = block;
  }

  public Block(Block block) {
    this.block = block.block;
  }

  public Item asItem() {
    return block.asItem();
  }
}
