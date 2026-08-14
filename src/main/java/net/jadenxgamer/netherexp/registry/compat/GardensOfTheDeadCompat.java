package net.jadenxgamer.netherexp.registry.compat;

import net.jadenxgamer.netherexp.core.item.GlowsporesItem;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

import static net.jadenxgamer.netherexp.util.RegistryHelper.registerBlock;

public class GardensOfTheDeadCompat {

    public static class Blocks {

        public static final Supplier<Block> SHROOMBLIGHT = registerBlock("shroomblight", () ->
                new Block(BlockBehaviour.Properties.ofLegacyCopy(net.minecraft.world.level.block.Blocks.SHROOMLIGHT).lightLevel((state) -> 10)));

        /**
         * Yellow Nether Bricks
         */

        public static final Supplier<Block> YELLOW_MIXED_NETHER_BRICKS = registerBlock("yellow_mixed_nether_bricks", () ->
                new Block(BlockBehaviour.Properties.ofLegacyCopy(net.minecraft.world.level.block.Blocks.NETHER_BRICKS)));

        public static final Supplier<Block> YELLOW_NETHER_BRICKS = registerBlock("yellow_nether_bricks", () ->
                new Block(BlockBehaviour.Properties.ofLegacyCopy(net.minecraft.world.level.block.Blocks.RED_NETHER_BRICKS).mapColor(MapColor.TERRACOTTA_YELLOW)));

        public static final Supplier<Block> YELLOW_NETHER_BRICK_SLAB = registerBlock("yellow_nether_brick_slab", () ->
                new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(YELLOW_NETHER_BRICKS.get())));

        public static final Supplier<Block> YELLOW_NETHER_BRICK_STAIRS = registerBlock("yellow_nether_brick_stairs", () ->
                new StairBlock(YELLOW_NETHER_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(YELLOW_NETHER_BRICKS.get())));

        public static final Supplier<Block> YELLOW_NETHER_BRICK_WALL = registerBlock("yellow_nether_brick_wall", () ->
                new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(YELLOW_NETHER_BRICKS.get())));

        public static void init() {
        }
    }

    public static class Items {

        public static final Supplier<Item> BLIGHTSPORES = JNEItems.ITEMS.register("blightspores", () ->
                new Item(new Item.Properties()));

        public static void init() {
        }
    }
}