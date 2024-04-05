package net.jadenxgamer.netherexp.registry.entity;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.custom.Apparition;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class JNEEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(NetherExp.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<Apparition>> APPARITION = ENTITY_TYPES.register("apparition", () ->
            EntityType.Builder.of(Apparition::new, MobCategory.MONSTER)
                    .sized(1.0f, 1.6f).build("apparition"));

    public static void init() {
        ENTITY_TYPES.register();
    }
}
