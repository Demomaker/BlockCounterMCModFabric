package net.demomaker.blockcounter.util;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.text.Text;

public class TranslationText {

  public static final Map<String, String> TRANSLATED_TO_DEFAULT_MAP = new HashMap<>();
  public static Text title = Text.translatable("blockcounter.title");
  public static Text description = Text.translatable("blockcounter.description");
  public static Text numberOfBlocks = Text.translatable("blockcounter.numberOfBlocks");
  public static Text total = Text.translatable("blockcounter.total");
  public static Text commandCountBlocks = Text.translatable("blockcounter.command.countblocks");
  public static Text commandSetPosition = Text.translatable("blockcounter.command.setposition");
  public static Text commandHelp = Text.translatable("blockcounter.command.help");
  public static Text commandArgumentFirstPosition = Text.translatable("blockcounter.command.arguments.firstposition");
  public static Text commandArgumentSecondPosition = Text.translatable("blockcounter.command.arguments.secondposition");
  public static Text commandArgumentBlockName = Text.translatable("blockcounter.command.arguments.blockname");
  public static Text commandArgumentCommandName = Text.translatable("blockcounter.command.arguments.commandname");

  public static String getDefaultEnglishEquivalent(String commandName) {
    if(commandName == null) {
      return null;
    }
    return TRANSLATED_TO_DEFAULT_MAP.get(commandName);
  }
}
