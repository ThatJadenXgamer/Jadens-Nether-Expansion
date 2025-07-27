package net.jadenxgamer.netherexp.registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.block.*;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.core.misc.JNESoundType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.lodestar.lodestone.systems.block.LodestoneLogBlock;

import java.util.function.Supplier;

import static net.jadenxgamer.netherexp.util.RegistryHelper.*;

@SuppressWarnings({"unused", "deprecation"})
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
            new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(SOUL_SLATE.get())));

    public static final Supplier<Block> SOUL_SLATE_STAIRS = registerBlock("soul_slate_stairs", () ->
            new StairBlock(SOUL_SLATE.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(SOUL_SLATE.get())));

    public static final Supplier<Block> SOUL_SLATE_WALL = registerBlock("soul_slate_wall", () ->
            new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(SOUL_SLATE.get())));

    /**
     * Soul Slate Bricks
     */

    public static final Supplier<Block> SOUL_SLATE_BRICKS = registerBlock("soul_slate_bricks", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(2.0f).requiresCorrectToolForDrops().sound(JNESoundType.SOUL_SLATE_BRICKS)));

    public static final Supplier<Block> SOUL_SLATE_BRICK_SLAB = registerBlock("soul_slate_brick_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(SOUL_SLATE_BRICKS.get())));

    public static final Supplier<Block> SOUL_SLATE_BRICK_STAIRS = registerBlock("soul_slate_brick_stairs", () ->
            new StairBlock(SOUL_SLATE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(SOUL_SLATE_BRICKS.get())));

    public static final Supplier<Block> SOUL_SLATE_BRICK_WALL = registerBlock("soul_slate_brick_wall", () ->
            new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(SOUL_SLATE_BRICKS.get())));

    public static final Supplier<Block> ETCHED_SOUL_SLATE_BRICKS = registerBlock("etched_soul_slate_bricks", () ->
            new LightableBlock(() -> ParticleTypes.SOUL_FIRE_FLAME, BlockBehaviour.Properties.ofLegacyCopy(SOUL_SLATE_BRICKS.get()).lightLevel(
                    blockState -> blockState.getValue(LightableBlock.LIT) ? 4 : 0)));

    public static final Supplier<Block> CRACKED_SOUL_SLATE_BRICKS = registerBlock("cracked_soul_slate_bricks", () ->
            new Block(BlockBehaviour.Properties.ofLegacyCopy(SOUL_SLATE_BRICKS.get())));

    public static final Supplier<Block> SOUL_SLATE_BRICK_PILLAR = registerBlock("soul_slate_brick_pillar", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.ofLegacyCopy(SOUL_SLATE_BRICKS.get())));

    public static final Supplier<Block> CHISELED_SOUL_SLATE_BRICKS = registerBlock("chiseled_soul_slate_bricks", () ->
            new LightableBlock(() -> ParticleTypes.SOUL_FIRE_FLAME, BlockBehaviour.Properties.ofLegacyCopy(SOUL_SLATE_BRICKS.get()).lightLevel(
                    blockState -> blockState.getValue(LightableBlock.LIT) ? 7 : 0)));

    /**
     * Soul Slate Tiles
     */

    public static final Supplier<Block> SOUL_SLATE_TILES = registerBlock("soul_slate_tiles", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PINK).strength(2.0f).requiresCorrectToolForDrops().sound(JNESoundType.SOUL_SLATE_BRICKS)));

    public static final Supplier<Block> SOUL_SLATE_TILE_SLAB = registerBlock("soul_slate_tile_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(SOUL_SLATE_TILES.get())));

    public static final Supplier<Block> SOUL_SLATE_TILE_STAIRS = registerBlock("soul_slate_tile_stairs", () ->
            new StairBlock(SOUL_SLATE_TILES.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(SOUL_SLATE_TILES.get())));

    public static final Supplier<Block> SOUL_SLATE_TILE_WALL = registerBlock("soul_slate_tile_wall", () ->
            new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(SOUL_SLATE_TILES.get())));

    public static final Supplier<Block> ETCHED_SOUL_SLATE_TILES = registerBlock("etched_soul_slate_tiles", () ->
            new LightableBlock(() -> ParticleTypes.SOUL_FIRE_FLAME, BlockBehaviour.Properties.ofLegacyCopy(SOUL_SLATE_TILES.get()).lightLevel(
                    blockState -> blockState.getValue(LightableBlock.LIT) ? 4 : 0)));

    public static final Supplier<Block> CHISELED_SOUL_SLATE_TILES = registerBlock("chiseled_soul_slate_tiles", () ->
            new LightableBlock(() -> ParticleTypes.SOUL_FIRE_FLAME, BlockBehaviour.Properties.ofLegacyCopy(SOUL_SLATE_TILES.get()).lightLevel(
                    blockState -> blockState.getValue(LightableBlock.LIT) ? 7 : 0)));

    /**
     * Fossil Ores
     */

    public static final Supplier<Block> FOSSIL_FUEL_ORE = registerBlock("fossil_fuel_ore", () ->
            new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.ofLegacyCopy(Blocks.SOUL_SOIL).strength(0.6f).sound(JNESoundType.FOSSIL_ORE)));

    public static final Supplier<Block> FOSSIL_ORE = registerBlock("fossil_ore", () ->
            new BuriedConverterBlock(ConstantInt.of(0), FOSSIL_FUEL_ORE, JNEConfigs.FOSSIL_ORE_CONVERSION_CHANCE, BlockBehaviour.Properties.ofLegacyCopy(Blocks.SOUL_SOIL).randomTicks().strength(0.6f).sound(JNESoundType.FOSSIL_ORE)));

    /**
     * Soul Sand Valley Additions
     */

    public static final Supplier<Block> SOUL_SWIRLS = registerBlock("soul_swirls", () ->
            new SwirlsBlock(JNEParticleTypes.SOUL_SWIRL_POP, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).replaceable().noCollission().instabreak()
                    .lightLevel(state -> state.getValue(SwirlsBlock.ACTIVE) ? 6 : 0).sound(JNESoundType.SOUL_SWIRLS)));

    public static final Supplier<Block> SOUL_CANDLE = registerBlock("soul_candle", () ->
            new SoulCandleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noOcclusion().strength(1.0f).lightLevel(SoulCandleBlock.STATE_TO_LUMINANCE).sound(JNESoundType.SOUL_CANDLE)));

    public static final Supplier<Block> SOUL_GLASS = registerBlock("soul_glass", () ->
            new SoulGlassBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).lightLevel(
                    state -> state.getValue(SoulGlassBlock.LIT) ? 12 : 0).strength(0.3f, 1200.0f).sound(SoundType.GLASS)));

    public static final Supplier<Block> DISCERNMENT_GLASS = registerBlock("discernment_glass", () ->
            new DiscernmentGlassBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).strength(0.3f, 1200.0f).sound(SoundType.GLASS)));

    public static final Supplier<Block> ECTO_SOUL_SAND = registerBlock("ecto_soul_sand", () ->
            new EctoSoulSandBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.SOUL_SAND).lightLevel((state) -> 4).randomTicks()));

    public static final Supplier<Block> SUSPICIOUS_SOUL_SAND = registerBlock("suspicious_soul_sand", () ->
            new SuspiciousSoulSandBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.SOUL_SAND).randomTicks().speedFactor(0.2f).strength(0.25f).pushReaction(PushReaction.DESTROY).sound(JNESoundType.SUSPICIOUS_SOUL_SAND)));

    public static final Supplier<Block> SOUL_MAGMA_BLOCK = registerBlock("soul_magma_block", () ->
            new SoulMagmaBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .isValidSpawn((state, level, pos, entity) -> entity.fireImmune())
                    .sound(JNESoundType.SOUL_MAGMA_BLOCK)));

    /**
     * Black Ice
     */

    public static final Supplier<Block> BLACK_ICE = registerBlock("black_ice", () ->
            new BlackIceBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.PACKED_ICE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(0.3f).lightLevel((state) -> 2).sound(JNESoundType.BLACK_ICE)));

    public static final Supplier<Block> BLACK_ICICLE = registerBlock("black_icicle", () ->
            new BlackIcicleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().forceSolidOn().noOcclusion().randomTicks().strength(0.1F, 3.0F).dynamicShape()
                    .offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY).sound(JNESoundType.BLACK_ICE)));

    public static final Supplier<Block> THIN_BLACK_ICE = registerBlock("thin_black_ice", () ->
            new ThinBlackIceBlock(BlockBehaviour.Properties.ofLegacyCopy(BLACK_ICE.get()).strength(0.05f).randomTicks().noOcclusion()));

    public static final Supplier<Block> SOUL_PERMAFROST = registerBlock("soul_permafrost", () ->
            new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.SOUL_SOIL).speedFactor(0.9F).friction(0.98F)));

    /**
     * Path Blocks
     */

    public static final Supplier<Block> SOUL_PATH = registerBlock("soul_path", () ->
            new JNEPathBlock(() -> Blocks.SOUL_SOIL, BlockBehaviour.Properties.ofLegacyCopy(Blocks.SOUL_SOIL)));

    public static final Supplier<Block> CRIMSON_NYLIUM_PATH = registerBlock("crimson_nylium_path", () ->
            new JNEPathBlock(() -> Blocks.NETHERRACK, BlockBehaviour.Properties.ofLegacyCopy(Blocks.CRIMSON_NYLIUM)));

    public static final Supplier<Block> WARPED_NYLIUM_PATH = registerBlock("warped_nylium_path", () ->
            new JNEPathBlock(() -> Blocks.NETHERRACK, BlockBehaviour.Properties.ofLegacyCopy(Blocks.WARPED_NYLIUM)));

    /**
     * Smooth Netherrack
     */

    public static final Supplier<Block> SMOOTH_NETHERRACK = registerBlock("smooth_netherrack", () ->
            new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.NETHERRACK).strength(0.4f).requiresCorrectToolForDrops()));

    public static final Supplier<Block> SMOOTH_NETHERRACK_SLAB = registerBlock("smooth_netherrack_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(JNEBlocks.SMOOTH_NETHERRACK.get())));

    public static final Supplier<Block> SMOOTH_NETHERRACK_STAIRS = registerBlock("smooth_netherrack_stairs", () ->
            new StairBlock(SMOOTH_NETHERRACK.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(JNEBlocks.SMOOTH_NETHERRACK.get())));

    public static final Supplier<Block> SMOOTH_NETHERRACK_WALL = registerBlock("smooth_netherrack_wall", () ->
            new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(JNEBlocks.SMOOTH_NETHERRACK.get())));

    /**
     * Netherrack Bricks
     */

    public static final Supplier<Block> NETHERRACK_BRICKS = registerBlock("netherrack_bricks", () ->
            new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.NETHERRACK).strength(0.4f).requiresCorrectToolForDrops().sound(JNESoundType.NETHERRACK_BRICKS)));

    public static final Supplier<Block> NETHERRACK_BRICK_SLAB = registerBlock("netherrack_brick_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(JNEBlocks.NETHERRACK_BRICKS.get())));

    public static final Supplier<Block> NETHERRACK_BRICK_STAIRS = registerBlock("netherrack_brick_stairs", () ->
            new StairBlock(NETHERRACK_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(JNEBlocks.NETHERRACK_BRICKS.get())));

    public static final Supplier<Block> NETHERRACK_BRICK_WALL = registerBlock("netherrack_brick_wall", () ->
            new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(JNEBlocks.NETHERRACK_BRICKS.get())));

    public static final Supplier<Block> NETHERRACK_TILES = registerBlock("netherrack_tiles", () ->
            new Block(BlockBehaviour.Properties.ofLegacyCopy(JNEBlocks.NETHERRACK_BRICKS.get())));

    public static final Supplier<Block> NETHERRACK_PILLAR = registerBlock("netherrack_pillar", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.ofLegacyCopy(JNEBlocks.NETHERRACK_BRICKS.get())));

    /**
     * Basalt
     */

    public static final Supplier<Block> BASALT_SLAB = registerBlock("basalt_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.BASALT)));

    public static final Supplier<Block> BASALT_STAIRS = registerBlock("basalt_stairs", () ->
            new StairBlock(Blocks.BASALT.defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(Blocks.BASALT)));

    public static final Supplier<Block> BASALT_WALL = registerBlock("basalt_wall", () ->
            new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.BASALT)));

    /**
     * Polished Basalt
     */

    public static final Supplier<Block> POLISHED_BASALT_SLAB = registerBlock("polished_basalt_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.POLISHED_BASALT)));

    public static final Supplier<Block> POLISHED_BASALT_STAIRS = registerBlock("polished_basalt_stairs", () ->
            new StairBlock(Blocks.POLISHED_BASALT.defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(Blocks.POLISHED_BASALT)));

    public static final Supplier<Block> POLISHED_BASALT_WALL = registerBlock("polished_basalt_wall", () ->
            new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.POLISHED_BASALT)));

    /**
     * Polished Basalt Bricks
     */

    public static final Supplier<Block> POLISHED_BASALT_BRICKS = registerBlock("polished_basalt_bricks", () ->
            new GildedBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.POLISHED_BASALT).sound(JNESoundType.POLISHED_BASALT_BRICKS)));

    public static final Supplier<Block> POLISHED_BASALT_BRICK_SLAB = registerBlock("polished_basalt_brick_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(JNEBlocks.POLISHED_BASALT_BRICKS.get())));

    public static final Supplier<Block> POLISHED_BASALT_BRICK_STAIRS = registerBlock("polished_basalt_brick_stairs", () ->
            new StairBlock(POLISHED_BASALT_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(JNEBlocks.POLISHED_BASALT_BRICKS.get())));

    public static final Supplier<Block> POLISHED_BASALT_BRICK_WALL = registerBlock("polished_basalt_brick_wall", () ->
            new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(JNEBlocks.POLISHED_BASALT_BRICKS.get())));


    /**
     * Netherite
     */

    public static final Supplier<Block> NETHERITE_PLATED_BLOCK = registerItemPropertiesBlock("netherite_plated_block", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(0.5f, 0.5f).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)), new Item.Properties().fireResistant());

    public static final Supplier<Block> CUT_NETHERITE_BLOCK = registerItemPropertiesBlock("cut_netherite_block", () ->
            new Block(BlockBehaviour.Properties.ofLegacyCopy(JNEBlocks.NETHERITE_PLATED_BLOCK.get())), new Item.Properties().fireResistant());

    public static final Supplier<Block> CUT_NETHERITE_SLAB = registerItemPropertiesBlock("cut_netherite_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(JNEBlocks.CUT_NETHERITE_BLOCK.get())), new Item.Properties().fireResistant());

    public static final Supplier<Block> CUT_NETHERITE_STAIRS = registerItemPropertiesBlock("cut_netherite_stairs", () ->
            new StairBlock(CUT_NETHERITE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(JNEBlocks.CUT_NETHERITE_BLOCK.get())), new Item.Properties().fireResistant());

    public static final Supplier<Block> CUT_NETHERITE_PILLAR = registerItemPropertiesBlock("cut_netherite_pillar", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.ofLegacyCopy(JNEBlocks.CUT_NETHERITE_BLOCK.get())), new Item.Properties().fireResistant());

    public static final Supplier<Block> NETHERITE_GRATE = registerItemPropertiesBlock("netherite_grate", () ->
            new WaterloggedTransparentBlock(BlockBehaviour.Properties.ofLegacyCopy(JNEBlocks.CUT_NETHERITE_BLOCK.get()).noOcclusion()), new Item.Properties().fireResistant());

    /**
     * Rusty Netherite
     */

    public static final Supplier<Block> RUSTY_NETHERITE_PLATED_BLOCK = registerItemPropertiesBlock("rusty_netherite_plated_block", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(0.5f, 0.5f).requiresCorrectToolForDrops().sound(SoundType.ANCIENT_DEBRIS)), new Item.Properties().fireResistant());

    public static final Supplier<Block> RUSTY_CUT_NETHERITE_BLOCK = registerItemPropertiesBlock("rusty_cut_netherite_block", () ->
            new Block(BlockBehaviour.Properties.ofLegacyCopy(JNEBlocks.RUSTY_NETHERITE_PLATED_BLOCK.get())), new Item.Properties().fireResistant());

    public static final Supplier<Block> RUSTY_CUT_NETHERITE_SLAB = registerItemPropertiesBlock("rusty_cut_netherite_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get())), new Item.Properties().fireResistant());

    public static final Supplier<Block> RUSTY_CUT_NETHERITE_STAIRS = registerItemPropertiesBlock("rusty_cut_netherite_stairs", () ->
            new StairBlock(RUSTY_CUT_NETHERITE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get())), new Item.Properties().fireResistant());

    public static final Supplier<Block> RUSTY_CUT_NETHERITE_PILLAR = registerItemPropertiesBlock("rusty_cut_netherite_pillar", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.ofLegacyCopy(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get())), new Item.Properties().fireResistant());

    public static final Supplier<Block> RUSTY_NETHERITE_GRATE = registerItemPropertiesBlock("rusty_netherite_grate", () ->
            new WaterloggedTransparentBlock(BlockBehaviour.Properties.ofLegacyCopy(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get()).noOcclusion()), new Item.Properties().fireResistant());

    /**
     * Claret WoodSet
     */

    public static final Supplier<Block> CEREBRAGE_CLARET_STEM = registerBlock("cerebrage_claret_stem", () ->
            new LodestoneLogBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.WARPED_STEM).mapColor(MapColor.CRIMSON_HYPHAE).sound(JNESoundType.CEREBRAGE_STEM), JNEBlocks.STRIPPED_CLARET_STEM));

    public static final Supplier<Block> CEREBRAGE_CLARET_HYPHAE = registerBlock("cerebrage_claret_hyphae", () ->
            new LodestoneLogBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.WARPED_HYPHAE).mapColor(MapColor.CRIMSON_HYPHAE).sound(JNESoundType.CEREBRAGE_STEM), JNEBlocks.STRIPPED_CLARET_HYPHAE));

    public static final Supplier<Block> STRIPPED_CLARET_STEM = registerBlock("stripped_claret_stem", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.STRIPPED_WARPED_STEM).mapColor(MapColor.NETHER).sound(JNESoundType.CEREBRAGE_STEM)));

    public static final Supplier<Block> STRIPPED_CLARET_HYPHAE = registerBlock("stripped_claret_hyphae", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.STRIPPED_WARPED_HYPHAE).mapColor(MapColor.NETHER).sound(JNESoundType.CEREBRAGE_STEM)));

    public static final Supplier<Block> CLARET_PLANKS = registerBlock("claret_planks", () ->
            new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.WARPED_PLANKS).mapColor(MapColor.NETHER)));

    public static final Supplier<Block> CLARET_SLAB = registerBlock("claret_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.WARPED_SLAB).mapColor(MapColor.NETHER)));

    public static final Supplier<Block> CLARET_STAIRS = registerBlock("claret_stairs", () ->
            new StairBlock(JNEBlocks.CLARET_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(Blocks.WARPED_STAIRS).mapColor(MapColor.NETHER)));

    public static final Supplier<Block> CLARET_FENCE = registerBlock("claret_fence", () ->
            new FenceBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.WARPED_FENCE).mapColor(MapColor.NETHER)));

    public static final Supplier<Block> CLARET_FENCE_GATE = registerBlock("claret_fence_gate", () ->
            new FenceGateBlock(JNEWoodType.CLARET, BlockBehaviour.Properties.ofLegacyCopy(Blocks.WARPED_FENCE_GATE).mapColor(MapColor.NETHER)));

    public static final Supplier<Block> CLARET_DOOR = registerBlock("claret_door", () ->
            new DoorBlock(JNEBlockSetType.CLARET, BlockBehaviour.Properties.ofLegacyCopy(Blocks.WARPED_DOOR).mapColor(MapColor.NETHER)));

    public static final Supplier<Block> CLARET_TRAPDOOR = registerBlock("claret_trapdoor", () ->
            new TrapDoorBlock(JNEBlockSetType.CLARET, BlockBehaviour.Properties.ofLegacyCopy(Blocks.WARPED_TRAPDOOR).mapColor(MapColor.NETHER)));

    public static final Supplier<Block> CLARET_BUTTON = registerBlock("claret_button", () ->
            new ButtonBlock(JNEBlockSetType.CLARET, 30, BlockBehaviour.Properties.ofLegacyCopy(Blocks.WARPED_BUTTON).mapColor(MapColor.NETHER)));

    public static final Supplier<Block> CLARET_PRESSURE_PLATE = registerBlock("claret_pressure_plate", () ->
            new PressurePlateBlock(JNEBlockSetType.CLARET, BlockBehaviour.Properties.ofLegacyCopy(Blocks.WARPED_PRESSURE_PLATE).mapColor(MapColor.NETHER)));

    public static final Supplier<Block> CLARET_SIGN = registerBlockWithoutItem("claret_sign", () ->
            new StandingSignBlock(JNEWoodType.CLARET, BlockBehaviour.Properties.ofLegacyCopy(Blocks.WARPED_SIGN).mapColor(MapColor.NETHER)));

    public static final Supplier<Block> CLARET_WALL_SIGN = registerBlockWithoutItem("claret_wall_sign", () ->
            new WallSignBlock(JNEWoodType.CLARET, BlockBehaviour.Properties.ofLegacyCopy(Blocks.WARPED_WALL_SIGN).mapColor(MapColor.NETHER).dropsLike(JNEBlocks.CLARET_SIGN.get())));

    public static final Supplier<Block> CLARET_HANGING_SIGN = registerBlockWithoutItem("claret_hanging_sign", () ->
            new CeilingHangingSignBlock(JNEWoodType.CLARET, BlockBehaviour.Properties.ofLegacyCopy(Blocks.WARPED_HANGING_SIGN).mapColor(MapColor.NETHER)));

    public static final Supplier<Block> CLARET_WALL_HANGING_SIGN = registerBlockWithoutItem("claret_wall_hanging_sign", () ->
            new WallHangingSignBlock(JNEWoodType.CLARET, BlockBehaviour.Properties.ofLegacyCopy(Blocks.WARPED_WALL_HANGING_SIGN).mapColor(MapColor.NETHER).dropsLike(JNEBlocks.CLARET_HANGING_SIGN.get())));

    /**
     * Storage Blocks
     */

    public static final Supplier<Block> MAGMA_CREAM_BLOCK = registerBlock("magma_cream_block", () ->
            new MagmaCreamBlock(BlockBehaviour.Properties.of().instabreak().noOcclusion().lightLevel((state) -> 10).sound(SoundType.HONEY_BLOCK)));

    /**
     * Quartz Blocks
     */

    public static final Supplier<Block> QUARTZ_CRYSTAL = registerBlock("quartz_crystal", () ->
            new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ).noOcclusion().strength(1.5f).sound(JNESoundType.QUARTZ_BLOCK)));

    public static final Supplier<Block> QUARTZ_CRYSTAL_BLOCK = registerBlock("quartz_crystal_block", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ).strength(2.5f).requiresCorrectToolForDrops().sound(JNESoundType.QUARTZ_BLOCK)));

    public static final Supplier<Block> CRACKED_QUARTZ_BRICKS = registerBlock("cracked_quartz_bricks", () ->
            new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.QUARTZ_BLOCK)));

    public static final Supplier<Block> CHISELED_QUARTZ_PILLAR = registerBlock("chiseled_quartz_pillar", () ->
            new SixDirectionalBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.QUARTZ_PILLAR)));

    /**
     * Nether Bricks
     */

    public static final Supplier<Block> NETHER_BRICK_PILLAR = registerBlock("nether_brick_pillar", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.NETHER_BRICKS)));

    public static final Supplier<Block> RED_MIXED_NETHER_BRICKS = registerBlock("red_mixed_nether_bricks", () ->
            new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.NETHER_BRICKS)));

    /**
     * Blue Nether Bricks
     */

    public static final Supplier<Block> BLUE_MIXED_NETHER_BRICKS = registerBlock("blue_mixed_nether_bricks", () ->
            new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.NETHER_BRICKS)));

    public static final Supplier<Block> BLUE_NETHER_BRICKS = registerBlock("blue_nether_bricks", () ->
            new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.RED_NETHER_BRICKS).mapColor(MapColor.WARPED_WART_BLOCK)));

    public static final Supplier<Block> BLUE_NETHER_BRICK_SLAB = registerBlock("blue_nether_brick_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(JNEBlocks.BLUE_NETHER_BRICKS.get())));

    public static final Supplier<Block> BLUE_NETHER_BRICK_STAIRS = registerBlock("blue_nether_brick_stairs", () ->
            new StairBlock(BLUE_NETHER_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(JNEBlocks.BLUE_NETHER_BRICKS.get())));

    public static final Supplier<Block> BLUE_NETHER_BRICK_WALL = registerBlock("blue_nether_brick_wall", () ->
            new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(JNEBlocks.BLUE_NETHER_BRICKS.get())));

    /**
     * Farming & Food
     */

    public static final Supplier<Block> NETHER_PIZZA = registerBlock("nether_pizza", () ->
            new NetherPizzaBlock(BlockBehaviour.Properties.of().strength(0.5f).noLootTable().noOcclusion().sound(SoundType.WOOL)));

    public static final Supplier<Block> WARPED_WART = registerBlock("warped_wart", () ->
            new WarpedWartBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WARPED_WART_BLOCK).instabreak().noCollission().noOcclusion().randomTicks().pushReaction(PushReaction.DESTROY).sound(SoundType.NETHER_WART)));

    public static final Supplier<Block> WRAITHING_LESION = registerBlock("wraithing_lesion", () ->
            new LesionBlock(JNEItems.WRAITHING_FLESH, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(1.5f, 1.0f).pushReaction(PushReaction.DESTROY).randomTicks().sound(JNESoundType.LESION_BLOCK)));

    public static final Supplier<Block> SOUL_TORCHFLOWER = registerBlock("soul_torchflower", () ->
            new NetherFlowerBlock(MobEffects.DIG_SPEED, 12, BlockBehaviour.Properties.ofLegacyCopy(Blocks.TORCHFLOWER)));

    public static final Supplier<Block> SOUL_TORCHFLOWER_CROP = registerBlockWithoutItem("soul_torchflower_crop", () ->
            new SoulTorchflowerCropBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.TORCHFLOWER_CROP)));

    public static final Supplier<Block> SORROWEED = registerBlock("sorroweed", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5f).sound(SoundType.MOSS)));

    public static final Supplier<Block> SORROWSQUASH = registerBlock("sorrowsquash", () ->
            new SorrowsquashBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.PUMPKIN).mapColor(MapColor.COLOR_LIGHT_GRAY).strength(1.0f).sound(SoundType.NETHER_WOOD)));

    public static final Supplier<Block> CARVED_SORROWSQUASH = registerBlock("carved_sorrowsquash", () ->
            new CarvedSorrowsquashBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.CARVED_PUMPKIN).mapColor(MapColor.COLOR_LIGHT_GRAY).strength(1.0f).sound(SoundType.NETHER_WOOD)));

    public static final Supplier<Block> GHOUL_O_LANTERN = registerBlock("ghoul_o_lantern", () ->
            new CarvedSorrowsquashBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.JACK_O_LANTERN).mapColor(MapColor.COLOR_ORANGE).strength(1.0f).lightLevel((state) -> 10).sound(SoundType.NETHER_WOOD)));

    public static final Supplier<Block> SORROWSQUASH_STEM = registerBlockWithoutItem("sorrowsquash_stem", () ->
            new VineStemHeadBlock(JNEBlocks.SORROWSQUASH, () -> Items.PUMPKIN_SEEDS, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().instabreak().randomTicks().sound(SoundType.NETHER_WOOD)));

    public static final Supplier<Block> SORROWSQUASH_STEM_PLANT = registerBlockWithoutItem("sorrowsquash_stem_plant", () ->
            new VineStemBodyBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().instabreak().sound(SoundType.NETHER_WOOD)));

    public static final Supplier<Block> CEREBRAGE_SKULL = registerBlockWithoutItem("cerebrage_skull", () ->
            new CerebrageSkullBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.SKELETON_SKULL).randomTicks()));

    /**
     * Shroomlight
     */

    public static final Supplier<Block> SHROOMNIGHT = registerBlock("shroomnight", () ->
            new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.SHROOMLIGHT).lightLevel((state) -> 8)));

    /**
     * Wart Beards
     */

    public static final Supplier<Block> NETHER_WART_BEARD = registerBlock("nether_wart_beard", () ->
            new BeardBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).instabreak().noCollission().sound(SoundType.WART_BLOCK)));

    public static final Supplier<Block> WARPED_WART_BEARD = registerBlock("warped_wart_beard", () ->
            new BeardBlock(BlockBehaviour.Properties.ofLegacyCopy(JNEBlocks.NETHER_WART_BEARD.get()).mapColor(MapColor.WARPED_WART_BLOCK)));

    /**
     * Ivy
     */

    public static final Supplier<Block> WEEPING_IVY = registerBlock("weeping_ivy", () ->
            new IvyBlock(JNEItems.WEEPING_HELIX, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).instabreak().noCollission().sound(SoundType.WEEPING_VINES)));

    public static final Supplier<Block> TWISTING_IVY = registerBlock("twisting_ivy", () ->
            new IvyBlock(JNEItems.TWISTING_HELIX, BlockBehaviour.Properties.of().mapColor(MapColor.WARPED_WART_BLOCK).instabreak().noCollission().sound(SoundType.WEEPING_VINES)));

    /**
     * Sprouts
     */

    public static final Supplier<Block> CRIMSON_SPROUTS = registerBlock("crimson_sprouts", () ->
            new NetherSproutsBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_RED)));

    /**
     * Sporeshrooms and Geysers
     */

    public static final Supplier<Block> CRIMSON_SPORESHROOM = registerBlock("crimson_sporeshroom", () ->
            new SporeshroomBlock(JNETags.Biomes.HAS_CRIMSON_SPORES, () -> ParticleTypes.CRIMSON_SPORE, JNEParticleTypes.CRIMSON_SMOG, BlockBehaviour.Properties.of().strength(0.5f).sound(SoundType.FUNGUS)));

    public static final Supplier<Block> WARPED_SPORESHROOM = registerBlock("warped_sporeshroom", () ->
            new SporeshroomBlock(JNETags.Biomes.HAS_WARPED_SPORES, () -> ParticleTypes.WARPED_SPORE, JNEParticleTypes.WARPED_SMOG, BlockBehaviour.Properties.of().strength(0.5f).sound(SoundType.FUNGUS)));

    public static final Supplier<Block> SOULED_GEYSER = registerBlock("souled_geyser", () ->
            new GeyserBlock(JNETags.Biomes.HAS_ASH, () -> ParticleTypes.ASH, JNEParticleTypes.BLACK_SMOKE, BlockBehaviour.Properties.ofLegacyCopy(JNEBlocks.SOUL_SLATE.get()).sound(JNESoundType.SOUL_SLATE)));

    public static final Supplier<Block> BASALTIC_GEYSER = registerBlock("basaltic_geyser", () ->
            new GeyserBlock(JNETags.Biomes.HAS_WHITE_ASH, () -> ParticleTypes.WHITE_ASH, JNEParticleTypes.WHITE_SMOKE, BlockBehaviour.Properties.ofLegacyCopy(Blocks.BASALT).sound(SoundType.BASALT)));

    /**
     * Potted Blocks
     */

    public static final Supplier<Block> POTTED_SOUL_SWIRLS = registerBlockWithoutItem("potted_soul_swirls", () ->
            new FlowerPotBlock(SOUL_SWIRLS.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));

    public static final Supplier<Block> POTTED_CRIMSON_SPORESHROOM = registerBlockWithoutItem("potted_crimson_sporeshroom", () ->
            new FlowerPotBlock(CRIMSON_SPORESHROOM.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));

    public static final Supplier<Block> POTTED_WARPED_SPORESHROOM = registerBlockWithoutItem("potted_warped_sporeshroom", () ->
            new FlowerPotBlock(WARPED_SPORESHROOM.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));

    public static final Supplier<Block> POTTED_SOUL_TORCHFLOWER = registerBlockWithoutItem("potted_soul_torchflower", () ->
            new FlowerPotBlock(SOUL_TORCHFLOWER.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));

    /**
     * Blackstone
     */

    public static final Supplier<Block> POLISHED_BLACKSTONE_PILLAR = registerBlock("polished_blackstone_pillar", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.POLISHED_BLACKSTONE)));

    public static final Supplier<Block> POLISHED_BLACKSTONE_FENCE = registerBlock("polished_blackstone_fence", () ->
            new FenceBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.POLISHED_BLACKSTONE)));

    public static final Supplier<Block> WEEPING_POLISHED_BLACKSTONE_BRICKS = registerBlock("weeping_polished_blackstone_bricks", () ->
            new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.POLISHED_BLACKSTONE_BRICKS)));

    public static final Supplier<Block> WEEPING_POLISHED_BLACKSTONE_BRICK_SLAB = registerBlock("weeping_polished_blackstone_brick_slab", () ->
            new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.POLISHED_BLACKSTONE_BRICKS)));

    public static final Supplier<Block> WEEPING_POLISHED_BLACKSTONE_BRICK_STAIRS = registerBlock("weeping_polished_blackstone_brick_stairs", () ->
            new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.POLISHED_BLACKSTONE_BRICKS)));

    public static final Supplier<Block> WEEPING_POLISHED_BLACKSTONE_BRICK_WALL = registerBlock("weeping_polished_blackstone_brick_wall", () ->
            new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.POLISHED_BLACKSTONE_BRICKS)));

    public static final Supplier<Block> TWISTING_POLISHED_BLACKSTONE_BRICKS = registerBlock("twisting_polished_blackstone_bricks", () ->
            new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.POLISHED_BLACKSTONE_BRICKS)));

    public static final Supplier<Block> TWISTING_POLISHED_BLACKSTONE_BRICK_SLAB = registerBlock("twisting_polished_blackstone_brick_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.POLISHED_BLACKSTONE_BRICKS)));

    public static final Supplier<Block> TWISTING_POLISHED_BLACKSTONE_BRICK_STAIRS = registerBlock("twisting_polished_blackstone_brick_stairs", () ->
            new StairBlock(TWISTING_POLISHED_BLACKSTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(Blocks.POLISHED_BLACKSTONE_BRICKS)));

    public static final Supplier<Block> TWISTING_POLISHED_BLACKSTONE_BRICK_WALL = registerBlock("twisting_polished_blackstone_brick_wall", () ->
            new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.POLISHED_BLACKSTONE_BRICKS)));

    public static void init(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
