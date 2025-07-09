package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.core.misc.JNETags;
import net.jadenxgamer.netherexp.registry.JNEMobEffects;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.jadenxgamer.netherexp.util.HolderHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class SwirlsBlock extends AmethystClusterBlock implements BonemealableBlock {

    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    private final Supplier<LodestoneWorldParticleType> particle;

    public SwirlsBlock(Supplier<LodestoneWorldParticleType> particle, Properties properties) {
        super(7, 3, properties);
        this.particle = particle;
        this.registerDefaultState(this.defaultBlockState().setValue(ACTIVE, false));
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity living) {
            if (state.getValue(ACTIVE) || !canActivateSwirls(living)) return;

            level.playSound(null, pos, JNESoundEvents.Interactions.SOUL_SWIRLS_BOOST.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            level.setBlock(pos, state.cycle(ACTIVE), Block.UPDATE_ALL);
            level.scheduleTick(pos, this, 50 * 20);

            living.addEffect(new MobEffectInstance(Holder.direct(JNEMobEffects.UNBOUNDED_SPEED.get()), 10 * 20, 0, false, true), entity);

            Direction[] directions = Direction.values();
            for (Direction direction : directions) {
                BlockPos directionPos = pos.relative(direction);
                if (!level.getBlockState(directionPos).isSolidRender(level, directionPos)) {
                    Direction.Axis axis = direction.getAxis();
                    double x = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double) direction.getStepX() : (double) level.random.nextFloat();
                    double y = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double) direction.getStepY() : (double) level.random.nextFloat();
                    double z = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double) direction.getStepZ() : (double) level.random.nextFloat();

                    swirlPopParticle(level, pos, level.random, x, y, z);
                }
            }
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(ACTIVE)) {
            level.setBlock(pos, state.cycle(ACTIVE), Block.UPDATE_ALL);
            level.playSound(null, pos, JNESoundEvents.Interactions.SOUL_SWIRLS_DEACTIVATE.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (!state.getValue(ACTIVE) || random.nextInt(20) != 0) return;

        double x = (double)pos.getX() + random.nextDouble();
        double y = (double)pos.getY() + 0.8;
        double z = (double)pos.getZ() + random.nextDouble();

        swirlPopParticle(level, pos, random, x, y, z);
    }

    private boolean canActivateSwirls(LivingEntity entity) {
        return !entity.getType().is(JNETags.EntityTypes.CANT_ACTIVATE_SWIRLS) && EnchantmentHelper.getItemEnchantmentLevel(HolderHelper.getEnchantmentHolder(entity.level(), Enchantments.SOUL_SPEED), entity.getItemBySlot(EquipmentSlot.FEET)) <= 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE, FACING, WATERLOGGED);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return state.getValue(FACING) == Direction.UP;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        BlockPos relativePos = pos.relative(direction);

        if (level.getBlockState(relativePos).isAir() && level.getBlockState(relativePos.below()).is(JNETags.Blocks.SOUL_SANDS)) {
            level.setBlock(pos, this.defaultBlockState(), Block.UPDATE_ALL);
        }
    }

    private void swirlPopParticle(Level level, BlockPos pos, RandomSource random, double x, double y,double z) {
        WorldParticleBuilder.create(particle)
                .setLightLevel(15728880)
                .setSpinData(SpinParticleData.createRandomDirection(random, 0.0f, 1.0f).setCoefficient(0.7f).setEasing(Easing.SINE_IN).build())
                .setScaleData(GenericParticleData.create(0.05f, 0.13f, 0.0f).setEasing(Easing.BOUNCE_IN_OUT).build())
                .setTransparencyData(GenericParticleData.create(1).build())
                .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                .setLifetime(random.nextInt(20, 30))
                .disableNoClip()
                .setGravityStrength(0.2f)
                .setMotion(0.0, 0.02, 0.0)
                .spawn(level, pos.getX() + x, pos.getY() + y, pos.getZ() + z);
    }
}
