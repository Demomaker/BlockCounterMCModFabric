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
        + TranslationText.total.getString() + blockEntries.calculateTotal().toString() + "\n"
        + "===================\n";
  }

  public static String createOneLiner(BlockEntries blockEntries) {
    return "[" + TranslationText.title.getString() + "]||" + TranslationText.numberOfBlocks.getString() + blockEntries.toOneLinerString() + "|" + TranslationText.total.getString() + blockEntries.calculateTotal().toString() + "||";
  }

  public static String createHelpMessage(String commandName) {
    if(commandName == null) {
      return TranslationText.commandHelpMessageCommon.getString();
    }

    switch(commandName) {
      case CommandSetPosition.COMMAND_NAME -> {
        return TranslationText.commandHelpMessageSetPosition.getString();
      }

      case CommandCountBlocks.COMMAND_NAME -> {
        return TranslationText.commandHelpMessageCountBlocks.getString();
      }

      default -> {
        return TranslationText.commandHelpMessageCommon.getString();
      }
    }
  }

  public static String createHelpMessageOneLiner(String commandName) {
    if(commandName == null) {
      return TranslationText.commandHelpMessageOneLinerCommon.getString();
    }

    switch(commandName) {
      case CommandSetPosition.COMMAND_NAME -> {
        return TranslationText.commandHelpMessageOneLinerSetPosition.getString();
      }

      case CommandCountBlocks.COMMAND_NAME -> {
        return TranslationText.commandHelpMessageOneLinerCountBlocks.getString();
      }

      default -> {
        return TranslationText.commandHelpMessageOneLinerCommon.getString();
      }
    }
  }
}
