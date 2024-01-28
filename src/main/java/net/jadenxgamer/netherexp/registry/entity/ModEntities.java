package net.jadenxgamer.netherexp.registry.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.custom.*;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<WarphopperEntity> WARPHOPPER = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(NetherExp.MOD_ID, "warphopper"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, WarphopperEntity::new).fireImmune()
                    .dimensions(EntityDimensions.fixed(0.6F, 2.2f)).build());

    public static final EntityType<ApparitionEntity> APPARITION = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(NetherExp.MOD_ID, "apparition"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ApparitionEntity::new).fireImmune()
                    .dimensions(EntityDimensions.changing(0.8F, 1.6f)).build());

    public static final EntityType<WispEntity> WISP = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(NetherExp.MOD_ID, "wisp"),
            FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, WispEntity::new).fireImmune()
                    .dimensions(EntityDimensions.fixed(0.4F, 0.4f)).build());

    public static final EntityType<GraspEntity> GRASP = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(NetherExp.MOD_ID, "grasp"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, GraspEntity::new).fireImmune()
                    .dimensions(EntityDimensions.fixed(1.2F, 3.5f)).build());

    public static final EntityType<MistChargeEntity> MIST_CHARGE = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(NetherExp.MOD_ID, "mist_charge"),
            FabricEntityTypeBuilder.<MistChargeEntity>create(SpawnGroup.MISC, MistChargeEntity::new)
            .dimensions(EntityDimensions.fixed(0.5F, 0.5F)).build());

    public static final EntityType<GraveCloudEntity> MIST_CHARGE_CLOUD = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(NetherExp.MOD_ID, "grave_cloud"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, GraveCloudEntity::new).fireImmune()
            .dimensions(EntityDimensions.changing(3.0F, 2.4F)).build());
}
