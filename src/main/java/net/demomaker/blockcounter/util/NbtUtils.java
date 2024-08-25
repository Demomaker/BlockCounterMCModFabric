package net.demomaker.blockcounter.util;

import net.demomaker.blockcounter.adapter.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

public class NbtUtils {
  public static CompoundTag getNbt(ItemStack itemStack) {
    return itemStack.itemStack().getTag();
  }

  public static void setNbt(ItemStack itemStack, CompoundTag newTag) {
    itemStack.itemStack().setTag(newTag);
  }

  public static CompoundTag getOrCreateNbt(ItemStack itemStack) {
    CompoundTag tag = getNbt(itemStack);
    if (tag == null) {
      tag = new CompoundTag();
      setNbt(itemStack, tag);
    }
    return tag;
  }

}
