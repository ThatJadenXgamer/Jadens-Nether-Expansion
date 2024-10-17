package net.jadenxgamer.netherexp.mixin.item;

import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin {

    @Inject(
            method = "useOn",
            at = @At("HEAD"),
            cancellable = true
    )
    private void netherexp$useOn(UseOnContext useOnContext, CallbackInfoReturnable<InteractionResult> cir) {
        /* Because of forge's sheer stupidity, right click functionality like stripping need to be done though the block class
         * and obviously will not do because we're on multiloader and will cause issues on fabric
         * thus why this mixin exists
        */
        Level level = useOnContext.getLevel();
        BlockState state = level.getBlockState(useOnContext.getClickedPos());
        Player player = useOnContext.getPlayer();
        if (state.is(JNEBlocks.CEREBRAGE_CLARET_STEM.get())) {
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);
            level.setBlock(useOnContext.getClickedPos(), JNEBlocks.STRIPPED_CLARET_STEM.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)), 2);
            if (!player.isCreative()) {
                useOnContext.getItemInHand().hurtAndBreak(1, player, p -> p.broadcastBreakEvent(useOnContext.getHand()));
            }
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
        else if (state.is(JNEBlocks.CEREBRAGE_CLARET_HYPHAE.get())) {
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);
            level.setBlock(useOnContext.getClickedPos(), JNEBlocks.STRIPPED_CLARET_HYPHAE.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)), 2);
            if (!player.isCreative()) {
                useOnContext.getItemInHand().hurtAndBreak(1, player, p -> p.broadcastBreakEvent(useOnContext.getHand()));
            }
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
        else if (state.is(JNEBlocks.SMOKESTALK_BLOCK.get())) {
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);
            level.setBlock(useOnContext.getClickedPos(), JNEBlocks.STRIPPED_SMOKESTALK_BLOCK.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)), 2);
            if (!player.isCreative()) {
                useOnContext.getItemInHand().hurtAndBreak(1, player, p -> p.broadcastBreakEvent(useOnContext.getHand()));
            }
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}
