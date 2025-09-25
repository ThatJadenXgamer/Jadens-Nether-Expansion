package net.jadenxgamer.netherexp.data.providers.loot.packs;

import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.Supplier;
import java.util.stream.Stream;

public final class JNEEntityLoot extends EntityLootSubProvider {

    public JNEEntityLoot(HolderLookup.Provider registries) {
        super(FeatureFlags.DEFAULT_FLAGS, registries);
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return JNEEntityType.ENTITY_TYPES.getEntries().stream().map(Supplier::get);
    }

    @Override
    public void generate() {
        add(JNEEntityType.WISP.get(), noDrop());
        add(JNEEntityType.APPARITION.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(item(Items.CHAIN, 1, 0, 2).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0, 1))))));
        add(JNEEntityType.VESSEL.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().add(applyLooting(registries, item(JNEItems.WRAITHING_FLESH.get(), 1, 2, 4))))
                .withPool(LootPool.lootPool().add(applyLooting(registries, item(Items.BONE, 1, 0, 2)))));
        // TODO: IMPLEMENT BANSHEE
        // add(JNEEntityType.BANSHEE.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(applyLooting(registries, item(JNEItems.BANSHEE_ROD.get(), 1, 0, 1))).when(LootItemKilledByPlayerCondition.killedByPlayer())));
        // TODO: IMPLEMENT ECTO SLAB
        // add(JNEEntityType.ECTO_SLAB.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(applyLooting(registries, item(JNEItems.PHASMO_SHARD.get(), 1, 0, 2)).when(DamageSourceCondition.hasDamageSource(DamageSourcePredicate.Builder.damageType().source(EntityPredicate.Builder.entity().of(EntityType.FROG))).invert()).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(SlimePredicate.sized(MinMaxBounds.Ints.atLeast(2)))))).add(item(JNEBlocks.PEARLESCENT_FROGMIST.get(), 1, 1, 8).when(DamageSourceCondition.hasDamageSource(DamageSourcePredicate.Builder.damageType().source(EntityPredicate.Builder.entity().of(EntityType.FROG).subPredicate(EntitySubPredicates.frogVariant(registries.holderOrThrow(FrogVariant.WARM))))))).add(item(JNEBlocks.VERDANT_FROGMIST.get(), 1, 1, 8).when(DamageSourceCondition.hasDamageSource(DamageSourcePredicate.Builder.damageType().source(EntityPredicate.Builder.entity().of(EntityType.FROG).subPredicate(EntitySubPredicates.frogVariant(registries.holderOrThrow(FrogVariant.COLD))))))).add(item(JNEBlocks.OCHRE_FROGMIST.get(), 1, 1, 8).when(DamageSourceCondition.hasDamageSource(DamageSourcePredicate.Builder.damageType().source(EntityPredicate.Builder.entity().of(EntityType.FROG).subPredicate(EntitySubPredicates.frogVariant(registries.holderOrThrow(FrogVariant.TEMPERATE)))))))));
        // TODO: IMPLEMENT WARPHOPPER
        // add(JNEEntityType.WARPHOPPER.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(applyLooting(registries, item(Items.MUTTON, 1, 5, 7), 2).apply(SmeltItemFunction.smelted().when(shouldSmeltLoot())))).withPool(LootPool.lootPool().add(applyLooting(registries, item(JNEItems.WARPHOPPER_FUR, 1, 3, 5), 2))));
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
     * Applies the {@linkplain EnchantedCountIncreaseFunction} for looting to the given {@linkplain FunctionUserBuilder<T>}
     *
     * @param registries propagated {@linkplain HolderLookup.Provider}
     * @param builder the builder
     * @return the builder with looting applied
     * @param <T> type param to avoid casting
     */
    private static <T extends FunctionUserBuilder<T>> T applyLooting(HolderLookup.Provider registries, FunctionUserBuilder<T> builder) {
        return applyLooting(registries, builder, 1);
    }

    /**
     * Applies the {@linkplain EnchantedCountIncreaseFunction} for looting to the given {@linkplain FunctionUserBuilder<T>}
     *
     * @param registries propagated {@linkplain HolderLookup.Provider}
     * @param builder the builder
     * @param level the level at which looting caps out
     * @return the builder with looting applied
     * @param <T> type param to avoid casting
     */
    private static <T extends FunctionUserBuilder<T>> T applyLooting(HolderLookup.Provider registries, FunctionUserBuilder<T> builder, float level) {
        return builder.apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0, level)));
    }

    /**
     * Get a {@linkplain LootPoolSingletonContainer.Builder} with the item and weight set
     *
     * @param item the item
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
     * @param item the item
     * @param weight the weight
     * @param count the count
     * @return the builder
     */
    private static LootPoolSingletonContainer.Builder<?> item(ItemLike item, int weight, float count) {
        return item(item, weight)
                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(count)));
    }

    /**
     * Get a {@linkplain LootPoolSingletonContainer.Builder} with the item and weight set, also applies a {@linkplain SetItemCountFunction} with a uniform random value
     *
     * @param item the item
     * @param weight the weight
     * @param min the minimum count
     * @param max the maximum count
     * @return the builder
     */
    private static LootPoolSingletonContainer.Builder<?> item(ItemLike item, int weight, float min, float max) {
        return item(item, weight)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)));
    }
}
