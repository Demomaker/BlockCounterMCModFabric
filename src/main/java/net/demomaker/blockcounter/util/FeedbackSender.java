package net.demomaker.blockcounter.util;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class FeedbackSender {
  public static void send(CommandContext<ServerCommandSource> context, String message) {
    context.getSource().sendFeedback(() -> Text.of(message), false);
  }

}
