package net.jadenxgamer.netherexp.mixin.client;

import net.jadenxgamer.netherexp.registry.JNEItems;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public class HumanoidModelMixin<T extends LivingEntity> {

    @Inject(
            method = "poseRightArm",
            at = @At(value = "TAIL")
    )
    private void netherexp$poseRightArm(T entity, CallbackInfo ci) {
        ItemStack main = entity.getMainHandItem();
        ItemStack offhand = entity.getOffhandItem();
        if (main.is(JNEItems.WILL_O_WISP.get())) {
            HumanoidModel<?> model = ((HumanoidModel<?>) (Object) this);
            model.rightArm.xRot = (float)Math.toRadians(-85.0F);
        }
        if (offhand.is(JNEItems.WILL_O_WISP.get())) {
            HumanoidModel<?> model = ((HumanoidModel<?>) (Object) this);
            model.leftArm.xRot = (float)Math.toRadians(-85.0F);
        }

        if (main.is(JNEItems.SHOTGUN_FIST.get())) {
            HumanoidModel<?> model = ((HumanoidModel<?>) (Object) this);
            model.rightArm.xRot = (float)Math.toRadians(-90.0F);
        }
        if (offhand.is(JNEItems.SHOTGUN_FIST.get())) {
            HumanoidModel<?> model = ((HumanoidModel<?>) (Object) this);
            model.leftArm.xRot = (float)Math.toRadians(-90.0F);
        }
    }
}
