package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class ThinBlackIceBlock extends BlackIceBlock {
    public ThinBlackIceBlock(Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getVisualShape(BlockState pState, BlockGetter pReader, BlockPos pPos, CollisionContext pContext) {
        return Shapes.empty();
    }

    @Override
    public float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return true;
    }

    @Override
    public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pSide) {
        return pAdjacentBlockState.is(this) ? true : super.skipRendering(pState, pAdjacentBlockState, pSide);
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        int breakingOdds = pEntity.isSprinting() ? 5 : 15;
        if (pLevel instanceof ServerLevel serverLevel && pEntity instanceof LivingEntity livingEntity && pLevel.random.nextFloat() < 0.07f) {
            if (!livingEntity.isShiftKeyDown() && !EnchantmentHelper.hasSoulSpeed(livingEntity)) {
                if (pLevel.random.nextInt(breakingOdds) == 0) {
                    pLevel.scheduleTick(pPos, this, 0);
                    Direction[] directions = Direction.values();
                    for (Direction direction : directions) {
                        BlockPos relativePos = pPos.relative(direction);
                        if (!pLevel.getBlockState(relativePos).is(pState.getBlock()) && serverLevel.random.nextBoolean()) {
                            pLevel.scheduleTick(relativePos, this, 2);
                        }
                    }
                } else {
                    serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, pState), pPos.getX() + 0.5, pPos.below().getY() + 0.9, pPos.getZ() + 0.5, 5, 0.0, 0.0, 0.0, 0);
                    pLevel.playSound(null, pPos, JNESoundEvents.BLOCK_THIN_BLACK_ICE_CRACKING.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                }
            }
        }
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        pLevel.destroyBlock(pPos, false);
    }

    @Override
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
        if (pFallDistance >= 1.0f) {
            pLevel.destroyBlock(pPos, false);
            pLevel.playSound(null, pPos, JNESoundEvents.BLOCK_THIN_BLACK_ICE_CRACKING.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
        }
    }
}
