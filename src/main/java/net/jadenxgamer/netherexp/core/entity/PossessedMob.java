package net.jadenxgamer.netherexp.core.entity;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

public abstract class PossessedMob<T extends Mob> extends ExorcismMob {

    private final Supplier<EntityType<T>> possessionOf;

    protected PossessedMob(EntityType<? extends PathfinderMob> entityType, Level level, Supplier<EntityType<T>> possessionOf) {
        super(entityType, level);
        this.possessionOf = possessionOf;
    }

    @Override
    public void doExorcism() {
        BlockPos pos = this.blockPosition();
        this.level().playSound(null, pos, JNESoundEvents.APPARITION_DEATH.get(), SoundSource.NEUTRAL, 1.0f, 1.0f);

        Mob convertTo = this.convertTo(possessionOf.get(), true);
        if (convertTo != null && this.level() instanceof ServerLevel serverLevel) {
            convertTo.finalizeSpawn(serverLevel, this.level().getCurrentDifficultyAt(pos), MobSpawnType.CONVERSION, null);
            convertTo.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
            if (this.hasCustomName()) convertTo.setCustomName(convertTo.getCustomName());
        }
    }

    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);
        if (!JNEConfigs.POSSESSED_MOBS_UNLEASH_APPARITION.get()) return;

        Apparition apparition = JNEEntityType.APPARITION.get().create(this.level());
        if (apparition != null) {
            apparition.setPossessionCooldown(JNEConfigs.APPARITION_POSSESSION_COOLDOWN.get());
            apparition.setPersonality(apparitionPersonality());
            apparition.setPos(this.getX(), this.getY(), this.getZ());
            this.level().addFreshEntity(apparition);
        }
    }

    public int apparitionPersonality() {
        return 0;
    }
}
