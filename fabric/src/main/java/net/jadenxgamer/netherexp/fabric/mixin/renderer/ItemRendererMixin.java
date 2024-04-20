package net.jadenxgamer.netherexp.fabric.mixin.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyVariable(
            method = "render",
            at = @At(value = "HEAD"),
            argsOnly = true
    )
    public BakedModel netherexp$2dTexture3dModel(BakedModel value, ItemStack stack, ItemDisplayContext context, boolean leftHanded, PoseStack pose, MultiBufferSource multiBufferSource, int light, int overlay) {
        if (stack.is(JNEItems.SHOTGUN_FIST.get()) && context != ItemDisplayContext.GUI) {
            return ((ItemRendererAccessor) this).netherexp$itemModelShaper().getModelManager().getModel(new ModelResourceLocation(NetherExp.MOD_ID, "shotgun_fist_handheld", "inventory"));
        }
        return value;
    }
}
