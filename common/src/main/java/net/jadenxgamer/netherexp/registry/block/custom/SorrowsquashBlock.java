package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.jadenxgamer.netherexp.registry.worldgen.feature.JNEConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class SorrowsquashBlock extends StemGrownBlock implements BonemealableBlock {
    public SorrowsquashBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    public @NotNull InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (itemStack.is(Items.SHEARS)) {
            if (!level.isClientSide) {
                Direction hitDirection = blockHitResult.getDirection();
                Direction direction = hitDirection.getAxis() == Direction.Axis.Y ? player.getDirection().getOpposite() : hitDirection;
                level.playSound(null, blockPos, SoundEvents.PUMPKIN_CARVE, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.setBlock(blockPos, JNEBlocks.CARVED_SORROWSQUASH.get().defaultBlockState().setValue(CarvedPumpkinBlock.FACING, direction), 11);
                ItemEntity itemEntity = new ItemEntity(level, (double)blockPos.getX() + 0.5 + (double)direction.getStepX() * 0.65, (double)blockPos.getY() + 0.1, (double)blockPos.getZ() + 0.5 + (double)direction.getStepZ() * 0.65, new ItemStack(Items.PUMPKIN_SEEDS, 4));
                itemEntity.setDeltaMovement(0.05 * (double)direction.getStepX() + level.random.nextDouble() * 0.02, 0.05, 0.05 * (double)direction.getStepZ() + level.random.nextDouble() * 0.02);
                level.addFreshEntity(itemEntity);
                itemStack.hurtAndBreak(1, player, (playerx) -> playerx.broadcastBreakEvent(interactionHand));
                level.gameEvent(player, GameEvent.SHEAR, blockPos);
                player.awardStat(Stats.ITEM_USED.get(Items.SHEARS));
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
        }
    }

    public @NotNull StemBlock getStem() {
        return (StemBlock)Blocks.PUMPKIN_STEM;
    }

    public @NotNull AttachedStemBlock getAttachedStem() {
        return (AttachedStemBlock)Blocks.ATTACHED_PUMPKIN_STEM;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean bl) {
        return level.getBlockState(pos.below()).is(JNETags.Blocks.SORROWEED_REPLACEABLE);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        level.registryAccess().registry(Registries.CONFIGURED_FEATURE).flatMap((registry) -> registry.getHolder(JNEConfiguredFeatures.SORROWEED_PATCH_BONEMEAL)).ifPresent((reference) ->
                reference.value().place(level, level.getChunkSource().getGenerator(), random, pos));
    }
}
