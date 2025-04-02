package net.jadenxgamer.netherexp.registry.item;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.fluid.JNEFluids;
import net.jadenxgamer.netherexp.registry.item.custom.*;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETrimPatterns;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class JNEItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NetherExp.MOD_ID);

    public static final Rarity ARTIFACT = Rarity.create("artifact", style -> style.withColor(15218975));

    public static final RegistryObject<Item> WARPHOPPER_FUR = registerItem("warphopper_fur", () ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> HOGHAM = registerItem("hogham", () ->
            new Item(new Item.Properties().food(JNEFoods.HOGHAM)));

    public static final RegistryObject<Item> COOKED_HOGHAM = registerItem("cooked_hogham", () ->
            new Item(new Item.Properties().food(JNEFoods.COOKED_HOGHAM)));

    public static final RegistryObject<Item> FOGGY_ESSENCE = registerItem("foggy_essence", () ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> IRON_SCRAP = registerItem("iron_scrap", () ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> FOSSIL_FUEL = registerItem("fossil_fuel", () ->
            new FuelItem(new Item.Properties(), 1600));

    public static final RegistryObject<Item> RIFT_ARMOR_TRIM_SMITHING_TEMPLATE = registerItem("rift_armor_trim_smithing_template", () ->
            SmithingTemplateItem.createArmorTrimTemplate(JNETrimPatterns.RIFT));

    public static final RegistryObject<Item> SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE = registerItem("spirit_armor_trim_smithing_template", () ->
            SmithingTemplateItem.createArmorTrimTemplate(JNETrimPatterns.SPIRIT));

    public static final RegistryObject<Item> VALOR_ARMOR_TRIM_SMITHING_TEMPLATE = registerItem("valor_armor_trim_smithing_template", () ->
            SmithingTemplateItem.createArmorTrimTemplate(JNETrimPatterns.VALOR));

    public static final RegistryObject<Item> PUMP_CHARGE_UPGRADE_SMITHING_TEMPLATE = registerItem("pump_charge_upgrade_smithing_template",
            PumpChargeSmithingTemplateItem::new);

    public static final RegistryObject<Item> LIGHTSPORES = registerItem("lightspores", () ->
            new LightsporesItem(new Item.Properties()));

    public static final RegistryObject<Item> NIGHTSPORES = registerItem("nightspores", () ->
            new NightsporesItem(new Item.Properties()));

    public static final RegistryObject<Item> ANTIDOTE = registerItem("antidote", () ->
            new AntidoteItem(new Item.Properties()));

    public static final RegistryObject<Item> GRENADE_ANTIDOTE = registerItem("grenade_antidote", () ->
            new GrenadeAntidoteItem(new Item.Properties()));

    public static final RegistryObject<Item> GLOWCHEESE = registerItem("glowcheese", () ->
            new Item(new Item.Properties().food(JNEFoods.GLOWCHEESE)));

    public static final RegistryObject<Item> NETHER_PIZZA_SLICE = registerItem("nether_pizza_slice", () ->
            new Item(new Item.Properties().food(JNEFoods.PIZZA_SLICE).rarity(Rarity.RARE)));

    public static final RegistryObject<Item> WISP_BOTTLE = registerItem("wisp_bottle", () ->
            new MobBottleItem<>(JNEEntityType.WISP, SoundEvents.BOTTLE_FILL, new Item.Properties().stacksTo(1).craftRemainder(Items.GLASS_BOTTLE)));

    public static final RegistryObject<Item> SANCTUM_COMPASS = registerItem("sanctum_compass", () ->
            new SanctumCompassItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));

    public static final RegistryObject<Item> WRAITHING_FLESH = registerItem("wraithing_flesh", () ->
            new Item(new Item.Properties().food(JNEFoods.WRAITHING_FLESH)));

    public static final RegistryObject<Item> PHASMO_SHARD = registerItem("phasmo_shard", () ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> PHASMO_ARROW = registerItem("phasmo_arrow", () ->
            new PhasmoArrowItem(new Item.Properties()));

    public static final RegistryObject<Item> WARPED_WART = registerItem("warped_wart", () ->
            new BlockItem(JNEBlocks.WARPED_WART.get(), new Item.Properties()));

    public static final RegistryObject<Item> MIST_CHARGE = registerItem("mist_charge", () ->
            new MistChargeItem(new Item.Properties()));

    public static final RegistryObject<Item> BANSHEE_ROD = registerItem("banshee_rod", () ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> BANSHEE_POWDER = registerItem("banshee_powder", () ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> WILL_O_WISP = registerItem("will_o_wisp", () ->
            new WillOWispItem(new Item.Properties()));

    public static final RegistryObject<Item> STRIDITE = registerItem("stridite", () ->
            new Item(new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> NETHERITE_PLATING = registerItem("netherite_plating", () ->
            new Item(new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> TREACHEROUS_FLAME = registerItem("treacherous_flame", () ->
            new TreacherousFlameItem(new Item.Properties().rarity(Rarity.RARE).fireResistant()));

    public static final RegistryObject<Item> SHOTGUN_FIST = registerItem("shotgun_fist", () ->
            new ShotgunFistItem(new Item.Properties().stacksTo(1).durability(512).fireResistant().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> PUMP_CHARGE_SHOTGUN = registerItem("pump_charge_shotgun", () ->
            new PumpChargeShotgunItem(new Item.Properties().stacksTo(1).durability(640).fireResistant().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> CLARET_SIGN = registerItem("claret_sign", () ->
            new SignItem(new Item.Properties().stacksTo(16), JNEBlocks.CLARET_SIGN.get(), JNEBlocks.CLARET_WALL_SIGN.get()));

    public static final RegistryObject<Item> CLARET_HANGING_SIGN = registerItem("claret_hanging_sign", () ->
            new HangingSignItem(JNEBlocks.CLARET_HANGING_SIGN.get(), JNEBlocks.CLARET_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> SMOKESTALK_SIGN = registerItem("smokestalk_sign", () ->
            new SignItem(new Item.Properties().stacksTo(16), JNEBlocks.SMOKESTALK_SIGN.get(), JNEBlocks.SMOKESTALK_WALL_SIGN.get()));

    public static final RegistryObject<Item> SMOKESTALK_HANGING_SIGN = registerItem("smokestalk_hanging_sign", () ->
            new HangingSignItem(JNEBlocks.SMOKESTALK_HANGING_SIGN.get(), JNEBlocks.SMOKESTALK_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> MUSIC_DISC_CRICKET  = registerItem("music_disc_cricket", () ->
            new RecordItem(13, JNESoundEvents.MUSIC_DISC_CRICKET.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 164));

    public static final RegistryObject<Item> MUSIC_DISC_BUCKSHOT_WONDERLAND = registerItem("music_disc_buckshot_wonderland", () ->
            new RecordItem(8, JNESoundEvents.MUSIC_DISC_BUCKSHOT_WONDERLAND.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 193));

    public static final RegistryObject<Item> SKULL_ON_A_STICK = registerItem("skull_on_a_stick", () ->
            new JNEFoodOnAStickItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).durability(100)));

    public static final RegistryObject<Item> APPARITION_SPAWN_EGG = registerItem("apparition_spawn_egg", () ->
            new ForgeSpawnEggItem(JNEEntityType.APPARITION, 4864303, 699311, new Item.Properties()));

    public static final RegistryObject<Item> WISP_SPAWN_EGG = registerItem("wisp_spawn_egg", () ->
            new ForgeSpawnEggItem(JNEEntityType.WISP, 6022120, 699311, new Item.Properties()));

    public static final RegistryObject<Item> VESSEL_SPAWN_EGG = registerItem("vessel_spawn_egg", () ->
            new ForgeSpawnEggItem(JNEEntityType.VESSEL, 4864303, 12698049, new Item.Properties()));

    public static final RegistryObject<Item> ECTO_SLAB_SPAWN_EGG = registerItem("ecto_slab_spawn_egg", () ->
            new ForgeSpawnEggItem(JNEEntityType.ECTO_SLAB, 4864303, 1788232, new Item.Properties()));

    public static final RegistryObject<Item> BANSHEE_SPAWN_EGG = registerItem("banshee_spawn_egg", () ->
            new ForgeSpawnEggItem(JNEEntityType.BANSHEE, 1985382, 1788232, new Item.Properties()));

    public static final RegistryObject<Item> STAMPEDE_SPAWN_EGG = registerItem("stampede_spawn_egg", () ->
            new ForgeSpawnEggItem(JNEEntityType.STAMPEDE, 4864303, 10236982, new Item.Properties()));

    public static final RegistryObject<Item> CARCASS_SPAWN_EGG = registerItem("carcass_spawn_egg", () ->
            new ForgeSpawnEggItem(JNEEntityType.CARCASS, 8263192, 4066060, new Item.Properties()));

    public static final RegistryObject<Item> SEALED_POTTERY_SHERD = registerItem("sealed_pottery_sherd", () ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> SPECTRE_POTTERY_SHERD = registerItem("spectre_pottery_sherd", () ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> MARIONETTE_POTTERY_SHERD = registerItem("marionette_pottery_sherd", () ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> ELDRITCH_POTTERY_SHERD = registerItem("eldritch_pottery_sherd", () ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> DECEPTION_POTTERY_SHERD = registerItem("deception_pottery_sherd", () ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> FIREARM_POTTERY_SHERD = registerItem("firearm_pottery_sherd", () ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> BOTANICAL_POTTERY_SHERD = registerItem("botanical_pottery_sherd", () ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> CEREBRAGE = registerItem("cerebrage", () ->
            new Item(new Item.Properties().food(JNEFoods.CEREBRAGE)));

    public static final RegistryObject<Item> CEREBRAGE_SEEDS = registerItem("cerebrage_seeds", () ->
            new CerebrageSeedItem(new Item.Properties()));

    public static final RegistryObject<Item> ROASTED_BONE = registerItem("roasted_bone", () ->
            new Item(new Item.Properties().food(JNEFoods.ROASTED_BONE).stacksTo(16)));

    public static final RegistryObject<Item> WEEPING_HELIX = registerItem("weeping_helix", () ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> TWISTING_HELIX = registerItem("twisting_helix", () ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> ANCIENT_WAX = registerItem("ancient_wax", () ->
            new AncientWaxItem(new Item.Properties()));

    public static final RegistryObject<Item> ANCIENT_TORCH = registerItem("ancient_torch", () ->
            new StandingAndWallBlockItem(JNEBlocks.ANCIENT_TORCH.get(), JNEBlocks.ANCIENT_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN));

    // ARTIFACTS

    public static final RegistryObject<Item> SHOTGUN_CORE = registerItem("shotgun_core", () ->
            new NetheriteArtifactItem(new Item.Properties().fireResistant().stacksTo(1).rarity(ARTIFACT)));

    /**
     * Mod Compat
     */

    public static final RegistryObject<Item> BRIGHTSPORES = registerItem("brightspores", () ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> BLIGHTSPORES = registerItem("blightspores", () ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> BLIGHTWART = registerItem("blightwart", () ->
            new BlockItem(JNEBlocks.BLIGHTWART.get(), new Item.Properties()));

    public static final RegistryObject<Item> NECROMIUM_PLATING = registerItem("necromium_plating", () ->
            new Item(new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> JACKHAMMER_FIST = registerItem("jackhammer_fist", () ->
            new JackhammerFistItem(new Item.Properties().stacksTo(1).durability(640).fireResistant().rarity(Rarity.EPIC)));


    private static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
