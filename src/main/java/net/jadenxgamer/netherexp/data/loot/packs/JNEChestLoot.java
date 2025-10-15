package net.jadenxgamer.netherexp.data.loot.packs;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public record JNEChestLoot(HolderLookup.Provider registries) implements LootTableSubProvider {

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        output.accept(key("chapel"),
                LootTable
                        .lootTable()
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(5)).add(item(Items.BONE, 3, 2, 3)).add(item(Items.SOUL_SOIL, 4, 3)))
                        .withPool(LootPool.lootPool().add(item(JNEItems.SANCTUM_COMPASS.get(), 1)))
                        .withPool(LootPool.lootPool().add(item(Items.PUMPKIN_SEEDS, 1, 7)))
                        .withPool(LootPool.lootPool().add(item(Items.SPLASH_POTION, 1, 2).apply(SetPotionFunction.setPotion(Potions.WATER))))
                        .withPool(LootPool.lootPool().add(item(Items.FLINT, 1, 4))));

        output.accept(key("sanctum_food"),
                LootTable
                        .lootTable()
                        .withPool(LootPool.lootPool().add(item(Items.MUTTON, 1, 3, 4)).add(item(JNEItems.HOGHAM.get(), 1, 3, 4)))
                        .withPool(LootPool.lootPool().add(item(JNEBlocks.BLACK_ICE.get(), 1, 19, 27))));

        output.accept(key("sanctum_supply"),
                LootTable
                        .lootTable()
                        .withPool(LootPool.lootPool().add(item(Items.FLINT, 1, 1, 3)))
                        .withPool(LootPool.lootPool().add(item(Items.IRON_NUGGET, 1, 9, 15)))
                        .withPool(LootPool.lootPool().add(item(Items.HONEYCOMB, 1, 1, 2)))
                        .withPool(LootPool
                                .lootPool()
                                .setRolls(ConstantValue.exactly(3))
                                .add(item(JNEBlocks.SOUL_SLATE.get(), 1, 7, 12))
                                .add(item(JNEItems.WRAITHING_FLESH.get(), 1, 5, 12))));
    }

    /**
     * Get a {@linkplain ResourceKey<LootTable>} for a loot table in netherexp/loot_table/chests
     *
     * @param name the internal name of the loot table
     * @return a new {@linkplain ResourceKey<LootTable>}
     */
    private ResourceKey<LootTable> key(String name) {
        return ResourceKey.create(Registries.LOOT_TABLE, NetherExp.id("chests/" + name));
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
