package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.effect.JNEMobEffects;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class SwirlsBlock
extends AmethystClusterBlock
implements BonemealableBlock {

    public static final BooleanProperty COOLDOWN = BooleanProperty.create("cooldown");

    protected final int type;

    public SwirlsBlock(int height, int xzOffset, Properties properties, int type) {
        super(height, xzOffset, properties);
        this.type = type;
        registerDefaultState(this.defaultBlockState().setValue(COOLDOWN, false).setValue(WATERLOGGED, false).setValue(FACING, Direction.UP));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!state.getValue(COOLDOWN) && entity instanceof LivingEntity && !entity.getType().is(JNETags.EntityTypes.CANT_ACTIVATE_SWIRLS)) {
            // Todo: add configs
//            int d = NetherExp.getConfig().blocks.soulSwirlsConfigs.unbounded_speed_duration * 20;
            int d = 10 * 20;
            ((LivingEntity) entity).addEffect(new MobEffectInstance(JNEMobEffects.UNBOUNDED_SPEED.get(), d, 0, true, true), entity);
            swirlPopParticles(level, pos);
            level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.SOUL_SWIRLS_BOOST.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            level.setBlock(pos, state.cycle(COOLDOWN), UPDATE_CLIENTS);
            // Todo: add configs
//            level.scheduleTick(pos, this, NetherExp.getConfig().blocks.soulSwirlsConfigs.soul_swirls_cooldown_ticks);
            level.scheduleTick(pos, this, 1000);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(COOLDOWN)) {
            level.setBlock(pos, state.cycle(COOLDOWN), UPDATE_CLIENTS);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        boolean cooldown = state.getValue(COOLDOWN);
        double x = (double)pos.getX() + random.nextDouble();
        double y = (double)pos.getY() + 0.8;
        double z = (double)pos.getZ() + random.nextDouble();
        int i = random.nextInt(20);
        if (cooldown && i == 0 ) {
            switch (type) {
                default: {
                    level.addParticle(JNEParticleTypes.SWIRL_POP.get(), x, y, z, Mth.nextDouble(random, -0.02, 0.02), 0.08, Mth.nextDouble(random, -0.02, 0.02));
                    break;
                }
                case 2: {
                    level.addParticle(JNEParticleTypes.SHALE_SWIRL_POP.get(), x, y, z, Mth.nextDouble(random, -0.02, 0.02), 0.08, Mth.nextDouble(random, -0.02, 0.02));
                    break;
                }
            }
        }
    }

    private void swirlPopParticles(Level level, BlockPos pos) {
        RandomSource random = level.random;
        for (Direction direction : Direction.values()) {
            BlockPos blockPos = pos.offset(direction.getNormal());
            if (level.getBlockState(blockPos).isSolidRender(level, blockPos)) continue;
            Direction.Axis axis = direction.getAxis();
            double e = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double) direction.getStepX() : (double) random.nextFloat();
            double f = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double) direction.getStepY() : (double) random.nextFloat();
            double g = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double) direction.getStepZ() : (double) random.nextFloat();
            switch (type) {
                default: {
                    level.addParticle(JNEParticleTypes.SWIRL_POP.get(), (double)pos.getX() + e, (double)pos.getY() + f, (double)pos.getZ() + g, 0.0, 0.02, 0.0);
                    break;
                }
                case 2: {
                    level.addParticle(JNEParticleTypes.SHALE_SWIRL_POP.get(), (double)pos.getX() + e, (double)pos.getY() + f, (double)pos.getZ() + g, 0.0, 0.02, 0.0);
                    break;
                }
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(COOLDOWN, FACING, WATERLOGGED);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState, boolean bl) {
        // TODO: Add configs
//        return NetherExp.getConfig().blocks.renewableConfigs.bone_mealable_soul_swirls;
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        popResource(serverLevel, blockPos, new ItemStack(this));
    }
}
