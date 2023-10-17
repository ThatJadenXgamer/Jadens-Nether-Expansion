package net.jadenxgamer.netherexp.registry.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.ModBlocks;
import net.jadenxgamer.netherexp.registry.entity.ModEntities;
import net.jadenxgamer.netherexp.registry.item.custom.LightSporesItem;
import net.jadenxgamer.netherexp.registry.item.custom.NightSporesItem;
import net.jadenxgamer.netherexp.registry.sound.ModSoundEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

@SuppressWarnings("unused")
public class ModItems {

    public static final Item WARPHOPPER_FUR = registerItem("warphopper_fur",
            new Item(new FabricItemSettings()));

    public static final Item HOGHAM = registerItem("hogham",
            new Item(new FabricItemSettings().food(ModFoodComponents.HOGHAM)));

    public static final Item COOKED_HOGHAM = registerItem("cooked_hogham",
            new Item(new FabricItemSettings().food(ModFoodComponents.COOKED_HOGHAM)));

    public static final Item FOGGY_ESSENCE = registerItem("foggy_essence",
            new Item(new FabricItemSettings()));

    public static final Item IRON_SCRAP = registerItem("iron_scrap",
            new Item(new FabricItemSettings()));

    public static final Item WHITE_ASH_POWDER = registerItem("white_ash_powder",
            new SnowballItem(new FabricItemSettings()));

    public static final Item RAW_PYRITE = registerItem("raw_pyrite",
            new Item(new FabricItemSettings()));

    public static final Item PYRITE_INGOT = registerItem("pyrite_ingot",
            new Item(new FabricItemSettings()));

    public static final Item WEEPING_IVY = registerItem("weeping_ivy",
            new AliasedBlockItem(ModBlocks.WEEPING_IVY, new FabricItemSettings()));

    public static final Item TWISTING_IVY = registerItem("twisting_ivy",
            new AliasedBlockItem(ModBlocks.TWISTING_IVY, new FabricItemSettings()));

    public static final Item NIGHTSPORES = registerItem("nightspores",
            new NightSporesItem(new FabricItemSettings()));

    public static final Item LIGHTSPORES = registerItem("lightspores",
            new LightSporesItem(new FabricItemSettings()));

    public static final Item GLOW_CHEESE = registerItem("glow_cheese",
            new Item(new FabricItemSettings().food(ModFoodComponents.GLOW_CHEESE)));

    public static final Item WARPHOPPER_SPAWN_EGG = registerItem("warphopper_spawn_egg",
            new SpawnEggItem(ModEntities.WARPHOPPER,0x119b85,0x324149,
                    new FabricItemSettings()));

    public static final Item MUSIC_DISC_CRICKET = registerItem("music_disc_cricket",
            new MusicDiscItem(13, ModSoundEvents.MUSIC_DISC_CRICKET,new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), 164));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(NetherExp.MOD_ID, name), item);
    }

    public static void registerModItems() {
        NetherExp.LOGGER.debug("Registering Items for " + NetherExp.MOD_ID);
    }
}
