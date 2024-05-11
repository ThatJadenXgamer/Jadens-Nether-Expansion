package net.jadenxgamer.netherexp.registry.block;

import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.custom.*;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

@SuppressWarnings({"unused", "deprecation"})
public class JNEBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(NetherExp.MOD_ID, Registries.BLOCK);

    ////////////
    // BLOCKS //
    ////////////

    // Soul Slate

    public static final RegistrySupplier<Block> SOUL_SLATE = registerBlock("soul_slate", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(4.0f).requiresCorrectToolForDrops().sound(JNESoundType.SOUL_SLATE)));

    public static final RegistrySupplier<Block> PALE_SOUL_SLATE = registerBlock("pale_soul_slate", () ->
            new Block(BlockBehaviour.Properties.copy(SOUL_SLATE.get()).mapColor(MapColor.TERRACOTTA_PINK)));

    public static final RegistrySupplier<Block> SOUL_SLATE_SLAB = registerBlock("soul_slate_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(SOUL_SLATE.get())));

    public static final RegistrySupplier<Block> SOUL_SLATE_STAIRS = registerBlock("soul_slate_stairs", () ->
            new StairBlock(JNEBlocks.SOUL_SLATE.get().defaultBlockState(), BlockBehaviour.Properties.copy(SOUL_SLATE.get())));

    public static final RegistrySupplier<Block> SOUL_SLATE_WALL = registerBlock("soul_slate_wall", () ->
            new WallBlock(BlockBehaviour.Properties.copy(SOUL_SLATE.get())));

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

    public static final RegistrySupplier<Block> SOUL_SLATE_TILES = registerBlock("soul_slate_tiles", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PINK).strength(2.0f).requiresCorrectToolForDrops().sound(JNESoundType.SOUL_SLATE_BRICKS)));

    public static final RegistrySupplier<Block> SOUL_SLATE_TILE_SLAB = registerBlock("soul_slate_tile_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(SOUL_SLATE_TILES.get())));

    public static final RegistrySupplier<Block> SOUL_SLATE_TILE_STAIRS = registerBlock("soul_slate_tile_stairs", () ->
            new StairBlock(JNEBlocks.SOUL_SLATE_TILES.get().defaultBlockState(), BlockBehaviour.Properties.copy(SOUL_SLATE_TILES.get())));

    public static final RegistrySupplier<Block> SOUL_SLATE_TILE_WALL = registerBlock("soul_slate_tile_wall", () ->
            new WallBlock(BlockBehaviour.Properties.copy(SOUL_SLATE_TILES.get())));

    public static final RegistrySupplier<Block> INDENTED_SOUL_SLATE_TILES = registerBlock("indented_soul_slate_tiles", () ->
            new LightAbleBlock(BlockBehaviour.Properties.copy(SOUL_SLATE_TILES.get())));

    public static final RegistrySupplier<Block> CHISELED_SOUL_SLATE_TILES = registerBlock("chiseled_soul_slate_tiles", () ->
            new LightAbleBlock(BlockBehaviour.Properties.copy(SOUL_SLATE_TILES.get()).lightLevel(
                    blockState -> blockState.getValue(SoulGlassBlock.LIT) ? 12 : 0)));

    // Soul Decorations

    public static final RegistrySupplier<Block> SOUL_CANDLE = registerBlock("soul_candle", () ->
            new SoulCandleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noOcclusion().strength(1.0f).lightLevel(SoulCandleBlock.STATE_TO_LUMINANCE).sound(JNESoundType.SOUL_CANDLE)));

    public static final RegistrySupplier<Block> SOUL_GLASS = registerBlock("soul_glass", () ->
            new SoulGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).lightLevel(
                    blockState -> blockState.getValue(SoulGlassBlock.LIT) ? 12 : 0).strength(0.3f, 1200.0f).sound(SoundType.GLASS)
            ));


    public static final RegistrySupplier<Block> SOUL_SWIRLS = registerBlock("soul_swirls", () ->
            new SwirlsBlock(7, 3, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).replaceable().noCollission().instabreak().sound(SoundType.NETHER_SPROUTS), 1));

    // CINDERSCAPES COMPATIBILITY
    public static final RegistrySupplier<Block> SHALE_SWIRLS = registerCompatBlock("shale_swirls", () ->
            new SwirlsBlock(7, 3, BlockBehaviour.Properties.copy(SOUL_SWIRLS.get()), 2), "cinderscapes");

    public static final RegistrySupplier<Block> ECTO_SOUL_SAND = registerBlock("ecto_soul_sand", () ->
            new EctoSoulSandBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_SAND).lightLevel((state) -> 4).randomTicks()));

    public static final RegistrySupplier<Block> SUSPICIOUS_SOUL_SAND = registerBlock("suspicious_soul_sand", () ->
            new JNEBrushableBlock(Blocks.SOUL_SAND, BlockBehaviour.Properties.copy(Blocks.SOUL_SAND).speedFactor(0.2f).strength(0.25f).pushReaction(PushReaction.DESTROY).sound(JNESoundType.SUSPICIOUS_SOUL_SAND), SoundEvents.BRUSH_SAND, JNESoundEvents.BRUSH_BRUSHING_SOUL_SAND_COMPLETE.get()));

    public static final RegistrySupplier<Block> SOUL_MAGMA_BLOCK = registerBlock("soul_magma_block", () ->
            new SoulMagmaBlock(BlockBehaviour.Properties.copy(Blocks.MAGMA_BLOCK).mapColor(MapColor.COLOR_LIGHT_BLUE).lightLevel((state) -> 4).sound(JNESoundType.SOUL_MAGMA_BLOCK)));

    public static final RegistrySupplier<Block> BLACK_ICE = registerBlock("black_ice", () ->
            new BlackIceBlock(BlockBehaviour.Properties.copy(Blocks.PACKED_ICE).mapColor(MapColor.COLOR_BLACK).randomTicks().requiresCorrectToolForDrops().strength(0.5f).lightLevel((state) -> 7).sound(JNESoundType.BLACK_ICE)));

    public static final RegistrySupplier<Block> FOSSIL_ORE = registerBlock("fossil_ore", () ->
            new FossilOreBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_SOIL).randomTicks().strength(0.6f)));

    public static final RegistrySupplier<Block> FOSSIL_FUEL_ORE = registerBlock("fossil_fuel_ore", () ->
            new JNEOreBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_SOIL).strength(0.6f), UniformInt.of(0, 2), 0));

    public static final RegistrySupplier<Block> DIAMOND_FOSSIL_ORE = registerBlock("diamond_fossil_ore", () ->
            new JNEOreBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_SOIL).strength(0.6f).requiresCorrectToolForDrops(), UniformInt.of(3, 7), 1));

    public static final RegistrySupplier<Block> SOUL_TORCHFLOWER = registerBlock("soul_torchflower", () ->
            new NetherFlowerBlock(MobEffects.LEVITATION, 5, BlockBehaviour.Properties.copy(Blocks.TORCHFLOWER)));

    public static final RegistrySupplier<Block> SOUL_TORCHFLOWER_CROP = registerBlockWithoutItem("soul_torchflower_crop", () ->
            new SoulTorchflowerBlock(BlockBehaviour.Properties.copy(Blocks.TORCHFLOWER_CROP)));

    public static final RegistrySupplier<Block> SORROWSQUASH = registerBlock("sorrowsquash", () ->
            new SorrowsquashBlock(BlockBehaviour.Properties.copy(Blocks.PUMPKIN).mapColor(MapColor.COLOR_LIGHT_GRAY).strength(1.0f).sound(SoundType.NETHER_WOOD)));

    public static final RegistrySupplier<Block> CARVED_SORROWSQUASH = registerBlock("carved_sorrowsquash", () ->
        new CarvedSorrowsquashBlock(BlockBehaviour.Properties.copy(Blocks.CARVED_PUMPKIN).mapColor(MapColor.COLOR_LIGHT_GRAY).strength(1.0f).sound(SoundType.NETHER_WOOD)));

    public static final RegistrySupplier<Block> GHOUL_O_LANTERN = registerBlock("ghoul_o_lantern", () ->
        new CarvedSorrowsquashBlock(BlockBehaviour.Properties.copy(Blocks.JACK_O_LANTERN).mapColor(MapColor.COLOR_ORANGE).strength(1.0f).lightLevel((state) -> 15).sound(SoundType.NETHER_WOOD)));

    public static final RegistrySupplier<Block> SOUL_GHOUL_O_LANTERN = registerBlock("soul_ghoul_o_lantern", () ->
        new CarvedSorrowsquashBlock(BlockBehaviour.Properties.copy(Blocks.JACK_O_LANTERN).mapColor(MapColor.COLOR_LIGHT_BLUE).strength(1.0f).lightLevel((state) -> 10).sound(SoundType.NETHER_WOOD)));

    public static final RegistrySupplier<Block> SOUL_JACK_O_LANTERN = registerBlock("soul_jack_o_lantern", () ->
        new CarvedPumpkinBlock(BlockBehaviour.Properties.copy(Blocks.JACK_O_LANTERN).mapColor(MapColor.COLOR_LIGHT_BLUE).strength(1.0f).lightLevel((state) -> 10)));

    public static final RegistrySupplier<Block> SORROWSQUASH_STEM = registerBlockWithoutItem("sorrowsquash_stem", () ->
            new VineStemBlock((StemGrownBlock) JNEBlocks.SORROWSQUASH.get(), () -> Items.PUMPKIN_SEEDS, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().instabreak().randomTicks().sound(SoundType.NETHER_WOOD)));

    public static final RegistrySupplier<Block> SORROWSQUASH_STEM_PLANT = registerBlockWithoutItem("sorrowsquash_stem_plant", () ->
            new VineStemPlantBlock((StemGrownBlock) JNEBlocks.SORROWSQUASH.get(), () -> Items.PUMPKIN_SEEDS, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().instabreak().sound(SoundType.NETHER_WOOD)));

    public static final RegistrySupplier<Block> SOUL_SOIL_LAYER = registerBlock("soul_soil_layer", () ->
            new SoulSoilLayerBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_SOIL).strength(0.1f).requiresCorrectToolForDrops().replaceable().forceSolidOff().isViewBlocking(
                    ((state, world, pos) -> state.getValue(SoulSoilLayerBlock.LAYERS) >= 8))
            ));

    public static final RegistrySupplier<Block> SHOTGUN_BARREL = registerBlock("shotgun_barrel", () ->
        new ShotgunBarrelBlock(BlockBehaviour.Properties.of().strength(3.5f).requiresCorrectToolForDrops().sound(SoundType.COPPER)));

    // Netherrack

    public static final RegistrySupplier<Block> SMOOTH_NETHERRACK = registerBlock("smooth_netherrack", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.NETHERRACK).strength(1.4f).requiresCorrectToolForDrops()));

    public static final RegistrySupplier<Block> SMOOTH_NETHERRACK_SLAB = registerBlock("smooth_netherrack_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(JNEBlocks.SMOOTH_NETHERRACK.get())));

    public static final RegistrySupplier<Block> SMOOTH_NETHERRACK_STAIRS = registerBlock("smooth_netherrack_stairs", () ->
            new JNEStairBlock(JNEBlocks.SMOOTH_NETHERRACK.get().defaultBlockState(), BlockBehaviour.Properties.copy(JNEBlocks.SMOOTH_NETHERRACK.get())));

    public static final RegistrySupplier<Block> SMOOTH_NETHERRACK_WALL = registerBlock("smooth_netherrack_wall", () ->
            new WallBlock(BlockBehaviour.Properties.copy(JNEBlocks.SMOOTH_NETHERRACK.get())));

    public static final RegistrySupplier<Block> NETHERRACK_BRICKS = registerBlock("netherrack_bricks", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.NETHERRACK).strength(1.4f).requiresCorrectToolForDrops().sound(JNESoundType.NETHERRACK_BRICKS)));

    public static final RegistrySupplier<Block> NETHERRACK_BRICK_SLAB = registerBlock("netherrack_brick_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(JNEBlocks.NETHERRACK_BRICKS.get())));

    public static final RegistrySupplier<Block> NETHERRACK_BRICK_STAIRS = registerBlock("netherrack_brick_stairs", () ->
            new JNEStairBlock(JNEBlocks.NETHERRACK_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(JNEBlocks.NETHERRACK_BRICKS.get())));

    public static final RegistrySupplier<Block> NETHERRACK_BRICK_WALL = registerBlock("netherrack_brick_wall", () ->
            new WallBlock(BlockBehaviour.Properties.copy(JNEBlocks.NETHERRACK_BRICKS.get())));

    public static final RegistrySupplier<Block> NETHERRACK_TILES = registerBlock("netherrack_tiles", () ->
            new Block(BlockBehaviour.Properties.copy(JNEBlocks.NETHERRACK_BRICKS.get())));

    public static final RegistrySupplier<Block> NETHERRACK_PILLAR = registerBlock("netherrack_pillar", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.copy(JNEBlocks.NETHERRACK_BRICKS.get())));

    // Basalt

    public static final RegistrySupplier<Block> BASALT_SLAB = registerBlock("basalt_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(Blocks.BASALT)));

    public static final RegistrySupplier<Block> BASALT_STAIRS = registerBlock("basalt_stairs", () ->
            new JNEStairBlock(Blocks.BASALT.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.BASALT)));

    public static final RegistrySupplier<Block> BASALT_WALL = registerBlock("basalt_wall", () ->
            new WallBlock(BlockBehaviour.Properties.copy(Blocks.BASALT)));

    public static final RegistrySupplier<Block> POLISHED_BASALT_SLAB = registerBlock("polished_basalt_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BASALT)));

    public static final RegistrySupplier<Block> POLISHED_BASALT_STAIRS = registerBlock("polished_basalt_stairs", () ->
            new JNEStairBlock(Blocks.POLISHED_BASALT.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.POLISHED_BASALT)));

    public static final RegistrySupplier<Block> POLISHED_BASALT_WALL = registerBlock("polished_basalt_wall", () ->
            new WallBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BASALT)));

    public static final RegistrySupplier<Block> POLISHED_BASALT_BRICKS = registerBlock("polished_basalt_bricks", () ->
            new PolishedBasaltBrickBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BASALT).sound(JNESoundType.BASALT_BRICKS)));

    public static final RegistrySupplier<Block> POLISHED_BASALT_BRICK_SLAB = registerBlock("polished_basalt_brick_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(JNEBlocks.POLISHED_BASALT_BRICKS.get())));

    public static final RegistrySupplier<Block> POLISHED_BASALT_BRICK_STAIRS = registerBlock("polished_basalt_brick_stairs", () ->
            new JNEStairBlock(JNEBlocks.POLISHED_BASALT_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(JNEBlocks.POLISHED_BASALT_BRICKS.get())));

    public static final RegistrySupplier<Block> POLISHED_BASALT_BRICK_WALL = registerBlock("polished_basalt_brick_wall", () ->
            new WallBlock(BlockBehaviour.Properties.copy(JNEBlocks.POLISHED_BASALT_BRICKS.get())));

    // Netherite

    public static final RegistrySupplier<Block> NETHERITE_PLATED_BLOCK = registerFireProofBlock("netherite_plated_block", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(1.0f, 1200.0f).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));

    public static final RegistrySupplier<Block> CUT_NETHERITE_BLOCK = registerFireProofBlock("cut_netherite_block", () ->
            new Block(BlockBehaviour.Properties.copy(JNEBlocks.NETHERITE_PLATED_BLOCK.get())));

    public static final RegistrySupplier<Block> CUT_NETHERITE_SLAB = registerFireProofBlock("cut_netherite_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(JNEBlocks.CUT_NETHERITE_BLOCK.get())));

    public static final RegistrySupplier<Block> CUT_NETHERITE_STAIRS = registerFireProofBlock("cut_netherite_stairs", () ->
            new JNEStairBlock(JNEBlocks.CUT_NETHERITE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(JNEBlocks.CUT_NETHERITE_BLOCK.get())));

    public static final RegistrySupplier<Block> CUT_NETHERITE_PILLAR = registerFireProofBlock("cut_netherite_pillar", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.copy(JNEBlocks.CUT_NETHERITE_BLOCK.get())));

    //TODO: Add Block
    public static final RegistrySupplier<Block> NETHERITE_GRATE = registerFireProofBlock("netherite_grate", () ->
            new Block(BlockBehaviour.Properties.copy(JNEBlocks.CUT_NETHERITE_BLOCK.get())));

    // Rusty Netherite

    public static final RegistrySupplier<Block> RUSTY_NETHERITE_PLATED_BLOCK = registerFireProofBlock("rusty_netherite_plated_block", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(1.0f, 1200.0f).requiresCorrectToolForDrops().sound(SoundType.ANCIENT_DEBRIS)));

    public static final RegistrySupplier<Block> RUSTY_CUT_NETHERITE_BLOCK = registerFireProofBlock("rusty_cut_netherite_block", () ->
            new Block(BlockBehaviour.Properties.copy(JNEBlocks.RUSTY_NETHERITE_PLATED_BLOCK.get())));

    public static final RegistrySupplier<Block> RUSTY_CUT_NETHERITE_SLAB = registerFireProofBlock("rusty_cut_netherite_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get())));

    public static final RegistrySupplier<Block> RUSTY_CUT_NETHERITE_STAIRS = registerFireProofBlock("rusty_cut_netherite_stairs", () ->
            new JNEStairBlock(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(JNEBlocks.CUT_NETHERITE_BLOCK.get())));

    public static final RegistrySupplier<Block> RUSTY_CUT_NETHERITE_PILLAR = registerFireProofBlock("rusty_cut_netherite_pillar", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.copy(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get())));

    //TODO: Add Block
    public static final RegistrySupplier<Block> RUSTY_NETHERITE_GRATE = registerFireProofBlock("rusty_netherite_grate", () ->
            new Block(BlockBehaviour.Properties.copy(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get())));

    // Enigma

    public static final RegistrySupplier<Block> ENIGMA_FLESH = registerBlock("enigma_flesh", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).strength(1.8f).sound(SoundType.FUNGUS)));

    public static final RegistrySupplier<Block> STRANGE_ENIGMA_FLESH = registerBlock("strange_enigma_flesh", () ->
            new StrangeEnigmaFleshBlock(BlockBehaviour.Properties.copy(JNEBlocks.ENIGMA_FLESH.get()).mapColor(MapColor.DIAMOND).lightLevel((state) -> 12)));

    public static final RegistrySupplier<Block> ENIGMA_CROWN = registerBlock("enigma_crown", () ->
            new EnigmaCrownBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).strength(0.2f).sound(SoundType.FUNGUS)));

    public static final RegistrySupplier<Block> ENIGMA_SHELF = registerBlock("enigma_shelf", () ->
            new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).noCollission().instabreak().sound(SoundType.FUNGUS)));

    // Claret

    public static final RegistrySupplier<Block> CLARET_STEM = registerBlock("claret_stem", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_STEM).mapColor(MapColor.CRIMSON_HYPHAE)));

    public static final RegistrySupplier<Block> CLARET_HYPHAE = registerBlock("claret_hyphae", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_HYPHAE).mapColor(MapColor.CRIMSON_HYPHAE)));

    public static final RegistrySupplier<Block> STRIPPED_CLARET_STEM = registerBlock("stripped_claret_stem", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_WARPED_STEM).mapColor(MapColor.NETHER)));

    public static final RegistrySupplier<Block> STRIPPED_CLARET_HYPHAE = registerBlock("stripped_claret_hyphae", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_WARPED_HYPHAE).mapColor(MapColor.NETHER)));

    public static final RegistrySupplier<Block> CLARET_PLANKS = registerBlock("claret_planks", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.WARPED_PLANKS).mapColor(MapColor.NETHER)));

    public static final RegistrySupplier<Block> CLARET_SLAB = registerBlock("claret_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_SLAB).mapColor(MapColor.NETHER)));

    public static final RegistrySupplier<Block> CLARET_STAIRS = registerBlock("claret_stairs", () ->
            new StairBlock(JNEBlocks.CLARET_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.WARPED_STAIRS).mapColor(MapColor.NETHER)));

    public static final RegistrySupplier<Block> CLARET_FENCE = registerBlock("claret_fence", () ->
            new FenceBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_FENCE).mapColor(MapColor.NETHER)));

    public static final RegistrySupplier<Block> CLARET_FENCE_GATE = registerBlock("claret_fence_gate", () ->
            new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_FENCE_GATE).mapColor(MapColor.NETHER), WoodType.CRIMSON));

    public static final RegistrySupplier<Block> CLARET_DOOR = registerBlock("claret_door", () ->
            new DoorBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_DOOR).mapColor(MapColor.NETHER), BlockSetType.CRIMSON));

    public static final RegistrySupplier<Block> CLARET_TRAPDOOR = registerBlock("claret_trapdoor", () ->
            new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_TRAPDOOR).mapColor(MapColor.NETHER), BlockSetType.CRIMSON));

    public static final RegistrySupplier<Block> CLARET_BUTTON = registerBlock("claret_button", () ->
            new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_BUTTON).mapColor(MapColor.NETHER), BlockSetType.CRIMSON, 30, true));

    public static final RegistrySupplier<Block> CLARET_PRESSURE_PLATE = registerBlock("claret_pressure_plate", () ->
            new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.WARPED_PRESSURE_PLATE).mapColor(MapColor.NETHER), BlockSetType.CRIMSON));

    public static final RegistrySupplier<Block> CLARET_SIGN = registerBlockWithoutItem("claret_sign", () ->
            new StandingSignBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_SIGN).mapColor(MapColor.NETHER), JNEWoodType.CLARET));

    public static final RegistrySupplier<Block> CLARET_WALL_SIGN = registerBlockWithoutItem("claret_wall_sign", () ->
            new WallSignBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_WALL_SIGN).mapColor(MapColor.NETHER).dropsLike(JNEBlocks.CLARET_SIGN.get()), JNEWoodType.CLARET));

    public static final RegistrySupplier<Block> CLARET_HANGING_SIGN = registerBlockWithoutItem("claret_hanging_sign", () ->
            new CeilingHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_HANGING_SIGN).mapColor(MapColor.NETHER), JNEWoodType.CLARET));

    public static final RegistrySupplier<Block> CLARET_WALL_HANGING_SIGN = registerBlockWithoutItem("claret_wall_hanging_sign", () ->
            new WallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_WALL_HANGING_SIGN).mapColor(MapColor.NETHER).dropsLike(JNEBlocks.CLARET_HANGING_SIGN.get()), JNEWoodType.CLARET));

    // EXHAUST MIRE

    public static final RegistrySupplier<Block> MAGMA_CREAM_BLOCK = registerBlock("magma_cream_block", () ->
            new MagmaCreamBlock(BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.PUSH_ONLY).sound(SoundType.HONEY_BLOCK)));

    public static final RegistrySupplier<Block> IGNEOUS_REEDS = registerBlock("igneous_reeds", () ->
            new IgneousReeds(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLUE).noCollission().instabreak().offsetType(BlockBehaviour.OffsetType.XZ).sound(JNESoundType.SMOKESTALK)));

    public static final RegistrySupplier<Block> SMOKESTALK = registerBlock("smokestalk", () ->
            new SmokestalkBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLUE).randomTicks().strength(0.5f).sound(JNESoundType.SMOKESTALK)));

    public static final RegistrySupplier<Block> SMOKESTALK_PLANT = registerBlockWithoutItem("smokestalk_plant", () ->
            new SmokestalkPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLUE).strength(0.5f).sound(JNESoundType.SMOKESTALK)));

    // Smokestalk

    public static final RegistrySupplier<Block> SMOKESTALK_BLOCK = registerBlock("smokestalk_block", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_STEM).mapColor(MapColor.TERRACOTTA_CYAN).sound(JNESoundType.SMOKESTALK_WOOD)));

    public static final RegistrySupplier<Block> STRIPPED_SMOKESTALK_BLOCK = registerBlock("stripped_smokestalk_block", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_WARPED_STEM).mapColor(MapColor.TERRACOTTA_GRAY).sound(JNESoundType.SMOKESTALK_WOOD)));

    public static final RegistrySupplier<Block> SMOKESTALK_PLANKS = registerBlock("smokestalk_planks", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.WARPED_PLANKS).mapColor(MapColor.TERRACOTTA_GRAY).sound(JNESoundType.SMOKESTALK_WOOD)));

    public static final RegistrySupplier<Block> SMOKESTALK_SLAB = registerBlock("smokestalk_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_SLAB).mapColor(MapColor.TERRACOTTA_GRAY).sound(JNESoundType.SMOKESTALK_WOOD)));

    public static final RegistrySupplier<Block> SMOKESTALK_STAIRS = registerBlock("smokestalk_stairs", () ->
            new StairBlock(JNEBlocks.SMOKESTALK_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.WARPED_STAIRS).mapColor(MapColor.TERRACOTTA_GRAY).sound(JNESoundType.SMOKESTALK_WOOD)));

    public static final RegistrySupplier<Block> SMOKESTALK_FENCE = registerBlock("smokestalk_fence", () ->
            new FenceBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_FENCE).mapColor(MapColor.TERRACOTTA_GRAY).sound(JNESoundType.SMOKESTALK_WOOD)));

    public static final RegistrySupplier<Block> SMOKESTALK_FENCE_GATE = registerBlock("smokestalk_fence_gate", () ->
            new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_FENCE_GATE).mapColor(MapColor.TERRACOTTA_GRAY).sound(JNESoundType.SMOKESTALK_WOOD), JNEWoodType.SMOKESTALK));

    public static final RegistrySupplier<Block> SMOKESTALK_DOOR = registerBlock("smokestalk_door", () ->
            new DoorBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_DOOR).mapColor(MapColor.TERRACOTTA_GRAY).sound(JNESoundType.SMOKESTALK_WOOD), JNEBlockSetType.SMOKESTALK));

    public static final RegistrySupplier<Block> SMOKESTALK_TRAPDOOR = registerBlock("smokestalk_trapdoor", () ->
            new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_TRAPDOOR).mapColor(MapColor.TERRACOTTA_GRAY).sound(JNESoundType.SMOKESTALK_WOOD), JNEBlockSetType.SMOKESTALK));

    public static final RegistrySupplier<Block> SMOKESTALK_BUTTON = registerBlock("smokestalk_button", () ->
            new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_BUTTON).mapColor(MapColor.TERRACOTTA_GRAY).sound(JNESoundType.SMOKESTALK_WOOD), JNEBlockSetType.SMOKESTALK, 30, true));

    public static final RegistrySupplier<Block> SMOKESTALK_PRESSURE_PLATE = registerBlock("smokestalk_pressure_plate", () ->
            new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.WARPED_PRESSURE_PLATE).mapColor(MapColor.TERRACOTTA_GRAY).sound(JNESoundType.SMOKESTALK_WOOD), JNEBlockSetType.SMOKESTALK));

    public static final RegistrySupplier<Block> SMOKESTALK_SIGN = registerBlockWithoutItem("smokestalk_sign", () ->
            new StandingSignBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_SIGN).mapColor(MapColor.TERRACOTTA_GRAY).sound(JNESoundType.SMOKESTALK_WOOD), JNEWoodType.SMOKESTALK));

    public static final RegistrySupplier<Block> SMOKESTALK_WALL_SIGN = registerBlockWithoutItem("smokestalk_wall_sign", () ->
            new WallSignBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_WALL_SIGN).mapColor(MapColor.TERRACOTTA_GRAY).dropsLike(JNEBlocks.SMOKESTALK_SIGN.get()).sound(JNESoundType.SMOKESTALK_WOOD), JNEWoodType.SMOKESTALK));

    public static final RegistrySupplier<Block> SMOKESTALK_HANGING_SIGN = registerBlockWithoutItem("smokestalk_hanging_sign", () ->
            new CeilingHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_HANGING_SIGN).mapColor(MapColor.TERRACOTTA_GRAY).sound(JNESoundType.SMOKESTALK_WOOD), JNEWoodType.SMOKESTALK));

    public static final RegistrySupplier<Block> SMOKESTALK_WALL_HANGING_SIGN = registerBlockWithoutItem("smokestalk_wall_hanging_sign", () ->
            new WallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_WALL_HANGING_SIGN).mapColor(MapColor.TERRACOTTA_GRAY).dropsLike(JNEBlocks.SMOKESTALK_HANGING_SIGN.get()).sound(JNESoundType.SMOKESTALK_WOOD), JNEWoodType.SMOKESTALK));

    // Quartz Blocks

    public static final RegistrySupplier<Block> QUARTZ_CRYSTAL = registerBlock("quartz_crystal", () ->
            new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ).noOcclusion().strength(1.5f).sound(JNESoundType.QUARTZ_BLOCK)));

    public static final RegistrySupplier<Block> QUARTZ_CRYSTAL_BLOCK = registerBlock("quartz_crystal_block", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ).strength(2.5f).requiresCorrectToolForDrops().sound(JNESoundType.QUARTZ_BLOCK)));

    public static final RegistrySupplier<Block> CRACKED_QUARTZ_BRICKS = registerBlock("cracked_quartz_bricks", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK)));

    public static final RegistrySupplier<Block> CHISELED_QUARTZ_PILLAR = registerBlock("chiseled_quartz_pillar", () ->
            new JNEDirectionalBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_PILLAR)));

    // Silica Sand

    public static final RegistrySupplier<Block> SILICA_SAND = registerBlock("silica_sand", () ->
            new SandBlock(8812140, BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).strength(0.4f).sound(SoundType.SAND)));

    public static final RegistrySupplier<Block> SILICA_SANDSTONE = registerBlock("silica_sandstone", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).strength(1.0f, 6.0f).requiresCorrectToolForDrops()));

    public static final RegistrySupplier<Block> SILICA_SANDSTONE_SLAB = registerBlock("silica_sandstone_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(JNEBlocks.SILICA_SANDSTONE.get())));

    public static final RegistrySupplier<Block> SILICA_SANDSTONE_STAIRS = registerBlock("silica_sandstone_stairs", () ->
            new JNEStairBlock(JNEBlocks.SILICA_SANDSTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(JNEBlocks.SILICA_SANDSTONE.get())));

    public static final RegistrySupplier<Block> SILICA_SANDSTONE_WALL = registerBlock("silica_sandstone_wall", () ->
            new WallBlock(BlockBehaviour.Properties.copy(JNEBlocks.SILICA_SANDSTONE.get())));

    public static final RegistrySupplier<Block> CUT_SILICA_SANDSTONE = registerBlock("cut_silica_sandstone", () ->
            new Block(BlockBehaviour.Properties.copy(JNEBlocks.SILICA_SANDSTONE.get())));

    public static final RegistrySupplier<Block> CUT_SILICA_SANDSTONE_SLAB = registerBlock("cut_silica_sandstone_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(JNEBlocks.SILICA_SANDSTONE.get())));

    public static final RegistrySupplier<Block> CHISELED_SILICA_SANDSTONE = registerBlock("chiseled_silica_sandstone", () ->
            new Block(BlockBehaviour.Properties.copy(JNEBlocks.SILICA_SANDSTONE.get())));

    public static final RegistrySupplier<Block> SMOOTH_SILICA_SANDSTONE = registerBlock("smooth_silica_sandstone", () ->
            new Block(BlockBehaviour.Properties.copy(JNEBlocks.SILICA_SANDSTONE.get())));

    public static final RegistrySupplier<Block> SMOOTH_SILICA_SANDSTONE_SLAB = registerBlock("smooth_silica_sandstone_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(JNEBlocks.SILICA_SANDSTONE.get())));

    public static final RegistrySupplier<Block> SMOOTH_SILICA_SANDSTONE_STAIRS = registerBlock("smooth_silica_sandstone_stairs", () ->
            new JNEStairBlock(JNEBlocks.SMOOTH_SILICA_SANDSTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(JNEBlocks.SILICA_SANDSTONE.get())));

    // Nether Bricks

    public static final RegistrySupplier<Block> NETHER_BRICK_PILLAR = registerBlock("nether_brick_pillar", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)));

    public static final RegistrySupplier<Block> RED_MIXED_NETHER_BRICKS = registerBlock("red_mixed_nether_bricks", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)));

    // Blue Nether Bricks

    public static final RegistrySupplier<Block> BLUE_MIXED_NETHER_BRICKS = registerBlock("blue_mixed_nether_bricks", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)));

    public static final RegistrySupplier<Block> BLUE_NETHER_BRICKS = registerBlock("blue_nether_bricks", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.RED_NETHER_BRICKS).mapColor(MapColor.WARPED_WART_BLOCK)));

    public static final RegistrySupplier<Block> BLUE_NETHER_BRICK_SLAB = registerBlock("blue_nether_brick_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(JNEBlocks.BLUE_NETHER_BRICKS.get())));

    public static final RegistrySupplier<Block> BLUE_NETHER_BRICK_STAIRS = registerBlock("blue_nether_brick_stairs", () ->
            new JNEStairBlock(JNEBlocks.BLUE_NETHER_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(JNEBlocks.BLUE_NETHER_BRICKS.get())));

    public static final RegistrySupplier<Block> BLUE_NETHER_BRICK_WALL = registerBlock("blue_nether_brick_wall", () ->
            new WallBlock(BlockBehaviour.Properties.copy(JNEBlocks.BLUE_NETHER_BRICKS.get())));

    // Nether Pizza

    public static final RegistrySupplier<Block> NETHER_PIZZA = registerBlock("nether_pizza", () ->
            new NetherPizzaBlock(BlockBehaviour.Properties.of().strength(0.5f).noLootTable().noOcclusion().sound(SoundType.WOOL)));

    public static final RegistrySupplier<Block> WARPED_WART = registerBlockWithoutItem("warped_wart", () ->
            new WarpedWartBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WARPED_WART_BLOCK).instabreak().noCollission().noOcclusion().randomTicks().pushReaction(PushReaction.DESTROY).sound(SoundType.NETHER_WART)));

    // Forest Decorations

    public static final RegistrySupplier<Block> SHROOMNIGHT = registerBlock("shroomnight", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.SHROOMLIGHT).lightLevel((state) -> 8)));

    // CINDERSCAPES COMPATIBILITY
    public static final RegistrySupplier<Block> SHROOMBLIGHT = registerCompatBlock("shroomblight", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.SHROOMLIGHT).lightLevel((state) -> 12)), "cinderscapes");

    // GARDENS OF THE DEAD COMPATIBILITY
    public static final RegistrySupplier<Block> SHROOMFRIGHT = registerCompatBlock("shroomfright", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.SHROOMLIGHT).lightLevel((state) -> 10)), "gardens_of_the_dead");

    public static final RegistrySupplier<Block> NETHER_WART_BEARD = registerBlock("nether_wart_beard", () ->
            new BeardBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).instabreak().noCollission().sound(SoundType.WART_BLOCK)));

    public static final RegistrySupplier<Block> WARPED_WART_BEARD = registerBlock("warped_wart_beard", () ->
            new BeardBlock(BlockBehaviour.Properties.copy(JNEBlocks.NETHER_WART_BEARD.get())));

    // CINDERSCAPES COMPATIBILITY
    public static final RegistrySupplier<Block> UMBRAL_WART_BEARD = registerCompatBlock("umbral_wart_beard", () ->
            new BeardBlock(BlockBehaviour.Properties.copy(JNEBlocks.NETHER_WART_BEARD.get())), "cinderscapes");

    //TODO: ADD TWILIGHT VINES

    public static final RegistrySupplier<Block> WEEPING_IVY = registerBlock("weeping_ivy", () ->
            new IvyBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).instabreak().noCollission().sound(SoundType.WEEPING_VINES)));

    public static final RegistrySupplier<Block> TWISTING_IVY = registerBlock("twisting_ivy", () ->
            new IvyBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WARPED_WART_BLOCK).instabreak().noCollission().sound(SoundType.WEEPING_VINES)));

    // CINDERSCAPES COMPATIBILITY
    public static final RegistrySupplier<Block> TWILIGHT_IVY = registerCompatBlock("twilight_ivy", () ->
            new IvyBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).instabreak().noCollission().sound(SoundType.WEEPING_VINES)), "cinderscapes");

    public static final RegistrySupplier<Block> RED_SCALE_FUNGUS = registerBlock("red_scale_fungus", () ->
            new ScaleFungusBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).randomTicks().instabreak().noCollission().sound(SoundType.FUNGUS)));

    public static final RegistrySupplier<Block> BLUE_SCALE_FUNGUS = registerBlock("blue_scale_fungus", () ->
            new ScaleFungusBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).randomTicks().instabreak().noCollission().sound(SoundType.FUNGUS)));

    // CINDERSCAPES COMPATIBILITY
    public static final RegistrySupplier<Block> VIOLET_SCALE_FUNGUS = registerCompatBlock("violet_scale_fungus", () ->
            new ScaleFungusBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).randomTicks().instabreak().noCollission().sound(SoundType.FUNGUS)), "cinderscapes");

    // GARDENS OF THE DEAD COMPATIBILITY
    public static final RegistrySupplier<Block> YELLOW_SCALE_FUNGUS = registerCompatBlock("yellow_scale_fungus", () ->
            new ScaleFungusBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).randomTicks().instabreak().noCollission().sound(SoundType.FUNGUS)), "gardens_of_the_dead");

    public static final RegistrySupplier<Block> CRIMSON_SPROUTS = registerBlock("crimson_sprouts", () ->
            new NetherSproutsBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_SPROUTS)));

    // Path Blocks

    public static final RegistrySupplier<Block> SOUL_PATH = registerBlock("soul_path", () ->
            new SoulPathBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_SOIL)));

    public static final RegistrySupplier<Block> CRIMSON_NYLIUM_PATH = registerBlock("crimson_nylium_path", () ->
            new NyliumPathBlock(BlockBehaviour.Properties.copy(Blocks.NETHERRACK).sound(SoundType.NYLIUM)));

    public static final RegistrySupplier<Block> WARPED_NYLIUM_PATH = registerBlock("warped_nylium_path", () ->
            new NyliumPathBlock(BlockBehaviour.Properties.copy(Blocks.NETHERRACK).sound(SoundType.NYLIUM)));

    // CINDERSCAPES COMPATIBLITY
    public static final RegistrySupplier<Block> UMBRAL_NYLIUM_PATH = registerCompatBlock("umbral_nylium_path", () ->
            new NyliumPathBlock(BlockBehaviour.Properties.copy(Blocks.NETHERRACK).sound(SoundType.NYLIUM)), "cinderscapes");

    // Decayable Blocks

    public static final RegistrySupplier<Block> DECAYABLE_NETHER_WART_BLOCK = registerBlockWithoutItem("decayable_nether_wart_block", () ->
            new DecayableWartBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_WART_BLOCK).randomTicks(), 1, Blocks.NETHER_WART_BLOCK));

    public static final RegistrySupplier<Block> DECAYABLE_WARPED_WART_BLOCK = registerBlockWithoutItem("decayable_warped_wart_block", () ->
            new DecayableWartBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_WART_BLOCK).randomTicks(), 2, Blocks.WARPED_WART_BLOCK));

    public static final RegistrySupplier<Block> DECAYABLE_SHROOMLIGHT = registerBlockWithoutItem("decayable_shroomlight", () ->
            new DecayableShroomBlock(BlockBehaviour.Properties.copy(Blocks.SHROOMLIGHT).randomTicks(), 1, Blocks.SHROOMLIGHT));

    public static final RegistrySupplier<Block> DECAYABLE_SHROOMNIGHT = registerBlockWithoutItem("decayable_shroomnight", () ->
            new DecayableShroomBlock(BlockBehaviour.Properties.copy(JNEBlocks.SHROOMNIGHT.get()).randomTicks(), 2, JNEBlocks.SHROOMNIGHT.get()));

    // Particle Emitters

    public static final RegistrySupplier<Block> CRIMSON_SPORESHROOM = registerBlock("crimson_sporeshroom", () ->
            new SporeshroomBlock(BlockBehaviour.Properties.of().strength(0.5f).pushReaction(PushReaction.DESTROY).sound(SoundType.FUNGUS), 1, JNETags.Biomes.HAS_CRIMSON_SPORES));

    public static final RegistrySupplier<Block> WARPED_SPORESHROOM = registerBlock("warped_sporeshroom", () ->
            new SporeshroomBlock(BlockBehaviour.Properties.of().strength(0.5f).pushReaction(PushReaction.DESTROY).sound(SoundType.FUNGUS), 2, JNETags.Biomes.HAS_WARPED_SPORES));

    // CINDERSCAPES COMPATIBILITY
    public static final RegistrySupplier<Block> UMBRAL_SPORESHROOM = registerCompatBlock("umbral_sporeshroom", () ->
            new SporeshroomBlock(BlockBehaviour.Properties.of().strength(0.5f).pushReaction(PushReaction.DESTROY).sound(SoundType.FUNGUS), 3, JNETags.Biomes.HAS_WARPED_SPORES), "cinderscapes");

    // CINDERSCAPES COMPATIBILITY
    // TODO: Add Type and Particles to Soulblight Sporeshroom
    public static final RegistrySupplier<Block> SOULBLIGHT_SPORESHROOM = registerCompatBlock("soulblight_sporeshroom", () ->
            new SporeshroomBlock(BlockBehaviour.Properties.of().strength(0.5f).pushReaction(PushReaction.DESTROY).sound(SoundType.FUNGUS), 1, JNETags.Biomes.HAS_SOULBLIGHT_SPORES), "gardens_of_the_dead");

    public static final RegistrySupplier<Block> SOULED_GEYSER = registerBlock("souled_geyser", () ->
            new GeyserBlock(BlockBehaviour.Properties.copy(JNEBlocks.SOUL_SLATE.get()).pushReaction(PushReaction.DESTROY).sound(JNESoundType.SOUL_SLATE), 1, false, JNETags.Biomes.HAS_ASH));

    public static final RegistrySupplier<Block> BASALTIC_GEYSER = registerBlock("basaltic_geyser", () ->
            new GeyserBlock(BlockBehaviour.Properties.copy(Blocks.BASALT).pushReaction(PushReaction.DESTROY).sound(SoundType.BASALT), 2, true, JNETags.Biomes.HAS_WHITE_ASH));

    // CINDERSCAPES COMPATIBILITY
    public static final RegistrySupplier<Block> BLACKSTONIC_GEYSER = registerCompatBlock("blackstonic_geyser", () ->
            new GeyserBlock(BlockBehaviour.Properties.copy(Blocks.BLACKSTONE).pushReaction(PushReaction.DESTROY).sound(JNESoundType.SOUL_SLATE), 2, true, JNETags.Biomes.HAS_WHITE_ASH), "cinderscapes");

    // CINDERSCAPES COMPATIBILITY
    public static final RegistrySupplier<Block> ASHEN_GEYSER = registerCompatBlock("ashen_geyser", () ->
            new GeyserBlock(BlockBehaviour.Properties.copy(Blocks.NETHERRACK).pushReaction(PushReaction.DESTROY).sound(JNESoundType.SOUL_SLATE), 1, true, JNETags.Biomes.HAS_ASH), "cinderscapes");

    // White Ash

    public static final RegistrySupplier<Block> WHITE_ASH = registerBlock("white_ash", () ->
            new WhiteAshBlock(BlockBehaviour.Properties.of().strength(0.1f).requiresCorrectToolForDrops().replaceable().forceSolidOff().isViewBlocking(
                    ((state, world, pos) -> state.getValue(SoulSoilLayerBlock.LAYERS) >= 8))
                    .sound(JNESoundType.WHITE_ASH).mapColor(MapColor.COLOR_LIGHT_GRAY)));

    public static final RegistrySupplier<Block> WHITE_ASH_BLOCK = registerBlock("white_ash_block", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).strength(0.1f).requiresCorrectToolForDrops().sound(JNESoundType.WHITE_ASH)));

    // Bones

    public static final RegistrySupplier<Block> SKELETON_SKULL_CANDLE = registerBlock("skeleton_skull_candle", () ->
            new SkullCandleBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).lightLevel((state) -> 14), 1));

    public static final RegistrySupplier<Block> SOUL_SKELETON_SKULL_CANDLE = registerBlock("soul_skeleton_skull_candle", () ->
            new SkullCandleBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).lightLevel((state) -> 10), 2));

    public static final RegistrySupplier<Block> BONE_CORTICAL = registerBlock("bone_cortical", () ->
            new BoneBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)));

    public static final RegistrySupplier<Block> BONE_ROD = registerBlock("bone_rod", () ->
            new BoneRodBlock(BlockBehaviour.Properties.of().noCollission().strength(0.5f).sound(SoundType.BONE_BLOCK)));

    public static final RegistrySupplier<Block> BONE_FENCE = registerBlock("bone_fence", () ->
            new BoneFenceBlock(BlockBehaviour.Properties.of().strength(0.5f).sound(SoundType.BONE_BLOCK)));

    public static final RegistrySupplier<Block> SKULL_BLOCK = registerBlock("skull_block", () ->
            new JNEHorizontalDirectionalBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)));

    public static final RegistrySupplier<Block> BURNING_SKULL_BLOCK = registerBlock("burning_skull_block", () ->
            new JNEHorizontalDirectionalBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK).lightLevel((state) -> 15)));

    public static final RegistrySupplier<Block> SOUL_BURNING_SKULL_BLOCK = registerBlock("soul_burning_skull_block", () ->
            new JNEHorizontalDirectionalBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK).lightLevel((state) -> 10)));

    public static final RegistrySupplier<Block> STACKED_BONES = registerBlock("stacked_bones", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)));

    public static final RegistrySupplier<Block> STACKED_BONE_SLAB = registerBlock("stacked_bone_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)));

    public static final RegistrySupplier<Block> STACKED_BONE_STAIRS = registerBlock("stacked_bone_stairs", () ->
            new JNEStairBlock(JNEBlocks.STACKED_BONES.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)));

    // Wither Bone
    
    public static final RegistrySupplier<Block> WITHER_BONE_BLOCK = registerBlock("wither_bone_block", () ->
            new WitherBoneBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK).strength(4.5f, 9.0f)));

    public static final RegistrySupplier<Block> WITHER_SKULL_BLOCK = registerBlock("wither_skull_block", () ->
            new JNEHorizontalDirectionalBlock(BlockBehaviour.Properties.copy(JNEBlocks.WITHER_BONE_BLOCK.get())));

    public static final RegistrySupplier<Block> BURNING_WITHER_SKULL_BLOCK = registerBlock("burning_wither_skull_block", () ->
            new JNEHorizontalDirectionalBlock(BlockBehaviour.Properties.copy(JNEBlocks.WITHER_BONE_BLOCK.get()).lightLevel((state) -> 15)));

    public static final RegistrySupplier<Block> SOUL_BURNING_WITHER_SKULL_BLOCK = registerBlock("soul_burning_wither_skull_block", () ->
            new JNEHorizontalDirectionalBlock(BlockBehaviour.Properties.copy(JNEBlocks.WITHER_BONE_BLOCK.get()).lightLevel((state) -> 10)));

    public static final RegistrySupplier<Block> STACKED_WITHER_BONES = registerBlock("stacked_wither_bones", () ->
            new Block(BlockBehaviour.Properties.copy(JNEBlocks.WITHER_BONE_BLOCK.get())));

    public static final RegistrySupplier<Block> STACKED_WITHER_BONE_SLAB = registerBlock("stacked_wither_bone_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(JNEBlocks.WITHER_BONE_BLOCK.get())));

    public static final RegistrySupplier<Block> STACKED_WITHER_BONE_STAIRS = registerBlock("stacked_wither_bone_stairs", () ->
            new JNEStairBlock(JNEBlocks.STACKED_WITHER_BONES.get().defaultBlockState(), BlockBehaviour.Properties.copy(JNEBlocks.WITHER_BONE_BLOCK.get())));

    // Blackstone
    
    public static final RegistrySupplier<Block> POLISHED_BLACKSTONE_PILLAR = registerBlock("polished_blackstone_pillar", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE)));

    public static final RegistrySupplier<Block> POLISHED_BLACKSTONE_FENCE = registerBlock("polished_blackstone_fence", () ->
            new FenceBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE)));

    public static final RegistrySupplier<Block> WEEPING_POLISHED_BLACKSTONE_BRICKS = registerBlock("weeping_polished_blackstone_bricks", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE_BRICKS)));

    public static final RegistrySupplier<Block> TWISTING_POLISHED_BLACKSTONE_BRICKS = registerBlock("twisting_polished_blackstone_bricks", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE_BRICKS)));

    // Flower Pots

    //TODO: Add Flower Pots

    ////////////////
    // REGISTRIES //
    ////////////////

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
        // If The Specified ModId is not found, then the Block Item won't be registered
        if (Platform.isModLoaded(modId)) {
            registerBlockItem(name, toReturn);
        }
        return toReturn;
    }

    private static <T extends Block> RegistrySupplier<Item> registerBlockItem(String name, RegistrySupplier<T> block) {
        return JNEItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends Block> RegistrySupplier<T> registerFireProofBlock(String name, Supplier<T> block) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        registerFireProofBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistrySupplier<Item> registerFireProofBlockItem(String name, RegistrySupplier<T> block) {
        return JNEItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().fireResistant()));
    }

    public static void init() {
        BLOCKS.register();
    }
}
