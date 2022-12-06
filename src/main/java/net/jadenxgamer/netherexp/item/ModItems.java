package net.jadenxgamer.netherexp.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item WARPHOPPER_FUR = registerItem("warphopper_fur",
            new Item(new FabricItemSettings().group(ModItemGroup.NETHEREXP_ITEMS)));

    public static final Item HOGHAM = registerItem("hogham",
            new Item(new FabricItemSettings().group(ModItemGroup.NETHEREXP_ITEMS).food(ModFoodComponents.HOGHAM)));

    public static final Item COOKED_HOGHAM = registerItem("cooked_hogham",
            new Item(new FabricItemSettings().group(ModItemGroup.NETHEREXP_ITEMS).food(ModFoodComponents.COOKED_HOGHAM)));

    public static final Item PYRITE_INGOT = registerItem("pyrite_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.NETHEREXP_ITEMS)));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(NetherExp.MOD_ID, name), item);
    }

    public static void registerModItems() {
        NetherExp.LOGGER.debug("Registering Mod Items for " + NetherExp.MOD_ID);
    }
}
