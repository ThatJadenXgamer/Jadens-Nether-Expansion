package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.compat.CompatUtil;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MobMixin {
    @Inject(
            method = "getDefaultLootTable",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$configurableLootTables(CallbackInfoReturnable<ResourceLocation> cir) {
        // this lets me dynamically alter the drop loot tables for mobs with configs and not have to
        // rely on LootTableModifications or just overriding the loot json
        Mob mob = ((Mob) (Object) this);
        if (mob instanceof Hoglin && JNEConfigs.HOGLIN_DROPS_HOGHAM.get() && !CompatUtil.checkNethersDelight()) {
            cir.setReturnValue(new ResourceLocation(NetherExp.MOD_ID, "entities/hoglin_hogham"));
        }
        if (mob instanceof WitherSkeleton && JNEConfigs.WITHER_SKELETON_DROPS_FOSSIL_FUEL.get()) {
            cir.setReturnValue(
                new ResourceLocation(NetherExp.MOD_ID, "entities/wither_skeleton_fossil_fuel"));
        }
    }
}
