package net.demomaker.blockcounter.util;

public class ResultMessageCreator {
  public static String createMessage(String counts) {
    StringBuilder chatMessage = new StringBuilder();
    chatMessage.append("===================\n");
    chatMessage.append("Number Of Blocks : \n");
    chatMessage.append(counts);
    chatMessage.append("===================");
    return chatMessage.toString();
  }
}
