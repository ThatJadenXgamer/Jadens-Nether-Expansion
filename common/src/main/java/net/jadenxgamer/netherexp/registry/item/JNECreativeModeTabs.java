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
                        output.accept(JNEBlocks.PALE_SOUL_SLATE.get());
                        output.accept(JNEBlocks.JAGGED_SOUL_SLATE.get());
                        output.accept(JNEBlocks.SOUL_SLATE.get());
                        output.accept(JNEBlocks.SOUL_SLATE_STAIRS.get());
                        output.accept(JNEBlocks.SOUL_SLATE_SLAB.get());
                        output.accept(JNEBlocks.SOUL_SLATE_WALL.get());
                        output.accept(JNEBlocks.SOUL_SLATE_BRICKS.get());
                        output.accept(JNEBlocks.SOUL_SLATE_BRICK_STAIRS.get());
                        output.accept(JNEBlocks.SOUL_SLATE_BRICK_SLAB.get());
                        output.accept(JNEBlocks.SOUL_SLATE_BRICK_WALL.get());
                        output.accept(JNEBlocks.INDENTED_SOUL_SLATE_BRICKS.get());
                        output.accept(JNEBlocks.CRACKED_SOUL_SLATE_BRICKS.get());
                        output.accept(JNEBlocks.SOUL_SLATE_BRICK_PILLAR.get());
                        output.accept(JNEBlocks.CHISELED_SOUL_SLATE_BRICKS.get());
                        output.accept(JNEBlocks.SOUL_SWIRLS.get());
                        output.accept(JNEBlocks.SOUL_GLASS.get());
                        output.accept(Blocks.SOUL_SOIL);
                        output.accept(Blocks.SOUL_SAND);
                        output.accept(JNEBlocks.ECTO_SOUL_SAND.get());
                        output.accept(JNEBlocks.SUSPICIOUS_SOUL_SAND.get());
                        output.accept(JNEBlocks.SOUL_MAGMA_BLOCK.get());
                        output.accept(JNEBlocks.SOUL_CANDLE.get());
                    })
                    .build());

    public static void init() {
        CREATIVE_MODE_TABS.register();
    }
}
