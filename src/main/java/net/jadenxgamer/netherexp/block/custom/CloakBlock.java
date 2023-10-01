package net.jadenxgamer.netherexp.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class CloakBlock extends Block {

    public static final BooleanProperty REVEALED = BooleanProperty.of("revealed");

    public CloakBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        boolean r = state.get(REVEALED);
        if (!r) {
            state.with(REVEALED, true);
            world.scheduleBlockTick(pos, this, 4);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        boolean revealed = state.get(REVEALED);
        if (world.isReceivingRedstonePower(pos)) {
            return;
        }
        if (revealed) {
            state.with(REVEALED, false);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(REVEALED);
    }
}
