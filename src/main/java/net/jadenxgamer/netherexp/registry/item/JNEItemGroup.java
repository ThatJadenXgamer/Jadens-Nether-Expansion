package net.jadenxgamer.netherexp.registry.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.fluid.JNEFluids;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class JNEItemGroup {

    @SuppressWarnings("unused")
    public static ItemGroup NETHEREXP = Registry.register(Registries.ITEM_GROUP, new Identifier(NetherExp.MOD_ID, "netherexp"),
            FabricItemGroup.builder().displayName(Text.literal("Jaden's Nether Expansion"))
                    .icon(() -> new ItemStack(JNEBlocks.PYRITE_NETHER_BRICKS)).entries((displayContext, entries) -> {
                        entries.add(JNEBlocks.SOUL_SLATE);
                        entries.add(JNEBlocks.PALE_SOUL_SLATE);
                        entries.add(JNEBlocks.JAGGED_SOUL_SLATE);
                        entries.add(JNEBlocks.SOUL_SLATE_STAIRS);
                        entries.add(JNEBlocks.SOUL_SLATE_SLAB);
                        entries.add(JNEBlocks.SOUL_SLATE_WALL);
                        entries.add(JNEBlocks.SOUL_SLATE_BRICKS);
                        entries.add(JNEBlocks.SOUL_SLATE_BRICK_STAIRS);
                        entries.add(JNEBlocks.SOUL_SLATE_BRICK_SLAB);
                        entries.add(JNEBlocks.SOUL_SLATE_BRICK_WALL);
                        entries.add(JNEBlocks.CRACKED_SOUL_SLATE_BRICKS);
                        entries.add(JNEBlocks.INDENTED_SOUL_SLATE_BRICKS);
                        entries.add(JNEBlocks.CHISELED_SOUL_SLATE_BRICKS);
                        entries.add(JNEBlocks.SOUL_SLATE_BRICK_PILLAR);

                        entries.add(JNEBlocks.FOSSIL_ORE);
                        entries.add(JNEBlocks.FOSSIL_FUEL_ORE);
                        entries.add(JNEBlocks.DIAMOND_FOSSIL_ORE);
                        entries.add(JNEItems.FOSSIL_FUEL);

                        entries.add(JNEItems.NECRO_SHARD);
                        entries.add(JNEItems.GUILLOTINE);
                        entries.add(JNEItems.SHOTGUN_FIST);

                        entries.add(JNEItems.WRAITHING_FLESH);
                        entries.add(JNEBlocks.SOUL_CANDLE);
                        entries.add(JNEBlocks.SOUL_GLASS);
                        entries.add(JNEBlocks.SOUL_SWIRLS);
                        if (NetherExp.checkModCompatCinderscapes()){
                            entries.add(JNEBlocks.SHALE_SWIRLS);
                        }
                        entries.add(JNEBlocks.SOUL_SOIL_LAYER);
                        entries.add(Blocks.SOUL_SOIL);
                        entries.add(JNEBlocks.SOUL_PATH);
                        entries.add(JNEBlocks.SUSPICIOUS_SOUL_SAND);
                        entries.add(Blocks.SOUL_SAND);
                        entries.add(JNEBlocks.ECTO_SOUL_SAND);
                        entries.add(JNEBlocks.SOUL_MAGMA_BLOCK);

                        entries.add(JNEBlocks.BLACK_ICE);
                        entries.add(JNEFluids.ECTOPLASM_BUCKET);

                        entries.add(Blocks.SOUL_TORCH);
                        entries.add(Blocks.SOUL_LANTERN);
                        entries.add(Blocks.SOUL_CAMPFIRE);
                        entries.add(JNEBlocks.SOUL_JACK_O_LANTERN);

                        entries.add(Items.PUMPKIN_SEEDS);
                        entries.add(JNEBlocks.SORROWSQUASH);
                        entries.add(JNEBlocks.CARVED_SORROWSQUASH);
                        entries.add(JNEBlocks.GHOUL_O_LANTERN);
                        entries.add(JNEBlocks.SOUL_GHOUL_O_LANTERN);

                        entries.add(Blocks.NETHERRACK);
                        entries.add(JNEBlocks.SMOOTH_NETHERRACK);
                        entries.add(JNEBlocks.SMOOTH_NETHERRACK_STAIRS);
                        entries.add(JNEBlocks.SMOOTH_NETHERRACK_SLAB);
                        entries.add(JNEBlocks.SMOOTH_NETHERRACK_WALL);

                        entries.add(JNEBlocks.NETHERRACK_BRICKS);
                        entries.add(JNEBlocks.NETHERRACK_BRICK_STAIRS);
                        entries.add(JNEBlocks.NETHERRACK_BRICK_SLAB);
                        entries.add(JNEBlocks.NETHERRACK_BRICK_WALL);
                        entries.add(JNEBlocks.NETHERRACK_TILES);
                        entries.add(JNEBlocks.NETHERRACK_PILLAR);

                        entries.add(JNEItems.WHITE_ASH_POWDER);
                        entries.add(JNEBlocks.WHITE_ASH_BLOCK);
                        entries.add(JNEBlocks.WHITE_ASH);
                        entries.add(Blocks.BASALT);
                        entries.add(JNEBlocks.BASALT_SLAB);
                        entries.add(JNEBlocks.BASALT_STAIRS);
                        entries.add(JNEBlocks.BASALT_WALL);
                        entries.add(Blocks.POLISHED_BASALT);
                        entries.add(JNEBlocks.POLISHED_BASALT_SLAB);
                        entries.add(JNEBlocks.POLISHED_BASALT_STAIRS);
                        entries.add(JNEBlocks.POLISHED_BASALT_WALL);
                        entries.add(JNEBlocks.POLISHED_BASALT_BRICKS);
                        entries.add(JNEBlocks.POLISHED_BASALT_BRICK_STAIRS);
                        entries.add(JNEBlocks.POLISHED_BASALT_BRICK_SLAB);
                        entries.add(JNEBlocks.POLISHED_BASALT_BRICK_WALL);

                        entries.add(Items.NETHERITE_INGOT);
                        entries.add(Blocks.NETHERITE_BLOCK);
                        entries.add(JNEItems.STRIDITE);
                        entries.add(JNEItems.NETHERITE_PLATING);
                        entries.add(JNEBlocks.NETHERITE_PLATED_BLOCK);
                        entries.add(JNEBlocks.NETHERITE_GRATE);
                        entries.add(JNEBlocks.CUT_NETHERITE_BLOCK);
                        entries.add(JNEBlocks.CUT_NETHERITE_STAIRS);
                        entries.add(JNEBlocks.CUT_NETHERITE_SLAB);
                        entries.add(JNEBlocks.CUT_NETHERITE_PILLAR);

                        entries.add(Items.NETHERITE_SCRAP);
                        entries.add(Blocks.ANCIENT_DEBRIS);
                        entries.add(JNEBlocks.RUSTY_NETHERITE_PLATED_BLOCK);
                        entries.add(JNEBlocks.RUSTY_NETHERITE_GRATE);
                        entries.add(JNEBlocks.RUSTY_CUT_NETHERITE_BLOCK);
                        entries.add(JNEBlocks.RUSTY_CUT_NETHERITE_STAIRS);
                        entries.add(JNEBlocks.RUSTY_CUT_NETHERITE_SLAB);
                        entries.add(JNEBlocks.RUSTY_CUT_NETHERITE_PILLAR);

                        if (NetherExp.getConfig().gamemechanics.enable_unfinished_items) {
                            entries.add(JNEBlocks.ENIGMA_CROWN);
                            entries.add(JNEBlocks.ENIGMA_SHELF);
                            entries.add(JNEBlocks.ENIGMA_FLESH);
                            entries.add(JNEBlocks.STRANGE_ENIGMA_FLESH);
                        }

                        entries.add(JNEBlocks.CLARET_STEM);
                        entries.add(JNEBlocks.CLARET_HYPHAE);
                        entries.add(JNEBlocks.STRIPPED_CLARET_STEM);
                        entries.add(JNEBlocks.STRIPPED_CLARET_HYPHAE);
                        entries.add(JNEBlocks.CLARET_PLANKS);
                        entries.add(JNEBlocks.CLARET_STAIRS);
                        entries.add(JNEBlocks.CLARET_SLAB);
                        entries.add(JNEBlocks.CLARET_FENCE);
                        entries.add(JNEBlocks.CLARET_FENCE_GATE);
                        entries.add(JNEBlocks.CLARET_DOOR);
                        entries.add(JNEBlocks.CLARET_TRAPDOOR);
                        entries.add(JNEBlocks.CLARET_PRESSURE_PLATE);
                        entries.add(JNEBlocks.CLARET_BUTTON);
                        entries.add(JNEItems.CLARET_SIGN_ITEM);
                        entries.add(JNEItems.CLARET_HANGING_SIGN_ITEM);

                        entries.add(JNEItems.MAGMA_CUBE_BUCKET);
                        entries.add(Items.MAGMA_CREAM);
                        entries.add(JNEBlocks.MAGMA_CREAM_BLOCK);

                        entries.add(JNEBlocks.SMOKESTALK);
                        entries.add(JNEBlocks.SMOKESTALK_BLOCK);
                        entries.add(JNEBlocks.STRIPPED_SMOKESTALK_BLOCK);
                        entries.add(JNEBlocks.SMOKESTALK_PLANKS);
                        entries.add(JNEBlocks.SMOKESTALK_STAIRS);
                        entries.add(JNEBlocks.SMOKESTALK_SLAB);
                        entries.add(JNEBlocks.SMOKESTALK_FENCE);
                        entries.add(JNEBlocks.SMOKESTALK_FENCE_GATE);
                        entries.add(JNEBlocks.SMOKESTALK_DOOR);
                        entries.add(JNEBlocks.SMOKESTALK_TRAPDOOR);
                        entries.add(JNEBlocks.SMOKESTALK_PRESSURE_PLATE);
                        entries.add(JNEBlocks.SMOKESTALK_BUTTON);
                        entries.add(JNEItems.SMOKESTALK_SIGN_ITEM);
                        entries.add(JNEItems.SMOKESTALK_HANGING_SIGN_ITEM);
                        if (NetherExp.getConfig().gamemechanics.enable_unfinished_items) {
                            entries.add(JNEBlocks.IGNEOUS_REEDS);
                            entries.add(JNEBlocks.IGNEOUS_VINES);
                            entries.add(JNEBlocks.EXPLOSIVE_SCORIA);
                            entries.add(JNEItems.IRON_SCRAP);
                        }

                        entries.add(Items.QUARTZ);
                        entries.add(JNEBlocks.QUARTZ_CRYSTAL);
                        entries.add(JNEBlocks.QUARTZ_CRYSTAL_BLOCK);
                        entries.add(Blocks.QUARTZ_BLOCK);
                        entries.add(Blocks.QUARTZ_STAIRS);
                        entries.add(Blocks.QUARTZ_SLAB);
                        entries.add(Blocks.CHISELED_QUARTZ_BLOCK);
                        entries.add(Blocks.QUARTZ_BRICKS);
                        entries.add(JNEBlocks.CRACKED_QUARTZ_BRICKS);
                        entries.add(JNEBlocks.QUARTZ_BRICK_WALL);
                        entries.add(JNEBlocks.CHISELED_QUARTZ_PILLAR);
                        entries.add(Blocks.QUARTZ_PILLAR);
                        entries.add(Blocks.SMOOTH_QUARTZ);
                        entries.add(Blocks.SMOOTH_QUARTZ_STAIRS);
                        entries.add(Blocks.SMOOTH_QUARTZ_SLAB);
                        entries.add(JNEBlocks.SMOOTH_QUARTZ_WALL);

                        entries.add(JNEBlocks.SILICA_SAND);
                        entries.add(JNEBlocks.SILICA_SANDSTONE);
                        entries.add(JNEBlocks.SILICA_SANDSTONE_STAIRS);
                        entries.add(JNEBlocks.SILICA_SANDSTONE_SLAB);
                        entries.add(JNEBlocks.SILICA_SANDSTONE_WALL);
                        entries.add(JNEBlocks.CUT_SILICA_SANDSTONE);
                        entries.add(JNEBlocks.CUT_SILICA_SANDSTONE_SLAB);
                        entries.add(JNEBlocks.CHISELED_SILICA_SANDSTONE);
                        entries.add(JNEBlocks.SMOOTH_SILICA_SANDSTONE);
                        entries.add(JNEBlocks.SMOOTH_SILICA_SANDSTONE_STAIRS);
                        entries.add(JNEBlocks.SMOOTH_SILICA_SANDSTONE_SLAB);

                        entries.add(Blocks.NETHER_BRICKS);
                        entries.add(Blocks.NETHER_BRICK_STAIRS);
                        entries.add(Blocks.NETHER_BRICK_SLAB);
                        entries.add(Blocks.NETHER_BRICK_WALL);
                        entries.add(Blocks.NETHER_BRICK_FENCE);
                        entries.add(JNEBlocks.NETHER_BRICK_PILLAR);
                        entries.add(Blocks.CRACKED_NETHER_BRICKS);
                        entries.add(Blocks.CHISELED_NETHER_BRICKS);

                        entries.add(JNEBlocks.RED_MIXED_NETHER_BRICKS);
                        entries.add(Blocks.RED_NETHER_BRICKS);
                        entries.add(Blocks.RED_NETHER_BRICK_STAIRS);
                        entries.add(Blocks.RED_NETHER_BRICK_SLAB);
                        entries.add(Blocks.RED_NETHER_BRICK_WALL);

                        entries.add(JNEBlocks.BLUE_MIXED_NETHER_BRICKS);
                        entries.add(JNEBlocks.BLUE_NETHER_BRICKS);
                        entries.add(JNEBlocks.BLUE_NETHER_BRICK_STAIRS);
                        entries.add(JNEBlocks.BLUE_NETHER_BRICK_SLAB);
                        entries.add(JNEBlocks.BLUE_NETHER_BRICK_WALL);

                        if (NetherExp.checkModCompatCinderscapes()) {
                            entries.add(JNEBlocks.VIOLET_MIXED_NETHER_BRICKS);
                            entries.add(JNEBlocks.VIOLET_NETHER_BRICKS);
                            entries.add(JNEBlocks.VIOLET_NETHER_BRICK_STAIRS);
                            entries.add(JNEBlocks.VIOLET_NETHER_BRICK_SLAB);
                            entries.add(JNEBlocks.VIOLET_NETHER_BRICK_WALL);
                        }

                        if (NetherExp.checkModCompatGardensOfTheDead()) {
                            entries.add(JNEBlocks.YELLOW_MIXED_NETHER_BRICKS);
                            entries.add(JNEBlocks.YELLOW_NETHER_BRICKS);
                            entries.add(JNEBlocks.YELLOW_NETHER_BRICK_STAIRS);
                            entries.add(JNEBlocks.YELLOW_NETHER_BRICK_SLAB);
                            entries.add(JNEBlocks.YELLOW_NETHER_BRICK_WALL);
                        }

                        if (NetherExp.getConfig().gamemechanics.enable_unfinished_items) {
                            entries.add(JNEItems.PYRITE_INGOT);
                            entries.add(JNEBlocks.PYRITE_NETHER_BRICKS);
                            entries.add(JNEBlocks.PYRITE_BLOCK);
                            entries.add(JNEBlocks.PYRITE_STAIRS);
                            entries.add(JNEBlocks.PYRITE_SLAB);
                            entries.add(JNEBlocks.PYRITE_WALL);
                            entries.add(JNEBlocks.PYRITE_LANTERN);
                            entries.add(JNEBlocks.SOUL_PYRITE_LANTERN);
                            entries.add(JNEBlocks.PYRITE_CHAIN);
                            entries.add(JNEBlocks.MEDIUM_WEIGHTED_PRESSURE_PLATE);
                            entries.add(JNEBlocks.PYRITE_BUTTON);
                        }

                        entries.add(JNEItems.LIGHTSPORES);
                        entries.add(Blocks.SHROOMLIGHT);
                        entries.add(JNEItems.NIGHTSPORES);
                        entries.add(JNEBlocks.SHROOMNIGHT);
                        if (NetherExp.checkModCompatCinderscapes()) {
                            entries.add(JNEItems.BLIGHTSPORES);
                            entries.add(JNEBlocks.SHROOMBLIGHT);
                        }
                        if (NetherExp.checkModCompatGardensOfTheDead()) {
                            entries.add(JNEItems.FRIGHTSPORES);
                            entries.add(JNEBlocks.SHROOMFRIGHT);
                        }

                        entries.add(JNEItems.GLOWCHEESE);
                        entries.add(JNEItems.NETHER_PIZZA);
                        entries.add(JNEItems.NETHER_PIZZA_SLICE);

                        entries.add(Blocks.CRIMSON_NYLIUM);
                        entries.add(JNEBlocks.CRIMSON_NYLIUM_PATH);
                        entries.add(Items.NETHER_WART);
                        entries.add(Blocks.NETHER_WART_BLOCK);
                        entries.add(JNEBlocks.NETHER_WART_BEARD);
                        entries.add(JNEBlocks.RED_SCALE_FUNGUS);
                        entries.add(Blocks.WEEPING_VINES);
                        entries.add(JNEBlocks.WEEPING_IVY);
                        entries.add(JNEBlocks.CRIMSON_SPROUTS);

                        entries.add(Blocks.WARPED_NYLIUM);
                        entries.add(JNEBlocks.WARPED_NYLIUM_PATH);
                        entries.add(JNEItems.WARPED_WART);
                        entries.add(Blocks.WARPED_WART_BLOCK);
                        entries.add(JNEBlocks.WARPED_WART_BEARD);
                        entries.add(JNEBlocks.BLUE_SCALE_FUNGUS);
                        entries.add(Blocks.TWISTING_VINES);
                        entries.add(JNEBlocks.TWISTING_IVY);
                        entries.add(Blocks.NETHER_SPROUTS);

                        if (NetherExp.checkModCompatCinderscapes()) {
                            entries.add(JNEBlocks.UMBRAL_NYLIUM_PATH);
                            entries.add(JNEBlocks.UMBRAL_WART_BEARD);
                            entries.add(JNEBlocks.VIOLET_SCALE_FUNGUS);
                            entries.add(JNEBlocks.TWILIGHT_VINES);
                            entries.add(JNEBlocks.TWILIGHT_IVY);
                        }

                        entries.add(JNEItems.WISP_BOTTLE);


                        entries.add(JNEItems.FOGGY_ESSENCE);
                        entries.add(JNEItems.MIST_CHARGE);

                        entries.add(JNEItems.HOGHAM);
                        entries.add(JNEItems.COOKED_HOGHAM);

                        if (NetherExp.getConfig().gamemechanics.enable_unfinished_items) {
                            entries.add(JNEItems.WARPHOPPER_FUR);
                            entries.add(JNEItems.MUSIC_DISC_CRICKET);
                        }

                        entries.add(JNEBlocks.CRIMSON_SPORESHROOM);
                        entries.add(JNEBlocks.WARPED_SPORESHROOM);
                        if (NetherExp.checkModCompatCinderscapes()) {
                            entries.add(JNEBlocks.UMBRAL_SPORESHROOM);
                        }
                        if (NetherExp.checkModCompatGardensOfTheDead()) {
                            entries.add(JNEBlocks.SOULBLIGHT_SPORESHROOM);
                        }
                        entries.add(JNEBlocks.SOULED_GEYSER);
                        entries.add(JNEBlocks.BASALTIC_GEYSER);
                        if (NetherExp.checkModCompatCinderscapes()) {
                            entries.add(JNEBlocks.BLACKSTONIC_GEYSER);
                            entries.add(JNEBlocks.ASHEN_GEYSER);
                        }

                        entries.add(JNEItems.SKULL_ON_A_STICK);
                        entries.add(Items.SKELETON_SKULL);
                        entries.add(JNEBlocks.SKELETON_SKULL_CANDLE);
                        entries.add(JNEBlocks.SOUL_SKELETON_SKULL_CANDLE);

                        entries.add(Items.BONE);
                        entries.add(JNEBlocks.BONE_ROD);
                        entries.add(JNEBlocks.BONE_FENCE);
                        entries.add(Blocks.BONE_BLOCK);
                        entries.add(JNEBlocks.STACKED_BONES);
                        entries.add(JNEBlocks.STACKED_BONE_STAIRS);
                        entries.add(JNEBlocks.STACKED_BONE_SLAB);
                        entries.add(JNEBlocks.SKULL_BLOCK);
                        entries.add(JNEBlocks.BURNING_SKULL_BLOCK);
                        entries.add(JNEBlocks.SOUL_BURNING_SKULL_BLOCK);

                        entries.add(JNEBlocks.WITHER_BONE_BLOCK);
                        entries.add(JNEBlocks.STACKED_WITHER_BONES);
                        entries.add(JNEBlocks.STACKED_WITHER_BONE_STAIRS);
                        entries.add(JNEBlocks.STACKED_WITHER_BONE_SLAB);
                        entries.add(JNEBlocks.WITHER_SKULL_BLOCK);
                        entries.add(JNEBlocks.BURNING_WITHER_SKULL_BLOCK);
                        entries.add(JNEBlocks.SOUL_BURNING_WITHER_SKULL_BLOCK);

                        entries.add(Blocks.GILDED_BLACKSTONE);
                        entries.add(Blocks.BLACKSTONE);
                        entries.add(Blocks.BLACKSTONE_STAIRS);
                        entries.add(Blocks.BLACKSTONE_SLAB);
                        entries.add(Blocks.BLACKSTONE_WALL);
                        entries.add(Blocks.CHISELED_POLISHED_BLACKSTONE);
                        entries.add(JNEBlocks.POLISHED_BLACKSTONE_PILLAR);
                        entries.add(Blocks.POLISHED_BLACKSTONE);
                        entries.add(Blocks.POLISHED_BLACKSTONE_STAIRS);
                        entries.add(Blocks.POLISHED_BLACKSTONE_SLAB);
                        entries.add(Blocks.POLISHED_BLACKSTONE_WALL);
                        entries.add(JNEBlocks.POLISHED_BLACKSTONE_FENCE);
                        entries.add(Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE);
                        entries.add(Blocks.POLISHED_BLACKSTONE_BUTTON);
                        entries.add(Blocks.POLISHED_BLACKSTONE_BRICKS);
                        entries.add(Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS);
                        entries.add(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB);
                        entries.add(Blocks.POLISHED_BLACKSTONE_BRICK_WALL);
                        entries.add(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS);
                        entries.add(JNEBlocks.WEEPING_POLISHED_BLACKSTONE_BRICKS);
                        entries.add(JNEBlocks.TWISTING_POLISHED_BLACKSTONE_BRICKS);

                        entries.add(JNEItems.RIFT_ARMOR_TRIM_SMITHING_TEMPLATE);

                        entries.add(JNEItems.WISP_SPAWN_EGG);
                        entries.add(JNEItems.APPARITION_SPAWN_EGG);
                        entries.add(JNEItems.STAMPEDE_SPAWN_EGG);
                        entries.add(JNEItems.GRASP_SPAWN_EGG);
                        if (NetherExp.getConfig().gamemechanics.enable_unfinished_items) {
                            entries.add(JNEItems.WARPHOPPER_SPAWN_EGG);
                        }
                    }).build());

    public static void registerItemGroup() {
    }
}
