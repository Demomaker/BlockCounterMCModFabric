package net.demomaker.blockcounter.util;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
public class UserMessageSender {
    private final ServerCommandSource serverCommand;
    private final StringBuilder message;

    public UserMessageSender(ServerCommandSource serverCommand) {
        this.serverCommand = serverCommand;
        this.message = new StringBuilder();
    }

    public UserMessageSender setTitle(String title) {
        this.message.append(title, 0, title.length());
        return this;
    }

    public UserMessageSender append(String text) {
        this.message.append(text);
        return this;
    }

    public void send() {
        String finalMessage = this.message.toString();
        this.serverCommand.sendFeedback(() -> Text.of(finalMessage), false);
    }
}