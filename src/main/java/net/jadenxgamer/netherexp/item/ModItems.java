package net.jadenxgamer.netherexp.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.block.ModBlocks;
import net.jadenxgamer.netherexp.entity.ModEntities;
import net.jadenxgamer.netherexp.item.custom.LightSporesItem;
import net.jadenxgamer.netherexp.item.custom.NightSporesItem;
import net.jadenxgamer.netherexp.sound.ModSoundEvents;
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

    public static final Item SORROWSQUASH_SEEDS = registerItem("sorrowsquash_seeds",
            new AliasedBlockItem(ModBlocks.SORROWSQUASH_STEM, new FabricItemSettings()));

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

    public static final Item WARPHOPPER_SPAWN_EGG = registerItem("warphopper_spawn_egg",
            new SpawnEggItem(ModEntities.WARPHOPPER,0x119b85,0x324149,
                    new FabricItemSettings()));

    public static final Item MUSIC_DISC_CRICKET = registerItem("music_disc_cricket",
            new MusicDiscItem(13, ModSoundEvents.MUSIC_DISC_CRICKET,new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), 164));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(NetherExp.MOD_ID, name), item);
    }

    public static void addItemsToItemGroups() {
        addToItemGroup(ItemGroups.INGREDIENTS, WARPHOPPER_FUR);
        addToItemGroup(ItemGroups.INGREDIENTS, IRON_SCRAP);
        addToItemGroup(ItemGroups.INGREDIENTS, RAW_PYRITE);
        addToItemGroup(ItemGroups.INGREDIENTS, PYRITE_INGOT);
        addToItemGroup(ItemGroups.INGREDIENTS, FOGGY_ESSENCE);

        addToItemGroup(ItemGroups.FOOD_AND_DRINK, HOGHAM);
        addToItemGroup(ItemGroups.FOOD_AND_DRINK, COOKED_HOGHAM);

        addToItemGroup(ItemGroups.BUILDING_BLOCKS, SORROWSQUASH_SEEDS);
        addToItemGroup(ItemGroups.BUILDING_BLOCKS, WEEPING_IVY);
        addToItemGroup(ItemGroups.BUILDING_BLOCKS, TWISTING_IVY);
        addToItemGroup(ItemGroups.BUILDING_BLOCKS, LIGHTSPORES);
        addToItemGroup(ItemGroups.BUILDING_BLOCKS, NIGHTSPORES);

        addToItemGroup(ItemGroups.SPAWN_EGGS, WARPHOPPER_SPAWN_EGG);

        addToItemGroup(ModItemGroup.NETHEREXP, SORROWSQUASH_SEEDS);
        addToItemGroup(ModItemGroup.NETHEREXP, WEEPING_IVY);
        addToItemGroup(ModItemGroup.NETHEREXP, TWISTING_IVY);
        addToItemGroup(ModItemGroup.NETHEREXP, LIGHTSPORES);
        addToItemGroup(ModItemGroup.NETHEREXP, NIGHTSPORES);
        addToItemGroup(ModItemGroup.NETHEREXP, WHITE_ASH_POWDER);
        addToItemGroup(ModItemGroup.NETHEREXP, HOGHAM);
        addToItemGroup(ModItemGroup.NETHEREXP, COOKED_HOGHAM);
        addToItemGroup(ModItemGroup.NETHEREXP, IRON_SCRAP);
        addToItemGroup(ModItemGroup.NETHEREXP, WARPHOPPER_SPAWN_EGG);
        addToItemGroup(ModItemGroup.NETHEREXP, WARPHOPPER_FUR);
        addToItemGroup(ModItemGroup.NETHEREXP, FOGGY_ESSENCE);
        addToItemGroup(ModItemGroup.NETHEREXP, MUSIC_DISC_CRICKET);
    }

    public static void addToItemGroup(ItemGroup group, Item item) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
    }

    public static void registerModItems() {
        NetherExp.LOGGER.debug("Registering Items for " + NetherExp.MOD_ID);
        addItemsToItemGroups();
    }
}
