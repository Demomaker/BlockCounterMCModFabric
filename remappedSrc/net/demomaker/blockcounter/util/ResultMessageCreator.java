package net.demomaker.blockcounter.util;

import net.demomaker.blockcounter.blockentity.BlockEntries;

public class ResultMessageCreator {
  public static String createMessage(BlockEntries blockEntries) {
    return "===================\n"
        + "Number Of Blocks : \n"
        + blockEntries.toString()
        + "===================";
  }
}
