package net.jadenxgamer.netherexp.registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.entity.Apparition;
import net.jadenxgamer.netherexp.core.entity.ExorcismMob;
import net.jadenxgamer.netherexp.core.entity.Wisp;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
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
    public static final Supplier<EntityType<Apparition>> APPARITION = ENTITY_TYPES.register("apparition", () ->
            EntityType.Builder.of(Apparition::new, MobCategory.MONSTER)
                    .sized(1.0f, 2.2f).fireImmune().build("apparition"));

    public static void init(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(WISP.get(), Wisp.createAttributes().build());
        event.put(APPARITION.get(), Apparition.createAttributes().build());
    }

    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(JNEEntityType.APPARITION.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ExorcismMob::checkSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}
