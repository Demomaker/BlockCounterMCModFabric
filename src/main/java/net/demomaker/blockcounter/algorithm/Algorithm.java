package net.demomaker.blockcounter.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.demomaker.blockcounter.adapter.block.Block;
import net.demomaker.blockcounter.adapter.block.BlockPos;
import net.demomaker.blockcounter.adapter.item.Item;
import net.demomaker.blockcounter.adapter.world.ServerWorld;
import net.demomaker.blockcounter.blockentity.BlockCount;
import net.demomaker.blockcounter.blockentity.BlockEntries;
import net.demomaker.blockcounter.blockentity.BlockEntry;
import net.demomaker.blockcounter.blockentity.DoubledBlockItemNames;
import net.demomaker.blockcounter.blockentity.ItemName;
import net.demomaker.blockcounter.command.config.CommandConfig;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Language;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class Algorithm {
    private ServerWorld serverWorld = null;
    private boolean stopAlgorithm = false;
    private boolean isRunning = false;
    public void setServerWorld(ServerWorld serverWorld) {
        this.serverWorld = serverWorld;
    }

    public void stop() {
        this.stopAlgorithm = true;
    }

    public BlockEntries execute(CommandConfig commandConfig) throws Exception {
        this.isRunning = true;
        this.setServerWorld(commandConfig.serverWorld);
        return this.GetBlockEntriesByCount(commandConfig.firstPosition, commandConfig.secondPosition, commandConfig.itemFilter);
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
        this.isRunning = false;
        return new BlockEntries(blockEntries);
    }

    public Map<String, BlockCount> GetAmountOfBlocksPerBlockEntry(BlockPos firstPosition,BlockPos secondPosition, ItemName itemName)
        throws Exception {
        if(this.serverWorld == null) {
            throw new Exception("user is not on a server");
        }

        Map<String, BlockCount> blockCounts = new HashMap<>();
        CountingBoundaries countingBoundaries = getCountingBoundaries(firstPosition, secondPosition);
        List<BlockPos> blockCoordinatesToIgnore = new ArrayList<>();
        for(int x = countingBoundaries.smallestX; x <= countingBoundaries.highestX; x++) {
            for(int y = countingBoundaries.smallestY; y <= countingBoundaries.highestY; y++) {
                for(int z = countingBoundaries.smallestZ; z <= countingBoundaries.highestZ; z++) {
                    if(this.stopAlgorithm) {
                        return blockCounts;
                    }
                    BlockPos currentBlockPos = new BlockPos(x, y, z);
                    if(blockCoordinatesToIgnore.contains(currentBlockPos)) {
                        continue;
                    }
                    ItemName currentItemName = getItemNameAt(currentBlockPos);

                    if((itemName.equals("") || currentItemName.equals(itemName)))
                    {
                        blockCoordinatesToIgnore.addAll(ignoreCoordinatesOfCoupledBlock(currentBlockPos, currentItemName, blockCoordinatesToIgnore));

                        if(blockCounts.containsKey(currentItemName.getString()))
                            blockCounts.replace(
                                currentItemName.getString(), blockCounts.get(currentItemName.getString()).increment());
                        else
                            blockCounts.putIfAbsent(currentItemName.getString(), new BlockCount());
                    }
                }
            }
        }
        blockCounts.putAll(getEntityCountsInArea(serverWorld.serverWorld(), firstPosition, secondPosition));
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

    private ItemName getItemNameAt(BlockPos blockPos) {
        Block currentBlock = new Block(serverWorld.getBlockState(blockPos).getBlock());
        ItemName itemName = new ItemName(Language.getInstance().get(currentBlock.block().getTranslationKey()));
        String airName = Language.getInstance().get(Blocks.AIR.getTranslationKey());
        if(itemName.getString().equals(airName)) {
            itemName = new ItemName(FluidVariantAttributes.getName(FluidVariant.of(serverWorld.getBlockState(blockPos).getFluidState().getFluid())).getString());
        }

        if(itemName.getString().equals(FluidVariantAttributes.getName(FluidVariant.of(Fluids.EMPTY)).getString())) {
            itemName = new ItemName(airName);
        }
        return itemName;
    }

    public Map<String, BlockCount> getEntityCountsInArea(World world, BlockPos start, BlockPos end) {
        Map<String, BlockCount> entityCounts = new HashMap<>();
        Box box = new Box(new Vec3d(start.getX(), start.getY(), start.getZ()), new Vec3d(end.getX(), end.getY(), end.getZ())); // Create a bounding box from start to end positions
        for (Entity entity : world.getEntitiesByClass(Entity.class, box, entity -> true)) {
            String name = Language.getInstance().get(entity.getType().getTranslationKey());
            if(entityCounts.containsKey(name))
                entityCounts.replace(name, entityCounts.get(name).increment());
            else
                entityCounts.putIfAbsent(name, new BlockCount());
        }
        return entityCounts;
    }

    private List<BlockPos> ignoreCoordinatesOfCoupledBlock(BlockPos blockPos, ItemName itemName, List<BlockPos> existingIgnoredCoordinates) {
        int x = blockPos.getX();
        int y = blockPos.getY();
        int z = blockPos.getZ();
        List<BlockPos> ignoredCoordinates = new ArrayList<>();
        if(DoubledBlockItemNames.getAsList().contains(itemName.getString())) {
            for(int x2 = x-1; x2 < x+2; x2++) {
                if(x2 == x) { continue; }
                BlockPos blockPos2 = new BlockPos(x2, y, z);
                if(itemName.equals(getItemNameAt(blockPos2))) {
                    if(!existingIgnoredCoordinates.contains(blockPos2)) {
                        ignoredCoordinates.add(blockPos2);
                    }
                    break;
                }
            }

            for(int y2 = y-1; y2 < y+2; y2++) {
                if(y2 == y) { continue; }
                BlockPos blockPos2 = new BlockPos(x, y2, z);
                if(itemName.equals(getItemNameAt(blockPos2))) {
                    if(!existingIgnoredCoordinates.contains(blockPos2)) {
                        ignoredCoordinates.add(blockPos2);
                    }
                    break;
                }
            }

            for(int z2 = z-1; z2 < z+2; z2++) {
                if(z2 == z) { continue; }
                BlockPos blockPos2 = new BlockPos(x, y, z2);
                if(itemName.equals(getItemNameAt(blockPos2))) {
                    if(!existingIgnoredCoordinates.contains(blockPos2)) {
                        ignoredCoordinates.add(blockPos2);
                    }
                    break;
                }
            }
        }

        return ignoredCoordinates;
    }
}


