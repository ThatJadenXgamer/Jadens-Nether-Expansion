package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.misc.JNETags;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.jadenxgamer.netherexp.util.HolderHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ThinBlackIceBlock extends BlackIceBlock {
    private static double breakChanceMultiplier = 1.0;

    public ThinBlackIceBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (level.isClientSide()) return;
        double breakingOdds = entity.isSprinting() ? JNEConfigs.THIN_ICE_BREAKING_CHANCE_SPRINTING.get() : JNEConfigs.THIN_ICE_BREAKING_CHANCE.get();

        if (entity instanceof LivingEntity living) {
            if (!canBreakThinIce(living)) return;

            if (level.random.nextDouble() < breakingOdds) {
                level.scheduleTick(pos, JNEBlocks.THIN_BLACK_ICE.get(), 0);
            } else {
                ((ServerLevel) level).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), pos.getX() + 0.5, pos.below().getY() + 1.0, pos.getZ() + 0.5, 1, 0.0, 0.0, 0.0, 0);
                if (level.random.nextInt(10) == 1) level.playSound(null, pos, JNESoundEvents.THIN_BLACK_ICE_CRACKING.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            }
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.destroyBlock(pos, false);
        breakNearbyThinIce(level, pos, random);
    }

    private static void breakNearbyThinIce(ServerLevel level, BlockPos pos, RandomSource random) {
        boolean brokenAny = false;
        double breakChance = JNEConfigs.NEARBY_THIN_ICE_BREAKING_CHANCE.get() * breakChanceMultiplier;
        Direction[] directions = Direction.values();
        for (Direction direction : directions) {
            if (random.nextDouble() < breakChance && level.getBlockState(pos.relative(direction)).is(JNEBlocks.THIN_BLACK_ICE.get())) {
                brokenAny = true;
                breakChanceMultiplier -= 0.1;
                level.scheduleTick(pos.relative(direction), JNEBlocks.THIN_BLACK_ICE.get(), 10);
            }
        }

        if (!brokenAny) breakChanceMultiplier = 1.0;
    }

    @SuppressWarnings("deprecation")
    private boolean canBreakThinIce(LivingEntity entity) {
        return !entity.getType().is(JNETags.EntityTypes.CANT_SHATTER_THIN_BLACK_ICE) && !entity.isShiftKeyDown() && EnchantmentHelper.getItemEnchantmentLevel(HolderHelper.getEnchantmentHolder(Enchantments.SOUL_SPEED), entity.getItemBySlot(EquipmentSlot.FEET)) <= 0;
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.causeFallDamage(fallDistance, 0.8f, entity.damageSources().fall());
        if (level.isClientSide()) return;
        level.scheduleTick(pos, JNEBlocks.THIN_BLACK_ICE.get(), 0);
    }

    @Override
    protected VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0f;
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    @Override
    protected boolean skipRendering(BlockState state, BlockState adjacentState, Direction direction) {
        return adjacentState.is(this) || super.skipRendering(state, adjacentState, direction);
    }
}
