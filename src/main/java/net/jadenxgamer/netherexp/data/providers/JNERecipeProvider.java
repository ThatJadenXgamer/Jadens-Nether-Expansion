package net.jadenxgamer.netherexp.data.providers;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class JNERecipeProvider extends RecipeProvider {

    public JNERecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper fileHelper) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput output) {

        // Crafting
        // # Blocks
        // ## Soul Slate
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, JNEBlocks.PALE_SOUL_SLATE.get(), 4).define('S', JNEBlocks.SOUL_SLATE.get()).define('N', Blocks.NETHERRACK).pattern(" S ").pattern("SNS").pattern(" S ").unlockedBy("has_soul_slate", has(JNEBlocks.SOUL_SLATE.get())).save(output);
        slab(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.SOUL_SLATE_SLAB.get(), JNEBlocks.SOUL_SLATE.get());
        stairBuilder(JNEBlocks.SOUL_SLATE_STAIRS.get(), Ingredient.of(JNEBlocks.SOUL_SLATE.get())).unlockedBy("has_soul_slate", has(JNEBlocks.SOUL_SLATE.get())).save(output);
        wall(output, RecipeCategory.DECORATIONS, JNEBlocks.SOUL_SLATE_WALL.get(), JNEBlocks.SOUL_SLATE.get());

        // ## Soul Slate Bricks
        bricks(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.SOUL_SLATE_BRICKS.get(), JNEBlocks.SOUL_SLATE.get());
        bricksBuilder(RecipeCategory.BUILDING_BLOCKS, JNEBlocks.SOUL_SLATE_BRICKS.get(), JNEBlocks.ETCHED_SOUL_SLATE_BRICKS.get()).unlockedBy("has_soul_slate", has(JNEBlocks.SOUL_SLATE.get())).save(output, "netherexp:soul_slate_bricks_from_etched");
        slab(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.SOUL_SLATE_BRICK_SLAB.get(), JNEBlocks.SOUL_SLATE_BRICKS.get());
        stairBuilder(JNEBlocks.SOUL_SLATE_BRICK_STAIRS.get(), Ingredient.of(JNEBlocks.SOUL_SLATE_BRICKS.get())).unlockedBy("has_soul_slate_bricks", has(JNEBlocks.SOUL_SLATE_BRICKS.get())).save(output);
        wall(output, RecipeCategory.DECORATIONS, JNEBlocks.SOUL_SLATE_BRICK_WALL.get(), JNEBlocks.SOUL_SLATE_BRICKS.get());
        etched(output, JNEBlocks.ETCHED_SOUL_SLATE_BRICKS.get(), JNEBlocks.SOUL_SLATE_BRICKS.get());
        pillar(output, JNEBlocks.SOUL_SLATE_BRICK_PILLAR.get(), JNEBlocks.SOUL_SLATE_BRICKS.get());
        chiseled(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.CHISELED_SOUL_SLATE_BRICKS.get(), JNEBlocks.SOUL_SLATE_BRICKS.get());

        // ## Soul Slate Tile
        tiles(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.SOUL_SLATE_TILES.get(), JNEBlocks.SOUL_SLATE.get());
        bricksBuilder(RecipeCategory.BUILDING_BLOCKS, JNEBlocks.SOUL_SLATE_TILES.get(), JNEBlocks.ETCHED_SOUL_SLATE_TILES.get()).unlockedBy("has_soul_slate", has(JNEBlocks.SOUL_SLATE.get())).save(output, "netherexp:soul_slate_tiles_from_etched");
        slab(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.SOUL_SLATE_TILE_SLAB.get(), JNEBlocks.SOUL_SLATE_TILES.get());
        stairBuilder(JNEBlocks.SOUL_SLATE_TILE_STAIRS.get(), Ingredient.of(JNEBlocks.SOUL_SLATE_TILES.get())).unlockedBy("has_soul_slate_tiles", has(JNEBlocks.SOUL_SLATE_TILES.get())).save(output);
        wall(output, RecipeCategory.DECORATIONS, JNEBlocks.SOUL_SLATE_TILE_WALL.get(), JNEBlocks.SOUL_SLATE_TILES.get());
        etched(output, JNEBlocks.ETCHED_SOUL_SLATE_TILES.get(), JNEBlocks.SOUL_SLATE_TILES.get());
        chiseled(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.CHISELED_SOUL_SLATE_TILES.get(), JNEBlocks.SOUL_SLATE_TILES.get());

        // ## Soul Sand Valley Addition
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, JNEBlocks.SOUL_CANDLE.get()).define('S', Items.STRING).define('B', ItemTags.SOUL_FIRE_BASE_BLOCKS).pattern("S").pattern("B").unlockedBy("has_soul_fire_base_block", has(ItemTags.SOUL_FIRE_BASE_BLOCKS)).save(output);
        twoByTwoPacker(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.SOUL_GLASS.get(), JNEItems.PHASMO_SHARD.get());
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, JNEBlocks.DISCERNMENT_GLASS.get(), 2).define('Q', Items.QUARTZ).define('S', JNEBlocks.SOUL_GLASS.get()).pattern(" Q ").pattern("QSQ").pattern(" Q ").unlockedBy("has_soul_glass", has(JNEBlocks.SOUL_GLASS.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, JNEBlocks.ECTO_SOUL_SAND.get(), 2).define('W', Blocks.SOUL_SAND).define('S', JNEItems.WRAITHING_FLESH.get()).pattern("WS").pattern("SW").unlockedBy("has_wraithing_fless", has(JNEItems.WRAITHING_FLESH.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, JNEBlocks.SOUL_MAGMA_BLOCK.get(), 4).define('A', Items.MAGMA_CREAM).define('B', ItemTags.SOUL_FIRE_BASE_BLOCKS).pattern("AB").pattern("BA").unlockedBy("has_magma_cream", has(Items.MAGMA_CREAM)).unlockedBy("has_soul_fire_base_block", has(ItemTags.SOUL_FIRE_BASE_BLOCKS)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, JNEBlocks.SOUL_SOIL_LAYER.get(), 24).define('#', Blocks.SOUL_SOIL).pattern("###").unlockedBy("has_soul_soil", has(Blocks.SOUL_SOIL)).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.SOUL_SOIL).requires(JNEBlocks.SOUL_SOIL_LAYER.get(), 9).unlockedBy("has_soul_soil", has(Blocks.SOUL_SOIL)).save(output, "netherexp:soul_soil_from_soul_soil_layer");
        twoByTwoPacker(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.BLACK_ICE.get(), JNEBlocks.BLACK_ICICLE.get(), 2);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, JNEBlocks.SOUL_PERMAFROST.get(), 4).define('A', JNEBlocks.BLACK_ICE.get()).define('B', Blocks.SOUL_SOIL).pattern("AB").pattern("BA").unlockedBy("has_black_ice", has(JNEBlocks.BLACK_ICE.get())).save(output);

        // ## Smooth Netherrack
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, JNEBlocks.SMOOTH_NETHERRACK.get(), 4).define('A', Blocks.NETHERRACK).define('B', Blocks.GRAVEL).pattern("AB").pattern("BA").unlockedBy("has_netherrack", has(Blocks.NETHERRACK)).save(output);
        slab(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.SMOOTH_NETHERRACK_SLAB.get(), JNEBlocks.SMOOTH_NETHERRACK.get());
        stairBuilder(JNEBlocks.SMOOTH_NETHERRACK_STAIRS.get(), Ingredient.of(JNEBlocks.SMOOTH_NETHERRACK.get())).unlockedBy("has_smooth_netherrack", has(JNEBlocks.SMOOTH_NETHERRACK.get())).save(output);
        wall(output, RecipeCategory.DECORATIONS, JNEBlocks.SMOOTH_NETHERRACK_WALL.get(), JNEBlocks.SMOOTH_NETHERRACK.get());

        // ## Netherrack Bricks
        bricks(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.NETHERRACK_BRICKS.get(), JNEBlocks.SMOOTH_NETHERRACK.get());
        slab(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.NETHERRACK_BRICK_SLAB.get(), JNEBlocks.NETHERRACK_BRICKS.get());
        stairBuilder(JNEBlocks.NETHERRACK_BRICK_STAIRS.get(), Ingredient.of(JNEBlocks.NETHERRACK_BRICKS.get())).unlockedBy("has_netherrack_bricks", has(JNEBlocks.NETHERRACK_BRICKS.get())).save(output);
        wall(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.NETHERRACK_BRICK_WALL.get(), JNEBlocks.NETHERRACK_BRICKS.get());
        tiles(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.NETHERRACK_TILES.get(), JNEBlocks.NETHERRACK_BRICKS.get());
        pillar(output, JNEBlocks.NETHERRACK_PILLAR.get(), JNEBlocks.NETHERRACK_BRICKS.get());

        // ## Basalt
        slab(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.BASALT_SLAB.get(), Blocks.BASALT);
        stairBuilder(JNEBlocks.BASALT_STAIRS.get(), Ingredient.of(Blocks.BASALT)).unlockedBy("has_basalt", has(Blocks.BASALT)).save(output);
        wall(output, RecipeCategory.DECORATIONS, JNEBlocks.BASALT_WALL.get(), Blocks.BASALT);

        // ## Polished Basalt
        slab(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.POLISHED_BASALT_SLAB.get(), Blocks.POLISHED_BASALT);
        stairBuilder(JNEBlocks.POLISHED_BASALT_STAIRS.get(), Ingredient.of(Blocks.POLISHED_BASALT)).unlockedBy("has_polished_basalt", has(Blocks.POLISHED_BASALT)).save(output);
        wall(output, RecipeCategory.DECORATIONS, JNEBlocks.POLISHED_BASALT_WALL.get(), Blocks.POLISHED_BASALT);

        // ## Polished Basalt Bricks
        bricks(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.POLISHED_BASALT_BRICKS.get(), Blocks.POLISHED_BASALT);
        slab(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.POLISHED_BASALT_BRICK_SLAB.get(), JNEBlocks.POLISHED_BASALT_BRICKS.get());
        stairBuilder(JNEBlocks.POLISHED_BASALT_BRICK_STAIRS.get(), Ingredient.of(JNEBlocks.POLISHED_BASALT_BRICKS.get())).unlockedBy("has_polished_basalt_bricks", has(JNEBlocks.POLISHED_BASALT_BRICKS.get())).save(output);
        wall(output, RecipeCategory.DECORATIONS, JNEBlocks.POLISHED_BASALT_BRICK_WALL.get(), JNEBlocks.POLISHED_BASALT_BRICKS.get());

        // ## Netherite
        transformEight(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.NETHERITE_PLATED_BLOCK.get(), Blocks.BLACKSTONE, JNEItems.NETHERITE_PLATING.get());
        cutBuilder(RecipeCategory.BUILDING_BLOCKS, JNEBlocks.CUT_NETHERITE_BLOCK.get(), Ingredient.of(JNEBlocks.NETHERITE_PLATED_BLOCK.get())).group("cut_netherite_block").unlockedBy("has_netherite_plated_block", has(JNEBlocks.NETHERITE_PLATED_BLOCK.get())).save(output);
        slabBuilder(RecipeCategory.BUILDING_BLOCKS, JNEBlocks.CUT_NETHERITE_SLAB.get(), Ingredient.of(JNEBlocks.CUT_NETHERITE_BLOCK.get())).group("cut_netherite_slab").unlockedBy("has_netherite_plated_block", has(JNEBlocks.NETHERITE_PLATED_BLOCK.get())).save(output);
        stairBuilder(JNEBlocks.CUT_NETHERITE_STAIRS.get(), Ingredient.of(JNEBlocks.CUT_NETHERITE_BLOCK.get())).group("cut_netherite_stairs").unlockedBy("has_netherite_plated_block", has(JNEBlocks.NETHERITE_PLATED_BLOCK.get())).save(output);
        pillarBuilder(JNEBlocks.CUT_NETHERITE_PILLAR.get(), JNEBlocks.CUT_NETHERITE_BLOCK.get()).unlockedBy("has_netherite_plated_block", has(JNEBlocks.NETHERITE_PLATED_BLOCK.get())).save(output);
        grateBuilder(JNEBlocks.NETHERITE_GRATE.get(), JNEBlocks.NETHERITE_PLATED_BLOCK.get()).group("netherite_grate").unlockedBy("has_netherite_plated_block", has(JNEBlocks.NETHERITE_PLATED_BLOCK.get())).save(output);

        // ## Rusty Netherite
        cutBuilder(RecipeCategory.BUILDING_BLOCKS, JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get(), Ingredient.of(JNEBlocks.RUSTY_NETHERITE_PLATED_BLOCK.get())).group("cut_netherite_block").unlockedBy("has_rusty_netherite_plated_block", has(JNEBlocks.RUSTY_NETHERITE_PLATED_BLOCK.get())).save(output);
        slabBuilder(RecipeCategory.BUILDING_BLOCKS, JNEBlocks.RUSTY_CUT_NETHERITE_SLAB.get(), Ingredient.of(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get())).group("cut_netherite_slab").unlockedBy("has_rusty_netherite_plated_block", has(JNEBlocks.RUSTY_NETHERITE_PLATED_BLOCK.get())).save(output);
        stairBuilder(JNEBlocks.RUSTY_CUT_NETHERITE_STAIRS.get(), Ingredient.of(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get())).group("cut_netherite_stairs").unlockedBy("has_rusty_netherite_plated_block", has(JNEBlocks.RUSTY_NETHERITE_PLATED_BLOCK.get())).save(output);
        pillarBuilder(JNEBlocks.RUSTY_CUT_NETHERITE_PILLAR.get(), JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get()).unlockedBy("has_rusty_netherite_plated_block", has(JNEBlocks.RUSTY_NETHERITE_PLATED_BLOCK.get())).save(output);
        grateBuilder(JNEBlocks.RUSTY_NETHERITE_GRATE.get(), JNEBlocks.RUSTY_NETHERITE_PLATED_BLOCK.get()).group("netherite_grate").unlockedBy("has_rusty_netherite_plated_block", has(JNEBlocks.RUSTY_NETHERITE_PLATED_BLOCK.get())).save(output);

        // ## Claret WoodSet
        woodFromLogs(output, JNEBlocks.CEREBRAGE_CLARET_HYPHAE.get(), JNEBlocks.CEREBRAGE_CLARET_STEM.get());
        woodFromLogs(output, JNEBlocks.STRIPPED_CLARET_HYPHAE.get(), JNEBlocks.STRIPPED_CLARET_STEM.get());
        planksFromLogs(output, JNEBlocks.CLARET_PLANKS.get(), JNETags.Items.CLARET_STEMS, 2);
        List.of(
                slabBuilder(RecipeCategory.BUILDING_BLOCKS, JNEBlocks.CLARET_SLAB.get(), Ingredient.of(JNEBlocks.CLARET_PLANKS.get())),
                stairBuilder(JNEBlocks.CLARET_STAIRS.get(), Ingredient.of(JNEBlocks.CLARET_PLANKS.get())),
                fenceBuilder(JNEBlocks.CLARET_FENCE.get(), Ingredient.of(JNEBlocks.CLARET_PLANKS.get())),
                fenceGateBuilder(JNEBlocks.CLARET_FENCE_GATE.get(), Ingredient.of(JNEBlocks.CLARET_PLANKS.get())),
                doorBuilder(JNEBlocks.CLARET_DOOR.get(), Ingredient.of(JNEBlocks.CLARET_PLANKS.get())),
                trapdoorBuilder(JNEBlocks.CLARET_TRAPDOOR.get(), Ingredient.of(JNEBlocks.CLARET_PLANKS.get())),
                buttonBuilder(JNEBlocks.CLARET_BUTTON.get(), Ingredient.of(JNEBlocks.CLARET_PLANKS.get())),
                pressurePlateBuilder(RecipeCategory.REDSTONE, JNEBlocks.CLARET_PRESSURE_PLATE.get(), Ingredient.of(JNEBlocks.CLARET_PLANKS.get())),
                signBuilder(JNEItems.CLARET_SIGN.get(), Ingredient.of(JNEBlocks.CLARET_PLANKS.get()))
        ).forEach(b -> b.unlockedBy("has_claret_planks", has(JNEBlocks.CLARET_PLANKS.get())).save(output));
        hangingSign(output, JNEBlocks.CLARET_HANGING_SIGN.get(), JNEBlocks.CLARET_PLANKS.get());

        // ## Storage Blocks
        nineBlockStorageRecipes(output, RecipeCategory.BREWING, Items.MAGMA_CREAM, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.MAGMA_CREAM_BLOCK.get(), "netherexp:magma_cream_block", null, "minecraft:magma_cream_from_magma_cream_block", null);

        // ## Quartz Blocks
        twoByTwoPacker(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.QUARTZ_CRYSTAL_BLOCK.get(), JNEBlocks.QUARTZ_CRYSTAL.get());
        chiseled(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.CHISELED_QUARTZ_PILLAR.get(), Blocks.QUARTZ_PILLAR);

        // ## Nether Bricks
        transformEightBuilder(RecipeCategory.BUILDING_BLOCKS, Blocks.RED_NETHER_BRICKS, Blocks.NETHER_BRICKS, Blocks.NETHER_WART_BLOCK).group("colored_nether_bricks").unlockedBy("has_nether_bricks", has(Blocks.NETHER_BRICKS)).save(output);
        pillar(output, JNEBlocks.NETHER_BRICK_PILLAR.get(), Blocks.NETHER_BRICKS);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, JNEBlocks.RED_MIXED_NETHER_BRICKS.get(), 4).define('A', Blocks.NETHER_BRICKS).define('B', Blocks.RED_NETHER_BRICKS).pattern("AB").pattern("BA").unlockedBy("has_red_nether_bricks", has(Blocks.RED_NETHER_BRICKS)).save(output);

        // ## Blue Nether Bricks
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, JNEBlocks.BLUE_MIXED_NETHER_BRICKS.get(), 4).define('A', Blocks.NETHER_BRICKS).define('B', JNEBlocks.BLUE_NETHER_BRICKS.get()).pattern("AB").pattern("BA").unlockedBy("has_blue_nether_bricks", has(JNEBlocks.BLUE_NETHER_BRICKS.get())).save(output);
        transformEightBuilder(RecipeCategory.BUILDING_BLOCKS, JNEBlocks.BLUE_NETHER_BRICKS.get(), Blocks.NETHER_BRICKS, Blocks.WARPED_WART_BLOCK).group("colored_nether_bricks").unlockedBy("has_nether_bricks", has(Blocks.NETHER_BRICKS)).save(output);
        slab(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.BLUE_NETHER_BRICK_SLAB.get(), JNEBlocks.BLUE_NETHER_BRICKS.get());
        stairBuilder(JNEBlocks.BLUE_NETHER_BRICK_STAIRS.get(), Ingredient.of(JNEBlocks.BLUE_NETHER_BRICKS.get())).unlockedBy("has_blue_nether_bricks", has(JNEBlocks.BLUE_NETHER_BRICKS.get())).save(output);
        wall(output, RecipeCategory.DECORATIONS, JNEBlocks.BLUE_NETHER_BRICK_WALL.get(), JNEBlocks.BLUE_NETHER_BRICKS.get());

        // ## Farming & Food
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, JNEBlocks.NETHER_PIZZA.get()).define('H', JNETags.Items.COOKED_HOGHAM).define('W', JNEBlocks.WARPED_WART.get()).define('C', JNEItems.GLOWCHEESE.get()).define('B', Items.BREAD).define('N', Items.NETHER_WART).pattern("HWH").pattern("CCC").pattern("BNB").unlockedBy("has_glowcheese", has(JNEItems.GLOWCHEESE.get())).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, JNEBlocks.WRAITHING_LESION.get()).requires(Items.GHAST_TEAR, 3).requires(JNEItems.WRAITHING_FLESH.get(), 6).unlockedBy("has_wraithing_flesh", has(JNEItems.WRAITHING_FLESH.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, JNEBlocks.GHOUL_O_LANTERN.get()).define('S', JNEBlocks.CARVED_SORROWSQUASH.get()).define('T', Items.SOUL_TORCH).pattern("S").pattern("T").unlockedBy("has_carved_sorrowsquash", has(JNEBlocks.CARVED_SORROWSQUASH.get())).save(output);

        // ## Ivy
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, JNEBlocks.TWISTING_IVY.get()).requires(Blocks.TWISTING_VINES).group("ivy_blocks").unlockedBy("has_twisting_vines", has(Blocks.TWISTING_VINES)).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, JNEBlocks.WEEPING_IVY.get()).requires(Blocks.WEEPING_VINES).group("ivy_blocks").unlockedBy("has_weeping_vines", has(Blocks.WEEPING_VINES)).save(output);

        // ## Sporeshrooms and Geysers
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, JNEBlocks.SOULED_GEYSER.get()).define('B', JNEBlocks.SOUL_SLATE.get()).define('G', JNEBlocks.SOULED_GEYSER.get()).define('L', Items.LAVA_BUCKET).pattern("B").pattern("G").pattern("L").unlockedBy("has_souled_geyser", has(JNEBlocks.SOULED_GEYSER.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, JNEBlocks.BASALTIC_GEYSER.get()).define('B', Blocks.BASALT).define('G', JNEBlocks.BASALTIC_GEYSER.get()).define('L', Items.LAVA_BUCKET).pattern("B").pattern("G").pattern("L").unlockedBy("has_basaltic_geyser", has(JNEBlocks.BASALTIC_GEYSER.get())).save(output);

        // ## Blackstone
        pillar(output, JNEBlocks.POLISHED_BLACKSTONE_PILLAR.get(), Blocks.POLISHED_BLACKSTONE);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, JNEBlocks.POLISHED_BLACKSTONE_FENCE.get(), 6).define('#', Blocks.POLISHED_BLACKSTONE).define('=', Blocks.POLISHED_BLACKSTONE_SLAB).pattern("#=#").pattern("#=#").unlockedBy("has_polished_blackstone", has(Blocks.POLISHED_BLACKSTONE));
        transformEight(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICKS.get(), Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.WEEPING_VINES);
        slab(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICK_SLAB.get(), JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICKS.get());
        stairBuilder(JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICK_STAIRS.get(), Ingredient.of(JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICKS.get())).unlockedBy("has_weeping_polished_blackstone_bricks", has(JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICKS.get())).save(output);
        wall(output, RecipeCategory.DECORATIONS, JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICK_WALL.get(), JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICKS.get());
        transformEight(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICKS.get(), Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.TWISTING_VINES);
        slab(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICK_SLAB.get(), JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICKS.get());
        stairBuilder(JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICK_STAIRS.get(), Ingredient.of(JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICKS.get())).unlockedBy("has_twisting_polished_blackstone_bricks", has(JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICKS.get())).save(output);
        wall(output, RecipeCategory.DECORATIONS, JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICK_WALL.get(), JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICKS.get());

        // ## Bones
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, JNEBlocks.SKELETON_SKULL_CANDLE.get()).define('S', Items.SKELETON_SKULL).define('C', Items.CANDLE).pattern("S").pattern("C").unlockedBy("has_skeleton_skull", has(Items.SKELETON_SKULL)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, JNEBlocks.SOUL_SKELETON_SKULL_CANDLE.get()).define('S', Items.SKELETON_SKULL).define('C', JNEBlocks.SOUL_CANDLE.get()).pattern("S").pattern("C").unlockedBy("has_soul_fire_base_block", has(ItemTags.SOUL_FIRE_BASE_BLOCKS)).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, JNEBlocks.ANCIENT_SKELETON_SKULL_CANDLE.get()).requires(Items.SKELETON_SKULL).requires(JNEBlocks.ANCIENT_CANDLE.get()).unlockedBy("has_ancient_wax", has(JNEItems.ANCIENT_WAX.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, JNEBlocks.BONE_PIKE.get(), 2).define('#', Items.BONE).pattern("#").pattern("#").unlockedBy("has_bone", has(Items.BONE)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, JNEBlocks.BONE_FENCE.get(), 4).define('#', Items.BONE).define('S', Items.STRING).pattern("#S#").pattern("#S#").unlockedBy("has_bone", has(Items.BONE)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, JNEBlocks.SKULL_BLOCK.get()).define('#', JNEBlocks.STACKED_BONE_SLAB.get()).pattern("#").pattern("#").unlockedBy("has_stacked_bones", has(JNEBlocks.STACKED_BONES.get())).save(output);
        burningSkull(output, JNEBlocks.BURNING_SKULL_BLOCK.get(), JNEBlocks.SKULL_BLOCK.get());
        soulBurningSkull(output, JNEBlocks.SOUL_BURNING_SKULL_BLOCK.get(), JNEBlocks.SKULL_BLOCK.get());
        ancientBurningSkull(output, JNEBlocks.ANCIENT_BURNING_SKULL_BLOCK.get(), JNEBlocks.SKULL_BLOCK.get());
        twoByTwoPacker(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.STACKED_BONES.get(), Blocks.BONE_BLOCK);
        slab(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.STACKED_BONE_SLAB.get(), JNEBlocks.STACKED_BONES.get());
        stairBuilder(JNEBlocks.STACKED_BONE_STAIRS.get(), Ingredient.of(JNEBlocks.STACKED_BONES.get())).unlockedBy("has_stacked_bones", has(JNEBlocks.STACKED_BONES.get())).save(output);

        // ## Wither Bones
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, JNEBlocks.WITHER_BONE_BLOCK.get(), 2).define('A', Blocks.BONE_BLOCK).define('B', ItemTags.COALS).pattern("AB").pattern("BA").unlockedBy("has_bone_block", has(Blocks.BONE_BLOCK)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, JNEBlocks.WITHER_SKULL_BLOCK.get()).define('#', JNEBlocks.STACKED_WITHER_BONE_SLAB.get()).pattern("#").pattern("#").unlockedBy("has_wither_stacked_bones", has(JNEBlocks.STACKED_WITHER_BONES.get())).save(output);
        burningSkull(output, JNEBlocks.BURNING_WITHER_SKULL_BLOCK.get(), JNEBlocks.WITHER_SKULL_BLOCK.get());
        soulBurningSkull(output, JNEBlocks.SOUL_BURNING_WITHER_SKULL_BLOCK.get(), JNEBlocks.WITHER_SKULL_BLOCK.get());
        ancientBurningSkull(output, JNEBlocks.ANCIENT_BURNING_WITHER_SKULL_BLOCK.get(), JNEBlocks.WITHER_SKULL_BLOCK.get());
        twoByTwoPacker(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.STACKED_WITHER_BONES.get(), JNEBlocks.WITHER_BONE_BLOCK.get());
        slab(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.STACKED_WITHER_BONE_SLAB.get(), JNEBlocks.STACKED_WITHER_BONES.get());
        stairBuilder(JNEBlocks.STACKED_WITHER_BONE_STAIRS.get(), Ingredient.of(JNEBlocks.STACKED_WITHER_BONES.get())).unlockedBy("has_stacked_bones", has(JNEBlocks.STACKED_WITHER_BONES.get())).save(output);

        // ## Sanctum Decorations
        gargoyleCopy(output, JNEBlocks.OSSIFIED_GARGOYLE_STATUE.get());
        gargoyleCopy(output, JNEBlocks.TRAMPLE_GARGOYLE_STATUE.get());
        gargoyleCopy(output, JNEBlocks.PHASE_GARGOYLE_STATUE.get());
        gargoyleCopy(output, JNEBlocks.GHOUL_GARGOYLE_STATUE.get());
        gargoyleCopy(output, JNEBlocks.WRETCHED_GARGOYLE_STATUE.get());
        gargoyleCopy(output, JNEBlocks.TREACHEROUS_GARGOYLE_STATUE.get());
        gargoyleCopy(output, JNEBlocks.CIRRIPEDIA_GARGOYLE_STATUE.get());
        gargoyleCopy(output, JNEBlocks.OCCULT_GARGOYLE_STATUE.get());
        gargoyleCopy(output, JNEBlocks.SEALED_GARGOYLE_STATUE.get());
        gargoyleCopy(output, JNEBlocks.OBFUSCATED_GARGOYLE_STATUE.get());
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, JNEBlocks.INSCRIBED_PANEL.get(), 4).define('S', JNEBlocks.SOUL_SLATE_BRICKS.get()).define('W', JNEItems.WRAITHING_FLESH.get()).pattern(" S ").pattern("SWS").pattern(" S ").unlockedBy("has_soul_slate_bricks", has(JNEBlocks.SOUL_SLATE_BRICKS.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, JNEBlocks.SHOTGUN_BARREL.get(), 2).define('B', Blocks.BONE_BLOCK).define('R', Items.REDSTONE).define('S', JNEItems.SHOTGUN_CORE.get()).define('N', JNEItems.STRIDITE.get()).pattern("BBB").pattern("RSR").pattern("NNN").unlockedBy("has_shotgun_core", has(JNEItems.SHOTGUN_CORE.get())).save(output);

        // ## Ancient Fire
        transformEight(output, RecipeCategory.BUILDING_BLOCKS, JNEBlocks.ANCIENT_WAX_BLOCK.get(), Blocks.HONEYCOMB_BLOCK, JNEItems.ANCIENT_WAX.get());
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, JNEItems.ANCIENT_TORCH.get(), 4).define('X', Ingredient.of(Items.COAL, Items.CHARCOAL, JNEItems.FOSSIL_FUEL.get())).define('#', Items.STICK).define('A', JNEItems.ANCIENT_WAX.get()).pattern("X").pattern("#").pattern("A").unlockedBy("has_ancient_wax", has(JNEItems.ANCIENT_WAX.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, JNEBlocks.ANCIENT_LANTERN.get()).define('#', JNEItems.ANCIENT_TORCH.get()).define('X', Items.IRON_NUGGET).pattern("XXX").pattern("X#X").pattern("XXX").unlockedBy("has_ancient_wax", has(JNEItems.ANCIENT_WAX.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, JNEBlocks.ANCIENT_CAMPFIRE.get()).define('L', ItemTags.LOGS).define('S', Items.STICK).define('#', JNEItems.ANCIENT_WAX.get()).pattern(" S ").pattern("S#S").pattern("LLL").unlockedBy("has_ancient_wax", has(JNEItems.ANCIENT_WAX.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, JNEBlocks.ANCIENT_CANDLE.get(), 6).define('S', Items.STRING).define('A', JNEItems.ANCIENT_WAX.get()).pattern("S").pattern("A").pattern("A").unlockedBy("has_ancient_wax", has(JNEItems.ANCIENT_WAX.get())).save(output);

        // # Items
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, JNEItems.RIFT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 2).define('#', Items.DIAMOND).define('C', Blocks.CRYING_OBSIDIAN).define('S', JNEItems.RIFT_ARMOR_TRIM_SMITHING_TEMPLATE.get()).pattern("#S#").pattern("#C#").pattern("###").unlockedBy("has_rift_armor_trim_smithing_template", has(JNEItems.RIFT_ARMOR_TRIM_SMITHING_TEMPLATE.get())).showNotification(true).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, JNEItems.SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 2).define('#', Items.DIAMOND).define('C', JNEBlocks.SOUL_SLATE.get()).define('S', JNEItems.SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE.get()).pattern("#S#").pattern("#C#").pattern("###").unlockedBy("has_spirit_armor_trim_smithing_template", has(JNEItems.SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE.get())).showNotification(true).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, JNEItems.VALOR_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 2).define('#', Items.DIAMOND).define('C', JNEBlocks.PALE_SOUL_SLATE.get()).define('S', JNEItems.VALOR_ARMOR_TRIM_SMITHING_TEMPLATE.get()).pattern("#S#").pattern("#C#").pattern("###").unlockedBy("has_valor_armor_trim_smithing_template", has(JNEItems.VALOR_ARMOR_TRIM_SMITHING_TEMPLATE.get())).showNotification(true).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, JNEItems.PUMP_CHARGE_UPGRADE_SMITHING_TEMPLATE.get(), 2).define('#', Items.BONE_BLOCK).define('F', JNEItems.TREACHEROUS_FLAME.get()).define('S', JNEItems.PUMP_CHARGE_UPGRADE_SMITHING_TEMPLATE.get()).pattern("#S#").pattern("#F#").pattern("###").unlockedBy("has_pump_charge_upgrade_smithing_template", has(JNEItems.PUMP_CHARGE_UPGRADE_SMITHING_TEMPLATE.get())).showNotification(true).save(output);
        nineBlockStorageRecipes(output, RecipeCategory.MISC, JNEItems.LIGHTSPORES.get(), RecipeCategory.DECORATIONS, Blocks.SHROOMLIGHT, "minecraft:shroomlight_from_lightspores", null, "netherexp:lightspores", null);
        nineBlockStorageRecipes(output, RecipeCategory.MISC, JNEItems.NIGHTSPORES.get(), RecipeCategory.DECORATIONS, JNEBlocks.SHROOMNIGHT.get(), "netherexp:shroomnight", null, "netherexp:nightspores", null);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, JNEItems.GLOWCHEESE.get(), 3).requires(JNEItems.LIGHTSPORES.get()).requires(JNEItems.NIGHTSPORES.get()).requires(Items.MILK_BUCKET).unlockedBy("has_glowspores", has(JNETags.Items.GLOWSPORES)).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, JNEItems.WRAITHING_FLESH.get()).requires(Items.ROTTEN_FLESH).requires(JNEItems.BANSHEE_POWDER.get()).unlockedBy("has_banshee_powder", has(JNEItems.BANSHEE_POWDER.get())).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, JNEItems.PHASMO_SHARD.get(), 2).requires(JNEItems.BANSHEE_POWDER.get()).requires(Items.SLIME_BALL).unlockedBy("has_banshee_powder", has(JNEItems.BANSHEE_POWDER.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, JNEItems.PHASMO_ARROW.get(), 2).define('#', JNEItems.PHASMO_SHARD.get()).define('X', Items.ARROW).pattern(" # ").pattern("#X#").pattern(" # ").unlockedBy("has_phasmo_shard", has(JNEItems.PHASMO_SHARD.get())).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, JNEItems.BANSHEE_POWDER.get(), 2).requires(JNEItems.BANSHEE_ROD.get()).unlockedBy("has_banshee_rod", has(JNEItems.BANSHEE_ROD.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, JNEItems.WILL_O_WISP.get(), 4).define('B', JNEItems.BANSHEE_POWDER.get()).define('#', JNEItems.WISP_BOTTLE.get()).pattern(" B ").pattern("B#B").pattern(" B ").unlockedBy("has_wisp_bottle", has(JNEItems.WISP_BOTTLE.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, JNEItems.NETHERITE_PLATING.get(), 64).define('G', Items.GOLD_INGOT).define('S', JNEItems.STRIDITE.get()).define('N', Items.NETHERITE_SCRAP).pattern("GS").pattern("SN").unlockedBy("has_stridite", has(JNEItems.STRIDITE.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, JNEItems.SHOTGUN_FIST.get()).define('N', Items.NETHERITE_INGOT).define('#', JNEItems.SHOTGUN_CORE.get()).define('S', Items.SKELETON_SKULL).pattern("N#S").pattern("N  ").unlockedBy("has_shotgun_core", has(JNEItems.SHOTGUN_CORE.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, JNEItems.SKULL_ON_A_STICK.get()).define('#', Items.FISHING_ROD).define('X', Items.SKELETON_SKULL).pattern("# ").pattern(" X").unlockedBy("has_skeleton_skull", has(Items.SKELETON_SKULL)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, JNEItems.ROASTED_BONE.get(), 3).define('C', JNEItems.CEREBRAGE.get()).define('W', Items.WARPED_FUNGUS).define('H', JNETags.Items.COOKED_HOGHAM).define('#', Items.BONE).define('F', Items.FIRE_CHARGE).pattern("CWH").pattern("###").pattern(" F ").unlockedBy("has_cooked_hogham", has(JNETags.Items.COOKED_HOGHAM)).save(output);

        // # Minecraft
        oneToOneConversionRecipe(output, Items.QUARTZ, Blocks.QUARTZ_BLOCK, "quartz", 4);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Blocks.MAGMA_BLOCK, 4).define('A', Items.MAGMA_CREAM).define('B', Blocks.NETHERRACK).pattern("AB").pattern("BA").unlockedBy("has_magma_cream", has(Items.MAGMA_CREAM)).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BROWN_DYE, 2).requires(Items.WHITE_DYE).requires(JNEItems.FOSSIL_FUEL.get()).unlockedBy("has_fossil_fuel", has(JNEItems.FOSSIL_FUEL.get())).group("brown_dye").save(output, getConversionRecipeName(Items.BROWN_DYE, JNEItems.FOSSIL_FUEL.get()));
        oneToOneConversionRecipe(output, Items.LIGHT_BLUE_DYE, JNEBlocks.SOUL_TORCHFLOWER.get(), "light_blue_dye");
        oneToOneConversionRecipe(output, Items.RED_DYE, JNEItems.WEEPING_HELIX.get(), "red_dye");
        oneToOneConversionRecipe(output, Items.CYAN_DYE, JNEItems.TWISTING_HELIX.get(), "cyan_dye");
        oneToOneConversionRecipe(output, Items.PUMPKIN_SEEDS, JNEBlocks.SORROWSQUASH.get(), "pumpkin_seeds");
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Blocks.TORCH, 4).define('X', JNEItems.FOSSIL_FUEL.get()).define('#', Items.STICK).pattern("X").pattern("#").unlockedBy("has_fossil_fuel", has(JNEItems.FOSSIL_FUEL.get())).save(output, "torch_from_fossil_fuel");
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Blocks.SOUL_TORCH, 4).define('X', JNEItems.FOSSIL_FUEL.get()).define('#', Items.STICK).define('S', ItemTags.SOUL_FIRE_BASE_BLOCKS).pattern("X").pattern("#").pattern("S").unlockedBy("has_fossil_fuel", has(JNEItems.FOSSIL_FUEL.get())).save(output, "soul_torch_from_fossil_fuel");

        // Cooking
        // # Blocks
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(JNEBlocks.SOUL_SLATE_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS, JNEBlocks.CRACKED_SOUL_SLATE_BRICKS.get(), 0.1f, 200);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(JNEBlocks.SMOOTH_NETHERRACK.get()), RecipeCategory.BUILDING_BLOCKS, Blocks.NETHERRACK, 0.1f, 200);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(JNEBlocks.RUSTY_NETHERITE_PLATED_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, JNEBlocks.NETHERITE_PLATED_BLOCK.get(), 0.1f, 200);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, JNEBlocks.CUT_NETHERITE_BLOCK.get(), 0.1f, 200);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(JNEBlocks.RUSTY_CUT_NETHERITE_SLAB.get()), RecipeCategory.BUILDING_BLOCKS, JNEBlocks.CUT_NETHERITE_SLAB.get(), 0.1f, 200);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(JNEBlocks.RUSTY_CUT_NETHERITE_STAIRS.get()), RecipeCategory.BUILDING_BLOCKS, JNEBlocks.CUT_NETHERITE_STAIRS.get(), 0.1f, 200);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(JNEBlocks.RUSTY_CUT_NETHERITE_PILLAR.get()), RecipeCategory.BUILDING_BLOCKS, JNEBlocks.CUT_NETHERITE_PILLAR.get(), 0.1f, 200);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(JNEBlocks.RUSTY_NETHERITE_GRATE.get()), RecipeCategory.BUILDING_BLOCKS, JNEBlocks.NETHERITE_GRATE.get(), 0.1f, 200);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.QUARTZ_BRICKS), RecipeCategory.BUILDING_BLOCKS, JNEBlocks.CRACKED_QUARTZ_BRICKS.get(), 0.1f, 200);

        // # Items
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(JNEItems.HOGHAM.get()), RecipeCategory.FOOD, JNEItems.COOKED_HOGHAM.get(), 0.35f, 200).unlockedBy("has_hogham", has(JNEItems.HOGHAM.get())).save(output);
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(JNEItems.HOGHAM.get()), RecipeCategory.FOOD, JNEItems.COOKED_HOGHAM.get(), 0.35f, 600).unlockedBy("has_hogham", has(JNEItems.HOGHAM.get())).save(output, "netherexp:cooked_hogham_from_campfire_cooking");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(JNEItems.HOGHAM.get()), RecipeCategory.FOOD, JNEItems.COOKED_HOGHAM.get(), 0.35f, 100).unlockedBy("has_hogham", has(JNEItems.HOGHAM.get())).save(output, "netherexp:cooked_hogham_from_smoking");

        // Smithing
        SmithingTrimRecipeBuilder.smithingTrim(Ingredient.of(JNEItems.RIFT_ARMOR_TRIM_SMITHING_TEMPLATE.get()), Ingredient.of(ItemTags.TRIMMABLE_ARMOR), Ingredient.of(ItemTags.TRIM_MATERIALS), RecipeCategory.COMBAT).unlocks("has_rift_armor_trim", has(JNEItems.RIFT_ARMOR_TRIM_SMITHING_TEMPLATE.get())).save(output, ResourceLocation.fromNamespaceAndPath(NetherExp.MOD_ID, "rift_armor_trim"));
        SmithingTrimRecipeBuilder.smithingTrim(Ingredient.of(JNEItems.SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE.get()), Ingredient.of(ItemTags.TRIMMABLE_ARMOR), Ingredient.of(ItemTags.TRIM_MATERIALS), RecipeCategory.COMBAT).unlocks("has_spirit_armor_trim", has(JNEItems.SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE.get())).save(output, ResourceLocation.fromNamespaceAndPath(NetherExp.MOD_ID, "spirit_armor_trim"));
        SmithingTrimRecipeBuilder.smithingTrim(Ingredient.of(JNEItems.VALOR_ARMOR_TRIM_SMITHING_TEMPLATE.get()), Ingredient.of(ItemTags.TRIMMABLE_ARMOR), Ingredient.of(ItemTags.TRIM_MATERIALS), RecipeCategory.COMBAT).unlocks("has_valor_armor_trim", has(JNEItems.VALOR_ARMOR_TRIM_SMITHING_TEMPLATE.get())).save(output, ResourceLocation.fromNamespaceAndPath(NetherExp.MOD_ID, "valor_armor_trim"));
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(JNEItems.PUMP_CHARGE_UPGRADE_SMITHING_TEMPLATE.get()), Ingredient.of(JNEItems.SHOTGUN_FIST.get()), Ingredient.of(JNEItems.TREACHEROUS_FLAME.get()), RecipeCategory.COMBAT, JNEItems.PUMP_CHARGE_SHOTGUN.get()).unlocks("has_pump_charge_upgrade_smithing_template", has(JNEItems.PUMP_CHARGE_UPGRADE_SMITHING_TEMPLATE.get())).save(output, ResourceLocation.fromNamespaceAndPath(NetherExp.MOD_ID, "pump_charge_shotgun"));
        
        // Stonecutting
        stonecutter(output, JNEBlocks.SOUL_SLATE.get(), JNEBlocks.SOUL_SLATE_SLAB.get(), 2);
        stonecutter(output, JNEBlocks.SOUL_SLATE.get(), JNEBlocks.SOUL_SLATE_STAIRS.get());
        stonecutter(output, JNEBlocks.SOUL_SLATE.get(), JNEBlocks.SOUL_SLATE_WALL.get());
        stonecutter(output, JNEBlocks.SOUL_SLATE.get(), JNEBlocks.SOUL_SLATE_BRICKS.get());
        stonecutter(output, JNEBlocks.SOUL_SLATE.get(), JNEBlocks.SOUL_SLATE_BRICK_SLAB.get(), 2);
        stonecutter(output, JNEBlocks.SOUL_SLATE.get(), JNEBlocks.SOUL_SLATE_BRICK_STAIRS.get());
        stonecutter(output, JNEBlocks.SOUL_SLATE.get(), JNEBlocks.SOUL_SLATE_BRICK_WALL.get());
        stonecutter(output, JNEBlocks.SOUL_SLATE.get(), JNEBlocks.SOUL_SLATE_BRICK_PILLAR.get());
        stonecutter(output, JNEBlocks.SOUL_SLATE.get(), JNEBlocks.CHISELED_SOUL_SLATE_BRICKS.get());
        stonecutter(output, JNEBlocks.SOUL_SLATE.get(), JNEBlocks.ETCHED_SOUL_SLATE_BRICKS.get());
        stonecutter(output, JNEBlocks.PALE_SOUL_SLATE.get(), JNEBlocks.SOUL_SLATE_TILE_SLAB.get(), 2);
        stonecutter(output, JNEBlocks.PALE_SOUL_SLATE.get(), JNEBlocks.SOUL_SLATE_TILE_STAIRS.get());
        stonecutter(output, JNEBlocks.PALE_SOUL_SLATE.get(), JNEBlocks.SOUL_SLATE_TILE_WALL.get());
        stonecutter(output, JNEBlocks.PALE_SOUL_SLATE.get(), JNEBlocks.CHISELED_SOUL_SLATE_TILES.get());
        stonecutter(output, JNEBlocks.PALE_SOUL_SLATE.get(), JNEBlocks.ETCHED_SOUL_SLATE_TILES.get());
        stonecutter(output, JNEBlocks.SOUL_SLATE_BRICKS.get(), JNEBlocks.SOUL_SLATE_BRICK_SLAB.get(), 2);
        stonecutter(output, JNEBlocks.SOUL_SLATE_BRICKS.get(), JNEBlocks.SOUL_SLATE_BRICK_STAIRS.get());
        stonecutter(output, JNEBlocks.SOUL_SLATE_BRICKS.get(), JNEBlocks.SOUL_SLATE_BRICK_WALL.get());
        stonecutter(output, JNEBlocks.SOUL_SLATE_BRICKS.get(), JNEBlocks.SOUL_SLATE_BRICK_PILLAR.get());
        stonecutter(output, JNEBlocks.SOUL_SLATE_BRICKS.get(), JNEBlocks.CHISELED_SOUL_SLATE_BRICKS.get());
        stonecutter(output, JNEBlocks.SOUL_SLATE_BRICKS.get(), JNEBlocks.ETCHED_SOUL_SLATE_BRICKS.get());
        stonecutter(output, JNEBlocks.ETCHED_SOUL_SLATE_BRICKS.get(), JNEBlocks.SOUL_SLATE_BRICKS.get());
        stonecutter(output, JNEBlocks.SOUL_SLATE_TILES.get(), JNEBlocks.SOUL_SLATE_TILE_SLAB.get(), 2);
        stonecutter(output, JNEBlocks.SOUL_SLATE_TILES.get(), JNEBlocks.SOUL_SLATE_TILE_STAIRS.get());
        stonecutter(output, JNEBlocks.SOUL_SLATE_TILES.get(), JNEBlocks.SOUL_SLATE_TILE_WALL.get());
        stonecutter(output, JNEBlocks.SOUL_SLATE_TILES.get(), JNEBlocks.CHISELED_SOUL_SLATE_TILES.get());
        stonecutter(output, JNEBlocks.SOUL_SLATE_TILES.get(), JNEBlocks.ETCHED_SOUL_SLATE_TILES.get());
        stonecutter(output, JNEBlocks.ETCHED_SOUL_SLATE_TILES.get(), JNEBlocks.SOUL_SLATE_TILES.get());
        stonecutter(output, JNEBlocks.SMOOTH_NETHERRACK.get(), Blocks.NETHERRACK);
        stonecutter(output, JNEBlocks.SMOOTH_NETHERRACK.get(), JNEBlocks.SMOOTH_NETHERRACK_SLAB.get(), 2);
        stonecutter(output, JNEBlocks.SMOOTH_NETHERRACK.get(), JNEBlocks.SMOOTH_NETHERRACK_STAIRS.get());
        stonecutter(output, JNEBlocks.SMOOTH_NETHERRACK.get(), JNEBlocks.SMOOTH_NETHERRACK_WALL.get());
        stonecutter(output, JNEBlocks.SMOOTH_NETHERRACK.get(), JNEBlocks.NETHERRACK_BRICKS.get());
        stonecutter(output, JNEBlocks.SMOOTH_NETHERRACK.get(), JNEBlocks.NETHERRACK_BRICK_SLAB.get(), 2);
        stonecutter(output, JNEBlocks.SMOOTH_NETHERRACK.get(), JNEBlocks.NETHERRACK_BRICK_STAIRS.get());
        stonecutter(output, JNEBlocks.SMOOTH_NETHERRACK.get(), JNEBlocks.NETHERRACK_BRICK_WALL.get());
        stonecutter(output, JNEBlocks.SMOOTH_NETHERRACK.get(), JNEBlocks.NETHERRACK_TILES.get());
        stonecutter(output, JNEBlocks.SMOOTH_NETHERRACK.get(), JNEBlocks.NETHERRACK_PILLAR.get());
        stonecutter(output, JNEBlocks.NETHERRACK_BRICKS.get(), JNEBlocks.NETHERRACK_BRICK_SLAB.get(), 2);
        stonecutter(output, JNEBlocks.NETHERRACK_BRICKS.get(), JNEBlocks.NETHERRACK_BRICK_STAIRS.get());
        stonecutter(output, JNEBlocks.NETHERRACK_BRICKS.get(), JNEBlocks.NETHERRACK_BRICK_WALL.get());
        stonecutter(output, JNEBlocks.NETHERRACK_BRICKS.get(), JNEBlocks.NETHERRACK_TILES.get());
        stonecutter(output, JNEBlocks.NETHERRACK_BRICKS.get(), JNEBlocks.NETHERRACK_PILLAR.get());
        stonecutter(output, Blocks.BASALT, JNEBlocks.BASALT_SLAB.get());
        stonecutter(output, Blocks.BASALT, JNEBlocks.BASALT_STAIRS.get());
        stonecutter(output, Blocks.BASALT, JNEBlocks.BASALT_WALL.get());
        stonecutter(output, Blocks.BASALT, JNEBlocks.POLISHED_BASALT_SLAB.get(), 2);
        stonecutter(output, Blocks.BASALT, JNEBlocks.POLISHED_BASALT_STAIRS.get());
        stonecutter(output, Blocks.BASALT, JNEBlocks.POLISHED_BASALT_WALL.get());
        stonecutter(output, Blocks.BASALT, JNEBlocks.POLISHED_BASALT_BRICKS.get());
        stonecutter(output, Blocks.BASALT, JNEBlocks.POLISHED_BASALT_BRICK_SLAB.get(), 2);
        stonecutter(output, Blocks.BASALT, JNEBlocks.POLISHED_BASALT_BRICK_STAIRS.get());
        stonecutter(output, Blocks.BASALT, JNEBlocks.POLISHED_BASALT_BRICK_WALL.get());
        stonecutter(output, Blocks.POLISHED_BASALT, JNEBlocks.POLISHED_BASALT_SLAB.get(), 2);
        stonecutter(output, Blocks.POLISHED_BASALT, JNEBlocks.POLISHED_BASALT_STAIRS.get());
        stonecutter(output, Blocks.POLISHED_BASALT, JNEBlocks.POLISHED_BASALT_WALL.get());
        stonecutter(output, Blocks.POLISHED_BASALT, JNEBlocks.POLISHED_BASALT_BRICKS.get());
        stonecutter(output, Blocks.POLISHED_BASALT, JNEBlocks.POLISHED_BASALT_BRICK_SLAB.get(), 2);
        stonecutter(output, Blocks.POLISHED_BASALT, JNEBlocks.POLISHED_BASALT_BRICK_STAIRS.get());
        stonecutter(output, Blocks.POLISHED_BASALT, JNEBlocks.POLISHED_BASALT_BRICK_WALL.get());
        stonecutter(output, JNEBlocks.POLISHED_BASALT_BRICKS.get(), JNEBlocks.POLISHED_BASALT_BRICK_SLAB.get(), 2);
        stonecutter(output, JNEBlocks.POLISHED_BASALT_BRICKS.get(), JNEBlocks.POLISHED_BASALT_BRICK_STAIRS.get());
        stonecutter(output, JNEBlocks.POLISHED_BASALT_BRICKS.get(), JNEBlocks.POLISHED_BASALT_BRICK_WALL.get());
        stonecutter(output, Blocks.RED_NETHER_BRICKS, Blocks.NETHER_BRICKS);
        stonecutter(output, Blocks.NETHER_BRICKS, JNEBlocks.NETHER_BRICK_PILLAR.get());
        stonecutter(output, Blocks.BLACKSTONE, JNEBlocks.POLISHED_BLACKSTONE_PILLAR.get());
        stonecutter(output, Blocks.POLISHED_BLACKSTONE, JNEBlocks.POLISHED_BLACKSTONE_PILLAR.get());
        stonecutter(output, JNEBlocks.BLUE_NETHER_BRICKS.get(), JNEBlocks.BLUE_NETHER_BRICK_SLAB.get(), 2);
        stonecutter(output, JNEBlocks.BLUE_NETHER_BRICKS.get(), JNEBlocks.BLUE_NETHER_BRICK_STAIRS.get());
        stonecutter(output, JNEBlocks.BLUE_NETHER_BRICKS.get(), JNEBlocks.BLUE_NETHER_BRICK_WALL.get());
        stonecutter(output, JNEBlocks.BLUE_NETHER_BRICKS.get(), Blocks.NETHER_BRICKS);
        stonecutter(output, RecipeCategory.DECORATIONS, Blocks.BONE_BLOCK, JNEBlocks.BONE_FENCE.get(), 4);
        stonecutter(output, RecipeCategory.DECORATIONS, Blocks.BONE_BLOCK, JNEBlocks.SKULL_BLOCK.get(), 1);
        stonecutter(output, Blocks.BONE_BLOCK, JNEBlocks.STACKED_BONES.get());
        stonecutter(output, Blocks.BONE_BLOCK, JNEBlocks.STACKED_BONE_SLAB.get(), 2);
        stonecutter(output, Blocks.BONE_BLOCK, JNEBlocks.STACKED_BONE_STAIRS.get());
        stonecutter(output, RecipeCategory.DECORATIONS, JNEBlocks.STACKED_BONES.get(), JNEBlocks.BONE_FENCE.get(), 4);
        stonecutter(output, RecipeCategory.DECORATIONS, JNEBlocks.STACKED_BONES.get(), JNEBlocks.SKULL_BLOCK.get(), 1);
        stonecutter(output, JNEBlocks.STACKED_BONES.get(), JNEBlocks.STACKED_BONE_SLAB.get(), 2);
        stonecutter(output, JNEBlocks.STACKED_BONES.get(), JNEBlocks.STACKED_BONE_STAIRS.get());
        stonecutter(output, RecipeCategory.DECORATIONS, JNEBlocks.WITHER_BONE_BLOCK.get(), JNEBlocks.WITHER_SKULL_BLOCK.get(), 1);
        stonecutter(output, JNEBlocks.WITHER_BONE_BLOCK.get(), JNEBlocks.STACKED_WITHER_BONES.get());
        stonecutter(output, JNEBlocks.WITHER_BONE_BLOCK.get(), JNEBlocks.STACKED_WITHER_BONE_SLAB.get(), 2);
        stonecutter(output, JNEBlocks.WITHER_BONE_BLOCK.get(), JNEBlocks.STACKED_WITHER_BONE_STAIRS.get());
        stonecutter(output, RecipeCategory.DECORATIONS, JNEBlocks.STACKED_WITHER_BONES.get(), JNEBlocks.WITHER_SKULL_BLOCK.get(), 1);
        stonecutter(output, JNEBlocks.STACKED_WITHER_BONES.get(), JNEBlocks.STACKED_WITHER_BONE_SLAB.get(), 2);
        stonecutter(output, JNEBlocks.STACKED_WITHER_BONES.get(), JNEBlocks.STACKED_WITHER_BONE_STAIRS.get());
    }

    protected static void stonecutter(RecipeOutput output, ItemLike ingredient, ItemLike result) {
        stonecutter(output, ingredient, result, 1);
    }
    
    protected static void stonecutter(RecipeOutput output, ItemLike ingredient, ItemLike result, int count) {
        stonecutter(output, RecipeCategory.BUILDING_BLOCKS, ingredient, result, count);
    }
    
    protected static void stonecutter(RecipeOutput output, RecipeCategory category, ItemLike ingredient, ItemLike result, int count) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ingredient), category, result, count)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(output, ResourceLocation.fromNamespaceAndPath(NetherExp.MOD_ID, getConversionRecipeName(result, ingredient)));
    }
    
    protected static void gargoyleCopy(RecipeOutput output, ItemLike gargoyle) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, gargoyle)
                .define('#', JNEItems.WRAITHING_FLESH.get())
                .define('S', gargoyle)
                .pattern("###")
                .pattern("#S#")
                .pattern("###")
                .unlockedBy(getHasName(gargoyle), has(gargoyle))
                .save(output);
    }

    protected static RecipeBuilder grateBuilder(ItemLike grateBlock, ItemLike material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, grateBlock, 4).define('M', material).pattern(" M ").pattern("M M").pattern(" M ");
    }

    protected static void pillar(RecipeOutput output, ItemLike pillar, ItemLike block) {
        pillarBuilder(pillar, block)
                .unlockedBy(getHasName(block), has(block))
                .save(output);
    }

    protected static RecipeBuilder pillarBuilder(ItemLike pillar, ItemLike block) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, pillar, 2)
                .define('#', block)
                .pattern("#")
                .pattern("#");
    }

    protected static void bricks(RecipeOutput recipeOutput, RecipeCategory category, ItemLike bricks, ItemLike block) {
        bricksBuilder(category, bricks, block)
                .unlockedBy(getHasName(block), has(block))
                .save(recipeOutput);
    }

    protected static RecipeBuilder bricksBuilder(RecipeCategory category, ItemLike bricks, ItemLike block) {
        return ShapedRecipeBuilder.shaped(category, bricks, 4)
                .define('#', block)
                .pattern("##")
                .pattern("##");
    }

    protected static void tiles(RecipeOutput recipeOutput, RecipeCategory category, ItemLike tiles, ItemLike block) {
        ShapedRecipeBuilder.shaped(category, tiles, 4)
                .define('#', block)
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(block), has(block))
                .save(recipeOutput);
    }

    protected static void twoByTwoPacker(RecipeOutput recipeOutput, RecipeCategory category, ItemLike packed, ItemLike unpacked, int count) {
        ShapedRecipeBuilder.shaped(category, packed, count)
                .define('#', unpacked)
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(unpacked), has(unpacked))
                .save(recipeOutput);
    }

    protected static void etched(RecipeOutput output, ItemLike etched, ItemLike block) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, etched, 8)
                .define('#', block)
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .unlockedBy(getHasName(block), has(block))
                .save(output);
    }

    protected static void transformEight(RecipeOutput output, RecipeCategory category, ItemLike result, ItemLike item, ItemLike catalyst) {
        transformEightBuilder(category, result, item, catalyst)
                .unlockedBy(getHasName(catalyst), has(catalyst))
                .save(output);
    }

    protected static RecipeBuilder transformEightBuilder(RecipeCategory category, ItemLike result, ItemLike item, ItemLike catalyst) {
        return ShapedRecipeBuilder.shaped(category, result, 8)
                .define('#', item)
                .define('C', catalyst)
                .pattern("###")
                .pattern("#C#")
                .pattern("###");
    }

    protected static void ancientBurningSkull(RecipeOutput output, ItemLike result, ItemLike block) {
        burningSkull(output, result, block, JNEItems.ANCIENT_TORCH.get());
    }

    protected static void soulBurningSkull(RecipeOutput output, ItemLike result, ItemLike block) {
        burningSkull(output, result, block, Items.SOUL_TORCH);
    }

    protected static void burningSkull(RecipeOutput output, ItemLike result, ItemLike block) {
        burningSkull(output, result, block, Items.TORCH);
    }

    protected static void burningSkull(RecipeOutput output, ItemLike result, ItemLike block, ItemLike fire) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result)
                .define('#', block)
                .define('T', fire)
                .pattern("#")
                .pattern("T")
                .unlockedBy(getHasName(block), has(block))
                .save(output);
    }
}
