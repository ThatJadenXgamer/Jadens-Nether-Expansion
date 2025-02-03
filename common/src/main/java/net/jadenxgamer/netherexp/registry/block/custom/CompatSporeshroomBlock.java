package net.jadenxgamer.netherexp.registry.block.custom;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import java.util.function.Supplier;

public class CompatSporeshroomBlock extends SporeshroomBlock{

    private final ResourceLocation sporeResourceLocation;

    /**
     * Identical to normal SporeshroomBlock but uses a ResourceLocation for the spore particle instead, useful when doing compat for other mods
     * @param sporeResourceLocation - call the resource location for a modded particle
     * @param smogSupplier - defines the particle that rises from the center of the block
     * @param biomeTag - spores will NOT be produced inside the provided tag key
     */
    public CompatSporeshroomBlock(Properties properties, ResourceLocation sporeResourceLocation, Supplier<SimpleParticleType> smogSupplier, TagKey<Biome> biomeTag) {
        super(properties, null, smogSupplier, biomeTag);
        this.sporeResourceLocation = sporeResourceLocation;
    }

    @Override
    public SimpleParticleType getSporeParticle() {
        ParticleType<?> particle = BuiltInRegistries.PARTICLE_TYPE.get(this.sporeResourceLocation);
        if (particle instanceof SimpleParticleType simpleParticleType) {
            return simpleParticleType;
        }
        return ParticleTypes.CRIMSON_SPORE;
    }
}
