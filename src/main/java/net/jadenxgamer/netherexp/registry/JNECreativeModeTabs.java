package net.jadenxgamer.netherexp.registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class JNECreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, NetherExp.MOD_ID);

    public static final Supplier<CreativeModeTab> SOUL_SAND_VALLEY = CREATIVE_MODE_TABS.register("soul_sand_valley", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.netherexp.jne_soul_sand_valley"))
            .icon(() -> new ItemStack(JNEBlocks.FOSSIL_ORE.get()))
            .displayItems((params, output) -> {
                output.accept(Blocks.SOUL_SOIL);
                output.accept(Blocks.SOUL_SAND);
                output.accept(JNEBlocks.ECTO_SOUL_SAND.get());
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

                output.accept(JNEBlocks.SOUL_GLASS.get());

                output.accept(JNEBlocks.FOSSIL_ORE.get());
                output.accept(JNEBlocks.FOSSIL_FUEL_ORE.get());

                output.accept(JNEBlocks.SOUL_SWIRLS.get());

                output.accept(Blocks.SOUL_TORCH);
                output.accept(Blocks.SOUL_LANTERN);
                output.accept(Blocks.SOUL_CAMPFIRE);
                output.accept(JNEBlocks.SOUL_CANDLE.get());

                output.accept(Items.BONE);
                output.accept(JNEItems.FOSSIL_FUEL.get());
                output.accept(JNEItems.WISP_BOTTLE.get());
                output.accept(JNEItems.WRAITHING_FLESH.get());
                output.accept(JNEItems.STRIDITE.get());
                output.accept(JNEItems.PHASMO_SHARD.get());
                output.accept(JNEItems.PHASMO_ARROW.get());
                output.accept(JNEItems.BANSHEE_ROD.get());
                output.accept(JNEItems.BANSHEE_POWDER.get());

                output.accept(JNEItems.WISP_SPAWN_EGG.get());
                output.accept(JNEItems.APPARITION_SPAWN_EGG.get());
                output.accept(JNEItems.VESSEL_SPAWN_EGG.get());
                output.accept(JNEItems.STAMPEDE_SPAWN_EGG.get());
                output.accept(JNEItems.ECTO_SLAB_SPAWN_EGG.get());
                output.accept(JNEItems.BANSHEE_SPAWN_EGG.get());
            })
            .build());

    public static void addToExistingTabs(BuildCreativeModeTabContentsEvent event) {
//        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
//            event.insertAfter(Items.OAK_WOOD.getDefaultInstance(), OKSBlocks.OAK_TWIGS.get().asItem().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
//        }
    }

    public static void init(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
