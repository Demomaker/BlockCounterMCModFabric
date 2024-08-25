package net.demomaker.blockcounter.adapter.book;

import java.util.ArrayList;
import java.util.List;
import net.demomaker.blockcounter.adapter.entity.ServerPlayerEntity;
import net.demomaker.blockcounter.adapter.item.ItemStack;
import net.demomaker.blockcounter.adapter.servercommand.ServerCommandContext;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.WritableBookContentComponent;
import net.minecraft.text.RawFilteredPair;

public class BookWriter {
  public static final int MAX_PAGE_LENGTH = 200;
  private static int getIndexOfFirstEmptyPage(List<RawFilteredPair<String>> pages) {
    for (int i = 0; i < pages.size(); i++) {
      if(pages.get(i).raw().isEmpty()) {
        return i;
      }
    }
    return pages.size();
  }

  public static boolean Write(ItemStack book, String message) {
    if(book == null || !book.getItem().isWritableBook()) {
      return false;
    }

    var writableBookContent = book.getBookContent(DataComponentTypes.WRITABLE_BOOK_CONTENT);
    if (writableBookContent == null) {
      return false;
    }

    List<RawFilteredPair<String>> pages = writableBookContent.pages();
    int indexOfFirstEmptyPage = getIndexOfFirstEmptyPage(pages);
    List<RawFilteredPair<String>> newPages = new ArrayList<>(pages.subList(0, indexOfFirstEmptyPage));
    for (String page : splitString(message, MAX_PAGE_LENGTH)) {
      newPages.add(RawFilteredPair.of(page));
    }
    if(indexOfFirstEmptyPage < pages.size()) {
      newPages.addAll(pages.subList(indexOfFirstEmptyPage + 1, pages.size()));
    }
    book.setBookContent(DataComponentTypes.WRITABLE_BOOK_CONTENT, new WritableBookContentComponent(newPages));
    return true;
  }

  public static List<String> splitString(String input, int maxLength) {
    List<String> result = new ArrayList<>();

    if (input == null || maxLength <= 0) {
      return result;
    }

    int length = input.length();
    int offset = 0;

    while (offset < length) {
      int end = Math.min(offset + maxLength, length);

      int lastNewline = input.lastIndexOf('\n', end);

      if (lastNewline >= offset) {
        result.add(input.substring(offset, lastNewline + 1));
        offset = lastNewline + 1;
      } else {
        result.add(input.substring(offset, end));
        offset = end;
      }
    }

    return result;
  }


  public static void detectPlayersWithBookAndQuillAndWrite(ServerCommandContext context, String message) {
    for (ServerPlayerEntity player : context.getSource().getServer().getPlayerManager().getPlayerList().stream().map(
        ServerPlayerEntity::new).toList()) {
      ItemStack mainHandItemStack = player.getMainHandStack();
      ItemStack offHandItemStack = player.getOffHandStack();

      if(mainHandItemStack.getItem().isWritableBook()) {
        Write(mainHandItemStack, message);
      }
      if(offHandItemStack.getItem().isWritableBook()) {
        Write(offHandItemStack, message);
      }
    }
  }
}
