package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.effect.ModStatusEffects;
import net.minecraft.block.AmethystClusterBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class SoulSwirlsBlock
extends AmethystClusterBlock
implements Fertilizable {

    public static final BooleanProperty COOLDOWN = BooleanProperty.of("cooldown");

    public SoulSwirlsBlock(int height, int xzOffset, Settings settings) {
        super(height, xzOffset, settings);
        setDefaultState(this.getStateManager().getDefaultState().with(COOLDOWN, false).with(WATERLOGGED, false).with(FACING, Direction.UP));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!state.get(COOLDOWN) && entity instanceof LivingEntity) {
            ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(ModStatusEffects.UNBOUNDED_SPEED, 1200, 0), entity);
            world.setBlockState(pos, state.cycle(COOLDOWN), NOTIFY_LISTENERS);
            world.scheduleBlockTick(pos, this, 1000);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(COOLDOWN)) {
            world.setBlockState(pos, state.cycle(COOLDOWN), NOTIFY_LISTENERS);
        }
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return NetherExp.getConfig().blocks.renewableConfigs.bone_mealable_soul_swirls;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        dropStack(world, pos, new ItemStack(this));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(COOLDOWN, FACING, WATERLOGGED);
    }
}
