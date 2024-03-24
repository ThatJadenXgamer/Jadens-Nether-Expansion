package net.jadenxgamer.netherexp.registry.block;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

public class JNEBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(NetherExp.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<Block> SOUL_SLATE = registerBlock("soul_slate", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(4.0f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));

    public static final RegistrySupplier<Block> SOUL_SLATE_SLAB = registerBlock("soul_slate_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.copy(SOUL_SLATE.get())));


    private static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistrySupplier<T> registerBlockWithoutItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistrySupplier<Item> registerBlockItem(String name, RegistrySupplier<T> block) {
        return JNEItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void init() {
        BLOCKS.register();
    }
}
