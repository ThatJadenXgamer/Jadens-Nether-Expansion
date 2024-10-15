package net.jadenxgamer.netherexp.registry.item.custom;

import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.block.custom.HeadbbageBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class HeadbbageSeedItem extends Item {
    public HeadbbageSeedItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();
        BlockPos pos;
        Level level = context.getLevel();
        BlockState state = level.getBlockState(pos = context.getClickedPos());
        if (state.is(Blocks.SKELETON_SKULL)) {
            int rotation = level.getBlockState(pos).getValue(HeadbbageBlock.ROTATION);
            level.setBlock(pos, JNEBlocks.HEADBBAGE_SKULL.get().defaultBlockState().setValue(HeadbbageBlock.ROTATION, rotation), 2);
            level.playSound(player, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.CROP_PLANTED, SoundSource.BLOCKS, 1.0f, 1.0f);
            if (player != null) {
                stack.shrink(1);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useOn(context);
    }
}
