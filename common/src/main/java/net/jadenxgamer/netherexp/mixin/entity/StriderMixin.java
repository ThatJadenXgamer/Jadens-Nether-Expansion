package net.jadenxgamer.netherexp.mixin.entity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ItemBasedSteering;
import net.minecraft.world.entity.ItemSteerable;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Strider.class)
public abstract class StriderMixin extends Animal implements ItemSteerable, Saddleable {
    @Shadow
    public abstract boolean isSaddled();

    @Shadow @Final
    private ItemBasedSteering steering;

    protected StriderMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    // Lets you retrieve your saddle from a Strider without brutally murdering it
    @Inject(
            method = "mobInteract",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$mobInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (this.isSaddled() && player.getItemInHand(hand).isEmpty() && player.isShiftKeyDown()) {
            this.steering.setSaddle(false);
            player.level().playSound(null, player.getOnPos(), SoundEvents.STRIDER_SADDLE, SoundSource.PLAYERS, 1.0f, 1.0f);
            player.setItemInHand(hand, Items.SADDLE.getDefaultInstance());
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}