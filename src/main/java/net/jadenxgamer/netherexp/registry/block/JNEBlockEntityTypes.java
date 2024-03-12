package net.jadenxgamer.netherexp.registry.block;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.entity.JNEBrushableBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class JNEBlockEntityTypes {

    // TODO Add Skeletal Heads
//    public static final BlockEntityType<ModSkullBlockEntity> SKULL =
//            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(NetherExp.MOD_ID, "skull"),
//                    FabricBlockEntityTypeBuilder.create(ModSkullBlockEntity::new,
//                    ModBlocks.SKELETAL_CREEPER_SKULL, ModBlocks.SKELETAL_CREEPER_WALL_SKULL
//                    ).build(null));

    public static final BlockEntityType<JNEBrushableBlockEntity> BRUSHABLE_BLOCK =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(NetherExp.MOD_ID, "brushable_block"),
                    FabricBlockEntityTypeBuilder.create(JNEBrushableBlockEntity::new, JNEBlocks.SUSPICIOUS_SOUL_SAND).build());

}
