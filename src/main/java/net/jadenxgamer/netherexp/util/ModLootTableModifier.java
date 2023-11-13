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

    private static final Identifier RUINED_PORTAL = new Identifier("minecraft", "chests/ruined_portal");

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (RUINED_PORTAL.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                .conditionally(RandomChanceLootCondition.builder(0.5f))
                .with(ItemEntry.builder(ModItems.RIFT_ARMOR_TRIM_SMITHING_TEMPLATE))
                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0f, 3.0f)).build());

                tableBuilder.pool(poolBuilder.build());
            }
        });
    }
}
