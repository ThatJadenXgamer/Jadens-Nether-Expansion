package net.jadenxgamer.netherexp.fabric.loot;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class JNELootModifiers {

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (id.equals(new ResourceLocation("minecraft", "chests/ruined_portal"))) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .when(LootItemRandomChanceCondition.randomChance(0.35f))
                        .add(LootItem.lootTableItem(JNEItems.RIFT_ARMOR_TRIM_SMITHING_TEMPLATE.get()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0f, 3.0f)).build());

                tableBuilder.pool(poolBuilder.build());
            }
        });
    }
}
