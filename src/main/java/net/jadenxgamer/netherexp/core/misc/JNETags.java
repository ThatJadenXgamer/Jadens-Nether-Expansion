package net.jadenxgamer.netherexp.core.misc;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;

public class JNETags {

    public static class Blocks {

        public static final TagKey<Block> SOUL_SANDS = createTag("soul_sands"); // Soul Sand blocks

        private static TagKey<Block> createTag(String name) {
            return TagKey.create(Registries.BLOCK, NetherExp.id(name));
        }
    }

    public static class EntityTypes {

        public static final TagKey<EntityType<?>> CANT_ACTIVATE_SWIRLS = createTag("cant_activate_swirls"); // Mobs cannot activate swirls
        public static final TagKey<EntityType<?>> CAN_PHASE_THROUGH_SOUL_GLASS = createTag("can_phase_through_soul_glass"); // Mobs can go through soul glass

        private static TagKey<EntityType<?>> createTag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, NetherExp.id(name));
        }
    }
}
