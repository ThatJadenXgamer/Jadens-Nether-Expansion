package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class SmokestalkPlantBlock extends GrowingPlantBodyBlock {

    public static final VoxelShape SHAPE = Block.box(5, 0, 5, 11, 16, 11);

    public SmokestalkPlantBlock(Properties properties) {
        super(properties, Direction.UP, SHAPE, false);
    }

    @Override
    protected @NotNull GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) JNEBlocks.SMOKESTALK.get();
    }
}
