package net.demomaker.blockcounter.adapter.item;

import java.util.ArrayList;
import java.util.List;
import net.demomaker.blockcounter.adapter.servercommand.ServerCommandContext;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;

public record ItemStack(net.minecraft.item.ItemStack itemStack) {

  public Item getItem() {
    return new Item(this.itemStack().getItem());
  }

  public List<Text> getBookContent() {
    List<Text> pages = new ArrayList<>();

    NbtCompound nbt = itemStack().getNbt();
    if(getItem().isWritableBook()) {
      if(nbt != null && nbt.contains("pages", 9)) {
        NbtList pageList = nbt.getList("pages", 8);

        for(int i = 0; i < pageList.size(); i++) {
          String pageText = pageList.getString(i);
          pages.add(Text.of(pageText));
        }
      }
    }
    return pages;
  }

  public void setBookContent(List<Text> pages) {
    if(getItem().isWritableBook()) {
      NbtCompound nbt = itemStack().getOrCreateNbt();
      NbtList pageList = new NbtList();

      for(Text page: pages) {
        pageList.add(NbtString.of(page.getString()));
      }

      nbt.put("pages", pageList);
    }
  }

  public static ItemStackArgument getArgument(ServerCommandContext commandContext, String name) {
    return ItemStackArgumentType.getItemStackArgument(commandContext.commandContext(), name);
  }
}
