package net.jadenxgamer.netherexp.registry.block;

import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.custom.LightAbleBlock;
import net.jadenxgamer.netherexp.registry.block.custom.SoulCandleBlock;
import net.jadenxgamer.netherexp.registry.block.custom.SoulGlassBlock;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

public class JNEBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(NetherExp.MOD_ID, Registries.BLOCK);

    // SOUL SLATE

    public static final RegistrySupplier<Block> SOUL_SLATE = registerBlock("soul_slate", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(4.0f).requiresCorrectToolForDrops().sound(JNESoundType.SOUL_SLATE)));

    public static final RegistrySupplier<Block> PALE_SOUL_SLATE = registerBlock("pale_soul_slate", () ->
            new Block(BlockBehaviour.Properties.copy(SOUL_SLATE.get())));

    public static final RegistrySupplier<Block> JAGGED_SOUL_SLATE = registerBlock("jagged_soul_slate", () ->
            new Block(BlockBehaviour.Properties.copy(SOUL_SLATE.get())));

    public static final RegistrySupplier<Block> SOUL_SLATE_SLAB = registerBlock("soul_slate_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(SOUL_SLATE.get())));

    public static final RegistrySupplier<Block> SOUL_SLATE_STAIRS = registerBlock("soul_slate_stairs", () ->
            new StairBlock(JNEBlocks.SOUL_SLATE.get().defaultBlockState(), BlockBehaviour.Properties.copy(SOUL_SLATE.get())));

    public static final RegistrySupplier<Block> SOUL_SLATE_WALL = registerBlock("soul_slate_wall", () ->
            new WallBlock(BlockBehaviour.Properties.copy(SOUL_SLATE.get())));

    // SOUL SLATE BRICKS

    public static final RegistrySupplier<Block> SOUL_SLATE_BRICKS = registerBlock("soul_slate_bricks", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(2.0f).requiresCorrectToolForDrops().sound(JNESoundType.SOUL_SLATE_BRICKS)));

    public static final RegistrySupplier<Block> SOUL_SLATE_BRICK_SLAB = registerBlock("soul_slate_brick_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(SOUL_SLATE_BRICKS.get())));

    public static final RegistrySupplier<Block> SOUL_SLATE_BRICK_STAIRS = registerBlock("soul_slate_brick_stairs", () ->
            new StairBlock(JNEBlocks.SOUL_SLATE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(SOUL_SLATE_BRICKS.get())));

    public static final RegistrySupplier<Block> SOUL_SLATE_BRICK_WALL = registerBlock("soul_slate_brick_wall", () ->
            new WallBlock(BlockBehaviour.Properties.copy(SOUL_SLATE_BRICKS.get())));

    public static final RegistrySupplier<Block> INDENTED_SOUL_SLATE_BRICKS = registerBlock("indented_soul_slate_bricks", () ->
            new LightAbleBlock(BlockBehaviour.Properties.copy(SOUL_SLATE_BRICKS.get())));

    public static final RegistrySupplier<Block> CRACKED_SOUL_SLATE_BRICKS = registerBlock("cracked_soul_slate_bricks", () ->
            new LightAbleBlock(BlockBehaviour.Properties.copy(SOUL_SLATE_BRICKS.get()).lightLevel(
                    blockState -> blockState.getValue(SoulGlassBlock.LIT) ? 12 : 0)));

    public static final RegistrySupplier<Block> SOUL_SLATE_BRICK_PILLAR = registerBlock("soul_slate_brick_pillar", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.copy(SOUL_SLATE_BRICKS.get())));

    public static final RegistrySupplier<Block> CHISELED_SOUL_SLATE_BRICKS = registerBlock("chiseled_soul_slate_bricks", () ->
            new LightAbleBlock(BlockBehaviour.Properties.copy(SOUL_SLATE_BRICKS.get()).lightLevel(
                    blockState -> blockState.getValue(SoulGlassBlock.LIT) ? 12 : 0)));

    // SOUL DECORATIONS

    public static final RegistrySupplier<Block> SOUL_CANDLE = registerBlock("soul_candle", () ->
            new SoulCandleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noOcclusion().strength(1.0f).lightLevel(SoulCandleBlock.STATE_TO_LUMINANCE).sound(JNESoundType.SOUL_CANDLE)));

    public static final RegistrySupplier<Block> SOUL_GLASS = registerBlock("soul_glass", () ->
            new SoulGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).lightLevel(
                    blockState -> blockState.getValue(SoulGlassBlock.LIT) ? 12 : 0).strength(0.3f, 1200.0f).sound(SoundType.GLASS)
            ));

    //TODO: Add Block
    public static final RegistrySupplier<Block> SOUL_SWIRLS = registerBlock("soul_swirls", () ->
            new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().instabreak().sound(SoundType.NETHER_SPROUTS)));

    //TODO: Add Block
    //Cinderscapes Compat
    public static final RegistrySupplier<Block> SHALE_SWIRLS = registerCompatBlock("shale_swirls", () ->
            new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.copy(SOUL_SWIRLS.get())), "cinderscapes");

    // TODO Add Block
    public static final RegistrySupplier<Block> ECTO_SOUL_SAND = registerBlock("ecto_soul_sand", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.SOUL_SAND).lightLevel((state) -> 4).randomTicks()));

    public static final RegistrySupplier<Block> SUSPICIOUS_SOUL_SAND = registerBlock("suspicious_soul_sand", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.SOUL_SAND).speedFactor(0.2f).strength(0.25f).pushReaction(PushReaction.DESTROY).sound(JNESoundType.SUSPICIOUS_SOUL_SAND)));

    public static final RegistrySupplier<Block> SOUL_MAGMA_BLOCK = registerBlock("soul_magma_block", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.MAGMA_BLOCK).mapColor(MapColor.COLOR_LIGHT_BLUE).lightLevel((state) -> 4).sound(JNESoundType.SOUL_MAGMA_BLOCK)));

    private static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistrySupplier<T> registerBlockWithoutItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }


    private static <T extends Block> RegistrySupplier<T> registerCompatBlock(String name, Supplier<T> block, String modId) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        if (Platform.isModLoaded(modId)) {
            registerBlockItem(name, toReturn);
        }
        return toReturn;
    }

    private static <T extends Block> RegistrySupplier<Item> registerBlockItem(String name, RegistrySupplier<T> block) {
        return JNEItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void init() {
        BLOCKS.register();
    }
}
