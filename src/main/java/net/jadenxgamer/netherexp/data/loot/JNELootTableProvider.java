package net.jadenxgamer.netherexp.data.loot;

import net.jadenxgamer.netherexp.data.JNEDataGen;
import net.jadenxgamer.netherexp.data.loot.packs.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class JNELootTableProvider extends LootTableProvider {

    /**
     * Create a new loot table provider.
     *
     * @param output             the output location
     * @param registries         a {@linkplain CompletableFuture} supplying the registries
     * @param existingFileHelper to confirm with {@linkplain JNEDataGen.DataProviderFactory}
     */
    public JNELootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, Set.of(), List.of(
                // we pass all sub providers to the super constructor, with the type of loot tables they generate
                new SubProviderEntry(JNEArchaeologyLoot::new, LootContextParamSets.ARCHAEOLOGY),
                new SubProviderEntry(JNEBrazierChestLoot::new, LootContextParamSets.EMPTY),
                new SubProviderEntry(JNEBlockLoot::new, LootContextParamSets.BLOCK),
                new SubProviderEntry(JNEChestLoot::new, LootContextParamSets.CHEST),
                new SubProviderEntry(JNEEntityLoot::new, LootContextParamSets.ENTITY)
        ), registries);
    }
}
