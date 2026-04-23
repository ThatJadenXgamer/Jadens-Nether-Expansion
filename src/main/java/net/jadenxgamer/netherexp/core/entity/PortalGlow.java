package net.jadenxgamer.netherexp.core.entity;

import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class PortalGlow extends Entity {
    private static final EntityDataAccessor<Integer> DATA_WIDTH = SynchedEntityData.defineId(PortalGlow.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_HEIGHT = SynchedEntityData.defineId(PortalGlow.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<String> DATA_AXIS = SynchedEntityData.defineId(PortalGlow.class, EntityDataSerializers.STRING);

    public PortalGlow(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_WIDTH, 1);
        builder.define(DATA_HEIGHT, 1);
        builder.define(DATA_AXIS, Direction.Axis.X.name());
    }

    public void setPortalDimensions(int width, int height, Direction.Axis axis) {
        this.entityData.set(DATA_WIDTH, width);
        this.entityData.set(DATA_HEIGHT, height);
        this.entityData.set(DATA_AXIS, axis.name());
        updateBoundingBox();
    }

    private void updateBoundingBox() {
        this.setBoundingBox(makeBoundingBox());
    }

    @Override
    protected AABB makeBoundingBox() {
        int width = this.entityData.get(DATA_WIDTH);
        int height = this.entityData.get(DATA_HEIGHT);
        Direction.Axis axis = Direction.Axis.valueOf(this.entityData.get(DATA_AXIS));

        double halfWidth = width / 2.0;
        double halfHeight = height / 2.0;
        double depth = 0.5;

        double minX, minY, minZ, maxX, maxY, maxZ;

        if (axis == Direction.Axis.X) {
            minX = this.getX() - halfWidth;
            maxX = this.getX() + halfWidth;
            minZ = this.getZ() - depth;
            maxZ = this.getZ() + depth;
        } else {
            minX = this.getX() - depth;
            maxX = this.getX() + depth;
            minZ = this.getZ() - halfWidth;
            maxZ = this.getZ() + halfWidth;
        }
        minY = this.getY() - halfHeight;
        maxY = this.getY() + halfHeight;

        return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (key.equals(DATA_WIDTH) || key.equals(DATA_HEIGHT) || key.equals(DATA_AXIS)) {
            updateBoundingBox();
        }
    }

    @Override
    public void tick() {
        if (this.level().isClientSide) return;

        int width = this.entityData.get(DATA_WIDTH);
        int height = this.entityData.get(DATA_HEIGHT);
        Direction.Axis axis = Direction.Axis.valueOf(this.entityData.get(DATA_AXIS));

        BlockPos topLeft = getTopLeftBlockPos(axis, width, height);

        if (!(this.level().getBlockState(topLeft).getBlock() instanceof NetherPortalBlock)) this.discard();
    }

    private @NotNull BlockPos getTopLeftBlockPos(Direction.Axis axis, int width, int height) {
        double topLeftX, topLeftY, topLeftZ;
        topLeftY = this.getY() + (height - 1) / 2.0;
        if (axis == Direction.Axis.X) {
            topLeftX = this.getX() - (width - 1) / 2.0;
            topLeftZ = this.getZ();
        } else {
            topLeftX = this.getX();
            topLeftZ = this.getZ() - (width - 1) / 2.0;
        }
        return BlockPos.containing(topLeftX, topLeftY, topLeftZ);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        this.entityData.set(DATA_WIDTH, compound.getInt("PortalWidth"));
        this.entityData.set(DATA_HEIGHT, compound.getInt("PortalHeight"));
        this.entityData.set(DATA_AXIS, compound.getString("PortalAxis"));
        updateBoundingBox();
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("PortalWidth", this.entityData.get(DATA_WIDTH));
        compound.putInt("PortalHeight", this.entityData.get(DATA_HEIGHT));
        compound.putString("PortalAxis", this.entityData.get(DATA_AXIS));
    }

    @Override
    public PushReaction getPistonPushReaction() {
        return PushReaction.IGNORE;
    }

    @Override
    public boolean isIgnoringBlockTriggers() {
        return true;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    public static void spawnForPortal(Level level, BlockPos portalBlockPos) {
        if (level.isClientSide) return;

        var state = level.getBlockState(portalBlockPos);
        if (!(state.getBlock() instanceof NetherPortalBlock)) return;

        Direction.Axis axis = state.getValue(NetherPortalBlock.AXIS);
        int minX = portalBlockPos.getX();
        int minY = portalBlockPos.getY();
        int minZ = portalBlockPos.getZ();
        int maxX = minX;
        int maxY = minY;
        int maxZ = minZ;

        if (axis == Direction.Axis.X) {
            while (level.getBlockState(new BlockPos(minX - 1, minY, minZ)).getBlock() instanceof NetherPortalBlock) minX--;
            while (level.getBlockState(new BlockPos(maxX + 1, minY, minZ)).getBlock() instanceof NetherPortalBlock) maxX++;
        } else {
            while (level.getBlockState(new BlockPos(minX, minY, minZ - 1)).getBlock() instanceof NetherPortalBlock) minZ--;
            while (level.getBlockState(new BlockPos(minX, minY, maxZ + 1)).getBlock() instanceof NetherPortalBlock) maxZ++;
        }

        while (level.getBlockState(new BlockPos(minX, maxY + 1, minZ)).getBlock() instanceof NetherPortalBlock) maxY++;
        while (level.getBlockState(new BlockPos(minX, minY - 1, minZ)).getBlock() instanceof NetherPortalBlock) minY--;

        int width = (axis == Direction.Axis.X) ? (maxX - minX + 1) : (maxZ - minZ + 1);
        int height = maxY - minY + 1;

        double centerX, centerY, centerZ;
        if (axis == Direction.Axis.X) {
            centerX = minX + (width - 1) / 2.0 + 0.5;
            centerY = minY + (height - 1) / 2.0 + 0.5;
            centerZ = minZ + 0.5;
        } else {
            centerX = minX + 0.5;
            centerY = minY + (height - 1) / 2.0 + 0.5;
            centerZ = minZ + (width - 1) / 2.0 + 0.5;
        }

        BlockPos topLeft = new BlockPos(minX, maxY, minZ);
        if (!level.getEntitiesOfClass(PortalGlow.class, new AABB(topLeft).inflate(0.5)).isEmpty()) return;

        PortalGlow glow = new PortalGlow(JNEEntityType.PORTAL_GLOW.get(), level);
        glow.setPos(centerX, centerY, centerZ);
        glow.setPortalDimensions(width, height, axis);
        level.addFreshEntity(glow);
    }

    public int getPortalWidth() {
        return this.entityData.get(DATA_WIDTH);
    }

    public int getPortalHeight() {
        return this.entityData.get(DATA_HEIGHT);
    }

    public Direction.Axis getPortalAxis() {
        return Direction.Axis.valueOf(this.entityData.get(DATA_AXIS));
    }
}