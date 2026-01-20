package net.jadenxgamer.netherexp.data;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.data.worldgen.placement.JNEMiscPlacement;
import net.jadenxgamer.netherexp.data.worldgen.placement.JNESoulSandValleyPlacement;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public class JNEBiomeModifiers {

    public static final class Misc {

        public static final ResourceKey<BiomeModifier> NETHERRACK_SPELEOTHEM = registerKey("netherrack_speleothem");
        public static final ResourceKey<BiomeModifier> SOUL_SOIL_SPELEOTHEM = registerKey("soul_soil_speleothem");

        public static ResourceKey<BiomeModifier> registerKey(String name) {
            return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, NetherExp.id(name));
        }

        public static void bootstrap(BootstrapContext<BiomeModifier> context) {

            HolderGetter<Biome> biomeHolderGetter = context.lookup(Registries.BIOME);
            HolderGetter<PlacedFeature> featureHolderGetter = context.lookup(Registries.PLACED_FEATURE);

            context.register(
                    NETHERRACK_SPELEOTHEM, new BiomeModifiers.AddFeaturesBiomeModifier(
                            biomeHolderGetter.getOrThrow(BiomeTags.IS_NETHER),
                            HolderSet.direct(featureHolderGetter.getOrThrow(JNEMiscPlacement.NETHERRACK_SPELEOTHEM)),
                            GenerationStep.Decoration.RAW_GENERATION));

            context.register(
                    SOUL_SOIL_SPELEOTHEM, new BiomeModifiers.AddFeaturesBiomeModifier(
                            HolderSet.direct(biomeHolderGetter.getOrThrow(Biomes.SOUL_SAND_VALLEY)),
                            HolderSet.direct(featureHolderGetter.getOrThrow(JNEMiscPlacement.SOUL_SOIL_SPELEOTHEM)),
                            GenerationStep.Decoration.RAW_GENERATION));
        }
    }

    public static final class SoulSandValley {

        public static final ResourceKey<BiomeModifier> BONE_PIKE = registryKey("bone_pike");
        public static final ResourceKey<BiomeModifier> ECTO_SOUL_SAND = registryKey("ecto_soul_sand");
        public static final ResourceKey<BiomeModifier> ECTOPLASM_LAKE = registryKey("ectoplasm_lake");
        public static final ResourceKey<BiomeModifier> FOSSIL_FUEL_ORE = registryKey("fossil_fuel_ore");
        public static final ResourceKey<BiomeModifier> FOSSIL_ORE = registryKey("fossil_ore");
        public static final ResourceKey<BiomeModifier> HANGING_MOUND = registryKey("hanging_mound");
        public static final ResourceKey<BiomeModifier> MOUND = registryKey("mound");
        public static final ResourceKey<BiomeModifier> ORE_SOUL_MAGMA = registryKey("ore_soul_magma");
        public static final ResourceKey<BiomeModifier> PALE_SOUL_SLATE = registryKey("pale_soul_slate");
        public static final ResourceKey<BiomeModifier> PALE_SOUL_SLATE_SURFACE = registryKey("pale_soul_slate_surface");
        public static final ResourceKey<BiomeModifier> SOUL_SWIRLS_CEILING = registryKey("soul_swirls_ceiling");
        public static final ResourceKey<BiomeModifier> SOUL_SWIRLS_FLOOR = registryKey("soul_swirls_floor");

        public static final ResourceKey<BiomeModifier> APPARITION = registryKey("apparition");
        public static final ResourceKey<BiomeModifier> VESSEL = registryKey("vessel");

        public static final ResourceKey<BiomeModifier> APPARITION_SPAWN_COST = registryKey("spawn_cost/apparition");
        public static final ResourceKey<BiomeModifier> VESSEL_SPAWN_COST = registryKey("spawn_cost/vessel");
        public static final ResourceKey<BiomeModifier> STRAY_SPAWN_COST = registryKey("spawn_cost/stray");

        public static ResourceKey<BiomeModifier> registryKey(String name) {
            return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, NetherExp.id("soul_sand_valley/" + name));
        }

        public static void bootstrap(BootstrapContext<BiomeModifier> context) {
            featureModifier(context, BONE_PIKE, JNESoulSandValleyPlacement.BONE_PIKE, GenerationStep.Decoration.UNDERGROUND_DECORATION);
            featureModifier(context, ECTO_SOUL_SAND, JNESoulSandValleyPlacement.ECTO_SOUL_SAND, GenerationStep.Decoration.VEGETAL_DECORATION);
            featureModifier(context, ECTOPLASM_LAKE, JNESoulSandValleyPlacement.ECTOPLASM_LAKE, GenerationStep.Decoration.VEGETAL_DECORATION);

            // Could these be made {@link GenerationStep.Decoration.UNDERGROUND_ORES} ?
            featureModifier(context, FOSSIL_FUEL_ORE, JNESoulSandValleyPlacement.FOSSIL_FUEL_ORE, GenerationStep.Decoration.UNDERGROUND_DECORATION);
            featureModifier(context, FOSSIL_ORE, JNESoulSandValleyPlacement.FOSSIL_ORE, GenerationStep.Decoration.UNDERGROUND_DECORATION);
            featureModifier(context, HANGING_MOUND, JNESoulSandValleyPlacement.HANGING_MOUND, GenerationStep.Decoration.RAW_GENERATION);
            featureModifier(context, MOUND, JNESoulSandValleyPlacement.MOUND, GenerationStep.Decoration.RAW_GENERATION);
            featureModifier(context, ORE_SOUL_MAGMA, JNESoulSandValleyPlacement.ORE_SOUL_MAGMA, GenerationStep.Decoration.UNDERGROUND_DECORATION);
            featureModifier(context, PALE_SOUL_SLATE, JNESoulSandValleyPlacement.PALE_SOUL_SLATE, GenerationStep.Decoration.RAW_GENERATION);
            featureModifier(context, PALE_SOUL_SLATE_SURFACE, JNESoulSandValleyPlacement.PALE_SOUL_SLATE_SURFACE, GenerationStep.Decoration.RAW_GENERATION);
            featureModifier(context, SOUL_SWIRLS_CEILING, JNESoulSandValleyPlacement.SOUL_SWIRLS_CEILING, GenerationStep.Decoration.VEGETAL_DECORATION);
            featureModifier(context, SOUL_SWIRLS_FLOOR, JNESoulSandValleyPlacement.SOUL_SWIRLS_FLOOR, GenerationStep.Decoration.VEGETAL_DECORATION);

            spawnModifier(context, APPARITION, JNEEntityType.APPARITION.get());
            spawnModifier(context, VESSEL, JNEEntityType.VESSEL.get());

            spawnCostModifier(context, APPARITION_SPAWN_COST, JNEEntityType.APPARITION.get());
            spawnCostModifier(context, VESSEL_SPAWN_COST, JNEEntityType.VESSEL.get());
            spawnCostModifier(context, STRAY_SPAWN_COST, EntityType.STRAY);
        }

        private static void featureModifier(
                BootstrapContext<BiomeModifier> context,
                ResourceKey<BiomeModifier> key,
                ResourceKey<PlacedFeature> feature,
                GenerationStep.Decoration step
        ) {
            HolderGetter<Biome> biomeHolderGetter = context.lookup(Registries.BIOME);
            HolderGetter<PlacedFeature> featureHolderGetter = context.lookup(Registries.PLACED_FEATURE);

            context.register(
                    key,
                    new BiomeModifiers.AddFeaturesBiomeModifier(
                            HolderSet.direct(biomeHolderGetter.getOrThrow(Biomes.SOUL_SAND_VALLEY)),
                            HolderSet.direct(featureHolderGetter.getOrThrow(feature)),
                            step));
        }

        private static void spawnModifier(BootstrapContext<BiomeModifier> context, ResourceKey<BiomeModifier> key, EntityType<?> entityType) {
            HolderGetter<Biome> biomeHolderGetter = context.lookup(Registries.BIOME);
            HolderGetter<PlacedFeature> featureHolderGetter = context.lookup(Registries.PLACED_FEATURE);

            context.register(
                    key,
                    new BiomeModifiers.AddSpawnsBiomeModifier(
                            HolderSet.direct(biomeHolderGetter.getOrThrow(Biomes.SOUL_SAND_VALLEY)),
                            List.of(new MobSpawnSettings.SpawnerData(entityType, 30, 1, 1))));
        }

        private static void spawnCostModifier(BootstrapContext<BiomeModifier> context, ResourceKey<BiomeModifier> key, EntityType<?> entityType) {
            HolderGetter<Biome> biomeHolderGetter = context.lookup(Registries.BIOME);
            HolderGetter<PlacedFeature> featureHolderGetter = context.lookup(Registries.PLACED_FEATURE);

            context.register(
                    key, new BiomeModifiers.AddSpawnCostsBiomeModifier(
                            HolderSet.direct(biomeHolderGetter.getOrThrow(Biomes.SOUL_SAND_VALLEY)),
                            HolderSet.direct(BuiltInRegistries.ENTITY_TYPE::wrapAsHolder, entityType),
                            new MobSpawnSettings.MobSpawnCost(0.15d, 0.7d)));
        }

    }

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        Misc.bootstrap(context);
        SoulSandValley.bootstrap(context);
    }
}
