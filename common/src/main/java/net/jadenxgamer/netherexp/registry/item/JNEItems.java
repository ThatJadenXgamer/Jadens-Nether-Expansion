package net.jadenxgamer.netherexp.registry.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;

public class JNEItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(NetherExp.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> EXAMPLE_ITEM = ITEMS.register("example_item", () ->
            new Item(new Item.Properties().arch$tab(NetherExp.EXAMPLE_TAB)));

    public static void init() {
        ITEMS.register();
    }
}
