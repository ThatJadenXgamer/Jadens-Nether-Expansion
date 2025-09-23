package net.jadenxgamer.netherexp.core.keys;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class JNETags {

    public static class Blocks {

        // Do this like Biomes or do Biomes like this?
        public static class Sounds {
            public static final TagKey<Block> BLACKSTONE = createTag("sounds/blackstone");
            public static final TagKey<Block> GLOWSTONE = createTag("sounds/glowstone");
            public static final TagKey<Block> MAGMA_BLOCK = createTag("sounds/magma_block");
            public static final TagKey<Block> POLISHED_BLACKSTONE_BRICKS = createTag("sounds/polished_blackstone_bricks");
            public static final TagKey<Block> QUARTZ_BLOCK = createTag("sounds/quartz_block");
        }

        public static final TagKey<Block> SOUL_SANDS = createTag("soul_sands"); // Soul Sand blocks
        public static final TagKey<Block> SOUL_CROP_MUTATION_BLOCKS = createTag("soul_crop_mutation_blocks"); // Blocks which mutate crops into soul variants when planted on
        public static final TagKey<Block> SORROWEED_REPLACEABLE = createTag("sorroweed_replaceable"); // Blocks that can be tainted by sorroweed
        public static final TagKey<Block> SOUL_LAYER_CAN_SURVIVE_ON = createTag("soul_layer_can_survive_on"); // self-explanatory
        public static final TagKey<Block> SOUL_LAYER_CANNOT_SURVIVE_ON = createTag("soul_layer_cannot_survive_on"); // also self-explanatory
        public static final TagKey<Block> BONE_FENCES = createTag("bone_fences"); // Bone fences will only connect to blocks in this tag
        public static final TagKey<Block> ANCIENT_FIRE_BASE_BLOCKS = createTag("ancient_fire_base_blocks"); // Blocks will burn ancient fire on top of 'em
        public static final TagKey<Block> FROGMISTS = createTag("frogmists"); // Frogmists
        public static final TagKey<Block> NIGHTSPORES_APPLICABLE = createTag("nightspores_applicable"); // Blocks that nightspores can be used on
        public static final TagKey<Block> LIGHTSPORES_APPLICABLE = createTag("lightspores_applicable"); // Blocks that lightspores can be used on
        public static final TagKey<Block> FOSSIL_ORE_CONVERTIBLE = createTag("fossil_ore_convertible"); // Blocks that convert to fossil ore when skeletons die on it
        public static final TagKey<Block> BLACK_ICE_REPLACEABLE = createTag("black_ice_replaceable");
        public static final TagKey<Block> BLACK_ICES = createTag("black_ices");
        public static final TagKey<Block> CLARET_STEMS = createTag("claret_stems");
        public static final TagKey<Block> MAGMA_BLOCKS = createTag("magma_blocks");
        public static final TagKey<Block> MOB_HEADS = createTag("mob_heads");
        public static final TagKey<Block> MOUND_BLOCKS = createTag("mound_blocks");
        public static final TagKey<Block> SHROOMLIGHTS = createTag("shroomlights");
        public static final TagKey<Block> SOUL_SLATE_REPLACEABLE = createTag("soul_slate_replaceable");
        public static final TagKey<Block> UNBOUNDED_SPEED_BLOCKS = createTag("unbounded_speed_blocks");
        public static final TagKey<Block> WART_BEARD_FEATURE_VALID = createTag("wart_beard_feature_valid");
        public static final TagKey<Block> WEEPING_VINES_FEATURE_VALID = createTag("weeping_vines_feature_valid");
        public static final TagKey<Block> GEYSERS = createTag("geysers");

        private static TagKey<Block> createTag(String name) {
            return TagKey.create(Registries.BLOCK, NetherExp.id(name));
        }
    }

    public static class Items {

        public static final TagKey<Item> SILVER_ARMORS = createTag("c", "silver_armors"); // Silver Armors
        public static final TagKey<Item> SILVER_WEAPONS = createTag("c", "silver_weapons"); // Silver Weapons
        public static final TagKey<Item> FROGMIST_VISIBLE_ITEMS = createTag("frogmist_visible_items"); // Frogmist can be broken when items in this tag are held in hand
        public static final TagKey<Item> CLARET_STEMS = createTag("claret_stems");
        public static final TagKey<Item> GLOWSPORES = createTag("glowspores");
        public static final TagKey<Item> COOKED_HOGHAM = createTag("cooked_hogham");
        public static final TagKey<Item> SHOTGUNS = createTag("shotguns");
        public static final TagKey<Item> ARTIFACTS = createTag("artifacts");
        public static final TagKey<Item> DOESNT_MODIFY_POTION_STACK_SIZE = createTag("doesnt_modify_potion_stack_size");
        public static final TagKey<Item> FROGMISTS = createTag("frogmists");
        public static final TagKey<Item> SHROOMLIGHTS = createTag("shroomlights");

        private static TagKey<Item> createTag(String name) {
            return TagKey.create(Registries.ITEM, NetherExp.id(name));
        }

        private static TagKey<Item> createTag(String namespace, String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(namespace, name));
        }
    }

    public static class EntityTypes {

        public static final TagKey<EntityType<?>> CANT_ACTIVATE_SWIRLS = createTag("cant_activate_swirls"); // Mobs cannot activate swirls
        public static final TagKey<EntityType<?>> CAN_PHASE_THROUGH_SOUL_GLASS = createTag("can_phase_through_soul_glass"); // Mobs that can go through soul glass
        public static final TagKey<EntityType<?>> CANT_SHATTER_THIN_BLACK_ICE = createTag("cant_shatter_thin_black_ice"); // Mobs which do not shatter thin black ice if stood on
        public static final TagKey<EntityType<?>> PROJECTILES_PASS_THROUGH = createTag("projectiles_pass_through"); // All projectiles absent from the blacklist below can phase through these entities
        public static final TagKey<EntityType<?>> PHANTASM_HULL_PROTECTS_BLACKLIST = createTag("phantasm_hull_protects_blacklist"); // Prevents projectiles from being phased through mobs in the above tag
        public static final TagKey<EntityType<?>> IGNORES_BLOCK_COLLISION = createTag("ignores_block_collision"); // Projectiles in this tag will go through blocks
        public static final TagKey<EntityType<?>> POSSESSED = createTag("possessed");
        public static final TagKey<EntityType<?>> ECTO_SLAB_POUNCE_DAMAGES = createTag("ecto_slab_pounce_damages");
        public static final TagKey<EntityType<?>> FOSSIL_FUEL_ORE_CONVERTING_SKELETONS = createTag("fossil_fuel_ore_converting_skeletons");
        public static final TagKey<EntityType<?>> FOSSIL_ORE_CONVERTING_SKELETONS = createTag("fossil_ore_converting_skeletons");
        public static final TagKey<EntityType<?>> IGNORES_SOUL_SAND_SLOWNESS = createTag("ignores_soul_sand_slowness");
        public static final TagKey<EntityType<?>> INGORES_TREACHEROUS_CANDLE = createTag("ignores_treacherous_candle");
        public static final TagKey<EntityType<?>> STAMPEDE_CANNOT_RUN_OVER = createTag("stampede_cannot_run_over");
        public static final TagKey<EntityType<?>> TARGET_REGARDLESS_OF_BETRAYED = createTag("target_regardless_of_betrayed");

        private static TagKey<EntityType<?>> createTag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, NetherExp.id(name));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> HAS_CRIMSON_SPORES = createTag("particles/has_crimson_spores"); // Prevents sporeshrooms from producing crimson spores here
        public static final TagKey<Biome> HAS_WARPED_SPORES = createTag("particles/has_warped_spores"); // Prevents sporeshrooms from producing warped spores here
        public static final TagKey<Biome> HAS_ASH = createTag("particles/has_ash"); // Prevents geysers from producing ash particles here
        public static final TagKey<Biome> HAS_WHITE_ASH = createTag("particles/has_white_ash"); // Prevents geysers from producing white ash particles here
        public static final TagKey<Biome> SOUL_SAND_VALLEYS = createTag("soul_sand_valleys");
        public static final TagKey<Biome> HAS_CHAPEL = createTag("has_structure/chapel");
        public static final TagKey<Biome> HAS_DEVILS_BLUFF = createTag("has_structure/devils_bluff");
        public static final TagKey<Biome> HAS_ICE_RIBS = createTag("has_structure/ice_ribs");
        public static final TagKey<Biome> HAS_MEGA_FOSSIL = createTag("has_structure/mega_fossil");
        public static final TagKey<Biome> HAS_QUARTZ_SPELEOTHEM = createTag("has_structure/quartz_speleothem");
        public static final TagKey<Biome> HAS_SANCTUM = createTag("has_structure/sanctum");

        private static TagKey<Biome> createTag(String name) {
            return TagKey.create(Registries.BIOME, NetherExp.id(name));
        }
    }

    public static class Fluids {
        public static final TagKey<Fluid> TURNS_TO_BLACK_ICE = createTag("turns_to_black_ice"); // Fluids in tag frost into black ice if in contact with ectoplasm or into thin black ice
        public static final TagKey<Fluid> ECTOPLASM = createTag("ectoplasm");

        private static TagKey<Fluid> createTag(String name) {
            return TagKey.create(Registries.FLUID, NetherExp.id(name));
        }
    }

    public static class DamageTypes {
        public static final TagKey<DamageType> OVERKILL_VALID = createTag("overkill_valid");
        public static final TagKey<DamageType> CAN_DISRUPT_UNDERGROUND_ECTO_SLABS = createTag("can_disrupt_underground_ecto_slabs");
        public static final TagKey<DamageType> IS_SUFFOCATION = createTag("is_suffocation");

        protected static TagKey<DamageType> createTag(String name) {
            return TagKey.create(Registries.DAMAGE_TYPE, NetherExp.id(name));
        }
    }
}
