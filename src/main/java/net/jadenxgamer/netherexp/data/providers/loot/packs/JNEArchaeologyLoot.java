package net.jadenxgamer.netherexp.data.providers.loot.packs;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public record JNEArchaeologyLoot(HolderLookup.Provider registries) implements LootTableSubProvider {

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        output.accept(
                key("basalt_deltas"),
                LootTable
                        .lootTable()
                        .withPool(LootPool
                                .lootPool()
                                .add(item(Items.MAGMA_CREAM, 25, 1, 4))
                                .add(item(Items.GLOWSTONE_DUST, 15, 2, 9))
                                .add(item(Blocks.BASALT, 20, 2, 8))
                                .add(item(Blocks.BLACKSTONE, 25, 3, 6))
                                .add(item(Items.QUARTZ, 8, 1, 6))
                                .add(item(Items.GOLD_NUGGET, 10, 7, 9))
                                .add(item(JNEItems.SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 1))));

        output.accept(
                key("bastion_remnant"),
                LootTable
                        .lootTable()
                        .withPool(LootPool
                                .lootPool()
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

        output.accept(
                key("beast_skull_common"),
                LootTable
                        .lootTable()
                        .withPool(LootPool
                                .lootPool()
                                .add(item(Items.GOLDEN_APPLE, 5, 1, 2))
                                .add(item(Items.GOLDEN_CARROT, 10, 2, 8))
                                .add(item(Items.GOLD_INGOT, 15, 2, 7))
                                .add(item(Items.SPECTRAL_ARROW, 30, 3, 14))
                                .add(item(Items.GOLD_NUGGET, 50, 2, 26))
                                .add(item(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, 10))
                                .add(item(Items.ENCHANTED_GOLDEN_APPLE, 1))));

        output.accept(
                key("beast_skull_rare"),
                LootTable
                        .lootTable()
                        .withPool(LootPool
                                .lootPool()
                                .add(item(Items.GOLDEN_APPLE, 6, 1, 2))
                                .add(item(Items.GOLD_INGOT, 15, 2, 7))
                                .add(item(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, 5))
                                .add(item(Items.ENCHANTED_GOLDEN_APPLE, 1))));

        output.accept(
                key("black_ice_glaciers"),
                LootTable
                        .lootTable()
                        .withPool(LootPool
                                .lootPool()
                                .add(item(JNEBlocks.BLACK_ICICLE.get(), 15, 2, 6))
                                .add(item(
                                        Items.CHAINMAIL_BOOTS,
                                        5).apply(new SetEnchantmentsFunction.Builder().withEnchantment(
                                        registries.holderOrThrow(Enchantments.SOUL_SPEED),
                                        ConstantValue.exactly(1))))
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

        output.accept(
                key("crimson_forest"),
                LootTable
                        .lootTable()
                        .withPool(LootPool
                                .lootPool()
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

        output.accept(
                key("devils_bluff"),
                LootTable
                        .lootTable()
                        .withPool(LootPool
                                .lootPool()
                                .add(item(Items.BONE, 16, 2, 5))
                                .add(item(Items.SKELETON_SKULL, 5, 1))
                                .add(item(Blocks.NETHERRACK, 8, 7))
                                .add(item(JNEItems.CEREBRAGE_SEEDS.get(), 8))
                                .add(item(Items.ROTTEN_FLESH, 16, 2, 8))));

        output.accept(
                key("fortress"),
                LootTable
                        .lootTable()
                        .withPool(LootPool
                                .lootPool()
                                .add(item(Items.NETHER_WART, 20, 1, 2))
                                .add(item(Items.ROTTEN_FLESH, 15, 1, 5))
                                .add(item(Items.MAGMA_CREAM, 8, 1, 2))
                                .add(item(Items.BLAZE_POWDER, 10, 1, 3))
                                .add(item(Items.BONE, 15, 1, 4))
                                .add(item(Items.NETHER_BRICK, 25, 4, 7))
                                .add(item(Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE, 1))));

        output.accept(
                key("mega_fossil_common"),
                LootTable
                        .lootTable()
                        .withPool(LootPool
                                .lootPool()
                                .add(item(Items.BONE, 10, 1, 4))
                                .add(item(JNEItems.WRAITHING_FLESH.get(), 10, 3, 4))
                                .add(item(JNEItems.STRIDITE.get(), 5, 2, 4))
                                .add(item(Items.QUARTZ, 8, 1, 8))
                                .add(item(Items.SKELETON_SKULL, 4))
                                .add(item(Items.ARROW, 8, 4, 9))
                                .add(item(JNEItems.SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 5))
                                .add(item(JNEItems.SPECTRE_POTTERY_SHERD.get(), 10))
                                .add(item(JNEItems.MARIONETTE_POTTERY_SHERD.get(), 10))));

        output.accept(
                key("mega_fossil_rare"),
                LootTable
                        .lootTable()
                        .withPool(LootPool
                                .lootPool()
                                .add(item(Items.LEATHER, 5, 2, 4))
                                .add(item(Items.GOLD_INGOT, 10, 3, 6))
                                .add(item(JNEItems.WRAITHING_FLESH.get(), 4, 6, 8))
                                .add(item(Items.QUARTZ, 3, 1, 8))
                                .add(item(Items.SKELETON_SKULL, 8))
                                .add(item(Items.SPECTRAL_ARROW, 8, 4, 9))
                                .add(item(JNEItems.SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 8))));

        output.accept(
                key("nether_wastes"),
                LootTable
                        .lootTable()
                        .withPool(LootPool
                                .lootPool()
                                .add(item(Items.GLOWSTONE_DUST, 10, 1, 5))
                                .add(item(Items.NETHERRACK, 25, 1, 5))
                                .add(item(Items.RED_MUSHROOM, 10, 1, 4))
                                .add(item(Items.BROWN_MUSHROOM, 10, 1, 4))
                                .add(item(Items.QUARTZ, 8, 1, 6))
                                .add(item(Items.GOLD_NUGGET, 10, 7, 9))
                                .add(item(JNEItems.SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 1))));

        output.accept(
                key("soul_sand_valley"),
                LootTable
                        .lootTable()
                        .withPool(LootPool
                                .lootPool()
                                .add(item(
                                        Items.CHAINMAIL_BOOTS,
                                        5).apply(new SetEnchantmentsFunction.Builder().withEnchantment(
                                        registries.holderOrThrow(Enchantments.SOUL_SPEED),
                                        ConstantValue.exactly(1))))
                                .add(item(Items.BONE, 10, 1, 4))
                                .add(item(JNEItems.FOSSIL_FUEL.get(), 10, 2, 4))
                                .add(item(JNEItems.WRAITHING_FLESH.get(), 10, 3, 4))
                                .add(item(JNEItems.STRIDITE.get(), 5, 2, 4))
                                .add(item(Items.QUARTZ, 8, 1, 8))
                                .add(item(Items.GOLD_NUGGET, 10, 7, 9))
                                .add(item(Items.GUNPOWDER, 8, 2, 5))
                                .add(item(Items.SKELETON_SKULL, 1))
                                .add(item(Items.ARROW, 8, 4, 9))
                                .add(item(JNEItems.SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 1))));

        output.accept(
                key("warped_forest"),
                LootTable
                        .lootTable()
                        .withPool(LootPool
                                .lootPool()
                                .add(item(Items.GOLD_INGOT, 8, 1, 2))
                                .add(item(Items.ENDER_PEARL, 8, 1, 2))
                                .add(item(Items.CRIMSON_FUNGUS, 8))
                                .add(item(Items.TWISTING_VINES, 25, 1, 4))
                                .add(item(Items.WARPED_FUNGUS, 15, 1, 2))
                                .add(item(Items.QUARTZ, 8, 1, 6))
                                .add(item(Items.GOLD_NUGGET, 10, 7, 9))
                                .add(item(JNEItems.SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 1))));

        output.accept(
                key("wisp_arch_default"),
                LootTable
                        .lootTable()
                        .withPool(LootPool.lootPool().add(EmptyLootItem.emptyItem().setWeight(90)).add(item(JNEItems.SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 1))));
    }

    /**
     * Get a {@linkplain ResourceKey<LootTable>} for a loot table in netherexp/loot_table/archaeology
     *
     * @param name the internal name of the loot table
     * @return a new {@linkplain ResourceKey<LootTable>}
     */
    private ResourceKey<LootTable> key(String name) {
        return ResourceKey.create(Registries.LOOT_TABLE, NetherExp.id("archaeology/" + name));
    }

    /**
     * Get a loot table with no drops
     *
     * @return {@code LootTable.lootTable();}
     */
    private static LootTable.Builder noDrop() {
        return LootTable.lootTable();
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
