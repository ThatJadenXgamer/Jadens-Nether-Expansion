package net.jadenxgamer.netherexp.registry.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.fluid.JNEFluids;
import net.jadenxgamer.netherexp.registry.item.brewing.Antidotes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackLinkedSet;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Set;

public class JNECreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(NetherExp.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> NETHEREXP = CREATIVE_MODE_TABS.register("netherexp",
            () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 1).icon(() -> new ItemStack(Items.NETHERITE_BLOCK))
                    .title(Component.literal("Jaden's Nether Expansion"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(JNEBlocks.SOUL_SLATE.get());
                        output.accept(JNEBlocks.SOUL_SLATE_STAIRS.get());
                        output.accept(JNEBlocks.SOUL_SLATE_SLAB.get());
                        output.accept(JNEBlocks.SOUL_SLATE_WALL.get());
                        output.accept(JNEBlocks.SOUL_SLATE_BRICKS.get());
                        output.accept(JNEBlocks.SOUL_SLATE_BRICK_STAIRS.get());
                        output.accept(JNEBlocks.SOUL_SLATE_BRICK_SLAB.get());
                        output.accept(JNEBlocks.SOUL_SLATE_BRICK_WALL.get());
                        output.accept(JNEBlocks.CRACKED_SOUL_SLATE_BRICKS.get());
                        output.accept(JNEBlocks.ETCHED_SOUL_SLATE_BRICKS.get());
                        output.accept(JNEBlocks.CHISELED_SOUL_SLATE_BRICKS.get());
                        output.accept(JNEBlocks.SOUL_SLATE_BRICK_PILLAR.get());

                        output.accept(JNEBlocks.PALE_SOUL_SLATE.get());
                        output.accept(JNEBlocks.SOUL_SLATE_TILES.get());
                        output.accept(JNEBlocks.SOUL_SLATE_TILE_STAIRS.get());
                        output.accept(JNEBlocks.SOUL_SLATE_TILE_SLAB.get());
                        output.accept(JNEBlocks.SOUL_SLATE_TILE_WALL.get());
                        output.accept(JNEBlocks.ETCHED_SOUL_SLATE_TILES.get());
                        output.accept(JNEBlocks.CHISELED_SOUL_SLATE_TILES.get());

                        output.accept(JNEBlocks.FOSSIL_ORE.get());
                        output.accept(JNEBlocks.FOSSIL_FUEL_ORE.get());
                        output.accept(JNEBlocks.DIAMOND_FOSSIL_ORE.get());
                        output.accept(JNEItems.FOSSIL_FUEL.get());

                        output.accept(JNEItems.SHOTGUN_FIST.get());
                        output.accept(JNEBlocks.SHOTGUN_BARREL.get());

                        output.accept(JNEItems.TREACHEROUS_FLAME.get());
                        output.accept(JNEBlocks.TREACHEROUS_CANDLE.get());
                        output.accept(JNEBlocks.BRAZIER_CHEST.get());
                        output.accept(JNEItems.PUMP_CHARGE_UPGRADE_SMITHING_TEMPLATE.get());
                        output.accept(JNEItems.PUMP_CHARGE_SHOTGUN.get());

                        output.accept(JNEItems.SANCTUM_COMPASS.get());
                        output.accept(JNEItems.WRAITHING_FLESH.get());
                        output.accept(JNEBlocks.SOUL_CANDLE.get());

                        output.accept(JNEBlocks.SOUL_SWIRLS.get());
                        if (NetherExp.compatCinderscapes()){
                            output.accept(JNEBlocks.SHALE_SWIRLS.get());
                        }
                        output.accept(JNEBlocks.SOUL_SOIL_LAYER.get());
                        output.accept(Blocks.SOUL_SOIL);
                        output.accept(JNEBlocks.SOUL_PATH.get());
                        output.accept(JNEBlocks.SUSPICIOUS_SOUL_SAND.get());
                        output.accept(Blocks.SOUL_SAND);
                        output.accept(JNEBlocks.ECTO_SOUL_SAND.get());
                        output.accept(JNEBlocks.SOUL_MAGMA_BLOCK.get());

                        output.accept(JNEItems.PHASMO_SHARD.get());
                        output.accept(JNEItems.PHASMO_ARROW.get());
                        output.accept(JNEBlocks.SOUL_GLASS.get());
                        output.accept(JNEBlocks.OCHRE_FROGMIST.get());
                        output.accept(JNEBlocks.VERDANT_FROGMIST.get());
                        output.accept(JNEBlocks.PEARLESCENT_FROGMIST.get());

                        output.accept(JNEItems.BANSHEE_ROD.get());
                        output.accept(JNEItems.BANSHEE_POWDER.get());
                        output.accept(JNEItems.WILL_O_WISP.get());

                        output.accept(JNEBlocks.BLACK_ICE.get());
                        output.accept(JNEFluids.ECTOPLASM_BUCKET.get());

                        output.accept(Blocks.SOUL_TORCH);
                        output.accept(Blocks.SOUL_LANTERN);
                        output.accept(Blocks.SOUL_CAMPFIRE);
                        output.accept(JNEBlocks.SOUL_JACK_O_LANTERN.get());

                        output.accept(Items.PUMPKIN_SEEDS);
                        output.accept(JNEBlocks.SORROWSQUASH.get());
                        output.accept(JNEBlocks.CARVED_SORROWSQUASH.get());
                        output.accept(JNEBlocks.GHOUL_O_LANTERN.get());
                        output.accept(JNEBlocks.SOUL_GHOUL_O_LANTERN.get());

                        output.accept(Blocks.NETHERRACK);
                        output.accept(JNEBlocks.SMOOTH_NETHERRACK.get());
                        output.accept(JNEBlocks.SMOOTH_NETHERRACK_STAIRS.get());
                        output.accept(JNEBlocks.SMOOTH_NETHERRACK_SLAB.get());
                        output.accept(JNEBlocks.SMOOTH_NETHERRACK_WALL.get());

                        output.accept(JNEBlocks.NETHERRACK_BRICKS.get());
                        output.accept(JNEBlocks.NETHERRACK_BRICK_STAIRS.get());
                        output.accept(JNEBlocks.NETHERRACK_BRICK_SLAB.get());
                        output.accept(JNEBlocks.NETHERRACK_BRICK_WALL.get());
                        output.accept(JNEBlocks.NETHERRACK_TILES.get());
                        output.accept(JNEBlocks.NETHERRACK_PILLAR.get());

                        output.accept(JNEItems.WHITE_ASH_POWDER.get());
                        output.accept(JNEBlocks.WHITE_ASH_BLOCK.get());
                        output.accept(JNEBlocks.WHITE_ASH.get());
                        output.accept(Blocks.BASALT);
                        output.accept(JNEBlocks.BASALT_SLAB.get());
                        output.accept(JNEBlocks.BASALT_STAIRS.get());
                        output.accept(JNEBlocks.BASALT_WALL.get());
                        output.accept(Blocks.POLISHED_BASALT);
                        output.accept(JNEBlocks.POLISHED_BASALT_SLAB.get());
                        output.accept(JNEBlocks.POLISHED_BASALT_STAIRS.get());
                        output.accept(JNEBlocks.POLISHED_BASALT_WALL.get());
                        output.accept(JNEBlocks.POLISHED_BASALT_BRICKS.get());
                        output.accept(JNEBlocks.POLISHED_BASALT_BRICK_STAIRS.get());
                        output.accept(JNEBlocks.POLISHED_BASALT_BRICK_SLAB.get());
                        output.accept(JNEBlocks.POLISHED_BASALT_BRICK_WALL.get());

                        output.accept(Items.NETHERITE_INGOT);
                        output.accept(Blocks.NETHERITE_BLOCK);
                        output.accept(JNEItems.STRIDITE.get());
                        output.accept(JNEItems.NETHERITE_PLATING.get());
                        output.accept(JNEBlocks.NETHERITE_PLATED_BLOCK.get());
                        output.accept(JNEBlocks.NETHERITE_GRATE.get());
                        output.accept(JNEBlocks.CUT_NETHERITE_BLOCK.get());
                        output.accept(JNEBlocks.CUT_NETHERITE_STAIRS.get());
                        output.accept(JNEBlocks.CUT_NETHERITE_SLAB.get());
                        output.accept(JNEBlocks.CUT_NETHERITE_PILLAR.get());

                        output.accept(Items.NETHERITE_SCRAP);
                        output.accept(Blocks.ANCIENT_DEBRIS);
                        output.accept(JNEBlocks.RUSTY_NETHERITE_PLATED_BLOCK.get());
                        output.accept(JNEBlocks.RUSTY_NETHERITE_GRATE.get());
                        output.accept(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK.get());
                        output.accept(JNEBlocks.RUSTY_CUT_NETHERITE_STAIRS.get());
                        output.accept(JNEBlocks.RUSTY_CUT_NETHERITE_SLAB.get());
                        output.accept(JNEBlocks.RUSTY_CUT_NETHERITE_PILLAR.get());

//                        if (NetherExp.getConfig().gamemechanics.enable_unfinished_items) {
//                            output.accept(JNEBlocks.ENIGMA_CROWN);
//                            output.accept(JNEBlocks.ENIGMA_SHELF);
//                            output.accept(JNEBlocks.ENIGMA_FLESH);
//                            output.accept(JNEBlocks.STRANGE_ENIGMA_FLESH);
//                        }

                        output.accept(JNEBlocks.CLARET_STEM.get());
                        output.accept(JNEBlocks.CLARET_HYPHAE.get());
                        output.accept(JNEBlocks.STRIPPED_CLARET_STEM.get());
                        output.accept(JNEBlocks.STRIPPED_CLARET_HYPHAE.get());
                        output.accept(JNEBlocks.CLARET_PLANKS.get());
                        output.accept(JNEBlocks.CLARET_STAIRS.get());
                        output.accept(JNEBlocks.CLARET_SLAB.get());
                        output.accept(JNEBlocks.CLARET_FENCE.get());
                        output.accept(JNEBlocks.CLARET_FENCE_GATE.get());
                        output.accept(JNEBlocks.CLARET_DOOR.get());
                        output.accept(JNEBlocks.CLARET_TRAPDOOR.get());
                        output.accept(JNEBlocks.CLARET_PRESSURE_PLATE.get());
                        output.accept(JNEBlocks.CLARET_BUTTON.get());
                        output.accept(JNEItems.CLARET_SIGN.get());
                        output.accept(JNEItems.CLARET_HANGING_SIGN.get());

                        output.accept(Items.MAGMA_CREAM);
                        output.accept(JNEBlocks.MAGMA_CREAM_BLOCK.get());

                        output.accept(JNEBlocks.IGNEOUS_REEDS.get());
                        output.accept(JNEBlocks.SMOKESTALK.get());
                        output.accept(JNEBlocks.SMOKESTALK_BLOCK.get());
                        output.accept(JNEBlocks.STRIPPED_SMOKESTALK_BLOCK.get());
                        output.accept(JNEBlocks.SMOKESTALK_PLANKS.get());
                        output.accept(JNEBlocks.SMOKESTALK_STAIRS.get());
                        output.accept(JNEBlocks.SMOKESTALK_SLAB.get());
                        output.accept(JNEBlocks.SMOKESTALK_FENCE.get());
                        output.accept(JNEBlocks.SMOKESTALK_FENCE_GATE.get());
                        output.accept(JNEBlocks.SMOKESTALK_DOOR.get());
                        output.accept(JNEBlocks.SMOKESTALK_TRAPDOOR.get());
                        output.accept(JNEBlocks.SMOKESTALK_PRESSURE_PLATE.get());
                        output.accept(JNEBlocks.SMOKESTALK_BUTTON.get());
                        output.accept(JNEItems.SMOKESTALK_SIGN.get());
                        output.accept(JNEItems.SMOKESTALK_HANGING_SIGN.get());
//                        if (NetherExp.getConfig().gamemechanics.enable_unfinished_items) {
//
////                            output.accept(JNEBlocks.EXPLOSIVE_SCORIA);
//                            output.accept(JNEItems.IRON_SCRAP.get());
//                        }

                        output.accept(Items.QUARTZ);
                        output.accept(JNEBlocks.QUARTZ_CRYSTAL.get());
                        output.accept(JNEBlocks.QUARTZ_CRYSTAL_BLOCK.get());
                        output.accept(Blocks.QUARTZ_BLOCK);
                        output.accept(Blocks.QUARTZ_STAIRS);
                        output.accept(Blocks.QUARTZ_SLAB);
                        output.accept(Blocks.CHISELED_QUARTZ_BLOCK);
                        output.accept(Blocks.QUARTZ_BRICKS);
                        output.accept(JNEBlocks.CRACKED_QUARTZ_BRICKS.get());
                        output.accept(JNEBlocks.CHISELED_QUARTZ_PILLAR.get());
                        output.accept(Blocks.QUARTZ_PILLAR);
                        output.accept(Blocks.SMOOTH_QUARTZ);
                        output.accept(Blocks.SMOOTH_QUARTZ_STAIRS);
                        output.accept(Blocks.SMOOTH_QUARTZ_SLAB);

//                        output.accept(JNEBlocks.SILICA_SAND.get());
//                        output.accept(JNEBlocks.SILICA_SANDSTONE.get());
//                        output.accept(JNEBlocks.SILICA_SANDSTONE_STAIRS.get());
//                        output.accept(JNEBlocks.SILICA_SANDSTONE_SLAB.get());
//                        output.accept(JNEBlocks.SILICA_SANDSTONE_WALL.get());
//                        output.accept(JNEBlocks.CUT_SILICA_SANDSTONE.get());
//                        output.accept(JNEBlocks.CUT_SILICA_SANDSTONE_SLAB.get());
//                        output.accept(JNEBlocks.CHISELED_SILICA_SANDSTONE.get());
//                        output.accept(JNEBlocks.SMOOTH_SILICA_SANDSTONE.get());
//                        output.accept(JNEBlocks.SMOOTH_SILICA_SANDSTONE_STAIRS.get());
//                        output.accept(JNEBlocks.SMOOTH_SILICA_SANDSTONE_SLAB.get());

                        output.accept(Blocks.NETHER_BRICKS);
                        output.accept(Blocks.NETHER_BRICK_STAIRS);
                        output.accept(Blocks.NETHER_BRICK_SLAB);
                        output.accept(Blocks.NETHER_BRICK_WALL);
                        output.accept(Blocks.NETHER_BRICK_FENCE);
                        output.accept(JNEBlocks.NETHER_BRICK_PILLAR.get());
                        output.accept(Blocks.CRACKED_NETHER_BRICKS);
                        output.accept(Blocks.CHISELED_NETHER_BRICKS);

                        output.accept(JNEBlocks.RED_MIXED_NETHER_BRICKS.get());
                        output.accept(Blocks.RED_NETHER_BRICKS);
                        output.accept(Blocks.RED_NETHER_BRICK_STAIRS);
                        output.accept(Blocks.RED_NETHER_BRICK_SLAB);
                        output.accept(Blocks.RED_NETHER_BRICK_WALL);

                        output.accept(JNEBlocks.BLUE_MIXED_NETHER_BRICKS.get());
                        output.accept(JNEBlocks.BLUE_NETHER_BRICKS.get());
                        output.accept(JNEBlocks.BLUE_NETHER_BRICK_STAIRS.get());
                        output.accept(JNEBlocks.BLUE_NETHER_BRICK_SLAB.get());
                        output.accept(JNEBlocks.BLUE_NETHER_BRICK_WALL.get());

//                        if (NetherExp.checkModCompatCinderscapes()) {
//                            output.accept(JNEBlocks.VIOLET_MIXED_NETHER_BRICKS);
//                            output.accept(JNEBlocks.VIOLET_NETHER_BRICKS);
//                            output.accept(JNEBlocks.VIOLET_NETHER_BRICK_STAIRS);
//                            output.accept(JNEBlocks.VIOLET_NETHER_BRICK_SLAB);
//                            output.accept(JNEBlocks.VIOLET_NETHER_BRICK_WALL);
//                        }
//
//                        if (NetherExp.checkModCompatGardensOfTheDead()) {
//                            output.accept(JNEBlocks.YELLOW_MIXED_NETHER_BRICKS);
//                            output.accept(JNEBlocks.YELLOW_NETHER_BRICKS);
//                            output.accept(JNEBlocks.YELLOW_NETHER_BRICK_STAIRS);
//                            output.accept(JNEBlocks.YELLOW_NETHER_BRICK_SLAB);
//                            output.accept(JNEBlocks.YELLOW_NETHER_BRICK_WALL);
//                        }

                        output.accept(JNEItems.LIGHTSPORES.get());
                        output.accept(Blocks.SHROOMLIGHT);
                        output.accept(JNEItems.NIGHTSPORES.get());
                        output.accept(JNEBlocks.SHROOMNIGHT.get());
                        if (NetherExp.compatCinderscapes()) {
                            output.accept(JNEItems.BLIGHTSPORES.get());
                            output.accept(JNEBlocks.SHROOMBLIGHT.get());
                        }
                        if (NetherExp.compatGardensOfTheDead()) {
                            output.accept(JNEItems.FRIGHTSPORES.get());
                            output.accept(JNEBlocks.SHROOMFRIGHT.get());
                        }

                        output.accept(JNEItems.GLOWCHEESE.get());
                        output.accept(JNEBlocks.NETHER_PIZZA.get());
                        output.accept(JNEItems.NETHER_PIZZA_SLICE.get());

                        output.accept(Blocks.CRIMSON_NYLIUM);
                        output.accept(JNEBlocks.CRIMSON_NYLIUM_PATH.get());
                        output.accept(Items.NETHER_WART);
                        output.accept(Blocks.NETHER_WART_BLOCK);
                        output.accept(JNEBlocks.NETHER_WART_BEARD.get());
                        output.accept(JNEBlocks.RED_SCALE_FUNGUS.get());
                        output.accept(Blocks.WEEPING_VINES);
                        output.accept(JNEBlocks.WEEPING_IVY.get());
                        output.accept(JNEBlocks.CRIMSON_SPROUTS.get());

                        output.accept(Blocks.WARPED_NYLIUM);
                        output.accept(JNEBlocks.WARPED_NYLIUM_PATH.get());
                        output.accept(JNEItems.WARPED_WART.get());
                        output.accept(Blocks.WARPED_WART_BLOCK);
                        output.accept(JNEBlocks.WARPED_WART_BEARD.get());
                        output.accept(JNEBlocks.BLUE_SCALE_FUNGUS.get());
                        output.accept(Blocks.TWISTING_VINES);
                        output.accept(JNEBlocks.TWISTING_IVY.get());
                        output.accept(Blocks.NETHER_SPROUTS);

                        if (NetherExp.compatCinderscapes()) {
                            output.accept(JNEBlocks.UMBRAL_NYLIUM_PATH.get());
                            output.accept(JNEBlocks.UMBRAL_WART_BEARD.get());
                            output.accept(JNEBlocks.VIOLET_SCALE_FUNGUS.get());
//                            output.accept(JNEBlocks.TWILIGHT_VINES.get());
                            output.accept(JNEBlocks.TWILIGHT_IVY.get());
                        }

                        output.accept(JNEItems.WISP_BOTTLE.get());

                        output.accept(JNEItems.HOGHAM.get());
                        output.accept(JNEItems.COOKED_HOGHAM.get());

//                        if (NetherExp.getConfig().gamemechanics.enable_unfinished_items) {
//                            output.accept(JNEItems.WARPHOPPER_FUR.get());
//                            output.accept(JNEItems.MUSIC_DISC_CRICKET.get());
//                        }

                        output.accept(JNEBlocks.CRIMSON_SPORESHROOM.get());
                        output.accept(JNEBlocks.WARPED_SPORESHROOM.get());
                        if (NetherExp.compatCinderscapes()) {
                            output.accept(JNEBlocks.UMBRAL_SPORESHROOM.get());
                        }
                        if (NetherExp.compatGardensOfTheDead()) {
                            output.accept(JNEBlocks.SOULBLIGHT_SPORESHROOM.get());
                        }
                        output.accept(JNEBlocks.SOULED_GEYSER.get());
                        output.accept(JNEBlocks.BASALTIC_GEYSER.get());
                        if (NetherExp.compatCinderscapes()) {
                            output.accept(JNEBlocks.BLACKSTONIC_GEYSER.get());
                            output.accept(JNEBlocks.ASHEN_GEYSER.get());
                        }

//                        output.accept(JNEItems.SKULL_ON_A_STICK);
                        output.accept(Items.SKELETON_SKULL);
                        output.accept(JNEBlocks.SKELETON_SKULL_CANDLE.get());
                        output.accept(JNEBlocks.SOUL_SKELETON_SKULL_CANDLE.get());

                        output.accept(Items.BONE);
                        output.accept(JNEBlocks.BONE_ROD.get());
                        output.accept(JNEBlocks.BONE_FENCE.get());
                        output.accept(Blocks.BONE_BLOCK);
                        output.accept(JNEBlocks.BONE_CORTICAL.get());
                        output.accept(JNEBlocks.STACKED_BONES.get());
                        output.accept(JNEBlocks.STACKED_BONE_STAIRS.get());
                        output.accept(JNEBlocks.STACKED_BONE_SLAB.get());
                        output.accept(JNEBlocks.SKULL_BLOCK.get());
                        output.accept(JNEBlocks.BURNING_SKULL_BLOCK.get());
                        output.accept(JNEBlocks.SOUL_BURNING_SKULL_BLOCK.get());

                        output.accept(JNEBlocks.WITHER_BONE_BLOCK.get());
                        output.accept(JNEBlocks.STACKED_WITHER_BONES.get());
                        output.accept(JNEBlocks.STACKED_WITHER_BONE_STAIRS.get());
                        output.accept(JNEBlocks.STACKED_WITHER_BONE_SLAB.get());
                        output.accept(JNEBlocks.WITHER_SKULL_BLOCK.get());
                        output.accept(JNEBlocks.BURNING_WITHER_SKULL_BLOCK.get());
                        output.accept(JNEBlocks.SOUL_BURNING_WITHER_SKULL_BLOCK.get());

                        output.accept(Blocks.GILDED_BLACKSTONE);
                        output.accept(Blocks.BLACKSTONE);
                        output.accept(Blocks.BLACKSTONE_STAIRS);
                        output.accept(Blocks.BLACKSTONE_SLAB);
                        output.accept(Blocks.BLACKSTONE_WALL);
                        output.accept(Blocks.CHISELED_POLISHED_BLACKSTONE);
                        output.accept(JNEBlocks.POLISHED_BLACKSTONE_PILLAR.get());
                        output.accept(Blocks.POLISHED_BLACKSTONE);
                        output.accept(Blocks.POLISHED_BLACKSTONE_STAIRS);
                        output.accept(Blocks.POLISHED_BLACKSTONE_SLAB);
                        output.accept(Blocks.POLISHED_BLACKSTONE_WALL);
                        output.accept(JNEBlocks.POLISHED_BLACKSTONE_FENCE.get());
                        output.accept(Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE);
                        output.accept(Blocks.POLISHED_BLACKSTONE_BUTTON);
                        output.accept(Blocks.POLISHED_BLACKSTONE_BRICKS);
                        output.accept(Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS);
                        output.accept(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB);
                        output.accept(Blocks.POLISHED_BLACKSTONE_BRICK_WALL);
                        output.accept(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS);
                        output.accept(JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICKS.get());
                        output.accept(JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICKS.get());

                        output.accept(JNEItems.RIFT_ARMOR_TRIM_SMITHING_TEMPLATE.get());
                        output.accept(JNEItems.SPIRIT_ARMOR_TRIM_SMITHING_TEMPLATE.get());

                        output.accept(JNEItems.WISP_SPAWN_EGG.get());
                        output.accept(JNEItems.APPARITION_SPAWN_EGG.get());
                        output.accept(JNEItems.VESSEL_SPAWN_EGG.get());
                        output.accept(JNEItems.ECTO_SLAB_SPAWN_EGG.get());
                        output.accept(JNEItems.BANSHEE_SPAWN_EGG.get());
                        output.accept(JNEItems.STAMPEDE_SPAWN_EGG.get());
//                        if (NetherExp.getConfig().gamemechanics.enable_unfinished_items) {
//                            output.accept(JNEItems.WARPHOPPER_SPAWN_EGG);
//                        }

                        output.accept(JNEBlocks.OSSIFIED_GARGOYLE_STATUE.get());
                        output.accept(JNEBlocks.TRAMPLE_GARGOYLE_STATUE.get());
                        output.accept(JNEBlocks.PHASE_GARGOYLE_STATUE.get());
                        output.accept(JNEBlocks.GHOUL_GARGOYLE_STATUE.get());
                        output.accept(JNEBlocks.WRETCHED_GARGOYLE_STATUE.get());
                        output.accept(JNEBlocks.TREACHEROUS_GARGOYLE_STATUE.get());
                        output.accept(JNEBlocks.CIRRIPEDIA_GARGOYLE_STATUE.get());
                        output.accept(JNEBlocks.OCCULT_GARGOYLE_STATUE.get());
                        output.accept(JNEBlocks.SEALED_GARGOYLE_STATUE.get());
                        output.accept(JNEBlocks.OBFUSCATED_GARGOYLE_STATUE.get());
                        output.accept(JNEItems.SEALED_POTTERY_SHERD.get());
                        output.accept(JNEItems.SPECTRE_POTTERY_SHERD.get());
                        output.accept(JNEItems.MARIONETTE_POTTERY_SHERD.get());
                        output.accept(JNEItems.ELDRITCH_POTTERY_SHERD.get());
                        output.accept(JNEItems.DECEPTION_POTTERY_SHERD.get());
                        output.accept(JNEItems.FIREARM_POTTERY_SHERD.get());
                        output.accept(JNEItems.BOTANICAL_POTTERY_SHERD.get());
                        addAntidotes(output);
                        addGrenadeAntidotes(output);

                    }).build());

    private static void addAntidotes(CreativeModeTab.Output output) {
        List<CompoundTag> list = Antidotes.ANTIDOTES;
        Set<ItemStack> set = ItemStackLinkedSet.createTypeAndTagSet();

        for (CompoundTag nbt : list) {
            ItemStack antidote = new ItemStack(JNEItems.ANTIDOTE.get());
            antidote.setTag(nbt);
            set.add(antidote);
        }

        output.acceptAll(set);
    }

    private static void addGrenadeAntidotes(CreativeModeTab.Output output) {
        List<CompoundTag> list = Antidotes.ANTIDOTES;
        Set<ItemStack> set = ItemStackLinkedSet.createTypeAndTagSet();

        for (CompoundTag nbt : list) {
            ItemStack grenade = new ItemStack(JNEItems.GRENADE_ANTIDOTE.get());
            grenade.setTag(nbt);
            set.add(grenade);
        }

        output.acceptAll(set);
    }

    public static void init() {
        CREATIVE_MODE_TABS.register();
    }
}
