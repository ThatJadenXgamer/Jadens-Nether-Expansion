package net.jadenxgamer.netherexp.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.block.ModBlocks;
import net.jadenxgamer.netherexp.sound.ModSoundEvents;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public class ModItems {

    public static final Item WARPHOPPER_FUR = registerItem("warphopper_fur",
            new Item(new FabricItemSettings().group(ModItemGroup.NETHEREXP)));

    public static final Item HOGHAM = registerItem("hogham",
            new Item(new FabricItemSettings().group(ModItemGroup.NETHEREXP).food(ModFoodComponents.HOGHAM)));

    public static final Item COOKED_HOGHAM = registerItem("cooked_hogham",
            new Item(new FabricItemSettings().group(ModItemGroup.NETHEREXP).food(ModFoodComponents.COOKED_HOGHAM)));

    public static final Item IRON_SCRAP = registerItem("iron_scrap",
            new Item(new FabricItemSettings().group(ModItemGroup.NETHEREXP)));

    public static final Item RAW_PYRITE = registerItem("raw_pyrite",
            new Item(new FabricItemSettings().group(ModItemGroup.NETHEREXP)));

    public static final Item PYRITE_INGOT = registerItem("pyrite_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.NETHEREXP)));

    public static final Item WEEPING_IVY = registerItem("weeping_ivy",
            new AliasedBlockItem(ModBlocks.WEEPING_IVY, new FabricItemSettings().group(ModItemGroup.NETHEREXP)));

    public static final Item TWISTING_IVY = registerItem("twisting_ivy",
            new AliasedBlockItem(ModBlocks.TWISTING_IVY, new FabricItemSettings().group(ModItemGroup.NETHEREXP)));

    public static final Item MUSIC_DISC_CRICKET = registerItem("music_disc_cricket",
            new MusicDiscItem(8, ModSoundEvents.MUSIC_DISC_CRICKET,new FabricItemSettings().group(ModItemGroup.NETHEREXP).maxCount(1).rarity(Rarity.RARE), 164));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(NetherExp.MOD_ID, name), item);
    }

    public static void registerModItems() {
        NetherExp.LOGGER.debug("Registering Mod Items for " + NetherExp.MOD_ID);
    }
}
