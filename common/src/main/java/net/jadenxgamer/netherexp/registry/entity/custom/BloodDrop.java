package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class BloodDrop extends AbstractArrow {
    private int heals = 1;

    public BloodDrop(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public BloodDrop(double d, double e, double f, Level level) {
        this(JNEEntityType.BLOOD_DROP.get(), level);
        this.setPos(d, e, f);
    }

    public BloodDrop(Level level, LivingEntity owner) {
        super(JNEEntityType.BLOOD_DROP.get(), owner, level);
    }

    public BloodDrop(double d, double e, double f, Level level, LivingEntity owner, int heals) {
        super(JNEEntityType.BLOOD_DROP.get(), owner, level);
        this.setPos(d, e, f);
        this.heals = heals;
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public void tick() {
        super.tick();
        if (random.nextInt(3) == 0) {
            this.level().addParticle(JNEParticleTypes.FALLING_BLOOD.get(), this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }
        if (!this.level().isClientSide && tickCount > 600) {
            this.playSound(getDefaultHitGroundSoundEvent(), 0.5f, 1.0f);
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof Player player) {
            player.heal(heals);
            for (int i = 0; i < 4; i++) {
                this.level().addParticle(JNEParticleTypes.FALLING_BLOOD.get(), player.getRandomX(0.5), player.getRandomY(), player.getRandomZ(0.5), 0.0, 0.0, 0.0);
            }
                if (!this.level().isClientSide) {
                this.playSound(getDefaultHitGroundSoundEvent(), 0.3f, 1.0f);
                this.discard();
            }
        }
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.HONEYCOMB_WAX_ON;
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        this.level().addParticle(JNEParticleTypes.FALLING_BLOOD.get(), this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        if (!this.level().isClientSide) {
            this.playSound(getDefaultHitGroundSoundEvent(), 0.3f, 1.0f);
            this.discard();
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("Heals", this.heals);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.heals = nbt.getInt("Heals");
    }
}
