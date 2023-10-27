package net.jadenxgamer.netherexp.registry.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.custom.*;
import net.jadenxgamer.netherexp.registry.misc_registry.ModTags;
import net.jadenxgamer.netherexp.registry.misc_registry.ModWoodType;
import net.jadenxgamer.netherexp.registry.particle.ModParticles;
import net.jadenxgamer.netherexp.registry.sound.ModSoundEvents;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.StructureKeys;

@SuppressWarnings({"deprecation", "unused"})
public class ModBlocks {

    // LIST OF BLOCKS:

    // Soul SLate

    public static final Block SOUL_SLATE = registerBlock("soul_slate",
            new Block(FabricBlockSettings.of().mapColor(MapColor.BROWN).strength(4f).requiresTool().sounds(ModSoundEvents.SOUL_SLATE)));

    public static final Block PALE_SOUL_SLATE = registerBlock("pale_soul_slate",
            new Block(FabricBlockSettings.copyOf(ModBlocks.SOUL_SLATE).sounds(ModSoundEvents.SOUL_SLATE)));

    public static final Block JAGGED_SOUL_SLATE = registerBlock("jagged_soul_slate",
            new Block(FabricBlockSettings.copyOf(ModBlocks.SOUL_SLATE).sounds(ModSoundEvents.SOUL_SLATE)));

    public static final Block SOUL_SLATE_SLAB = registerBlock("soul_slate_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.SOUL_SLATE).sounds(ModSoundEvents.SOUL_SLATE)));

    public static final Block SOUL_SLATE_STAIRS = registerBlock("soul_slate_stairs",
            new ModStairsBlock(ModBlocks.SOUL_SLATE.getDefaultState(),
                    FabricBlockSettings.copyOf(ModBlocks.SOUL_SLATE).sounds(ModSoundEvents.SOUL_SLATE)));

    public static final Block SOUL_SLATE_WALL = registerBlock("soul_slate_wall",
            new WallBlock(FabricBlockSettings.copyOf(ModBlocks.SOUL_SLATE).sounds(ModSoundEvents.SOUL_SLATE)));

    // Soul Slate Bricks

    public static final Block SOUL_SLATE_BRICKS = registerBlock("soul_slate_bricks",
            new Block(FabricBlockSettings.of().mapColor(MapColor.BROWN).strength(2f).requiresTool().sounds(ModSoundEvents.SOUL_SLATE_BRICKS)));

    public static final Block SOUL_SLATE_BRICK_SLAB = registerBlock("soul_slate_brick_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.SOUL_SLATE_BRICKS).sounds(ModSoundEvents.SOUL_SLATE_BRICKS)));

    public static final Block SOUL_SLATE_BRICK_STAIRS = registerBlock("soul_slate_brick_stairs",
            new ModStairsBlock(ModBlocks.SOUL_SLATE_BRICKS.getDefaultState(),
            FabricBlockSettings.copyOf(ModBlocks.SOUL_SLATE_BRICKS).sounds(ModSoundEvents.SOUL_SLATE_BRICKS)));

    public static final Block SOUL_SLATE_BRICK_WALL = registerBlock("soul_slate_brick_wall",
            new WallBlock(FabricBlockSettings.copyOf(ModBlocks.SOUL_SLATE_BRICKS).sounds(ModSoundEvents.SOUL_SLATE_BRICKS)));

    public static final Block INDENTED_SOUL_SLATE_BRICKS = registerBlock("indented_soul_slate_bricks",
            new LightAbleBlock(FabricBlockSettings.copyOf(ModBlocks.SOUL_SLATE_BRICKS).luminance(
            state -> state.get(LightAbleBlock.LIT) ? 7 : 0).sounds(ModSoundEvents.SOUL_SLATE_BRICKS)));

    public static final Block CRACKED_SOUL_SLATE_BRICKS = registerBlock("cracked_soul_slate_bricks",
            new Block(FabricBlockSettings.copyOf(ModBlocks.SOUL_SLATE_BRICKS).sounds(ModSoundEvents.SOUL_SLATE_BRICKS)));

    public static final Block SOUL_SLATE_BRICK_PILLAR = registerBlock("soul_slate_brick_pillar",
            new PillarBlock(FabricBlockSettings.copyOf(ModBlocks.SOUL_SLATE_BRICKS).sounds(ModSoundEvents.SOUL_SLATE_BRICKS)));

    public static final Block CHISELED_SOUL_SLATE_BRICKS = registerBlock("chiseled_soul_slate_bricks",
            new LightAbleBlock(FabricBlockSettings.copyOf(ModBlocks.SOUL_SLATE_BRICKS).luminance(
            state -> state.get(LightAbleBlock.LIT) ? 7 : 0).sounds(ModSoundEvents.SOUL_SLATE_BRICKS)));

    // Soul Candle

    //TODO: Add a Soul Candle Cake
    public static final Block SOUL_CANDLE = registerBlock("soul_candle",
            new SoulCandleBlock(FabricBlockSettings.of().mapColor(MapColor.BROWN).noCollision().nonOpaque().strength(0.1f).sounds(ModSoundEvents.SOUL_CANDLE).luminance(SoulCandleBlock.STATE_TO_LUMINANCE)));

    // Soul Decorations

    public static final Block SOUL_GLASS = registerBlock("soul_glass",
            new SoulGlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_BROWN).strength(0.3f, 1200.0f).sounds(BlockSoundGroup.GLASS)));

    public static final Block SOUL_SWIRLS = registerBlock("soul_swirls",
            new SoulSwirlsBlock(7,3,FabricBlockSettings.of().mapColor(MapColor.BROWN).noCollision().breakInstantly().sounds(BlockSoundGroup.NETHER_SPROUTS)));

    public static final Block ECTO_SOUL_SAND = registerBlock("ecto_soul_sand",
            new EctoSoulSandBlock(FabricBlockSettings.copyOf(Blocks.SOUL_SAND).mapColor(MapColor.BROWN).luminance(3).sounds(BlockSoundGroup.SOUL_SAND)));

    public static final Block SOUL_MAGMA_BLOCK = registerBlock("soul_magma_block",
            new SoulMagmaBlock(FabricBlockSettings.copyOf(Blocks.SOUL_SOIL).mapColor(MapColor.LIGHT_BLUE).luminance(3).sounds(ModSoundEvents.SOUL_MAGMA_BLOCK)));

    // Sorrowsquash

    public static final Block SORROWSQUASH = registerBlock("sorrowsquash",
            new SorrowsquashBlock(FabricBlockSettings.of().mapColor(MapColor.WHITE_GRAY).strength(1.0f).sounds(BlockSoundGroup.NETHER_STEM)));

