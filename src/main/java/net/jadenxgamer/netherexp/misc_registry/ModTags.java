package net.jadenxgamer.netherexp.misc_registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class ModTags {
    public static class Blocks {

        // Decides if Ash can or cannot be placed on these Blocks
        public static final TagKey<Block> ASH_CAN_SURVIVE_ON = createTag("ash_can_survive_on");
        public static final TagKey<Block> ASH_CANNOT_SURVIVE_ON = createTag("ash_cannot_survive_on");

        // Tag to define all White Ash related Blocks
        public static final TagKey<Block> WHITE_ASH = createTag("white_ash");

        // Tag to define all Nether Stem Blocks
        public static final TagKey<Block> STEMS = createTag("stems");

        // Decides if Soul Layer can or cannot be placed on these Blocks
        public static final TagKey<Block> SOUL_LAYER_CAN_SURVIVE_ON = createTag("soul_layer_can_survive_on");
        public static final TagKey<Block> SOUL_LAYER_CANNOT_SURVIVE_ON = createTag("soul_layer_cannot_survive_on");

        // Makes Enigma Crown Plantable on these Blocks
        public static final TagKey<Block> ENIGMA_CROWN_PLANTABLE_ON = createTag("enigma_crown_plantable_on");

        // Makes Scale Fungus Plantable on these Blocks
        public static final TagKey<Block> SCALE_FUNGUS_PLANTABLE_ON = createTag("scale_fungus_plantable_on");

        /*
        *  Makes Bone Fences connect with Blocks in this tag
        *  This is not normally used but exists if for whatever reason you may need it
        */
        public static final TagKey<Block> BONE_FENCES = createTag("bone_fences");

        // Igneous Reeds Plantable on these Blocks
        public static final TagKey<Block> IGNEOUS_REEDS_PLANTABLE_ON = createTag("igneous_reeds_plantable_on");

        // Tag to define all kinds of Magma Blocks
        public static final TagKey<Block> MAGMA_BLOCKS = createTag("magma_blocks");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(NetherExp.MOD_ID, name));
        }
    }
    public static class EntityTypes {

        /*
         *  Entities in this tag can go through Soul Glass
         *  (This Tag does not have any functionality as of now)
         */
        public static final TagKey<EntityType<?>> SOUL_GLASS_PASSABLE = createTag2("soul_glass_passable");

        private static TagKey<EntityType<?>> createTag2(String name) {
            return TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(NetherExp.MOD_ID, name));
        }
    }
    public static class Items {

        /*
         *  items in this tag can swing through Soul Glass' Hitbox
         *  (This Tag does not have any functionality as of now)
         */
        public static final TagKey<Item> SWINGS_THROUGH_SOUL_GLASS = createTag3("swings_through_soul_glass");

        private static TagKey<Item> createTag3(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(NetherExp.MOD_ID, name));
        }
    }
    public static class Biomes {

        // Prevents Crimson Sporeshroom from creating Particles inside this Biome
        public static final TagKey<Biome> HAS_CRIMSON_SPORES = createTag4("particles/has_crimson_spores");

        // Prevents Warped Sporeshroom from creating Particles inside this Biome
        public static final TagKey<Biome> HAS_WARPED_SPORES = createTag4("particles/has_warped_spores");

        // Prevents Souled Geyser from creating Particles inside this Biome
        public static final TagKey<Biome> HAS_ASH = createTag4("particles/has_ash");

        // Prevents Basaltic Geyser from creating Particles inside this Biome
        public static final TagKey<Biome> HAS_WHITE_ASH = createTag4("particles/has_white_ash");
        private static TagKey<Biome> createTag4(String name) {
            return TagKey.of(RegistryKeys.BIOME, new Identifier(NetherExp.MOD_ID, name));
        }
    }
}
