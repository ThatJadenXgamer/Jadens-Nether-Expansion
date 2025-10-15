package net.jadenxgamer.netherexp.data;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.keys.JNEDamageTypes;
import net.jadenxgamer.netherexp.core.keys.JNEJukeboxSongs;
import net.jadenxgamer.netherexp.core.keys.JNETrimPatterns;
import net.jadenxgamer.netherexp.core.worldgen.feature.JNEPlacedFeatures;
import net.jadenxgamer.netherexp.core.worldgen.feature.JNEConfiguredFeatures;
import net.jadenxgamer.netherexp.data.providers.JNEAdvancementProvider;
import net.jadenxgamer.netherexp.data.providers.JNEDataMapProvider;
import net.jadenxgamer.netherexp.data.providers.loot.JNELootTableProvider;
import net.jadenxgamer.netherexp.data.providers.JNERecipeProvider;
import net.jadenxgamer.netherexp.data.providers.tags.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class JNEDataGen {

    /**
     * Generates Datapack Registry Data.
     * Always called.
     * @param event propagated
     */
    public static void registryData(GatherDataEvent event) {
        event.createDatapackRegistryObjects(
                new RegistrySetBuilder()
                        .add(Registries.DAMAGE_TYPE, JNEDamageTypes::bootstrap)
                        .add(Registries.TRIM_PATTERN, JNETrimPatterns::bootstrap)
                        .add(Registries.CONFIGURED_FEATURE, JNEFeatures::bootstrap)
                        .add(Registries.PLACED_FEATURE, JNEPlacement::bootstrap)
                        .add(Registries.JUKEBOX_SONG, JNEJukeboxSongs::bootstrap),
                Set.of(NetherExp.MOD_ID, "minecraft"));
    }


    /**
     * Generates Server Data.
     * Called if {@link GatherDataEvent#includeServer()} is true.
     * @param event propagated
     * @param output the generators output
     * @param fileHelper the events file helper
     */
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
                JNEPaintingVariantTagsProvider::new,
                JNEStructureTagsProvider::new
        );

        factories.forEach(factory -> event.addProvider(
                factory.makeDataProvider(output, lookupProvider, fileHelper)
        ));

        // We need this for the ItemTagProvider
        TagsProvider<Block> blockTagsProvider = event.addProvider(new JNEBlockTagsProvider(output, lookupProvider, fileHelper));

        event.addProvider(new JNEItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), fileHelper));
        event.addProvider(new JNEDataMapProvider(output, lookupProvider));
    }

    /**
     * Helper Interface
     */
    public interface DataProviderFactory {
        DataProvider makeDataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper fileHelper);
    }

}
