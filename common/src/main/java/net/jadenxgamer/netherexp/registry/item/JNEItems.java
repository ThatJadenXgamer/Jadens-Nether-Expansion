package net.jadenxgamer.netherexp.registry.item;

import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.fuel.FuelRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.item.custom.*;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETrimPatterns;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class JNEItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(NetherExp.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> WARPHOPPER_FUR = registerItem("warphopper_fur", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> HOGHAM = registerItem("hogham", () ->
            new Item(new Item.Properties().food(JNEFoodProperties.HOGHAM)));

    public static final RegistrySupplier<Item> COOKED_HOGHAM = registerItem("cooked_hogham", () ->
            new Item(new Item.Properties().food(JNEFoodProperties.COOKED_HOGHAM)));

    public static final RegistrySupplier<Item> FOGGY_ESSENCE = registerItem("foggy_essence", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> IRON_SCRAP = registerItem("iron_scrap", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> WHITE_ASH_POWDER = registerItem("white_ash_powder", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> FOSSIL_FUEL = registerFuel("fossil_fuel", () ->
            new Item(new Item.Properties()), 100);

    public static final RegistrySupplier<Item> RIFT_ARMOR_TRIM_SMITHING_TEMPLATE = registerItem("rift_armor_trim_smithing_template", () ->
            SmithingTemplateItem.createArmorTrimTemplate(JNETrimPatterns.RIFT));

    public static final RegistrySupplier<Item> SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE = registerItem("spirit_armor_trim_smithing_template", () ->
            SmithingTemplateItem.createArmorTrimTemplate(JNETrimPatterns.SPIRIT));

    public static final RegistrySupplier<Item> PUMP_CHARGE_UPGRADE_SMITHING_TEMPLATE = registerItem("pump_charge_upgrade_smithing_template",
            PumpChargeSmithingTemplateItem::new);

    public static final RegistrySupplier<Item> LIGHTSPORES = registerItem("lightspores", () ->
            new LightsporesItem(new Item.Properties()));

    public static final RegistrySupplier<Item> NIGHTSPORES = registerItem("nightspores", () ->
            new NightsporesItem(new Item.Properties()));

    public static final RegistrySupplier<Item> BLIGHTSPORES = registerItem("blightspores", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> FRIGHTSPORES = registerItem("frightspores", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> ANTIDOTE = registerItem("antidote", () ->
            new AntidoteItem(new Item.Properties().stacksTo(16)));

    public static final RegistrySupplier<Item> GRENADE_ANTIDOTE = registerItem("grenade_antidote", () ->
            new GrenadeAntidoteItem(new Item.Properties().stacksTo(16)));

    public static final RegistrySupplier<Item> GLOWCHEESE = registerItem("glowcheese", () ->
            new Item(new Item.Properties().food(JNEFoodProperties.GLOWCHEESE)));

    public static final RegistrySupplier<Item> NETHER_PIZZA_SLICE = registerItem("nether_pizza_slice", () ->
            new Item(new Item.Properties().food(JNEFoodProperties.PIZZA_SLICE)));

    public static final RegistrySupplier<Item> WISP_BOTTLE = registerItem("wisp_bottle", () ->
            new MobBottleItem(SoundEvents.BOTTLE_EMPTY, new Item.Properties().stacksTo(1).craftRemainder(Items.GLASS_BOTTLE)));

    public static final RegistrySupplier<Item> WRAITHING_FLESH = registerItem("wraithing_flesh", () ->
            new Item(new Item.Properties().food(JNEFoodProperties.WRAITHING_FLESH)));

    public static final RegistrySupplier<Item> PHASMO_SHARD = registerItem("phasmo_shard", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> PHASMO_ARROW = registerItem("phasmo_arrow", () ->
            new PhasmoArrowItem(new Item.Properties()));

    public static final RegistrySupplier<Item> WARPED_WART = registerItem("warped_wart", () ->
            new BlockItem(JNEBlocks.WARPED_WART.get(), new Item.Properties()));

    public static final RegistrySupplier<Item> MIST_CHARGE = registerItem("mist_charge", () ->
            new MistChargeItem(new Item.Properties()));

    public static final RegistrySupplier<Item> BANSHEE_ROD = registerItem("banshee_rod", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> BANSHEE_POWDER = registerItem("banshee_powder", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> WILL_O_WISP = registerItem("will_o_wisp", () ->
            new WillOWispItem(new Item.Properties()));

    public static final RegistrySupplier<Item> STRIDITE = registerItem("stridite", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> NETHERITE_PLATING = registerItem("netherite_plating", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> TREACHEROUS_FLAME = registerItem("treacherous_flame", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> SHOTGUN_FIST = registerItem("shotgun_fist", () ->
            new ShotgunFistItem(new Item.Properties().stacksTo(1).durability(256).rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> PUMP_CHARGE_SHOTGUN = registerItem("pump_charge_shotgun", () ->
            new PumpChargeShotgunItem(new Item.Properties().stacksTo(1).durability(356).rarity(Rarity.UNCOMMON)));

    public static final RegistrySupplier<Item> CLARET_SIGN = registerItem("claret_sign", () ->
            new SignItem(new Item.Properties().stacksTo(16), JNEBlocks.CLARET_SIGN.get(), JNEBlocks.CLARET_WALL_SIGN.get()));

    public static final RegistrySupplier<Item> CLARET_HANGING_SIGN = registerItem("claret_hanging_sign", () ->
            new HangingSignItem(JNEBlocks.CLARET_HANGING_SIGN.get(), JNEBlocks.CLARET_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));

    public static final RegistrySupplier<Item> SMOKESTALK_SIGN = registerItem("smokestalk_sign", () ->
            new SignItem(new Item.Properties().stacksTo(16), JNEBlocks.SMOKESTALK_SIGN.get(), JNEBlocks.SMOKESTALK_WALL_SIGN.get()));

    public static final RegistrySupplier<Item> SMOKESTALK_HANGING_SIGN = registerItem("smokestalk_hanging_sign", () ->
            new HangingSignItem(JNEBlocks.SMOKESTALK_HANGING_SIGN.get(), JNEBlocks.SMOKESTALK_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));

    public static final RegistrySupplier<Item> MUSIC_DISC_CRICKET  = registerItem("music_disc_cricket", () ->
            new RecordItem(13, JNESoundEvents.MUSIC_DISC_CRICKET.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 164));

    public static final RegistrySupplier<Item> APPARITION_SPAWN_EGG = registerItem("apparition_spawn_egg", () ->
            new ArchitecturySpawnEggItem(JNEEntityType.APPARITION, 4864303, 699311, new Item.Properties()));

    public static final RegistrySupplier<Item> WISP_SPAWN_EGG = registerItem("wisp_spawn_egg", () ->
            new ArchitecturySpawnEggItem(JNEEntityType.WISP, 6022120, 699311, new Item.Properties()));

    public static final RegistrySupplier<Item> VESSEL_SPAWN_EGG = registerItem("vessel_spawn_egg", () ->
            new ArchitecturySpawnEggItem(JNEEntityType.VESSEL, 4864303, 12698049, new Item.Properties()));

    public static final RegistrySupplier<Item> ECTO_SLAB_SPAWN_EGG = registerItem("ecto_slab_spawn_egg", () ->
            new ArchitecturySpawnEggItem(JNEEntityType.ECTO_SLAB, 4864303, 1788232, new Item.Properties()));

    public static final RegistrySupplier<Item> BANSHEE_SPAWN_EGG = registerItem("banshee_spawn_egg", () ->
            new ArchitecturySpawnEggItem(JNEEntityType.BANSHEE, 1985382, 1788232, new Item.Properties()));

    public static final RegistrySupplier<Item> STAMPEDE_SPAWN_EGG = registerItem("stampede_spawn_egg", () ->
            new ArchitecturySpawnEggItem(JNEEntityType.STAMPEDE, 4864303, 10236982, new Item.Properties()));

    public static final RegistrySupplier<Item> SEALED_POTTERY_SHERD = registerItem("sealed_pottery_sherd", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> SPECTRE_POTTERY_SHERD = registerItem("spectre_pottery_sherd", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> MARIONETTE_POTTERY_SHERD = registerItem("marionette_pottery_sherd", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> ELDRITCH_POTTERY_SHERD = registerItem("eldritch_pottery_sherd", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> DECEPTION_POTTERY_SHERD = registerItem("deception_pottery_sherd", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> FIREARM_POTTERY_SHERD = registerItem("firearm_pottery_sherd", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> BOTANICAL_POTTERY_SHERD = registerItem("botanical_pottery_sherd", () ->
            new Item(new Item.Properties()));

    private static <T extends Item> RegistrySupplier<T> registerItem(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    private static RegistrySupplier<Item> registerFuel(String name, Supplier<Item> supplier, int fuelValue) {
        return ITEMS.register(name, () -> {
            Item item = supplier.get();
            FuelRegistry.register(fuelValue, item);
            return item;
        });
    }

    public static void init() {
        ITEMS.register();
    }
}
