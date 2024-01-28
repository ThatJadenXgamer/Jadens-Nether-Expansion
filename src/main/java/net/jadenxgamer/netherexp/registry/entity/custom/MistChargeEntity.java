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
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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

    // TODO: Add Custom Sounds to Mist Charge
    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        BlockPos blockPos = entity.getBlockPos();
        this.getWorld().playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.BLOCK_MUD_PLACE, SoundCategory.NEUTRAL, 1F, 1F);
        if (this.getWorld() instanceof ServerWorld) {
            GraveCloudEntity graveCloudEntity = ModEntities.MIST_CHARGE_CLOUD.create(this.getWorld());
            if (graveCloudEntity != null) {
                graveCloudEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos));
                graveCloudEntity.setOwner((LivingEntity) this.getOwner());
                this.getWorld().spawnEntity(graveCloudEntity);
            }
        }

        this.discard();
        super.onEntityHit(entityHitResult);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        BlockPos blockPos = blockHitResult.getBlockPos();
        this.getWorld().playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.BLOCK_MUD_PLACE, SoundCategory.NEUTRAL, 1F, 1F);
        if (this.getWorld() instanceof ServerWorld) {
            GraveCloudEntity graveCloudEntity = ModEntities.MIST_CHARGE_CLOUD.create(this.getWorld());
            if (graveCloudEntity != null) {
                graveCloudEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos));
                graveCloudEntity.setOwner((LivingEntity) this.getOwner());
                this.getWorld().spawnEntity(graveCloudEntity);
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