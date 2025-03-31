package net.jadenxgamer.netherexp.registry.misc_registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.material.Fluid;

public class JNETags {
    public static class Blocks {
        public static final TagKey<Block> STEMS = createBlockTag("stems"); // Tag to define all Nether Stem Blocks
        public static final TagKey<Block> SOUL_LAYER_CAN_SURVIVE_ON = createBlockTag("soul_layer_can_survive_on"); // Allows Soul Layer to be placed on non-full Blocks
        public static final TagKey<Block> SOUL_LAYER_CANNOT_SURVIVE_ON = createBlockTag("soul_layer_cannot_survive_on"); // Prevents Soul Layer from being placed on these Blocks
        public static final TagKey<Block> ENIGMA_CROWN_PLANTABLE_ON = createBlockTag("enigma_crown_plantable_on"); // Enigma Crown are Plantable on these Blocks
        public static final TagKey<Block> SCALE_FUNGUS_PLANTABLE_ON = createBlockTag("scale_fungus_plantable_on"); // Scale Fungus are Plantable on these Blocks
        public static final TagKey<Block> BONE_FENCES = createBlockTag("bone_fences"); // Makes Bone Fences connect with Blocks in this tag, it's technically unused but exists if for whatever reason you may need it
        public static final TagKey<Block> IGNEOUS_REEDS_PLANTABLE_ON = createBlockTag("igneous_reeds_plantable_on"); // Igneous Reeds are Plantable on these Blocks
        public static final TagKey<Block> SOUL_SAND_BLOCKS = createBlockTag("soul_sand_blocks"); // Blocks in this tag are considered Soul Sand
        public static final TagKey<Block> MOUND_BLOCKS = createBlockTag("mound_blocks"); // Mounds can have terrain variation on top of these blocks
        public static final TagKey<Block> SOUL_CROP_MUTATION_BLOCKS = createBlockTag("soul_crop_mutation_blocks"); // Blocks in this tag can change certain crops that are planned on it
        public static final TagKey<Block> WART_BEARD_FEATURE_VALID = createBlockTag("wart_beard_feature_valid"); // Warped Wart Beard can generate on these Blocks
        public static final TagKey<Block> NETHER_WART_BLOCKS = createBlockTag("nether_wart_blocks"); // Blocks in this tag will be considered as Nether Wart Block
        public static final TagKey<Block> WARPED_WART_BLOCKS = createBlockTag("warped_wart_blocks"); // Blocks in this tag will be considered as Warped Wart Block
        public static final TagKey<Block> FOSSIL_ORE_CONVERTIBLE = createBlockTag("fossil_ore_convertible"); // Killing Skeletons on these blocks Converts it to Fossil Ore
        public static final TagKey<Block> UNBOUNDED_SPEED_BLOCKS = createBlockTag("unbounded_speed_blocks"); // Blocks in this tag will no longer slow you down if inflicted with Unbounded Speed or the Entity is in the IGNORES_SOUL_SAND_SLOWNESS tag
        public static final TagKey<Block> FROGMIST = createBlockTag("frogmist"); // Tag to define all Frogmist (p.s: due to some quirks if a frogmist block isn't in this tag it may crash the game)
        public static final TagKey<Block> POSSESSABLE_GARGOYLE_STATUES = createBlockTag("possessable_gargoyle_statues"); // Gargoyle Statues defined in this tag will be sought after by Apparitions (if the specified statue doesn't have a possession hardcoded into it then the apparition will turn into a Vessel)
        public static final TagKey<Block> SANCTUM_BLOCKS = createBlockTag("sanctum_blocks"); // Prevents those pesky Basalt Deltas configured features from overriding or replacing Sanctum Blocks
        public static final TagKey<Block> SORROWEED_REPLACEABLE = createBlockTag("sorroweed_replaceable"); // Sorroweed can overtake these blocks when grownpublic static final TagKey<Block> BLACK_ICE_REPLACEABLE = createBlockTag("black_ice_replaceable");
        public static final TagKey<Block> BLACK_ICE_REPLACEABLE = createBlockTag("black_ice_replaceable");
        public static final TagKey<Block> ANCIENT_FIRE_BASE_BLOCKS = createBlockTag("ancient_fire_base_blocks");

        private static TagKey<Block> createBlockTag(String name) {
            return TagKey.create(Registries.BLOCK, new ResourceLocation(NetherExp.MOD_ID, name));
        }
    }
    public static class EntityTypes {
        public static final TagKey<EntityType<?>> SOUL_GLASS_PASSABLE = createEntityTypeTag("soul_glass_passable"); //Entities in this tag can go through Soul Glass (UNUSED)
        public static final TagKey<EntityType<?>> CANT_ACTIVATE_SWIRLS = createEntityTypeTag("cant_activate_swirls"); // Entities in this tag cannot activate Swirls
        public static final TagKey<EntityType<?>> PROJECTILES_PASS_THROUGH = createEntityTypeTag("projectiles_pass_through"); // Projectiles in the PHANTASM_HULL_PROTECTS_AGAINST tag will phase through entities in this tag
        public static final TagKey<EntityType<?>> PHANTASM_HULL_PROTECTS_BLACKLIST = createEntityTypeTag("phantasm_hull_protects_blacklist"); // Phantasm Hull will NOT protect you against projectiles in this tag
        public static final TagKey<EntityType<?>> IMMUNE_TO_GRAVE_CLOUDS = createEntityTypeTag("immune_to_grave_clouds"); // Grave Clouds don't damage or slow down entities in this tag
        public static final TagKey<EntityType<?>> IGNORES_SOUL_SAND_SLOWNESS = createEntityTypeTag("ignores_soul_sand_slowness"); // Soul Sand won't slow down entities in this tag
        public static final TagKey<EntityType<?>> ECTO_SLAB_POUNCE_DAMAGES = createEntityTypeTag("ecto_slab_pounce_damages"); // Ecto Slab Pounce Damages Entities in this tag
        public static final TagKey<EntityType<?>> STAMPEDE_CANNOT_RUN_OVER = createEntityTypeTag("stampede_cannot_run_over"); // Stampede cannot run over mobs in this tag
        public static final TagKey<EntityType<?>> IGNORES_BLOCK_COLLISION = createEntityTypeTag("ignores_block_collision"); // Entities in this tag will ignore all block collision
        public static final TagKey<EntityType<?>> IGNORES_TREACHEROUS_CANDLE = createEntityTypeTag("ignores_treacherous_candle"); // Mobs in this do not care about the Treacherous Candle if the opportunity to break it is available
        public static final TagKey<EntityType<?>> TARGETS_REGARDLESS_OF_BETRAYED = createEntityTypeTag("targets_regardless_of_betrayed"); // Mobs in this tag find and attack targets even if they have the Betrayed Effect

