package net.jadenxgamer.netherexp.mixin.block;

import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockSetType.class)
public interface BlockSetTypeAccessor {

    @Invoker("register")
    static BlockSetType netherexp$invokeRegisterBlockSetType(BlockSetType blockSetType) {
        throw new AssertionError();
    }
}