    public static final Block CARVED_SORROWSQUASH = registerBlock("carved_sorrowsquash",
            new CarvedSorrowsquashBlock(FabricBlockSettings.of().mapColor(MapColor.WHITE_GRAY).strength(1.0f).sounds(BlockSoundGroup.NETHER_STEM)));

    public static final Block GHOUL_O_LANTERN = registerBlock("ghoul_o_lantern",
            new CarvedSorrowsquashBlock(FabricBlockSettings.of().mapColor(MapColor.ORANGE).strength(1.0f).luminance(15).sounds(BlockSoundGroup.NETHER_STEM)));

    public static final Block SOUL_GHOUL_O_LANTERN = registerBlock("soul_ghoul_o_lantern",
            new CarvedSorrowsquashBlock(FabricBlockSettings.copyOf(ModBlocks.GHOUL_O_LANTERN).mapColor(MapColor.LIGHT_BLUE).sounds(BlockSoundGroup.NETHER_STEM)));

    public static final Block SOUL_JACK_O_LANTERN = registerBlock("soul_jack_o_lantern",
            new CarvedPumpkinBlock(FabricBlockSettings.copyOf(Blocks.JACK_O_LANTERN).mapColor(MapColor.LIGHT_BLUE).sounds(BlockSoundGroup.WOOD)));

    public static final Block SORROWSQUASH_STEM = registerBlockWithoutItem("sorrowsquash_stem",
            new VineStemBlock((GourdBlock)SORROWSQUASH, () -> Items.PUMPKIN_SEEDS,FabricBlockSettings.of().mapColor(MapColor.BROWN).noCollision().breakInstantly().ticksRandomly().sounds(BlockSoundGroup.NETHER_STEM)));

    public static final Block SORROWSQUASH_STEM_PLANT = registerBlockWithoutItem("sorrowsquash_stem_plant",
            new VineStemPlantBlock((GourdBlock)SORROWSQUASH, () -> Items.PUMPKIN_SEEDS,FabricBlockSettings.of().mapColor(MapColor.BROWN).noCollision().breakInstantly().sounds(BlockSoundGroup.NETHER_STEM)));

    // Smooth Netherrack

    public static final Block SMOOTH_NETHERRACK = registerBlock("smooth_netherrack",
            new Block(FabricBlockSettings.copyOf(Blocks.NETHERRACK).strength(1.4f).requiresTool().sounds(BlockSoundGroup.NETHERRACK)));

    public static final Block SMOOTH_NETHERRACK_SLAB = registerBlock("smooth_netherrack_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.SMOOTH_NETHERRACK).sounds(BlockSoundGroup.NETHERRACK)));

    public static final Block SMOOTH_NETHERRACK_STAIRS = registerBlock("smooth_netherrack_stairs",
            new ModStairsBlock(ModBlocks.SMOOTH_NETHERRACK.getDefaultState(),
                    FabricBlockSettings.copyOf(ModBlocks.SMOOTH_NETHERRACK).sounds(BlockSoundGroup.NETHERRACK)));

    public static final Block SMOOTH_NETHERRACK_WALL = registerBlock("smooth_netherrack_wall",
            new WallBlock(FabricBlockSettings.copyOf(ModBlocks.SMOOTH_NETHERRACK).sounds(BlockSoundGroup.NETHERRACK)));

    // Netherrack Bricks

    public static final Block NETHERRACK_BRICKS = registerBlock("netherrack_bricks",
            new Block(FabricBlockSettings.copyOf(ModBlocks.SMOOTH_NETHERRACK).requiresTool().sounds(ModSoundEvents.NETHERRACK_BRICKS)));

    public static final Block NETHERRACK_BRICK_SLAB = registerBlock("netherrack_brick_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.NETHERRACK_BRICKS).sounds(ModSoundEvents.NETHERRACK_BRICKS)));

    public static final Block NETHERRACK_BRICK_STAIRS = registerBlock("netherrack_brick_stairs",
            new ModStairsBlock(ModBlocks.NETHERRACK_BRICKS.getDefaultState(),
            FabricBlockSettings.copyOf(ModBlocks.NETHERRACK_BRICKS).sounds(ModSoundEvents.NETHERRACK_BRICKS)));

    public static final Block NETHERRACK_BRICK_WALL = registerBlock("netherrack_brick_wall",
            new WallBlock(FabricBlockSettings.copyOf(ModBlocks.NETHERRACK_BRICKS).sounds(ModSoundEvents.NETHERRACK_BRICKS)));

    public static final Block NETHERRACK_TILES = registerBlock("netherrack_tiles",
            new Block(FabricBlockSettings.copyOf(ModBlocks.NETHERRACK_BRICKS).sounds(ModSoundEvents.NETHERRACK_BRICKS)));

    public static final Block NETHERRACK_PILLAR = registerBlock("netherrack_pillar",
            new PillarBlock(FabricBlockSettings.copyOf(ModBlocks.NETHERRACK_BRICKS).sounds(ModSoundEvents.NETHERRACK_BRICKS)));

    // Basalt

    public static final Block BASALT_SLAB = registerBlock("basalt_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.BASALT).sounds(BlockSoundGroup.BASALT)));

    public static final Block POLISHED_BASALT_BRICKS = registerBlock("polished_basalt_bricks",
            new PolishedBasaltBrickBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_BASALT).sounds(ModSoundEvents.BASALT_BRICKS)));

    public static final Block POLISHED_BASALT_BRICK_SLAB = registerBlock("polished_basalt_brick_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.POLISHED_BASALT_BRICKS).sounds(ModSoundEvents.BASALT_BRICKS)));

    public static final Block POLISHED_BASALT_BRICK_STAIRS = registerBlock("polished_basalt_brick_stairs",
            new ModStairsBlock(Blocks.POLISHED_BASALT.getDefaultState(),
            FabricBlockSettings.copyOf(ModBlocks.POLISHED_BASALT_BRICKS).sounds(ModSoundEvents.BASALT_BRICKS)));

    // Enigma Block

    public static final Block ENIGMA_FLESH = registerBlock("enigma_flesh",
            new PillarBlock(FabricBlockSettings.of().mapColor(MapColor.TERRACOTTA_PURPLE).strength(1.8f).sounds(BlockSoundGroup.FUNGUS)));

    public static final Block STRANGE_ENIGMA_FLESH = registerBlock("strange_enigma_flesh",
            new StrangeEnigmaFleshBlock(FabricBlockSettings.copyOf(ModBlocks.ENIGMA_FLESH).mapColor(MapColor.DIAMOND_BLUE).luminance(12).sounds(BlockSoundGroup.FUNGUS)));

    public static final Block ENIGMA_CROWN = registerBlock("enigma_crown",
            new EnigmaCrownBlock(FabricBlockSettings.of().mapColor(MapColor.TERRACOTTA_PURPLE).strength(0.2f).sounds(BlockSoundGroup.FUNGUS)));

    public static final Block ENIGMA_SHELF = registerBlock("enigma_shelf",
            new DeadCoralWallFanBlock(FabricBlockSettings.of().mapColor(MapColor.TERRACOTTA_PURPLE).noCollision().breakInstantly().sounds(BlockSoundGroup.FUNGUS)));

    // Claret Woodset

    public static final Block CLARET_STEM = registerBlock("claret_stem",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.WARPED_STEM).mapColor(MapColor.DARK_DULL_PINK).sounds(BlockSoundGroup.NETHER_STEM)));

