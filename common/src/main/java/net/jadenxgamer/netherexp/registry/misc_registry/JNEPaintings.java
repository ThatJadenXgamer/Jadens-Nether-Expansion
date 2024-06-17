package net.jadenxgamer.netherexp.registry.misc_registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class JNEPaintings {
    public static final DeferredRegister<PaintingVariant> PAINTING_VARIANT = DeferredRegister.create(NetherExp.MOD_ID, Registries.PAINTING_VARIANT);

    public static final RegistrySupplier<PaintingVariant> HOUSE = PAINTING_VARIANT.register("house", () ->
            new PaintingVariant(64, 32));

    public static void init() {
        PAINTING_VARIANT.register();
    }
}
