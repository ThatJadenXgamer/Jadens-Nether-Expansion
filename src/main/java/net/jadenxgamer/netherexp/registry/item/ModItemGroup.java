package net.jadenxgamer.netherexp.registry.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {

    public static ItemGroup NETHEREXP = Registry.register(Registries.ITEM_GROUP, new Identifier(NetherExp.MOD_ID, "netherexp"),
            FabricItemGroup.builder().displayName(Text.literal("Nether Expansion Tab"))
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

                        entries.add(ModBlocks.SOUL_CANDLE);
                        entries.add(ModBlocks.SOUL_GLASS);
                        entries.add(ModBlocks.SOUL_SWIRLS);
                        entries.add(ModBlocks.SOUL_SOIL_LAYER);
                        entries.add(Blocks.SOUL_SOIL);
                        entries.add(Blocks.SOUL_SAND);
                        entries.add(ModBlocks.ECTO_SOUL_SAND);
                        entries.add(ModBlocks.SOUL_MAGMA_BLOCK);

                        entries.add(Blocks.SOUL_TORCH);
                        entries.add(Blocks.SOUL_LANTERN);
                        entries.add(Blocks.SOUL_CAMPFIRE);
                        entries.add(ModBlocks.SOUL_JACK_O_LANTERN);

                        entries.add(ModItems.SORROWSQUASH_SEEDS);
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

                        entries.add(Blocks.BASALT);
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
                        entries.add(ModBlocks.IGNEOUS_REEDS);
                        entries.add(ModBlocks.IGNEOUS_VINES);
                        entries.add(ModBlocks.EXPLOSIVE_SCORIA);

                        entries.add(ModBlocks.QUARTZ_CRYSTAL);
                        entries.add(ModBlocks.QUARTZ_CRYSTAL_BLOCK);
                        entries.add(ModBlocks.CHISELED_QUARTZ_PILLAR);
                        entries.add(ModBlocks.CRACKED_QUARTZ_BRICKS);
                        entries.add(ModBlocks.SMOOTH_QUARTZ_WALL);
                        entries.add(ModBlocks.QUARTZ_BRICK_WALL);

                        entries.add(ModBlocks.SILICA_SAND);
                        entries.add(ModBlocks.SILICA_SANDSTONE);
                        entries.add(ModBlocks.SILICA_SANDSTONE_STAIRS);
                        entries.add(ModBlocks.SILICA_SANDSTONE_SLAB);
                        entries.add(ModBlocks.SILICA_SANDSTONE_WALL);
                        entries.add(ModBlocks.CUT_SILICA_SANDSTONE);
                        entries.add(ModBlocks.CUT_SILICA_SANDSTONE_SLAB);
                        entries.add(ModBlocks.SMOOTH_SILICA_SANDSTONE);
                        entries.add(ModBlocks.SMOOTH_SILICA_SANDSTONE_STAIRS);
                        entries.add(ModBlocks.SMOOTH_SILICA_SANDSTONE_SLAB);

                        entries.add(Blocks.NETHER_BRICKS);
                        entries.add(Blocks.NETHER_BRICK_STAIRS);
                        entries.add(Blocks.NETHER_BRICK_SLAB);
                        entries.add(Blocks.NETHER_BRICK_WALL);
                        entries.add(Blocks.NETHER_BRICK_FENCE);
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

                        entries.add(ModItems.RAW_PYRITE);
                        entries.add(ModItems.PYRITE_INGOT);
                        entries.add(ModBlocks.PYRITE_BLOCK);
                        entries.add(ModBlocks.PYRITE_STAIRS);
                        entries.add(ModBlocks.PYRITE_SLAB);
                        entries.add(ModBlocks.PYRITE_WALL);
                        entries.add(ModBlocks.PYRITE_LANTERN);
                        entries.add(ModBlocks.SOUL_PYRITE_LANTERN);
                        entries.add(ModBlocks.PYRITE_CHAIN);
                        entries.add(ModBlocks.MEDIUM_WEIGHTED_PRESSURE_PLATE);
                        entries.add(ModBlocks.PYRITE_BUTTON);
                        entries.add(ModBlocks.PYRITE_NETHER_BRICKS);

                        entries.add(ModBlocks.FLAMETHROWER);
                        entries.add(ModBlocks.SPIKETRAP);

                        entries.add(ModItems.LIGHTSPORES);
                        entries.add(Blocks.SHROOMLIGHT);
                        entries.add(ModItems.NIGHTSPORES);
                        entries.add(ModBlocks.SHROOMNIGHT);

                        entries.add(Items.NETHER_WART);
                        entries.add(Blocks.NETHER_WART_BLOCK);
                        entries.add(ModBlocks.NETHER_WART_BEARD);

                        entries.add(Blocks.WARPED_WART_BLOCK);
                        entries.add(ModBlocks.WARPED_WART_BEARD);

                        entries.add(Blocks.WEEPING_VINES);
                        entries.add(ModBlocks.WEEPING_IVY);
                        entries.add(Blocks.TWISTING_VINES);
                        entries.add(ModBlocks.TWISTING_IVY);
                    }).build());

    public static void registerItemGroup() {

    }
}
