package net.jadenxgamer.netherexp.data;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.block.*;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class JNELootTableProvider extends LootTableProvider {

    public JNELootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Set.of(), List.of(
                new SubProviderEntry(ArchaeologyLoot::new, LootContextParamSets.ARCHAEOLOGY),
                new SubProviderEntry(BlockLoot::new, LootContextParamSets.BLOCK)
        ), registries);
    }

    private record ArchaeologyLoot(HolderLookup.Provider registries) implements LootTableSubProvider {

        @Override
        public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
            output.accept(key("basalt_deltas"), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                                      .add(item(Items.MAGMA_CREAM, 25, 1, 4))
                                      .add(item(Items.GLOWSTONE_DUST, 15, 2, 9))
                                      .add(item(Blocks.BASALT, 20, 2, 8))
                                      .add(item(Blocks.BLACKSTONE, 25, 3, 6))
                                      .add(item(Items.QUARTZ, 8, 1, 6))
                                      .add(item(Items.GOLD_NUGGET, 10, 7, 9))
                                      .add(item(JNEItems.SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 1))));
            output.accept(key("bastion_remnant"), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                                      .add(item(Items.GLOWSTONE_DUST, 15, 3, 6))
                                      .add(item(Items.SPECTRAL_ARROW, 8, 8))
                                      .add(item(Items.ENCHANTED_GOLDEN_APPLE, 1))
                                      .add(item(Items.GOLDEN_APPLE, 3))
                                      .add(item(Items.GOLDEN_CARROT, 5, 1, 4))
                                      .add(item(Items.GOLD_INGOT, 15, 2, 7))
                                      .add(item(JNEItems.HOGHAM.get(), 15, 4))
                                      .add(item(Blocks.GILDED_BLACKSTONE, 15, 3))
                                      .add(item(Blocks.BLACKSTONE, 20, 5, 8))
                                      .add(item(Blocks.CRYING_OBSIDIAN, 5, 2, 5))
                                      .add(item(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, 1))));
            output.accept(key("beast_skull_common"), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                                      .add(item(Items.GOLDEN_APPLE, 5, 1, 2))
                                      .add(item(Items.GOLDEN_CARROT, 10, 2, 8))
                                      .add(item(Items.GOLD_INGOT, 15, 2, 7))
                                      .add(item(Items.SPECTRAL_ARROW, 30, 3, 14))
                                      .add(item(Items.GOLD_NUGGET, 50, 2, 26))
                                      .add(item(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, 10))
                                      .add(item(Items.ENCHANTED_GOLDEN_APPLE, 1))));
            output.accept(key("beast_skull_rare"), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                                      .add(item(Items.GOLDEN_APPLE, 6, 1, 2))
                                      .add(item(Items.GOLD_INGOT, 15, 2, 7))
                                      .add(item(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, 5))
                                      .add(item(Items.ENCHANTED_GOLDEN_APPLE, 1))));
            output.accept(key("black_ice_glaciers"), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                                      .add(item(JNEBlocks.BLACK_ICICLE.get(), 15, 2, 6))
                                      .add(item(Items.CHAINMAIL_BOOTS, 5).apply(new SetEnchantmentsFunction.Builder().withEnchantment(registries.holderOrThrow(Enchantments.SOUL_SPEED), ConstantValue.exactly(1))))
                                      .add(item(Items.BONE, 10, 1, 4))
                                      .add(item(JNEItems.FOSSIL_FUEL.get(), 10, 2, 4))
                                      .add(item(JNEItems.WRAITHING_FLESH.get(), 10, 3, 4))
                                      .add(item(JNEItems.STRIDITE.get(), 5, 2, 4))
                                      .add(item(Items.QUARTZ, 8, 1, 8))
                                      .add(item(Items.GOLD_NUGGET, 10, 7, 9))
                                      .add(item(Items.GUNPOWDER, 8, 2, 5))
                                      .add(item(Items.SKELETON_SKULL, 1))
                                      .add(item(Items.TIPPED_ARROW, 10, 4, 9).apply(SetPotionFunction.setPotion(Potions.SLOW_FALLING)))
                                      .add(item(JNEItems.SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 1))));
            output.accept(key("crimson_forest"), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                                      .add(item(Items.LEATHER, 10, 3))
                                      .add(item(JNEItems.HOGHAM.get(), 15, 1, 3))
                                      .add(item(Items.GOLD_INGOT, 8, 1, 2))
                                      .add(item(Items.CROSSBOW, 2).apply(EnchantRandomlyFunction.randomEnchantment()))
                                      .add(item(Items.WARPED_FUNGUS, 8))
                                      .add(item(Items.WEEPING_VINES, 15, 1, 4))
                                      .add(item(Items.CRIMSON_FUNGUS, 10, 1, 2))
                                      .add(item(Items.QUARTZ, 8, 1, 6))
                                      .add(item(Items.GOLD_NUGGET, 10, 7, 9))
                                      .add(item(JNEItems.SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 1))));
            output.accept(key("devils_bluff"), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                                      .add(item(Items.BONE, 16, 2, 5))
                                      .add(item(Items.SKELETON_SKULL, 5, 1))
                                      .add(item(Blocks.NETHERRACK, 8, 7))
                                      .add(item(JNEItems.CEREBRAGE_SEEDS.get(), 8))
                                      .add(item(Items.ROTTEN_FLESH, 16, 2, 8))));
            output.accept(key("fortress"), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                                      .add(item(Items.NETHER_WART, 20, 1, 2))
                                      .add(item(Items.ROTTEN_FLESH, 15, 1, 5))
                                      .add(item(Items.MAGMA_CREAM, 8, 1, 2))
                                      .add(item(Items.BLAZE_POWDER, 10, 1, 3))
                                      .add(item(Items.BONE, 15, 1, 4))
                                      .add(item(Items.NETHER_BRICK, 25, 4, 7))
                                      .add(item(Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE, 1))));
            output.accept(key("mega_fossil_common"), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                                      .add(item(Items.BONE, 10, 1, 4))
                                      .add(item(JNEItems.WRAITHING_FLESH.get(), 10, 3, 4))
                                      .add(item(JNEItems.STRIDITE.get(), 5, 2, 4))
                                      .add(item(Items.QUARTZ, 8, 1, 8))
                                      .add(item(Items.SKELETON_SKULL, 4))
                                      .add(item(Items.ARROW, 8, 4, 9))
                                      .add(item(JNEItems.SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 5))
                                      .add(item(JNEItems.SPECTRE_POTTERY_SHERD.get(), 10))
                                      .add(item(JNEItems.MARIONETTE_POTTERY_SHERD.get(), 10))));
            output.accept(key("mega_fossil_rare"), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                                      .add(item(Items.LEATHER, 5, 2, 4))
                                      .add(item(Items.GOLD_INGOT, 10, 3, 6))
                                      .add(item(JNEItems.WRAITHING_FLESH.get(), 4, 6, 8))
                                      .add(item(Items.QUARTZ, 3, 1, 8))
                                      .add(item(Items.SKELETON_SKULL, 8))
                                      .add(item(Items.SPECTRAL_ARROW, 8, 4, 9))
                                      .add(item(JNEItems.SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 8))));
            output.accept(key("nether_wastes"), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                                      .add(item(Items.GLOWSTONE_DUST, 10, 1, 5))
                                      .add(item(Items.NETHERRACK, 25, 1, 5))
                                      .add(item(Items.RED_MUSHROOM, 10, 1, 4))
                                      .add(item(Items.BROWN_MUSHROOM, 10, 1, 4))
                                      .add(item(Items.QUARTZ, 8, 1, 6))
                                      .add(item(Items.GOLD_NUGGET, 10, 7, 9))
                                      .add(item(JNEItems.SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 1))));
            output.accept(key("soul_sand_valley"), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                                      .add(item(Items.CHAINMAIL_BOOTS, 5).apply(new SetEnchantmentsFunction.Builder().withEnchantment(registries.holderOrThrow(Enchantments.SOUL_SPEED), ConstantValue.exactly(1))))
                                      .add(item(Items.BONE, 10, 1, 4))
                                      .add(item(JNEItems.FOSSIL_FUEL.get(), 10, 2, 4))
                                      .add(item(JNEItems.WRAITHING_FLESH.get(), 10, 3, 4))
                                      .add(item(JNEItems.STRIDITE.get(), 5, 2, 4))
                                      .add(item(Items.QUARTZ, 8, 1, 8))
                                      .add(item(Items.GOLD_NUGGET,10, 7, 9))
                                      .add(item(Items.GUNPOWDER, 8, 2, 5))
                                      .add(item(Items.SKELETON_SKULL, 1))
                                      .add(item(Items.ARROW, 8, 4, 9))
                                      .add(item(JNEItems.SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 1))));
            output.accept(key("warped_forest"), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                                      .add(item(Items.GOLD_INGOT, 8, 1, 2))
                                      .add(item(Items.ENDER_PEARL, 8, 1, 2))
                                      .add(item(Items.CRIMSON_FUNGUS, 8))
                                      .add(item(Items.TWISTING_VINES, 25, 1, 4))
                                      .add(item(Items.WARPED_FUNGUS, 15, 1, 2))
                                      .add(item(Items.QUARTZ, 8, 1, 6))
                                      .add(item(Items.GOLD_NUGGET, 10, 7, 9))
                                      .add(item(JNEItems.SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 1))));
            output.accept(key("wisp_arch_default"), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                                      .add(EmptyLootItem.emptyItem().setWeight(90))
                                      .add(item(JNEItems.SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 1))));
        }

        private ResourceKey<LootTable> key(String name) {
            return ResourceKey.create(Registries.LOOT_TABLE, NetherExp.id("archaeology/" + name));
        }
    }

    private static final class BlockLoot extends BlockLootSubProvider {
        BlockLoot(HolderLookup.Provider registries) {
            super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            ArrayList<Block> iterableBlocks = new ArrayList<>();
            JNEBlocks.BLOCKS.getEntries().stream().map(Supplier::get).forEach(iterableBlocks::add);
            return iterableBlocks;
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
            add(JNEBlocks.SOUL_SOIL_LAYER.get(), (block) ->
                    LootTable.lootTable().withPool(LootPool.lootPool()
                                                           .add(AlternativesEntry.alternatives(
                                                                   JNELayerBlock.LAYERS.getPossibleValues(),
                                                                   (layers) -> layers == 8 ?
                                                                           LootItem.lootTableItem(Blocks.SOUL_SOIL) :
                                                                           LootItem.lootTableItem(block)
                                                                                   .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(JNELayerBlock.LAYERS, layers)))
                                                                                   .apply(SetItemCountFunction.setCount(ConstantValue.exactly((float) layers)))))));
            add(JNEBlocks.ECTOPLASM_CAULDRON.get(), noDrop());

            // Black Ice
            dropSelf(JNEBlocks.BLACK_ICE.get());
            dropSelf(JNEBlocks.BLACK_ICICLE.get());
            dropWhenSilkTouch(JNEBlocks.THIN_BLACK_ICE.get());
            add(JNEBlocks.SOUL_PERMAFROST.get(), block -> createSilkTouchOnlyTable(block).withPool(LootPool.lootPool().add(item(Items.SOUL_SOIL, 1)).when(doesNotHaveSilkTouch()).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.SHOVELS)))).withPool(LootPool.lootPool().add(item(JNEBlocks.BLACK_ICICLE.get(), 1, 2, 4)).when(doesNotHaveSilkTouch()).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.PICKAXES)))));

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
            add(JNEBlocks.QUARTZ_CRYSTAL.get(), block -> createSilkTouchDispatchTable(block, applyExplosionDecay(block, LootItem.lootTableItem(Items.QUARTZ).apply(setCount(1, 3)).apply(ApplyBonusCount.addOreBonusCount(registries.holderOrThrow(Enchantments.FORTUNE))))));
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
            add(JNEBlocks.WARPED_WART.get(), (block) -> LootTable.lootTable().withPool(applyExplosionDecay(block, LootPool.lootPool().add(LootItem.lootTableItem(JNEBlocks.WARPED_WART.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WarpedWartBlock.AGE, 3).hasProperty(WarpedWartBlock.HALF, DoubleBlockHalf.LOWER)))).apply(ApplyBonusCount.addUniformBonusCount(registries.holderOrThrow(Enchantments.FORTUNE)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WarpedWartBlock.AGE, 3).hasProperty(WarpedWartBlock.HALF, DoubleBlockHalf.LOWER))))))));
            
            add(JNEBlocks.WRAITHING_LESION.get(), noDrop());
            dropSelf(JNEBlocks.SOUL_TORCHFLOWER.get());
            dropOther(JNEBlocks.SOUL_TORCHFLOWER_CROP.get(), Items.TORCHFLOWER_SEEDS);
            dropSelf(JNEBlocks.SORROWEED.get());
            dropSelf(JNEBlocks.SORROWSQUASH.get());
            dropSelf(JNEBlocks.CARVED_SORROWSQUASH.get());
            dropSelf(JNEBlocks.GHOUL_O_LANTERN.get());
            add(JNEBlocks.SORROWSQUASH_STEM.get(), block -> LootTable.lootTable().withPool(LootPool.lootPool().add(item(Items.PUMPKIN_SEEDS, 1)).when(BonusLevelTableCondition.bonusLevelFlatChance(registries.holderOrThrow(Enchantments.FORTUNE), 0.33f, 0.55f, 0.77f, 1f))));
            add(JNEBlocks.SORROWSQUASH_STEM_PLANT.get(), block -> LootTable.lootTable().withPool(LootPool.lootPool().add(item(Items.PUMPKIN_SEEDS, 1)).when(BonusLevelTableCondition.bonusLevelFlatChance(registries.holderOrThrow(Enchantments.FORTUNE), 0.33f, 0.55f, 0.77f, 1f))));
            add(JNEBlocks.CEREBRAGE_SKULL.get(), block -> LootTable.lootTable().withPool(applyExplosionDecay(block, LootPool.lootPool().add(item(Items.SKELETON_SKULL, 1)))).withPool(LootPool.lootPool().add(item(JNEItems.CEREBRAGE_SEEDS.get(), 1))).withPool(LootPool.lootPool().add(item(JNEItems.CEREBRAGE.get(), 1, 3, 6)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CerebrageSkullBlock.AGE, 3)))));

            // Shroomlight
            dropSelf(JNEBlocks.SHROOMNIGHT.get());

            // Wart Beards
            add(JNEBlocks.NETHER_WART_BEARD.get(), BlockLootSubProvider::createShearsOnlyDrop);
            add(JNEBlocks.WARPED_WART_BEARD.get(), BlockLootSubProvider::createShearsOnlyDrop);

            // Ivy
            add(JNEBlocks.WEEPING_IVY.get(), block -> createMultifaceBlockDrops(block, HAS_SHEARS).withPool(LootPool.lootPool().add(item(JNEItems.WEEPING_HELIX.get(), 1).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(IvyBlock.HELIX, true))))));
            add(JNEBlocks.TWISTING_IVY.get(), block -> createMultifaceBlockDrops(block, HAS_SHEARS).withPool(LootPool.lootPool().add(item(JNEItems.TWISTING_HELIX.get(), 1).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(IvyBlock.HELIX, true))))));

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
            add(JNEBlocks.BONE_PIKE.get(), block -> LootTable.lootTable().withPool(LootPool.lootPool().add(AlternativesEntry.alternatives(AlternativesEntry.alternatives(BonePikeBlock.BONES.getPossibleValues(), bones -> LootItem.lootTableItem(block).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BonePikeBlock.BONES, bones))).apply(SetItemCountFunction.setCount(ConstantValue.exactly((float) bones)))).when(hasSilkTouch()), AlternativesEntry.alternatives(BonePikeBlock.BONES.getPossibleValues(), bones -> LootItem.lootTableItem(Items.BONE).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BonePikeBlock.BONES, bones))).apply(SetItemCountFunction.setCount(ConstantValue.exactly((float) bones)))).when(doesNotHaveSilkTouch())))));
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
            add(JNEBlocks.ANCIENT_CAMPFIRE.get(), (block) -> this.createSilkTouchDispatchTable(block, this.applyExplosionCondition(block, LootItem.lootTableItem(JNEItems.ANCIENT_WAX.get()))));
            dropSelf(JNEBlocks.ANCIENT_CANDLE.get());

            dropSelf(JNEBlocks.OCHRE_FROGMIST.get());
            dropSelf(JNEBlocks.PEARLESCENT_FROGMIST.get());
            dropSelf(JNEBlocks.VERDANT_FROGMIST.get());
        }
    }

    private static LootPoolSingletonContainer.Builder<?> item(ItemLike item, int weight) {
        return LootItem.lootTableItem(item)
                .setWeight(weight);
    }

    private static LootPoolSingletonContainer.Builder<?> item(ItemLike item, int weight, float count) {
        return LootItem.lootTableItem(item)
                .setWeight(weight)
                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(count)));
    }

    private static LootPoolSingletonContainer.Builder<?> item(ItemLike item, int weight, float min, float max) {
        return LootItem.lootTableItem(item)
                .setWeight(weight)
                .apply(setCount(min, max));
    }

    private static LootItemFunction.Builder setCount(float min, float max) {
        return SetItemCountFunction.setCount(UniformGenerator.between(min, max));
    }
}
