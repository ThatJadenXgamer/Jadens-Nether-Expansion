package net.jadenxgamer.netherexp.registry.item.custom;

import net.jadenxgamer.netherexp.registry.advancements.JNECriteriaTriggers;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.block.custom.CerebrageBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
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

public class CerebrageSeedItem extends Item {
    public CerebrageSeedItem(Properties properties) {
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
            int rotation = level.getBlockState(pos).getValue(CerebrageBlock.ROTATION);
            level.setBlock(pos, JNEBlocks.CEREBRAGE_SKULL.get().defaultBlockState().setValue(CerebrageBlock.ROTATION, rotation), 2);
            level.playSound(player, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.CROP_PLANTED, SoundSource.BLOCKS, 1.0f, 1.0f);
            if (player != null && !player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            if (player instanceof ServerPlayer serverPlayer) {
                JNECriteriaTriggers.PLANTED_CEREBRAGE.trigger(serverPlayer);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useOn(context);
    }
}
