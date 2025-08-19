package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.keys.JNEDamageSources;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.core.worldgen.feature.JNEConfiguredFeatures;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.ItemAbilities;

public class SorrowsquashBlock extends Block implements BonemealableBlock, Fallable {
    public SorrowsquashBlock(Properties properties) {
        super(properties);
    }

    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.canPerformAction(ItemAbilities.SHEARS_CARVE)) {
            Direction direction = hitResult.getDirection().getAxis() == Direction.Axis.Y ? player.getDirection().getOpposite() : hitResult.getDirection();
            level.playSound(null, pos, SoundEvents.PUMPKIN_CARVE, SoundSource.BLOCKS, 1.0f, 1.0f);
            level.setBlock(pos, JNEBlocks.CARVED_SORROWSQUASH.get().defaultBlockState().setValue(CarvedPumpkinBlock.FACING, direction), 11);
            Block.popResourceFromFace(level, pos, direction, new ItemStack(Items.PUMPKIN_SEEDS, 4));
            if (!player.getAbilities().instabuild) stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return JNEConfigs.SORROWSQUASH_TAINTING.get() && level.getBlockState(pos.below()).is(JNETags.Blocks.SORROWEED_REPLACEABLE);
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

    @Override
    public DamageSource getFallDamageSource(Entity entity) {
        return entity.damageSources().source(JNEDamageSources.SORROWSQUISHED, entity);
    }
}
