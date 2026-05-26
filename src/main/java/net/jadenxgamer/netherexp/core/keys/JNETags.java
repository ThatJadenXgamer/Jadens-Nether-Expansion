package net.jadenxgamer.netherexp.core.keys;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class JNETags {

    public static class Blocks {

        public static final TagKey<Block> SOUL_SANDS = createTag("soul_sands"); // Soul Sand blocks
        public static final TagKey<Block> SOUL_CROP_MUTATION_BLOCKS = createTag("soul_crop_mutation_blocks"); // Blocks which mutate crops into soul variants when planted on
        public static final TagKey<Block> SORROWEED_REPLACEABLE = createTag("sorroweed_replaceable"); // Blocks that can be tainted by sorroweed
        public static final TagKey<Block> SOUL_LAYER_CAN_SURVIVE_ON = createTag("soul_layer_can_survive_on"); // self-explanatory
        public static final TagKey<Block> SOUL_LAYER_CANNOT_SURVIVE_ON = createTag("soul_layer_cannot_survive_on"); // also self-explanatory
        public static final TagKey<Block> BONE_FENCES = createTag("bone_fences"); // Bone fences will only connect to blocks in this tag
        public static final TagKey<Block> ANCIENT_FIRE_BASE_BLOCKS = createTag("ancient_fire_base_blocks"); // Blocks will burn ancient fire on top of 'em
        public static final TagKey<Block> FROGMIST = createTag("frogmist"); // Frogmists
        public static final TagKey<Block> SPELEOTHEM_BASE_BLOCKS = createTag("speleothem_base_blocks"); // Blocks which speleothems can be placed on
        public static final TagKey<Block> UNBOUNDED_SPEED_BLOCKS = createTag("unbounded_speed_blocks"); // Blocks in this tag have their block speed factor ignored when unbounded speed is applied
        public static final TagKey<Block> SILTMARRAM_PLANTABLE_ON = createTag("siltmarram_plantable_on"); // Blocks in this tag can support siltmarrams
        public static final TagKey<Block> SOUL_SWIRLS = createTag("soul_swirls"); // Blocks in this tag are treated as soul swirls and can be petrified

        private static TagKey<Block> createTag(String name) {
            return TagKey.create(Registries.BLOCK, NetherExp.netherexpPath(name));
        }
    }

    public static class Items {

        public static final TagKey<Item> SILVER_ARMORS = createTag("c", "silver_armors"); // Silver Armors
        public static final TagKey<Item> SILVER_WEAPONS = createTag("c", "silver_weapons"); // Silver Weapons
        public static final TagKey<Item> FROGMIST_VISIBLE_ITEMS = createTag("frogmist_visible_items"); // Frogmist can be broken when items in this tag are held in hand
        public static final TagKey<Item> STAMPEDE_EDIBLE = createTag("stampede_edible"); // Stampedes can eat or be fed these items to replenish health and hunger
        public static final TagKey<Item> STAMPEDE_FAVORITES = createTag("stampede_favorites"); // Stampedes replenish more health, hunger and can be tamed with these items
        public static final TagKey<Item> SHOTGUN_SHELLS = createTag("shotgun_shells"); // Shotguns can load these items as shells

        private static TagKey<Item> createTag(String name) {
            return TagKey.create(Registries.ITEM, NetherExp.netherexpPath(name));
        }

        private static TagKey<Item> createTag(String namespace, String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(namespace, name));
        }
    }

    public static class EntityTypes {

        public static final TagKey<EntityType<?>> CANT_ACTIVATE_SWIRLS = createTag("cant_activate_swirls"); // Mobs cannot activate swirls
        public static final TagKey<EntityType<?>> CAN_PHASE_THROUGH_SOUL_GLASS = createTag("can_phase_through_soul_glass"); // Mobs that can go through soul glass
        public static final TagKey<EntityType<?>> CANT_SHATTER_THIN_BLACK_ICE = createTag("cant_shatter_thin_black_ice"); // Mobs which do not shatter thin black ice if stood on
        public static final TagKey<EntityType<?>> CANT_BREAK_CRUSTS = createTag("cant_break_crusts"); // Mobs which do not break crust blocks when standing on them
        public static final TagKey<EntityType<?>> PROJECTILES_PASS_THROUGH = createTag("projectiles_pass_through"); // All projectiles absent from the blacklist below can phase through these entities
        public static final TagKey<EntityType<?>> PHANTASM_HULL_PROTECTS_BLACKLIST = createTag("phantasm_hull_protects_blacklist"); // Prevents projectiles from being phased through mobs in the above tag
        public static final TagKey<EntityType<?>> IGNORES_BLOCK_COLLISION = createTag("ignores_block_collision"); // Projectiles in this tag will go through blocks
        public static final TagKey<EntityType<?>> IGNORES_SOUL_SAND_SLOWNESS = createTag("ignores_soul_sand_slowness"); // Entities within this tag cannot be slowed down by soul sand
        public static final TagKey<EntityType<?>> SAFE_FROM_ECTO_SLAB_EMERGE_BURST = createTag("safe_from_ecto_slab_emerge_burst"); // Entities within this tag are immune to emerge burst attack's AoE

        private static TagKey<EntityType<?>> createTag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, NetherExp.netherexpPath(name));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> HAS_CRIMSON_SPORES = createBiomeTag("particles/has_crimson_spores"); // Prevents sporeshrooms from producing crimson spores here
        public static final TagKey<Biome> HAS_WARPED_SPORES = createBiomeTag("particles/has_warped_spores"); // Prevents sporeshrooms from producing warped spores here
        public static final TagKey<Biome> HAS_ASH = createBiomeTag("particles/has_ash"); // Prevents geysers from producing ash particles here
        public static final TagKey<Biome> HAS_WHITE_ASH = createBiomeTag("particles/has_white_ash"); // Prevents geysers from producing white ash particles here
        public static final TagKey<Biome> SPAWNS_DAMP_VARIANT_STRIDERS_AND_SILTMARRAM = createBiomeTag("spawns_damp_variant_striders_and_siltmarram");
        public static final TagKey<Biome> SPAWNS_MOIST_VARIANT_STRIDERS_AND_SILTMARRAM = createBiomeTag("spawns_moist_variant_striders_and_siltmarram");
        public static final TagKey<Biome> SPAWNS_DRY_VARIANT_STRIDERS_AND_SILTMARRAM = createBiomeTag("spawns_dry_variant_striders_and_siltmarram");

        private static TagKey<Biome> createBiomeTag(String name) {
            return TagKey.create(Registries.BIOME, NetherExp.netherexpPath(name));
        }
    }

    public static class Fluids {
        public static final TagKey<Fluid> TURNS_TO_BLACK_ICE = createBiomeTag("turns_to_black_ice"); // Fluids in tag frost into black ice if in contact with ectoplasm or into thin black ice

        private static TagKey<Fluid> createBiomeTag(String name) {
            return TagKey.create(Registries.FLUID, NetherExp.netherexpPath(name));
        }
    }
}
