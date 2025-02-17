package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Supplier;

public class HelixIvyBlock extends IvyBlock {
    public static final BooleanProperty HELIX = BooleanProperty.create("helix");
    private final Supplier<Item> glowSpore;
    private final Supplier<SimpleParticleType> glowSporeParticle;
    private final Supplier<Item> helixItem;
    public HelixIvyBlock(Supplier<Item> glowSpore, Supplier<SimpleParticleType> glowSporeParticle, Supplier<Item> helixItem, Properties properties) {
        super(properties);
        this.glowSpore = glowSpore;
        this.glowSporeParticle = glowSporeParticle;
        this.helixItem = helixItem;
        this.registerDefaultState(this.defaultBlockState().setValue(HELIX, false));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        boolean helix = pState.getValue(HELIX);
        if (!helix) {
            if (itemStack.is(glowSpore.get())) {
                pLevel.playSound(pPlayer, pPos, JNESoundEvents.LIGHTSPORES_APPLY.get(), SoundSource.BLOCKS, 1.0f, pLevel.getRandom().nextFloat() * 0.4f + 0.8f);
                pLevel.setBlock(pPos, pState.cycle(HELIX), Block.UPDATE_ALL);
                if (!pPlayer.isCreative()) {
                    itemStack.shrink(1);
                }
                blockParticle(pLevel, pPos);
                return InteractionResult.SUCCESS;
            }
            else if (itemStack.is(helixItem.get())) {
                pLevel.playSound(pPlayer, pPos, this.soundType.getPlaceSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
                pLevel.setBlock(pPos, pState.cycle(HELIX), Block.UPDATE_ALL);
                if (!pPlayer.isCreative()) {
                    itemStack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        } else {
            if (itemStack.is(Items.SHEARS)) {
                pLevel.playSound(pPlayer, pPos, JNESoundEvents.LIGHTSPORES_SHEAR.get(), SoundSource.BLOCKS, 1.0f, pLevel.getRandom().nextFloat() * 0.4f + 0.8f);
                pLevel.setBlock(pPos, pState.cycle(HELIX), Block.UPDATE_ALL);
                Block.popResourceFromFace(pLevel, pPos, pHit.getDirection(), new ItemStack(helixItem.get(), 1));
                if (!pPlayer.isCreative()) {
                    itemStack.hurtAndBreak(1, pPlayer, p -> p.broadcastBreakEvent(pHand));
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    private void blockParticle(Level level, BlockPos pos) {
        RandomSource randomSource = level.random;
        Direction[] var5 = Direction.values();

        for (Direction direction : var5) {
            BlockPos blockPos2 = pos.relative(direction);
            if (!level.getBlockState(blockPos2).isSolidRender(level, blockPos2)) {
                Direction.Axis axis = direction.getAxis();
                double e = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double) direction.getStepX() : (double) randomSource.nextFloat();
                double f = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double) direction.getStepY() : (double) randomSource.nextFloat();
                double g = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double) direction.getStepZ() : (double) randomSource.nextFloat();
                level.addParticle(glowSporeParticle.get(), (double) pos.getX() + e, (double) pos.getY() + f, (double) pos.getZ() + g, 0.0, 0.0, 0.0);
            }
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HELIX);
    }
}