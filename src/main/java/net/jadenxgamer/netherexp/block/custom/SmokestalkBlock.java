package net.jadenxgamer.netherexp.block.custom;

import net.jadenxgamer.netherexp.block.ModBlocks;
import net.jadenxgamer.netherexp.misc_registry.ModTags;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.WorldAccess;

public class SmokestalkBlock
extends AbstractPlantStemBlock {

    public static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(Block.createCuboidShape(5, 0, 5, 11, 8, 11), Block.createCuboidShape(4, 8, 4, 12, 12, 12), BooleanBiFunction.OR);

    public SmokestalkBlock(AbstractBlock.Settings settings) {
        super(settings, Direction.UP, SHAPE, false, 0.1);
    }

    @Override
    protected int getGrowthLength(Random random) {
        return VineLogic.getGrowthLength(random);
    }

    @Override
    protected Block getPlant() {
        return ModBlocks.SMOKESTALK_PLANT;
    }

    @Override
    protected boolean chooseStemState(BlockState state) {
        return VineLogic.isValidForWeepingStem(state);
    }
}