        private static TagKey<EntityType<?>> createEntityTypeTag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(NetherExp.MOD_ID, name));
        }
    }
    public static class Items {
        public static final TagKey<Item> SMOKESTALK_FUEL = createItemTag("smokestalk_fuel"); // Smokestalk accepts these as fuel items
        public static final TagKey<Item> SHOTGUNS = createItemTag("shotguns"); // Items in this tag will be treated as Shotguns and can be Enchanted like one too
        public static final TagKey<Item> FROGMIST_VISIBLE_ITEMS = createItemTag("frogmist_visible_items"); // Frogmist's hitbox shows up only if you're holding items in this tag
        public static final TagKey<Item> STAMPEDE_EDIBLE = createItemTag("stampede_edible"); // Stampedes eat these items to regain health
        public static final TagKey<Item> STAMPEDE_FAVORITES = createItemTag("stampede_favorites"); // Stampedes can be tamed with these items
        public static final TagKey<Item> DOESNT_MODIFY_POTION_STACK_SIZE = createItemTag("doesnt_modify_potion_stack_size"); // Items in this tag override the potion stacksize change config, incase modpack devs might need it
        public static final TagKey<Item> DOESNT_SLOWDOWN_WHEN_USING = createItemTag("doesnt_slowdown_when_using"); // Items in this tag do not reduce the player's movement speed if they are moving while using said item
        public static final TagKey<Item> SILVER_ARMORS = createItemTag("silver_armors"); // Armors made from silver of some kind, used for possessed mobs weakening
        public static final TagKey<Item> SCULK_GRINDABLES = createItemTag("sculk_grindables"); // Sculk Grinders can grind down these items in experience

        private static TagKey<Item> createItemTag(String name) {
            return TagKey.create(Registries.ITEM, new ResourceLocation(NetherExp.MOD_ID, name));
        }
        private static TagKey<Item> createCommonItemTag(String name) {
            return TagKey.create(Registries.ITEM, new ResourceLocation("forge", name));
        }
    }
    public static class Biomes {
        public static final TagKey<Biome> HAS_CRIMSON_SPORES = createBiomeTag("particles/has_crimson_spores"); // Prevents Crimson Sporeshroom from creating Particles inside this Biome
        public static final TagKey<Biome> HAS_WARPED_SPORES = createBiomeTag("particles/has_warped_spores"); // Prevents Warped & Umbral Sporeshroom from creating Particles inside this Biome
        public static final TagKey<Biome> HAS_SOULBLIGHT_SPORES = createBiomeTag("particles/has_soulblight_spores"); // Prevents Soulblight Sporeshroom from creating Particles inside this Biome
        public static final TagKey<Biome> HAS_ASH = createBiomeTag("particles/has_ash"); // Prevents Souled & Ashen Geyser from creating Particles inside this Biome
        public static final TagKey<Biome> HAS_WHITE_ASH = createBiomeTag("particles/has_white_ash"); // Prevents Basaltic & Blackstonic Geyser from creating Particles inside this Biome

        private static TagKey<Biome> createBiomeTag(String name) {
            return TagKey.create(Registries.BIOME, new ResourceLocation(NetherExp.MOD_ID, name));
        }
    }

    public static class Fluids {

        /*
         * Fluids in this tag are treated like Ectoplasm
         * -Turns into Black Ice when in contact with Water
         * -Produces Ectoplasm Fog when submerged
        */
        public static final TagKey<Fluid> ECTOPLASM = createFluidTag("ectoplasm");

        private static TagKey<Fluid> createFluidTag(String name) {
            return TagKey.create(Registries.FLUID, new ResourceLocation(NetherExp.MOD_ID, name));
        }
    }

    public static class DamageTypes {
        public static final TagKey<DamageType> IS_SUFFOCATION = createDamageTypeTag("is_suffocation"); // Produces Suffocation Sounds
        public static final TagKey<DamageType> CAN_DISRUPT_UNDERGROUND_ECTO_SLABS = createDamageTypeTag("can_disrupt_underground_ecto_slabs"); // these damage sources can disrupt an underground ecto slab forcing them out

        private static TagKey<DamageType> createDamageTypeTag(String name) {
            return TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(NetherExp.MOD_ID, name));
        }
    }

    public static class Structures {
        public static final TagKey<Structure> SANCTUM_COMPASS_LOCATED = createStructureTags("sanctum_compass_located"); // Sanctum Compasses can locate this structure

        private static TagKey<Structure> createStructureTags(String name) {
            return TagKey.create(Registries.STRUCTURE, new ResourceLocation(NetherExp.MOD_ID, name));
        }
    }
}
