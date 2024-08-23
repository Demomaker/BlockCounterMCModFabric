package net.demomaker.blockcounter.util;

import net.demomaker.blockcounter.facade.ServerCommandContext;
import net.demomaker.blockcounter.facade.ServerCommandSource;

public class FeedbackSender {
  public static void send(ServerCommandContext<ServerCommandSource> context, String message) {
    context.getSource().sendFeedback(message, false);
  }

}
