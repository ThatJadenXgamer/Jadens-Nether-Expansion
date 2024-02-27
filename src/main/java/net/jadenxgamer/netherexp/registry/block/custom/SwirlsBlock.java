package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.effect.JNEStatusEffects;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.jadenxgamer.netherexp.registry.sound.JNESoundEvents;
import net.minecraft.block.AmethystClusterBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class SwirlsBlock
extends AmethystClusterBlock
implements Fertilizable {
    public static final BooleanProperty COOLDOWN = BooleanProperty.of("cooldown");

    protected final ParticleEffect particle;

    public SwirlsBlock(int height, int xzOffset, Settings settings, ParticleEffect particle) {
        super(height, xzOffset, settings);
        this.particle = particle;
        setDefaultState(this.getStateManager().getDefaultState().with(COOLDOWN, false).with(WATERLOGGED, false).with(FACING, Direction.UP));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!state.get(COOLDOWN) && entity instanceof LivingEntity && !entity.getType().isIn(JNETags.EntityTypes.CANT_ACTIVATE_SWIRLS) && NetherExp.getConfig().blocks.soulSwirlsConfigs.soul_swirls_boosting) {
            int d = NetherExp.getConfig().blocks.soulSwirlsConfigs.unbounded_speed_duration * 20;
            ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(JNEStatusEffects.UNBOUNDED_SPEED, d, 0, true, true), entity);
            swirlPopParticles(world, pos);
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.SOUL_SWIRLS_BOOST, SoundCategory.BLOCKS, 1.0f, 1.0f);
            world.setBlockState(pos, state.cycle(COOLDOWN), NOTIFY_LISTENERS);
            world.scheduleBlockTick(pos, this, NetherExp.getConfig().blocks.soulSwirlsConfigs.soul_swirls_cooldown_ticks);
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
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        boolean cooldown = state.get(COOLDOWN);
        double x = (double)pos.getX() + random.nextDouble();
        double y = (double)pos.getY() + 0.8;
        double z = (double)pos.getZ() + random.nextDouble();
        int i = random.nextInt(20);
        if (cooldown && i == 0 ) {
            world.addParticle(this.particle, x, y, z, MathHelper.nextDouble(random, -0.02, 0.02), 0.08, MathHelper.nextDouble(random, -0.02, 0.02));
        }
    }

    private void swirlPopParticles(World world, BlockPos pos) {
        Random random = world.random;
        for (Direction direction : Direction.values()) {
            BlockPos blockPos = pos.offset(direction);
            if (world.getBlockState(blockPos).isOpaqueFullCube(world, blockPos)) continue;
            Direction.Axis axis = direction.getAxis();
            double e = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double) direction.getOffsetX() : (double) random.nextFloat();
            double f = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double) direction.getOffsetY() : (double) random.nextFloat();
            double g = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double) direction.getOffsetZ() : (double) random.nextFloat();
            world.addParticle(this.particle, (double)pos.getX() + e, (double)pos.getY() + f, (double)pos.getZ() + g, 0.0, 0.02, 0.0);
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
