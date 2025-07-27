package net.jadenxgamer.netherexp.core.keys;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class JNETags {

    public static class Blocks {

        public static final TagKey<Block> SOUL_SANDS = createTag("soul_sands"); // Soul Sand blocks
        public static final TagKey<Block> SOUL_CROP_MUTATION_BLOCKS = createTag("soul_crop_mutation_blocks"); // Blocks which mutate crops into soul variants when planted on
        public static final TagKey<Block> SORROWEED_REPLACEABLE = createTag("sorroweed_replaceable"); // Blocks that can be tainted by sorroweed

        private static TagKey<Block> createTag(String name) {
            return TagKey.create(Registries.BLOCK, NetherExp.id(name));
        }
    }

    public static class EntityTypes {

        public static final TagKey<EntityType<?>> CANT_ACTIVATE_SWIRLS = createTag("cant_activate_swirls"); // Mobs cannot activate swirls
        public static final TagKey<EntityType<?>> CAN_PHASE_THROUGH_SOUL_GLASS = createTag("can_phase_through_soul_glass"); // Mobs that can go through soul glass
        public static final TagKey<EntityType<?>> CANT_SHATTER_THIN_BLACK_ICE = createTag("cant_shatter_thin_black_ice"); // Mobs which do not shatter thin black ice if stood on

        private static TagKey<EntityType<?>> createTag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, NetherExp.id(name));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> HAS_CRIMSON_SPORES = createBiomeTag("particles/has_crimson_spores"); // Prevents sporeshrooms from producing crimson spores here
        public static final TagKey<Biome> HAS_WARPED_SPORES = createBiomeTag("particles/has_warped_spores"); // Prevents sporeshrooms from producing warped spores here
        public static final TagKey<Biome> HAS_ASH = createBiomeTag("particles/has_ash"); // Prevents geysers from producing ash particles here
        public static final TagKey<Biome> HAS_WHITE_ASH = createBiomeTag("particles/has_white_ash"); // Prevents geysers from producing white ash particles here

        private static TagKey<Biome> createBiomeTag(String name) {
            return TagKey.create(Registries.BIOME, NetherExp.id(name));
        }
    }

    public static class Fluids {
        public static final TagKey<Fluid> TURNS_TO_BLACK_ICE = createBiomeTag("turns_to_black_ice"); // Fluids in tag frost into black ice if in contact with ectoplasm or into thin black ice

        private static TagKey<Fluid> createBiomeTag(String name) {
            return TagKey.create(Registries.FLUID, NetherExp.id(name));
        }
    }
}
