package net.demomaker.blockcounter.adapter.item;

import net.demomaker.blockcounter.adapter.servercommand.ServerCommandContext;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.WritableBookContentComponent;
import net.minecraft.text.RawFilteredPair;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public record ItemStack(net.minecraft.item.ItemStack itemStack) {

  public Item getItem() {
    return new Item(this.itemStack().getItem());
  }

  public List<Text> getBookContent() {
    List<Text> pages = new ArrayList<>();

    WritableBookContentComponent bookContentComponent = itemStack().get(DataComponentTypes.WRITABLE_BOOK_CONTENT);
    if(getItem().isWritableBook()) {
      if(bookContentComponent != null) {
        List<RawFilteredPair<String>> initialPages = bookContentComponent.pages();

        for (RawFilteredPair<String> initialPage : initialPages) {
          String pageText = initialPage.get(false);
          pages.add(Text.of(pageText));
        }
      }
    }
    return pages;
  }

  public void setBookContent(List<Text> pages) {
    if(getItem().isWritableBook()) {
      List<RawFilteredPair<String>> pageList = new ArrayList<>();

      for(Text page: pages) {
        pageList.add(RawFilteredPair.of(page.getString()));
      }

      WritableBookContentComponent newBookContentComponent = new WritableBookContentComponent(pageList);
      itemStack().set(DataComponentTypes.WRITABLE_BOOK_CONTENT, newBookContentComponent);
    }
  }

  public static ItemStackArgument getArgument(ServerCommandContext commandContext, String name) {
    return ItemStackArgumentType.getItemStackArgument(commandContext.commandContext(), name);
  }
}
