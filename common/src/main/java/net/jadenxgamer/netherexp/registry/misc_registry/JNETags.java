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
import net.minecraft.world.level.material.Fluid;

public class JNETags {
    public static class Blocks {
        public static final TagKey<Block> ASH_CAN_SURVIVE_ON = createBlockTag("ash_can_survive_on"); // Allows White Ash to be placed on non-full Blocks
        public static final TagKey<Block> ASH_CANNOT_SURVIVE_ON = createBlockTag("ash_cannot_survive_on"); // Prevents White Ash from being placed on these Blocks
        public static final TagKey<Block> WHITE_ASH = createBlockTag("white_ash"); // Tag to define all White Ash related Blocks
        public static final TagKey<Block> STEMS = createBlockTag("stems"); // Tag to define all Nether Stem Blocks
        public static final TagKey<Block> SOUL_LAYER_CAN_SURVIVE_ON = createBlockTag("soul_layer_can_survive_on"); // Allows Soul Layer to be placed on non-full Blocks
        public static final TagKey<Block> SOUL_LAYER_CANNOT_SURVIVE_ON = createBlockTag("soul_layer_cannot_survive_on"); // Prevents Soul Layer from being placed on these Blocks
        public static final TagKey<Block> ENIGMA_CROWN_PLANTABLE_ON = createBlockTag("enigma_crown_plantable_on"); // Enigma Crown are Plantable on these Blocks
        public static final TagKey<Block> SCALE_FUNGUS_PLANTABLE_ON = createBlockTag("scale_fungus_plantable_on"); // Scale Fungus are Plantable on these Blocks
        public static final TagKey<Block> BONE_FENCES = createBlockTag("bone_fences"); // Makes Bone Fences connect with Blocks in this tag, it's technically unused but exists if for whatever reason you may need it
        public static final TagKey<Block> IGNEOUS_REEDS_PLANTABLE_ON = createBlockTag("igneous_reeds_plantable_on"); // Igneous Reeds are Plantable on these Blocks
        public static final TagKey<Block> ZOMBIFIED_PIGLIN_CANNOT_SPAWN_ON = createBlockTag("zombified_piglin_cannot_spawn_on"); // Zombified Piglins cannot spawn on these Blocks
        public static final TagKey<Block> PIGLIN_CANNOT_SPAWN_ON = createBlockTag("piglin_cannot_spawn_on"); // Piglins cannot spawn on these Blocks
        public static final TagKey<Block> HOGLIN_CANNOT_SPAWN_ON = createBlockTag("hoglin_cannot_spawn_on"); // Hoglins cannot spawn on these Blocks
        public static final TagKey<Block> SOUL_SAND_BLOCKS = createBlockTag("soul_sand_blocks"); // Blocks in this tag are considered Soul Sand
        public static final TagKey<Block> WEEPING_VINES_FEATURE_VALID = createBlockTag("weeping_vines_feature_valid"); // Weeping Vines can generate on these Blocks
        public static final TagKey<Block> WART_BEARD_FEATURE_VALID = createBlockTag("wart_beard_feature_valid"); // Warped Wart Beard can generate on these Blocks
        public static final TagKey<Block> ROOTS_PLANTABLE_ON = createBlockTag("roots_plantable_on"); // Nether Roots are Plantable on these Blocks
        public static final TagKey<Block> HUGE_FUNGUS_GENERATES_VINES = createBlockTag("huge_fungus_generates_vines"); // Huge Fungus Will Generate Weeping Vines below these Blocks
        public static final TagKey<Block> NETHER_WART_BLOCKS = createBlockTag("nether_wart_blocks"); // Blocks in this tag will be considered as Nether Wart Block
        public static final TagKey<Block> WARPED_WART_BLOCKS = createBlockTag("warped_wart_blocks"); // Blocks in this tag will be considered as Warped Wart Block
        public static final TagKey<Block> FOSSIL_ORE_CONVERTIBLE = createBlockTag("fossil_ore_convertible"); // Killing Skeletons on these blocks Converts it to Fossil Ore
        public static final TagKey<Block> UNBOUNDED_SPEED_BLOCKS = createBlockTag("unbounded_speed_blocks"); // Blocks in this tag will no longer slow you down if Unbounded Speed or if the Entity is in IGNORES_SOUL_SAND_SLOWNESS
        public static final TagKey<Block> NETHERITE_GRATES = createBlockTag("netherite_grates"); // Blocks in this tag are treated as Netherite Grates
        public static final TagKey<Block> FROGMIST = createBlockTag("frogmist"); // Tag to define all Frogmist (p.s: due to some quirks if a frogmist block isn't in this tag it may crash the game)
        public static final TagKey<Block> SOUNDS_BLACKSTONE = createBlockTag("sounds/blackstone"); // Blocks in this tag produce Blackstone Sounds
        public static final TagKey<Block> SOUNDS_POLISHED_BLACKSTONE = createBlockTag("sounds/polished_blackstone"); // Blocks in this tag produce Polished Blackstone Sounds
        public static final TagKey<Block> SOUNDS_POLISHED_BLACKSTONE_BRICKS = createBlockTag("sounds/polished_blackstone_bricks"); // Blocks in this tag produce Blackstone Brick Sounds
        public static final TagKey<Block> SOUNDS_MAGMA_BLOCK = createBlockTag("sounds/magma_block"); // Blocks in this tag produce Magma Block Sounds
        public static final TagKey<Block> SOUNDS_GLOWSTONE = createBlockTag("sounds/glowstone"); // Blocks in this tag produce Glowstone Sounds
        public static final TagKey<Block> SOUNDS_QUARTZ_BLOCK = createBlockTag("sounds/quartz_block"); // Blocks in this tag produce Quartz Block Sounds

