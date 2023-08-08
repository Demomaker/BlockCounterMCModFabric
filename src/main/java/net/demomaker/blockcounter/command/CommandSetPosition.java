package net.demomaker.blockcounter.command;

import static net.minecraft.server.command.CommandManager.argument;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.demomaker.blockcounter.command.config.CommandConfigs;
import net.demomaker.blockcounter.command.config.SetPositionCommandConfig;
import net.demomaker.blockcounter.main.BlockCounter;
import net.demomaker.blockcounter.player.PlayerConfig;
import net.demomaker.blockcounter.util.ModObjects;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.item.Item;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class CommandSetPosition extends BasicCommand {
  public static final String COMMAND_NAME = "setposition";
  public static com.mojang.brigadier.builder.LiteralArgumentBuilder<net.minecraft.server.command.ServerCommandSource> getServerCommandFormat(
      CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, Command<ServerCommandSource> CountBlocksCommand, Command<ServerCommandSource> CountBlocksWithoutItemArgumentCommand) {
    return CommandManager.literal(BlockCounter.MOD_ID).then(
        dispatcher.register(CommandManager.literal(CommandSetPosition.COMMAND_NAME)
            .requires(cs -> cs.hasPermissionLevel(0))
            .executes(CountBlocksWithoutItemArgumentCommand)
            .then(argument(BLOCK_ARGUMENT_NAME, ItemStackArgumentType.itemStack(registryAccess))
            .requires(cs -> cs.hasPermissionLevel(0))
            .executes(CountBlocksCommand)
        ))
    );
  }

    @Override
  public int run(CommandContext<ServerCommandSource> context) {
    ItemStackArgument itemArgument = ItemStackArgumentType.getItemStackArgument(context, BLOCK_ARGUMENT_NAME);
    Item item = itemArgument.getItem();
    return countBlocks(context, item);
  }

  public int countBlocks(CommandContext<ServerCommandSource> context, Item item) {
    try {

      ServerPlayerEntity player = getPlayerFromContext(context);
      PlayerConfig currentPlayerConfig = getPlayerConfigFromContext(context);
      SetPositionCommandConfig setPositionCommandConfig = currentPlayerConfig.getCommandConfigs()
          .getSetPositionCommandConfig();
      setPositionCommandConfig.serverWorld = getServerWorldFromContext(context);
      setPositionCommandConfig.itemFilter = item;

      BlockPos firstPosition = currentPlayerConfig.getCommandConfigs().getSetPositionCommandConfig().firstPosition;
      BlockPos secondPosition = currentPlayerConfig.getCommandConfigs().getSetPositionCommandConfig().secondPosition;

      Vec3d playerPosition = player.getPos();
      int x = MathHelper.floor(playerPosition.getX());
      int y = MathHelper.floor(playerPosition.getY());
      int z = MathHelper.floor(playerPosition.getZ());

      if (firstPosition == null) {
        firstPosition = new BlockPos(x, y, z);
        setFirstPosition(currentPlayerConfig, firstPosition);
      }
      else if (secondPosition == null) {
        secondPosition = new BlockPos(x, y, z);
        setSecondPosition(currentPlayerConfig, secondPosition);
      }

      String positionWord = (secondPosition == null) ? "first" : "second";
      String chatMessage = "Set "
          + positionWord
          + " position at : "
          + "("
          + "x: " + firstPosition.getX()
          + ", y: " + firstPosition.getY()
          + ", z: " + firstPosition.getZ()
          + ")";
      context.getSource().sendFeedback(() -> Text.of(chatMessage), false);

      if(secondPosition != null) {
        int result = super.countBlocks(context, currentPlayerConfig.getCommandConfigs()
            .getSetPositionCommandConfig());
        setFirstPosition(currentPlayerConfig, null);
        setSecondPosition(currentPlayerConfig, null);;
        return result;
      }
    }
    catch(Exception e) {
      context.getSource().sendFeedback(() -> Text.of(e.getMessage()), false);
      return e.hashCode();
    }
    return 0;
  }

  protected void setFirstPosition(PlayerConfig playerConfig, BlockPos position) {
    SetPositionCommandConfig currentSetPositionCommandConfig = playerConfig.getCommandConfigs().getSetPositionCommandConfig();
    currentSetPositionCommandConfig.firstPosition = position;
    playerConfig.setCommandConfigs(new CommandConfigs(currentSetPositionCommandConfig));
    ModObjects.playerConfigs.setPlayerConfig(playerConfig);
  }

  protected void setSecondPosition(PlayerConfig playerConfig, BlockPos position) {
    SetPositionCommandConfig currentSetPositionCommandConfig = playerConfig.getCommandConfigs().getSetPositionCommandConfig();
    currentSetPositionCommandConfig.secondPosition = position;
    playerConfig.setCommandConfigs(new CommandConfigs(currentSetPositionCommandConfig));
    ModObjects.playerConfigs.setPlayerConfig(playerConfig);
  }
}
