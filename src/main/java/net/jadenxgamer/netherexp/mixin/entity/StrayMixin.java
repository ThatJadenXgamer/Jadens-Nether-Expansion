package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.registry.entity.variants.StrayType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Stray.class)
public abstract class StrayMixin extends AbstractSkeleton implements VariantHolder<StrayType> {
    //TODO: clean up this atrocious mixin, this is honestly vile

    private static final EntityDataAccessor<Integer> DATA_TYPE_ID = SynchedEntityData.defineId(Stray.class, EntityDataSerializers.INT);;

    protected StrayMixin(EntityType<? extends AbstractSkeleton> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(
            method = "checkStraySpawnRules",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private static void netherexp$checkStraySpawnRules(EntityType<Stray> pStray, ServerLevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom, CallbackInfoReturnable<Boolean> cir) {
        // lets strays spawn normally in dimensions with ceilings
        if (pLevel.dimensionType().hasCeiling()) {
            cir.setReturnValue(checkMonsterSpawnRules(pStray, pLevel, pSpawnType, pPos, pRandom));
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        Holder<Biome> holder = pLevel.getBiome(this.blockPosition());
        StrayType type = StrayType.byBiome(holder);
        setVariant(type);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        this.setVariant(StrayType.byName(pCompound.getString("Type")));
        super.readAdditionalSaveData(pCompound);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putString("Type", this.getVariant().getSerializedName());
        super.addAdditionalSaveData(pCompound);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_TYPE_ID, 0);
    }

    @Override
    public StrayType getVariant() {
        return StrayType.byId(this.entityData.get(DATA_TYPE_ID));
    }

    @Override
    public void setVariant(StrayType strayType) {
        this.entityData.set(DATA_TYPE_ID, strayType.getId());
    }
}
