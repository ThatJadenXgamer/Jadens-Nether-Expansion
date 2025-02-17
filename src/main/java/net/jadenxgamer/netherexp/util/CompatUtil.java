package net.jadenxgamer.netherexp.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.fml.loading.FMLLoader;

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
        return FMLLoader.getLoadingModList().getModFileById(CINDERSCAPES) != null;
    }

    public static boolean checkGardensOfTheDead() {
        return FMLLoader.getLoadingModList().getModFileById(GARDENS_OF_THE_DEAD) != null;
    }

    public static boolean checkInfernalExpansion() {
        return FMLLoader.getLoadingModList().getModFileById(INFERNAL_EXPANSION) != null;
    }

    public static boolean checkGreedAndBleed() {
        return FMLLoader.getLoadingModList().getModFileById(GREED_AND_BLEED) != null;
    }

    public static boolean checkBiomesOPlenty() {
        return FMLLoader.getLoadingModList().getModFileById(BIOMES_O_PLENTY) != null;
    }

    public static boolean checkQuark() {
        return FMLLoader.getLoadingModList().getModFileById(QUARK) != null;
    }

    public static boolean checkDiceyVentures() {
        return FMLLoader.getLoadingModList().getModFileById(DICEY_VENTURE) != null;
    }

    public static boolean checkFarmersDelight() {
        return FMLLoader.getLoadingModList().getModFileById(FARMERS_DELIGHT) != null;
    }

    public static boolean checkNethersDelight() {
        return FMLLoader.getLoadingModList().getModFileById(MY_NETHERS_DELIGHT) != null;
    }

    public static boolean checkOreganized() {
        return FMLLoader.getLoadingModList().getModFileById(OREGANIZED) != null;
    }

    public static boolean checkAlexsCaves() {
        return FMLLoader.getLoadingModList().getModFileById(ALEXS_CAVES) != null;
    }

    public static boolean checkAlexsMobs() {
        return FMLLoader.getLoadingModList().getModFileById(ALEXS_MOBS) != null;
    }

    public static boolean checkArtsAndCrafts() {
        return FMLLoader.getLoadingModList().getModFileById(ARTS_AND_CRAFTS) != null;
    }

    public static boolean checkCavernsAndChasms() {
        return FMLLoader.getLoadingModList().getModFileById(CAVERNS_AND_CHASMS) != null;
    }

    public static boolean checkSullysMod() {
        return FMLLoader.getLoadingModList().getModFileById(SULLYS_MOD) != null;
    }

    public static boolean checkSupplementaries() {
        return FMLLoader.getLoadingModList().getModFileById(SUPPLEMENTARIES) != null;
    }

    public static boolean checkRubinatedNether() {
        return FMLLoader.getLoadingModList().getModFileById(RUBINATED_NETHER) != null;
    }

    public static boolean checkAnySilverMod() {
        return checkOreganized() || checkCavernsAndChasms() || FMLLoader.getLoadingModList().getModFileById(GALOSPHERE) != null;
    }
}
