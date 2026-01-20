package net.jadenxgamer.netherexp.data.providers.loot.packs;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public record JNEBrazierChestLoot(HolderLookup.Provider registries) implements LootTableSubProvider {

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        output.accept(
                key("exposed"), LootTable.lootTable()
                        .withPool(LootPool
                                .lootPool()
                                .add(item(JNEItems.SEALED_POTTERY_SHERD.get(), 2))
                                .add(item(JNEItems.ELDRITCH_POTTERY_SHERD.get(), 2))
                                .add(item(JNEItems.DECEPTION_POTTERY_SHERD.get(), 2))
                                .add(item(JNEItems.BOTANICAL_POTTERY_SHERD.get(), 1))
                                .add(item(JNEItems.FIREARM_POTTERY_SHERD.get(), 1)))
                        .withPool(LootPool
                                .lootPool()
                                .setRolls(UniformGenerator.between(2, 5))
                                .add(item(Items.SKELETON_SKULL, 1))
                                .add(item(JNEItems.STRIDITE.get(), 8, 1, 2))
                                .add(item(JNEItems.WRAITHING_FLESH.get(), 10, 5, 7))
                                .add(item(JNEItems.PHASMO_ARROW.get(), 10, 3, 18))
                                .add(item(Items.IRON_INGOT, 15, 2, 4))
                                .add(item(Items.DIAMOND, 7, 1, 5))
                                .add(item(Items.IRON_BLOCK, 3, 1, 2))
                                .add(item(JNEItems.WILL_O_WISP.get(), 5, 2, 6)))
                        .withPool(LootPool
                                .lootPool()
                                .setRolls(ConstantValue.exactly(2))
                                .add(item(Items.EXPERIENCE_BOTTLE, 15, 4, 7))
                                .add(item(Items.IRON_NUGGET, 10, 1, 3)))
                        .withPool(LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(2))
                                        .add(item(Items.BOOK, 15).apply(EnchantRandomlyFunction.randomEnchantment()))
                                        .add(item(Items.BOOK, 4).apply(EnchantRandomlyFunction
                                                .randomEnchantment()
                                                .withEnchantment(registries.holderOrThrow(Enchantments.MENDING))))
                                        // TODO: IMPLEMENT ENCHANTMENTS
                                        // .add(item(Items.BOOK, 4).apply(EnchantRandomlyFunction.randomEnchantment().withEnchantment(registries.holderOrThrow(JNEEnchantments.PHANTASM_HULL))))
                                        .add(item(Items.BOOK, 6).apply(EnchantRandomlyFunction
                                                .randomEnchantment()
                                                .withOneOf(HolderSet.direct(
                                                        registries::holderOrThrow,
                                                        Enchantments.SILK_TOUCH,
                                                        Enchantments.EFFICIENCY,
                                                        Enchantments.FORTUNE,
                                                        Enchantments.SWEEPING_EDGE))))
                                // TODO: IMPLEMENT ENCHANTMENTS
                                // .add(item(Items.BOOK, 8).apply(EnchantRandomlyFunction.randomEnchantment().withOneOf(HolderSet.direct(registries::holderOrThrow, JNEEnchantments.RECOIL, JNEEnchantments.BARRAGE, JNEEnchantments.CARTRIDGE))))
                        )
                        .withPool(LootPool
                                .lootPool()
                                .add(EmptyLootItem.emptyItem().setWeight(65))
                                .add(item(JNEItems.ANCIENT_WAX.get(), 45, 1, 2))
                                .add(item(JNEItems.VALOR_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 25))
                                .add(item(JNEItems.MUSIC_DISC_BUCKSHOT_WONDERLAND.get(), 5))
                                .add(item(JNEItems.SHOTGUN_CORE.get(), 8))
                                .add(item(Items.ANCIENT_DEBRIS, 25, 1, 2)))
                        .withPool(LootPool
                                .lootPool()
                                .add(item(Items.BRUSH, 1)
                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(5, 32)))
                                        .when(LootItemRandomChanceCondition.randomChance(0.15f)))));

        output.accept(
                key("hidden"), LootTable
                        .lootTable()
                        .withPool(LootPool
                                .lootPool()
                                .setRolls(UniformGenerator.between(2, 5))
                                .add(item(Items.SKELETON_SKULL, 1))
                                .add(item(JNEItems.STRIDITE.get(), 8, 1, 2))
                                .add(item(JNEItems.WRAITHING_FLESH.get(), 10, 5, 7))
                                .add(item(JNEItems.PHASMO_ARROW.get(), 10, 3, 18))
                                .add(item(Items.IRON_INGOT, 15, 2, 4))
                                .add(item(Items.DIAMOND, 7, 1, 5))
                                .add(item(Items.IRON_BLOCK, 3, 1, 2))
                                .add(item(JNEItems.WILL_O_WISP.get(), 5, 2, 6)))
                        .withPool(LootPool
                                .lootPool()
                                .add(item(JNEItems.SEALED_POTTERY_SHERD.get(), 2))
                                .add(item(JNEItems.ELDRITCH_POTTERY_SHERD.get(), 2))
                                .add(item(JNEItems.DECEPTION_POTTERY_SHERD.get(), 2))
                                .add(item(JNEItems.BOTANICAL_POTTERY_SHERD.get(), 1))
                                .add(item(JNEItems.FIREARM_POTTERY_SHERD.get(), 1)))
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(2)).add(item(Items.EXPERIENCE_BOTTLE, 15, 4, 7)))
                        .withPool(LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(2))
                                        .add(item(Items.BOOK, 5).apply(EnchantRandomlyFunction
                                                .randomEnchantment()
                                                .withOneOf(HolderSet.direct(
                                                        registries::holderOrThrow,
                                                        Enchantments.SHARPNESS,
                                                        Enchantments.SWEEPING_EDGE,
                                                        Enchantments.PROTECTION,
                                                        Enchantments.LOOTING))))
                                        .add(item(Items.BOOK, 15).apply(EnchantRandomlyFunction.randomEnchantment()))
                                        .add(item(Items.BOOK, 4).apply(EnchantRandomlyFunction
                                                .randomEnchantment()
                                                .withEnchantment(registries.holderOrThrow(Enchantments.MENDING))))
                                        // TODO: IMPLEMENT ENCHANTMENTS
                                        // .add(item(Items.BOOK, 5).apply(EnchantRandomlyFunction.randomEnchantment().withEnchantment(registries.holderOrThrow(JNEEnchantments.PHANTASM_HULL))))
                                        .add(item(Items.BOOK, 6).apply(EnchantRandomlyFunction
                                                .randomEnchantment()
                                                .withOneOf(HolderSet.direct(
                                                        registries::holderOrThrow,
                                                        Enchantments.SILK_TOUCH,
                                                        Enchantments.EFFICIENCY,
                                                        Enchantments.FORTUNE,
                                                        Enchantments.SWEEPING_EDGE))))
                                // TODO: IMPLEMENT ENCHANTMENTS
                                // .add(item(Items.BOOK, 6).apply(EnchantRandomlyFunction.randomEnchantment().withOneOf(HolderSet.direct(registries::holderOrThrow, JNEEnchantments.RECOIL, JNEEnchantments.BARRAGE, JNEEnchantments.CARTRIDGE, Enchantments.QUICK_CHARGE))))
                        )
                        .withPool(LootPool
                                .lootPool()
                                .add(EmptyLootItem.emptyItem().setWeight(50))
                                .add(item(JNEItems.ANCIENT_WAX.get(), 45, 1, 2))
                                .add(item(JNEItems.VALOR_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 25))
                                .add(item(JNEItems.MUSIC_DISC_BUCKSHOT_WONDERLAND.get(), 15))
                                .add(item(JNEItems.SHOTGUN_CORE.get(), 10))
                                .add(item(JNEItems.PUMP_CHARGE_UPGRADE_SMITHING_TEMPLATE.get(), 8))
                                .add(item(Items.ANCIENT_DEBRIS, 25, 1, 4)))
                        .withPool(LootPool
                                .lootPool()
                                .add(EmptyLootItem.emptyItem().setWeight(5))
                                .add(item(Items.POTION, 1).apply(SetPotionFunction.setPotion(Potions.STRENGTH)))
                                .add(item(Items.POTION, 1, 2).apply(SetPotionFunction.setPotion(Potions.REGENERATION)))
                                .add(item(Items.POTION, 1).apply(SetPotionFunction.setPotion(Potions.INVISIBILITY))))
                        .withPool(LootPool
                                .lootPool()
                                .add(item(Items.BRUSH, 1)
                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(5, 32)))
                                        .when(LootItemRandomChanceCondition.randomChance(0.15f)))));
    }

    /**
     * Get a {@linkplain ResourceKey<LootTable>} for a loot table in netherexp/loot_table/brazier_chest
     *
     * @param name the internal name of the loot table
     * @return a new {@linkplain ResourceKey<LootTable>}
     */
    private ResourceKey<LootTable> key(String name) {
        return ResourceKey.create(Registries.LOOT_TABLE, NetherExp.id("brazier_chest/" + name));
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
