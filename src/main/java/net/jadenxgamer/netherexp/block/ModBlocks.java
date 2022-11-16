package net.jadenxgamer.netherexp.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.block.custom.ModStairsBlock;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    // Blocks List:

    // Soul Slate Blocks

    public static final Block SOUL_SLATE = registerBlock("soul_slate",
            new Block(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool().sounds(BlockSoundGroup.DEEPSLATE)), ItemGroup.BUILDING_BLOCKS);

    public static final Block SOUL_SLATE_SLAB = registerBlock("soul_slate_slab",
            new SlabBlock(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool().sounds(BlockSoundGroup.DEEPSLATE)), ItemGroup.BUILDING_BLOCKS);

    public static final Block SOUL_SLATE_STAIRS = registerBlock("soul_slate_stairs",
            new ModStairsBlock(ModBlocks.SOUL_SLATE.getDefaultState(),
                    FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool().sounds(BlockSoundGroup.DEEPSLATE)), ItemGroup.BUILDING_BLOCKS);

    public static final Block SOUL_SLATE_WALL = registerBlock("soul_slate_wall",
            new WallBlock(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool().sounds(BlockSoundGroup.DEEPSLATE_BRICKS)), ItemGroup.DECORATIONS);

    public static final Block SOUL_SLATE_BRICKS = registerBlock("soul_slate_bricks",
            new Block(FabricBlockSettings.of(Material.STONE).strength(2f).requiresTool().sounds(BlockSoundGroup.DEEPSLATE_BRICKS)), ItemGroup.BUILDING_BLOCKS);

    public static final Block SOUL_SLATE_BRICK_SLAB = registerBlock("soul_slate_brick_slab",
            new SlabBlock(FabricBlockSettings.of(Material.STONE).strength(2f).requiresTool().sounds(BlockSoundGroup.DEEPSLATE_BRICKS)), ItemGroup.BUILDING_BLOCKS);

    public static final Block SOUL_SLATE_BRICK_STAIRS = registerBlock("soul_slate_brick_stairs",
            new ModStairsBlock(ModBlocks.SOUL_SLATE_BRICKS.getDefaultState(),
            FabricBlockSettings.of(Material.STONE).strength(2f).requiresTool().sounds(BlockSoundGroup.DEEPSLATE_BRICKS)), ItemGroup.BUILDING_BLOCKS);

    public static final Block SOUL_SLATE_BRICK_WALL = registerBlock("soul_slate_brick_wall",
            new WallBlock(FabricBlockSettings.of(Material.STONE).strength(2f).requiresTool().sounds(BlockSoundGroup.DEEPSLATE_BRICKS)), ItemGroup.DECORATIONS);

    public static final Block CRACKED_SOUL_SLATE_BRICKS = registerBlock("cracked_soul_slate_bricks",
            new Block(FabricBlockSettings.of(Material.STONE).strength(2f).requiresTool().sounds(BlockSoundGroup.DEEPSLATE_BRICKS)), ItemGroup.BUILDING_BLOCKS);

    public static final Block SOUL_SLATE_BRICK_PILLAR = registerBlock("soul_slate_brick_pillar",
            new PillarBlock(FabricBlockSettings.of(Material.STONE).strength(2f).requiresTool().sounds(BlockSoundGroup.DEEPSLATE_BRICKS)), ItemGroup.BUILDING_BLOCKS);

    public static final Block CHISELED_SOUL_SLATE_BRICKS = registerBlock("chiseled_soul_slate_bricks",
            new Block(FabricBlockSettings.of(Material.STONE).strength(2f).requiresTool().sounds(BlockSoundGroup.DEEPSLATE_BRICKS)), ItemGroup.BUILDING_BLOCKS);

    // Basalt Blocks

    public static final Block BASALT_SLAB = registerBlock("basalt_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.BASALT).sounds(BlockSoundGroup.BASALT)), ItemGroup.BUILDING_BLOCKS);

    public static final Block POLISHED_BASALT_BRICKS = registerBlock("polished_basalt_bricks",
            new Block(FabricBlockSettings.of(Material.STONE).strength(1f).requiresTool().sounds(BlockSoundGroup.BASALT)), ItemGroup.BUILDING_BLOCKS);

    public static final Block POLISHED_BASALT_BRICK_SLAB = registerBlock("polished_basalt_brick_slab",
            new SlabBlock(FabricBlockSettings.of(Material.STONE).strength(1f).requiresTool().sounds(BlockSoundGroup.BASALT)), ItemGroup.BUILDING_BLOCKS);

    public static final Block GILDED_BASALT_BRICKS = registerBlock("gilded_basalt_bricks",
            new Block(FabricBlockSettings.of(Material.STONE).strength(1f).requiresTool().sounds(BlockSoundGroup.BASALT)), ItemGroup.BUILDING_BLOCKS);

    public static final Block GILDED_BASALT_BRICK_SLAB = registerBlock("gilded_basalt_brick_slab",
            new SlabBlock(FabricBlockSettings.of(Material.STONE).strength(1f).requiresTool().sounds(BlockSoundGroup.BASALT)), ItemGroup.BUILDING_BLOCKS);


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
