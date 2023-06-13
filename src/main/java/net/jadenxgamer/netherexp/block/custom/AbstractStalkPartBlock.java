package net.jadenxgamer.netherexp.block.custom;

import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import static net.jadenxgamer.netherexp.block.custom.SmokestalkPlantBlock.LEAVES;

public abstract class AbstractStalkPartBlock
        extends Block {
    protected final Direction growthDirection;
    protected final boolean tickWater;
    protected final VoxelShape outlineShape;

    protected AbstractStalkPartBlock(AbstractBlock.Settings settings, Direction growthDirection, VoxelShape outlineShape, boolean tickWater) {
        super(settings);
        this.growthDirection = growthDirection;
        this.outlineShape = outlineShape;
        this.tickWater = tickWater;
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(this.growthDirection));
        BlockState blockState2 = ctx.getWorld().getBlockState(ctx.getBlockPos().down());
        boolean bl = false;
        if (blockState2.isOf(Blocks.GRAY_CONCRETE)) {
            bl = true;
        }
        if (blockState.isOf(this.getStem()) || blockState.isOf(this.getPlant())) {
            if (bl) {
                return this.getPlant().getDefaultState().with(LEAVES, true);
            }
            if (!bl) {
                return this.getPlant().getDefaultState().with(LEAVES, false);
            }
        }
        return this.getRandomGrowthState(ctx.getWorld());
    }

    public BlockState getRandomGrowthState(WorldAccess world) {
        return this.getPlant().getDefaultState();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.offset(this.growthDirection.getOpposite());
        BlockState blockState = world.getBlockState(blockPos);
        if (!this.canAttachTo(blockState)) {
            return false;
        }
        return blockState.isOf(this.getStem()) || blockState.isOf(this.getPlant()) || blockState.isSideSolidFullSquare(world, blockPos, this.growthDirection);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.canPlaceAt(world, pos)) {
            world.breakBlock(pos, true);
        }
    }

    protected boolean canAttachTo(BlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.outlineShape;
    }

    protected abstract AbstractStalkStemBlock getStem();

    protected abstract Block getPlant();
}
