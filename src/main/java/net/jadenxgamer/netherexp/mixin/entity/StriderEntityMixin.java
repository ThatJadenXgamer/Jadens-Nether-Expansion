package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.ai.DockNearAromaGeneratorGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemSteerable;
import net.minecraft.entity.Saddleable;
import net.minecraft.entity.SaddledComponent;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StriderEntity.class)
public abstract class StriderEntityMixin extends AnimalEntity implements ItemSteerable, Saddleable {
    @Shadow public abstract boolean isSaddled();

    @Shadow @Final private SaddledComponent saddledComponent;

    protected StriderEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "initGoals",
            at = @At(value = "HEAD")
    )
    private void netherexp$initGoals(CallbackInfo ci) {
        this.goalSelector.add(3, new DockNearAromaGeneratorGoal(((StriderEntity) (Object) this), 1.2, 16, 6));
    }

    // Lets you retrieve your saddle from a Strider without brutally murdering it
    @Inject(
            method = "interactMob",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$interactStrider(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (this.isSaddled() && player.getStackInHand(hand).isEmpty() && player.isSneaking() && NetherExp.getConfig().entities.striderConfigs.retrievable_saddles) {
            this.saddledComponent.setSaddled(false);
            player.playSound(SoundEvents.ENTITY_STRIDER_SADDLE, 1, 1);
            player.setStackInHand(hand, Items.SADDLE.getDefaultStack());
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }
}
