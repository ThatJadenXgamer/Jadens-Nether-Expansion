package net.jadenxgamer.netherexp.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.block.custom.LightAbleBlock;
import net.jadenxgamer.netherexp.block.custom.ModStairsBlock;
import net.jadenxgamer.netherexp.block.custom.RotatingBlock;
import net.jadenxgamer.netherexp.block.custom.WitherBoneBlock;
import net.jadenxgamer.netherexp.item.ModItemGroup;
import net.jadenxgamer.netherexp.sound.ModSoundEvents;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    // LIST OF BLOCKS:

    public static final Block SOUL_SLATE = registerBlock("soul_slate",
            new Block(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool().sounds(ModSoundEvents.SOUL_SLATE)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block SOUL_SLATE_SLAB = registerBlock("soul_slate_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.SOUL_SLATE).sounds(ModSoundEvents.SOUL_SLATE)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block SOUL_SLATE_STAIRS = registerBlock("soul_slate_stairs",
            new ModStairsBlock(ModBlocks.SOUL_SLATE.getDefaultState(),
                    FabricBlockSettings.copyOf(ModBlocks.SOUL_SLATE).sounds(ModSoundEvents.SOUL_SLATE)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block SOUL_SLATE_WALL = registerBlock("soul_slate_wall",
            new WallBlock(FabricBlockSettings.copyOf(ModBlocks.SOUL_SLATE).sounds(ModSoundEvents.SOUL_SLATE)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block SOUL_SLATE_BRICKS = registerBlock("soul_slate_bricks",
            new Block(FabricBlockSettings.of(Material.STONE).strength(2f).requiresTool().sounds(ModSoundEvents.SOUL_SLATE_BRICKS)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block SOUL_SLATE_BRICK_SLAB = registerBlock("soul_slate_brick_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.SOUL_SLATE_BRICKS).sounds(ModSoundEvents.SOUL_SLATE_BRICKS)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block SOUL_SLATE_BRICK_STAIRS = registerBlock("soul_slate_brick_stairs",
            new ModStairsBlock(ModBlocks.SOUL_SLATE_BRICKS.getDefaultState(),
            FabricBlockSettings.copyOf(ModBlocks.SOUL_SLATE_BRICKS).sounds(ModSoundEvents.SOUL_SLATE_BRICKS)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block SOUL_SLATE_BRICK_WALL = registerBlock("soul_slate_brick_wall",
            new WallBlock(FabricBlockSettings.copyOf(ModBlocks.SOUL_SLATE_BRICKS).sounds(ModSoundEvents.SOUL_SLATE_BRICKS)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block INDENTED_SOUL_SLATE_BRICKS = registerBlock("indented_soul_slate_bricks",
            new LightAbleBlock(FabricBlockSettings.copyOf(ModBlocks.SOUL_SLATE_BRICKS).luminance(
            state -> state.get(LightAbleBlock.LIT) ? 7 : 0).sounds(ModSoundEvents.SOUL_SLATE_BRICKS)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block CRACKED_SOUL_SLATE_BRICKS = registerBlock("cracked_soul_slate_bricks",
            new Block(FabricBlockSettings.copyOf(ModBlocks.SOUL_SLATE_BRICKS).sounds(ModSoundEvents.SOUL_SLATE_BRICKS)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block SOUL_SLATE_BRICK_PILLAR = registerBlock("soul_slate_brick_pillar",
            new PillarBlock(FabricBlockSettings.copyOf(ModBlocks.SOUL_SLATE_BRICKS).sounds(ModSoundEvents.SOUL_SLATE_BRICKS)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block CHISELED_SOUL_SLATE_BRICKS = registerBlock("chiseled_soul_slate_bricks",
            new Block(FabricBlockSettings.copyOf(ModBlocks.SOUL_SLATE_BRICKS).sounds(ModSoundEvents.SOUL_SLATE_BRICKS)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block BASALT_SLAB = registerBlock("basalt_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.BASALT).sounds(BlockSoundGroup.BASALT)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block POLISHED_BASALT_BRICKS = registerBlock("polished_basalt_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.POLISHED_BASALT).sounds(ModSoundEvents.BASALT_BRICKS)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block POLISHED_BASALT_BRICK_SLAB = registerBlock("polished_basalt_brick_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_BASALT).sounds(ModSoundEvents.BASALT_BRICKS)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block GILDED_BASALT_BRICKS = registerBlock("gilded_basalt_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.POLISHED_BASALT).sounds(ModSoundEvents.BASALT_BRICKS)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block GILDED_BASALT_BRICK_SLAB = registerBlock("gilded_basalt_brick_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_BASALT).sounds(ModSoundEvents.BASALT_BRICKS)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block NETHER_BRICK_PILLAR = registerBlock("nether_brick_pillar",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS).sounds(BlockSoundGroup.NETHER_BRICKS)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block RED_MIXED_NETHER_BRICKS = registerBlock("red_mixed_nether_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS).sounds(BlockSoundGroup.NETHER_BRICKS)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block BLUE_MIXED_NETHER_BRICKS = registerBlock("blue_mixed_nether_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS).sounds(BlockSoundGroup.NETHER_BRICKS)), ModItemGroup.NETHEREXP_BLOCKS);
    public static final Block BLUE_NETHER_BRICKS = registerBlock("blue_nether_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.RED_NETHER_BRICKS).sounds(BlockSoundGroup.NETHER_BRICKS)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block BLUE_NETHER_BRICK_SLAB = registerBlock("blue_nether_brick_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.BLUE_NETHER_BRICKS).sounds(BlockSoundGroup.NETHER_BRICKS)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block BLUE_NETHER_BRICK_STAIRS = registerBlock("blue_nether_brick_stairs",
            new ModStairsBlock(ModBlocks.BLUE_NETHER_BRICKS.getDefaultState(),
            FabricBlockSettings.copyOf(ModBlocks.BLUE_NETHER_BRICKS).sounds(BlockSoundGroup.NETHER_BRICKS)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block BLUE_NETHER_BRICK_WALL = registerBlock("blue_nether_brick_wall",
            new WallBlock(FabricBlockSettings.copyOf(ModBlocks.BLUE_NETHER_BRICKS).sounds(BlockSoundGroup.NETHER_BRICKS)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block PYRITE_BLOCK = registerBlock("pyrite_block",
            new Block(FabricBlockSettings.of(Material.METAL).strength(3.5f).requiresTool().sounds(BlockSoundGroup.COPPER)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block PYRITE_SLAB = registerBlock("pyrite_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.PYRITE_BLOCK).sounds(BlockSoundGroup.COPPER)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block PYRITE_STAIRS = registerBlock("pyrite_stairs",
            new ModStairsBlock(ModBlocks.PYRITE_BLOCK.getDefaultState(),
                    FabricBlockSettings.copyOf(ModBlocks.PYRITE_BLOCK).sounds(BlockSoundGroup.COPPER)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block PYRITE_WALL = registerBlock("pyrite_wall",
            new WallBlock(FabricBlockSettings.copyOf(ModBlocks.PYRITE_BLOCK).sounds(BlockSoundGroup.COPPER)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block PYRITE_BUTTON = registerBlock("pyrite_button",
            new StoneButtonBlock(FabricBlockSettings.of(Material.DECORATION).noCollision().strength(1f).sounds(BlockSoundGroup.COPPER)), ModItemGroup.NETHEREXP_BLOCKS);
    public static final Block PYRITE_LANTERN = registerBlock("pyrite_lantern",
            new PillarBlock(FabricBlockSettings.copyOf(ModBlocks.PYRITE_BLOCK).strength(0.4f).luminance(15).sounds(BlockSoundGroup.LANTERN)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block SOUL_PYRITE_LANTERN = registerBlock("soul_pyrite_lantern",
            new PillarBlock(FabricBlockSettings.copyOf(ModBlocks.PYRITE_LANTERN).luminance(10).sounds(BlockSoundGroup.LANTERN)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block PYRITE_NETHER_BRICKS = registerBlock("pyrite_nether_bricks",
            new RotatingBlock(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS).sounds(BlockSoundGroup.NETHER_BRICKS)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block SHROOMNIGHT = registerBlock("shroomnight",
            new Block(FabricBlockSettings.copyOf(Blocks.SHROOMLIGHT).luminance(8).sounds(BlockSoundGroup.SHROOMLIGHT)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block CRIMSON_SPROUTS = registerBlock("crimson_sprouts",
            new SproutsBlock(FabricBlockSettings.copyOf(Blocks.NETHER_SPROUTS).sounds(BlockSoundGroup.NETHER_SPROUTS)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block CHAINWIRE_FENCE = registerBlock("chainwire_fence",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.IRON_BARS).sounds(BlockSoundGroup.CHAIN)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block STACKED_BONES = registerBlock("stacked_bones",
            new Block(FabricBlockSettings.copyOf(Blocks.BONE_BLOCK).sounds(BlockSoundGroup.BONE)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block STACKED_BONE_SLAB = registerBlock("stacked_bone_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.STACKED_BONES).sounds(BlockSoundGroup.BONE)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block STACKED_BONE_STAIRS = registerBlock("stacked_bone_stairs",
            new ModStairsBlock(ModBlocks.STACKED_BONES.getDefaultState(),
                    FabricBlockSettings.copyOf(ModBlocks.STACKED_BONES).sounds(BlockSoundGroup.BONE)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block WITHER_BONE_BLOCK = registerBlock("wither_bone_block",
            new WitherBoneBlock(FabricBlockSettings.copyOf(Blocks.BONE_BLOCK).strength(4.5f,9.0f).sounds(BlockSoundGroup.BONE)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block STACKED_WITHER_BONES = registerBlock("stacked_wither_bones",
            new Block(FabricBlockSettings.copyOf(ModBlocks.WITHER_BONE_BLOCK).strength(2.0f,9.0f).sounds(BlockSoundGroup.BONE)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block STACKED_WITHER_BONE_SLAB = registerBlock("stacked_wither_bone_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.STACKED_WITHER_BONES).sounds(BlockSoundGroup.BONE)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block STACKED_WITHER_BONE_STAIRS = registerBlock("stacked_wither_bone_stairs",
            new ModStairsBlock(ModBlocks.STACKED_WITHER_BONES.getDefaultState(),
                    FabricBlockSettings.copyOf(ModBlocks.STACKED_WITHER_BONES).sounds(BlockSoundGroup.BONE)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block WEEPING_POLISHED_BLACKSTONE_BRICKS = registerBlock("weeping_polished_blackstone_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.POLISHED_BLACKSTONE_BRICKS)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block TWISTING_POLISHED_BLACKSTONE_BRICKS = registerBlock("twisting_polished_blackstone_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.POLISHED_BLACKSTONE_BRICKS)), ModItemGroup.NETHEREXP_BLOCKS);

    public static final Block POLISHED_BLACKSTONE_PILLAR = registerBlock("polished_blackstone_pillar",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_BLACKSTONE_BRICKS)), ModItemGroup.NETHEREXP_BLOCKS);

    // Methods

    private static Block registerBlock(String name, Block block, ItemGroup tab) {
        registerBlockItem(name, block, tab);
        return Registry.register(Registry.BLOCK, new Identifier(NetherExp.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup tab) {
        return Registry.register(Registry.ITEM, new Identifier(NetherExp.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(tab)));
    }

    public static void registerModBlocks() {
        NetherExp.LOGGER.debug("Registering ModBlocks for " + NetherExp.MOD_ID);
    }
}
