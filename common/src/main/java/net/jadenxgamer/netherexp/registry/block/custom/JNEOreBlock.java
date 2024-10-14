package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.advancements.JNECriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class JNEOreBlock extends DropExperienceBlock {

    private final int type;

    public JNEOreBlock(Properties properties, IntProvider intProvider, int type) {
        super(properties, intProvider);
        this.type = type;
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        super.playerDestroy(level, player, pos, state, blockEntity, stack);
        if (type == 1) {
            JNECriteriaTriggers.BROKEN_DIAMOND_FOSSIL_ORE.trigger((ServerPlayer) player);
        }
        else {
            JNECriteriaTriggers.BROKEN_FOSSIL_FUEL_ORE.trigger((ServerPlayer) player);
        }
    }
}
