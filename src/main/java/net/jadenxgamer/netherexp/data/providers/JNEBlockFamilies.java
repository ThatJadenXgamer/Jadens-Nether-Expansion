package net.jadenxgamer.netherexp.data.providers;

import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JNEBlockFamilies {

    private static final List<BlockFamily> BLOCK_FAMILIES = new ArrayList<>();
    
    public static final BlockFamily SOUL_SLATE = family(JNEBlocks.SOUL_SLATE.get())
            .slab(JNEBlocks.SOUL_SLATE_SLAB.get())
            .stairs(JNEBlocks.SOUL_SLATE_STAIRS.get())
            .wall(JNEBlocks.SOUL_SLATE_WALL.get())
            .getFamily();

    public static final BlockFamily SOUL_SLATE_BRICKS = family(JNEBlocks.SOUL_SLATE_BRICKS.get())
            .slab(JNEBlocks.SOUL_SLATE_BRICK_SLAB.get())
            .stairs(JNEBlocks.SOUL_SLATE_BRICK_STAIRS.get())
            .wall(JNEBlocks.SOUL_SLATE_BRICK_WALL.get())
            .cracked(JNEBlocks.CRACKED_SOUL_SLATE_BRICKS.get())
            // .chiseled(JNEBlocks.CHISELED_SOUL_SLATE_BRICKS.get())
            .getFamily();
    
    public static final BlockFamily SOUL_SLATE_TILES = family(JNEBlocks.SOUL_SLATE_TILES.get())
            .slab(JNEBlocks.SOUL_SLATE_TILE_SLAB.get())
            .stairs(JNEBlocks.SOUL_SLATE_TILE_STAIRS.get())
            .wall(JNEBlocks.SOUL_SLATE_TILE_WALL.get())
            // .chiseled(JNEBlocks.CHISELED_SOUL_SLATE_TILES.get())
            .getFamily();
    
    public static final BlockFamily SMOOTH_NETHERRACK = family(JNEBlocks.SMOOTH_NETHERRACK.get())
            .slab(JNEBlocks.SMOOTH_NETHERRACK_SLAB.get())
            .stairs(JNEBlocks.SMOOTH_NETHERRACK_STAIRS.get())
            .wall(JNEBlocks.SMOOTH_NETHERRACK_WALL.get())
            .getFamily();
    
    public static final BlockFamily NETHERRACK_BRICKS = family(JNEBlocks.NETHERRACK_BRICKS.get())
            .slab(JNEBlocks.NETHERRACK_BRICK_SLAB.get())
            .stairs(JNEBlocks.NETHERRACK_BRICK_STAIRS.get())
            .wall(JNEBlocks.NETHERRACK_BRICK_WALL.get())
            .getFamily();

    /*
    TODO: BLOCKSTATES
     
    public static final BlockFamily POLISHED_BASALT_BRICKS = family(JNEBlocks.POLISHED_BASALT_BRICKS.get())
            .slab(JNEBlocks.POLISHED_BASALT_BRICK_SLAB.get())
            .stairs(JNEBlocks.POLISHED_BASALT_BRICK_STAIRS.get())
            .wall(JNEBlocks.POLISHED_BASALT_BRICK_WALL.get())
            .getFamily();
    */
    
    public static final BlockFamily CUT_NETHERITE_BLOCK = family(JNEBlocks.CUT_NETHERITE_BLOCK.get())
            .slab(JNEBlocks.CUT_NETHERITE_SLAB.get())
            .stairs(JNEBlocks.CUT_NETHERITE_STAIRS.get())
            .getFamily();

    public static final BlockFamily RUSTY_CUT_NETHERITE_BLOCK = family(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get())
            .slab(JNEBlocks.RUSTY_CUT_NETHERITE_SLAB.get())
            .stairs(JNEBlocks.RUSTY_CUT_NETHERITE_STAIRS.get())
            .getFamily();

    public static final BlockFamily CLARET = family(JNEBlocks.CLARET_PLANKS.get())
            .slab(JNEBlocks.CLARET_SLAB.get())
            .stairs(JNEBlocks.CLARET_STAIRS.get())
            .fence(JNEBlocks.CLARET_FENCE.get())
            .fenceGate(JNEBlocks.CLARET_FENCE_GATE.get())
            .door(JNEBlocks.CLARET_DOOR.get())
            .trapdoor(JNEBlocks.CLARET_TRAPDOOR.get())
            .button(JNEBlocks.CLARET_BUTTON.get())
            .pressurePlate(JNEBlocks.CLARET_PRESSURE_PLATE.get())
            .sign(JNEBlocks.CLARET_SIGN.get(), JNEBlocks.CLARET_WALL_SIGN.get())
            .getFamily();
    
    public static final BlockFamily BLUE_NETHER_BRICKS = family(JNEBlocks.BLUE_NETHER_BRICKS.get())
            .slab(JNEBlocks.BLUE_NETHER_BRICK_SLAB.get())
            .stairs(JNEBlocks.BLUE_NETHER_BRICK_STAIRS.get())
            .wall(JNEBlocks.BLUE_NETHER_BRICK_WALL.get())
            .getFamily();
    
    public static final BlockFamily WEEPING_POLISHED_BLACKSTONE_BRICKS = family(JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICKS.get())
            .slab(JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICK_SLAB.get())
            .stairs(JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICK_STAIRS.get())
            .wall(JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICK_WALL.get())
            .getFamily();

    public static final BlockFamily TWISTING_POLISHED_BLACKSTONE_BRICKS = family(JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICKS.get())
            .slab(JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICK_SLAB.get())
            .stairs(JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICK_STAIRS.get())
            .wall(JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICK_WALL.get())
            .getFamily();
    
    public static final BlockFamily STACKED_BONES = family(JNEBlocks.STACKED_BONES.get())
            .slab(JNEBlocks.STACKED_BONE_SLAB.get())
            .stairs(JNEBlocks.STACKED_BONE_STAIRS.get())
            .getFamily();

    public static final BlockFamily STACKED_WITHER_BONES = family(JNEBlocks.STACKED_WITHER_BONES.get())
            .slab(JNEBlocks.STACKED_WITHER_BONE_SLAB.get())
            .stairs(JNEBlocks.STACKED_WITHER_BONE_STAIRS.get())
            .getFamily();
    
    private static BlockFamily.Builder family(Block baseBlock) {
        BlockFamily.Builder builder = new BlockFamily.Builder(baseBlock);
        BLOCK_FAMILIES.add(builder.getFamily());
        return builder;
    }

    public static Stream<BlockFamily> getBlockFamilies() {
        return BLOCK_FAMILIES.stream();
    }
}
