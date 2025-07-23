package net.jadenxgamer.netherexp.core.item;

import net.jadenxgamer.netherexp.core.block.CerebrageSkullBlock;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNECriteriaTriggers;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
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
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        BlockState state = level.getBlockState(pos);
        if (state.is(Blocks.SKELETON_SKULL)) {
            int rotation = level.getBlockState(pos).getValue(CerebrageSkullBlock.ROTATION);
            level.setBlock(pos, JNEBlocks.CEREBRAGE_SKULL.get().defaultBlockState().setValue(CerebrageSkullBlock.ROTATION, rotation), CerebrageSkullBlock.UPDATE_ALL);
            level.playSound(player, pos, JNESoundEvents.CEREBRAGE_PLANT.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            if (player != null && !player.getAbilities().instabuild) stack.shrink(1);
            if (player instanceof ServerPlayer serverPlayer) JNECriteriaTriggers.PLANTED_CEREBRAGE.get().trigger(serverPlayer);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useOn(context);
    }
}
