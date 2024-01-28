package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.block.entity.ModSkullBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public abstract class AbstractModSkullBlock extends BlockWithEntity {
    public static final BooleanProperty POWERED = Properties.POWERED;
    private final ModSkullBlock.ModSkullType type;

    protected AbstractModSkullBlock(Settings settings, ModSkullBlock.ModSkullType type) {
        super(settings);
        this.type = type;
        this.setDefaultState(this.stateManager.getDefaultState().with(POWERED, false));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return super.getRenderType(state);
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ModSkullBlockEntity(pos, state);
    }

    public ModSkullBlock.ModSkullType getSkullType() {
        return this.type;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return super.canPathfindThrough(state, world, pos, type);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(POWERED, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClient) {
            boolean bl = world.isReceivingRedstonePower(pos);
            if (bl != state.get(POWERED)) {
                world.setBlockState(pos, state.with(POWERED, bl), 2);
            }
        }
    }
}
