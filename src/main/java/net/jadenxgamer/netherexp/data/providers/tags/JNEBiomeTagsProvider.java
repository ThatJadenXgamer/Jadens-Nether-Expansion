package net.jadenxgamer.netherexp.data.providers.tags;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.data.JNEDataGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public final class JNEBiomeTagsProvider extends BiomeTagsProvider {

    /**
     * Create a new biome tags provider.
     *
     * @param output             the output location
     * @param registries         a {@linkplain CompletableFuture} supplying the registries
     * @param existingFileHelper a {@linkplain ExistingFileHelper} to find existing files
     */
    public JNEBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, registries, NetherExp.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        tag(JNETags.Biomes.SOUL_SAND_VALLEYS)
                .add(Biomes.SOUL_SAND_VALLEY)
        // TODO: IMPLEMENT BIOMES
        // .add(JNEBiomes.BLACK_ICE_GLACIERS)
        // .add(JNEBiomes.SORROWSQUASH_PASTURES)
        ;

        tag(JNETags.Biomes.HAS_ASH).add(Biomes.SOUL_SAND_VALLEY).addOptional(ResourceLocation.fromNamespaceAndPath("cinderscapes", "ashy_shoals"));
        tag(JNETags.Biomes.HAS_CRIMSON_SPORES).add(Biomes.CRIMSON_FOREST).addOptional(ResourceLocation.fromNamespaceAndPath("gardens_of_the_dead", "whistling_woods"));
        tag(JNETags.Biomes.HAS_WARPED_SPORES).add(Biomes.WARPED_FOREST).addOptional(ResourceLocation.fromNamespaceAndPath("cinderscapes", "luminous_grove"));
        tag(JNETags.Biomes.HAS_WHITE_ASH).add(Biomes.BASALT_DELTAS).addOptional(ResourceLocation.fromNamespaceAndPath("cinderscapes", "blackstone_shales"));

        tag(JNETags.Biomes.HAS_CHAPEL)
                .add(Biomes.SOUL_SAND_VALLEY)
                .add(Biomes.NETHER_WASTES)
                .add(Biomes.CRIMSON_FOREST)
                .add(Biomes.WARPED_FOREST)
                // TODO: IMPLEMENT BIOMES
                // .add(JNEBiomes.BLACK_ICE_GLACIERS)
                // .add(JNEBiomes.SORROWSQUASH_PASTURES)
                .addOptional(ResourceLocation.fromNamespaceAndPath("cinderscapes", "ashy_shoals"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("cinderscapes", "blackstone_shales"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("cinderscapes", "luminous_grove"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("gardens_of_the_dead", "soulblight_forest"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("soulfulnether", "fright_forest"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored", "mycotoxic_undergrowth"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored", "glistering_meadow"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored", "infernal_holt"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored", "blackstone_basin"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("biomesoplenty", "crystalline_chasm"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("biomesoplenty", "undergrowth"));
        tag(JNETags.Biomes.HAS_DEVILS_BLUFF).add(Biomes.NETHER_WASTES).addOptional(ResourceLocation.fromNamespaceAndPath("biomesoplenty", "crystalline_chasm")).addOptional(ResourceLocation.fromNamespaceAndPath("biomesoplenty", "visceral_heap"));
        // TODO: IMPLEMENT BLACK ICE GLACIERS
        // tag(JNETags.Biomes.HAS_ICE_RIBS).add(JNEBiomes.BLACK_ICE_GLACIERS);
        tag(JNETags.Biomes.HAS_ICE_RIBS)
                .add(Biomes.SOUL_SAND_VALLEY)
        // TODO: IMPLEMENT BLACK ICE GLACIERS
        // .add(JNEBiomes.BLACK_ICE_GLACIERS)
        ;
        tag(JNETags.Biomes.HAS_MEGA_FOSSIL)
                .add(Biomes.SOUL_SAND_VALLEY)
        // TODO: IMPLEMENT BLACK ICE GLACIERS
        // .add(JNEBiomes.BLACK_ICE_GLACIERS)
        ;
        tag(JNETags.Biomes.HAS_QUARTZ_SPELEOTHEM).add(Biomes.NETHER_WASTES);
        tag(JNETags.Biomes.HAS_SANCTUM)
                .add(Biomes.SOUL_SAND_VALLEY)
                // TODO: IMPLEMENT BIOMES
                // .add(JNEBiomes.BLACK_ICE_GLACIERS)
                // .add(JNEBiomes.SORROWSQUASH_PASTURES)
                .addOptional(ResourceLocation.fromNamespaceAndPath("cinderscapes", "ashy_shoals"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("cinderscapes", "blackstone_shales"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("gardens_of_the_dead", "soulblight_forest"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("soulfulnether", "fright_forest"));

        tag(BiomeTags.HAS_BASTION_REMNANT)
        // TODO: IMPLEMENT BIOMES
        // .add(JNEBiomes.EXHAUST_MIRE)
        // .add(JNEBiomes.BLACK_ICE_GLACIERS)
        ;
        tag(BiomeTags.HAS_NETHER_FOSSIL)
        // TODO: IMPLEMENT BLACK ICE GLACIERS
        // .add(JNEBiomes.BLACK_ICE_GLACIERS)
        ;
        tag(BiomeTags.IS_NETHER)
        // TODO: IMPLEMENT BIOMES
        // .add(JNEBiomes.BLACK_ICE_GLACIERS)
        // .add(JNEBiomes.EXHAUST_MIRE)
        // .add(JNEBiomes.SORROWSQUASH_PASTURES)
        ;
        tag(BiomeTags.SNOW_GOLEM_MELTS)
                .remove(Biomes.SOUL_SAND_VALLEY)
        // TODO: IMPLEMENT EXHAUST MIRE
        // .add(JNEBiomes.EXHAUST_MIRE)
        ;
        tag(BiomeTags.SPAWNS_COLD_VARIANT_FROGS)
                .add(Biomes.SOUL_SAND_VALLEY)
        // TODO: IMPLEMENT BLACK ICE GLACIERS
        // .add(JNEBiomes.BLACK_ICE_GLACIERS)
        ;
        tag(BiomeTags.SPAWNS_WARM_VARIANT_FROGS).remove(Biomes.SOUL_SAND_VALLEY);

    }
}
