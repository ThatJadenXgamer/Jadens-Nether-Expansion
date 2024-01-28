package net.jadenxgamer.netherexp.registry.block;

import net.jadenxgamer.netherexp.NetherExp;

public class ModBlockEntities {

    // TODO Add Skeletal Heads
//    public static final BlockEntityType<ModSkullBlockEntity> SKULL =
//            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(NetherExp.MOD_ID, "skull"),
//                    FabricBlockEntityTypeBuilder.create(ModSkullBlockEntity::new,
//                    ModBlocks.SKELETAL_CREEPER_SKULL, ModBlocks.SKELETAL_CREEPER_WALL_SKULL
//                    ).build(null));

    public static void registerModBlockEntities() {
        NetherExp.LOGGER.debug("Registering Block Entities for " + NetherExp.MOD_ID);
    }
}
