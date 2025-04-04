package net.jadenxgamer.netherexp.registry.block;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.custom.*;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.item.custom.GargoyleStatueItem;
import net.jadenxgamer.netherexp.registry.misc_registry.*;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.jadenxgamer.netherexp.util.CompatUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@SuppressWarnings({"unused", "deprecation"})
public class JNEBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, NetherExp.MOD_ID);

    ////////////
    // BLOCKS //
    ////////////

    // Soul Slate

    public static final RegistryObject<Block> SOUL_SLATE = registerBlock("soul_slate", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(4.0f).requiresCorrectToolForDrops().sound(JNESoundType.SOUL_SLATE)));

    public static final RegistryObject<Block> PALE_SOUL_SLATE = registerBlock("pale_soul_slate", () ->
            new Block(BlockBehaviour.Properties.copy(SOUL_SLATE.get()).strength(3.0f, 1.0f).mapColor(MapColor.TERRACOTTA_PINK)));

    public static final RegistryObject<Block> SOUL_SLATE_SLAB = registerBlock("soul_slate_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(SOUL_SLATE.get())));

    public static final RegistryObject<Block> SOUL_SLATE_STAIRS = registerBlock("soul_slate_stairs", () ->
            new StairBlock(JNEBlocks.SOUL_SLATE.get().defaultBlockState(), BlockBehaviour.Properties.copy(SOUL_SLATE.get())));

    public static final RegistryObject<Block> SOUL_SLATE_WALL = registerBlock("soul_slate_wall", () ->
            new WallBlock(BlockBehaviour.Properties.copy(SOUL_SLATE.get())));

    public static final RegistryObject<Block> SOUL_SLATE_BRICKS = registerBlock("soul_slate_bricks", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(2.0f).requiresCorrectToolForDrops().sound(JNESoundType.SOUL_SLATE_BRICKS)));

    public static final RegistryObject<Block> SOUL_SLATE_BRICK_SLAB = registerBlock("soul_slate_brick_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(SOUL_SLATE_BRICKS.get())));

    public static final RegistryObject<Block> SOUL_SLATE_BRICK_STAIRS = registerBlock("soul_slate_brick_stairs", () ->
            new StairBlock(JNEBlocks.SOUL_SLATE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(SOUL_SLATE_BRICKS.get())));

    public static final RegistryObject<Block> SOUL_SLATE_BRICK_WALL = registerBlock("soul_slate_brick_wall", () ->
            new WallBlock(BlockBehaviour.Properties.copy(SOUL_SLATE_BRICKS.get())));

    public static final RegistryObject<Block> ETCHED_SOUL_SLATE_BRICKS = registerBlock("etched_soul_slate_bricks", () ->
            new LightAbleBlock(BlockBehaviour.Properties.copy(SOUL_SLATE_BRICKS.get()).lightLevel(
                    blockState -> blockState.getValue(LightAbleBlock.LIT) ? 4 : 0)));

    public static final RegistryObject<Block> CRACKED_SOUL_SLATE_BRICKS = registerBlock("cracked_soul_slate_bricks", () ->
            new Block(BlockBehaviour.Properties.copy(SOUL_SLATE_BRICKS.get())));

    public static final RegistryObject<Block> SOUL_SLATE_BRICK_PILLAR = registerBlock("soul_slate_brick_pillar", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.copy(SOUL_SLATE_BRICKS.get())));

    public static final RegistryObject<Block> CHISELED_SOUL_SLATE_BRICKS = registerBlock("chiseled_soul_slate_bricks", () ->
            new LightAbleBlock(BlockBehaviour.Properties.copy(SOUL_SLATE_BRICKS.get()).lightLevel(
                    blockState -> blockState.getValue(LightAbleBlock.LIT) ? 7 : 0)));

    public static final RegistryObject<Block> SOUL_SLATE_TILES = registerBlock("soul_slate_tiles", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PINK).strength(2.0f).requiresCorrectToolForDrops().sound(JNESoundType.SOUL_SLATE_BRICKS)));

    public static final RegistryObject<Block> SOUL_SLATE_TILE_SLAB = registerBlock("soul_slate_tile_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(SOUL_SLATE_TILES.get())));

    public static final RegistryObject<Block> SOUL_SLATE_TILE_STAIRS = registerBlock("soul_slate_tile_stairs", () ->
            new StairBlock(JNEBlocks.SOUL_SLATE_TILES.get().defaultBlockState(), BlockBehaviour.Properties.copy(SOUL_SLATE_TILES.get())));

    public static final RegistryObject<Block> SOUL_SLATE_TILE_WALL = registerBlock("soul_slate_tile_wall", () ->
            new WallBlock(BlockBehaviour.Properties.copy(SOUL_SLATE_TILES.get())));

    public static final RegistryObject<Block> ETCHED_SOUL_SLATE_TILES = registerBlock("etched_soul_slate_tiles", () ->
            new LightAbleBlock(BlockBehaviour.Properties.copy(SOUL_SLATE_TILES.get()).lightLevel(
                    blockState -> blockState.getValue(LightAbleBlock.LIT) ? 4 : 0)));

    public static final RegistryObject<Block> CHISELED_SOUL_SLATE_TILES = registerBlock("chiseled_soul_slate_tiles", () ->
            new LightAbleBlock(BlockBehaviour.Properties.copy(SOUL_SLATE_TILES.get()).lightLevel(
                    blockState -> blockState.getValue(LightAbleBlock.LIT) ? 7 : 0)));

    // Sanctum

    public static final RegistryObject<Block> BRAZIER_CHEST = registerBlock("brazier_chest", () ->
            new BrazierChestBlock(BlockBehaviour.Properties.of().strength(120.0f, 1200.0f).lightLevel(
                    blockState -> blockState.getValue(BrazierChestBlock.LOCKED) ? 0 : 10).isRedstoneConductor((a, b, c) -> false).sound(JNESoundType.SOUL_SLATE)));

    public static final RegistryObject<Block> TREACHEROUS_CANDLE = registerBlock("treacherous_candle", () ->
            new TreacherousCandleBlock(BlockBehaviour.Properties.of().strength(120.0f, 1200.0f).noOcclusion().lightLevel(
                    blockState -> blockState.getValue(TreacherousCandleBlock.LIT) ? 10 : 0).sound(JNESoundType.SOUL_SLATE)));

    public static final RegistryObject<Block> HAZE_BLOCK = registerBlockWithoutItem("haze_block", () ->
            new HazeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instabreak().noOcclusion().sound(JNESoundType.HAZE_BLOCK)));

    public static final RegistryObject<Block> SCULK_GRINDER = registerBlock("sculk_grinder", () ->
            new SculkGrinderBlock(BlockBehaviour.Properties.of().strength(80.0f, 1200.0f).lightLevel(state -> 13).sound(SoundType.SCULK_CATALYST)));

    // Soul Decorations

    public static final RegistryObject<Block> SOUL_CANDLE = registerBlock("soul_candle", () ->
            new SoulCandleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noOcclusion().strength(1.0f).lightLevel(SoulCandleBlock.STATE_TO_LUMINANCE).sound(JNESoundType.SOUL_CANDLE)));

    public static final RegistryObject<Block> SOUL_GLASS = registerBlock("soul_glass", () ->
            new SoulGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).lightLevel(
                    blockState -> blockState.getValue(SoulGlassBlock.LIT) ? 12 : 0).strength(0.3f, 1200.0f).sound(SoundType.GLASS)));

    public static final RegistryObject<Block> DISCERNMENT_GLASS = registerBlock("discernment_glass", () ->
            new DiscernmentGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).strength(0.3f, 1200.0f).sound(SoundType.GLASS)));

    public static final RegistryObject<Block> OCHRE_FROGMIST = registerBlock("ochre_frogmist", () ->
            new FrogmistBlock(BlockBehaviour.Properties.of().noCollission().pushReaction(PushReaction.DESTROY).instabreak().requiresCorrectToolForDrops().noOcclusion().noParticlesOnBreak().sound(JNESoundType.FROGMIST)));

    public static final RegistryObject<Block> VERDANT_FROGMIST = registerBlock("verdant_frogmist", () ->
            new FrogmistBlock(BlockBehaviour.Properties.of().noCollission().pushReaction(PushReaction.DESTROY).instabreak().requiresCorrectToolForDrops().noOcclusion().noParticlesOnBreak().sound(JNESoundType.FROGMIST)));

    public static final RegistryObject<Block> PEARLESCENT_FROGMIST = registerBlock("pearlescent_frogmist", () ->
            new FrogmistBlock(BlockBehaviour.Properties.of().noCollission().pushReaction(PushReaction.DESTROY).instabreak().requiresCorrectToolForDrops().noOcclusion().noParticlesOnBreak().sound(JNESoundType.FROGMIST)));

    public static final RegistryObject<Block> SOUL_SWIRLS = registerBlock("soul_swirls", () ->
            new SwirlsBlock(7, 3, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).replaceable().noCollission().instabreak()
                    .lightLevel(state -> state.getValue(SwirlsBlock.COOLDOWN) ? 6 : 0).sound(SoundType.NETHER_SPROUTS), JNEParticleTypes.SWIRL_POP));

    public static final RegistryObject<Block> WRAITHING_LESION = registerBlock("wraithing_lesion", () ->
            new WraithingLesionBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(1.5f, 1.0f).pushReaction(PushReaction.DESTROY).randomTicks().sound(SoundType.MUD)));

    public static final RegistryObject<Block> ECTO_SOUL_SAND = registerBlock("ecto_soul_sand", () ->
            new EctoSoulSandBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_SAND).lightLevel((state) -> 4).randomTicks()));

    public static final RegistryObject<Block> SUSPICIOUS_SOUL_SAND = registerBlock("suspicious_soul_sand", () ->
            new JNEBrushableBlock(Blocks.SOUL_SAND, BlockBehaviour.Properties.copy(Blocks.SOUL_SAND).speedFactor(0.2f).strength(0.25f).randomTicks().pushReaction(PushReaction.DESTROY).sound(JNESoundType.SUSPICIOUS_SOUL_SAND), SoundEvents.BRUSH_SAND, JNESoundEvents.BRUSH_BRUSHING_SOUL_SAND_COMPLETE.get()));

    public static final RegistryObject<Block> SOUL_MAGMA_BLOCK = registerBlock("soul_magma_block", () ->
            new SoulMagmaBlock(BlockBehaviour.Properties.copy(Blocks.MAGMA_BLOCK).mapColor(MapColor.COLOR_LIGHT_BLUE).sound(JNESoundType.SOUL_MAGMA_BLOCK)));

    public static final RegistryObject<Block> BLACK_ICE = registerBlock("black_ice", () ->
            new BlackIceBlock(BlockBehaviour.Properties.copy(Blocks.PACKED_ICE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(0.3f).lightLevel((state) -> 2).sound(JNESoundType.BLACK_ICE)));

    public static final RegistryObject<Block> BLACK_ICICLE = registerBlock("black_icicle", () ->
            new BlackIcicleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().forceSolidOn().noOcclusion().randomTicks().strength(0.1F, 3.0F).dynamicShape().offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY).sound(JNESoundType.BLACK_ICE)));

    public static final RegistryObject<Block> THIN_BLACK_ICE = registerBlock("thin_black_ice", () ->
            new ThinBlackIceBlock(BlockBehaviour.Properties.copy(BLACK_ICE.get()).strength(0.05f).noOcclusion().sound(JNESoundType.BLACK_ICE)));

    public static final RegistryObject<Block> SOUL_PERMAFROST = registerBlock("soul_permafrost", () ->
            new SoulPermafrostBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_SOIL).speedFactor(0.9F).friction(0.98F)));

    public static final RegistryObject<Block> FOSSIL_ORE = registerBlock("fossil_ore", () ->
            new FossilOreBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_SOIL).randomTicks().strength(0.6f)));

    public static final RegistryObject<Block> FOSSIL_FUEL_ORE = registerBlock("fossil_fuel_ore", () ->
            new JNEOreBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_SOIL).strength(0.6f), UniformInt.of(0, 2), 0));

    public static final RegistryObject<Block> SOUL_TORCHFLOWER = registerBlock("soul_torchflower", () ->
            new NetherFlowerBlock(MobEffects.DIG_SPEED, 5, BlockBehaviour.Properties.copy(Blocks.TORCHFLOWER)));

    public static final RegistryObject<Block> SOUL_TORCHFLOWER_CROP = registerBlockWithoutItem("soul_torchflower_crop", () ->
            new SoulTorchflowerCropBlock(BlockBehaviour.Properties.copy(Blocks.TORCHFLOWER_CROP)));

    public static final RegistryObject<Block> CEREBRAGE_SKULL = registerBlockWithoutItem("cerebrage_skull", () ->
            new CerebrageBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).randomTicks()));

    public static final RegistryObject<Block> SORROWEED = registerBlock("sorroweed", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5f).sound(SoundType.MOSS)));

    public static final RegistryObject<Block> SORROWSQUASH = registerBlock("sorrowsquash", () ->
            new SorrowsquashBlock(BlockBehaviour.Properties.copy(Blocks.PUMPKIN).mapColor(MapColor.COLOR_LIGHT_GRAY).strength(1.0f).sound(SoundType.NETHER_WOOD)));

    public static final RegistryObject<Block> CARVED_SORROWSQUASH = registerBlock("carved_sorrowsquash", () ->
        new CarvedSorrowsquashBlock(BlockBehaviour.Properties.copy(Blocks.CARVED_PUMPKIN).mapColor(MapColor.COLOR_LIGHT_GRAY).strength(1.0f).sound(SoundType.NETHER_WOOD)));

    public static final RegistryObject<Block> GHOUL_O_LANTERN = registerBlock("ghoul_o_lantern", () ->
        new CarvedSorrowsquashBlock(BlockBehaviour.Properties.copy(Blocks.JACK_O_LANTERN).mapColor(MapColor.COLOR_ORANGE).strength(1.0f).lightLevel((state) -> 10).sound(SoundType.NETHER_WOOD)));

    public static final RegistryObject<Block> SORROWSQUASH_STEM = registerBlockWithoutItem("sorrowsquash_stem", () ->
            new VineStemBlock((StemGrownBlock) JNEBlocks.SORROWSQUASH.get(), () -> Items.PUMPKIN_SEEDS, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().instabreak().randomTicks().sound(SoundType.NETHER_WOOD)));

    public static final RegistryObject<Block> SORROWSQUASH_STEM_PLANT = registerBlockWithoutItem("sorrowsquash_stem_plant", () ->
            new VineStemPlantBlock((StemGrownBlock) JNEBlocks.SORROWSQUASH.get(), () -> Items.PUMPKIN_SEEDS, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().instabreak().sound(SoundType.NETHER_WOOD)));

    public static final RegistryObject<Block> SOUL_SOIL_LAYER = registerBlock("soul_soil_layer", () ->
            new SoulSoilLayerBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_SOIL).strength(0.1f).requiresCorrectToolForDrops().replaceable().forceSolidOff().isViewBlocking(
                    ((state, world, pos) -> state.getValue(SoulSoilLayerBlock.LAYERS) >= 8))
            ));

    public static final RegistryObject<Block> SHOTGUN_BARREL = registerBlock("shotgun_barrel", () ->
        new ShotgunBarrelBlock(BlockBehaviour.Properties.of().strength(3.5f).requiresCorrectToolForDrops().sound(SoundType.COPPER)));

    // Ancient Fire
    public static final RegistryObject<Block> ANCIENT_WAX_BLOCK = registerBlock("ancient_wax_block", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.HONEYCOMB_BLOCK)));

    public static final RegistryObject<Block> ANCIENT_FIRE = registerBlockWithoutItem("ancient_fire", () ->
            new AncientFireBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_FIRE).mapColor(MapColor.COLOR_RED).lightLevel(state -> 13), 0));

    public static final RegistryObject<Block> ANCIENT_TORCH = registerBlockWithoutItem("ancient_torch", () ->
            new JNETorchBlock(BlockBehaviour.Properties.copy(Blocks.TORCH).mapColor(MapColor.COLOR_RED).lightLevel(state -> 13), JNEParticleTypes.TREACHEROUS_FLAME));

    public static final RegistryObject<Block> ANCIENT_WALL_TORCH = registerBlockWithoutItem("ancient_wall_torch", () ->
            new JNEWallTorchBlock(BlockBehaviour.Properties.copy(Blocks.WALL_TORCH).mapColor(MapColor.COLOR_RED).dropsLike(JNEBlocks.ANCIENT_TORCH.get()).lightLevel(state -> 13), JNEParticleTypes.TREACHEROUS_FLAME));

    public static final RegistryObject<Block> ANCIENT_LANTERN = registerBlock("ancient_lantern", () ->
            new LanternBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_LANTERN).mapColor(MapColor.COLOR_RED).lightLevel(state -> 13)));

    public static final RegistryObject<Block> ANCIENT_CAMPFIRE = registerBlock("ancient_campfire", () ->
            new AncientCampfireBlock(false, 0, BlockBehaviour.Properties.copy(Blocks.SOUL_CAMPFIRE).mapColor(MapColor.COLOR_RED).lightLevel(state -> 13)));

    public static final RegistryObject<Block> ANCIENT_CANDLE = registerBlock("ancient_candle", () ->
            new AncientCandleBlock(BlockBehaviour.Properties.copy(JNEBlocks.SOUL_CANDLE.get()).mapColor(MapColor.COLOR_RED).lightLevel(state -> state.getValue(AncientCandleBlock.LIT) ? 7 : 0)));

    // Netherrack

    public static final RegistryObject<Block> SMOOTH_NETHERRACK = registerBlock("smooth_netherrack", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.NETHERRACK).strength(1.4f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> SMOOTH_NETHERRACK_SLAB = registerBlock("smooth_netherrack_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(JNEBlocks.SMOOTH_NETHERRACK.get())));

    public static final RegistryObject<Block> SMOOTH_NETHERRACK_STAIRS = registerBlock("smooth_netherrack_stairs", () ->
            new JNEStairBlock(JNEBlocks.SMOOTH_NETHERRACK.get().defaultBlockState(), BlockBehaviour.Properties.copy(JNEBlocks.SMOOTH_NETHERRACK.get())));

    public static final RegistryObject<Block> SMOOTH_NETHERRACK_WALL = registerBlock("smooth_netherrack_wall", () ->
            new WallBlock(BlockBehaviour.Properties.copy(JNEBlocks.SMOOTH_NETHERRACK.get())));

    public static final RegistryObject<Block> NETHERRACK_BRICKS = registerBlock("netherrack_bricks", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.NETHERRACK).strength(1.4f).requiresCorrectToolForDrops().sound(JNESoundType.NETHERRACK_BRICKS)));

    public static final RegistryObject<Block> NETHERRACK_BRICK_SLAB = registerBlock("netherrack_brick_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(JNEBlocks.NETHERRACK_BRICKS.get())));

    public static final RegistryObject<Block> NETHERRACK_BRICK_STAIRS = registerBlock("netherrack_brick_stairs", () ->
            new JNEStairBlock(JNEBlocks.NETHERRACK_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(JNEBlocks.NETHERRACK_BRICKS.get())));

    public static final RegistryObject<Block> NETHERRACK_BRICK_WALL = registerBlock("netherrack_brick_wall", () ->
            new WallBlock(BlockBehaviour.Properties.copy(JNEBlocks.NETHERRACK_BRICKS.get())));

    public static final RegistryObject<Block> NETHERRACK_TILES = registerBlock("netherrack_tiles", () ->
            new Block(BlockBehaviour.Properties.copy(JNEBlocks.NETHERRACK_BRICKS.get())));

    public static final RegistryObject<Block> NETHERRACK_PILLAR = registerBlock("netherrack_pillar", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.copy(JNEBlocks.NETHERRACK_BRICKS.get())));

    // Basalt

    public static final RegistryObject<Block> BASALT_SLAB = registerBlock("basalt_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(Blocks.BASALT)));

    public static final RegistryObject<Block> BASALT_STAIRS = registerBlock("basalt_stairs", () ->
            new JNEStairBlock(Blocks.BASALT.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.BASALT)));

    public static final RegistryObject<Block> BASALT_WALL = registerBlock("basalt_wall", () ->
            new WallBlock(BlockBehaviour.Properties.copy(Blocks.BASALT)));

    public static final RegistryObject<Block> POLISHED_BASALT_SLAB = registerBlock("polished_basalt_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BASALT)));

    public static final RegistryObject<Block> POLISHED_BASALT_STAIRS = registerBlock("polished_basalt_stairs", () ->
            new JNEStairBlock(Blocks.POLISHED_BASALT.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.POLISHED_BASALT)));

    public static final RegistryObject<Block> POLISHED_BASALT_WALL = registerBlock("polished_basalt_wall", () ->
            new WallBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BASALT)));

    public static final RegistryObject<Block> POLISHED_BASALT_BRICKS = registerBlock("polished_basalt_bricks", () ->
            new PolishedBasaltBrickBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BASALT).sound(JNESoundType.BASALT_BRICKS)));

    public static final RegistryObject<Block> POLISHED_BASALT_BRICK_SLAB = registerBlock("polished_basalt_brick_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(JNEBlocks.POLISHED_BASALT_BRICKS.get())));

    public static final RegistryObject<Block> POLISHED_BASALT_BRICK_STAIRS = registerBlock("polished_basalt_brick_stairs", () ->
            new JNEStairBlock(JNEBlocks.POLISHED_BASALT_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(JNEBlocks.POLISHED_BASALT_BRICKS.get())));

    public static final RegistryObject<Block> POLISHED_BASALT_BRICK_WALL = registerBlock("polished_basalt_brick_wall", () ->
            new WallBlock(BlockBehaviour.Properties.copy(JNEBlocks.POLISHED_BASALT_BRICKS.get())));

    // Netherite

    public static final RegistryObject<Block> NETHERITE_PLATED_BLOCK = registerItemPropertiesBlock("netherite_plated_block", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(0.5f, 0.5f).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)), new Item.Properties().fireResistant());

    public static final RegistryObject<Block> CUT_NETHERITE_BLOCK = registerItemPropertiesBlock("cut_netherite_block", () ->
            new Block(BlockBehaviour.Properties.copy(JNEBlocks.NETHERITE_PLATED_BLOCK.get())), new Item.Properties().fireResistant());

    public static final RegistryObject<Block> CUT_NETHERITE_SLAB = registerItemPropertiesBlock("cut_netherite_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(JNEBlocks.CUT_NETHERITE_BLOCK.get())), new Item.Properties().fireResistant());

    public static final RegistryObject<Block> CUT_NETHERITE_STAIRS = registerItemPropertiesBlock("cut_netherite_stairs", () ->
            new JNEStairBlock(JNEBlocks.CUT_NETHERITE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(JNEBlocks.CUT_NETHERITE_BLOCK.get())), new Item.Properties().fireResistant());

    public static final RegistryObject<Block> CUT_NETHERITE_PILLAR = registerItemPropertiesBlock("cut_netherite_pillar", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.copy(JNEBlocks.CUT_NETHERITE_BLOCK.get())), new Item.Properties().fireResistant());

    public static final RegistryObject<Block> NETHERITE_GRATE = registerItemPropertiesBlock("netherite_grate", () ->
            new LiquidloggedGrateBlock(BlockBehaviour.Properties.copy(JNEBlocks.CUT_NETHERITE_BLOCK.get()).noOcclusion().lightLevel(LiquidloggedGrateBlock::getLuminance)), new Item.Properties().fireResistant());

    // Rusty Netherite

    public static final RegistryObject<Block> RUSTY_NETHERITE_PLATED_BLOCK = registerItemPropertiesBlock("rusty_netherite_plated_block", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(0.5f, 0.5f).requiresCorrectToolForDrops().sound(SoundType.ANCIENT_DEBRIS)), new Item.Properties().fireResistant());

    public static final RegistryObject<Block> RUSTY_CUT_NETHERITE_BLOCK = registerItemPropertiesBlock("rusty_cut_netherite_block", () ->
            new Block(BlockBehaviour.Properties.copy(JNEBlocks.RUSTY_NETHERITE_PLATED_BLOCK.get())), new Item.Properties().fireResistant());

    public static final RegistryObject<Block> RUSTY_CUT_NETHERITE_SLAB = registerItemPropertiesBlock("rusty_cut_netherite_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get())), new Item.Properties().fireResistant());

    public static final RegistryObject<Block> RUSTY_CUT_NETHERITE_STAIRS = registerItemPropertiesBlock("rusty_cut_netherite_stairs", () ->
            new JNEStairBlock(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get())), new Item.Properties().fireResistant());

    public static final RegistryObject<Block> RUSTY_CUT_NETHERITE_PILLAR = registerItemPropertiesBlock("rusty_cut_netherite_pillar", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.copy(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get())), new Item.Properties().fireResistant());

    public static final RegistryObject<Block> RUSTY_NETHERITE_GRATE = registerItemPropertiesBlock("rusty_netherite_grate", () ->
            new LiquidloggedGrateBlock(BlockBehaviour.Properties.copy(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get()).noOcclusion().lightLevel(LiquidloggedGrateBlock::getLuminance)), new Item.Properties().fireResistant());

    // Enigma

    public static final RegistryObject<Block> ENIGMA_FLESH = registerBlock("enigma_flesh", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).strength(1.8f).sound(SoundType.FUNGUS)));

    public static final RegistryObject<Block> STRANGE_ENIGMA_FLESH = registerBlock("strange_enigma_flesh", () ->
            new StrangeEnigmaFleshBlock(BlockBehaviour.Properties.copy(JNEBlocks.ENIGMA_FLESH.get()).mapColor(MapColor.DIAMOND).lightLevel((state) -> 12)));

    public static final RegistryObject<Block> ENIGMA_CROWN = registerBlock("enigma_crown", () ->
            new EnigmaCrownBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).strength(0.2f).sound(SoundType.FUNGUS)));

    public static final RegistryObject<Block> ENIGMA_SHELF = registerBlock("enigma_shelf", () ->
            new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).noCollission().instabreak().sound(SoundType.FUNGUS)));

    // Claret

    public static final RegistryObject<Block> CEREBRAGE_CLARET_STEM = registerBlock("cerebrage_claret_stem", () ->
            new LogBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_STEM).mapColor(MapColor.CRIMSON_HYPHAE).sound(JNESoundType.CEREBRAGE_STEM), JNEBlocks.STRIPPED_CLARET_STEM));

    public static final RegistryObject<Block> CEREBRAGE_CLARET_HYPHAE = registerBlock("cerebrage_claret_hyphae", () ->
            new LogBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_HYPHAE).mapColor(MapColor.CRIMSON_HYPHAE).sound(JNESoundType.CEREBRAGE_STEM), JNEBlocks.STRIPPED_CLARET_HYPHAE));

    public static final RegistryObject<Block> STRIPPED_CLARET_STEM = registerBlock("stripped_claret_stem", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_WARPED_STEM).mapColor(MapColor.NETHER).sound(JNESoundType.CEREBRAGE_STEM)));

    public static final RegistryObject<Block> STRIPPED_CLARET_HYPHAE = registerBlock("stripped_claret_hyphae", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_WARPED_HYPHAE).mapColor(MapColor.NETHER).sound(JNESoundType.CEREBRAGE_STEM)));

    public static final RegistryObject<Block> CLARET_PLANKS = registerBlock("claret_planks", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.WARPED_PLANKS).mapColor(MapColor.NETHER)));

    public static final RegistryObject<Block> CLARET_SLAB = registerBlock("claret_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_SLAB).mapColor(MapColor.NETHER)));

    public static final RegistryObject<Block> CLARET_STAIRS = registerBlock("claret_stairs", () ->
            new StairBlock(JNEBlocks.CLARET_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.WARPED_STAIRS).mapColor(MapColor.NETHER)));

    public static final RegistryObject<Block> CLARET_FENCE = registerBlock("claret_fence", () ->
            new FenceBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_FENCE).mapColor(MapColor.NETHER)));

    public static final RegistryObject<Block> CLARET_FENCE_GATE = registerBlock("claret_fence_gate", () ->
            new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_FENCE_GATE).mapColor(MapColor.NETHER), WoodType.CRIMSON));

    public static final RegistryObject<Block> CLARET_DOOR = registerBlock("claret_door", () ->
            new DoorBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_DOOR).mapColor(MapColor.NETHER), BlockSetType.CRIMSON));

    public static final RegistryObject<Block> CLARET_TRAPDOOR = registerBlock("claret_trapdoor", () ->
            new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_TRAPDOOR).mapColor(MapColor.NETHER), BlockSetType.CRIMSON));

    public static final RegistryObject<Block> CLARET_BUTTON = registerBlock("claret_button", () ->
            new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_BUTTON).mapColor(MapColor.NETHER), BlockSetType.CRIMSON, 30, true));

    public static final RegistryObject<Block> CLARET_PRESSURE_PLATE = registerBlock("claret_pressure_plate", () ->
            new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.WARPED_PRESSURE_PLATE).mapColor(MapColor.NETHER), BlockSetType.CRIMSON));

    public static final RegistryObject<Block> CLARET_SIGN = registerBlockWithoutItem("claret_sign", () ->
            new StandingSignBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_SIGN).mapColor(MapColor.NETHER), JNEWoodType.CLARET));

    public static final RegistryObject<Block> CLARET_WALL_SIGN = registerBlockWithoutItem("claret_wall_sign", () ->
            new WallSignBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_WALL_SIGN).mapColor(MapColor.NETHER).dropsLike(JNEBlocks.CLARET_SIGN.get()), JNEWoodType.CLARET));

    public static final RegistryObject<Block> CLARET_HANGING_SIGN = registerBlockWithoutItem("claret_hanging_sign", () ->
            new CeilingHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_HANGING_SIGN).mapColor(MapColor.NETHER), JNEWoodType.CLARET));

    public static final RegistryObject<Block> CLARET_WALL_HANGING_SIGN = registerBlockWithoutItem("claret_wall_hanging_sign", () ->
            new WallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_WALL_HANGING_SIGN).mapColor(MapColor.NETHER).dropsLike(JNEBlocks.CLARET_HANGING_SIGN.get()), JNEWoodType.CLARET));

    // EXHAUST MIRE

    public static final RegistryObject<Block> MAGMA_CREAM_BLOCK = registerBlock("magma_cream_block", () ->
            new MagmaCreamBlock(BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.PUSH_ONLY).sound(SoundType.HONEY_BLOCK)));

    public static final RegistryObject<Block> IGNEOUS_REEDS = registerBlock("igneous_reeds", () ->
            new IgneousReeds(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLUE).noCollission().instabreak().offsetType(BlockBehaviour.OffsetType.XZ).sound(JNESoundType.SMOKESTALK)));

    public static final RegistryObject<Block> SMOKESTALK = registerBlock("smokestalk", () ->
            new SmokestalkBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLUE).randomTicks().strength(0.5f).sound(JNESoundType.SMOKESTALK)));

    public static final RegistryObject<Block> SMOKESTALK_PLANT = registerBlockWithoutItem("smokestalk_plant", () ->
            new SmokestalkPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLUE).strength(0.5f).sound(JNESoundType.SMOKESTALK)));

    // Smokestalk

    public static final RegistryObject<Block> SMOKESTALK_BLOCK = registerBlock("smokestalk_block", () ->
            new LogBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_STEM).mapColor(MapColor.TERRACOTTA_CYAN).sound(JNESoundType.SMOKESTALK_WOOD), JNEBlocks.STRIPPED_SMOKESTALK_BLOCK));

    public static final RegistryObject<Block> STRIPPED_SMOKESTALK_BLOCK = registerBlock("stripped_smokestalk_block", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_WARPED_STEM).mapColor(MapColor.TERRACOTTA_GRAY).sound(JNESoundType.SMOKESTALK_WOOD)));

    public static final RegistryObject<Block> SMOKESTALK_PLANKS = registerBlock("smokestalk_planks", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.WARPED_PLANKS).mapColor(MapColor.TERRACOTTA_GRAY).sound(JNESoundType.SMOKESTALK_WOOD)));

    public static final RegistryObject<Block> SMOKESTALK_SLAB = registerBlock("smokestalk_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_SLAB).mapColor(MapColor.TERRACOTTA_GRAY).sound(JNESoundType.SMOKESTALK_WOOD)));

    public static final RegistryObject<Block> SMOKESTALK_STAIRS = registerBlock("smokestalk_stairs", () ->
            new StairBlock(JNEBlocks.SMOKESTALK_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.WARPED_STAIRS).mapColor(MapColor.TERRACOTTA_GRAY).sound(JNESoundType.SMOKESTALK_WOOD)));

    public static final RegistryObject<Block> SMOKESTALK_FENCE = registerBlock("smokestalk_fence", () ->
            new FenceBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_FENCE).mapColor(MapColor.TERRACOTTA_GRAY).sound(JNESoundType.SMOKESTALK_WOOD)));

    public static final RegistryObject<Block> SMOKESTALK_FENCE_GATE = registerBlock("smokestalk_fence_gate", () ->
            new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_FENCE_GATE).mapColor(MapColor.TERRACOTTA_GRAY).sound(JNESoundType.SMOKESTALK_WOOD), JNEWoodType.SMOKESTALK));

    public static final RegistryObject<Block> SMOKESTALK_DOOR = registerBlock("smokestalk_door", () ->
            new DoorBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_DOOR).mapColor(MapColor.TERRACOTTA_GRAY).sound(JNESoundType.SMOKESTALK_WOOD), JNEBlockSetType.SMOKESTALK));

    public static final RegistryObject<Block> SMOKESTALK_TRAPDOOR = registerBlock("smokestalk_trapdoor", () ->
            new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_TRAPDOOR).mapColor(MapColor.TERRACOTTA_GRAY).sound(JNESoundType.SMOKESTALK_WOOD), JNEBlockSetType.SMOKESTALK));

    public static final RegistryObject<Block> SMOKESTALK_BUTTON = registerBlock("smokestalk_button", () ->
            new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_BUTTON).mapColor(MapColor.TERRACOTTA_GRAY).sound(JNESoundType.SMOKESTALK_WOOD), JNEBlockSetType.SMOKESTALK, 30, true));

    public static final RegistryObject<Block> SMOKESTALK_PRESSURE_PLATE = registerBlock("smokestalk_pressure_plate", () ->
            new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.WARPED_PRESSURE_PLATE).mapColor(MapColor.TERRACOTTA_GRAY).sound(JNESoundType.SMOKESTALK_WOOD), JNEBlockSetType.SMOKESTALK));

    public static final RegistryObject<Block> SMOKESTALK_SIGN = registerBlockWithoutItem("smokestalk_sign", () ->
            new StandingSignBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_SIGN).mapColor(MapColor.TERRACOTTA_GRAY).sound(JNESoundType.SMOKESTALK_WOOD), JNEWoodType.SMOKESTALK));

    public static final RegistryObject<Block> SMOKESTALK_WALL_SIGN = registerBlockWithoutItem("smokestalk_wall_sign", () ->
            new WallSignBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_WALL_SIGN).mapColor(MapColor.TERRACOTTA_GRAY).dropsLike(JNEBlocks.SMOKESTALK_SIGN.get()).sound(JNESoundType.SMOKESTALK_WOOD), JNEWoodType.SMOKESTALK));

    public static final RegistryObject<Block> SMOKESTALK_HANGING_SIGN = registerBlockWithoutItem("smokestalk_hanging_sign", () ->
            new CeilingHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_HANGING_SIGN).mapColor(MapColor.TERRACOTTA_GRAY).sound(JNESoundType.SMOKESTALK_WOOD), JNEWoodType.SMOKESTALK));

    public static final RegistryObject<Block> SMOKESTALK_WALL_HANGING_SIGN = registerBlockWithoutItem("smokestalk_wall_hanging_sign", () ->
            new WallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_WALL_HANGING_SIGN).mapColor(MapColor.TERRACOTTA_GRAY).dropsLike(JNEBlocks.SMOKESTALK_HANGING_SIGN.get()).sound(JNESoundType.SMOKESTALK_WOOD), JNEWoodType.SMOKESTALK));

    // Quartz Blocks

    public static final RegistryObject<Block> QUARTZ_CRYSTAL = registerBlock("quartz_crystal", () ->
            new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ).noOcclusion().strength(1.5f).sound(JNESoundType.QUARTZ_BLOCK)));

    public static final RegistryObject<Block> QUARTZ_CRYSTAL_BLOCK = registerBlock("quartz_crystal_block", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ).strength(2.5f).requiresCorrectToolForDrops().sound(JNESoundType.QUARTZ_BLOCK)));

    public static final RegistryObject<Block> CRACKED_QUARTZ_BRICKS = registerBlock("cracked_quartz_bricks", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK)));

    public static final RegistryObject<Block> CHISELED_QUARTZ_PILLAR = registerBlock("chiseled_quartz_pillar", () ->
            new JNEDirectionalBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_PILLAR)));

    // Silica Sand

    public static final RegistryObject<Block> SILICA_SAND = registerBlock("silica_sand", () ->
            new SandBlock(8812140, BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).strength(0.4f).sound(SoundType.SAND)));

    public static final RegistryObject<Block> SILICA_SANDSTONE = registerBlock("silica_sandstone", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).strength(1.0f, 6.0f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> SILICA_SANDSTONE_SLAB = registerBlock("silica_sandstone_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(JNEBlocks.SILICA_SANDSTONE.get())));

    public static final RegistryObject<Block> SILICA_SANDSTONE_STAIRS = registerBlock("silica_sandstone_stairs", () ->
            new JNEStairBlock(JNEBlocks.SILICA_SANDSTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(JNEBlocks.SILICA_SANDSTONE.get())));

    public static final RegistryObject<Block> SILICA_SANDSTONE_WALL = registerBlock("silica_sandstone_wall", () ->
            new WallBlock(BlockBehaviour.Properties.copy(JNEBlocks.SILICA_SANDSTONE.get())));

    public static final RegistryObject<Block> CUT_SILICA_SANDSTONE = registerBlock("cut_silica_sandstone", () ->
            new Block(BlockBehaviour.Properties.copy(JNEBlocks.SILICA_SANDSTONE.get())));

    public static final RegistryObject<Block> CUT_SILICA_SANDSTONE_SLAB = registerBlock("cut_silica_sandstone_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(JNEBlocks.SILICA_SANDSTONE.get())));

    public static final RegistryObject<Block> CHISELED_SILICA_SANDSTONE = registerBlock("chiseled_silica_sandstone", () ->
            new Block(BlockBehaviour.Properties.copy(JNEBlocks.SILICA_SANDSTONE.get())));

    public static final RegistryObject<Block> SMOOTH_SILICA_SANDSTONE = registerBlock("smooth_silica_sandstone", () ->
            new Block(BlockBehaviour.Properties.copy(JNEBlocks.SILICA_SANDSTONE.get())));

    public static final RegistryObject<Block> SMOOTH_SILICA_SANDSTONE_SLAB = registerBlock("smooth_silica_sandstone_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(JNEBlocks.SILICA_SANDSTONE.get())));

    public static final RegistryObject<Block> SMOOTH_SILICA_SANDSTONE_STAIRS = registerBlock("smooth_silica_sandstone_stairs", () ->
            new JNEStairBlock(JNEBlocks.SMOOTH_SILICA_SANDSTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(JNEBlocks.SILICA_SANDSTONE.get())));

    // Nether Bricks

    public static final RegistryObject<Block> NETHER_BRICK_PILLAR = registerBlock("nether_brick_pillar", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)));

    public static final RegistryObject<Block> RED_MIXED_NETHER_BRICKS = registerBlock("red_mixed_nether_bricks", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)));

    // Blue Nether Bricks

    public static final RegistryObject<Block> BLUE_MIXED_NETHER_BRICKS = registerBlock("blue_mixed_nether_bricks", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)));

    public static final RegistryObject<Block> BLUE_NETHER_BRICKS = registerBlock("blue_nether_bricks", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.RED_NETHER_BRICKS).mapColor(MapColor.WARPED_WART_BLOCK)));

    public static final RegistryObject<Block> BLUE_NETHER_BRICK_SLAB = registerBlock("blue_nether_brick_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(JNEBlocks.BLUE_NETHER_BRICKS.get())));

    public static final RegistryObject<Block> BLUE_NETHER_BRICK_STAIRS = registerBlock("blue_nether_brick_stairs", () ->
            new JNEStairBlock(JNEBlocks.BLUE_NETHER_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(JNEBlocks.BLUE_NETHER_BRICKS.get())));

    public static final RegistryObject<Block> BLUE_NETHER_BRICK_WALL = registerBlock("blue_nether_brick_wall", () ->
            new WallBlock(BlockBehaviour.Properties.copy(JNEBlocks.BLUE_NETHER_BRICKS.get())));

    // Nether Pizza

    public static final RegistryObject<Block> NETHER_PIZZA = registerBlock("nether_pizza", () ->
            new NetherPizzaBlock(BlockBehaviour.Properties.of().strength(0.5f).noLootTable().noOcclusion().sound(SoundType.WOOL)));

    public static final RegistryObject<Block> WARPED_WART = registerBlockWithoutItem("warped_wart", () ->
            new WarpedWartBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WARPED_WART_BLOCK).instabreak().noCollission().noOcclusion().randomTicks().pushReaction(PushReaction.DESTROY).sound(SoundType.NETHER_WART)));

    // Forest Decorations

    public static final RegistryObject<Block> SHROOMNIGHT = registerBlock("shroomnight", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.SHROOMLIGHT).lightLevel((state) -> 8)));

    public static final RegistryObject<Block> NETHER_WART_BEARD = registerBlock("nether_wart_beard", () ->
            new BeardBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).instabreak().noCollission().sound(SoundType.WART_BLOCK)));

    public static final RegistryObject<Block> WARPED_WART_BEARD = registerBlock("warped_wart_beard", () ->
            new BeardBlock(BlockBehaviour.Properties.copy(JNEBlocks.NETHER_WART_BEARD.get())));

    public static final RegistryObject<Block> WEEPING_IVY = registerBlock("weeping_ivy", () ->
            new HelixIvyBlock(JNEItems.LIGHTSPORES, JNEParticleTypes.FALLING_SHROOMLIGHT, JNEItems.WEEPING_HELIX, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).instabreak().noCollission().sound(SoundType.WEEPING_VINES)));

    public static final RegistryObject<Block> TWISTING_IVY = registerBlock("twisting_ivy", () ->
            new HelixIvyBlock(JNEItems.NIGHTSPORES, JNEParticleTypes.FALLING_SHROOMNIGHT, JNEItems.TWISTING_HELIX, BlockBehaviour.Properties.of().mapColor(MapColor.WARPED_WART_BLOCK).instabreak().noCollission().sound(SoundType.WEEPING_VINES)));

    public static final RegistryObject<Block> RED_SCALE_FUNGUS = registerBlock("red_scale_fungus", () ->
            new ScaleFungusBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).randomTicks().instabreak().noCollission().sound(SoundType.FUNGUS)));

    public static final RegistryObject<Block> BLUE_SCALE_FUNGUS = registerBlock("blue_scale_fungus", () ->
            new ScaleFungusBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).randomTicks().instabreak().noCollission().sound(SoundType.FUNGUS)));

    public static final RegistryObject<Block> CRIMSON_SPROUTS = registerBlock("crimson_sprouts", () ->
            new NetherSproutsBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_SPROUTS)));

    // Path Blocks

    public static final RegistryObject<Block> SOUL_PATH = registerBlock("soul_path", () ->
            new SoulPathBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_SOIL)));

    public static final RegistryObject<Block> CRIMSON_NYLIUM_PATH = registerBlock("crimson_nylium_path", () ->
            new NyliumPathBlock(BlockBehaviour.Properties.copy(Blocks.NETHERRACK).sound(SoundType.NYLIUM)));

    public static final RegistryObject<Block> WARPED_NYLIUM_PATH = registerBlock("warped_nylium_path", () ->
            new NyliumPathBlock(BlockBehaviour.Properties.copy(Blocks.NETHERRACK).sound(SoundType.NYLIUM)));

    // Decayable Blocks

    public static final RegistryObject<Block> DECAYABLE_NETHER_WART_BLOCK = registerBlockWithoutItem("decayable_nether_wart_block", () ->
            new DecayableWartBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_WART_BLOCK).randomTicks(), 1, Blocks.NETHER_WART_BLOCK));

    public static final RegistryObject<Block> DECAYABLE_WARPED_WART_BLOCK = registerBlockWithoutItem("decayable_warped_wart_block", () ->
            new DecayableWartBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_WART_BLOCK).randomTicks(), 2, Blocks.WARPED_WART_BLOCK));

    public static final RegistryObject<Block> DECAYABLE_SHROOMLIGHT = registerBlockWithoutItem("decayable_shroomlight", () ->
            new DecayableShroomBlock(BlockBehaviour.Properties.copy(Blocks.SHROOMLIGHT).randomTicks(), 1, Blocks.SHROOMLIGHT));

    public static final RegistryObject<Block> DECAYABLE_SHROOMNIGHT = registerBlockWithoutItem("decayable_shroomnight", () ->
            new DecayableShroomBlock(BlockBehaviour.Properties.copy(JNEBlocks.SHROOMNIGHT.get()).randomTicks(), 2, JNEBlocks.SHROOMNIGHT.get()));

    // Particle Emitters

    public static final RegistryObject<Block> CRIMSON_SPORESHROOM = registerBlock("crimson_sporeshroom", () ->
            new SporeshroomBlock(BlockBehaviour.Properties.of().strength(0.5f).pushReaction(PushReaction.DESTROY).sound(SoundType.FUNGUS), () -> ParticleTypes.CRIMSON_SPORE, JNEParticleTypes.CRIMSON_SMOG, JNETags.Biomes.HAS_CRIMSON_SPORES));

    public static final RegistryObject<Block> WARPED_SPORESHROOM = registerBlock("warped_sporeshroom", () ->
            new SporeshroomBlock(BlockBehaviour.Properties.of().strength(0.5f).pushReaction(PushReaction.DESTROY).sound(SoundType.FUNGUS), () -> ParticleTypes.WARPED_SPORE, JNEParticleTypes.WARPED_SMOG, JNETags.Biomes.HAS_WARPED_SPORES));

    public static final RegistryObject<Block> SOULED_GEYSER = registerBlock("souled_geyser", () ->
            new GeyserBlock(BlockBehaviour.Properties.copy(JNEBlocks.SOUL_SLATE.get()).pushReaction(PushReaction.DESTROY).sound(JNESoundType.SOUL_SLATE), () -> ParticleTypes.ASH, JNEParticleTypes.BLACK_SMOKE, false, JNETags.Biomes.HAS_ASH));

    public static final RegistryObject<Block> BASALTIC_GEYSER = registerBlock("basaltic_geyser", () ->
            new GeyserBlock(BlockBehaviour.Properties.copy(Blocks.BASALT).pushReaction(PushReaction.DESTROY).sound(SoundType.BASALT), () -> ParticleTypes.WHITE_ASH, JNEParticleTypes.WHITE_SMOKE, true, JNETags.Biomes.HAS_WHITE_ASH));

    // Bones

    public static final RegistryObject<Block> SKELETON_SKULL_CANDLE = registerBlock("skeleton_skull_candle", () ->
            new SkullCandleBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).lightLevel((state) -> 14), () -> ParticleTypes.SMALL_FLAME));

    public static final RegistryObject<Block> SOUL_SKELETON_SKULL_CANDLE = registerBlock("soul_skeleton_skull_candle", () ->
            new SkullCandleBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).lightLevel((state) -> 10), JNEParticleTypes.SMALL_SOUL_FIRE_FLAME));

    public static final RegistryObject<Block> ANCIENT_SKELETON_SKULL_CANDLE = registerBlock("ancient_skeleton_skull_candle", () ->
            new SkullCandleBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).lightLevel((state) -> 13), JNEParticleTypes.TREACHEROUS_FLAME));

    public static final RegistryObject<Block> BONE_CORTICAL = registerBlock("bone_cortical", () ->
            new BoneBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)));

    public static final RegistryObject<Block> BONE_ROD = registerBlock("bone_rod", () ->
            new BoneRodBlock(BlockBehaviour.Properties.of().noCollission().strength(0.5f).sound(SoundType.BONE_BLOCK)));

    public static final RegistryObject<Block> BONE_FENCE = registerBlock("bone_fence", () ->
            new BoneFenceBlock(BlockBehaviour.Properties.of().strength(0.5f).sound(SoundType.BONE_BLOCK)));

    public static final RegistryObject<Block> SKULL_BLOCK = registerBlock("skull_block", () ->
            new JNEHorizontalDirectionalBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)));

    public static final RegistryObject<Block> BURNING_SKULL_BLOCK = registerBlock("burning_skull_block", () ->
            new JNEHorizontalDirectionalBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK).lightLevel((state) -> 15)));

    public static final RegistryObject<Block> SOUL_BURNING_SKULL_BLOCK = registerBlock("soul_burning_skull_block", () ->
            new JNEHorizontalDirectionalBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK).lightLevel((state) -> 10)));

    public static final RegistryObject<Block> ANCIENT_BURNING_SKULL_BLOCK = registerBlock("ancient_burning_skull_block", () ->
            new JNEHorizontalDirectionalBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK).lightLevel((state) -> 13)));

    public static final RegistryObject<Block> STACKED_BONES = registerBlock("stacked_bones", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)));

    public static final RegistryObject<Block> STACKED_BONE_SLAB = registerBlock("stacked_bone_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)));

    public static final RegistryObject<Block> STACKED_BONE_STAIRS = registerBlock("stacked_bone_stairs", () ->
            new JNEStairBlock(JNEBlocks.STACKED_BONES.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)));

    // Wither Bone

    public static final RegistryObject<Block> WITHER_BONE_BLOCK = registerBlock("wither_bone_block", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK).strength(4.5f, 9.0f)));

    public static final RegistryObject<Block> WITHER_SKULL_BLOCK = registerBlock("wither_skull_block", () ->
            new JNEHorizontalDirectionalBlock(BlockBehaviour.Properties.copy(JNEBlocks.WITHER_BONE_BLOCK.get())));

    public static final RegistryObject<Block> BURNING_WITHER_SKULL_BLOCK = registerBlock("burning_wither_skull_block", () ->
            new JNEHorizontalDirectionalBlock(BlockBehaviour.Properties.copy(JNEBlocks.WITHER_BONE_BLOCK.get()).lightLevel((state) -> 15)));

    public static final RegistryObject<Block> SOUL_BURNING_WITHER_SKULL_BLOCK = registerBlock("soul_burning_wither_skull_block", () ->
            new JNEHorizontalDirectionalBlock(BlockBehaviour.Properties.copy(JNEBlocks.WITHER_BONE_BLOCK.get()).lightLevel((state) -> 10)));

    public static final RegistryObject<Block> ANCIENT_BURNING_WITHER_SKULL_BLOCK = registerBlock("ancient_burning_wither_skull_block", () ->
            new JNEHorizontalDirectionalBlock(BlockBehaviour.Properties.copy(JNEBlocks.WITHER_BONE_BLOCK.get()).lightLevel((state) -> 13)));

    public static final RegistryObject<Block> STACKED_WITHER_BONES = registerBlock("stacked_wither_bones", () ->
            new Block(BlockBehaviour.Properties.copy(JNEBlocks.WITHER_BONE_BLOCK.get())));

    public static final RegistryObject<Block> STACKED_WITHER_BONE_SLAB = registerBlock("stacked_wither_bone_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(JNEBlocks.WITHER_BONE_BLOCK.get())));

    public static final RegistryObject<Block> STACKED_WITHER_BONE_STAIRS = registerBlock("stacked_wither_bone_stairs", () ->
            new JNEStairBlock(JNEBlocks.STACKED_WITHER_BONES.get().defaultBlockState(), BlockBehaviour.Properties.copy(JNEBlocks.WITHER_BONE_BLOCK.get())));

    // Blackstone

    public static final RegistryObject<Block> POLISHED_BLACKSTONE_PILLAR = registerBlock("polished_blackstone_pillar", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE)));

    public static final RegistryObject<Block> POLISHED_BLACKSTONE_FENCE = registerBlock("polished_blackstone_fence", () ->
            new FenceBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE)));

    public static final RegistryObject<Block> WEEPING_POLISHED_BLACKSTONE_BRICKS = registerBlock("weeping_polished_blackstone_bricks", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE_BRICKS)));

    public static final RegistryObject<Block> TWISTING_POLISHED_BLACKSTONE_BRICKS = registerBlock("twisting_polished_blackstone_bricks", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE_BRICKS)));

    // GARGOYLE STATUES

    public static final RegistryObject<Block> OSSIFIED_GARGOYLE_STATUE = registerGargoyleBlock("ossified_gargoyle_statue", () ->
            new GargoyleStatueBlock(BlockBehaviour.Properties.of().noOcclusion().strength(1.0f, 5.0f).sound(JNESoundType.SOUL_SLATE)));

    public static final RegistryObject<Block> TRAMPLE_GARGOYLE_STATUE = registerGargoyleBlock("trample_gargoyle_statue", () ->
            new GargoyleStatueBlock(BlockBehaviour.Properties.of().noOcclusion().strength(1.0f, 5.0f).sound(JNESoundType.SOUL_SLATE)));

    public static final RegistryObject<Block> PHASE_GARGOYLE_STATUE = registerGargoyleBlock("phase_gargoyle_statue", () ->
            new GargoyleStatueBlock(BlockBehaviour.Properties.of().noOcclusion().strength(1.0f, 5.0f).sound(JNESoundType.SOUL_SLATE)));

    public static final RegistryObject<Block> GHOUL_GARGOYLE_STATUE = registerGargoyleBlock("ghoul_gargoyle_statue", () ->
            new GargoyleStatueBlock(BlockBehaviour.Properties.of().noOcclusion().strength(1.0f, 5.0f).sound(JNESoundType.SOUL_SLATE)));

    public static final RegistryObject<Block> WRETCHED_GARGOYLE_STATUE = registerGargoyleBlock("wretched_gargoyle_statue", () ->
            new GargoyleStatueBlock(BlockBehaviour.Properties.of().noOcclusion().strength(1.0f, 5.0f).sound(JNESoundType.SOUL_SLATE)));

    public static final RegistryObject<Block> TREACHEROUS_GARGOYLE_STATUE = registerGargoyleBlock("treacherous_gargoyle_statue", () ->
            new GargoyleStatueBlock(BlockBehaviour.Properties.of().noOcclusion().strength(1.0f, 5.0f).sound(JNESoundType.SOUL_SLATE)));

    public static final RegistryObject<Block> CIRRIPEDIA_GARGOYLE_STATUE = registerGargoyleBlock("cirripedia_gargoyle_statue", () ->
            new GargoyleStatueBlock(BlockBehaviour.Properties.of().noOcclusion().strength(1.0f, 5.0f).sound(JNESoundType.SOUL_SLATE)));

    public static final RegistryObject<Block> OCCULT_GARGOYLE_STATUE = registerGargoyleBlock("occult_gargoyle_statue", () ->
            new GargoyleStatueBlock(BlockBehaviour.Properties.of().noOcclusion().strength(1.0f, 5.0f).sound(JNESoundType.SOUL_SLATE)));

    public static final RegistryObject<Block> SEALED_GARGOYLE_STATUE = registerGargoyleBlock("sealed_gargoyle_statue", () ->
            new GargoyleStatueBlock(BlockBehaviour.Properties.of().noOcclusion().strength(1.0f, 5.0f).sound(JNESoundType.SOUL_SLATE)));

    public static final RegistryObject<Block> OBFUSCATED_GARGOYLE_STATUE = registerGargoyleBlock("obfuscated_gargoyle_statue", () ->
            new GargoyleStatueBlock(BlockBehaviour.Properties.of().noOcclusion().strength(1.0f, 5.0f).sound(JNESoundType.SOUL_SLATE)));

    public static final RegistryObject<Block> INSCRIBED_PANEL = registerBlock("inscribed_panel", () ->
            new InscribedPanelBlock(BlockBehaviour.Properties.of().noOcclusion().strength(1.0f, 5.0f).sound(JNESoundType.SOUL_SLATE).lightLevel(InscribedPanelBlock.STATE_TO_LUMINANCE)));

    // Flower Pots
    public static final RegistryObject<Block> POTTED_SOUL_SWIRLS = registerBlockWithoutItem("potted_soul_swirls", () ->
            new FlowerPotBlock(SOUL_SWIRLS.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> POTTED_ENIGMA_CROWN = registerBlockWithoutItem("potted_enigma_crown", () ->
            new FlowerPotBlock(ENIGMA_CROWN.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> POTTED_SMOKESTALK = registerBlockWithoutItem("potted_smokestalk", () ->
            new FlowerPotBlock(SMOKESTALK.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> POTTED_RED_SCALE_FUNGUS = registerBlockWithoutItem("potted_red_scale_fungus", () ->
            new FlowerPotBlock(RED_SCALE_FUNGUS.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> POTTED_BLUE_SCALE_FUNGUS = registerBlockWithoutItem("potted_blue_scale_fungus", () ->
            new FlowerPotBlock(BLUE_SCALE_FUNGUS.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> POTTED_CRIMSON_SPORESHROOM = registerBlockWithoutItem("potted_crimson_sporeshroom", () ->
            new FlowerPotBlock(CRIMSON_SPORESHROOM.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> POTTED_WARPED_SPORESHROOM = registerBlockWithoutItem("potted_warped_sporeshroom", () ->
            new FlowerPotBlock(WARPED_SPORESHROOM.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> POTTED_SOUL_TORCHFLOWER = registerBlockWithoutItem("potted_soul_torchflower", () ->
            new FlowerPotBlock(SOUL_TORCHFLOWER.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));

    // Mod Compatibility

    /**
     * Cinderscapes
     */

    public static final RegistryObject<Block> SHROOMBRIGHT = registerCompatBlock("shroombright", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.SHROOMLIGHT).lightLevel((state) -> 14)), CompatUtil.CINDERSCAPES);

    public static final RegistryObject<Block> UMBRAL_SPORESHROOM = registerCompatBlock("umbral_sporeshroom", () ->
            new SporeshroomBlock(BlockBehaviour.Properties.of().strength(0.5f).pushReaction(PushReaction.DESTROY).sound(SoundType.FUNGUS), () -> ParticleTypes.WARPED_SPORE, JNEParticleTypes.UMBRAL_SMOG, JNETags.Biomes.HAS_WARPED_SPORES), CompatUtil.CINDERSCAPES);

    public static final RegistryObject<Block> BLACKSTONIC_GEYSER = registerCompatBlock("blackstonic_geyser", () ->
            new GeyserBlock(BlockBehaviour.Properties.copy(Blocks.BLACKSTONE).pushReaction(PushReaction.DESTROY).sound(JNESoundType.SOUL_SLATE), () -> ParticleTypes.WHITE_ASH, JNEParticleTypes.WHITE_SMOKE, true, JNETags.Biomes.HAS_WHITE_ASH), CompatUtil.CINDERSCAPES);

    public static final RegistryObject<Block> ASHEN_GEYSER = registerCompatBlock("ashen_geyser", () ->
            new GeyserBlock(BlockBehaviour.Properties.copy(Blocks.NETHERRACK).pushReaction(PushReaction.DESTROY).sound(JNESoundType.SOUL_SLATE), () -> ParticleTypes.ASH, JNEParticleTypes.BLACK_SMOKE, true, JNETags.Biomes.HAS_ASH), CompatUtil.CINDERSCAPES);

    public static final RegistryObject<Block> UMBRAL_WART_BEARD = registerCompatBlock("umbral_wart_beard", () ->
            new BeardBlock(BlockBehaviour.Properties.copy(JNEBlocks.NETHER_WART_BEARD.get())), CompatUtil.CINDERSCAPES);

    public static final RegistryObject<Block> TWILIGHT_IVY = registerCompatBlock("twilight_ivy", () ->
            new IvyBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).instabreak().noCollission().sound(SoundType.WEEPING_VINES)), CompatUtil.CINDERSCAPES);

    public static final RegistryObject<Block> VIOLET_SCALE_FUNGUS = registerCompatBlock("violet_scale_fungus", () ->
            new ScaleFungusBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).randomTicks().instabreak().noCollission().sound(SoundType.FUNGUS)), CompatUtil.CINDERSCAPES);

    public static final RegistryObject<Block> UMBRAL_NYLIUM_PATH = registerCompatBlock("umbral_nylium_path", () ->
            new NyliumPathBlock(BlockBehaviour.Properties.copy(Blocks.NETHERRACK).sound(SoundType.NYLIUM)), CompatUtil.CINDERSCAPES);

    public static final RegistryObject<Block> SHALE_SWIRLS = registerCompatBlock("shale_swirls", () ->
            new SwirlsBlock(7, 3, BlockBehaviour.Properties.copy(SOUL_SWIRLS.get()), JNEParticleTypes.SHALE_SWIRL_POP), CompatUtil.CINDERSCAPES);

    /**
     * Gardens of The Dead
     */
    public static final RegistryObject<Block> SHROOMBLIGHT = registerCompatBlock("shroomblight", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.SHROOMLIGHT).lightLevel((state) -> 10)), CompatUtil.GARDENS_OF_THE_DEAD);

    public static final RegistryObject<Block> SOULBLIGHT_SPORESHROOM = registerCompatBlock("soulblight_sporeshroom", () ->
            new CompatSporeshroomBlock(BlockBehaviour.Properties.of().strength(0.5f).pushReaction(PushReaction.DESTROY).sound(SoundType.FUNGUS), new ResourceLocation(CompatUtil.GARDENS_OF_THE_DEAD, "soulblight_spore"), JNEParticleTypes.SOULBLIGHT_SMOG, JNETags.Biomes.HAS_SOULBLIGHT_SPORES), CompatUtil.GARDENS_OF_THE_DEAD);

    public static final RegistryObject<Block> BLIGHT_SWIRLS = registerCompatBlock("blight_swirls", () ->
            new SwirlsBlock(7, 3, BlockBehaviour.Properties.copy(SOUL_SWIRLS.get()), JNEParticleTypes.BLIGHT_SWIRL_POP), CompatUtil.GARDENS_OF_THE_DEAD);

    public static final RegistryObject<Block> BLIGHTWART = registerBlockWithoutItem("blightwart", () ->
            new WallGrowingWartBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_WART), JNEItems.BLIGHTWART));

    public static final RegistryObject<Block> BLIGHTWART_BEARD = registerCompatBlock("blightwart_beard", () ->
            new BeardBlock(BlockBehaviour.Properties.copy(WARPED_WART_BEARD.get())), CompatUtil.GARDENS_OF_THE_DEAD);

    public static final RegistryObject<Block> YELLOW_MIXED_NETHER_BRICKS = registerCompatBlock("yellow_mixed_nether_bricks", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)), CompatUtil.GARDENS_OF_THE_DEAD);

    public static final RegistryObject<Block> YELLOW_NETHER_BRICKS = registerCompatBlock("yellow_nether_bricks", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.RED_NETHER_BRICKS).mapColor(MapColor.TERRACOTTA_YELLOW)), CompatUtil.GARDENS_OF_THE_DEAD);

    public static final RegistryObject<Block> YELLOW_NETHER_BRICK_SLAB = registerCompatBlock("yellow_nether_brick_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(JNEBlocks.YELLOW_NETHER_BRICKS.get())), CompatUtil.GARDENS_OF_THE_DEAD);

    public static final RegistryObject<Block> YELLOW_NETHER_BRICK_STAIRS = registerCompatBlock("yellow_nether_brick_stairs", () ->
            new JNEStairBlock(JNEBlocks.YELLOW_NETHER_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(JNEBlocks.YELLOW_NETHER_BRICKS.get())), CompatUtil.GARDENS_OF_THE_DEAD);

    public static final RegistryObject<Block> YELLOW_NETHER_BRICK_WALL = registerCompatBlock("yellow_nether_brick_wall", () ->
            new WallBlock(BlockBehaviour.Properties.copy(JNEBlocks.YELLOW_NETHER_BRICKS.get())), CompatUtil.GARDENS_OF_THE_DEAD);

    /**
     * Rubinated Nether
     */
    public static final RegistryObject<Block> SOUL_RUBY_ORE = registerCompatBlock("soul_ruby_ore", () ->
            new DropExperienceBlock(BlockBehaviour.Properties.copy(JNEBlocks.SOUL_MAGMA_BLOCK.get()).requiresCorrectToolForDrops(), UniformInt.of(3, 6)), CompatUtil.RUBINATED_NETHER);


    /**
     * Caverns & Chasms
     */

    public static final RegistryObject<Block> NECROMIUM_PLATED_BLOCK = registerItemPropertiesCompatBlock("necromium_plated_block", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GREEN).strength(0.5f, 0.5f).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)), new Item.Properties().fireResistant(), CompatUtil.CAVERNS_AND_CHASMS);

    public static final RegistryObject<Block> CUT_NECROMIUM_BLOCK = registerItemPropertiesCompatBlock("cut_necromium_block", () ->
            new Block(BlockBehaviour.Properties.copy(JNEBlocks.NECROMIUM_PLATED_BLOCK.get())), new Item.Properties().fireResistant(), CompatUtil.CAVERNS_AND_CHASMS);

    public static final RegistryObject<Block> CUT_NECROMIUM_SLAB = registerItemPropertiesCompatBlock("cut_necromium_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(JNEBlocks.CUT_NECROMIUM_BLOCK.get())), new Item.Properties().fireResistant(), CompatUtil.CAVERNS_AND_CHASMS);

    public static final RegistryObject<Block> CUT_NECROMIUM_STAIRS = registerItemPropertiesCompatBlock("cut_necromium_stairs", () ->
            new JNEStairBlock(JNEBlocks.CUT_NECROMIUM_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(JNEBlocks.CUT_NECROMIUM_BLOCK.get())), new Item.Properties().fireResistant(), CompatUtil.CAVERNS_AND_CHASMS);

    public static final RegistryObject<Block> CUT_NECROMIUM_PILLAR = registerItemPropertiesCompatBlock("cut_necromium_pillar", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.copy(JNEBlocks.CUT_NECROMIUM_BLOCK.get())), new Item.Properties().fireResistant(), CompatUtil.CAVERNS_AND_CHASMS);

    public static final RegistryObject<Block> NECROMIUM_GRATE = registerItemPropertiesCompatBlock("necromium_grate", () ->
            new LiquidloggedGrateBlock(BlockBehaviour.Properties.copy(JNEBlocks.CUT_NECROMIUM_BLOCK.get()).noOcclusion().lightLevel(LiquidloggedGrateBlock::getLuminance)), new Item.Properties().fireResistant(), CompatUtil.CAVERNS_AND_CHASMS);

    /**
     * Alex's Caves
     */

    public static final RegistryObject<Block> CARMINE_FROGMIST = registerCompatBlock("carmine_frogmist", () ->
            new FrogmistBlock(BlockBehaviour.Properties.of().noCollission().pushReaction(PushReaction.DESTROY).instabreak().requiresCorrectToolForDrops().noOcclusion().noParticlesOnBreak().sound(JNESoundType.FROGMIST)), CompatUtil.ALEXS_CAVES);

    /**
     * Oreganized
     */

    public static final RegistryObject<Block> GROOVED_BLACK_ICE = registerCompatBlock("grooved_black_ice", () ->
            new BlackIceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.CHIME).requiresCorrectToolForDrops().strength(0.3f).lightLevel((state) -> 2).sound(JNESoundType.BLACK_ICE)), CompatUtil.OREGANIZED);

    ////////////////
    // REGISTRIES //
    ////////////////

    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        JNEItems.ITEMS.register(name, () -> new BlockItem(toReturn.get(), new Item.Properties()));
        return toReturn;
    }
    public static <T extends Block> RegistryObject<T> registerGargoyleBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        JNEItems.ITEMS.register(name, () -> new GargoyleStatueItem(toReturn.get(), new Item.Properties()));
        return toReturn;
    }

    public static <T extends Block> RegistryObject<T> registerBlockWithoutItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    public static <T extends Block> RegistryObject<T> registerCompatBlock(String name, Supplier<T> block, String modId) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        // If The Specified ModId is not found, then the Block Item won't be registered
        if (FMLLoader.getLoadingModList().getModFileById(modId) != null) {
            JNEItems.ITEMS.register(name, () -> new BlockItem(toReturn.get(), new Item.Properties()));
        }
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerItemPropertiesBlock(String name, Supplier<T> block, Item.Properties properties) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        JNEItems.ITEMS.register(name, () -> new BlockItem(toReturn.get(), properties));
        return toReturn;
    }

    public static <T extends Block> RegistryObject<T> registerItemPropertiesCompatBlock(String name, Supplier<T> block, Item.Properties properties, String modId) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        // If The Specified ModId is not found, then the Block Item won't be registered
        if (FMLLoader.getLoadingModList().getModFileById(modId) != null) {
            JNEItems.ITEMS.register(name, () -> new BlockItem(toReturn.get(), properties));
        }
        return toReturn;
    }

    public static void init(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
