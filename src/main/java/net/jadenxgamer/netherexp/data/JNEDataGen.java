package net.jadenxgamer.netherexp.data;

import net.jadenxgamer.netherexp.data.providers.JNEAdvancementProvider;
import net.jadenxgamer.netherexp.data.providers.JNEDataMapProvider;
import net.jadenxgamer.netherexp.data.providers.loot.JNELootTableProvider;
import net.jadenxgamer.netherexp.data.providers.JNERecipeProvider;
import net.jadenxgamer.netherexp.data.providers.tags.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class JNEDataGen {

    // Gets called only if includeServer, so no checking is done.
    public static void serverData(GatherDataEvent event, PackOutput output, ExistingFileHelper fileHelper) {
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        List<DataProviderFactory> factories = List.of(
                JNEAdvancementProvider::new,
                JNELootTableProvider::new,
                JNERecipeProvider::new,
                JNEDamageTypeTagsProvider::new,
                JNEEntityTypeTagsProvider::new,
                JNEFluidTypeTagsProvider::new,
                JNEBiomeTagsProvider::new,
                JNEPaintingVariantTagsProvider::new
        );

        factories.forEach(factory -> event.addProvider(
                factory.makeDataProvider(output, lookupProvider, fileHelper)
        ));

        // We need this for the ItemTagProvider
        TagsProvider<Block> blockTagsProvider = event.addProvider(new JNEBlockTagsProvider(output, lookupProvider, fileHelper));

        event.addProvider(new JNEItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), fileHelper));
        event.addProvider(new JNEDataMapProvider(output, lookupProvider));
    }

    private interface DataProviderFactory {
        DataProvider makeDataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper fileHelper);
    }

}
