package net.jadenxgamer.netherexp.registry.block;

import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.custom.*;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNEBlockSetType;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundType;
import net.jadenxgamer.netherexp.registry.misc_registry.JNEWoodType;
import net.minecraft.core.registries.Registries;
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

@SuppressWarnings("unused")
public class JNEBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(NetherExp.MOD_ID, Registries.BLOCK);

    ////////////
    // BLOCKS //
    ////////////

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
    //CINDERSCAPES COMPAT
    public static final RegistrySupplier<Block> SHALE_SWIRLS = registerCompatBlock("shale_swirls", () ->
            new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.copy(SOUL_SWIRLS.get())), "cinderscapes");

    // TODO Add Block
    public static final RegistrySupplier<Block> ECTO_SOUL_SAND = registerBlock("ecto_soul_sand", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.SOUL_SAND).lightLevel((state) -> 4).randomTicks()));

    // TODO Add Block
    public static final RegistrySupplier<Block> SUSPICIOUS_SOUL_SAND = registerBlock("suspicious_soul_sand", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.SOUL_SAND).speedFactor(0.2f).strength(0.25f).pushReaction(PushReaction.DESTROY).sound(JNESoundType.SUSPICIOUS_SOUL_SAND)));

    public static final RegistrySupplier<Block> SOUL_MAGMA_BLOCK = registerBlock("soul_magma_block", () ->
            new SoulMagmaBlock(BlockBehaviour.Properties.copy(Blocks.MAGMA_BLOCK).mapColor(MapColor.COLOR_LIGHT_BLUE).lightLevel((state) -> 4).sound(JNESoundType.SOUL_MAGMA_BLOCK)));

    public static final RegistrySupplier<Block> BLACK_ICE = registerBlock("black_ice", () ->
            new BlackIceBlock(BlockBehaviour.Properties.copy(Blocks.PACKED_ICE).mapColor(MapColor.COLOR_BLACK).randomTicks().requiresCorrectToolForDrops().strength(0.5f).lightLevel((state) -> 5).sound(JNESoundType.BLACK_ICE)));

    public static final RegistrySupplier<Block> FOSSIL_ORE = registerBlock("fossil_ore", () ->
            new FossilOreBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_SOIL).randomTicks().strength(0.6f)));

    public static final RegistrySupplier<Block> FOSSIL_FUEL_ORE = registerBlock("fossil_fuel_ore", () ->
            new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_SOIL).strength(0.6f), UniformInt.of(0, 2)));

    public static final RegistrySupplier<Block> DIAMOND_FOSSIL_ORE = registerBlock("diamond_fossil_ore", () ->
            new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_SOIL).strength(0.6f).requiresCorrectToolForDrops(), UniformInt.of(3, 7)));

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
        new CarvedSorrowsquashBlock(BlockBehaviour.Properties.copy(Blocks.JACK_O_LANTERN).mapColor(MapColor.COLOR_LIGHT_BLUE).strength(1.0f).lightLevel((state) -> 10)));

    public static final RegistrySupplier<Block> SORROWSQUASH_STEM = registerBlock("sorrowsquash_stem", () ->
            new VineStemBlock((StemGrownBlock) JNEBlocks.SORROWSQUASH.get(), () -> Items.PUMPKIN_SEEDS, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().instabreak().randomTicks().sound(SoundType.NETHER_WOOD)));

    public static final RegistrySupplier<Block> SORROWSQUASH_STEM_PLANT = registerBlock("sorrowsquash_stem_plant", () ->
            new VineStemPlantBlock((StemGrownBlock) JNEBlocks.SORROWSQUASH.get(), () -> Items.PUMPKIN_SEEDS, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().instabreak().sound(SoundType.NETHER_WOOD)));

    // SMOOTH NETHERRACK

    public static final RegistrySupplier<Block> SMOOTH_NETHERRACK = registerBlock("smooth_netherrack", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.NETHERRACK).strength(1.4f).requiresCorrectToolForDrops()));

    public static final RegistrySupplier<Block> SMOOTH_NETHERRACK_SLAB = registerBlock("smooth_netherrack_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(JNEBlocks.SMOOTH_NETHERRACK.get())));

    public static final RegistrySupplier<Block> SMOOTH_NETHERRACK_STAIRS = registerBlock("smooth_netherrack_stairs", () ->
            new JNEStairBlock(JNEBlocks.SMOOTH_NETHERRACK.get().defaultBlockState(), BlockBehaviour.Properties.copy(JNEBlocks.SMOOTH_NETHERRACK.get())));

    public static final RegistrySupplier<Block> SMOOTH_NETHERRACK_WALL = registerBlock("smooth_netherrack_wall", () ->
            new WallBlock(BlockBehaviour.Properties.copy(JNEBlocks.SMOOTH_NETHERRACK.get())));

    // NETHERRACK BRICKS

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

    // BASALT

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

    // NETHERITE

    public static final RegistrySupplier<Block> NETHERITE_PLATED_BLOCK = registerFireProofBlock("netherite_plated_block", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(1.0f, 1200.0f).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));

    public static final RegistrySupplier<Block> CUT_NETHERITE_BLOCK = registerFireProofBlock("cut_netherite_block", () ->
            new Block(BlockBehaviour.Properties.copy(JNEBlocks.NETHERITE_PLATED_BLOCK.get())));

    public static final RegistrySupplier<Block> CUT_NETHERITE_SLAB = registerFireProofBlock("cut_netherite_slab", () ->
            new Block(BlockBehaviour.Properties.copy(JNEBlocks.CUT_NETHERITE_BLOCK.get())));

    public static final RegistrySupplier<Block> CUT_NETHERITE_STAIRS = registerFireProofBlock("cut_netherite_stairs", () ->
            new JNEStairBlock(JNEBlocks.CUT_NETHERITE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(JNEBlocks.CUT_NETHERITE_BLOCK.get())));

    public static final RegistrySupplier<Block> CUT_NETHERITE_PILLAR = registerFireProofBlock("cut_netherite_pillar", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.copy(JNEBlocks.CUT_NETHERITE_BLOCK.get())));

    //TODO: Add Block
    public static final RegistrySupplier<Block> NETHERITE_GRATE = registerFireProofBlock("netherite_grate", () ->
            new Block(BlockBehaviour.Properties.copy(JNEBlocks.CUT_NETHERITE_BLOCK.get())));

    // RUSTY NETHERITE

    public static final RegistrySupplier<Block> RUSTY_NETHERITE_PLATED_BLOCK = registerFireProofBlock("rusty_netherite_plated_block", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(1.0f, 1200.0f).requiresCorrectToolForDrops().sound(SoundType.ANCIENT_DEBRIS)));

    public static final RegistrySupplier<Block> RUSTY_CUT_NETHERITE_BLOCK = registerFireProofBlock("rusty_cut_netherite_block", () ->
            new Block(BlockBehaviour.Properties.copy(JNEBlocks.RUSTY_NETHERITE_PLATED_BLOCK.get())));

    public static final RegistrySupplier<Block> RUSTY_CUT_NETHERITE_SLAB = registerFireProofBlock("rusty_cut_netherite_slab", () ->
            new Block(BlockBehaviour.Properties.copy(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get())));

    public static final RegistrySupplier<Block> RUSTY_CUT_NETHERITE_STAIRS = registerFireProofBlock("rusty_cut_netherite_stairs", () ->
            new JNEStairBlock(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(JNEBlocks.CUT_NETHERITE_BLOCK.get())));

    public static final RegistrySupplier<Block> RUSTY_CUT_NETHERITE_PILLAR = registerFireProofBlock("rusty_cut_netherite_pillar", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.copy(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get())));

    //TODO: Add Block
    public static final RegistrySupplier<Block> RUSTY_NETHERITE_GRATE = registerFireProofBlock("rusty_netherite_grate", () ->
            new Block(BlockBehaviour.Properties.copy(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get())));

    // ENIGMA

    public static final RegistrySupplier<Block> ENIGMA_FLESH = registerBlock("enigma_flesh", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).strength(1.8f).sound(SoundType.FUNGUS)));

    public static final RegistrySupplier<Block> STRANGE_ENIGMA_FLESH = registerBlock("strange_enigma_flesh", () ->
            new StrangeEnigmaFleshBlock(BlockBehaviour.Properties.copy(JNEBlocks.ENIGMA_FLESH.get()).mapColor(MapColor.DIAMOND).lightLevel((state) -> 12)));

    public static final RegistrySupplier<Block> ENIGMA_CROWN = registerBlock("enigma_crown", () ->
            new EnigmaCrownBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).strength(0.2f).sound(SoundType.FUNGUS)));

    public static final RegistrySupplier<Block> ENIGMA_SHELF = registerBlock("enigma_shelf", () ->
            new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).noCollission().instabreak().sound(SoundType.FUNGUS)));

    // CLARET

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

    // SMOKESTALK

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

    //

//    public static final RegistrySupplier<Block> = registerBlock("", () ->
//            )

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
