package net.jadenxgamer.netherexp.datagen;

import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Set;

public class JNEBlockLootTableProvider extends BlockLootSubProvider {
    protected JNEBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(JNEBlocks.PYROCLAST.get());
        this.add(JNEBlocks.PYROCLAST_SLAB.get(), this::createSlabItemTable);
        dropSelf(JNEBlocks.PYROCLAST_SLAB.get());
        dropSelf(JNEBlocks.PYROCLAST_STAIRS.get());
        dropSelf(JNEBlocks.PYROCLAST_WALL.get());
        dropSelf(JNEBlocks.SILT.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Set.of(
                JNEBlocks.PYROCLAST.get(),
                JNEBlocks.PYROCLAST_SLAB.get(),
                JNEBlocks.PYROCLAST_STAIRS.get(),
                JNEBlocks.PYROCLAST_WALL.get(),
                JNEBlocks.SILT.get()
        );
    }
}
