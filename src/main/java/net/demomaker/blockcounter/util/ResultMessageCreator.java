package net.demomaker.blockcounter.util;

import net.demomaker.blockcounter.blockentity.BlockEntries;

public class ResultMessageCreator {
  public static String createMessage(BlockEntries blockEntries) {
    return "===================\n"
        + "Number Of Blocks : \n"
        + blockEntries.toString()
        + "-------------------\n"
        + "Total : " + blockEntries.calculateTotal().toString() + "\n"
        + "===================";
  }

  public static String createOneLiner(BlockEntries blockEntries) {
    return "||Number Of Blocks : " + blockEntries.toOneLinerString() + "|Total : " + blockEntries.calculateTotal().toString() + "||";
  }
}
