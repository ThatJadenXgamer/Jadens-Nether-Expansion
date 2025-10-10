package net.jadenxgamer.netherexp.util;

import com.mojang.serialization.MapCodec;
import net.jadenxgamer.netherexp.core.item.GargoyleStatueItem;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.function.Supplier;

public class RegistryHelper {

    public static <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> block) {
        Supplier<T> toReturn = JNEBlocks.BLOCKS.register(name, block);
        JNEItems.ITEMS.register(name, () -> new BlockItem(toReturn.get(), new Item.Properties()));
        return toReturn;
    }

    public static <T extends Block> Supplier<T> registerItemPropertiesBlock(String name, Supplier<T> block, Item.Properties properties) {
        Supplier<T> toReturn = JNEBlocks.BLOCKS.register(name, block);
        JNEItems.ITEMS.register(name, () -> new BlockItem(toReturn.get(), properties));
        return toReturn;
    }

    public static <T extends Block> Supplier<T> registerGargoyleStatue(String name, Supplier<T> block, Item.Properties properties) {
        Supplier<T> toReturn = JNEBlocks.BLOCKS.register(name, block);
        JNEItems.ITEMS.register(name, () -> new GargoyleStatueItem(toReturn.get(), properties.stacksTo(16)));
        return toReturn;
    }

    public static <T extends Block> Supplier<T> registerBlockWithoutItem(String name, Supplier<T> block) {
        return JNEBlocks.BLOCKS.register(name, block);
    }

    // Dear mojang, I hope your soup is cold for this. It was actual torture figuring out registering ColorParticleOptions.
    public static Supplier<ParticleType<ColorParticleOption>> registerColorParticle(String name, boolean overrideLimitter) {
        return JNEParticleTypes.PARTICLE_TYPES.register(name, () ->
                new ParticleType<ColorParticleOption>(overrideLimitter) {
                    @Override
                    public MapCodec<ColorParticleOption> codec() {
                        return ColorParticleOption.codec(this);
                    }

                    @Override
                    public StreamCodec<? super RegistryFriendlyByteBuf, ColorParticleOption> streamCodec() {
                        return ColorParticleOption.streamCodec(this);
                    }
                }
        );
    }

    public static <T> void vanillaRegister(RegisterEvent.RegisterHelper<T> registry, String name, Supplier<T> object) {
        registry.register(ResourceLocation.fromNamespaceAndPath("minecraft", name), object.get());
    }

    public static void insertToTab(BuildCreativeModeTabContentsEvent event, Item after, Block toAdd, boolean before) {
        insertToTab(event, after, toAdd.asItem(), before);
    }

    public static void insertToTab(BuildCreativeModeTabContentsEvent event, Block after, Block toAdd, boolean before) {
        insertToTab(event, after.asItem(), toAdd.asItem(), before);
    }

    public static void insertToTab(BuildCreativeModeTabContentsEvent event, Block after, Item toAdd, boolean before) {
        insertToTab(event, after.asItem(), toAdd, before);
    }

    public static void insertToTab(BuildCreativeModeTabContentsEvent event, Item after, Item toAdd, boolean before) {
        if (before) {
            event.insertBefore(after.getDefaultInstance(), toAdd.getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        } else {
            event.insertAfter(after.getDefaultInstance(), toAdd.getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