    public static final Block CLARET_HYPHAE = registerBlock("claret_hyphae",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.WARPED_HYPHAE).mapColor(MapColor.DARK_DULL_PINK).sounds(BlockSoundGroup.NETHER_STEM)));

    public static final Block STRIPPED_CLARET_STEM = registerBlock("stripped_claret_stem",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_WARPED_STEM).mapColor(MapColor.DARK_RED).sounds(BlockSoundGroup.NETHER_STEM)));

    public static final Block STRIPPED_CLARET_HYPHAE = registerBlock("stripped_claret_hyphae",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_WARPED_HYPHAE).mapColor(MapColor.DARK_RED).sounds(BlockSoundGroup.NETHER_STEM)));
    public static final Block CLARET_PLANKS = registerBlock("claret_planks",
            new Block(FabricBlockSettings.of().strength(2.0f, 3.0f).mapColor(MapColor.DARK_RED).sounds(BlockSoundGroup.NETHER_WOOD)));

    public static final Block CLARET_SLAB = registerBlock("claret_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.CLARET_PLANKS).mapColor(MapColor.DARK_RED).sounds(BlockSoundGroup.NETHER_WOOD)));

    public static final Block CLARET_STAIRS = registerBlock("claret_stairs",
            new ModStairsBlock(ModBlocks.CLARET_PLANKS.getDefaultState(),
                    FabricBlockSettings.copyOf(ModBlocks.CLARET_PLANKS).mapColor(MapColor.DARK_RED).sounds(BlockSoundGroup.NETHER_WOOD)));

    public static final Block CLARET_FENCE = registerBlock("claret_fence",
            new FenceBlock(FabricBlockSettings.copyOf(ModBlocks.CLARET_PLANKS).mapColor(MapColor.DARK_RED).sounds(BlockSoundGroup.NETHER_WOOD)));

    public static final Block CLARET_FENCE_GATE = registerBlock("claret_fence_gate",
            new FenceGateBlock(FabricBlockSettings.copyOf(ModBlocks.CLARET_PLANKS).mapColor(MapColor.DARK_RED).sounds(BlockSoundGroup.NETHER_WOOD), WoodType.CRIMSON));

    public static final Block CLARET_DOOR = registerBlock("claret_door",
            new DoorBlock(FabricBlockSettings.copyOf(Blocks.WARPED_DOOR).mapColor(MapColor.DARK_RED).sounds(BlockSoundGroup.NETHER_WOOD), BlockSetType.CRIMSON));

    public static final Block CLARET_TRAPDOOR = registerBlock("claret_trapdoor",
            new TrapdoorBlock(FabricBlockSettings.copyOf(Blocks.WARPED_TRAPDOOR).mapColor(MapColor.DARK_RED).sounds(BlockSoundGroup.NETHER_WOOD), BlockSetType.CRIMSON));

    public static final Block CLARET_BUTTON = registerBlock("claret_button",
            new ButtonBlock(FabricBlockSettings.of().noCollision().strength(0.5f).mapColor(MapColor.DARK_RED).sounds(BlockSoundGroup.NETHER_WOOD), BlockSetType.CRIMSON, 30, true));

    public static final Block CLARET_PRESSURE_PLATE = registerBlock("claret_pressure_plate",
            new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING,FabricBlockSettings.copyOf(ModBlocks.CLARET_PLANKS).mapColor(MapColor.DARK_RED).strength(0.5f).noCollision().sounds(BlockSoundGroup.NETHER_WOOD), BlockSetType.CRIMSON));

    public static final Block CLARET_SIGN = registerBlockWithoutItem("claret_sign",
            new SignBlock(FabricBlockSettings.copyOf(Blocks.WARPED_SIGN).mapColor(MapColor.DARK_RED).strength(1.0f).sounds(BlockSoundGroup.NETHER_WOOD), ModWoodType.CLARET));

    public static final Block CLARET_WALL_SIGN = registerBlockWithoutItem("claret_wall_sign",
            new WallSignBlock(FabricBlockSettings.copyOf(Blocks.WARPED_WALL_SIGN).mapColor(MapColor.DARK_RED).strength(1.0f).dropsLike(CLARET_SIGN).sounds(BlockSoundGroup.NETHER_WOOD), ModWoodType.CLARET));

    public static final Block CLARET_HANGING_SIGN = registerBlockWithoutItem("claret_hanging_sign",
            new HangingSignBlock(FabricBlockSettings.copyOf(Blocks.WARPED_HANGING_SIGN).mapColor(MapColor.DARK_RED).strength(1.0f).sounds(BlockSoundGroup.NETHER_WOOD), ModWoodType.CLARET));

    public static final Block CLARET_WALL_HANGING_SIGN = registerBlockWithoutItem("claret_wall_hanging_sign",
            new WallHangingSignBlock(FabricBlockSettings.copyOf(Blocks.WARPED_WALL_HANGING_SIGN).mapColor(MapColor.DARK_RED).strength(1.0f).dropsLike(CLARET_HANGING_SIGN).sounds(BlockSoundGroup.NETHER_WOOD), ModWoodType.CLARET));

    // Magma Cream

    public static final Block MAGMA_CREAM_BLOCK = registerBlock("magma_cream_block",
            new MagmaCreamBlock(FabricBlockSettings.of().mapColor(MapColor.TERRACOTTA_LIME).breakInstantly().nonOpaque().sounds(BlockSoundGroup.SLIME)));

    // Smokestalk

    public static final Block EXPLOSIVE_SCORIA = registerBlock("explosive_scoria",
            new ExplosiveScoriaBlock(FabricBlockSettings.of().mapColor(MapColor.TERRACOTTA_RED).strength(3.0f,0.2f).requiresTool().sounds(BlockSoundGroup.NETHER_ORE)));

    public static final Block IGNEOUS_REEDS = registerBlock("igneous_reeds",
            new IgneousReeds(FabricBlockSettings.of().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).noCollision().breakInstantly().requiresTool().offset(AbstractBlock.OffsetType.XZ).sounds(ModSoundEvents.SMOKESTALK)));

    //TODO: Add LootTables
    public static final Block IGNEOUS_VINES = registerBlock("igneous_vines",
            new IgneousVinesBlock(FabricBlockSettings.of().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).ticksRandomly().noCollision().breakInstantly().sounds(BlockSoundGroup.WEEPING_VINES)));

    public static final Block IGNEOUS_VINES_PLANT = registerBlockWithoutItem("igneous_vines_plant",
            new IgneousVinesPlantBlock(FabricBlockSettings.of().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).noCollision().breakInstantly().sounds(BlockSoundGroup.WEEPING_VINES)));

    public static final Block SMOKESTALK = registerBlock("smokestalk",
            new SmokestalkBlock(FabricBlockSettings.of().ticksRandomly().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).breakInstantly().sounds(ModSoundEvents.SMOKESTALK)));

    public static final Block SMOKESTALK_PLANT = registerBlockWithoutItem("smokestalk_plant",
            new SmokestalkPlantBlock(FabricBlockSettings.of().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).breakInstantly().sounds(ModSoundEvents.SMOKESTALK)));

    // Smokestalk Woodset

    public static final Block SMOKESTALK_BLOCK = registerBlock("smokestalk_block",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.WARPED_STEM).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).sounds(ModSoundEvents.SMOKESTALK_WOOD)));

    public static final Block STRIPPED_SMOKESTALK_BLOCK = registerBlock("stripped_smokestalk_block",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_WARPED_STEM).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).sounds(ModSoundEvents.SMOKESTALK_WOOD)));

    public static final Block SMOKESTALK_PLANKS = registerBlock("smokestalk_planks",
            new Block(FabricBlockSettings.of().strength(2.0f, 3.0f).mapColor(MapColor.GRAY).sounds(ModSoundEvents.SMOKESTALK_WOOD)));

    public static final Block SMOKESTALK_SLAB = registerBlock("smokestalk_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.SMOKESTALK_PLANKS).mapColor(MapColor.GRAY).sounds(ModSoundEvents.SMOKESTALK_WOOD)));

    public static final Block SMOKESTALK_STAIRS = registerBlock("smokestalk_stairs",
            new ModStairsBlock(ModBlocks.CLARET_PLANKS.getDefaultState(),
                    FabricBlockSettings.copyOf(ModBlocks.SMOKESTALK_PLANKS).mapColor(MapColor.GRAY).sounds(ModSoundEvents.SMOKESTALK_WOOD)));

    public static final Block SMOKESTALK_FENCE = registerBlock("smokestalk_fence",
            new FenceBlock(FabricBlockSettings.copyOf(ModBlocks.SMOKESTALK_PLANKS).mapColor(MapColor.GRAY).sounds(ModSoundEvents.SMOKESTALK_WOOD)));

    public static final Block SMOKESTALK_FENCE_GATE = registerBlock("smokestalk_fence_gate",
            new FenceGateBlock(FabricBlockSettings.copyOf(ModBlocks.SMOKESTALK_PLANKS).mapColor(MapColor.GRAY).sounds(ModSoundEvents.SMOKESTALK_WOOD), WoodType.BAMBOO));

    public static final Block SMOKESTALK_DOOR = registerBlock("smokestalk_door",
            new DoorBlock(FabricBlockSettings.copyOf(Blocks.WARPED_DOOR).mapColor(MapColor.GRAY).sounds(ModSoundEvents.SMOKESTALK_WOOD), BlockSetType.BAMBOO));

    public static final Block SMOKESTALK_TRAPDOOR = registerBlock("smokestalk_trapdoor",
            new TrapdoorBlock(FabricBlockSettings.copyOf(Blocks.WARPED_TRAPDOOR).mapColor(MapColor.GRAY).sounds(ModSoundEvents.SMOKESTALK_WOOD), BlockSetType.BAMBOO));

    public static final Block SMOKESTALK_BUTTON = registerBlock("smokestalk_button",
            new ButtonBlock(FabricBlockSettings.of().noCollision().strength(0.5f).mapColor(MapColor.GRAY).sounds(BlockSoundGroup.NETHER_WOOD), BlockSetType.BAMBOO, 30, true));

    public static final Block SMOKESTALK_PRESSURE_PLATE = registerBlock("smokestalk_pressure_plate",
            new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING,FabricBlockSettings.copyOf(ModBlocks.SMOKESTALK_PLANKS).mapColor(MapColor.GRAY).strength(0.5f).noCollision().sounds(ModSoundEvents.SMOKESTALK_WOOD), BlockSetType.BAMBOO));

    public static final Block SMOKESTALK_SIGN = registerBlockWithoutItem("smokestalk_sign",
            new SignBlock(FabricBlockSettings.copyOf(Blocks.WARPED_SIGN).mapColor(MapColor.GRAY).strength(1.0f).sounds(ModSoundEvents.SMOKESTALK_WOOD), ModWoodType.SMOKESTALK));

    public static final Block SMOKESTALK_WALL_SIGN = registerBlockWithoutItem("smokestalk_wall_sign",
            new WallSignBlock(FabricBlockSettings.copyOf(Blocks.WARPED_WALL_SIGN).mapColor(MapColor.GRAY).strength(1.0f).dropsLike(SMOKESTALK_SIGN).sounds(ModSoundEvents.SMOKESTALK_WOOD), ModWoodType.SMOKESTALK));

    public static final Block SMOKESTALK_HANGING_SIGN = registerBlockWithoutItem("smokestalk_hanging_sign",
            new HangingSignBlock(FabricBlockSettings.copyOf(Blocks.WARPED_HANGING_SIGN).mapColor(MapColor.GRAY).strength(1.0f).sounds(ModSoundEvents.SMOKESTALK_WOOD), ModWoodType.SMOKESTALK));

    public static final Block SMOKESTALK_WALL_HANGING_SIGN = registerBlockWithoutItem("smokestalk_wall_hanging_sign",
            new WallHangingSignBlock(FabricBlockSettings.copyOf(Blocks.WARPED_WALL_HANGING_SIGN).mapColor(MapColor.GRAY).strength(1.0f).dropsLike(SMOKESTALK_HANGING_SIGN).sounds(ModSoundEvents.SMOKESTALK_WOOD), ModWoodType.SMOKESTALK));

    // Quartz Block

    public static final Block QUARTZ_CRYSTAL = registerBlock("quartz_crystal",
            new AmethystClusterBlock(7, 3, FabricBlockSettings.of().mapColor(MapColor.OFF_WHITE).nonOpaque().strength(1.5f).sounds(ModSoundEvents.QUARTZ_CRYSTAL)));

    public static final Block QUARTZ_CRYSTAL_BLOCK = registerBlock("quartz_crystal_block",
            new PillarBlock(FabricBlockSettings.of().strength(2.5f).mapColor(MapColor.OFF_WHITE).requiresTool().sounds(ModSoundEvents.QUARTZ_CRYSTAL)));

    public static final Block SMOOTH_QUARTZ_WALL = registerBlock("smooth_quartz_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.QUARTZ_BRICKS).mapColor(MapColor.OFF_WHITE)));

    public static final Block QUARTZ_BRICK_WALL = registerBlock("quartz_brick_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.QUARTZ_BRICKS).mapColor(MapColor.OFF_WHITE)));

    public static final Block CRACKED_QUARTZ_BRICKS = registerBlock("cracked_quartz_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.QUARTZ_BRICKS).mapColor(MapColor.OFF_WHITE)));

    public static final Block CHISELED_QUARTZ_PILLAR = registerBlock("chiseled_quartz_pillar",
            new ModFacingBlock(FabricBlockSettings.copyOf(Blocks.QUARTZ_PILLAR).mapColor(MapColor.OFF_WHITE)));

    // Silica Sandstone

    public static final Block SILICA_SAND = registerBlock("silica_sand",
            new SandBlock(8812140, FabricBlockSettings.of().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).strength(0.4f).sounds(BlockSoundGroup.SAND)));

    public static final Block SILICA_SANDSTONE = registerBlock("silica_sandstone",
            new Block(FabricBlockSettings.of().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).strength(1.0f).requiresTool()));

    public static final Block SILICA_SANDSTONE_SLAB = registerBlock("silica_sandstone_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.SILICA_SANDSTONE).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));

    public static final Block SILICA_SANDSTONE_STAIRS = registerBlock("silica_sandstone_stairs",
            new ModStairsBlock(ModBlocks.SILICA_SANDSTONE.getDefaultState(),
            FabricBlockSettings.copyOf(ModBlocks.SILICA_SANDSTONE).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));

    public static final Block SILICA_SANDSTONE_WALL = registerBlock("silica_sandstone_wall",
            new WallBlock(FabricBlockSettings.copyOf(ModBlocks.SILICA_SANDSTONE).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));

    // Cut Silica Sandstone

    public static final Block CUT_SILICA_SANDSTONE = registerBlock("cut_silica_sandstone",
            new Block(FabricBlockSettings.of().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).strength(1.0f, 6.0f).requiresTool()));

    public static final Block CUT_SILICA_SANDSTONE_SLAB = registerBlock("cut_silica_sandstone_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.CUT_SILICA_SANDSTONE).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));

    public static final Block CHISELED_SILICA_SANDSTONE = registerBlock("chiseled_silica_sandstone",
            new Block(FabricBlockSettings.copyOf(ModBlocks.CUT_SILICA_SANDSTONE).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));

    // Smooth Silica Sandstone

    public static final Block SMOOTH_SILICA_SANDSTONE = registerBlock("smooth_silica_sandstone",
            new Block(FabricBlockSettings.of().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).strength(2.2f, 6.0f)));

    public static final Block SMOOTH_SILICA_SANDSTONE_SLAB = registerBlock("smooth_silica_sandstone_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.SMOOTH_SILICA_SANDSTONE).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));

    public static final Block SMOOTH_SILICA_SANDSTONE_STAIRS = registerBlock("smooth_silica_sandstone_stairs",
            new ModStairsBlock(ModBlocks.SMOOTH_SILICA_SANDSTONE.getDefaultState(),
                    FabricBlockSettings.copyOf(ModBlocks.SMOOTH_SILICA_SANDSTONE).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));

    // Nether Bricks

    public static final Block NETHER_BRICK_PILLAR = registerBlock("nether_brick_pillar",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS).mapColor(MapColor.DARK_RED).sounds(BlockSoundGroup.NETHER_BRICKS)));

    public static final Block RED_MIXED_NETHER_BRICKS = registerBlock("red_mixed_nether_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS).mapColor(MapColor.DARK_RED).sounds(BlockSoundGroup.NETHER_BRICKS)));

    public static final Block BLUE_MIXED_NETHER_BRICKS = registerBlock("blue_mixed_nether_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS).mapColor(MapColor.DARK_RED).sounds(BlockSoundGroup.NETHER_BRICKS)));

    // Blue Nether Bricks

    public static final Block BLUE_NETHER_BRICKS = registerBlock("blue_nether_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.RED_NETHER_BRICKS).mapColor(MapColor.DARK_AQUA).sounds(BlockSoundGroup.NETHER_BRICKS)));

    public static final Block BLUE_NETHER_BRICK_SLAB = registerBlock("blue_nether_brick_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.BLUE_NETHER_BRICKS).mapColor(MapColor.DARK_AQUA).sounds(BlockSoundGroup.NETHER_BRICKS)));

    public static final Block BLUE_NETHER_BRICK_STAIRS = registerBlock("blue_nether_brick_stairs",
            new ModStairsBlock(ModBlocks.BLUE_NETHER_BRICKS.getDefaultState(),
            FabricBlockSettings.copyOf(ModBlocks.BLUE_NETHER_BRICKS).mapColor(MapColor.DARK_AQUA).sounds(BlockSoundGroup.NETHER_BRICKS)));

    public static final Block BLUE_NETHER_BRICK_WALL = registerBlock("blue_nether_brick_wall",
            new WallBlock(FabricBlockSettings.copyOf(ModBlocks.BLUE_NETHER_BRICKS).mapColor(MapColor.DARK_AQUA).sounds(BlockSoundGroup.NETHER_BRICKS)));

    // Pyrite

    public static final Block PYRITE_BLOCK = registerBlock("pyrite_block",
            new Block(FabricBlockSettings.of().strength(3.5f,6.5f).mapColor(MapColor.TERRACOTTA_ORANGE).requiresTool().sounds(BlockSoundGroup.COPPER)));

    public static final Block PYRITE_SLAB = registerBlock("pyrite_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.PYRITE_BLOCK).mapColor(MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.COPPER)));

    public static final Block PYRITE_STAIRS = registerBlock("pyrite_stairs",
            new ModStairsBlock(ModBlocks.PYRITE_BLOCK.getDefaultState(),
                    FabricBlockSettings.copyOf(ModBlocks.PYRITE_BLOCK).mapColor(MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.COPPER)));

    public static final Block PYRITE_WALL = registerBlock("pyrite_wall",
            new WallBlock(FabricBlockSettings.copyOf(ModBlocks.PYRITE_BLOCK).mapColor(MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.COPPER)));

    public static final Block PYRITE_BUTTON = registerBlock("pyrite_button",
            new ButtonBlock(FabricBlockSettings.of().noCollision().strength(1f).mapColor(MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.COPPER), BlockSetType.STONE, 10, false));

    public static final Block MEDIUM_WEIGHTED_PRESSURE_PLATE = registerBlock("medium_weighted_pressure_plate",
            new WeightedPressurePlateBlock(75, FabricBlockSettings.of().mapColor(MapColor.TERRACOTTA_ORANGE).noCollision().strength(1f).sounds(BlockSoundGroup.METAL), BlockSetType.IRON));

    public static final Block PYRITE_LANTERN = registerBlock("pyrite_lantern",
            new PillarBlock(FabricBlockSettings.copyOf(ModBlocks.PYRITE_BLOCK).mapColor(MapColor.TERRACOTTA_ORANGE).strength(0.4f).luminance(15).sounds(BlockSoundGroup.LANTERN)));

    public static final Block SOUL_PYRITE_LANTERN = registerBlock("soul_pyrite_lantern",
            new PillarBlock(FabricBlockSettings.copyOf(ModBlocks.PYRITE_LANTERN).mapColor(MapColor.TERRACOTTA_ORANGE).luminance(10).sounds(BlockSoundGroup.LANTERN)));

    public static final Block PYRITE_CHAIN = registerBlock("pyrite_chain",
            new ChainBlock(FabricBlockSettings.of().strength(3.5f,6.5f).mapColor(MapColor.TERRACOTTA_ORANGE).requiresTool().nonOpaque().sounds(BlockSoundGroup.CHAIN)));

    public static final Block PYRITE_NETHER_BRICKS = registerBlock("pyrite_nether_bricks",
            new RotatingBlock(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS).mapColor(MapColor.DARK_RED).sounds(BlockSoundGroup.NETHER_BRICKS)));

    // Fortress Traps

    /*
     * TODO: ADD UNOBTAINABLE BLOCK
    */
    public static final Block FLAMETHROWER = registerBlock("flamethrower",
            new Block(FabricBlockSettings.of().strength(2.5f).mapColor(MapColor.DARK_RED).requiresTool().sounds(BlockSoundGroup.NETHER_BRICKS)));

    /*
     * TODO: ADD UNOBTAINABLE BLOCK
     */
    public static final Block SPIKETRAP = registerBlock("spiketrap",
            new Block(FabricBlockSettings.of().strength(3.5f).mapColor(MapColor.DARK_RED).requiresTool().sounds(BlockSoundGroup.COPPER)));

    // Decorations

    public static final Block SHROOMNIGHT = registerBlock("shroomnight",
            new Block(FabricBlockSettings.copyOf(Blocks.SHROOMLIGHT).mapColor(MapColor.PINK).luminance(9).sounds(BlockSoundGroup.SHROOMLIGHT)));

    public static final Block NETHER_WART_BEARD = registerBlock("nether_wart_beard",
            new BeardBlock(FabricBlockSettings.of().mapColor(MapColor.RED).breakInstantly().noCollision().sounds(BlockSoundGroup.WART_BLOCK)));

    public static final Block WARPED_WART_BEARD = registerBlock("warped_wart_beard",
            new BeardBlock(FabricBlockSettings.of().mapColor(MapColor.BRIGHT_TEAL).breakInstantly().noCollision().sounds(BlockSoundGroup.WART_BLOCK)));

    public static final Block WEEPING_IVY = registerBlockWithoutItem("weeping_ivy",
            new WeepingIvyBlock(FabricBlockSettings.of().mapColor(MapColor.RED).breakInstantly().noCollision().ticksRandomly().sounds(BlockSoundGroup.WEEPING_VINES)));

    public static final Block TWISTING_IVY = registerBlockWithoutItem("twisting_ivy",
            new TwistingIvyBlock(FabricBlockSettings.of().mapColor(MapColor.BRIGHT_TEAL).breakInstantly().noCollision().ticksRandomly().sounds(BlockSoundGroup.WEEPING_VINES)));

    public static final Block RED_SCALE_FUNGUS = registerBlock("red_scale_fungus",
            new ScaleFungusBlock(FabricBlockSettings.of().mapColor(MapColor.RED).ticksRandomly().breakInstantly().noCollision().sounds(BlockSoundGroup.FUNGUS)));

    public static final Block BLUE_SCALE_FUNGUS = registerBlock("blue_scale_fungus",
            new ScaleFungusBlock(FabricBlockSettings.of().mapColor(MapColor.BRIGHT_TEAL).ticksRandomly().breakInstantly().noCollision().sounds(BlockSoundGroup.FUNGUS)));

    /*
     * TODO: ADD UNOBTAINABLE BLOCK
     */
    public static final Block BOOMSHROOM = registerBlock("boomshroom",
            new BoomshroomBlock(FabricBlockSettings.of().mapColor(MapColor.LIGHT_BLUE).breakInstantly().luminance(5).sounds(BlockSoundGroup.FUNGUS)));

    /*
     * TODO: ADD UNOBTAINABLE BLOCK
     */
    public static final Block BOOMSHROOM_BUNDLE = registerBlock("boomshroom_bundle",
            new Block(FabricBlockSettings.of().mapColor(MapColor.LIGHT_BLUE).strength(1.5f).luminance(7).sounds(BlockSoundGroup.FUNGUS)));

    public static final Block CRIMSON_SPROUTS = registerBlock("crimson_sprouts",
            new SproutsBlock(FabricBlockSettings.copyOf(Blocks.NETHER_SPROUTS).mapColor(MapColor.RED).sounds(BlockSoundGroup.NETHER_SPROUTS)));

    public static final Block SOUL_SOIL_LAYER = registerBlock("soul_soil_layer",
            new SoulSoilLayerBlock(FabricBlockSettings.of().mapColor(MapColor.BROWN).strength(0.5f)
            .sounds(BlockSoundGroup.SOUL_SOIL).blockVision(((state, world, pos) -> state.get(LayerBlock.LAYERS) >= 8))));


    // Path Blocks

    public static final Block SOUL_PATH = registerBlock("soul_path",
            new SoulPathBlock(FabricBlockSettings.copyOf(Blocks.DIRT_PATH).mapColor(MapColor.SPRUCE_BROWN).strength(0.5f).sounds(BlockSoundGroup.SOUL_SOIL)));

    public static final Block CRIMSON_NYLIUM_PATH = registerBlock("crimson_nylium_path",
            new NyliumPathBlock(FabricBlockSettings.copyOf(Blocks.DIRT_PATH).mapColor(MapColor.BRIGHT_RED).requiresTool().strength(0.4f).sounds(BlockSoundGroup.NYLIUM)));

    public static final Block WARPED_NYLIUM_PATH = registerBlock("warped_nylium_path",
            new NyliumPathBlock(FabricBlockSettings.copyOf(Blocks.DIRT_PATH).mapColor(MapColor.LIGHT_BLUE).requiresTool().strength(0.4f).sounds(BlockSoundGroup.NYLIUM)));

    // Spotted Wart Blocks

    public static final Block SPOTTED_NETHER_WART_BLOCK = registerBlockWithoutItem("spotted_nether_wart_block",
            new SpottedWartBlock(FabricBlockSettings.copyOf(Blocks.NETHER_WART_BLOCK).mapColor(MapColor.RED).sounds(BlockSoundGroup.WART_BLOCK), Blocks.NETHER_WART_BLOCK, 1));

    public static final Block SPOTTED_WARPED_WART_BLOCK = registerBlockWithoutItem("spotted_warped_wart_block",
            new SpottedWartBlock(FabricBlockSettings.copyOf(Blocks.WARPED_WART_BLOCK).mapColor(MapColor.BRIGHT_TEAL).sounds(BlockSoundGroup.WART_BLOCK), Blocks.WARPED_WART_BLOCK, 2));

    // Decayable Blocks

    public static final Block DECAYABLE_NETHER_WART_BLOCK = registerBlockWithoutItem("decayable_nether_wart_block",
            new DecayableWartBlock(FabricBlockSettings.copyOf(Blocks.NETHER_WART_BLOCK).mapColor(MapColor.RED).ticksRandomly().sounds(BlockSoundGroup.WART_BLOCK),ModParticles.FALLING_NETHER_WART, Blocks.NETHER_WART_BLOCK, 1));

    public static final Block DECAYABLE_WARPED_WART_BLOCK = registerBlockWithoutItem("decayable_warped_wart_block",
            new DecayableWartBlock(FabricBlockSettings.copyOf(Blocks.WARPED_WART_BLOCK).mapColor(MapColor.BRIGHT_TEAL).ticksRandomly().sounds(BlockSoundGroup.WART_BLOCK),ModParticles.FALLING_WARPED_WART, Blocks.WARPED_WART_BLOCK, 2));

    public static final Block DECAYABLE_SHROOMLIGHT = registerBlockWithoutItem("decayable_shroomlight",
            new DecayableShroomBlock(FabricBlockSettings.copyOf(Blocks.SHROOMLIGHT).mapColor(MapColor.ORANGE).ticksRandomly().sounds(BlockSoundGroup.SHROOMLIGHT),ModParticles.FALLING_SHROOMLIGHT, Blocks.SHROOMLIGHT));

    public static final Block DECAYABLE_SHROOMNIGHT = registerBlockWithoutItem("decayable_shroomnight",
            new DecayableShroomBlock(FabricBlockSettings.copyOf(ModBlocks.SHROOMNIGHT).mapColor(MapColor.PINK).ticksRandomly().sounds(BlockSoundGroup.SHROOMLIGHT),ModParticles.FALLING_SHROOMNIGHT, ModBlocks.SHROOMNIGHT));

    // Particle Emitters

    public static final Block CRIMSON_SPORESHROOM = registerBlock("crimson_sporeshroom",
            new SporeshroomBlock(FabricBlockSettings.of().strength(0.5f).pistonBehavior(PistonBehavior.DESTROY).sounds(BlockSoundGroup.FUNGUS),ModParticles.CRIMSON_SMOG, ParticleTypes.CRIMSON_SPORE, ModTags.Biomes.HAS_CRIMSON_SPORES));

    public static final Block WARPED_SPORESHROOM = registerBlock("warped_sporeshroom",
            new SporeshroomBlock(FabricBlockSettings.of().strength(0.5f).pistonBehavior(PistonBehavior.DESTROY).sounds(BlockSoundGroup.FUNGUS),ModParticles.WARPED_SMOG, ParticleTypes.WARPED_SPORE, ModTags.Biomes.HAS_WARPED_SPORES));

    public static final Block SOULED_GEYSER = registerBlock("souled_geyser",
            new GeyserBlock(FabricBlockSettings.copyOf(ModBlocks.SOUL_SLATE).nonOpaque().sounds(ModSoundEvents.SOUL_SLATE),ModParticles.BLACK_SMOKE, ParticleTypes.ASH, ModTags.Biomes.HAS_ASH));

    public static final Block BASALTIC_GEYSER = registerBlock("basaltic_geyser",
            new GeyserBlock(FabricBlockSettings.copyOf(Blocks.BASALT).nonOpaque().sounds(BlockSoundGroup.BASALT),ModParticles.WHITE_SMOKE, ParticleTypes.WHITE_ASH, ModTags.Biomes.HAS_WHITE_ASH));

    // White Ash

    public static final Block ASHY_BASALT = registerBlockWithoutItem("ashy_basalt",
            new AshyBasaltBlock(FabricBlockSettings.copyOf(Blocks.BASALT).sounds(BlockSoundGroup.BASALT)));

    public static final Block WHITE_ASH = registerBlock("white_ash",
            new WhiteAshLayerBlock(FabricBlockSettings.of().strength(0.1f).requiresTool()
            .sounds(ModSoundEvents.WHITE_ASH).blockVision(((state, world, pos) -> state.get(LayerBlock.LAYERS) >= 8))));

    public static final Block WHITE_ASH_BLOCK = registerBlock("white_ash_block",
            new WhiteAshBlock(FabricBlockSettings.of().requiresTool().sounds(ModSoundEvents.WHITE_ASH)));

    public static final Block CHAINWIRE_FENCE = registerBlock("chainwire_fence",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.IRON_BARS).sounds(BlockSoundGroup.CHAIN)));

    // Bones

    public static final Block SKELETON_SKULL_CANDLE = registerBlock("skeleton_skull_candle",
            new SkullCandleBlock(FabricBlockSettings.copyOf(Blocks.SKELETON_SKULL).luminance(13), ParticleTypes.SMALL_FLAME, ParticleTypes.SMOKE));

    public static final Block SOUL_SKELETON_SKULL_CANDLE = registerBlock("soul_skeleton_skull_candle",
            new SkullCandleBlock(FabricBlockSettings.copyOf(Blocks.SKELETON_SKULL).luminance(10), ModParticles.SMALL_SOUL_FLAME, ParticleTypes.SMOKE));

    public static final Block BONE_ROD = registerBlock("bone_rod",
            new BoneRodBlock(FabricBlockSettings.of().noCollision().strength(0.5f).sounds(BlockSoundGroup.BONE)));

    public static final Block BONE_FENCE = registerBlock("bone_fence",
            new BoneFenceBlock(FabricBlockSettings.of().strength(0.5f).sounds(BlockSoundGroup.BONE)));

    public static final Block SKULL_BLOCK = registerBlock("skull_block",
            new RotatingBlock(FabricBlockSettings.copyOf(Blocks.BONE_BLOCK).sounds(BlockSoundGroup.BONE)));

    public static final Block BURNING_SKULL_BLOCK = registerBlock("burning_skull_block",
            new RotatingBlock(FabricBlockSettings.copyOf(ModBlocks.SKULL_BLOCK).luminance(15).sounds(BlockSoundGroup.BONE)));

    public static final Block SOUL_BURNING_SKULL_BLOCK = registerBlock("soul_burning_skull_block",
            new RotatingBlock(FabricBlockSettings.copyOf(ModBlocks.BURNING_SKULL_BLOCK).luminance(10).sounds(BlockSoundGroup.BONE)));

    public static final Block STACKED_BONES = registerBlock("stacked_bones",
            new Block(FabricBlockSettings.copyOf(Blocks.BONE_BLOCK).sounds(BlockSoundGroup.BONE)));

    public static final Block STACKED_BONE_SLAB = registerBlock("stacked_bone_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.STACKED_BONES).sounds(BlockSoundGroup.BONE)));

    public static final Block STACKED_BONE_STAIRS = registerBlock("stacked_bone_stairs",
            new ModStairsBlock(ModBlocks.STACKED_BONES.getDefaultState(),
            FabricBlockSettings.copyOf(ModBlocks.STACKED_BONES).sounds(BlockSoundGroup.BONE)));

    // Wither Bones

    public static final Block WITHER_BONE_BLOCK = registerBlock("wither_bone_block",
            new WitherBoneBlock(FabricBlockSettings.copyOf(Blocks.BONE_BLOCK).strength(4.5f,9.0f).sounds(BlockSoundGroup.BONE)));

    public static final Block WITHER_SKULL_BLOCK = registerBlock("wither_skull_block",
            new RotatingBlock(FabricBlockSettings.copyOf(ModBlocks.WITHER_BONE_BLOCK).sounds(BlockSoundGroup.BONE)));

    public static final Block BURNING_WITHER_SKULL_BLOCK = registerBlock("burning_wither_skull_block",
            new RotatingBlock(FabricBlockSettings.copyOf(ModBlocks.WITHER_SKULL_BLOCK).luminance(15).sounds(BlockSoundGroup.BONE)));

    public static final Block SOUL_BURNING_WITHER_SKULL_BLOCK = registerBlock("soul_burning_wither_skull_block",
            new RotatingBlock(FabricBlockSettings.copyOf(ModBlocks.BURNING_WITHER_SKULL_BLOCK).luminance(10).sounds(BlockSoundGroup.BONE)));

    public static final Block STACKED_WITHER_BONES = registerBlock("stacked_wither_bones",
            new Block(FabricBlockSettings.copyOf(ModBlocks.WITHER_BONE_BLOCK).strength(2.0f,9.0f).sounds(BlockSoundGroup.BONE)));

    public static final Block STACKED_WITHER_BONE_SLAB = registerBlock("stacked_wither_bone_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.STACKED_WITHER_BONES).sounds(BlockSoundGroup.BONE)));

    public static final Block STACKED_WITHER_BONE_STAIRS = registerBlock("stacked_wither_bone_stairs",
            new ModStairsBlock(ModBlocks.STACKED_WITHER_BONES.getDefaultState(),
            FabricBlockSettings.copyOf(ModBlocks.STACKED_WITHER_BONES).sounds(BlockSoundGroup.BONE)));

    // Blackstone

    public static final Block POLISHED_BLACKSTONE_PILLAR = registerBlock("polished_blackstone_pillar",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_BLACKSTONE_BRICKS)));

    public static final Block POLISHED_BLACKSTONE_FENCE = registerBlock("polished_blackstone_fence",
            new FenceBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_BLACKSTONE_BRICKS)));

    public static final Block WEEPING_POLISHED_BLACKSTONE_BRICKS = registerBlock("weeping_polished_blackstone_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.POLISHED_BLACKSTONE_BRICKS)));

    public static final Block TWISTING_POLISHED_BLACKSTONE_BRICKS = registerBlock("twisting_polished_blackstone_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.POLISHED_BLACKSTONE_BRICKS)));

    // Potted Plants

    public static final Block POTTED_SOUL_SWIRLS = registerBlockWithoutItem("potted_soul_swirls",
            new FlowerPotBlock(SOUL_SWIRLS, FabricBlockSettings.of().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block POTTED_ENIGMA_CROWN = registerBlockWithoutItem("potted_enigma_crown",
            new EnigmaCrownPotBlock(ENIGMA_CROWN, FabricBlockSettings.of().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block POTTED_SMOKESTALK = registerBlockWithoutItem("potted_smokestalk",
            new FlowerPotBlock(SMOKESTALK, FabricBlockSettings.of().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block POTTED_RED_SCALE_FUNGUS = registerBlockWithoutItem("potted_red_scale_fungus",
            new FlowerPotBlock(RED_SCALE_FUNGUS, FabricBlockSettings.of().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block POTTED_BLUE_SCALE_FUNGUS = registerBlockWithoutItem("potted_blue_scale_fungus",
            new FlowerPotBlock(BLUE_SCALE_FUNGUS, FabricBlockSettings.of().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block POTTED_CRIMSON_SPORESHROOM = registerBlockWithoutItem("potted_crimson_sporeshroom",
            new FlowerPotBlock(CRIMSON_SPORESHROOM, FabricBlockSettings.of().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block POTTED_WARPED_SPORESHROOM = registerBlockWithoutItem("potted_warped_sporeshroom",
            new FlowerPotBlock(WARPED_SPORESHROOM, FabricBlockSettings.of().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));

    // REGISTRIES:

    @SuppressWarnings("all")
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(NetherExp.MOD_ID, name), block);
    }

    private static Block registerBlockWithoutItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(NetherExp.MOD_ID, name), block);
    }


    @SuppressWarnings("all")
    private static Item registerBlockItem(String name, Block block) {
        Item item = Registry.register(Registries.ITEM, new Identifier(NetherExp.MOD_ID, name),
        new BlockItem(block, new FabricItemSettings()));
        return item;
    }

    public static void registerModBlocks() {
        NetherExp.LOGGER.debug("Registering Blocks for " + NetherExp.MOD_ID);
    }
}
