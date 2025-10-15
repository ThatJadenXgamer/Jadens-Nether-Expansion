package net.jadenxgamer.netherexp.registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.entity.Wisp;
import net.jadenxgamer.netherexp.core.item.CerebrageSeedItem;
import net.jadenxgamer.netherexp.core.item.GlowsporesItem;
import net.jadenxgamer.netherexp.core.item.MobBottleItem;
import net.jadenxgamer.netherexp.core.item.NonConsumableItem;
import net.jadenxgamer.netherexp.core.keys.JNEJukeboxSongs;
import net.jadenxgamer.netherexp.core.keys.JNETrimPatterns;
import net.jadenxgamer.netherexp.core.misc.JNEFoods;
import net.jadenxgamer.netherexp.util.RegistryHelper;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class JNEItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, NetherExp.MOD_ID);

    public static final Supplier<Item> HOGHAM = ITEMS.register("hogham", () ->
            new Item(new Item.Properties().food(JNEFoods.HOGHAM)));

    public static final Supplier<Item> COOKED_HOGHAM = ITEMS.register("cooked_hogham", () ->
            new Item(new Item.Properties().food(JNEFoods.COOKED_HOGHAM)));

    public static final Supplier<Item> FOSSIL_FUEL = ITEMS.register("fossil_fuel", () ->
            new Item(new Item.Properties()));

    public static final Supplier<Item> RIFT_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("rift_armor_trim_smithing_template", () ->
            SmithingTemplateItem.createArmorTrimTemplate(JNETrimPatterns.RIFT));

    public static final Supplier<Item> SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("spirit_armor_trim_smithing_template", () ->
            SmithingTemplateItem.createArmorTrimTemplate(JNETrimPatterns.SPIRIT));

    public static final Supplier<Item> VALOR_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("valor_armor_trim_smithing_template", () ->
            SmithingTemplateItem.createArmorTrimTemplate(JNETrimPatterns.VALOR));

    public static final Supplier<Item> PUMP_CHARGE_UPGRADE_SMITHING_TEMPLATE = ITEMS.register("pump_charge_upgrade_smithing_template", () ->
            new Item(new Item.Properties()));

    public static final Supplier<Item> LIGHTSPORES = ITEMS.register("lightspores", () ->
            new GlowsporesItem(JNEParticleTypes.LIGHTSPORE, new Item.Properties()));

    public static final Supplier<Item> NIGHTSPORES = ITEMS.register("nightspores", () ->
            new GlowsporesItem(JNEParticleTypes.NIGHTSPORE, new Item.Properties()));

    public static final Supplier<Item> ANTIDOTE = ITEMS.register("antidote", () ->
            new Item(new Item.Properties()));

    public static final Supplier<Item> GRENADE_ANTIDOTE = ITEMS.register("grenade_antidote", () ->
            new Item(new Item.Properties()));

    public static final Supplier<Item> GLOWCHEESE = ITEMS.register("glowcheese", () ->
            new Item(new Item.Properties().food(JNEFoods.GLOWCHEESE)));

    public static final Supplier<Item> NETHER_PIZZA_SLICE = ITEMS.register("nether_pizza_slice", () ->
            new Item(new Item.Properties().food(JNEFoods.PIZZA_SLICE).rarity(Rarity.RARE)));

    public static final Supplier<Item> WISP_BOTTLE = ITEMS.register("wisp_bottle", () ->
            new MobBottleItem<Wisp>(JNEEntityType.WISP, JNESoundEvents.WISP_BOTTLE_EMPTY, new Item.Properties().stacksTo(1).craftRemainder(Items.GLASS_BOTTLE)));

    public static final Supplier<Item> SANCTUM_COMPASS = ITEMS.register("sanctum_compass", () ->
            new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));

    public static final Supplier<Item> WRAITHING_FLESH = ITEMS.register("wraithing_flesh", () ->
            new Item(new Item.Properties().food(JNEFoods.WRAITHING_FLESH)));

    public static final Supplier<Item> PHASMO_SHARD = ITEMS.register("phasmo_shard", () ->
            new Item(new Item.Properties()));

    public static final Supplier<Item> PHASMO_ARROW = ITEMS.register("phasmo_arrow", () ->
            new Item(new Item.Properties()));

    public static final Supplier<Item> BANSHEE_ROD = ITEMS.register("banshee_rod", () ->
            new Item(new Item.Properties()));

    public static final Supplier<Item> BANSHEE_POWDER = ITEMS.register("banshee_powder", () ->
            new Item(new Item.Properties()));

    public static final Supplier<Item> WILL_O_WISP = ITEMS.register("will_o_wisp", () ->
            new Item(new Item.Properties()));

    public static final Supplier<Item> STRIDITE = ITEMS.register("stridite", () ->
            new Item(new Item.Properties().fireResistant()));

    public static final Supplier<Item> NETHERITE_PLATING = ITEMS.register("netherite_plating", () ->
            new Item(new Item.Properties().fireResistant()));

    public static final Supplier<Item> TREACHEROUS_FLAME = ITEMS.register("treacherous_flame", () ->
            new Item(new Item.Properties().rarity(Rarity.RARE).fireResistant()));

    public static final Supplier<Item> SHOTGUN_FIST = ITEMS.register("shotgun_fist", () ->
            new Item(new Item.Properties().stacksTo(1).durability(512).fireResistant().rarity(Rarity.RARE)));

    public static final Supplier<Item> PUMP_CHARGE_SHOTGUN = ITEMS.register("pump_charge_shotgun", () ->
            new Item(new Item.Properties().stacksTo(1).durability(640).fireResistant().rarity(Rarity.EPIC)));

    public static final Supplier<Item> CLARET_SIGN = ITEMS.register("claret_sign", () ->
            new SignItem(new Item.Properties().stacksTo(16), JNEBlocks.CLARET_SIGN.get(), JNEBlocks.CLARET_WALL_SIGN.get()));

    public static final Supplier<Item> CLARET_HANGING_SIGN = ITEMS.register("claret_hanging_sign", () ->
            new HangingSignItem(JNEBlocks.CLARET_HANGING_SIGN.get(), JNEBlocks.CLARET_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));

    public static final Supplier<Item> MUSIC_DISC_CRICKET  = ITEMS.register("music_disc_cricket", () ->
            new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));

    public static final Supplier<Item> MUSIC_DISC_BUCKSHOT_WONDERLAND = ITEMS.register("music_disc_buckshot_wonderland", () ->
            new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));

    public static final Supplier<Item> SKULL_ON_A_STICK = ITEMS.register("skull_on_a_stick", () ->
            new Item(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).durability(100)));

    public static final Supplier<Item> WISP_SPAWN_EGG = ITEMS.register("wisp_spawn_egg", () ->
            new DeferredSpawnEggItem(JNEEntityType.WISP, 6022120, 699311, new Item.Properties()));

    public static final Supplier<Item> APPARITION_SPAWN_EGG = ITEMS.register("apparition_spawn_egg", () ->
            new DeferredSpawnEggItem(JNEEntityType.APPARITION, 4864303, 699311, new Item.Properties()));

    public static final Supplier<Item> VESSEL_SPAWN_EGG = ITEMS.register("vessel_spawn_egg", () ->
            new DeferredSpawnEggItem(JNEEntityType.VESSEL, 4864303, 12698049, new Item.Properties()));

    public static final Supplier<Item> ECTO_SLAB_SPAWN_EGG = ITEMS.register("ecto_slab_spawn_egg", () ->
            new DeferredSpawnEggItem(JNEEntityType.WISP, 4864303, 1788232, new Item.Properties()));

    public static final Supplier<Item> BANSHEE_SPAWN_EGG = ITEMS.register("banshee_spawn_egg", () ->
            new DeferredSpawnEggItem(JNEEntityType.BANSHEE, 1985382, 1788232, new Item.Properties()));

    public static final Supplier<Item> STAMPEDE_SPAWN_EGG = ITEMS.register("stampede_spawn_egg", () ->
            new DeferredSpawnEggItem(JNEEntityType.WISP, 4864303, 10236982, new Item.Properties()));

    public static final Supplier<Item> CARCASS_SPAWN_EGG = ITEMS.register("carcass_spawn_egg", () ->
            new DeferredSpawnEggItem(JNEEntityType.WISP, 8263192, 4066060, new Item.Properties()));

    public static final Supplier<Item> FALSE_CARCASS_SPAWN_EGG = ITEMS.register("false_carcass_spawn_egg", () ->
            new DeferredSpawnEggItem(JNEEntityType.WISP, 4066060, 8263192, new Item.Properties()));

    public static final Supplier<Item> SEALED_POTTERY_SHERD = ITEMS.register("sealed_pottery_sherd", () ->
            new Item(new Item.Properties()));

    public static final Supplier<Item> SPECTRE_POTTERY_SHERD = ITEMS.register("spectre_pottery_sherd", () ->
            new Item(new Item.Properties()));

    public static final Supplier<Item> MARIONETTE_POTTERY_SHERD = ITEMS.register("marionette_pottery_sherd", () ->
            new Item(new Item.Properties()));

    public static final Supplier<Item> ELDRITCH_POTTERY_SHERD = ITEMS.register("eldritch_pottery_sherd", () ->
            new Item(new Item.Properties()));

    public static final Supplier<Item> DECEPTION_POTTERY_SHERD = ITEMS.register("deception_pottery_sherd", () ->
            new Item(new Item.Properties()));

    public static final Supplier<Item> FIREARM_POTTERY_SHERD = ITEMS.register("firearm_pottery_sherd", () ->
            new Item(new Item.Properties()));

    public static final Supplier<Item> BOTANICAL_POTTERY_SHERD = ITEMS.register("botanical_pottery_sherd", () ->
            new Item(new Item.Properties()));

    public static final Supplier<Item> CEREBRAGE = ITEMS.register("cerebrage", () ->
            new Item(new Item.Properties().food(JNEFoods.CEREBRAGE)));

    public static final Supplier<Item> CEREBRAGE_SEEDS = ITEMS.register("cerebrage_seeds", () ->
            new CerebrageSeedItem(new Item.Properties()));

    public static final Supplier<Item> ROASTED_BONE = ITEMS.register("roasted_bone", () ->
            new Item(new Item.Properties().food(JNEFoods.ROASTED_BONE).stacksTo(16)));

    public static final Supplier<Item> WEEPING_HELIX = ITEMS.register("weeping_helix", () ->
            new Item(new Item.Properties()));

    public static final Supplier<Item> TWISTING_HELIX = ITEMS.register("twisting_helix", () ->
            new Item(new Item.Properties()));

    public static final Supplier<Item> ANCIENT_WAX = ITEMS.register("ancient_wax", () ->
            new Item(new Item.Properties()));

    public static final Supplier<Item> ANCIENT_TORCH = ITEMS.register("ancient_torch", () ->
            new StandingAndWallBlockItem(JNEBlocks.ANCIENT_TORCH.get(), JNEBlocks.ANCIENT_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN));

    public static final Supplier<Item> OCHRE_FROGMIST = ITEMS.register("ochre_frogmist", () ->
            new PlaceOnWaterBlockItem(JNEBlocks.OCHRE_FROGMIST.get(), new Item.Properties()));

    public static final Supplier<Item> PEARLESCENT_FROGMIST = ITEMS.register("pearlescent_frogmist", () ->
            new PlaceOnWaterBlockItem(JNEBlocks.PEARLESCENT_FROGMIST.get(), new Item.Properties()));

    public static final Supplier<Item> VERDANT_FROGMIST = ITEMS.register("verdant_frogmist", () ->
            new PlaceOnWaterBlockItem(JNEBlocks.VERDANT_FROGMIST.get(), new Item.Properties()));
    
    /**
     * Artifacts
     */

    public static final Supplier<Item> SHOTGUN_CORE = ITEMS.register("shotgun_core", () ->
            new NonConsumableItem(new Item.Properties().fireResistant().stacksTo(1)));
    
    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static void backportRegistries(RegisterEvent event) {
        event.register(Registries.ITEM,
                registry -> {
                    if (!BuiltInRegistries.ITEM.containsKey(NetherExp.idVanilla("music_disc_tears")))
                        RegistryHelper.vanillaRegister(registry, "music_disc_tears", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(JNEJukeboxSongs.TEARS)));
                }
        );
    }
}
