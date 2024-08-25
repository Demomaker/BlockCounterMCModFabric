package net.demomaker.blockcounter.util;

import net.demomaker.blockcounter.blockentity.BlockEntries;
import net.demomaker.blockcounter.command.implementation.CommandCountBlocks;
import net.demomaker.blockcounter.command.implementation.CommandSetPosition;

public class ResultMessageCreator {
  public static String createMessage(BlockEntries blockEntries) {
    return "===[" + TranslationText.title.getString() + "]===\n"
        + TranslationText.numberOfBlocks.getString() + "\n"
        + blockEntries.toString()
        + "-------------------\n"
        + TranslationText.total + blockEntries.calculateTotal().toString() + "\n"
        + "===================\n";
  }

  public static String createOneLiner(BlockEntries blockEntries) {
    return "[" + TranslationText.title.getString() + "]||" + TranslationText.numberOfBlocks.getString() + blockEntries.toOneLinerString() + "|" + TranslationText.total + blockEntries.calculateTotal().toString() + "||";
  }

  public static String createHelpMessage(String commandName) {
    if(commandName == null) {
      return ""; //Full Help Message
    }

    switch(commandName) {
      case CommandSetPosition.COMMAND_NAME -> {
        return ""; //Command Set Position Help Message
      }

      case CommandCountBlocks.COMMAND_NAME -> {
        return ""; //Command Count Blocks Help Message
      }

      default -> {
        return ""; //Indicate unknown command
      }
    }
  }

  public static String createHelpMessageOneLiner(String commandName) {
    if(commandName == null) {
      return ""; //Full Help Message
    }

    switch(commandName) {
      case CommandSetPosition.COMMAND_NAME -> {
        return ""; //Command Set Position Help Message
      }

      case CommandCountBlocks.COMMAND_NAME -> {
        return ""; //Command Count Blocks Help Message
      }

      default -> {
        return ""; //Indicate unknown command
      }
    }
  }
}
