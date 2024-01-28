package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.custom.LavaBucketable;
import net.jadenxgamer.netherexp.registry.item.ModItems;
import net.jadenxgamer.netherexp.registry.sound.ModSoundEvents;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(MagmaCubeEntity.class)
public abstract class MagmaCubeEntityMixin extends SlimeEntity implements LavaBucketable {
    @Shadow public abstract void setSize(int size, boolean heal);

    @Unique
    private static final TrackedData<Boolean> FROM_BUCKET;

    public MagmaCubeEntityMixin(EntityType<? extends SlimeEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean cannotDespawn() {
        return super.cannotDespawn() || this.isFromBucket();
    }


    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FROM_BUCKET, false);
    }

    @SuppressWarnings("all")
    @Override
    public boolean isFromBucket() {
        return this.dataTracker.get(FROM_BUCKET);
    }

    @SuppressWarnings("all")
    @Override
    public void setFromBucket(boolean fromBucket) {
        this.dataTracker.set(FROM_BUCKET, fromBucket);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("FromBucket", this.isFromBucket());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setFromBucket(nbt.getBoolean("FromBucket"));
    }

    @SuppressWarnings("all")
    public void copyDataToStack(ItemStack stack) {
        LavaBucketable.copyDataToStack(this, stack);
    }

    @SuppressWarnings("all")
    public void copyDataFromNbt(NbtCompound nbt) {
        LavaBucketable.copyDataFromNbt(this, nbt);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (this.isSmall() && NetherExp.getConfig().entities.magmaCubeConfigs.bucketable_small_cubes) {
            LavaBucketable.tryBucket(player, hand, this);
        }
        return super.interactMob(player, hand);
    }

    @SuppressWarnings("all")
    @Override
    public ItemStack getBucketItem() {
        return ModItems.MAGMA_CUBE_BUCKET.getDefaultStack();
    }

    @SuppressWarnings("all")
    @Override
    public SoundEvent getBucketFillSound() {
        return ModSoundEvents.ITEM_BUCKET_FILL_MAGMA_CUBE;
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if (spawnReason == SpawnReason.BUCKET) {
            return entityData;
        }
        else {
            this.setSize(0, false);
            return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        }
    }

    static {
        FROM_BUCKET = DataTracker.registerData(MagmaCubeEntityMixin.class, TrackedDataHandlerRegistry.BOOLEAN);
    }
}
