package net.jadenxgamer.netherexp.core.entity;

import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

import java.util.Optional;

import static net.jadenxgamer.netherexp.config.JNEConfigs.*;
import static net.minecraft.world.entity.monster.Monster.isDarkEnoughToSpawn;

public abstract class ExorcismMob extends PathfinderMob {

    protected ExorcismMob(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void aiStep() {
        if (!level().isClientSide && this.isInWaterOrRain()) {
            doExorcism();
        }
        super.aiStep();
    }

    @Override
    public boolean isSensitiveToWater() {
        return true;
    }

    public boolean hurtWithCleanWater(ThrownPotion potion) {
        ItemStack stack = potion.getItem();
        PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
        if (contents == null) return false;

        Optional<Holder<Potion>> potionContents = contents.potion();
        return potionContents.isPresent() && potionContents.get().is(Potions.WATER) && contents.customEffects().isEmpty();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getDirectEntity() instanceof ThrownPotion potion && hurtWithCleanWater(potion)) {
            doExorcism();
        } else if (source.getEntity() instanceof Player player) {
            if (player.getMainHandItem().is(JNETags.Items.SILVER_WEAPONS)) {
                amount *= SILVER_PARANORMAL_DAMAGE_MULTIPLIER.get();
                if (SILVER_PARANORMAL_INFLICTS_SLOWNESS.get()) this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 2));
                this.level().broadcastEntityEvent(this, (byte) 47);
            }
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (target instanceof Player player) {
            boolean isWearingSilverArmor = player.getInventory().armor.stream().anyMatch(itemStack -> itemStack.is(JNETags.Items.SILVER_ARMORS));

            if (isWearingSilverArmor) {
                this.hurt(level().damageSources().playerAttack(player), (float) SILVER_PARANORMAL_PROTECTION_DAMAGE.getAsDouble());
                if (SILVER_PARANORMAL_INFLICTS_SLOWNESS.get()) this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 2));
                this.level().broadcastEntityEvent(this, (byte) 47);
            }
        }
        return super.doHurtTarget(target);
    }

    /**
     * Executes custom logic for the mob's exorcism
     */
    public void doExorcism() {
        BlockPos pos = this.blockPosition();
        if (getExorcismSound() != null) this.level().playSound(null, pos, getExorcismSound(), SoundSource.NEUTRAL, 1.0f, 1.0f);
        for (int i = 0; i < 12; i++) {
            ((ServerLevel) this.level()).sendParticles(JNEParticleTypes.SOUL_CLOUD.get(), this.getRandomX(0.5), this.getRandomY() - 0.25, this.getRandomZ(0.5), 1, 0.0, 0.0, 0.0, 0.0);
        }
        this.discard();
    }

    /**
     * SoundEvent to play during this mob's exorcism
     */
    @Nullable
    public SoundEvent getExorcismSound() {
        return this.getDeathSound();
    }

    private void silverParticle(Level level, RandomSource random, double x, double y, double z) {
        for (int i = 0; i < 9; i++) {
            WorldParticleBuilder.create(JNEParticleTypes.SILVER_GLIMMER.get())
                    .setFullBrightLighting()
                    .setSpinData(SpinParticleData.createRandomDirection(random, 0.0f, 1.0f).setCoefficient(0.7f).setEasing(Easing.SINE_IN).build())
                    .setScaleData(GenericParticleData.create(0.165f, 0.0f).setEasing(Easing.BOUNCE_IN_OUT).build())
                    .setTransparencyData(GenericParticleData.create(1).build())
                    .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                    .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                    .setLifetime(random.nextInt(10, 28))
                    .enableNoClip()
                    .setMotion(random.nextDouble() * 0.08, random.nextDouble() * 0.08, random.nextDouble() * 0.08)
                    .spawn(level, x, y, z);
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 47) this.silverParticle(this.level(), this.random, this.getRandomX(0.5), this.getRandomY() - 0.25, this.getRandomZ(0.5));
        super.handleEntityEvent(id);
    }

    public static boolean checkSpawnRules(EntityType<? extends ExorcismMob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getDifficulty() != Difficulty.PEACEFUL && (MobSpawnType.ignoresLightRequirements(spawnType) || isDarkEnoughToSpawn(level, pos, random)) && checkMobSpawnRules(type, level, spawnType, pos, random);
    }
}
