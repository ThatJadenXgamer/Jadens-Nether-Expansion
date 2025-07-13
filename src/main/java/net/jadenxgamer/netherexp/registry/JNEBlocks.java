package net.jadenxgamer.netherexp.registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.block.*;
import net.jadenxgamer.netherexp.core.misc.JNESoundType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.lodestar.lodestone.systems.block.LodestoneLogBlock;

import java.util.function.Supplier;

import static net.jadenxgamer.netherexp.util.RegistryHelper.*;

@SuppressWarnings("unused")
public class JNEBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, NetherExp.MOD_ID);

    /**
     * Soul Slate
     */
    
    public static final Supplier<Block> SOUL_SLATE = registerBlock("soul_slate", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(4.0f, 12.0f).requiresCorrectToolForDrops().sound(JNESoundType.SOUL_SLATE)));

    public static final Supplier<Block> PALE_SOUL_SLATE = registerBlock("pale_soul_slate", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PINK).strength(3.0f, 1.0f).requiresCorrectToolForDrops().sound(JNESoundType.SOUL_SLATE)));

    public static final Supplier<Block> SOUL_SLATE_SLAB = registerBlock("soul_slate_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(SOUL_SLATE.get())));

    public static final Supplier<Block> SOUL_SLATE_STAIRS = registerBlock("soul_slate_stairs", () ->
            new StairBlock(JNEBlocks.SOUL_SLATE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(SOUL_SLATE.get())));

    public static final Supplier<Block> SOUL_SLATE_WALL = registerBlock("soul_slate_wall", () ->
            new WallBlock(BlockBehaviour.Properties.ofFullCopy(SOUL_SLATE.get())));

    /**
     * Soul Slate Bricks
     */
    
    public static final Supplier<Block> SOUL_SLATE_BRICKS = registerBlock("soul_slate_bricks", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(2.0f).requiresCorrectToolForDrops().sound(JNESoundType.SOUL_SLATE_BRICKS)));

    public static final Supplier<Block> SOUL_SLATE_BRICK_SLAB = registerBlock("soul_slate_brick_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(SOUL_SLATE_BRICKS.get())));

    public static final Supplier<Block> SOUL_SLATE_BRICK_STAIRS = registerBlock("soul_slate_brick_stairs", () ->
            new StairBlock(JNEBlocks.SOUL_SLATE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(SOUL_SLATE_BRICKS.get())));

    public static final Supplier<Block> SOUL_SLATE_BRICK_WALL = registerBlock("soul_slate_brick_wall", () ->
            new WallBlock(BlockBehaviour.Properties.ofFullCopy(SOUL_SLATE_BRICKS.get())));

    public static final Supplier<Block> ETCHED_SOUL_SLATE_BRICKS = registerBlock("etched_soul_slate_bricks", () ->
            new LightableBlock(() -> ParticleTypes.SOUL_FIRE_FLAME, BlockBehaviour.Properties.ofFullCopy(SOUL_SLATE_BRICKS.get()).lightLevel(
                    blockState -> blockState.getValue(LightableBlock.LIT) ? 4 : 0)));

    public static final Supplier<Block> CRACKED_SOUL_SLATE_BRICKS = registerBlock("cracked_soul_slate_bricks", () ->
            new Block(BlockBehaviour.Properties.ofFullCopy(SOUL_SLATE_BRICKS.get())));

    public static final Supplier<Block> SOUL_SLATE_BRICK_PILLAR = registerBlock("soul_slate_brick_pillar", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(SOUL_SLATE_BRICKS.get())));

    public static final Supplier<Block> CHISELED_SOUL_SLATE_BRICKS = registerBlock("chiseled_soul_slate_bricks", () ->
            new LightableBlock(() -> ParticleTypes.SOUL_FIRE_FLAME, BlockBehaviour.Properties.ofFullCopy(SOUL_SLATE_BRICKS.get()).lightLevel(
                    blockState -> blockState.getValue(LightableBlock.LIT) ? 7 : 0)));

    /**
     * Soul Slate Tiles
     */

    public static final Supplier<Block> SOUL_SLATE_TILES = registerBlock("soul_slate_tiles", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PINK).strength(2.0f).requiresCorrectToolForDrops().sound(JNESoundType.SOUL_SLATE_BRICKS)));

    public static final Supplier<Block> SOUL_SLATE_TILE_SLAB = registerBlock("soul_slate_tile_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(SOUL_SLATE_TILES.get())));

    public static final Supplier<Block> SOUL_SLATE_TILE_STAIRS = registerBlock("soul_slate_tile_stairs", () ->
            new StairBlock(JNEBlocks.SOUL_SLATE_TILES.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(SOUL_SLATE_TILES.get())));

    public static final Supplier<Block> SOUL_SLATE_TILE_WALL = registerBlock("soul_slate_tile_wall", () ->
            new WallBlock(BlockBehaviour.Properties.ofFullCopy(SOUL_SLATE_TILES.get())));

    public static final Supplier<Block> ETCHED_SOUL_SLATE_TILES = registerBlock("etched_soul_slate_tiles", () ->
            new LightableBlock(() -> ParticleTypes.SOUL_FIRE_FLAME, BlockBehaviour.Properties.ofFullCopy(SOUL_SLATE_TILES.get()).lightLevel(
                    blockState -> blockState.getValue(LightableBlock.LIT) ? 4 : 0)));

    public static final Supplier<Block> CHISELED_SOUL_SLATE_TILES = registerBlock("chiseled_soul_slate_tiles", () ->
            new LightableBlock(() -> ParticleTypes.SOUL_FIRE_FLAME, BlockBehaviour.Properties.ofFullCopy(SOUL_SLATE_TILES.get()).lightLevel(
                    blockState -> blockState.getValue(LightableBlock.LIT) ? 7 : 0)));

    /**
     * Fossil Ores
     */

    public static final Supplier<Block> FOSSIL_FUEL_ORE = registerBlock("fossil_fuel_ore", () ->
            new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_SOIL).strength(0.6f).sound(JNESoundType.FOSSIL_ORE)));

    public static final Supplier<Block> FOSSIL_ORE = registerBlock("fossil_ore", () ->
            new BuriedConverterBlock(ConstantInt.of(0), FOSSIL_FUEL_ORE, JNEConfigs.FOSSIL_ORE_CONVERSION_ODDS, BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_SOIL).randomTicks().strength(0.6f).sound(JNESoundType.FOSSIL_ORE)));

    /**
     * Soul Sand Valley Additions
     */
    
    public static final Supplier<Block> SOUL_SWIRLS = registerBlock("soul_swirls", () ->
            new SwirlsBlock(JNEParticleTypes.SOUL_SWIRL_POP, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).replaceable().noCollission().instabreak()
                    .lightLevel(state -> state.getValue(SwirlsBlock.ACTIVE) ? 6 : 0).sound(JNESoundType.SOUL_SWIRLS)));

    public static final Supplier<Block> SOUL_CANDLE = registerBlock("soul_candle", () ->
            new SoulCandleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noOcclusion().strength(1.0f).lightLevel(SoulCandleBlock.STATE_TO_LUMINANCE).sound(JNESoundType.SOUL_CANDLE)));

    public static final Supplier<Block> SOUL_GLASS = registerBlock("soul_glass", () ->
            new SoulGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).lightLevel(
                    state -> state.getValue(SoulGlassBlock.LIT) ? 12 : 0).strength(0.3f, 1200.0f).sound(SoundType.GLASS)));

    public static final Supplier<Block> ECTO_SOUL_SAND = registerBlock("ecto_soul_sand", () ->
            new EctoSoulSandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_SAND).lightLevel((state) -> 4).randomTicks()));

    public static final Supplier<Block> SUSPICIOUS_SOUL_SAND = registerBlock("suspicious_soul_sand", () ->
            new SuspiciousSoulSandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_SAND).randomTicks().speedFactor(0.2f).strength(0.25f).pushReaction(PushReaction.DESTROY).sound(JNESoundType.SUSPICIOUS_SOUL_SAND)));

    public static final Supplier<Block> SOUL_MAGMA_BLOCK = registerBlock("soul_magma_block", () ->
            new SoulMagmaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MAGMA_BLOCK).mapColor(MapColor.COLOR_LIGHT_BLUE).sound(JNESoundType.SOUL_MAGMA_BLOCK)));

    /**
     * Path Blocks
     */
    
    public static final Supplier<Block> SOUL_PATH = registerBlock("soul_path", () ->
            new JNEPathBlock(() -> Blocks.SOUL_SOIL, BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_SOIL)));

    public static final Supplier<Block> CRIMSON_NYLIUM_PATH = registerBlock("crimson_nylium_path", () ->
            new JNEPathBlock(() -> Blocks.NETHERRACK, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_NYLIUM)));

    public static final Supplier<Block> WARPED_NYLIUM_PATH = registerBlock("warped_nylium_path", () ->
            new JNEPathBlock(() -> Blocks.NETHERRACK, BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_NYLIUM)));

    /**
     * Smooth Netherrack
     */

    public static final Supplier<Block> SMOOTH_NETHERRACK = registerBlock("smooth_netherrack", () ->
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERRACK).strength(0.4f).requiresCorrectToolForDrops()));

    public static final Supplier<Block> SMOOTH_NETHERRACK_SLAB = registerBlock("smooth_netherrack_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(JNEBlocks.SMOOTH_NETHERRACK.get())));

    public static final Supplier<Block> SMOOTH_NETHERRACK_STAIRS = registerBlock("smooth_netherrack_stairs", () ->
            new StairBlock(JNEBlocks.SMOOTH_NETHERRACK.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(JNEBlocks.SMOOTH_NETHERRACK.get())));

    public static final Supplier<Block> SMOOTH_NETHERRACK_WALL = registerBlock("smooth_netherrack_wall", () ->
            new WallBlock(BlockBehaviour.Properties.ofFullCopy(JNEBlocks.SMOOTH_NETHERRACK.get())));

    /**
     * Netherrack Bricks
     */
    
    public static final Supplier<Block> NETHERRACK_BRICKS = registerBlock("netherrack_bricks", () ->
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERRACK).strength(0.4f).requiresCorrectToolForDrops().sound(JNESoundType.NETHERRACK_BRICKS)));

    public static final Supplier<Block> NETHERRACK_BRICK_SLAB = registerBlock("netherrack_brick_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(JNEBlocks.NETHERRACK_BRICKS.get())));

    public static final Supplier<Block> NETHERRACK_BRICK_STAIRS = registerBlock("netherrack_brick_stairs", () ->
            new StairBlock(JNEBlocks.NETHERRACK_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(JNEBlocks.NETHERRACK_BRICKS.get())));

    public static final Supplier<Block> NETHERRACK_BRICK_WALL = registerBlock("netherrack_brick_wall", () ->
            new WallBlock(BlockBehaviour.Properties.ofFullCopy(JNEBlocks.NETHERRACK_BRICKS.get())));

    public static final Supplier<Block> NETHERRACK_TILES = registerBlock("netherrack_tiles", () ->
            new Block(BlockBehaviour.Properties.ofFullCopy(JNEBlocks.NETHERRACK_BRICKS.get())));

    public static final Supplier<Block> NETHERRACK_PILLAR = registerBlock("netherrack_pillar", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(JNEBlocks.NETHERRACK_BRICKS.get())));

    /**
     * Basalt
     */
    
    public static final Supplier<Block> BASALT_SLAB = registerBlock("basalt_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BASALT)));

    public static final Supplier<Block> BASALT_STAIRS = registerBlock("basalt_stairs", () ->
            new StairBlock(Blocks.BASALT.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.BASALT)));

    public static final Supplier<Block> BASALT_WALL = registerBlock("basalt_wall", () ->
            new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BASALT)));

    /**
     * Polished Basalt
     */
    
    public static final Supplier<Block> POLISHED_BASALT_SLAB = registerBlock("polished_basalt_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_BASALT)));

    public static final Supplier<Block> POLISHED_BASALT_STAIRS = registerBlock("polished_basalt_stairs", () ->
            new StairBlock(Blocks.POLISHED_BASALT.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_BASALT)));

    public static final Supplier<Block> POLISHED_BASALT_WALL = registerBlock("polished_basalt_wall", () ->
            new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_BASALT)));

    /**
     * Polished Basalt Bricks
     */
    
    public static final Supplier<Block> POLISHED_BASALT_BRICKS = registerBlock("polished_basalt_bricks", () ->
            new GildedBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_BASALT).sound(JNESoundType.POLISHED_BASALT_BRICKS)));

    public static final Supplier<Block> POLISHED_BASALT_BRICK_SLAB = registerBlock("polished_basalt_brick_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(JNEBlocks.POLISHED_BASALT_BRICKS.get())));

    public static final Supplier<Block> POLISHED_BASALT_BRICK_STAIRS = registerBlock("polished_basalt_brick_stairs", () ->
            new StairBlock(JNEBlocks.POLISHED_BASALT_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(JNEBlocks.POLISHED_BASALT_BRICKS.get())));

    public static final Supplier<Block> POLISHED_BASALT_BRICK_WALL = registerBlock("polished_basalt_brick_wall", () ->
            new WallBlock(BlockBehaviour.Properties.ofFullCopy(JNEBlocks.POLISHED_BASALT_BRICKS.get())));


    /**
     * Netherite
     */

    public static final Supplier<Block> NETHERITE_PLATED_BLOCK = registerItemPropertiesBlock("netherite_plated_block", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(0.5f, 0.5f).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)), new Item.Properties().fireResistant());

    public static final Supplier<Block> CUT_NETHERITE_BLOCK = registerItemPropertiesBlock("cut_netherite_block", () ->
            new Block(BlockBehaviour.Properties.ofFullCopy(JNEBlocks.NETHERITE_PLATED_BLOCK.get())), new Item.Properties().fireResistant());

    public static final Supplier<Block> CUT_NETHERITE_SLAB = registerItemPropertiesBlock("cut_netherite_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(JNEBlocks.CUT_NETHERITE_BLOCK.get())), new Item.Properties().fireResistant());

    public static final Supplier<Block> CUT_NETHERITE_STAIRS = registerItemPropertiesBlock("cut_netherite_stairs", () ->
            new StairBlock(JNEBlocks.CUT_NETHERITE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(JNEBlocks.CUT_NETHERITE_BLOCK.get())), new Item.Properties().fireResistant());

    public static final Supplier<Block> CUT_NETHERITE_PILLAR = registerItemPropertiesBlock("cut_netherite_pillar", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(JNEBlocks.CUT_NETHERITE_BLOCK.get())), new Item.Properties().fireResistant());

    public static final Supplier<Block> NETHERITE_GRATE = registerItemPropertiesBlock("netherite_grate", () ->
            new WaterloggedTransparentBlock(BlockBehaviour.Properties.ofFullCopy(JNEBlocks.CUT_NETHERITE_BLOCK.get()).noOcclusion()), new Item.Properties().fireResistant());

    /**
     * Rusty Netherite
     */

    public static final Supplier<Block> RUSTY_NETHERITE_PLATED_BLOCK = registerItemPropertiesBlock("rusty_netherite_plated_block", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(0.5f, 0.5f).requiresCorrectToolForDrops().sound(SoundType.ANCIENT_DEBRIS)), new Item.Properties().fireResistant());

    public static final Supplier<Block> RUSTY_CUT_NETHERITE_BLOCK = registerItemPropertiesBlock("rusty_cut_netherite_block", () ->
            new Block(BlockBehaviour.Properties.ofFullCopy(JNEBlocks.RUSTY_NETHERITE_PLATED_BLOCK.get())), new Item.Properties().fireResistant());

    public static final Supplier<Block> RUSTY_CUT_NETHERITE_SLAB = registerItemPropertiesBlock("rusty_cut_netherite_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get())), new Item.Properties().fireResistant());

    public static final Supplier<Block> RUSTY_CUT_NETHERITE_STAIRS = registerItemPropertiesBlock("rusty_cut_netherite_stairs", () ->
            new StairBlock(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get())), new Item.Properties().fireResistant());

    public static final Supplier<Block> RUSTY_CUT_NETHERITE_PILLAR = registerItemPropertiesBlock("rusty_cut_netherite_pillar", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get())), new Item.Properties().fireResistant());

    public static final Supplier<Block> RUSTY_NETHERITE_GRATE = registerItemPropertiesBlock("rusty_netherite_grate", () ->
            new WaterloggedTransparentBlock(BlockBehaviour.Properties.ofFullCopy(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get()).noOcclusion()), new Item.Properties().fireResistant());

    /**
     * Claret Woodset
     */

    public static final Supplier<Block> CEREBRAGE_CLARET_STEM = registerBlock("cerebrage_claret_stem", () ->
            new LodestoneLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_STEM).mapColor(MapColor.CRIMSON_HYPHAE).sound(JNESoundType.CEREBRAGE_STEM), JNEBlocks.STRIPPED_CLARET_STEM));

    public static final Supplier<Block> CEREBRAGE_CLARET_HYPHAE = registerBlock("cerebrage_claret_hyphae", () ->
            new LodestoneLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_HYPHAE).mapColor(MapColor.CRIMSON_HYPHAE).sound(JNESoundType.CEREBRAGE_STEM), JNEBlocks.STRIPPED_CLARET_HYPHAE));

    public static final Supplier<Block> STRIPPED_CLARET_STEM = registerBlock("stripped_claret_stem", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_WARPED_STEM).mapColor(MapColor.NETHER).sound(JNESoundType.CEREBRAGE_STEM)));

    public static final Supplier<Block> STRIPPED_CLARET_HYPHAE = registerBlock("stripped_claret_hyphae", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_WARPED_HYPHAE).mapColor(MapColor.NETHER).sound(JNESoundType.CEREBRAGE_STEM)));

    public static final Supplier<Block> CLARET_PLANKS = registerBlock("claret_planks", () ->
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_PLANKS).mapColor(MapColor.NETHER)));

    public static final Supplier<Block> CLARET_SLAB = registerBlock("claret_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_SLAB).mapColor(MapColor.NETHER)));

    public static final Supplier<Block> CLARET_STAIRS = registerBlock("claret_stairs", () ->
            new StairBlock(JNEBlocks.CLARET_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_STAIRS).mapColor(MapColor.NETHER)));

    public static final Supplier<Block> CLARET_FENCE = registerBlock("claret_fence", () ->
            new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_FENCE).mapColor(MapColor.NETHER)));

    public static final Supplier<Block> CLARET_FENCE_GATE = registerBlock("claret_fence_gate", () ->
            new FenceGateBlock(JNEWoodType.CLARET, BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_FENCE_GATE).mapColor(MapColor.NETHER)));

    public static final Supplier<Block> CLARET_DOOR = registerBlock("claret_door", () ->
            new DoorBlock(JNEBlockSetType.CLARET, BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_DOOR).mapColor(MapColor.NETHER)));

    public static final Supplier<Block> CLARET_TRAPDOOR = registerBlock("claret_trapdoor", () ->
            new TrapDoorBlock(JNEBlockSetType.CLARET, BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_TRAPDOOR).mapColor(MapColor.NETHER)));

    public static final Supplier<Block> CLARET_BUTTON = registerBlock("claret_button", () ->
            new ButtonBlock(JNEBlockSetType.CLARET, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_BUTTON).mapColor(MapColor.NETHER)));

    public static final Supplier<Block> CLARET_PRESSURE_PLATE = registerBlock("claret_pressure_plate", () ->
            new PressurePlateBlock(JNEBlockSetType.CLARET, BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_PRESSURE_PLATE).mapColor(MapColor.NETHER)));

    public static final Supplier<Block> CLARET_SIGN = registerBlockWithoutItem("claret_sign", () ->
            new StandingSignBlock(JNEWoodType.CLARET, BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_SIGN).mapColor(MapColor.NETHER)));

    public static final Supplier<Block> CLARET_WALL_SIGN = registerBlockWithoutItem("claret_wall_sign", () ->
            new WallSignBlock(JNEWoodType.CLARET, BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_WALL_SIGN).mapColor(MapColor.NETHER).dropsLike(JNEBlocks.CLARET_SIGN.get())));

    public static final Supplier<Block> CLARET_HANGING_SIGN = registerBlockWithoutItem("claret_hanging_sign", () ->
            new CeilingHangingSignBlock(JNEWoodType.CLARET, BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_HANGING_SIGN).mapColor(MapColor.NETHER)));

    public static final Supplier<Block> CLARET_WALL_HANGING_SIGN = registerBlockWithoutItem("claret_wall_hanging_sign", () ->
            new WallHangingSignBlock(JNEWoodType.CLARET, BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_WALL_HANGING_SIGN).mapColor(MapColor.NETHER).dropsLike(JNEBlocks.CLARET_HANGING_SIGN.get())));

    public static void init(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
