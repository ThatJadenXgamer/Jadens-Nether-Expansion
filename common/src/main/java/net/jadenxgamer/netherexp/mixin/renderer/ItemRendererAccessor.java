package net.jadenxgamer.netherexp.mixin.renderer;

import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.entity.ItemRenderer;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Debug(export = true)
@Mixin(ItemRenderer.class)
public interface ItemRendererAccessor {
    @Accessor("itemModelShaper")
    ItemModelShaper netherexp$itemModelShaper();
}