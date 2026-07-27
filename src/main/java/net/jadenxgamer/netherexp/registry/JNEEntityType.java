package net.jadenxgamer.netherexp.registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.entity.*;
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

    /**
     * Living Entities
     */

    public static final Supplier<EntityType<Wisp>> WISP = ENTITY_TYPES.register("wisp", () ->
            EntityType.Builder.of(Wisp::new, MobCategory.AMBIENT)
                    .sized(0.5f, 0.6f).fireImmune().build("wisp"));

    public static final Supplier<EntityType<Apparition>> APPARITION = ENTITY_TYPES.register("apparition", () ->
            EntityType.Builder.of(Apparition::new, MobCategory.MONSTER)
                    .sized(1.0f, 2.2f).fireImmune().build("apparition"));

    public static final Supplier<EntityType<Vessel>> VESSEL = ENTITY_TYPES.register("vessel", () ->
            EntityType.Builder.of(Vessel::new, MobCategory.MONSTER)
                    .sized(0.8f, 2.6f).fireImmune().build("vessel"));

    public static final Supplier<EntityType<EctoSlab>> ECTO_SLAB = ENTITY_TYPES.register("ecto_slab", () ->
            EntityType.Builder.of(EctoSlab::new, MobCategory.MONSTER)
                    .sized(1.375f, 0.75f).fireImmune().build("ecto_slab"));

    public static final Supplier<EntityType<Banshee>> BANSHEE = ENTITY_TYPES.register("banshee", () ->
            EntityType.Builder.of(Banshee::new, MobCategory.MONSTER)
                    .sized(1.25f, 2.375f).fireImmune().build("banshee"));

    public static final Supplier<EntityType<Carcass>> CARCASS = ENTITY_TYPES.register("carcass", () ->
            EntityType.Builder.of(Carcass::new, MobCategory.MISC)
                    .sized(1.4F, 1.3F).fireImmune().build("carcass"));

    /**
     * Non-Living Entities
     */

    public static final Supplier<EntityType<ShotgunPellet>> SHOTGUN_PELLET = ENTITY_TYPES.register("shotgun_pellet", () ->
            EntityType.Builder.<ShotgunPellet>of(ShotgunPellet::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("shotgun_pellet"));

    public static final Supplier<EntityType<SlugPellet>> SLUG_PELLET = ENTITY_TYPES.register("slug_pellet", () ->
            EntityType.Builder.<SlugPellet>of(SlugPellet::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("slug_pellet"));

    public static final Supplier<EntityType<PhasmoPellet>> PHASMO_PELLET = ENTITY_TYPES.register("phasmo_pellet", () ->
            EntityType.Builder.<PhasmoPellet>of(PhasmoPellet::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("phasmo_pellet"));

    public static final Supplier<EntityType<PhasmoArrow>> PHASMO_ARROW = ENTITY_TYPES.register("phasmo_arrow", () ->
            EntityType.Builder.<PhasmoArrow>of(PhasmoArrow::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("phasmo_arrow"));

    public static final Supplier<EntityType<WillOWisp>> WILL_O_WISP = ENTITY_TYPES.register("will_o_wisp", () ->
            EntityType.Builder.<WillOWisp>of(WillOWisp::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("will_o_wisp"));

    public static final Supplier<EntityType<PortalGlow>> PORTAL_GLOW = ENTITY_TYPES.register("portal_glow", () ->
            EntityType.Builder.<PortalGlow>of(PortalGlow::new, MobCategory.MISC)
                    .sized(1.0f, 1.0f).noSummon().build("portal_glow"));

    public static void init(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(WISP.get(), Wisp.createAttributes().build());
        event.put(APPARITION.get(), Apparition.createAttributes().build());
        event.put(VESSEL.get(), Vessel.createAttributes().build());
        event.put(ECTO_SLAB.get(), EctoSlab.createAttributes().build());
        event.put(BANSHEE.get(), Banshee.createAttributes().build());
        event.put(CARCASS.get(), Carcass.createAttributes().build());
    }

    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(JNEEntityType.APPARITION.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ExorcismMob::checkSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(JNEEntityType.VESSEL.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ExorcismMob::checkSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(JNEEntityType.ECTO_SLAB.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EctoSlab::checkSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(JNEEntityType.BANSHEE.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ExorcismMob::checkSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}