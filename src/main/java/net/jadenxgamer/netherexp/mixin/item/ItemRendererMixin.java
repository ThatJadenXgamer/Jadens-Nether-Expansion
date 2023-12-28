package net.jadenxgamer.netherexp.mixin.item;


import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.item.ModItems;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyVariable(
            method = "renderItem",
            at = @At(value = "HEAD"),
            argsOnly = true
    )
    public BakedModel useRubyStaffModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (stack.isOf(ModItems.GUILLOTINE) && renderMode != ModelTransformationMode.GUI) {
            return ((ItemRendererAccessor) this).netherexp$getModels().getModelManager().getModel(new ModelIdentifier(NetherExp.MOD_ID, "guillotine_hand", "inventory"));
        }
        return value;
    }
}