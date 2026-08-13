package net.jadenxgamer.netherexp.registry.compat;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

import static net.jadenxgamer.netherexp.util.RegistryHelper.registerBlock;

public class GardensOfTheDeadCompat {
    
    public static class Blocks {

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

        public static void init() {}
    }
}