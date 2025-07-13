package net.jadenxgamer.netherexp.util;

import com.mojang.serialization.MapCodec;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

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

    public static <T extends Block> Supplier<T> registerBlockWithoutItem(String name, Supplier<T> block) {
        return JNEBlocks.BLOCKS.register(name, block);
    }

    // Dear mojang, I hope your soup is cold for this. It was actual torture figuring out your spaghetti of a "code" that was registering ColorParticleOptions.
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
}
