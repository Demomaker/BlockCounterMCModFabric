package net.demomaker.blockcounter.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.demomaker.blockcounter.blockentity.BlockCount;
import net.demomaker.blockcounter.blockentity.BlockEntries;
import net.demomaker.blockcounter.blockentity.BlockEntry;
import net.demomaker.blockcounter.blockentity.ItemName;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;


public class Algorithm {
    private ServerWorld serverWorld = null;
    private boolean stopAlgorithm = false;
    public void setServerWorld(ServerWorld serverWorld) {
        this.serverWorld = serverWorld;
    }
    public ServerWorld getServerWorld() { return this.serverWorld; }

    public void stop() {
        this.stopAlgorithm = true;
    }

    public void start() {
        this.stopAlgorithm = false;
    }

    public BlockEntries GetBlockEntriesByCount(BlockPos firstPosition, BlockPos secondPosition, Item itemInput)
        throws Exception {
        ItemName itemName = new ItemName("");
        if(itemInput != null)
        {
            itemName = new ItemName(itemInput.getName().getString());
        }
        Map<String, BlockCount> localBlockCounts = GetAmountOfBlocksPerBlockEntry(firstPosition, secondPosition, itemName);
        List<BlockEntry> blockEntries = new ArrayList<>();
        for(String key : localBlockCounts.keySet()) {
            blockEntries.add(new BlockEntry(new ItemName(key), localBlockCounts.get(key)));
        }
        return new BlockEntries(blockEntries);
    }

    public Map<String, BlockCount> GetAmountOfBlocksPerBlockEntry(BlockPos firstPosition,BlockPos secondPosition, ItemName itemName)
        throws Exception {
        if(this.serverWorld == null) {
            throw new Exception("user is not on a server");
        }

        Map<String, BlockCount> blockCounts = new HashMap<>();
        CountingBoundaries countingBoundaries = getCountingBoundaries(firstPosition, secondPosition);
        for(int x = countingBoundaries.smallestX; x <= countingBoundaries.highestX; x++) {
            for(int y = countingBoundaries.smallestY; y <= countingBoundaries.highestY; y++) {
                for(int z = countingBoundaries.smallestZ; z <= countingBoundaries.highestZ; z++) {
                    if(this.stopAlgorithm) {
                        return blockCounts;
                    }
                    Block currentBlock = serverWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    ItemName currentItemName = new ItemName(currentBlock.asItem().getName().getString());
                    if((itemName.equals("") || currentItemName.equals(itemName)))
                    {
                        if(blockCounts.containsKey(currentItemName.getString()))
                            blockCounts.replace(
                                currentItemName.getString(), blockCounts.get(currentItemName.getString()).increment());
                        else
                            blockCounts.putIfAbsent(currentItemName.getString(), new BlockCount());
                    }
                }
            }
        }
        return blockCounts;
    }

    public CountingBoundaries getCountingBoundaries(BlockPos firstPosition, BlockPos secondPosition) {
        CountingBoundary xBoundaries = new CountingBoundary(firstPosition.getX(), secondPosition.getX());
        CountingBoundary yBoundaries = new CountingBoundary(firstPosition.getY(), secondPosition.getY());
        CountingBoundary zBoundaries = new CountingBoundary(firstPosition.getZ(), secondPosition.getZ());

        return new CountingBoundaries(
            xBoundaries.lowest,
            yBoundaries.lowest,
            zBoundaries.lowest,
            xBoundaries.highest,
            yBoundaries.highest,
            zBoundaries.highest
        );
    }
}


