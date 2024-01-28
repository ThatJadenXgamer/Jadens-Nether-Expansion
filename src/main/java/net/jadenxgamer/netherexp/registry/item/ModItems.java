package net.jadenxgamer.netherexp.registry.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.ModBlocks;
import net.jadenxgamer.netherexp.registry.entity.ModEntities;
import net.jadenxgamer.netherexp.registry.item.custom.*;
import net.jadenxgamer.netherexp.registry.misc_registry.ModArmorTrimPatterns;
import net.jadenxgamer.netherexp.registry.sound.ModSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Direction;

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
            new SnowballItem(new FabricItemSettings().maxCount(16)));

    public static final Item RAW_PYRITE = registerItem("raw_pyrite",
            new Item(new FabricItemSettings()));

    public static final Item PYRITE_INGOT = registerItem("pyrite_ingot",
            new Item(new FabricItemSettings()));

    public static final Item FOSSIL_FUEL = registerItem("fossil_fuel",
            new Item(new FabricItemSettings()));

    public static final Item RIFT_ARMOR_TRIM_SMITHING_TEMPLATE = registerItem("rift_armor_trim_smithing_template",
            SmithingTemplateItem.of(ModArmorTrimPatterns.RIFT));

    public static final Item NIGHTSPORES = registerItem("nightspores",
            new NightSporesItem(new FabricItemSettings()));

    public static final Item LIGHTSPORES = registerItem("lightspores",
            new LightSporesItem(new FabricItemSettings()));

    public static final Item BLIGHTSPORES = registerItem("blightspores",
            new Item(new FabricItemSettings()));

    public static final Item WITHER_ANTIDOTE = registerItem("wither_antidote",
            new AntidoteItem(new FabricItemSettings()));

    public static final Item HUNGER_ANTIDOTE = registerItem("hunger_antidote",
            new AntidoteItem(new FabricItemSettings()));

    public static final Item SLOWNESS_ANTIDOTE = registerItem("slowness_antidote",
            new AntidoteItem(new FabricItemSettings()));

    public static final Item GLOWCHEESE = registerItem("glowcheese",
            new Item(new FabricItemSettings().food(ModFoodComponents.GLOWCHEESE)));

    public static final Item NETHER_PIZZA = registerItem("nether_pizza",
            new AliasedBlockItem(ModBlocks.NETHER_PIZZA ,new FabricItemSettings().maxCount(1)));

    public static final Item NETHER_PIZZA_SLICE = registerItem("nether_pizza_slice",
            new Item(new FabricItemSettings().food(ModFoodComponents.PIZZA_SLICE)));

    public static final Item WISP_BOTTLE = registerItem("wisp_bottle",
            new EntityBottleItem(ModEntities.WISP, SoundEvents.ITEM_BOTTLE_EMPTY, new FabricItemSettings().maxCount(1)));

    public static final Item MAGMA_CUBE_BUCKET = registerItem("magma_cube_bucket",
            new EntityBucketItem(EntityType.MAGMA_CUBE, Fluids.LAVA, ModSoundEvents.ITEM_BUCKET_EMPTY_MAGMA_CUBE, new FabricItemSettings().maxCount(1)));

    public static final Item WRAITHING_FLESH = registerItem("wraithing_flesh",
            new Item(new FabricItemSettings().food(ModFoodComponents.WRAITHING_FLESH)));

    public static final Item WARPED_WART = registerItem("warped_wart",
            new AliasedBlockItem(ModBlocks.WARPED_WART, new FabricItemSettings()));

    public static final Item MIST_CHARGE = registerItem("mist_charge",
            new MistChargeItem(new FabricItemSettings().maxCount(16)));

    public static final Item NECRO_SHARD = registerItem("necro_shard",
            new Item(new FabricItemSettings()));

    public static final Item GUILLOTINE = registerItem("guillotine",
            new AxeItem(ModToolMaterials.GUILLOTINE, 6.0F, -3.2F, new FabricItemSettings()));

    public static final Item WARPHOPPER_SPAWN_EGG = registerItem("warphopper_spawn_egg",
            new SpawnEggItem(ModEntities.WARPHOPPER,0x119b85,0x324149,
                    new FabricItemSettings()));

    public static final Item APPARITION_SPAWN_EGG = registerItem("apparition_spawn_egg",
            new SpawnEggItem(ModEntities.APPARITION,4864303,699311,
                    new FabricItemSettings()));

    public static final Item WISP_SPAWN_EGG = registerItem("wisp_spawn_egg",
            new SpawnEggItem(ModEntities.WISP,6022120,699311,
                    new FabricItemSettings()));

    public static final Item GRASP_SPAWN_EGG = registerItem("grasp_spawn_egg",
            new SpawnEggItem(ModEntities.GRASP,4864303,6910597,
                    new FabricItemSettings()));

    public static final Item CLARET_SIGN_ITEM = registerItem("claret_sign",
            new SignItem(new FabricItemSettings().maxCount(16), ModBlocks.CLARET_SIGN, ModBlocks.CLARET_WALL_SIGN));

    public static final Item CLARET_HANGING_SIGN_ITEM = registerItem("claret_hanging_sign",
            new HangingSignItem(ModBlocks.CLARET_HANGING_SIGN, ModBlocks.CLARET_WALL_HANGING_SIGN, new FabricItemSettings().maxCount(16)));

    public static final Item SMOKESTALK_SIGN_ITEM = registerItem("smokestalk_sign",
            new SignItem(new FabricItemSettings().maxCount(16), ModBlocks.SMOKESTALK_SIGN, ModBlocks.SMOKESTALK_WALL_SIGN));

    public static final Item SMOKESTALK_HANGING_SIGN_ITEM = registerItem("smokestalk_hanging_sign",
            new HangingSignItem(ModBlocks.SMOKESTALK_HANGING_SIGN, ModBlocks.SMOKESTALK_WALL_HANGING_SIGN, new FabricItemSettings().maxCount(16)));

    // TODO Add Skeletal Heads
//    public static final Item SKELETAL_CREEPER_SKULL_ITEM = registerItem("skeletal_creeper_skull",
//            new VerticallyAttachableBlockItem(ModBlocks.SKELETAL_CREEPER_SKULL, ModBlocks.SKELETAL_CREEPER_WALL_SKULL, new FabricItemSettings().rarity(Rarity.UNCOMMON).equipmentSlot(stack -> EquipmentSlot.HEAD), Direction.DOWN));

    public static final Item MUSIC_DISC_CRICKET = registerItem("music_disc_cricket",
            new MusicDiscItem(13, ModSoundEvents.MUSIC_DISC_CRICKET, new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), 164));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(NetherExp.MOD_ID, name), item);
    }

    public static void registerModItems() {
        NetherExp.LOGGER.debug("Registering Items for " + NetherExp.MOD_ID);
    }
}
