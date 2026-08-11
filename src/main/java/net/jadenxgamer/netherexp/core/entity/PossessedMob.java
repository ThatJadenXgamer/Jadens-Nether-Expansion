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
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;

public abstract class PossessedMob extends ExorcismMob implements Enemy {

    private String possessionOf;

    protected PossessedMob(EntityType<? extends PathfinderMob> entityType, Level level, ResourceLocation defaultPossessionOf) {
        super(entityType, level);
        this.possessionOf = defaultPossessionOf.toString();
    }

    @Override
    public void doExorcism() {
        if (this.isDeadOrDying()) return;
        BlockPos pos = this.blockPosition();
        this.level().playSound(null, pos, JNESoundEvents.APPARITION_DEATH.get(), SoundSource.NEUTRAL, 1.0f, 1.0f);
        this.level().broadcastEntityEvent(this, (byte) 92);

        EntityType<?> possessionOf = getPossessionOf() == null ? null : LookupRegistryHelper.getEntityType(ResourceLocation.parse(getPossessionOf()));
        if (possessionOf == null) {
            this.discard();
            return;
        }
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
        var multiplier = level().getDifficulty() == Difficulty.HARD ? JNEConfigs.HARD_DIFFICULTY_UNLEASHING_MULTIPLIER.get() : 1.0;
        var unleashingOdds = this.apparitionUnleashingOdds() * multiplier;
        if (random.nextDouble() > unleashingOdds || !JNEConfigs.POSSESSED_MOBS_UNLEASH_APPARITION.get()) return;

        Apparition apparition = JNEEntityType.APPARITION.get().create(this.level());
        if (apparition != null && this.level() instanceof ServerLevel serverLevel) {
            apparition.setPossessionCooldown(JNEConfigs.APPARITION_POSSESSION_COOLDOWN.get());
            apparition.setPersonality(apparitionPersonality());
            apparition.setPos(this.getX(), this.getY(), this.getZ());
            apparition.finalizeSpawn(serverLevel, this.level().getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.CONVERSION, null);
            if (this.getTarget() != null) apparition.setTarget(this.getTarget());
            this.level().addFreshEntity(apparition);
        }
    }

    /**
     * @return unleashing odds for an apparition upon the possession's death
     */
    protected double apparitionUnleashingOdds() {
        return 0.0;
    }

    /**
     * @return sets the unleashed apparition to the specified personality
     * <p>
     * If set to "0" the personality will be randomized between 1-4
     */
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
        if (nbt.contains("PossessionOf")) {
            this.setPossessionOf(nbt.getString("PossessionOf"));
        }
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

    public void setPossessionOf(ResourceLocation possessionOf) {
        this.possessionOf = possessionOf.toString();
    }
}