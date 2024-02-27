package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TopConnectingBlock extends Block {

    public static final BooleanProperty CONNECTED = BooleanProperty.of("connected");

    public TopConnectingBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(CONNECTED, false));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        boolean c = state.get(CONNECTED);
        if (!c) {
            if (world.getBlockState(pos.up()).isOf(JNEBlocks.JAGGED_SOUL_SLATE)) {
                world.setBlockState(pos, state.cycle(CONNECTED), NOTIFY_LISTENERS);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        boolean c = state.get(CONNECTED);
        if (!c) {
            if (world.getBlockState(pos.up()).isOf(JNEBlocks.JAGGED_SOUL_SLATE)) {
                world.setBlockState(pos, state.cycle(CONNECTED), NOTIFY_LISTENERS);
            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CONNECTED);
    }
}
