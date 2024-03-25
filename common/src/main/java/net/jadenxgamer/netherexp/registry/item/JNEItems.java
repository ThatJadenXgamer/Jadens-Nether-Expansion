package net.jadenxgamer.netherexp.registry.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class JNEItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(NetherExp.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> WARPHOPPER_FUR = registerItem("warphopper_fur", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> HOGHAM  = registerItem("hogham", () ->
            new Item(new Item.Properties().food(JNEFoodProperties.HOGHAM)));

    public static final RegistrySupplier<Item> COOKED_HOGHAM = registerItem("cooked_hogham", () ->
            new Item(new Item.Properties().food(JNEFoodProperties.COOKED_HOGHAM)));

    public static final RegistrySupplier<Item> FOGGY_ESSENCE = registerItem("foggy_essence", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> IRON_SCRAP = registerItem("iron_scrap", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> WHITE_ASH_POWDER = registerItem("white_ash_powder", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> FOSSIL_FUEL = registerItem("fossil_fuel", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> RIFT_ARMOR_TRIM_SMITHING_TEMPLATE = registerItem("rift_armor_trim_smithing_template", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE = registerItem("spirit_armor_trim_smithing_template", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> NIGHTSPORES = registerItem("nightspores", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> LIGHTSPORES = registerItem("lightspores", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> BLIGHTSPORES = registerItem("blightspores", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> FRIGHTSPORES = registerItem("frightspores", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> ANTIDOTE = registerItem("antidote", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> GLOWCHEESE = registerItem("glowcheese", () ->
            new Item(new Item.Properties().food(JNEFoodProperties.GLOWCHEESE)));

    public static final RegistrySupplier<Item> NETHER_PIZZA = registerItem("nether_pizza", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> NETHER_PIZZA_SLICE = registerItem("nether_pizza_slice", () ->
            new Item(new Item.Properties().food(JNEFoodProperties.PIZZA_SLICE)));

    public static final RegistrySupplier<Item> WISP_BOTTLE = registerItem("wisp_bottle", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> MAGMA_CUBE_BUCKET = registerItem("magma_cube_bucket", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> WRAITHING_FLESH = registerItem("wraithing_flesh", () ->
            new Item(new Item.Properties().food(JNEFoodProperties.WRAITHING_FLESH)));

    public static final RegistrySupplier<Item> WARPED_WART = registerItem("warped_wart", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> MIST_CHARGE = registerItem("mist_charge", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> STRIDITE = registerItem("stridite", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> NETHERITE_PLATING = registerItem("netherite_plating", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> NECRO_SHARD = registerItem("necro_shard", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> SHOTGUN_FIST = registerItem("shotgun_fist", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> MUSIC_DISC_CRICKET  = registerItem("music_disc_cricket", () ->
            new RecordItem(13, JNESoundEvents.MUSIC_DISC_CRICKET.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 164));


    private static <T extends Item> RegistrySupplier<T> registerItem(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    public static void init() {
        ITEMS.register();
    }
}
