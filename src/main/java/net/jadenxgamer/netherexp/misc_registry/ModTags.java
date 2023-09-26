package net.jadenxgamer.netherexp.misc_registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModTags {
    public static class Blocks {

        public static final TagKey<Block> ASH_CAN_SURVIVE_ON = createTag("ash_can_survive_on");
        public static final TagKey<Block> ASH_CANNOT_SURVIVE_ON = createTag("ash_cannot_survive_on");

        public static final TagKey<Block> WHITE_ASH = createTag("white_ash");

        public static final TagKey<Block> STEMS = createTag("stems");
        public static final TagKey<Block> SOUL_LAYER_CAN_SURVIVE_ON = createTag("soul_layer_can_survive_on");
        public static final TagKey<Block> SOUL_LAYER_CANNOT_SURVIVE_ON = createTag("soul_layer_cannot_survive_on");
        public static final TagKey<Block> ENIGMA_CROWN_PLANTABLE_ON = createTag("enigma_crown_plantable_on");
        public static final TagKey<Block> SCALE_FUNGUS_PLANTABLE_ON = createTag("scale_fungus_plantable_on");
        public static final TagKey<Block> BONE_FENCES = createTag("bone_fences");
        public static final TagKey<Block> IGNEOUS_REEDS_PLANTABLE_ON = createTag("igneous_reeds_plantable_on");

        public static final TagKey<Block> MAGMA_BLOCKS = createTag("magma_blocks");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(Registry.BLOCK_KEY, new Identifier(NetherExp.MOD_ID, name));
        }
    }
    public static class EntityTypes {

        public static final TagKey<EntityType<?>> SOUL_GLASS_PASSABLE = createTag2("soul_glass_passable");

        private static TagKey<EntityType<?>> createTag2(String name) {
            return TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(NetherExp.MOD_ID, name));
        }
    }
    public static class Items {

        public static final TagKey<Item> SWINGS_THROUGH_SOUL_GLASS = createTag3("swings_through_soul_glass");

        private static TagKey<Item> createTag3(String name) {
            return TagKey.of(Registry.ITEM_KEY, new Identifier(NetherExp.MOD_ID, name));
        }
    }
}
