package net.demomaker.blockcounter.adapter.item;

import java.util.ArrayList;
import java.util.List;
import net.demomaker.blockcounter.adapter.servercommand.ServerCommandContext;
import net.demomaker.blockcounter.util.NbtUtils;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.text.Text;

public record ItemStack(net.minecraft.item.ItemStack itemStack) {

  public Item getItem() {
    return new Item(this.itemStack().getItem());
  }

  public List<Text> getBookContent() {
    List<Text> pages = new ArrayList<>();

    CompoundTag nbt = NbtUtils.getNbt(this);
    if(getItem().isWritableBook()) {
      if(nbt != null && nbt.contains("pages", 9)) {
        ListTag pageList = nbt.getList("pages", 8);

        for(int i = 0; i < pageList.size(); i++) {
          String pageText = pageList.getString(i);
          Text text = Text.of(pageText);
          pages.add(text);
        }
      }
    }
    return pages;
  }

  public void setBookContent(List<Text> pages) {
    if(getItem().isWritableBook()) {
      CompoundTag nbt = NbtUtils.getOrCreateNbt(this);
      ListTag pageList = new ListTag();

      for(Text page: pages) {
        pageList.add(StringTag.of(page.getString()));
      }

      nbt.put("pages", pageList);
    }
  }

  public static ItemStackArgument getArgument(ServerCommandContext commandContext, String name) {
    return ItemStackArgumentType.getItemStackArgument(commandContext.commandContext(), name);
  }
}
