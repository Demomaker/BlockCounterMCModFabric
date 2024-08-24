package net.demomaker.blockcounter.adapter.item;

import net.minecraft.item.Items;
import net.minecraft.text.Text;

public record Item(net.minecraft.item.Item item) {

  public Text getName() {
    return item().getName();
  }

  public boolean isWritableBook() {
    return item() == Items.WRITABLE_BOOK;
  }
}
