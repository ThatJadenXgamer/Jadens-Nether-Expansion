package net.jadenxgamer.netherexp.mixin.renderer;

import net.minecraft.core.particles.SimpleParticleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SimpleParticleType.class)
public interface SimpleParticleTypeAccessor {

    @Invoker("<init>")
    static SimpleParticleType netherexp$invokeRegisterParticleType(boolean bl) {
        throw new AssertionError();
    }
}
