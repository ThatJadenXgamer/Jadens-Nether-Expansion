package net.jadenxgamer.netherexp.registry.item.custom;

import net.jadenxgamer.netherexp.registry.misc_registry.ModTags;
import net.jadenxgamer.netherexp.registry.sound.ModSoundEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class EctoplasmBucketItem extends BucketItem {
    private final Fluid fluid;

    public EctoplasmBucketItem(Fluid fluid, Settings settings) {
        super(fluid, settings);
        this.fluid = fluid;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void playEmptyingSound(@Nullable PlayerEntity player, WorldAccess world, BlockPos pos) {
        SoundEvent soundEvent = this.fluid.isIn(ModTags.Fluids.ECTOPLASM) ? ModSoundEvents.ITEM_BUCKET_EMPTY_ECTOPLASM : SoundEvents.ITEM_BUCKET_EMPTY;
        world.playSound(player, pos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
        world.emitGameEvent(player, GameEvent.FLUID_PLACE, pos);
    }
}
