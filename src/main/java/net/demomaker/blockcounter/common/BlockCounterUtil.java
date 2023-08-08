package net.demomaker.blockcounter.common;

import net.demomaker.blockcounter.util.ResultMessageCreator;
import net.demomaker.blockcounter.util.UserMessageSender;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;

public class BlockCounterUtil {
    private BlockPos firstPosition;
    private BlockPos secondPosition;
    private Item filterItem;

    private final UserMessageSender userMessageSender;
    private final Algorithm calculator;

    public BlockCounterUtil(UserMessageSender userMessageSender, Algorithm calculator) {
        this.userMessageSender = userMessageSender;
        this.calculator = calculator;
    }

    public BlockCounterUtil setFirstPosition(BlockPos firstPosition) {
        this.firstPosition = firstPosition;
        return this;
    }

    public BlockCounterUtil setSecondPosition(BlockPos secondPosition) {
        this.secondPosition = secondPosition;
        return this;
    }

    public BlockCounterUtil setFilterItem(Item filterItem) {
        this.filterItem = filterItem;
        return this;
    }

    public void count() {
        String chatMessage = ResultMessageCreator.createMessage(calculator.GetStringContainingAllBlockCountsFor(firstPosition, secondPosition, filterItem));
        userMessageSender.append(chatMessage).send();
    }
}