        // MOD COMPAT
        public static final TagKey<Block> UMBRAL_WART_BLOCKS = createBlockTag("mod_compat/umbral_wart_blocks"); // Blocks in this tag will be considered as Umbral Wart Block, it's used to grow Umbral Wart Beards
        public static final TagKey<Block> UMBRAL_NYLIUM = createBlockTag("mod_compat/umbral_nylium"); // Blocks in this tag will be considered as Umbral Nylium, it's used to turn them into Umbral Paths

        private static TagKey<Block> createBlockTag(String name) {
            return TagKey.create(Registries.BLOCK, new ResourceLocation(NetherExp.MOD_ID, name));
        }
    }
    public static class EntityTypes {
        public static final TagKey<EntityType<?>> SOUL_GLASS_PASSABLE = createEntityTypeTag("soul_glass_passable"); //Entities in this tag can go through Soul Glass
        public static final TagKey<EntityType<?>> CANT_ACTIVATE_SWIRLS = createEntityTypeTag("cant_activate_swirls"); // Entities in this tag cannot activate Swirls
        public static final TagKey<EntityType<?>> PROJECTILES_PASS_THROUGH = createEntityTypeTag("projectiles_pass_through"); // Projectiles in the PHANTASM_HULL_PROTECTS_AGAINST will not collide with entities in this tag
        public static final TagKey<EntityType<?>> PHANTASM_HULL_PROTECTS_AGAINST = createEntityTypeTag("phantasm_hull_protects_against"); // Phantasm Hull will protect you against projectiles in this tag
        public static final TagKey<EntityType<?>> IMMUNE_TO_GRAVE_CLOUDS = createEntityTypeTag("immune_to_grave_clouds"); // Grave Clouds don't damage or slow down entities in this tag
        public static final TagKey<EntityType<?>> IGNORES_SOUL_SAND_SLOWNESS = createEntityTypeTag("ignores_soul_sand_slowness"); // Soul Sand won't slow down entities in this tag
        public static final TagKey<EntityType<?>> ECTOSLAB_POUNCE_DAMAGES = createEntityTypeTag("ectoslab_pounce_damages"); // Ecto Slab Pounce Damages Entities in this tag

        private static TagKey<EntityType<?>> createEntityTypeTag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(NetherExp.MOD_ID, name));
        }
    }
    public static class Items {
        public static final TagKey<Item> SMOKESTALK_FUEL = createItemTag("smokestalk_fuel"); // Smokestalk accepts these as fuel items
        public static final TagKey<Item> SHOTGUNS = createItemTag("shotguns"); // Items in this tag will be treated as Shotguns and can be Enchanted like one too
        public static final TagKey<Item> FROGMIST_VISIBLE_ITEMS = createItemTag("frogmist_visible_items"); // Frogmist's hitbox shows up only if you're holding items in this tag

        private static TagKey<Item> createItemTag(String name) {
            return TagKey.create(Registries.ITEM, new ResourceLocation(NetherExp.MOD_ID, name));
        }
    }
    public static class Biomes {
        public static final TagKey<Biome> HAS_CRIMSON_SPORES = createBiomeTag("particles/has_crimson_spores"); // Prevents Crimson Sporeshroom from creating Particles inside this Biome
        public static final TagKey<Biome> HAS_WARPED_SPORES = createBiomeTag("particles/has_warped_spores"); // Prevents Warped & Umbral Sporeshroom from creating Particles inside this Biome
        public static final TagKey<Biome> HAS_SOULBLIGHT_SPORES = createBiomeTag("particles/has_soulblight_spores"); // Prevents Soulblight Sporeshroom from creating Particles inside this Biome
        public static final TagKey<Biome> HAS_ASH = createBiomeTag("particles/has_ash"); // Prevents Souled & Ashen Geyser from creating Particles inside this Biome
        public static final TagKey<Biome> HAS_WHITE_ASH = createBiomeTag("particles/has_white_ash"); // Prevents Basaltic & Blackstonic Geyser from creating Particles inside this Biome

        // MOD COMPAT
        public static final TagKey<Biome> LUMINOUS_GROVE = createBiomeTag("mod_compat/luminous_grove"); // Used to check if the biome is Luminous Grove from Cinderscapes
        public static final TagKey<Biome> ASHY_SHOALS = createBiomeTag("mod_compat/ashy_shoals"); // Used to check if the biome is Ashy Shoals from Cinderscapes
        public static final TagKey<Biome> BLACKSTONE_SHALES = createBiomeTag("mod_compat/blackstone_shales"); // Used to check if the biome is Blackstone Shales from Cinderscapes
        public static final TagKey<Biome> SOULBLIGHT_FOREST = createBiomeTag("mod_compat/soulblight_forest"); // Used to check if the biome is Soulblight Forest from Gardens of The Dead

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
        public static final TagKey<DamageType> CANT_DAMAGE_ARMOR = createDamageTypeTag("cant_damage_armor"); // Damage Types in this tag won't take durability away from armor

        private static TagKey<DamageType> createDamageTypeTag(String name) {
            return TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(NetherExp.MOD_ID, name));
        }
    }
}
