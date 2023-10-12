package net.jadenxgamer.netherexp.util;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.jadenxgamer.netherexp.registry.item.ModItems;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class ModLootTableModifier {

    private static final Identifier PIGLIN_BARTERING = new Identifier("minecraft", "gameplay/piglin_bartering");

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (PIGLIN_BARTERING.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                .conditionally(RandomChanceLootCondition.builder(0.5f))
                .with(ItemEntry.builder(ModItems.LIGHTSPORES))
                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0f, 5.0f)).build());

                tableBuilder.pool(poolBuilder.build());
            }
        });
    }
}
