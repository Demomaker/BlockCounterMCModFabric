package net.demomaker.blockcounter.common;

import static net.minecraft.server.command.CommandManager.argument;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.demomaker.blockcounter.util.ResultMessageCreator;
import net.demomaker.blockcounter.util.UserMessageSender;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.CommandManager.RegistrationEnvironment;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

public class CommandSetPosition implements Command<ServerCommandSource> {
  private static final CommandSetPosition CMD = new CommandSetPosition();
  private static final String COMMAND_NAME = "setposition";
  public static final Algorithm ALGORITHM = new Algorithm();
  public static BlockPos firstPosition = null;
  public static BlockPos secondPosition = null;

  public static LiteralCommandNode register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, RegistrationEnvironment environment) {
    return dispatcher.register(CommandManager.literal(COMMAND_NAME).executes(CMD));
  }

  @Override
  public int run(CommandContext<ServerCommandSource> context) {
    PlayerEntity player = (PlayerEntity) context.getSource().getEntity();
    if(player == null) {
      return 0;
    }
    Vec3d playerPosition = player.getPos();
    int x = MathHelper.floor(playerPosition.getX());
    int y = MathHelper.floor(playerPosition.getY());
    int z = MathHelper.floor(playerPosition.getZ());
    if(firstPosition == null) {
      firstPosition = new BlockPos(x, y, z);
      this.secondPosition(firstPosition, 1, context.getSource());
      return 0;
    }
    if(secondPosition == null) {
      secondPosition = new BlockPos(x, y, z);
      this.sendPosition(secondPosition, 2, context.getSource());
    }
    ALGORITHM.setServerWorld(context.getSource().getWorld());
    String chatMessage = ResultMessageCreator.createMessage(ALGORITHM.GetStringContainingAllBlockCountsFor(firstPosition, secondPosition, null));

    firstPosition = null;
    secondPosition = null;
    context.getSource().sendFeedback(Text.of(chatMessage), false);
    return 0;
  }

  private void sendPosition(BlockPos position, byte positionNumber, ServerCommandSource serverCommand) {
    new UserMessageSender(serverCommand)
      .setTitle("Set " + positionNumber == 1? "first" : "second" + " position at: ")
      .append("(")
      .append("x: " + position.getX())
      .append(", y: " + position.getY())
      .append(", z: " + position.getZ())
      .append(")")
      .send();
  }
}
