package net.jadenxgamer.netherexp.registry.entity;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.custom.Apparition;
import net.jadenxgamer.netherexp.registry.entity.custom.SoulBullet;
import net.jadenxgamer.netherexp.registry.entity.custom.Wisp;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class JNEEntityType {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(NetherExp.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<Apparition>> APPARITION = ENTITY_TYPES.register("apparition", () ->
            EntityType.Builder.of(Apparition::new, MobCategory.MONSTER)
                    .sized(1.0f, 2.2f).fireImmune().build("apparition"));

    public static final RegistrySupplier<EntityType<Wisp>> WISP = ENTITY_TYPES.register("wisp", () ->
            EntityType.Builder.of(Wisp::new, MobCategory.AMBIENT)
                    .sized(0.6f, 0.6f).fireImmune().build("wisp"));

    public static final RegistrySupplier<EntityType<SoulBullet>> SOUL_BULLET = ENTITY_TYPES.register("soul_bullet", () ->
            EntityType.Builder.<SoulBullet>of(SoulBullet::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("soul_bullet"));

    public static void init() {
        ENTITY_TYPES.register();
    }
}
