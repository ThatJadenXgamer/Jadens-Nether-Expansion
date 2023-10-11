package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class SmokestalkPlantBlock
extends AbstractPlantBlock {

    //TODO: The Leaves blockstate doesn't get activated unless the block is updated
    public static final BooleanProperty LEAVES = BooleanProperty.of("leaves");

    public static final VoxelShape SHAPE = Block.createCuboidShape(5, 0, 5, 11, 16, 11);

    public SmokestalkPlantBlock(AbstractBlock.Settings settings) {
        super(settings, Direction.UP, SHAPE, false);
        this.setDefaultState(this.stateManager.getDefaultState().with(LEAVES, false));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == this.growthDirection.getOpposite() && !state.canPlaceAt(world, pos)) {
            world.scheduleBlockTick(pos, this, 1);
        }
        AbstractPlantStemBlock abstractPlantStemBlock = this.getStem();
        if (direction == this.growthDirection && !neighborState.isOf(this) && !neighborState.isOf(abstractPlantStemBlock)) {
            return this.copyState(state, abstractPlantStemBlock.getRandomGrowthState(world));
        }
        if (this.tickWater) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if (direction == Direction.DOWN) {
            return state.with(LEAVES, this.isGrayConcrete(neighborState));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(this.growthDirection));
        BlockPos blockPos = ctx.getBlockPos();
        World worldAccess = ctx.getWorld();
        if (blockState.isOf(this.getStem()) || blockState.isOf(this.getPlant())) {
            return this.getPlant().getDefaultState().with(LEAVES, this.isGrayConcrete(worldAccess.getBlockState(blockPos.down())));
        }
        return this.getRandomGrowthState(ctx.getWorld());
    }

    private boolean isGrayConcrete(BlockState state) {
        return state.isOf(Blocks.GRAY_CONCRETE);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LEAVES);
    }

    @Override
    protected AbstractPlantStemBlock getStem() {
        return (AbstractPlantStemBlock) ModBlocks.SMOKESTALK;
    }
}
