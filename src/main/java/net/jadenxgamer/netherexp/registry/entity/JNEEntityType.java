package net.jadenxgamer.netherexp.registry.entity;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.custom.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JNEEntityType {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, NetherExp.MOD_ID);

    // LIVING ENTITIES

    public static final RegistryObject<EntityType<Apparition>> APPARITION = ENTITY_TYPES.register("apparition", () ->
            EntityType.Builder.of(Apparition::new, MobCategory.MONSTER)
                    .sized(1.0f, 2.2f).fireImmune().build("apparition"));

    public static final RegistryObject<EntityType<Wisp>> WISP = ENTITY_TYPES.register("wisp", () ->
            EntityType.Builder.of(Wisp::new, MobCategory.AMBIENT)
                    .sized(0.6f, 0.6f).fireImmune().build("wisp"));

    public static final RegistryObject<EntityType<Vessel>> VESSEL = ENTITY_TYPES.register("vessel", () ->
            EntityType.Builder.of(Vessel::new, MobCategory.MONSTER)
                    .sized(0.6F, 2.99F).fireImmune().build("vessel"));

    public static final RegistryObject<EntityType<EctoSlab>> ECTO_SLAB = ENTITY_TYPES.register("ecto_slab", () ->
            EntityType.Builder.of(EctoSlab::new, MobCategory.MONSTER)
                    .sized(2.04F, 2.04F).fireImmune().build("ecto_slab"));

    public static final RegistryObject<EntityType<Banshee>> BANSHEE = ENTITY_TYPES.register("banshee", () ->
            EntityType.Builder.of(Banshee::new, MobCategory.MONSTER)
                    .sized(1.2F, 2.55F).fireImmune().build("banshee"));

    public static final RegistryObject<EntityType<Stampede>> STAMPEDE = ENTITY_TYPES.register("stampede", () ->
            EntityType.Builder.of(Stampede::new, MobCategory.MONSTER)
                    .sized(1.8F, 4.0F).fireImmune().build("stampede"));

    public static final RegistryObject<EntityType<Carcass>> CARCASS = ENTITY_TYPES.register("carcass", () ->
            EntityType.Builder.of(Carcass::new, MobCategory.MISC)
                    .sized(1.4F, 1.3F).fireImmune().build("carcass"));

    public static final RegistryObject<EntityType<FalseCarcass>> FALSE_CARCASS = ENTITY_TYPES.register("false_carcass", () ->
            EntityType.Builder.of(FalseCarcass::new, MobCategory.MISC)
                    .sized(1.4F, 1.0F).fireImmune().build("false_carcass"));

    // OTHER

    public static final RegistryObject<EntityType<ShotgunPellet>> SHOTGUN_PELLET = ENTITY_TYPES.register("shotgun_pellet", () ->
            EntityType.Builder.<ShotgunPellet>of(ShotgunPellet::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("shotgun_pellet"));

    public static final RegistryObject<EntityType<BlackIcicle>> BLACK_ICICLE = ENTITY_TYPES.register("black_icicle", () ->
            EntityType.Builder.<BlackIcicle>of(BlackIcicle::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("black_icicle"));

    public static final RegistryObject<EntityType<PhasmoArrow>> PHASMO_ARROW = ENTITY_TYPES.register("phasmo_arrow", () ->
            EntityType.Builder.<PhasmoArrow>of(PhasmoArrow::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("phasmo_arrow"));

    public static final RegistryObject<EntityType<MistCharge>> MIST_CHARGE = ENTITY_TYPES.register("mist_charge", () ->
            EntityType.Builder.<MistCharge>of(MistCharge::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("mist_charge"));

    public static final RegistryObject<EntityType<GraveCloud>> GRAVE_CLOUD = ENTITY_TYPES.register("grave_cloud", () ->
            EntityType.Builder.of(GraveCloud::new, MobCategory.MISC)
                    .sized(3.0F, 2.4F).fireImmune().build("grave_cloud"));

    public static final RegistryObject<EntityType<WillOWisp>> WILL_O_WISP = ENTITY_TYPES.register("will_o_wisp", () ->
            EntityType.Builder.<WillOWisp>of(WillOWisp::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F).build("will_o_wisp"));

    public static final RegistryObject<EntityType<ThrownAntidote>> ANTIDOTE = ENTITY_TYPES.register("antidote", () ->
            EntityType.Builder.<ThrownAntidote>of(ThrownAntidote::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F).build("antidote"));

    public static final RegistryObject<EntityType<GrenadeEffectCloud>> GRENADE_EFFECT_CLOUD = ENTITY_TYPES.register("grenade_effect_cloud", () ->
            EntityType.Builder.<GrenadeEffectCloud>of(GrenadeEffectCloud::new, MobCategory.MISC)
                    .sized(10.0F, 6.5F).fireImmune().build("grenade_effect_cloud"));

    public static void init(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
