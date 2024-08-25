package net.demomaker.blockcounter.util;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class TranslationText {

  public static final Map<String, String> TRANSLATED_TO_DEFAULT_MAP = new HashMap<>();
  public static Text title = createTranslatableText("blockcounter.title");
  public static Text description = createTranslatableText("blockcounter.description");
  public static Text numberOfBlocks = createTranslatableText("blockcounter.numberOfBlocks");
  public static Text total = createTranslatableText("blockcounter.total");
  public static Text commandPreCommand = createTranslatableText("blockcounter.command.precommand");
  public static Text commandCountBlocks = createTranslatableText("blockcounter.command.countblocks");
  public static Text commandSetPosition = createTranslatableText("blockcounter.command.setposition");
  public static Text commandHelp = createTranslatableText("blockcounter.command.help");
  public static Text commandArgumentFirstPosition = createTranslatableText("blockcounter.command.arguments.firstposition");
  public static Text commandArgumentSecondPosition = createTranslatableText("blockcounter.command.arguments.secondposition");
  public static Text commandArgumentBlockName = createTranslatableText("blockcounter.command.arguments.blockname");
  public static Text commandArgumentCommandName = createTranslatableText("blockcounter.command.arguments.commandname");
  public static Text commandHelpMessageCountBlocks = createTranslatableText("blockcounter.commandhelpmessage.countblocks");
  public static Text commandHelpMessageSetPosition = createTranslatableText("blockcounter.commandhelpmessage.setposition");
  public static Text commandHelpMessageCommon = createTranslatableText("blockcounter.commandhelpmessage.common");
  public static Text commandHelpMessageOneLinerCountBlocks = createTranslatableText("blockcounter.commandhelpmessageoneliner.countblocks");
  public static Text commandHelpMessageOneLinerSetPosition = createTranslatableText("blockcounter.commandhelpmessageoneliner.setposition");
  public static Text commandHelpMessageOneLinerCommon = createTranslatableText("blockcounter.commandhelpmessageoneliner.common");

  public static String getDefaultEnglishEquivalent(String commandName) {
    if(commandName == null) {
      return null;
    }
    return TRANSLATED_TO_DEFAULT_MAP.get(commandName);
  }

  private static Text createTranslatableText(String key) {
    return new TranslatableText(key);
  }
}
