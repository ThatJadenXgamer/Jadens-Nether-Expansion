package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class MistCharge extends ThrowableItemProjectile {

    public final AnimationState defaultAnimationState = new AnimationState();

    public MistCharge(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public MistCharge(LivingEntity livingEntity, Level level) {
        super(JNEEntityType.MIST_CHARGE.get(), livingEntity, level);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return JNEItems.MIST_CHARGE.get();
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        BlockPos blockPos = entity.getOnPos();
        this.level().playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.MUD_PLACE, SoundSource.NEUTRAL, 1F, 1F);
        if (this.level() instanceof ServerLevel) {
            GraveCloud graveCloud = JNEEntityType.GRAVE_CLOUD.get().create(this.level());
            if (graveCloud != null) {
                graveCloud.moveTo(Vec3.atBottomCenterOf(blockPos));
                graveCloud.setOwnerUUID((LivingEntity) this.getOwner());
                this.level().addFreshEntity(graveCloud);
            }
        }

        this.discard();
        super.onHitEntity(entityHitResult);
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        BlockPos blockPos = blockHitResult.getBlockPos();
        this.level().playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.MUD_PLACE, SoundSource.NEUTRAL, 1F, 1F);
        if (this.level() instanceof ServerLevel) {
            GraveCloud graveCloud = JNEEntityType.GRAVE_CLOUD.get().create(this.level());
            if (graveCloud != null) {
                graveCloud.moveTo(Vec3.atBottomCenterOf(blockPos));
                graveCloud.setOwnerUUID((LivingEntity) this.getOwner());
                this.level().addFreshEntity(graveCloud);
            }
        }

        this.discard();
        super.onHitBlock(blockHitResult);
    }

    @Override
    public void tick() {
        super.tick();
        defaultAnimationState.startIfStopped(this.tickCount);
        this.spawnParticles();
    }

    private void spawnParticles() {
        for (int j = 0; j < 1; ++j) {
            this.level().addParticle(JNEParticleTypes.GRASP_MIST.get(), this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0, 0, 0);
        }
    }

    @Override
    protected float getGravity() {
        return 0.05f;
    }
}
