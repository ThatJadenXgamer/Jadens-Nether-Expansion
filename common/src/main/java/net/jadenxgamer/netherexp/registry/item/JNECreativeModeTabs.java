package net.jadenxgamer.netherexp.registry.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class JNECreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(NetherExp.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> NETHEREXP = CREATIVE_MODE_TABS.register("netherexp",
            () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 1).icon(() -> new ItemStack(Items.CRIMSON_NYLIUM))
                    .title(Component.literal("Jaden's Nether Expansion"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(JNEBlocks.SOUL_SLATE.get());
                        output.accept(JNEBlocks.PALE_SOUL_SLATE.get());
                        output.accept(JNEBlocks.JAGGED_SOUL_SLATE.get());
                        output.accept(JNEBlocks.SOUL_SLATE_STAIRS.get());
                        output.accept(JNEBlocks.SOUL_SLATE_SLAB.get());
                        output.accept(JNEBlocks.SOUL_SLATE_WALL.get());
                        output.accept(JNEBlocks.SOUL_SLATE_BRICKS.get());
                        output.accept(JNEBlocks.SOUL_SLATE_BRICK_STAIRS.get());
                        output.accept(JNEBlocks.SOUL_SLATE_BRICK_SLAB.get());
                        output.accept(JNEBlocks.SOUL_SLATE_BRICK_WALL.get());
                        output.accept(JNEBlocks.CRACKED_SOUL_SLATE_BRICKS.get());
                        output.accept(JNEBlocks.INDENTED_SOUL_SLATE_BRICKS.get());
                        output.accept(JNEBlocks.CHISELED_SOUL_SLATE_BRICKS.get());
                        output.accept(JNEBlocks.SOUL_SLATE_BRICK_PILLAR.get());

                        output.accept(JNEBlocks.FOSSIL_ORE.get());
                        output.accept(JNEBlocks.FOSSIL_FUEL_ORE.get());
                        output.accept(JNEBlocks.DIAMOND_FOSSIL_ORE.get());
                        output.accept(JNEItems.FOSSIL_FUEL.get());

                        output.accept(JNEItems.NECRO_SHARD.get());
                        //output.accept(JNEItems.GUILLOTINE);
                        output.accept(JNEItems.SHOTGUN_FIST.get());

                        output.accept(JNEItems.WRAITHING_FLESH.get());
                        output.accept(JNEBlocks.SOUL_CANDLE.get());
                        output.accept(JNEBlocks.SOUL_GLASS.get());
                        output.accept(JNEBlocks.SOUL_SWIRLS.get());
                        if (NetherExp.checkModCompatCinderscapes()){
                            output.accept(JNEBlocks.SHALE_SWIRLS.get());
                        }
                        //output.accept(JNEBlocks.SOUL_SOIL_LAYER);
                        output.accept(Blocks.SOUL_SOIL);
                        //output.accept(JNEBlocks.SOUL_PATH);
                        output.accept(JNEBlocks.SUSPICIOUS_SOUL_SAND.get());
                        output.accept(Blocks.SOUL_SAND);
                        output.accept(JNEBlocks.ECTO_SOUL_SAND.get());
                        output.accept(JNEBlocks.SOUL_MAGMA_BLOCK.get());

                        output.accept(JNEBlocks.BLACK_ICE.get());
                        //output.accept(JNEFluids.ECTOPLASM_BUCKET);

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

                        //output.accept(JNEItems.WHITE_ASH_POWDER);
                        //output.accept(JNEBlocks.WHITE_ASH_BLOCK);
                        //output.accept(JNEBlocks.WHITE_ASH);
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
                        
                        output.accept(JNEBlocks.ENIGMA_CROWN.get());
                        output.accept(JNEBlocks.ENIGMA_SHELF.get());
                        output.accept(JNEBlocks.ENIGMA_FLESH.get());
                        output.accept(JNEBlocks.STRANGE_ENIGMA_FLESH.get());

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
                    })
                    .build());

    public static void init() {
        CREATIVE_MODE_TABS.register();
    }
}
