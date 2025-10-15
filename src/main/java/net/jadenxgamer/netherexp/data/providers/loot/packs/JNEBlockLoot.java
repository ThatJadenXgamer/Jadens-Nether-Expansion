package net.jadenxgamer.netherexp.data.providers.loot.packs;

import net.jadenxgamer.netherexp.core.block.*;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import javax.swing.plaf.nimbus.State;
import java.util.Set;

public final class JNEBlockLoot extends BlockLootSubProvider {

    /**
     * Create a new block loot sub provider
     *
     * @param registries a {@linkplain HolderLookup.Provider} supplying the registries
     */
    public JNEBlockLoot(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
    }

    /**
     * Get all blocks we want to generate loot for, used for verification so we don't miss any.
     *
     * @return an {@linkplain Iterable} of blocks
     */
    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return JNEBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::get).map(b -> (Block) b).toList();
    }

    @Override
    protected void generate() {

        // Soul Slate
        dropSelf(JNEBlocks.SOUL_SLATE.get());
        add(JNEBlocks.PALE_SOUL_SLATE.get(), block -> createSingleItemTableWithSilkTouch(block, JNEBlocks.SOUL_SLATE.get()));
        add(JNEBlocks.SOUL_SLATE_SLAB.get(), this::createSlabItemTable);
        dropSelf(JNEBlocks.SOUL_SLATE_STAIRS.get());
        dropSelf(JNEBlocks.SOUL_SLATE_WALL.get());

        // Soul Slate Bricks
        dropSelf(JNEBlocks.SOUL_SLATE_BRICKS.get());
        add(JNEBlocks.SOUL_SLATE_BRICK_SLAB.get(), this::createSlabItemTable);
        dropSelf(JNEBlocks.SOUL_SLATE_BRICK_STAIRS.get());
        dropSelf(JNEBlocks.SOUL_SLATE_BRICK_WALL.get());
        dropSelf(JNEBlocks.ETCHED_SOUL_SLATE_BRICKS.get());
        dropSelf(JNEBlocks.CHISELED_SOUL_SLATE_BRICKS.get());
        dropSelf(JNEBlocks.SOUL_SLATE_BRICK_PILLAR.get());
        dropSelf(JNEBlocks.CRACKED_SOUL_SLATE_BRICKS.get());

        // Soul Slate Tiles
        dropSelf(JNEBlocks.SOUL_SLATE_TILES.get());
        add(JNEBlocks.SOUL_SLATE_TILE_SLAB.get(), this::createSlabItemTable);
        dropSelf(JNEBlocks.SOUL_SLATE_TILE_STAIRS.get());
        dropSelf(JNEBlocks.SOUL_SLATE_TILE_WALL.get());
        dropSelf(JNEBlocks.ETCHED_SOUL_SLATE_TILES.get());
        dropSelf(JNEBlocks.CHISELED_SOUL_SLATE_TILES.get());

        // Fossil Ores
        add(JNEBlocks.FOSSIL_FUEL_ORE.get(), block -> createOreDrop(block, JNEItems.FOSSIL_FUEL.get()));
        add(JNEBlocks.FOSSIL_ORE.get(), block -> createSingleItemTableWithSilkTouch(block, Items.BONE, ConstantValue.exactly(1))); // explicitly setting count applies explosion decay

        // Soul Sand Valley Additions
        // TODO: BLOCKSTATE FUCKERY ???
        add(JNEBlocks.SOUL_SWIRLS.get(), BlockLootSubProvider::createShearsOnlyDrop);
        add(JNEBlocks.SOUL_CANDLE.get(), this::createCandleDrops); // generates an entry for 4 candles, shouldn't matter though
        dropWhenSilkTouch(JNEBlocks.SOUL_GLASS.get());
        dropSelf(JNEBlocks.DISCERNMENT_GLASS.get());
        add(JNEBlocks.ECTO_SOUL_SAND.get(), block -> createSingleItemTableWithSilkTouch(block, Items.SOUL_SAND));
        add(JNEBlocks.SUSPICIOUS_SOUL_SAND.get(), noDrop());
        dropSelf(JNEBlocks.SOUL_MAGMA_BLOCK.get());
        // TODO: FIX THIS
        add(
                JNEBlocks.SOUL_SOIL_LAYER.get(), (block) ->
                        LootTable.lootTable().withPool(LootPool.lootPool()
                                .add(AlternativesEntry.alternatives(
                                        JNELayerBlock.LAYERS.getPossibleValues(),
                                        (layers) -> layers == 8 ?
                                                LootItem.lootTableItem(Blocks.SOUL_SOIL) :
                                                LootItem.lootTableItem(block)
                                                        .when(hasProperty(block, JNELayerBlock.LAYERS, layers))
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly((float) layers)))))));

        dropOther(JNEBlocks.ECTOPLASM_CAULDRON.get(), Items.CAULDRON);
        dropSelf(JNEBlocks.DRIFTING_SOULS.get());

        // Black Ice
        dropSelf(JNEBlocks.BLACK_ICE.get());
        dropSelf(JNEBlocks.BLACK_ICICLE.get());
        dropWhenSilkTouch(JNEBlocks.THIN_BLACK_ICE.get());
        add(
                JNEBlocks.SOUL_PERMAFROST.get(),
                block -> createSilkTouchOnlyTable(block)
                        .withPool(LootPool
                                .lootPool()
                                .add(item(Items.SOUL_SOIL, 1))
                                .when(doesNotHaveSilkTouch())
                                .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.SHOVELS))))
                        .withPool(LootPool
                                .lootPool()
                                .add(item(JNEBlocks.BLACK_ICICLE.get(), 1, 2, 4))
                                .when(doesNotHaveSilkTouch())
                                .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.PICKAXES)))));

        // Path Blocks
        dropOther(JNEBlocks.SOUL_PATH.get(), Items.SOUL_SOIL);
        dropOther(JNEBlocks.CRIMSON_NYLIUM_PATH.get(), Items.NETHERRACK);
        dropOther(JNEBlocks.WARPED_NYLIUM_PATH.get(), Items.NETHERRACK);

        // Smooth Netherrack
        dropSelf(JNEBlocks.SMOOTH_NETHERRACK.get());
        add(JNEBlocks.SMOOTH_NETHERRACK_SLAB.get(), this::createSlabItemTable);
        dropSelf(JNEBlocks.SMOOTH_NETHERRACK_STAIRS.get());
        dropSelf(JNEBlocks.SMOOTH_NETHERRACK_WALL.get());

        // Netherrack Bricks
        dropSelf(JNEBlocks.NETHERRACK_BRICKS.get());
        add(JNEBlocks.NETHERRACK_BRICK_SLAB.get(), this::createSlabItemTable);
        dropSelf(JNEBlocks.NETHERRACK_BRICK_STAIRS.get());
        dropSelf(JNEBlocks.NETHERRACK_BRICK_WALL.get());
        dropSelf(JNEBlocks.NETHERRACK_TILES.get());
        dropSelf(JNEBlocks.NETHERRACK_PILLAR.get());

        // Basalt
        add(JNEBlocks.BASALT_SLAB.get(), this::createSlabItemTable);
        dropSelf(JNEBlocks.BASALT_STAIRS.get());
        dropSelf(JNEBlocks.BASALT_WALL.get());

        // Polished Basalt
        add(JNEBlocks.POLISHED_BASALT_SLAB.get(), this::createSlabItemTable);
        dropSelf(JNEBlocks.POLISHED_BASALT_STAIRS.get());
        dropSelf(JNEBlocks.POLISHED_BASALT_WALL.get());

        // Polished Basalt Bricks
        dropSelf(JNEBlocks.POLISHED_BASALT_BRICKS.get());
        add(JNEBlocks.POLISHED_BASALT_BRICK_SLAB.get(), this::createSlabItemTable);
        dropSelf(JNEBlocks.POLISHED_BASALT_BRICK_STAIRS.get());
        dropSelf(JNEBlocks.POLISHED_BASALT_BRICK_WALL.get());

        // Netherite
        dropSelf(JNEBlocks.NETHERITE_PLATED_BLOCK.get());
        dropSelf(JNEBlocks.CUT_NETHERITE_BLOCK.get());
        add(JNEBlocks.CUT_NETHERITE_SLAB.get(), this::createSlabItemTable);
        dropSelf(JNEBlocks.CUT_NETHERITE_STAIRS.get());
        dropSelf(JNEBlocks.CUT_NETHERITE_PILLAR.get());
        dropSelf(JNEBlocks.NETHERITE_GRATE.get());

        // Rusty Netherite
        dropSelf(JNEBlocks.RUSTY_NETHERITE_PLATED_BLOCK.get());
        dropSelf(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get());
        add(JNEBlocks.RUSTY_CUT_NETHERITE_SLAB.get(), this::createSlabItemTable);
        dropSelf(JNEBlocks.RUSTY_CUT_NETHERITE_STAIRS.get());
        dropSelf(JNEBlocks.RUSTY_CUT_NETHERITE_PILLAR.get());
        dropSelf(JNEBlocks.RUSTY_NETHERITE_GRATE.get());

        // Claret Woodset
        dropSelf(JNEBlocks.CEREBRAGE_CLARET_STEM.get());
        dropSelf(JNEBlocks.CEREBRAGE_CLARET_HYPHAE.get());
        dropSelf(JNEBlocks.STRIPPED_CLARET_STEM.get());
        dropSelf(JNEBlocks.STRIPPED_CLARET_HYPHAE.get());
        dropSelf(JNEBlocks.CLARET_PLANKS.get());
        add(JNEBlocks.CLARET_SLAB.get(), this::createSlabItemTable);
        dropSelf(JNEBlocks.CLARET_STAIRS.get());
        dropSelf(JNEBlocks.CLARET_FENCE.get());
        dropSelf(JNEBlocks.CLARET_FENCE_GATE.get());
        add(JNEBlocks.CLARET_DOOR.get(), this::createDoorTable);
        dropSelf(JNEBlocks.CLARET_TRAPDOOR.get());
        dropSelf(JNEBlocks.CLARET_BUTTON.get());
        dropSelf(JNEBlocks.CLARET_PRESSURE_PLATE.get());
        dropSelf(JNEBlocks.CLARET_SIGN.get());
        dropSelf(JNEBlocks.CLARET_HANGING_SIGN.get());

        // Storage Blocks
        add(JNEBlocks.MAGMA_CREAM_BLOCK.get(), block -> createSingleItemTableWithSilkTouch(block, Items.MAGMA_CREAM, UniformGenerator.between(1, 3)));

        // Quartz Blocks
        add(
                JNEBlocks.QUARTZ_CRYSTAL.get(),
                block -> createSilkTouchDispatchTable(
                        block,
                        applyExplosionDecay(
                                block,
                                LootItem
                                        .lootTableItem(Items.QUARTZ)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between((float) 1, (float) 3)))
                                        .apply(ApplyBonusCount.addOreBonusCount(registries.holderOrThrow(Enchantments.FORTUNE))))));

        dropSelf(JNEBlocks.QUARTZ_CRYSTAL_BLOCK.get());
        dropSelf(JNEBlocks.CRACKED_QUARTZ_BRICKS.get());
        dropSelf(JNEBlocks.CHISELED_QUARTZ_PILLAR.get());

        // Nether Bricks
        dropSelf(JNEBlocks.NETHER_BRICK_PILLAR.get());
        dropSelf(JNEBlocks.RED_MIXED_NETHER_BRICKS.get());

        // Blue Nether Bricks
        dropSelf(JNEBlocks.BLUE_MIXED_NETHER_BRICKS.get());
        dropSelf(JNEBlocks.BLUE_NETHER_BRICKS.get());
        add(JNEBlocks.BLUE_NETHER_BRICK_SLAB.get(), this::createSlabItemTable);
        dropSelf(JNEBlocks.BLUE_NETHER_BRICK_STAIRS.get());
        dropSelf(JNEBlocks.BLUE_NETHER_BRICK_WALL.get());

        // Farming & Food
        // TODO: CHECK THIS (seems good though)
        add(
                JNEBlocks.WARPED_WART.get(),
                (block) -> LootTable
                        .lootTable()
                        .withPool(applyExplosionDecay(
                                block,
                                LootPool
                                        .lootPool()
                                        .add(LootItem
                                                .lootTableItem(JNEBlocks.WARPED_WART.get())
                                                .apply(SetItemCountFunction
                                                        .setCount(UniformGenerator.between(2.0F, 4.0F))
                                                        .when(LootItemBlockStatePropertyCondition
                                                                .hasBlockStateProperties(block)
                                                                .setProperties(StatePropertiesPredicate.Builder
                                                                        .properties()
                                                                        .hasProperty(WarpedWartBlock.AGE, 3)
                                                                        .hasProperty(WarpedWartBlock.HALF, DoubleBlockHalf.LOWER))))
                                                .apply(ApplyBonusCount
                                                        .addUniformBonusCount(registries.holderOrThrow(Enchantments.FORTUNE))
                                                        .when(LootItemBlockStatePropertyCondition
                                                                .hasBlockStateProperties(block)
                                                                .setProperties(StatePropertiesPredicate.Builder
                                                                        .properties()
                                                                        .hasProperty(WarpedWartBlock.AGE, 3)
                                                                        .hasProperty(WarpedWartBlock.HALF, DoubleBlockHalf.LOWER))))))));

        add(JNEBlocks.WRAITHING_LESION.get(), noDrop());
        dropSelf(JNEBlocks.SOUL_TORCHFLOWER.get());
        dropOther(JNEBlocks.SOUL_TORCHFLOWER_CROP.get(), Items.TORCHFLOWER_SEEDS);
        dropSelf(JNEBlocks.SORROWEED.get());
        dropSelf(JNEBlocks.SORROWSQUASH.get());
        dropSelf(JNEBlocks.CARVED_SORROWSQUASH.get());
        dropSelf(JNEBlocks.GHOUL_O_LANTERN.get());
        add(
                JNEBlocks.SORROWSQUASH_STEM.get(),
                block -> LootTable
                        .lootTable()
                        .withPool(LootPool
                                .lootPool()
                                .add(item(Items.PUMPKIN_SEEDS, 1))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(registries.holderOrThrow(Enchantments.FORTUNE), 0.33f, 0.55f, 0.77f, 1f))));

        add(
                JNEBlocks.SORROWSQUASH_STEM_PLANT.get(),
                block -> LootTable
                        .lootTable()
                        .withPool(LootPool
                                .lootPool()
                                .add(item(Items.PUMPKIN_SEEDS, 1))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(registries.holderOrThrow(Enchantments.FORTUNE), 0.33f, 0.55f, 0.77f, 1f))));

        add(
                JNEBlocks.CEREBRAGE_SKULL.get(),
                block -> LootTable
                        .lootTable()
                        .withPool(applyExplosionDecay(block, LootPool.lootPool().add(item(Items.SKELETON_SKULL, 1))))
                        .withPool(LootPool.lootPool().add(item(JNEItems.CEREBRAGE_SEEDS.get(), 1)))
                        .withPool(LootPool
                                .lootPool()
                                .add(item(JNEItems.CEREBRAGE.get(), 1, 3, 6))
                                .when(hasProperty(block, CerebrageSkullBlock.AGE, 3))));

        // Shroomlight
        dropSelf(JNEBlocks.SHROOMNIGHT.get());

        // Wart Beards
        add(JNEBlocks.NETHER_WART_BEARD.get(), BlockLootSubProvider::createShearsOnlyDrop);
        add(JNEBlocks.WARPED_WART_BEARD.get(), BlockLootSubProvider::createShearsOnlyDrop);

        // Ivy
        add(
                JNEBlocks.WEEPING_IVY.get(),
                block -> createMultifaceBlockDrops(block, HAS_SHEARS).withPool(LootPool
                        .lootPool()
                        .add(item(JNEItems.WEEPING_HELIX.get(), 1).when(hasProperty(block, IvyBlock.HELIX, true)))));

        add(
                JNEBlocks.TWISTING_IVY.get(),
                block -> createMultifaceBlockDrops(block, HAS_SHEARS).withPool(LootPool
                        .lootPool()
                        .add(item(JNEItems.TWISTING_HELIX.get(), 1).when(hasProperty(block, IvyBlock.HELIX, true)))));

        // Sprouts
        add(JNEBlocks.CRIMSON_SPROUTS.get(), BlockLootSubProvider::createShearsOnlyDrop);

        // Sporeshrooms and Geysers
        dropSelf(JNEBlocks.CRIMSON_SPORESHROOM.get());
        dropSelf(JNEBlocks.WARPED_SPORESHROOM.get());
        dropSelf(JNEBlocks.SOULED_GEYSER.get());
        dropSelf(JNEBlocks.BASALTIC_GEYSER.get());

        // Potted Blocks
        dropPottedContents(JNEBlocks.POTTED_SOUL_SWIRLS.get());
        dropPottedContents(JNEBlocks.POTTED_CRIMSON_SPORESHROOM.get());
        dropPottedContents(JNEBlocks.POTTED_WARPED_SPORESHROOM.get());
        dropPottedContents(JNEBlocks.POTTED_SOUL_TORCHFLOWER.get());

        // Blackstone
        dropSelf(JNEBlocks.POLISHED_BLACKSTONE_PILLAR.get());
        dropSelf(JNEBlocks.POLISHED_BLACKSTONE_FENCE.get());
        dropSelf(JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICKS.get());
        add(JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICK_SLAB.get(), this::createSlabItemTable);
        dropSelf(JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICK_STAIRS.get());
        dropSelf(JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICK_WALL.get());
        dropSelf(JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICKS.get());
        add(JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICK_SLAB.get(), this::createSlabItemTable);
        dropSelf(JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICK_STAIRS.get());
        dropSelf(JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICK_WALL.get());

        // Bones
        dropSelf(JNEBlocks.SKELETON_SKULL_CANDLE.get());
        dropSelf(JNEBlocks.SOUL_SKELETON_SKULL_CANDLE.get());
        dropSelf(JNEBlocks.ANCIENT_SKELETON_SKULL_CANDLE.get());
        add(
                JNEBlocks.BONE_PIKE.get(),
                block -> LootTable
                        .lootTable()
                        .withPool(LootPool
                                .lootPool()
                                .add(AlternativesEntry.alternatives(
                                        AlternativesEntry
                                                .alternatives(
                                                        BonePikeBlock.BONES.getPossibleValues(),
                                                        bones -> LootItem
                                                                .lootTableItem(block)
                                                                .when(hasProperty(block, BonePikeBlock.BONES, bones))
                                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly((float) bones))))
                                                .when(hasSilkTouch()),
                                        AlternativesEntry
                                                .alternatives(
                                                        BonePikeBlock.BONES.getPossibleValues(),
                                                        bones -> LootItem
                                                                .lootTableItem(Items.BONE)
                                                                .when(hasProperty(block, BonePikeBlock.BONES, bones))
                                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly((float) bones))))
                                                .when(doesNotHaveSilkTouch())))));

        dropSelf(JNEBlocks.BONE_FENCE.get());
        dropSelf(JNEBlocks.SKULL_BLOCK.get());
        dropSelf(JNEBlocks.BURNING_SKULL_BLOCK.get());
        dropSelf(JNEBlocks.SOUL_BURNING_SKULL_BLOCK.get());
        dropSelf(JNEBlocks.ANCIENT_BURNING_SKULL_BLOCK.get());
        dropSelf(JNEBlocks.STACKED_BONES.get());
        add(JNEBlocks.STACKED_BONE_SLAB.get(), this::createSlabItemTable);
        dropSelf(JNEBlocks.STACKED_BONE_STAIRS.get());

        // Wither Bones
        dropSelf(JNEBlocks.WITHER_BONE_BLOCK.get());
        dropSelf(JNEBlocks.WITHER_SKULL_BLOCK.get());
        dropSelf(JNEBlocks.BURNING_WITHER_SKULL_BLOCK.get());
        dropSelf(JNEBlocks.SOUL_BURNING_WITHER_SKULL_BLOCK.get());
        dropSelf(JNEBlocks.ANCIENT_BURNING_WITHER_SKULL_BLOCK.get());
        dropSelf(JNEBlocks.STACKED_WITHER_BONES.get());
        add(JNEBlocks.STACKED_WITHER_BONE_SLAB.get(), this::createSlabItemTable);
        dropSelf(JNEBlocks.STACKED_WITHER_BONE_STAIRS.get());

        // Sanctum Decorations
        add(JNEBlocks.OSSIFIED_GARGOYLE_STATUE.get(), this::createDoorTable);
        add(JNEBlocks.TRAMPLE_GARGOYLE_STATUE.get(), this::createDoorTable);
        add(JNEBlocks.PHASE_GARGOYLE_STATUE.get(), this::createDoorTable);
        add(JNEBlocks.GHOUL_GARGOYLE_STATUE.get(), this::createDoorTable);
        add(JNEBlocks.WRETCHED_GARGOYLE_STATUE.get(), this::createDoorTable);
        add(JNEBlocks.TREACHEROUS_GARGOYLE_STATUE.get(), this::createDoorTable);
        add(JNEBlocks.CIRRIPEDIA_GARGOYLE_STATUE.get(), this::createDoorTable);
        add(JNEBlocks.OCCULT_GARGOYLE_STATUE.get(), this::createDoorTable);
        add(JNEBlocks.SEALED_GARGOYLE_STATUE.get(), this::createDoorTable);
        add(JNEBlocks.OBFUSCATED_GARGOYLE_STATUE.get(), this::createDoorTable);
        dropSelf(JNEBlocks.INSCRIBED_PANEL.get());
        dropSelf(JNEBlocks.SHOTGUN_BARREL.get());

        // Ancient Fire
        dropSelf(JNEBlocks.ANCIENT_WAX_BLOCK.get());
        dropSelf(JNEBlocks.ANCIENT_TORCH.get());
        dropSelf(JNEBlocks.ANCIENT_LANTERN.get());
        add(JNEBlocks.ANCIENT_CAMPFIRE.get(), block -> this.createSilkTouchDispatchTable(block, this.applyExplosionCondition(block, LootItem.lootTableItem(JNEItems.ANCIENT_WAX.get()))));
        dropSelf(JNEBlocks.ANCIENT_CANDLE.get());

        dropSelf(JNEBlocks.OCHRE_FROGMIST.get());
        dropSelf(JNEBlocks.PEARLESCENT_FROGMIST.get());
        dropSelf(JNEBlocks.VERDANT_FROGMIST.get());
    }

    private static <T extends Comparable<T> & StringRepresentable> LootItemCondition.Builder hasProperty(Block block, Property<T> property, T value) {
        return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, value));
    }

    private static LootItemCondition.Builder hasProperty(Block block, IntegerProperty property, int value) {
        return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, value));
    }

    private static LootItemCondition.Builder hasProperty(Block block, BooleanProperty property, boolean value) {
        return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, value));
    }

    /**
     * Get a {@linkplain LootPoolSingletonContainer.Builder} with the item and weight set
     *
     * @param item   the item
     * @param weight the weight
     * @return the builder
     */
    private static LootPoolSingletonContainer.Builder<?> item(ItemLike item, int weight) {
        return LootItem.lootTableItem(item)
                .setWeight(weight);
    }

    /**
     * Get a {@linkplain LootPoolSingletonContainer.Builder} with the item and weight set, also applies a {@linkplain SetItemCountFunction} with a constant value
     *
     * @param item   the item
     * @param weight the weight
     * @param count  the count
     * @return the builder
     */
    private static LootPoolSingletonContainer.Builder<?> item(ItemLike item, int weight, float count) {
        return item(item, weight)
                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(count)));
    }

    /**
     * Get a {@linkplain LootPoolSingletonContainer.Builder} with the item and weight set, also applies a {@linkplain SetItemCountFunction} with a uniform random value
     *
     * @param item   the item
     * @param weight the weight
     * @param min    the minimum count
     * @param max    the maximum count
     * @return the builder
     */
    private static LootPoolSingletonContainer.Builder<?> item(ItemLike item, int weight, float min, float max) {
        return item(item, weight)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)));
    }

}
