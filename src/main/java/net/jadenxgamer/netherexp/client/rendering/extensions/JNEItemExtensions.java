package net.jadenxgamer.netherexp.client.rendering.extensions;

import net.jadenxgamer.netherexp.client.JNEItemRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class JNEItemExtensions {

    public static final IClientItemExtensions itemExt = new IClientItemExtensions() {
        @Override
        public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return new JNEItemRenderer();
        }
    };
}
