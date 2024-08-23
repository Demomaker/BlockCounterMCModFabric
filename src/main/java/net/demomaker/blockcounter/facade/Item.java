package net.demomaker.blockcounter.facade;

import net.minecraft.item.Items;
import net.minecraft.text.Text;

public class Item {
  protected net.minecraft.item.Item item;
  public Item(net.minecraft.item.Item item) {
    this.item = item;
  }

  public Text getName() {
    return item.getName();
  }
  public boolean isWritableBook() { return item == Items.WRITABLE_BOOK; }
}
