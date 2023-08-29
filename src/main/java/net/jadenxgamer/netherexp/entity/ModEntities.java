package net.jadenxgamer.netherexp.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.entity.custom.WarphopperEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntities {
    public static final EntityType<WarphopperEntity> WARPHOPPER = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(NetherExp.MOD_ID, "warphopper"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, WarphopperEntity::new)
                    .dimensions(EntityDimensions.fixed(1.4f,4.5f)).build());
}
