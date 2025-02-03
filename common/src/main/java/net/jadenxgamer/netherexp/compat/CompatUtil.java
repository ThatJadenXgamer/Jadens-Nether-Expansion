package net.jadenxgamer.netherexp.compat;

import dev.architectury.platform.Platform;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class CompatUtil {

    public static String CINDERSCAPES = "cinderscapes";
    public static String GARDENS_OF_THE_DEAD = "gardens_of_the_dead";
    public static String INFERNAL_EXPANSION = "infernalexp";
    public static final String GREED_AND_BLEED = "greedandbleed";
    public static final String BIOMES_O_PLENTY = "biomesoplenty";
    public static final String QUARK = "quark";
    public static final String DICEY_VENTURE = "dicey_ventures";
    public static final String FARMERS_DELIGHT = "farmersdelight";
    public static final String MY_NETHERS_DELIGHT = "mynethersdelight";
    public static final String OREGANIZED = "oreganized";
    public static final String ALEXS_CAVES = "alexscaves";
    public static final String ALEXS_MOBS = "alexsmobs";
    public static final String ARTS_AND_CRAFTS = "arts_and_crafts";
    public static final String CAVERNS_AND_CHASMS = "caverns_and_chasms";
    public static final String SULLYS_MOD = "sullysmod";
    public static final String SUPPLEMENTARIES = "supplementaries";
    public static final String RUBINATED_NETHER = "rubinated_nether";
    public static final String GALOSPHERE = "galosphere";

    public static boolean checkCinderscapes() {
        return Platform.isModLoaded(CINDERSCAPES);
    }

    public static boolean checkGardensOfTheDead() {
        return Platform.isModLoaded(GARDENS_OF_THE_DEAD);
    }

    public static boolean checkInfernalExpansion() {
        return Platform.isModLoaded(INFERNAL_EXPANSION);
    }

    public static boolean checkGreedAndBleed() {
        return Platform.isModLoaded(GREED_AND_BLEED);
    }

    public static boolean checkBiomesOPlenty() {
        return Platform.isModLoaded(BIOMES_O_PLENTY);
    }

    public static boolean checkQuark() {
        return Platform.isModLoaded(QUARK);
    }

    public static boolean checkDiceyVentures() {
        return Platform.isModLoaded(DICEY_VENTURE);
    }

    public static boolean checkFarmersDelight() {
        return Platform.isModLoaded(FARMERS_DELIGHT);
    }

    public static boolean checkNethersDelight() {
        return Platform.isModLoaded(MY_NETHERS_DELIGHT);
    }

    public static boolean checkOreganized() {
        return Platform.isModLoaded(OREGANIZED);
    }

    public static boolean checkAlexsCaves() {
        return Platform.isModLoaded(ALEXS_CAVES);
    }

    public static boolean checkAlexsMobs() {
        return Platform.isModLoaded(ALEXS_MOBS);
    }

    public static boolean checkArtsAndCrafts() {
        return Platform.isModLoaded(ARTS_AND_CRAFTS);
    }

    public static boolean checkCavernsAndChasms() {
        return Platform.isModLoaded(CAVERNS_AND_CHASMS);
    }

    public static boolean checkSullysMod() {
        return Platform.isModLoaded(SULLYS_MOD);
    }

    public static boolean checkSupplementaries() {
        return Platform.isModLoaded(SUPPLEMENTARIES);
    }

    public static boolean checkRubinatedNether() {
        return Platform.isModLoaded(RUBINATED_NETHER);
    }

    public static boolean checkAnySilverMod() {
        return checkOreganized() || checkCavernsAndChasms() || Platform.isModLoaded(GALOSPHERE);
    }

    public static class BiomeKeys {
        public static final ResourceKey<Biome> SOULBLIGHT_FOREST = register(CompatUtil.GARDENS_OF_THE_DEAD, "soulblight_forest");

        private static ResourceKey<Biome> register(String namespace, String id) {
            return ResourceKey.create(Registries.BIOME, new ResourceLocation(namespace, id));
        }
    }

    public static class Registry {

        public static Block getBlock(ResourceLocation id) {
            return BuiltInRegistries.BLOCK.get(id);
        }

        public static Item getItem(ResourceLocation id) {
            return BuiltInRegistries.ITEM.get(id);
        }
    }
}
