package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.block.ModBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractPlantBlock;
import net.minecraft.block.AbstractPlantStemBlock;
import net.minecraft.block.Block;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;

public class SmokestalkPlantBlock
extends AbstractPlantBlock {

    public static final VoxelShape SHAPE = Block.createCuboidShape(5, 0, 5, 11, 16, 11);

    public SmokestalkPlantBlock(AbstractBlock.Settings settings) {
        super(settings, Direction.UP, SHAPE, false);
    }

    @Override
    protected AbstractPlantStemBlock getStem() {
        return (AbstractPlantStemBlock) ModBlocks.SMOKESTALK;
    }
}
