package net.jadenxgamer.netherexp.block.custom;

import net.jadenxgamer.netherexp.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;

import java.util.function.Supplier;

public class VineStemPlantBlock
extends AbstractPlantBlock {
    public static final VoxelShape SHAPE = Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);

    private final GourdBlock gourdBlock;
    private final Supplier<Item> pickBlockItem;

    public VineStemPlantBlock(GourdBlock gourdBlock, Supplier<Item> pickBlockItem, AbstractBlock.Settings settings) {
        super(settings, Direction.UP, SHAPE, false);
        this.gourdBlock = gourdBlock;
        this.pickBlockItem = pickBlockItem;
    }

    @Override
    protected AbstractPlantStemBlock getStem() {
        return (AbstractPlantStemBlock) ModBlocks.SORROWSQUASH_STEM;
    }
}

