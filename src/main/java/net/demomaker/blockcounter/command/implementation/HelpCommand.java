package net.demomaker.blockcounter.command.implementation;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.demomaker.blockcounter.adapter.argumentbuilder.ServerCommandArgumentBuilder;
import net.demomaker.blockcounter.adapter.servercommand.ServerCommand;
import net.demomaker.blockcounter.adapter.servercommand.ServerCommandContext;
import net.demomaker.blockcounter.adapter.servercommand.ServerCommandRegistryAccess;
import net.demomaker.blockcounter.command.config.SourceType;
import net.demomaker.blockcounter.identity.CommandExecutionConfig;
import net.demomaker.blockcounter.identity.CommandExecutionConfigResolver;
import net.demomaker.blockcounter.main.ModCommands;
import net.demomaker.blockcounter.util.ResultMessageCreator;
import net.demomaker.blockcounter.util.TranslationText;

public class HelpCommand extends ServerCommand {
  public static final String DEFAULT_COMMAND_NAME = "help";
  public static final String COMMAND_NAME = "command_name";
  public static ServerCommandArgumentBuilder getDefaultServerCommandFormat(ServerCommandRegistryAccess registryAccess, ServerCommand Command) {
    return new ServerCommandArgumentBuilder()
        .beginCommand(DEFAULT_COMMAND_NAME, Command)
        .addStringArgument(COMMAND_NAME, ModCommands.HELP_COMMAND)
        .endCommand();
  }

  public static ServerCommandArgumentBuilder getTranslatedServerCommandFormat(ServerCommandRegistryAccess registryAccess, ServerCommand Command) {
    TranslationText.TRANSLATED_TO_DEFAULT_MAP.put(TranslationText.commandHelp.getString(), DEFAULT_COMMAND_NAME);

    return new ServerCommandArgumentBuilder()
        .beginCommand(TranslationText.commandHelp.getString(), Command)
        .addStringArgument(TranslationText.commandArgumentCommandName.getString(), ModCommands.HELP_COMMAND)
        .endCommand();
  }

  @Override
  public int run(ServerCommandContext commandContext) {
    String commandName = null;
    try {
      commandName = StringArgumentType.getString(commandContext.commandContext(), COMMAND_NAME);
    } catch (Exception ignored) {}

    try {
      if (commandName == null) {
        commandName = StringArgumentType.getString(commandContext.commandContext(), TranslationText.commandArgumentCommandName.getString());
        commandName = TranslationText.getDefaultEnglishEquivalent(commandName);
      }
    } catch (Exception ignored) {}

    String resultMessage = ResultMessageCreator.createHelpMessage(commandName);

    try {
      CommandExecutionConfig commandExecutionConfig = CommandExecutionConfigResolver.getConfigFromContext(commandContext);
      if(commandExecutionConfig.getCommandConfigs().getSourceType().equals(SourceType.COMMAND_BLOCK)) {
        resultMessage = ResultMessageCreator.createHelpMessageOneLiner(commandName);
      }
    }
    catch (Exception ignored) {}

    commandContext.sendFeedback(resultMessage, false);
    return 0;
  }
}
