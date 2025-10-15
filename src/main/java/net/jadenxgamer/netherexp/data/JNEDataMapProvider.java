package net.jadenxgamer.netherexp.data;

import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class JNEDataMapProvider extends DataMapProvider {

    /**
     * Create a new data map provider.
     *
     * @param packOutput     the output location
     * @param lookupProvider a {@linkplain CompletableFuture} supplying the registries
     */
    public JNEDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(BuiltInRegistries.ITEM.wrapAsHolder(JNEItems.FOSSIL_FUEL.get()), new FurnaceFuel(1600), false)
                .remove(BuiltInRegistries.ITEM.wrapAsHolder(JNEBlocks.CEREBRAGE_CLARET_STEM.get().asItem()))
                .remove(BuiltInRegistries.ITEM.wrapAsHolder(JNEBlocks.CEREBRAGE_CLARET_HYPHAE.get().asItem()))
                .remove(BuiltInRegistries.ITEM.wrapAsHolder(JNEBlocks.STRIPPED_CLARET_STEM.get().asItem()))
                .remove(BuiltInRegistries.ITEM.wrapAsHolder(JNEBlocks.STRIPPED_CLARET_HYPHAE.get().asItem()))
                .remove(BuiltInRegistries.ITEM.wrapAsHolder(JNEBlocks.CLARET_PLANKS.get().asItem()))
                .remove(BuiltInRegistries.ITEM.wrapAsHolder(JNEBlocks.CLARET_SLAB.get().asItem()))
                .remove(BuiltInRegistries.ITEM.wrapAsHolder(JNEBlocks.CLARET_STAIRS.get().asItem()))
                .remove(BuiltInRegistries.ITEM.wrapAsHolder(JNEBlocks.CLARET_FENCE.get().asItem()))
                .remove(BuiltInRegistries.ITEM.wrapAsHolder(JNEBlocks.CLARET_FENCE_GATE.get().asItem()))
                .remove(BuiltInRegistries.ITEM.wrapAsHolder(JNEBlocks.CLARET_DOOR.get().asItem()))
                .remove(BuiltInRegistries.ITEM.wrapAsHolder(JNEBlocks.CLARET_TRAPDOOR.get().asItem()))
                .remove(BuiltInRegistries.ITEM.wrapAsHolder(JNEBlocks.CLARET_BUTTON.get().asItem()))
                .remove(BuiltInRegistries.ITEM.wrapAsHolder(JNEBlocks.CLARET_PRESSURE_PLATE.get().asItem()));
    }
}
