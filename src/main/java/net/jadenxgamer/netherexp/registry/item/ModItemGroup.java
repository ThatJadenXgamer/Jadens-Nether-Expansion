package net.jadenxgamer.netherexp.registry.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
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
                        entries.add(ModBlocks.SOUL_SLATE_SLAB);
                        entries.add(ModBlocks.SOUL_SLATE_STAIRS);
                        entries.add(ModBlocks.SOUL_SLATE_WALL);
                        entries.add(ModBlocks.SOUL_SLATE_BRICKS);
                        entries.add(ModBlocks.SOUL_SLATE_BRICK_SLAB);
                        entries.add(ModBlocks.SOUL_SLATE_BRICK_STAIRS);
                        entries.add(ModBlocks.SOUL_SLATE_BRICK_WALL);
                        entries.add(ModBlocks.CRACKED_SOUL_SLATE_BRICKS);
                        entries.add(ModBlocks.INDENTED_SOUL_SLATE_BRICKS);
                        entries.add(ModBlocks.CHISELED_SOUL_SLATE_BRICKS);
                        entries.add(ModBlocks.SOUL_SLATE_BRICK_PILLAR);

                        entries.add(ModBlocks.SOUL_CANDLE);
                        entries.add(ModBlocks.SOUL_GLASS);
                        entries.add(ModBlocks.SOUL_SWIRLS);
                        entries.add(ModBlocks.SOUL_SOIL_LAYER);
                        entries.add(ModBlocks.ECTO_SOUL_SAND);
                        entries.add(ModBlocks.SOUL_MAGMA_BLOCK);

                        entries.add(ModItems.SORROWSQUASH_SEEDS);
                        entries.add(ModBlocks.SORROWSQUASH);
                        entries.add(ModBlocks.CARVED_SORROWSQUASH);
                        entries.add(ModBlocks.GHOUL_O_LANTERN);
                        entries.add(ModBlocks.SOUL_GHOUL_O_LANTERN);
                        entries.add(ModBlocks.SOUL_JACK_O_LANTERN);

                        entries.add(ModBlocks.SMOOTH_NETHERRACK);
                        entries.add(ModBlocks.SMOOTH_NETHERRACK_SLAB);
                        entries.add(ModBlocks.SMOOTH_NETHERRACK_STAIRS);
                        entries.add(ModBlocks.SMOOTH_NETHERRACK_WALL);
                        entries.add(ModBlocks.NETHERRACK_BRICKS);
                        entries.add(ModBlocks.NETHERRACK_BRICK_SLAB);
                        entries.add(ModBlocks.NETHERRACK_BRICK_STAIRS);
                        entries.add(ModBlocks.NETHERRACK_BRICK_WALL);
                        entries.add(ModBlocks.NETHERRACK_TILES);
                        entries.add(ModBlocks.NETHERRACK_PILLAR);

                        entries.add(ModBlocks.BASALT_SLAB);
                        entries.add(ModBlocks.POLISHED_BASALT_BRICKS);
                        entries.add(ModBlocks.POLISHED_BASALT_BRICK_SLAB);
                        entries.add(ModBlocks.POLISHED_BASALT_BRICK_STAIRS);

                        entries.add(ModBlocks.ENIGMA_CROWN);
                        entries.add(ModBlocks.ENIGMA_SHELF);
                        entries.add(ModBlocks.ENIGMA_FLESH);
                        entries.add(ModBlocks.STRANGE_ENIGMA_FLESH);
                    }).build());

    public static void registerItemGroup() {

    }
}
