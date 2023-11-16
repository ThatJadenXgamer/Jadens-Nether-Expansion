package net.jadenxgamer.netherexp.registry.misc_registry;

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
        public static final TagKey<Block> ASH_CAN_SURVIVE_ON = createBlockTag("ash_can_survive_on");
        public static final TagKey<Block> ASH_CANNOT_SURVIVE_ON = createBlockTag("ash_cannot_survive_on");

        // Tag to define all White Ash related Blocks
        public static final TagKey<Block> WHITE_ASH = createBlockTag("white_ash");

        // Tag to define all Nether Stem Blocks
        public static final TagKey<Block> STEMS = createBlockTag("stems");

        // Decides if Soul Layer can or cannot be placed on these Blocks
        public static final TagKey<Block> SOUL_LAYER_CAN_SURVIVE_ON = createBlockTag("soul_layer_can_survive_on");
        public static final TagKey<Block> SOUL_LAYER_CANNOT_SURVIVE_ON = createBlockTag("soul_layer_cannot_survive_on");

        // Makes Enigma Crown are Plantable on these Blocks
        public static final TagKey<Block> ENIGMA_CROWN_PLANTABLE_ON = createBlockTag("enigma_crown_plantable_on");

        // Makes Scale Fungus are Plantable on these Blocks
        public static final TagKey<Block> SCALE_FUNGUS_PLANTABLE_ON = createBlockTag("scale_fungus_plantable_on");

        /*
        *  Makes Bone Fences connect with Blocks in this tag
        *  This is not normally used but exists if for whatever reason you may need it
        */
        public static final TagKey<Block> BONE_FENCES = createBlockTag("bone_fences");

        // Igneous Reeds are Plantable on these Blocks
        public static final TagKey<Block> IGNEOUS_REEDS_PLANTABLE_ON = createBlockTag("igneous_reeds_plantable_on");

        // Tag to define all kinds of Magma Blocks
        public static final TagKey<Block> MAGMA_BLOCKS = createBlockTag("magma_blocks");

        // Zombified Piglins cannot spawn on these Blocks
        public static final TagKey<Block> ZOMBIFIED_PIGLIN_CANNOT_SPAWN_ON = createBlockTag("zombified_piglin_cannot_spawn_on");

        // Piglins cannot spawn on these Blocks
        public static final TagKey<Block> PIGLIN_CANNOT_SPAWN_ON = createBlockTag("piglin_cannot_spawn_on");

        // Hoglins cannot spawn on these Blocks
        public static final TagKey<Block> HOGLIN_CANNOT_SPAWN_ON = createBlockTag("hoglin_cannot_spawn_on");

        // Sorrowsquash Vines are Plantable on these Blocks
        public static final TagKey<Block> SORROWSQUASH_VINE_PLANTABLE_ON = createBlockTag("sorrowsquash_vine_plantable_on");

        // Weeping Vines can generate on these Blocks
        public static final TagKey<Block> WEEPING_VINES_FEATURE_VALID = createBlockTag("weeping_vines_feature_valid");

        // Nether Roots are Plantable on these Blocks
        public static final TagKey<Block> ROOTS_PLANTABLE_ON = createBlockTag("roots_plantable_on");

        // Huge Fungus Will Generate Weeping Vines below these Blocks
        public static final TagKey<Block> HUGE_FUNGUS_GENERATES_VINES = createBlockTag("huge_fungus_generates_vines");

        /*
        *  Blocks in this tag will be considered as Nether Wart Block
        *  Bone Mealing it causes Nether Wart Beards to grow
        *  Hoglins, Piglins and Zombified Piglins cannot spawn on these blocks
        */
        public static final TagKey<Block> NETHER_WART_BLOCKS = createBlockTag("nether_wart_blocks");

        /*
         *  Blocks in this tag will be considered as Warped Wart Block
         *  Bone Mealing it causes Warped Wart Beards to grow
         *  Hoglins, Piglins and Zombified Piglins cannot spawn on these blocks
         */
        public static final TagKey<Block> WARPED_WART_BLOCKS = createBlockTag("warped_wart_blocks");

        /*
         *  Blocks in this tag will be considered as Umbral Wart Block
         *  Bone Mealing it causes Umbral Wart Beards to grow (only if Cinderscapes is installed)
         *  This tag is intended for Cinderscapes Compatibility
         */
        public static final TagKey<Block> UMBRAL_WART_BLOCKS = createBlockTag("umbral_wart_blocks");

        /*
         *  Blocks in this tag will be considered as Umbral Nylium
         *  This block can be shoveled to create Umbral Path blocks (only if Cinderscapes is installed)
         *  This tag is intended for Cinderscapes Compatibility
         */
        public static final TagKey<Block> UMBRAL_NYLIUM = createBlockTag("umbral_nylium");

        // Blocks in this tag produce Blackstone Sounds
        public static final TagKey<Block> SOUNDS_BLACKSTONE = createBlockTag("sounds/blackstone");

        // Blocks in this tag produce Blackstone Sounds
        public static final TagKey<Block> SOUNDS_POLISHED_BLACKSTONE = createBlockTag("sounds/polished_blackstone");

        // Blocks in this tag produce Blackstone Sounds
        public static final TagKey<Block> SOUNDS_POLISHED_BLACKSTONE_BRICKS = createBlockTag("sounds/polished_blackstone_bricks");

        // Blocks in this tag produce Magma Block Sounds
        public static final TagKey<Block> SOUNDS_MAGMA_BLOCK = createBlockTag("sounds/magma_block");

        // Blocks in this tag produce Glowstone Sounds
        public static final TagKey<Block> SOUNDS_GLOWSTONE = createBlockTag("sounds/glowstone");

        // Blocks in this tag produce Quartz Block Sounds
        public static final TagKey<Block> SOUNDS_QUARTZ_BLOCK = createBlockTag("sounds/quartz_block");


        private static TagKey<Block> createBlockTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(NetherExp.MOD_ID, name));
        }
    }
    public static class EntityTypes {

        /*
         *  Entities in this tag can go through Soul Glass
         *  (This Tag does not have any functionality as of now)
         */
        public static final TagKey<EntityType<?>> SOUL_GLASS_PASSABLE = createEntityTypeTag("soul_glass_passable");

        private static TagKey<EntityType<?>> createEntityTypeTag(String name) {
            return TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(NetherExp.MOD_ID, name));
        }
    }
    public static class Items {

        /*
         *  items in this tag can swing through Soul Glass' Hitbox
         *  (This Tag does not have any functionality as of now)
         */
        public static final TagKey<Item> SWINGS_THROUGH_SOUL_GLASS = createItemTag("swings_through_soul_glass");

        // Smokestalk accepts these as fuel items
        public static final TagKey<Item> SMOKESTALK_FUEL = createItemTag("smokestalk_fuel");

        private static TagKey<Item> createItemTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(NetherExp.MOD_ID, name));
        }
    }
    public static class Biomes {

        // Prevents Crimson Sporeshroom from creating Particles inside this Biome
        public static final TagKey<Biome> HAS_CRIMSON_SPORES = createBiomeTag("particles/has_crimson_spores");

        // Prevents Warped Sporeshroom from creating Particles inside this Biome
        public static final TagKey<Biome> HAS_WARPED_SPORES = createBiomeTag("particles/has_warped_spores");

        // Prevents Umbral Sporeshroom from creating Particles inside this Biome
        public static final TagKey<Biome> HAS_WARPED_SPORES_UMBRAL = createBiomeTag("particles/has_warped_spores_umbral");

        // Prevents Souled Geyser from creating Particles inside this Biome
        public static final TagKey<Biome> HAS_ASH = createBiomeTag("particles/has_ash");

        // Prevents Basaltic Geyser from creating Particles inside this Biome
        public static final TagKey<Biome> HAS_WHITE_ASH = createBiomeTag("particles/has_white_ash");

        // Prevents Blackstonic Geyser from creating Particles inside this Biome
        public static final TagKey<Biome> HAS_WHITE_ASH_BLACKSTONIC = createBiomeTag("particles/has_white_ash_blackstonic");


        // Prevents Basaltic Geyser from creating Particles inside this Biome
        public static final TagKey<Biome> HAS_ASH_ASHEN = createBiomeTag("particles/has_ash_ashen");


        // used to check if the biome is Luminous Grove from Cinderscapes
        public static final TagKey<Biome> LUMINOUS_GROVE = createBiomeTag("luminous_grove");

        // used to check if the biome is Ashy Shoals from Cinderscapes
        public static final TagKey<Biome> ASHY_SHOALS = createBiomeTag("ashy_shoals");

        // used to check if the biome is Blackstone Shales from Cinderscapes
        public static final TagKey<Biome> BLACKSTONE_SHALES = createBiomeTag("blackstone_shales");

        private static TagKey<Biome> createBiomeTag(String name) {
            return TagKey.of(RegistryKeys.BIOME, new Identifier(NetherExp.MOD_ID, name));
        }
    }
}
