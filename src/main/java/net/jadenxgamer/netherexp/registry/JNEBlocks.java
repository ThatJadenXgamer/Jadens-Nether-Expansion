package net.jadenxgamer.netherexp.registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.block.*;
import net.jadenxgamer.netherexp.core.misc.JNESoundType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

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
            new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_SOIL).strength(0.6f)));

    public static final Supplier<Block> FOSSIL_ORE = registerBlock("fossil_ore", () ->
            new BuriedConverterBlock(ConstantInt.of(0), FOSSIL_FUEL_ORE, BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_SOIL).randomTicks().strength(0.6f)));

    public static final Supplier<Block> SOUL_SWIRLS = registerBlock("soul_swirls", () ->
            new SwirlsBlock(JNEParticleTypes.SOUL_SWIRL_POP, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).replaceable().noCollission().instabreak()
                    .lightLevel(state -> state.getValue(SwirlsBlock.ACTIVE) ? 6 : 0).sound(SoundType.NETHER_SPROUTS)));

    public static final Supplier<Block> SOUL_CANDLE = registerBlock("soul_candle", () ->
            new SoulCandleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noOcclusion().strength(1.0f).lightLevel(SoulCandleBlock.STATE_TO_LUMINANCE).sound(JNESoundType.SOUL_CANDLE)));

    public static final Supplier<Block> SOUL_GLASS = registerBlock("soul_glass", () ->
            new SoulGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).lightLevel(
                    state -> state.getValue(SoulGlassBlock.LIT) ? 12 : 0).strength(0.3f, 1200.0f).sound(SoundType.GLASS)));

    public static void init(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
