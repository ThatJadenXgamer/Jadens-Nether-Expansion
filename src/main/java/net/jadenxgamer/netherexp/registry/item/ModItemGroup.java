package net.jadenxgamer.netherexp.registry.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.ModBlocks;
import net.jadenxgamer.netherexp.registry.fluid.ModFluids;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {

    @SuppressWarnings("unused")
    public static ItemGroup NETHEREXP = Registry.register(Registries.ITEM_GROUP, new Identifier(NetherExp.MOD_ID, "netherexp"),
            FabricItemGroup.builder().displayName(Text.literal("Jaden's Nether Expansion"))
                    .icon(() -> new ItemStack(ModBlocks.PYRITE_NETHER_BRICKS)).entries((displayContext, entries) -> {
                        entries.add(ModBlocks.SOUL_SLATE);
                        entries.add(ModBlocks.PALE_SOUL_SLATE);
                        entries.add(ModBlocks.JAGGED_SOUL_SLATE);
                        entries.add(ModBlocks.SOUL_SLATE_STAIRS);
                        entries.add(ModBlocks.SOUL_SLATE_SLAB);
                        entries.add(ModBlocks.SOUL_SLATE_WALL);
                        entries.add(ModBlocks.SOUL_SLATE_BRICKS);
                        entries.add(ModBlocks.SOUL_SLATE_BRICK_STAIRS);
                        entries.add(ModBlocks.SOUL_SLATE_BRICK_SLAB);
                        entries.add(ModBlocks.SOUL_SLATE_BRICK_WALL);
                        entries.add(ModBlocks.CRACKED_SOUL_SLATE_BRICKS);
                        entries.add(ModBlocks.INDENTED_SOUL_SLATE_BRICKS);
                        entries.add(ModBlocks.CHISELED_SOUL_SLATE_BRICKS);
                        entries.add(ModBlocks.SOUL_SLATE_BRICK_PILLAR);

                        entries.add(ModBlocks.FOSSIL_ORE);
                        entries.add(ModBlocks.FOSSIL_FUEL_ORE);
                        entries.add(ModBlocks.OPALIZED_FOSSIL_ORE);
                        entries.add(ModItems.FOSSIL_FUEL);

                        entries.add(ModItems.NECRO_SHARD);
                        entries.add(ModItems.GUILLOTINE);

                        entries.add(ModItems.WRAITHING_FLESH);
                        entries.add(ModBlocks.SOUL_CANDLE);
                        entries.add(ModBlocks.SOUL_GLASS);
                        entries.add(ModBlocks.SOUL_SWIRLS);
                        entries.add(ModBlocks.SOUL_SOIL_LAYER);
                        entries.add(Blocks.SOUL_SOIL);
                        entries.add(ModBlocks.SOUL_PATH);
                        entries.add(Blocks.SOUL_SAND);
                        entries.add(ModBlocks.ECTO_SOUL_SAND);
                        entries.add(ModBlocks.SOUL_MAGMA_BLOCK);

                        entries.add(ModBlocks.BLACK_ICE);
                        entries.add(ModFluids.ECTOPLASM_BUCKET);

                        entries.add(Blocks.SOUL_TORCH);
                        entries.add(Blocks.SOUL_LANTERN);
                        entries.add(Blocks.SOUL_CAMPFIRE);
                        entries.add(ModBlocks.SOUL_JACK_O_LANTERN);

                        entries.add(Items.PUMPKIN_SEEDS);
                        entries.add(ModBlocks.SORROWSQUASH);
                        entries.add(ModBlocks.CARVED_SORROWSQUASH);
                        entries.add(ModBlocks.GHOUL_O_LANTERN);
                        entries.add(ModBlocks.SOUL_GHOUL_O_LANTERN);

                        entries.add(Blocks.NETHERRACK);
                        entries.add(ModBlocks.SMOOTH_NETHERRACK);
                        entries.add(ModBlocks.SMOOTH_NETHERRACK_STAIRS);
                        entries.add(ModBlocks.SMOOTH_NETHERRACK_SLAB);
                        entries.add(ModBlocks.SMOOTH_NETHERRACK_WALL);

                        entries.add(ModBlocks.NETHERRACK_BRICKS);
                        entries.add(ModBlocks.NETHERRACK_BRICK_STAIRS);
                        entries.add(ModBlocks.NETHERRACK_BRICK_SLAB);
                        entries.add(ModBlocks.NETHERRACK_BRICK_WALL);
                        entries.add(ModBlocks.NETHERRACK_TILES);
                        entries.add(ModBlocks.NETHERRACK_PILLAR);

                        entries.add(ModItems.WHITE_ASH_POWDER);
                        entries.add(ModBlocks.WHITE_ASH_BLOCK);
                        entries.add(ModBlocks.WHITE_ASH);
                        entries.add(ModBlocks.BASALT_SLAB);
                        entries.add(Blocks.POLISHED_BASALT);
                        entries.add(ModBlocks.POLISHED_BASALT_BRICKS);
                        entries.add(ModBlocks.POLISHED_BASALT_BRICK_STAIRS);
                        entries.add(ModBlocks.POLISHED_BASALT_BRICK_SLAB);

                        entries.add(ModBlocks.ENIGMA_CROWN);
                        entries.add(ModBlocks.ENIGMA_SHELF);
                        entries.add(ModBlocks.ENIGMA_FLESH);
                        entries.add(ModBlocks.STRANGE_ENIGMA_FLESH);

                        entries.add(ModBlocks.CLARET_STEM);
                        entries.add(ModBlocks.CLARET_HYPHAE);
                        entries.add(ModBlocks.STRIPPED_CLARET_STEM);
                        entries.add(ModBlocks.STRIPPED_CLARET_HYPHAE);
                        entries.add(ModBlocks.CLARET_PLANKS);
                        entries.add(ModBlocks.CLARET_STAIRS);
                        entries.add(ModBlocks.CLARET_SLAB);
                        entries.add(ModBlocks.CLARET_FENCE);
                        entries.add(ModBlocks.CLARET_FENCE_GATE);
                        entries.add(ModBlocks.CLARET_DOOR);
                        entries.add(ModBlocks.CLARET_TRAPDOOR);
                        entries.add(ModBlocks.CLARET_PRESSURE_PLATE);
                        entries.add(ModBlocks.CLARET_BUTTON);
                        entries.add(ModItems.CLARET_SIGN_ITEM);
                        entries.add(ModItems.CLARET_HANGING_SIGN_ITEM);

                        entries.add(Items.MAGMA_CREAM);
                        entries.add(ModBlocks.MAGMA_CREAM_BLOCK);

                        entries.add(ModBlocks.SMOKESTALK);
                        entries.add(ModBlocks.SMOKESTALK_BLOCK);
                        entries.add(ModBlocks.STRIPPED_SMOKESTALK_BLOCK);
                        entries.add(ModBlocks.SMOKESTALK_PLANKS);
                        entries.add(ModBlocks.SMOKESTALK_STAIRS);
                        entries.add(ModBlocks.SMOKESTALK_SLAB);
                        entries.add(ModBlocks.SMOKESTALK_FENCE);
                        entries.add(ModBlocks.SMOKESTALK_FENCE_GATE);
                        entries.add(ModBlocks.SMOKESTALK_DOOR);
                        entries.add(ModBlocks.SMOKESTALK_TRAPDOOR);
                        entries.add(ModBlocks.SMOKESTALK_PRESSURE_PLATE);
                        entries.add(ModBlocks.SMOKESTALK_BUTTON);
                        entries.add(ModItems.SMOKESTALK_SIGN_ITEM);
                        entries.add(ModItems.SMOKESTALK_HANGING_SIGN_ITEM);
                        entries.add(ModBlocks.IGNEOUS_REEDS);
                        entries.add(ModBlocks.IGNEOUS_VINES);
                        entries.add(ModBlocks.EXPLOSIVE_SCORIA);
                        entries.add(ModItems.IRON_SCRAP);

                        entries.add(Items.QUARTZ);
                        entries.add(ModBlocks.QUARTZ_CRYSTAL);
                        entries.add(ModBlocks.QUARTZ_CRYSTAL_BLOCK);
                        entries.add(Blocks.QUARTZ_BLOCK);
                        entries.add(Blocks.QUARTZ_STAIRS);
                        entries.add(Blocks.QUARTZ_SLAB);
                        entries.add(Blocks.CHISELED_QUARTZ_BLOCK);
                        entries.add(Blocks.QUARTZ_BRICKS);
                        entries.add(ModBlocks.CRACKED_QUARTZ_BRICKS);
                        entries.add(ModBlocks.QUARTZ_BRICK_WALL);
                        entries.add(ModBlocks.CHISELED_QUARTZ_PILLAR);
                        entries.add(Blocks.QUARTZ_PILLAR);
                        entries.add(Blocks.SMOOTH_QUARTZ);
                        entries.add(Blocks.SMOOTH_QUARTZ_STAIRS);
                        entries.add(Blocks.SMOOTH_QUARTZ_SLAB);
                        entries.add(ModBlocks.SMOOTH_QUARTZ_WALL);

                        entries.add(ModBlocks.SILICA_SAND);
                        entries.add(ModBlocks.SILICA_SANDSTONE);
                        entries.add(ModBlocks.SILICA_SANDSTONE_STAIRS);
                        entries.add(ModBlocks.SILICA_SANDSTONE_SLAB);
                        entries.add(ModBlocks.SILICA_SANDSTONE_WALL);
                        entries.add(ModBlocks.CUT_SILICA_SANDSTONE);
                        entries.add(ModBlocks.CUT_SILICA_SANDSTONE_SLAB);
                        entries.add(ModBlocks.CHISELED_SILICA_SANDSTONE);
                        entries.add(ModBlocks.SMOOTH_SILICA_SANDSTONE);
                        entries.add(ModBlocks.SMOOTH_SILICA_SANDSTONE_STAIRS);
                        entries.add(ModBlocks.SMOOTH_SILICA_SANDSTONE_SLAB);

                        entries.add(Blocks.NETHER_BRICKS);
                        entries.add(Blocks.NETHER_BRICK_STAIRS);
                        entries.add(Blocks.NETHER_BRICK_SLAB);
                        entries.add(Blocks.NETHER_BRICK_WALL);
                        entries.add(Blocks.NETHER_BRICK_FENCE);
                        entries.add(ModBlocks.NETHER_BRICK_PILLAR);
                        entries.add(Blocks.CRACKED_NETHER_BRICKS);
                        entries.add(Blocks.CHISELED_NETHER_BRICKS);

                        entries.add(ModBlocks.RED_MIXED_NETHER_BRICKS);
                        entries.add(Blocks.RED_NETHER_BRICKS);
                        entries.add(Blocks.RED_NETHER_BRICK_STAIRS);
                        entries.add(Blocks.RED_NETHER_BRICK_SLAB);
                        entries.add(Blocks.RED_NETHER_BRICK_WALL);

                        entries.add(ModBlocks.BLUE_MIXED_NETHER_BRICKS);
                        entries.add(ModBlocks.BLUE_NETHER_BRICKS);
                        entries.add(ModBlocks.BLUE_NETHER_BRICK_STAIRS);
                        entries.add(ModBlocks.BLUE_NETHER_BRICK_SLAB);
                        entries.add(ModBlocks.BLUE_NETHER_BRICK_WALL);

                        entries.add(ModItems.PYRITE_INGOT);
                        entries.add(ModBlocks.PYRITE_NETHER_BRICKS);
                        entries.add(ModBlocks.PYRITE_BLOCK);
                        entries.add(ModBlocks.PYRITE_STAIRS);
                        entries.add(ModBlocks.PYRITE_SLAB);
                        entries.add(ModBlocks.PYRITE_WALL);
                        entries.add(ModBlocks.PYRITE_LANTERN);
                        entries.add(ModBlocks.SOUL_PYRITE_LANTERN);
                        entries.add(ModBlocks.PYRITE_CHAIN);
                        entries.add(ModBlocks.MEDIUM_WEIGHTED_PRESSURE_PLATE);
                        entries.add(ModBlocks.PYRITE_BUTTON);

                        entries.add(ModItems.LIGHTSPORES);
                        entries.add(Blocks.SHROOMLIGHT);
                        entries.add(ModItems.NIGHTSPORES);
                        entries.add(ModBlocks.SHROOMNIGHT);
                        if (NetherExp.checkModCompatCinderscapes()) {
                            entries.add(ModItems.BLIGHTSPORES);
                            entries.add(ModBlocks.SHROOMBLIGHT);
                        }

                        entries.add(ModItems.GLOWCHEESE);
                        entries.add(ModItems.NETHER_PIZZA);
                        entries.add(ModItems.NETHER_PIZZA_SLICE);

                        entries.add(Items.NETHER_WART);
                        entries.add(Blocks.NETHER_WART_BLOCK);
                        entries.add(ModBlocks.NETHER_WART_BEARD);
                        entries.add(ModBlocks.RED_SCALE_FUNGUS);

                        entries.add(ModItems.WARPED_WART);
                        entries.add(Blocks.WARPED_WART_BLOCK);
                        entries.add(ModBlocks.WARPED_WART_BEARD);
                        entries.add(ModBlocks.BLUE_SCALE_FUNGUS);

                        if (NetherExp.checkModCompatCinderscapes()) {
                            entries.add(ModBlocks.UMBRAL_WART_BEARD);
                            entries.add(ModBlocks.VIOLET_SCALE_FUNGUS);
                        }

                        entries.add(Blocks.WEEPING_VINES);
                        entries.add(ModBlocks.WEEPING_IVY);
                        entries.add(Blocks.TWISTING_VINES);
                        entries.add(ModBlocks.TWISTING_IVY);
                        if (NetherExp.checkModCompatCinderscapes()) {
                            entries.add(ModBlocks.TWILIGHT_VINES);
                            entries.add(ModBlocks.TWILIGHT_IVY);
                        }

                        entries.add(Blocks.NETHER_SPROUTS);
                        entries.add(ModBlocks.CRIMSON_SPROUTS);

                        entries.add(Blocks.CRIMSON_NYLIUM);
                        entries.add(ModBlocks.CRIMSON_NYLIUM_PATH);
                        entries.add(Blocks.WARPED_NYLIUM);
                        entries.add(ModBlocks.WARPED_NYLIUM_PATH);
                        if (NetherExp.checkModCompatCinderscapes()) {
                            entries.add(ModBlocks.UMBRAL_NYLIUM_PATH);
                        }

                        entries.add(ModItems.WISP_BOTTLE);
                        entries.add(ModItems.WISP_SPAWN_EGG);
                        entries.add(ModItems.APPARITION_SPAWN_EGG);

                        entries.add(ModItems.FOGGY_ESSENCE);

                        entries.add(ModItems.HOGHAM);
                        entries.add(ModItems.COOKED_HOGHAM);

                        entries.add(ModItems.WARPHOPPER_FUR);
                        entries.add(ModItems.MUSIC_DISC_CRICKET);
                        entries.add(ModItems.WARPHOPPER_SPAWN_EGG);

                        entries.add(ModBlocks.CRIMSON_SPORESHROOM);
                        entries.add(ModBlocks.WARPED_SPORESHROOM);
                        if (NetherExp.checkModCompatCinderscapes()) {
                            entries.add(ModBlocks.UMBRAL_SPORESHROOM);
                        }
                        entries.add(ModBlocks.SOULED_GEYSER);
                        entries.add(ModBlocks.BASALTIC_GEYSER);
                        if (NetherExp.checkModCompatCinderscapes()) {
                            entries.add(ModBlocks.BLACKSTONIC_GEYSER);
                            entries.add(ModBlocks.ASHEN_GEYSER);
                        }

                        entries.add(Items.SKELETON_SKULL);
                        entries.add(ModBlocks.SKELETON_SKULL_CANDLE);
                        entries.add(ModBlocks.SOUL_SKELETON_SKULL_CANDLE);

                        entries.add(Items.BONE);
                        entries.add(ModBlocks.BONE_ROD);
                        entries.add(ModBlocks.BONE_FENCE);
                        entries.add(Blocks.BONE_BLOCK);
                        entries.add(ModBlocks.STACKED_BONES);
                        entries.add(ModBlocks.STACKED_BONE_STAIRS);
                        entries.add(ModBlocks.STACKED_BONE_SLAB);
                        entries.add(ModBlocks.SKULL_BLOCK);
                        entries.add(ModBlocks.BURNING_SKULL_BLOCK);
                        entries.add(ModBlocks.SOUL_BURNING_SKULL_BLOCK);

                        entries.add(ModBlocks.WITHER_BONE_BLOCK);
                        entries.add(ModBlocks.STACKED_WITHER_BONES);
                        entries.add(ModBlocks.STACKED_WITHER_BONE_STAIRS);
                        entries.add(ModBlocks.STACKED_WITHER_BONE_SLAB);
                        entries.add(ModBlocks.WITHER_SKULL_BLOCK);
                        entries.add(ModBlocks.BURNING_WITHER_SKULL_BLOCK);
                        entries.add(ModBlocks.SOUL_BURNING_WITHER_SKULL_BLOCK);

                        entries.add(Blocks.GILDED_BLACKSTONE);
                        entries.add(Blocks.BLACKSTONE);
                        entries.add(Blocks.BLACKSTONE_STAIRS);
                        entries.add(Blocks.BLACKSTONE_SLAB);
                        entries.add(Blocks.BLACKSTONE_WALL);
                        entries.add(Blocks.CHISELED_POLISHED_BLACKSTONE);
                        entries.add(ModBlocks.POLISHED_BLACKSTONE_PILLAR);
                        entries.add(Blocks.POLISHED_BLACKSTONE);
                        entries.add(Blocks.POLISHED_BLACKSTONE_STAIRS);
                        entries.add(Blocks.POLISHED_BLACKSTONE_SLAB);
                        entries.add(Blocks.POLISHED_BLACKSTONE_WALL);
                        entries.add(ModBlocks.POLISHED_BLACKSTONE_FENCE);
                        entries.add(Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE);
                        entries.add(Blocks.POLISHED_BLACKSTONE_BUTTON);
                        entries.add(Blocks.POLISHED_BLACKSTONE_BRICKS);
                        entries.add(Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS);
                        entries.add(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB);
                        entries.add(Blocks.POLISHED_BLACKSTONE_BRICK_WALL);
                        entries.add(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS);
                        entries.add(ModBlocks.WEEPING_POLISHED_BLACKSTONE_BRICKS);
                        entries.add(ModBlocks.TWISTING_POLISHED_BLACKSTONE_BRICKS);

                        entries.add(ModItems.RIFT_ARMOR_TRIM_SMITHING_TEMPLATE);
                    }).build());

    public static void registerItemGroup() {
    }
}
