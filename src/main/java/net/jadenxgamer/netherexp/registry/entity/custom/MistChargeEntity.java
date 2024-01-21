package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.registry.entity.ModEntities;
import net.jadenxgamer.netherexp.registry.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class MistChargeEntity extends ThrownItemEntity implements GeoEntity {

    @SuppressWarnings("all")
    private AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);

    public MistChargeEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public MistChargeEntity(LivingEntity livingEntity, World world) {
        super(ModEntities.MIST_CHARGE, livingEntity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.MIST_CHARGE;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }


    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (this.getWorld() instanceof ServerWorld) {
            BlockPos blockPos = entity.getBlockPos();
            MistChargeCloudEntity mistChargeCloudEntity = ModEntities.MIST_CHARGE_CLOUD.create(this.getWorld());
            if (mistChargeCloudEntity != null) {
                mistChargeCloudEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos));
                mistChargeCloudEntity.setOwner((LivingEntity) this.getOwner());
                this.getWorld().spawnEntity(mistChargeCloudEntity);
            }
        }

        this.discard();
        super.onEntityHit(entityHitResult);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        BlockPos blockPos = blockHitResult.getBlockPos();
        if (this.getWorld() instanceof ServerWorld) {
            MistChargeCloudEntity mistChargeCloudEntity = ModEntities.MIST_CHARGE_CLOUD.create(this.getWorld());
            if (mistChargeCloudEntity != null) {
                mistChargeCloudEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos));
                mistChargeCloudEntity.setOwner((LivingEntity) this.getOwner());
                this.getWorld().spawnEntity(mistChargeCloudEntity);
            }
        }

        this.discard();
        super.onBlockHit(blockHitResult);
    }

    ////////////////////
    // GeckoLib Stuff //
    ////////////////////

    protected static final RawAnimation DEFAULT_ANIM = RawAnimation.begin().thenLoop("animation.mist_charge.default");

    @SuppressWarnings("all")
    private PlayState predicate(AnimationState event) {
        return event.setAndContinue(DEFAULT_ANIM);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller",
                0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }
}
