package net.jadenxgamer.netherexp.registry;

import net.jadenxgamer.elysium_api.api.util.LookupRegistryHelper;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.util.RegistryHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static net.jadenxgamer.netherexp.util.RegistryHelper.*;

public class JNECreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, NetherExp.MOD_ID);

    public static final Supplier<CreativeModeTab> SOUL_SAND_VALLEY = CREATIVE_MODE_TABS.register("soul_sand_valley", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.netherexp.jne_soul_sand_valley"))
            .icon(() -> new ItemStack(JNEBlocks.FOSSIL_ORE.get()))
            .displayItems((params, output) -> {
                output.accept(Blocks.SOUL_SOIL);
                output.accept(Blocks.SOUL_SAND);
                output.accept(JNEBlocks.ECTO_SOUL_SAND.get());
                output.accept(JNEBlocks.SUSPICIOUS_SOUL_SAND.get());
                output.accept(JNEBlocks.SOUL_SLATE.get());
                output.accept(JNEBlocks.SOUL_SLATE_STAIRS.get());
                output.accept(JNEBlocks.SOUL_SLATE_SLAB.get());
                output.accept(JNEBlocks.SOUL_SLATE_WALL.get());
                output.accept(JNEBlocks.SOUL_SLATE_BRICKS.get());
                output.accept(JNEBlocks.ETCHED_SOUL_SLATE_BRICKS.get());
                output.accept(JNEBlocks.CRACKED_SOUL_SLATE_BRICKS.get());
                output.accept(JNEBlocks.SOUL_SLATE_BRICK_PILLAR.get());
                output.accept(JNEBlocks.SOUL_SLATE_BRICK_STAIRS.get());
                output.accept(JNEBlocks.SOUL_SLATE_BRICK_SLAB.get());
                output.accept(JNEBlocks.SOUL_SLATE_BRICK_WALL.get());
                output.accept(JNEBlocks.CHISELED_SOUL_SLATE_BRICKS.get());
                output.accept(JNEBlocks.PALE_SOUL_SLATE.get());
                output.accept(JNEBlocks.SOUL_SLATE_TILES.get());
                output.accept(JNEBlocks.ETCHED_SOUL_SLATE_TILES.get());
                output.accept(JNEBlocks.SOUL_SLATE_TILE_STAIRS.get());
                output.accept(JNEBlocks.SOUL_SLATE_TILE_SLAB.get());
                output.accept(JNEBlocks.SOUL_SLATE_TILE_WALL.get());
                output.accept(JNEBlocks.CHISELED_SOUL_SLATE_TILES.get());

                output.accept(JNEBlocks.SOUL_PATH.get());

                output.accept(JNEBlocks.FOSSIL_ORE.get());
                output.accept(JNEBlocks.FOSSIL_FUEL_ORE.get());

                output.accept(JNEBlocks.SOUL_SWIRLS.get());

                output.accept(JNEBlocks.SOUL_GLASS.get());
                output.accept(JNEBlocks.DISCERNMENT_GLASS.get());

                output.accept(Blocks.SOUL_TORCH);
                output.accept(Blocks.SOUL_LANTERN);
                output.accept(Blocks.SOUL_CAMPFIRE);
                output.accept(JNEBlocks.SOUL_CANDLE.get());
                output.accept(JNEBlocks.SOUL_MAGMA_BLOCK.get());
                output.accept(JNEBlocks.SOULED_GEYSER.get());

                output.accept(JNEBlocks.BLACK_ICE.get());
                output.accept(JNEBlocks.BLACK_ICICLE.get());
                output.accept(JNEBlocks.THIN_BLACK_ICE.get());

                output.accept(JNEBlocks.SORROWEED.get());
                output.accept(JNEBlocks.SORROWSQUASH.get());
                output.accept(JNEBlocks.CARVED_SORROWSQUASH.get());
                output.accept(JNEBlocks.GHOUL_O_LANTERN.get());
                output.accept(Items.PUMPKIN_SEEDS);

                output.accept(JNEItems.WISP_BOTTLE.get());
                output.accept(JNEItems.WRAITHING_FLESH.get());
                output.accept(JNEBlocks.WRAITHING_LESION.get());
                output.accept(JNEItems.STRIDITE.get());
                output.accept(JNEItems.NETHERITE_PLATING.get());
                output.accept(JNEItems.PHASMO_SHARD.get());
                output.accept(JNEItems.PHASMO_ARROW.get());
                output.accept(JNEItems.BANSHEE_ROD.get());
                output.accept(JNEItems.BANSHEE_POWDER.get());

                output.accept(JNEBlocks.SOUL_TORCHFLOWER.get());

                output.accept(Items.SKELETON_SPAWN_EGG);
                output.accept(Items.GHAST_SPAWN_EGG);
                output.accept(JNEItems.WISP_SPAWN_EGG.get());
                output.accept(JNEItems.APPARITION_SPAWN_EGG.get());
                output.accept(JNEItems.VESSEL_SPAWN_EGG.get());
                output.accept(JNEItems.STAMPEDE_SPAWN_EGG.get());
                output.accept(JNEItems.ECTO_SLAB_SPAWN_EGG.get());
                output.accept(JNEItems.BANSHEE_SPAWN_EGG.get());
            })
            .build());

    public static final Supplier<CreativeModeTab> CRIMSON_FOREST = CREATIVE_MODE_TABS.register("crimson_forest", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.netherexp.jne_crimson_forest"))
            .icon(() -> new ItemStack(JNEItems.WEEPING_HELIX.get()))
            .displayItems((params, output) -> {
                output.accept(Blocks.CRIMSON_NYLIUM);
                output.accept(JNEBlocks.CRIMSON_NYLIUM_PATH.get());
                output.accept(Blocks.NETHER_WART_BLOCK);
                output.accept(JNEBlocks.NETHER_WART_BEARD.get());
                output.accept(JNEBlocks.CRIMSON_SPORESHROOM.get());
                output.accept(Blocks.CRIMSON_FUNGUS);
                output.accept(Blocks.CRIMSON_ROOTS);
                output.accept(JNEBlocks.CRIMSON_SPROUTS.get());
                output.accept(Blocks.WEEPING_VINES);
                output.accept(JNEItems.WEEPING_HELIX.get());
                output.accept(JNEBlocks.WEEPING_IVY.get());
                output.accept(Blocks.SHROOMLIGHT);
                output.accept(JNEItems.LIGHTSPORES.get());
                output.accept(Items.NETHER_WART);

                output.accept(Blocks.CRIMSON_STEM);
                output.accept(Blocks.CRIMSON_HYPHAE);
                output.accept(Blocks.STRIPPED_CRIMSON_STEM);
                output.accept(Blocks.STRIPPED_CRIMSON_HYPHAE);
                output.accept(Blocks.CRIMSON_PLANKS);
                output.accept(Blocks.CRIMSON_STAIRS);
                output.accept(Blocks.CRIMSON_SLAB);
                output.accept(Blocks.CRIMSON_FENCE);
                output.accept(Blocks.CRIMSON_FENCE_GATE);
                output.accept(Blocks.CRIMSON_DOOR);
                output.accept(Blocks.CRIMSON_PRESSURE_PLATE);
                output.accept(Blocks.CRIMSON_BUTTON);

                output.accept(JNEItems.HOGHAM.get());
                output.accept(JNEItems.COOKED_HOGHAM.get());

                output.accept(Blocks.NETHERRACK);
                output.accept(JNEBlocks.SMOOTH_NETHERRACK.get());
                output.accept(JNEBlocks.SMOOTH_NETHERRACK_STAIRS.get());
                output.accept(JNEBlocks.SMOOTH_NETHERRACK_SLAB.get());
                output.accept(JNEBlocks.SMOOTH_NETHERRACK_WALL.get());
                output.accept(JNEBlocks.NETHERRACK_BRICKS.get());
                output.accept(JNEBlocks.NETHERRACK_TILES.get());
                output.accept(JNEBlocks.NETHERRACK_PILLAR.get());
                output.accept(JNEBlocks.NETHERRACK_BRICK_STAIRS.get());
                output.accept(JNEBlocks.NETHERRACK_BRICK_SLAB.get());
                output.accept(JNEBlocks.NETHERRACK_BRICK_WALL.get());

                output.accept(Items.PIGLIN_SPAWN_EGG);
                output.accept(Items.PIGLIN_BRUTE_SPAWN_EGG);
                output.accept(Items.ZOMBIFIED_PIGLIN_SPAWN_EGG);
                output.accept(Items.HOGLIN_SPAWN_EGG);
                output.accept(Items.ZOGLIN_SPAWN_EGG);
            })
            .build());

    public static final Supplier<CreativeModeTab> WARPED_FOREST = CREATIVE_MODE_TABS.register("warped_forest", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.netherexp.jne_warped_forest"))
            .icon(() -> new ItemStack(JNEItems.TWISTING_HELIX.get()))
            .displayItems((params, output) -> {
                output.accept(Blocks.WARPED_NYLIUM);
                output.accept(JNEBlocks.WARPED_NYLIUM_PATH.get());
                output.accept(Blocks.WARPED_WART_BLOCK);
                output.accept(JNEBlocks.WARPED_WART_BEARD.get());
                output.accept(JNEBlocks.WARPED_SPORESHROOM.get());
                output.accept(Blocks.WARPED_FUNGUS);
                output.accept(Blocks.WARPED_ROOTS);
                output.accept(Blocks.NETHER_SPROUTS);
                output.accept(Blocks.TWISTING_VINES);
                output.accept(JNEItems.TWISTING_HELIX.get());
                output.accept(JNEBlocks.TWISTING_IVY.get());
                output.accept(JNEBlocks.SHROOMNIGHT.get());
                output.accept(JNEItems.NIGHTSPORES.get());
                output.accept(JNEBlocks.WARPED_WART.get());

                output.accept(Blocks.WARPED_STEM);
                output.accept(Blocks.WARPED_HYPHAE);
                output.accept(Blocks.STRIPPED_WARPED_STEM);
                output.accept(Blocks.STRIPPED_WARPED_HYPHAE);
                output.accept(Blocks.WARPED_PLANKS);
                output.accept(Blocks.WARPED_STAIRS);
                output.accept(Blocks.WARPED_SLAB);
                output.accept(Blocks.WARPED_FENCE);
                output.accept(Blocks.WARPED_FENCE_GATE);
                output.accept(Blocks.WARPED_DOOR);
                output.accept(Blocks.WARPED_PRESSURE_PLATE);
                output.accept(Blocks.WARPED_BUTTON);

                output.accept(Blocks.NETHERRACK);
                output.accept(JNEBlocks.SMOOTH_NETHERRACK.get());
                output.accept(JNEBlocks.SMOOTH_NETHERRACK_STAIRS.get());
                output.accept(JNEBlocks.SMOOTH_NETHERRACK_SLAB.get());
                output.accept(JNEBlocks.SMOOTH_NETHERRACK_WALL.get());
                output.accept(JNEBlocks.NETHERRACK_BRICKS.get());
                output.accept(JNEBlocks.NETHERRACK_TILES.get());
                output.accept(JNEBlocks.NETHERRACK_PILLAR.get());
                output.accept(JNEBlocks.NETHERRACK_BRICK_STAIRS.get());
                output.accept(JNEBlocks.NETHERRACK_BRICK_SLAB.get());
                output.accept(JNEBlocks.NETHERRACK_BRICK_WALL.get());

                output.accept(Items.ENDERMAN_SPAWN_EGG);
            })
            .build());

    public static final Supplier<CreativeModeTab> BASALT_DELTAS = CREATIVE_MODE_TABS.register("basalt_deltas", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.netherexp.jne_basalt_deltas"))
            .icon(() -> new ItemStack(JNEBlocks.POLISHED_BASALT_BRICKS.get()))
            .displayItems((params, output) -> {
                output.accept(Blocks.BASALT);
                output.accept(Blocks.SMOOTH_BASALT);
                output.accept(JNEBlocks.BASALT_STAIRS.get());
                output.accept(JNEBlocks.BASALT_SLAB.get());
                output.accept(JNEBlocks.BASALT_WALL.get());
                output.accept(Blocks.POLISHED_BASALT);
                output.accept(JNEBlocks.POLISHED_BASALT_STAIRS.get());
                output.accept(JNEBlocks.POLISHED_BASALT_SLAB.get());
                output.accept(JNEBlocks.POLISHED_BASALT_WALL.get());
                output.accept(JNEBlocks.POLISHED_BASALT_BRICKS.get());
                output.accept(JNEBlocks.POLISHED_BASALT_BRICK_STAIRS.get());
                output.accept(JNEBlocks.POLISHED_BASALT_BRICK_SLAB.get());
                output.accept(JNEBlocks.POLISHED_BASALT_BRICK_WALL.get());

                output.accept(Blocks.BLACKSTONE);
                output.accept(Blocks.GILDED_BLACKSTONE);
                output.accept(Blocks.BLACKSTONE_STAIRS);
                output.accept(Blocks.BLACKSTONE_SLAB);
                output.accept(Blocks.BLACKSTONE_WALL);
                output.accept(Blocks.CHISELED_POLISHED_BLACKSTONE);
                output.accept(Blocks.POLISHED_BLACKSTONE);
                output.accept(JNEBlocks.POLISHED_BLACKSTONE_PILLAR.get());
                output.accept(Blocks.POLISHED_BLACKSTONE_STAIRS);
                output.accept(Blocks.POLISHED_BLACKSTONE_SLAB);
                output.accept(Blocks.POLISHED_BLACKSTONE_WALL);
                output.accept(JNEBlocks.POLISHED_BLACKSTONE_FENCE.get());
                output.accept(Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE);
                output.accept(Blocks.POLISHED_BLACKSTONE_BUTTON);
                output.accept(Blocks.POLISHED_BLACKSTONE_BRICKS);
                output.accept(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS);
                output.accept(Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS);
                output.accept(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB);
                output.accept(Blocks.POLISHED_BLACKSTONE_BRICK_WALL);
                output.accept(JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICKS.get());
                output.accept(JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICK_SLAB.get());
                output.accept(JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICK_STAIRS.get());
                output.accept(JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICK_WALL.get());
                output.accept(JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICKS.get());
                output.accept(JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICK_SLAB.get());
                output.accept(JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICK_STAIRS.get());
                output.accept(JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICK_WALL.get());

                output.accept(JNEBlocks.BASALTIC_GEYSER.get());

                output.accept(Items.MAGMA_CREAM);
                output.accept(JNEBlocks.MAGMA_CREAM_BLOCK.get());
                output.accept(Blocks.MAGMA_BLOCK);

                output.accept(Items.MAGMA_CUBE_SPAWN_EGG);
                output.accept(Items.GHAST_SPAWN_EGG);
            })
            .build());

    public static final Supplier<CreativeModeTab> NETHER_WASTES = CREATIVE_MODE_TABS.register("nether_wastes", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.netherexp.jne_nether_wastes"))
            .icon(() -> new ItemStack(JNEBlocks.NETHERRACK_TILES.get()))
            .displayItems((params, output) -> {
                output.accept(Blocks.NETHERRACK);
                output.accept(JNEBlocks.SMOOTH_NETHERRACK.get());
                output.accept(JNEBlocks.SMOOTH_NETHERRACK_STAIRS.get());
                output.accept(JNEBlocks.SMOOTH_NETHERRACK_SLAB.get());
                output.accept(JNEBlocks.SMOOTH_NETHERRACK_WALL.get());
                output.accept(JNEBlocks.NETHERRACK_BRICKS.get());
                output.accept(JNEBlocks.NETHERRACK_BRICK_STAIRS.get());
                output.accept(JNEBlocks.NETHERRACK_BRICK_SLAB.get());
                output.accept(JNEBlocks.NETHERRACK_BRICK_WALL.get());
                output.accept(JNEBlocks.NETHERRACK_TILES.get());
                output.accept(JNEBlocks.NETHERRACK_PILLAR.get());

                output.accept(Items.MAGMA_BLOCK);
                output.accept(Blocks.SOUL_SAND);
                output.accept(Blocks.GLOWSTONE);
                output.accept(Blocks.NETHER_QUARTZ_ORE);
                output.accept(Blocks.NETHER_GOLD_ORE);
                output.accept(Blocks.ANCIENT_DEBRIS);

                output.accept(JNEBlocks.QUARTZ_CRYSTAL.get());
                output.accept(JNEBlocks.QUARTZ_CRYSTAL_BLOCK.get());
                output.accept(Blocks.QUARTZ_BLOCK);
                output.accept(Blocks.QUARTZ_PILLAR);
                output.accept(JNEBlocks.CHISELED_QUARTZ_PILLAR.get());
                output.accept(Blocks.QUARTZ_STAIRS);
                output.accept(Blocks.QUARTZ_SLAB);
                output.accept(Blocks.SMOOTH_QUARTZ);
                output.accept(Blocks.SMOOTH_QUARTZ_STAIRS);
                output.accept(Blocks.SMOOTH_QUARTZ_SLAB);
                output.accept(Blocks.QUARTZ_BRICKS);
                output.accept(JNEBlocks.CRACKED_QUARTZ_BRICKS.get());

                output.accept(JNEItems.CEREBRAGE_SEEDS.get());
                output.accept(JNEItems.CEREBRAGE.get());
                output.accept(JNEBlocks.CEREBRAGE_CLARET_STEM.get());
                output.accept(JNEBlocks.CEREBRAGE_CLARET_HYPHAE.get());
                output.accept(JNEBlocks.STRIPPED_CLARET_STEM.get());
                output.accept(JNEBlocks.STRIPPED_CLARET_HYPHAE.get());
                output.accept(JNEBlocks.CLARET_PLANKS.get());
                output.accept(JNEBlocks.CLARET_STAIRS.get());
                output.accept(JNEBlocks.CLARET_SLAB.get());
                output.accept(JNEBlocks.CLARET_FENCE.get());
                output.accept(JNEBlocks.CLARET_FENCE_GATE.get());
                output.accept(JNEBlocks.CLARET_DOOR.get());
                output.accept(JNEBlocks.CLARET_PRESSURE_PLATE.get());
                output.accept(JNEBlocks.CLARET_BUTTON.get());

                output.accept(Items.NETHER_BRICK);
                output.accept(Blocks.NETHER_BRICKS);
                output.accept(Blocks.CRACKED_NETHER_BRICKS);
                output.accept(JNEBlocks.NETHER_BRICK_PILLAR.get());
                output.accept(Blocks.NETHER_BRICK_STAIRS);
                output.accept(Blocks.NETHER_BRICK_SLAB);
                output.accept(Blocks.NETHER_BRICK_WALL);
                output.accept(Blocks.NETHER_BRICK_FENCE);
                output.accept(Blocks.CHISELED_NETHER_BRICKS);

                output.accept(JNEBlocks.RED_MIXED_NETHER_BRICKS.get());
                output.accept(Blocks.RED_NETHER_BRICKS);
                output.accept(Blocks.RED_NETHER_BRICK_STAIRS);
                output.accept(Blocks.RED_NETHER_BRICK_SLAB);
                output.accept(Blocks.RED_NETHER_BRICK_WALL);

                output.accept(JNEBlocks.BLUE_MIXED_NETHER_BRICKS.get());
                output.accept(JNEBlocks.BLUE_NETHER_BRICKS.get());
                output.accept(JNEBlocks.BLUE_NETHER_BRICK_STAIRS.get());
                output.accept(JNEBlocks.BLUE_NETHER_BRICK_SLAB.get());
                output.accept(JNEBlocks.BLUE_NETHER_BRICK_WALL.get());

                output.accept(Items.NETHERITE_BLOCK);
                output.accept(JNEBlocks.NETHERITE_PLATED_BLOCK.get());
                output.accept(JNEBlocks.NETHERITE_GRATE.get());
                output.accept(JNEBlocks.CUT_NETHERITE_BLOCK.get());
                output.accept(JNEBlocks.CUT_NETHERITE_STAIRS.get());
                output.accept(JNEBlocks.CUT_NETHERITE_SLAB.get());
                output.accept(JNEBlocks.CUT_NETHERITE_PILLAR.get());
                output.accept(JNEBlocks.RUSTY_NETHERITE_PLATED_BLOCK.get());
                output.accept(JNEBlocks.RUSTY_NETHERITE_GRATE.get());
                output.accept(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get());
                output.accept(JNEBlocks.RUSTY_CUT_NETHERITE_STAIRS.get());
                output.accept(JNEBlocks.RUSTY_CUT_NETHERITE_SLAB.get());
                output.accept(JNEBlocks.RUSTY_CUT_NETHERITE_PILLAR.get());

                output.accept(Items.LAVA_BUCKET);
                output.accept(Items.RED_MUSHROOM);
                output.accept(Items.BROWN_MUSHROOM);

                output.accept(Items.GOLD_INGOT);
                output.accept(Items.QUARTZ);
                output.accept(Items.NETHERITE_SCRAP);
                output.accept(Items.NETHERITE_INGOT);
                output.accept(JNEItems.NETHERITE_PLATING.get());
                output.accept(Items.MAGMA_CREAM);
                output.accept(JNEBlocks.MAGMA_CREAM_BLOCK.get());

                output.accept(Items.PIGLIN_SPAWN_EGG);
                output.accept(Items.PIGLIN_BRUTE_SPAWN_EGG);
                output.accept(Items.ZOMBIFIED_PIGLIN_SPAWN_EGG);
                output.accept(Items.GHAST_SPAWN_EGG);
                output.accept(Items.MAGMA_CUBE_SPAWN_EGG);
                output.accept(Items.BLAZE_SPAWN_EGG);
                output.accept(Items.WITHER_SKELETON_SPAWN_EGG);

                output.accept(LookupRegistryHelper.getItem(NetherExp.idVanilla("music_disc_tears")));
            })
            .build());

    public static void addToExistingTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            insertToTab(event, Items.POLISHED_BLACKSTONE, JNEBlocks.POLISHED_BLACKSTONE_PILLAR.get(), false);
            insertToTab(event, Items.POLISHED_BLACKSTONE_WALL, JNEBlocks.POLISHED_BLACKSTONE_FENCE.get(), false);
            insertToTab(event, Items.POLISHED_BLACKSTONE_BRICKS, JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICKS.get(), false);
            insertToTab(event, JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICKS.get(), JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICK_WALL.get(), false);
            insertToTab(event, JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICKS.get(), JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICK_SLAB.get(), false);
            insertToTab(event, JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICKS.get(), JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICK_STAIRS.get(), false);
            insertToTab(event, JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICK_WALL.get(), JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICKS.get(), false);
            insertToTab(event, JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICKS.get(), JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICK_WALL.get(), false);
            insertToTab(event, JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICKS.get(), JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICK_SLAB.get(), false);
            insertToTab(event, JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICKS.get(), JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICK_STAIRS.get(), false);

            insertToTab(event, Items.BASALT, JNEBlocks.BASALT_WALL.get(), false);
            insertToTab(event, Items.BASALT, JNEBlocks.BASALT_SLAB.get(), false);
            insertToTab(event, Items.BASALT, JNEBlocks.BASALT_STAIRS.get(), false);

            insertToTab(event, Items.POLISHED_BASALT, JNEBlocks.POLISHED_BASALT_WALL.get(), false);
            insertToTab(event, Items.POLISHED_BASALT, JNEBlocks.POLISHED_BASALT_SLAB.get(), false);
            insertToTab(event, Items.POLISHED_BASALT, JNEBlocks.POLISHED_BASALT_STAIRS.get(), false);

            insertToTab(event, JNEBlocks.POLISHED_BASALT_WALL.get(), JNEBlocks.POLISHED_BASALT_BRICKS.get(), false);
            insertToTab(event, JNEBlocks.POLISHED_BASALT_BRICKS.get(), JNEBlocks.POLISHED_BASALT_BRICK_WALL.get(), false);
            insertToTab(event, JNEBlocks.POLISHED_BASALT_BRICKS.get(), JNEBlocks.POLISHED_BASALT_BRICK_SLAB.get(), false);
            insertToTab(event, JNEBlocks.POLISHED_BASALT_BRICKS.get(), JNEBlocks.POLISHED_BASALT_BRICK_STAIRS.get(), false);

            insertToTab(event, Items.CRACKED_NETHER_BRICKS, JNEBlocks.NETHER_BRICK_PILLAR.get(), false);
            insertToTab(event, Items.RED_NETHER_BRICK_WALL, JNEBlocks.BLUE_NETHER_BRICKS.get(), false);
            insertToTab(event, JNEBlocks.BLUE_NETHER_BRICKS.get(), JNEBlocks.BLUE_NETHER_BRICK_WALL.get(), false);
            insertToTab(event, JNEBlocks.BLUE_NETHER_BRICKS.get(), JNEBlocks.BLUE_NETHER_BRICK_SLAB.get(), false);
            insertToTab(event, JNEBlocks.BLUE_NETHER_BRICKS.get(), JNEBlocks.BLUE_NETHER_BRICK_STAIRS.get(), false);
            insertToTab(event, Items.RED_NETHER_BRICKS, JNEBlocks.RED_MIXED_NETHER_BRICKS.get(), true);
            insertToTab(event, JNEBlocks.BLUE_NETHER_BRICKS.get(), JNEBlocks.BLUE_MIXED_NETHER_BRICKS.get(), true);
            
            insertToTab(event, Items.QUARTZ_PILLAR, JNEBlocks.CHISELED_QUARTZ_PILLAR.get(), false);
            insertToTab(event, Items.QUARTZ_BRICKS, JNEBlocks.CRACKED_QUARTZ_BRICKS.get(), false);
            insertToTab(event, Items.QUARTZ_BLOCK, JNEBlocks.QUARTZ_CRYSTAL_BLOCK.get(), true);
        }
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            insertToTab(event, Items.NETHER_QUARTZ_ORE, JNEBlocks.QUARTZ_CRYSTAL.get(), false);
            insertToTab(event, Items.NETHER_QUARTZ_ORE, JNEBlocks.QUARTZ_CRYSTAL_BLOCK.get(), false);

            insertToTab(event, Items.SHROOMLIGHT, JNEBlocks.SHROOMNIGHT.get(), false);
            insertToTab(event, Items.SHROOMLIGHT, JNEItems.LIGHTSPORES.get(), false);
            insertToTab(event, JNEBlocks.SHROOMNIGHT.get(), JNEItems.NIGHTSPORES.get(), false);

            insertToTab(event, Items.NETHER_WART_BLOCK, JNEBlocks.NETHER_WART_BEARD.get(), false);
            insertToTab(event, Items.WARPED_WART_BLOCK, JNEBlocks.WARPED_WART_BEARD.get(), false);

            insertToTab(event, Items.NETHER_WART, JNEBlocks.WARPED_WART.get(), false);
        }
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            insertToTab(event, Items.SHROOMLIGHT, JNEBlocks.SHROOMNIGHT.get(), false);
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            insertToTab(event, Items.NETHER_WART, JNEBlocks.WARPED_WART.get(), false);
        }
    }

    public static void init(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
