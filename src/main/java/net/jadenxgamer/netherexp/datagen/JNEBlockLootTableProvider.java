package net.jadenxgamer.netherexp.datagen;

import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class JNEBlockLootTableProvider extends BlockLootSubProvider {
    protected JNEBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        add(JNEBlocks.DAMP_SILTMARRAM.get(), createShearsOnlyDrop(JNEBlocks.DAMP_SILTMARRAM.get()));
        add(JNEBlocks.MOIST_SILTMARRAM.get(), createShearsOnlyDrop(JNEBlocks.MOIST_SILTMARRAM.get()));
        add(JNEBlocks.DRY_SILTMARRAM.get(), createShearsOnlyDrop(JNEBlocks.DRY_SILTMARRAM.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Set.of(
                JNEBlocks.DAMP_SILTMARRAM.get(),
                JNEBlocks.MOIST_SILTMARRAM.get(),
                JNEBlocks.DRY_SILTMARRAM.get()
        );
    }
}
