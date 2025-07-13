package net.jadenxgamer.netherexp.registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.entity.Wisp;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class JNEEntityType {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, NetherExp.MOD_ID);

    public static final Supplier<EntityType<Wisp>> WISP = ENTITY_TYPES.register("wisp", () ->
            EntityType.Builder.of(Wisp::new, MobCategory.AMBIENT)
                    .sized(0.5f, 0.6f).fireImmune().build("wisp"));

    public static void init(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(WISP.get(), Wisp.createAttributes().build());
    }

    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {

    }
}
