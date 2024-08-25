package net.demomaker.blockcounter.util;

import net.demomaker.blockcounter.adapter.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class NbtUtils {
  public static NbtCompound getNbt(ItemStack itemStack) {
    return itemStack.itemStack().getTag();
  }

  public static void setNbt(ItemStack itemStack, NbtCompound newTag) {
    itemStack.itemStack().setTag(newTag);
  }

  public static NbtCompound getOrCreateNbt(ItemStack itemStack) {
    NbtCompound tag = getNbt(itemStack);
    if (tag == null) {
      tag = new NbtCompound();
      setNbt(itemStack, tag);
    }
    return tag;
  }

}
