package net.demomaker.blockcounter.util;

import com.mojang.brigadier.context.CommandContext;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.WritableBookContentComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.RawFilteredPair;
import net.minecraft.util.Hand;

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

  public static void Write(ItemStack book, String message) {
    if(book == null || book.getItem() != Items.WRITABLE_BOOK) {
      return;
    }

    var writableBookContent = book.get(DataComponentTypes.WRITABLE_BOOK_CONTENT);
    if (writableBookContent == null) {
      return;
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
    book.set(DataComponentTypes.WRITABLE_BOOK_CONTENT, new WritableBookContentComponent(newPages));
  }

  public static List<String> splitString(String input, int maxLength) {
    List<String> result = new ArrayList<>();

    if (input == null || maxLength <= 0) {
      return result;
    }

    int length = input.length();
    for (int i = 0; i < length; i += maxLength) {
      String substring = input.substring(i, Math.min(length, i + maxLength));
      result.add(substring);
    }

    return result;
  }

  public static void detectPlayersWithBookAndQuillAndWrite(CommandContext<ServerCommandSource> context, String message) {
    for (ServerPlayerEntity player : context.getSource().getServer().getPlayerManager().getPlayerList()) {
      if (isHoldingBookAndQuill(player, Hand.MAIN_HAND)) {
        Write(player.getMainHandStack(), message);
      }

      if (isHoldingBookAndQuill(player, Hand.OFF_HAND)) {
        Write(player.getOffHandStack(), message);
      }
    }
  }

  private static boolean isHoldingBookAndQuill(ServerPlayerEntity player, Hand hand) {
    ItemStack itemStack = player.getStackInHand(hand);
    return itemStack.getItem() == Items.WRITABLE_BOOK;
  }
}
