package net.demomaker.blockcounter.facade;

import net.minecraft.component.ComponentType;
import net.minecraft.component.type.WritableBookContentComponent;

public class ItemStack {
  protected final net.minecraft.item.ItemStack itemStack;
  public ItemStack(net.minecraft.item.ItemStack itemStack) {
    this.itemStack = itemStack;
  }

  public Item getItem() {
    return new Item(this.itemStack.getItem());
  }

  public WritableBookContentComponent get(ComponentType<WritableBookContentComponent> writableBookContent) {
    return itemStack.get(writableBookContent);
  }

  public WritableBookContentComponent set(ComponentType<WritableBookContentComponent> writableBookContent,
      WritableBookContentComponent writableBookContentComponent) {
    return itemStack.set(writableBookContent, writableBookContentComponent);
  }
}
