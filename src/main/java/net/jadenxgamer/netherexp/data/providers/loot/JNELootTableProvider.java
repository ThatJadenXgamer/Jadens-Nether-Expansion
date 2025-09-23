package net.jadenxgamer.netherexp.data.providers.loot;

import net.jadenxgamer.netherexp.data.providers.loot.packs.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class JNELootTableProvider extends LootTableProvider {

    public JNELootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper fileHelper) {
        super(output, Set.of(), List.of(
                new SubProviderEntry(JNEArchaeologyLoot::new, LootContextParamSets.ARCHAEOLOGY),
                new SubProviderEntry(JNEBrazierChestLoot::new, LootContextParamSets.EMPTY),
                new SubProviderEntry(JNEBlockLoot::new, LootContextParamSets.BLOCK),
                new SubProviderEntry(JNEChestLoot::new, LootContextParamSets.CHEST),
                new SubProviderEntry(JNEEntityLoot::new, LootContextParamSets.ENTITY)
        ), registries);
    }
}
