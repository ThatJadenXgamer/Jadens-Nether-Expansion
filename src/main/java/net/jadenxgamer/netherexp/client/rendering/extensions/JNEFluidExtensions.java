package net.jadenxgamer.netherexp.client.rendering.extensions;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;

public class JNEFluidExtensions {

    public static final IClientFluidTypeExtensions ectoplasmExt = new IClientFluidTypeExtensions() {
        @Override
        public ResourceLocation getStillTexture() {
            return NetherExp.id("block/ectoplasm_still");
        }

        @Override
        public ResourceLocation getFlowingTexture() {
            return NetherExp.id("block/ectoplasm_flow");
        }
    };
}
