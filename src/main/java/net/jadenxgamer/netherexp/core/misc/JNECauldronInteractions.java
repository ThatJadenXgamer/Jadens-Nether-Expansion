package net.jadenxgamer.netherexp.core.misc;

import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNEFluids;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public interface JNECauldronInteractions extends CauldronInteraction {

    CauldronInteraction FILL_ECTOPLASM = (state, world, pos, player, hand, stack) ->
            CauldronInteraction.emptyBucket(world, pos, player, hand, stack, JNEBlocks.ECTOPLASM_CAULDRON.get().defaultBlockState(), JNESoundEvents.BUCKET_EMPTY_ECTOPLASM.get());

    CauldronInteraction EMPTY_ECTOPLASM = (state, world, pos, player, hand, stack) ->
            CauldronInteraction.fillBucket(state, world, pos, player, hand, stack, new ItemStack(JNEFluids.ECTOPLASM_BUCKET.get()), blockState -> true, JNESoundEvents.BUCKET_FILL_ECTOPLASM.get());

    CauldronInteraction.InteractionMap ECTOPLASM = CauldronInteraction.newInteractionMap("ectoplasm");

    static void register() {
        EMPTY.map().put(JNEFluids.ECTOPLASM_BUCKET.get(), FILL_ECTOPLASM);
        WATER.map().put(JNEFluids.ECTOPLASM_BUCKET.get(), FILL_ECTOPLASM);
        LAVA.map().put(JNEFluids.ECTOPLASM_BUCKET.get(), FILL_ECTOPLASM);
        POWDER_SNOW.map().put(JNEFluids.ECTOPLASM_BUCKET.get(), FILL_ECTOPLASM);
        ECTOPLASM.map().put(JNEFluids.ECTOPLASM_BUCKET.get(), FILL_ECTOPLASM);

        ECTOPLASM.map().put(Items.BUCKET, EMPTY_ECTOPLASM);

        CauldronInteraction.addDefaultInteractions(ECTOPLASM.map());
    }
}
