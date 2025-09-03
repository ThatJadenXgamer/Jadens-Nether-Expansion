package net.jadenxgamer.netherexp.core.entity;

import net.jadenxgamer.elysium_api.api.util.LookupRegistryHelper;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public abstract class PossessedMob extends ExorcismMob {

    private String possessionOf;

    protected PossessedMob(EntityType<? extends PathfinderMob> entityType, Level level, ResourceLocation defaultPossessionOf) {
        super(entityType, level);
        this.possessionOf = defaultPossessionOf.toString();
    }

    @Override
    public void doExorcism() {
        BlockPos pos = this.blockPosition();
        this.level().playSound(null, pos, JNESoundEvents.APPARITION_DEATH.get(), SoundSource.NEUTRAL, 1.0f, 1.0f);
        this.level().broadcastEntityEvent(this, (byte) 77);

        var possessionOf = getPossessionOf() == null ? null : LookupRegistryHelper.getEntityType(ResourceLocation.parse(getPossessionOf()));
        if (possessionOf == null) return;
        EntityType<? extends Mob> possessionType = (EntityType<? extends Mob>) possessionOf;
        Mob convertTo = this.convertTo(possessionType, true);
        if (convertTo != null && this.level() instanceof ServerLevel serverLevel) {
            convertTo.finalizeSpawn(serverLevel, this.level().getCurrentDifficultyAt(pos), MobSpawnType.CONVERSION, null);
            convertTo.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
            if (this.hasCustomName()) convertTo.setCustomName(convertTo.getCustomName());
        }
    }

    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);
        var unleashingOdds = this.random.nextInt(apparitionUnleashingOdds(this.level().getDifficulty()));
        if (unleashingOdds == 0 && !JNEConfigs.POSSESSED_MOBS_UNLEASH_APPARITION.get()) return;

        Apparition apparition = JNEEntityType.APPARITION.get().create(this.level());
        if (apparition != null) {
            apparition.setPossessionCooldown(JNEConfigs.APPARITION_POSSESSION_COOLDOWN.get());
            apparition.setPersonality(apparitionPersonality());
            apparition.setPos(this.getX(), this.getY(), this.getZ());
            this.level().addFreshEntity(apparition);
        }
    }

    protected int apparitionUnleashingOdds(Difficulty difficulty) {
        return difficulty == Difficulty.HARD ? 1 : 3;
    }

    public int apparitionPersonality() {
        return 0;
    }

    //////////
    // DATA //
    //////////

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putString("PossessionOf", this.getPossessionOf());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setPossessionOf(nbt.getString("PossessionOf"));
    }

    ///////////////////////
    // GETTERS & SETTERS //
    ///////////////////////

    public String getPossessionOf() {
        return possessionOf;
    }

    public void setPossessionOf(String possessionOf) {
        this.possessionOf = possessionOf;
    }
}
