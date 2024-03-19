package net.jadenxgamer.netherexp.registry.misc_registry;

import com.google.common.collect.Sets;
import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.util.Identifier;

import java.util.Set;

public class JNELootTables {

    private static final Set<Identifier> LOOT_TABLES = Sets.newHashSet();

    public static final Identifier ARCHAEOLOGY_SOUL_SAND_VALLEY = register("archaeology/soul_sand_valley");

    private static Identifier register(String id) {
        return registerLootTable(new Identifier(NetherExp.MOD_ID, id));
    }

    private static Identifier registerLootTable(Identifier id) {
        if (LOOT_TABLES.add(id)) {
            return id;
        } else {
            throw new IllegalArgumentException(id + " is already a registered built-in loot table");
        }
    }
}
