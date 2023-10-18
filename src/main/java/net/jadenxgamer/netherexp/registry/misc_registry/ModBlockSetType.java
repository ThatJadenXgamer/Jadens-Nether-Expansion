package net.jadenxgamer.netherexp.registry.misc_registry;

import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.block.BlockSetType;
import net.minecraft.util.Identifier;

public class ModBlockSetType {

    public static final BlockSetType CLARET = new BlockSetTypeBuilder().register(new Identifier(NetherExp.MOD_ID, "claret"));
    public static final BlockSetType SMOKESTALK = new BlockSetTypeBuilder().register(new Identifier(NetherExp.MOD_ID, "smokestalk"));

    public static void registerBlockSetType() {
        NetherExp.LOGGER.debug("Registering BlockSetType for " + NetherExp.MOD_ID);
    }
}
