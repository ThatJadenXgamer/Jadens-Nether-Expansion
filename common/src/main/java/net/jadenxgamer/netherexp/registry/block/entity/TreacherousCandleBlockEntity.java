package net.jadenxgamer.netherexp.registry.block.entity;

import net.jadenxgamer.netherexp.registry.block.JNEBlockEntityType;
import net.jadenxgamer.netherexp.registry.block.custom.TreacherousCandleBlock;
import net.jadenxgamer.netherexp.registry.effect.JNEMobEffects;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class TreacherousCandleBlockEntity extends BlockEntity {
    private int maximumWaves = 5;
    private int currentWave = 1;
    private int maximumWaveDelay = 650;
    private int currentWaveDelay = 80;
    private int spawnRadius = 8;
    private int mobsPerWave = 4;
    private int resetMobsPerWave = 4;
    private int increaseInMobsPerWave = 2;
    private int health = 20;
    private int completionCooldown = 72000;
    private List<EntityType<?>> spawnableMobs = new ArrayList<>();

    public TreacherousCandleBlockEntity(BlockPos pos, BlockState state) {
        super(JNEBlockEntityType.TREACHEROUS_CANDLE.get(), pos, state);
    }

    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.maximumWaves = nbt.getInt("MaximumWaves"); // Maximum Number of Waves
        this.currentWave = nbt.getInt("CurrentWave"); // Tracks current Wave's number
        this.maximumWaveDelay = nbt.getInt("MaximumWaveDelay"); // Maximum Time Delay between Waves
        this.currentWaveDelay = nbt.getInt("CurrentWaveDelay"); // Tracks current Time Delay between Waves
        this.spawnRadius = nbt.getInt("SpawnRadius"); // Spawn Radius of Mobs
        this.mobsPerWave = nbt.getInt("MobsPerWave"); // Number of Mobs Spawned Per Wave
        this.resetMobsPerWave = nbt.getInt("ResetMobsPerWave"); // Resets MobsPerWave once it's been defeated
        this.increaseInMobsPerWave = nbt.getInt("IncreaseInMobsPerWave"); // Gradual increase in mob spawns per wave
        this.health = nbt.getInt("Health"); // Current Health of the Candle
        this.completionCooldown = nbt.getInt("CompletionCooldown"); // Once completed tracks the candle's cooldown to reset it

        // List for Spawnable Mobs
        spawnableMobs.clear();
        ListTag mobsListTag = nbt.getList("SpawnableMobs", 8);
        for (int i = 0; i < mobsListTag.size(); i++) {
            String mobTypeString = mobsListTag.getString(i);
            EntityType.byString(mobTypeString).ifPresent(entityType -> spawnableMobs.add(entityType));
        }
    }

    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("MaximumWaves", this.maximumWaves);
        nbt.putInt("CurrentWave", this.currentWave);
        nbt.putInt("MaximumWaveDelay", this.maximumWaveDelay);
        nbt.putInt("CurrentWaveDelay", this.currentWaveDelay);
        nbt.putInt("MobsPerWave", this.mobsPerWave);
        nbt.putInt("ResetMobsPerWave", this.resetMobsPerWave);
        nbt.putInt("IncreaseInMobsPerWave", this.increaseInMobsPerWave);
        nbt.putInt("SpawnRadius", this.spawnRadius);
        nbt.putInt("Health", this.health);
        nbt.putInt("CompletionCooldown", this.completionCooldown);

        ListTag mobsListTag = new ListTag();
        for (EntityType<?> entityType : spawnableMobs) {
            mobsListTag.add(StringTag.valueOf(EntityType.getKey(entityType).toString()));
        }
        nbt.put("SpawnableMobs", mobsListTag);
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TreacherousCandleBlockEntity blockEntity) {
        if (state.getValue(TreacherousCandleBlock.COMPLETED)) {
            --blockEntity.completionCooldown;
            if (blockEntity.completionCooldown == 20) {
                level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.PLAYERS, 0.5f, 1.0f);
            }
            if (blockEntity.completionCooldown <= 0) {
                resetValues(blockEntity);
                level.setBlock(pos, state.setValue(TreacherousCandleBlock.COMPLETED, false).setValue(TreacherousCandleBlock.LIT, false), 2);
                level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.5f, 1.0f);
            }
        }
        else if (state.getValue(TreacherousCandleBlock.LIT)) {
            if (blockEntity.currentWave <= blockEntity.maximumWaves) {
                --blockEntity.currentWaveDelay;
                for (Player player : level.getEntitiesOfClass(Player.class, (new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ())).inflate(16.0, 16.0, 16.0))) {
                    player.addEffect(new MobEffectInstance(JNEMobEffects.BETRAYED.get(), 80, 0));
                }
                if (blockEntity.currentWaveDelay <= 0) {
                    blockEntity.currentWave++;
                    blockEntity.spawnWave(level, pos);
                    blockEntity.mobsPerWave = blockEntity.mobsPerWave + blockEntity.increaseInMobsPerWave;
                    blockEntity.currentWaveDelay = blockEntity.maximumWaveDelay;
                }
            }
            else {
                blockEntity.mobsPerWave = blockEntity.resetMobsPerWave;
                level.setBlock(pos, state.cycle(TreacherousCandleBlock.COMPLETED), 2);
            }
        }
    }

    public static void resetValues(TreacherousCandleBlockEntity blockEntity) {
        blockEntity.completionCooldown = 72000;
        blockEntity.currentWave = 1;
        blockEntity.health = 20;
        blockEntity.mobsPerWave = blockEntity.resetMobsPerWave;
        blockEntity.currentWaveDelay = 80;
    }

    private void spawnWave(Level level, BlockPos pos) {
        RandomSource random = level.random;
        if (!spawnableMobs.isEmpty()) {
            for (int i = 0; i < mobsPerWave; i++) {
                BlockPos spawnPos = findValidSpawnPosition(level, pos, random);
                if (spawnPos != null) {
                    spawnMob(level, spawnPos, random);
                }
            }
        }
    }

    private BlockPos findValidSpawnPosition(Level level, BlockPos pos, RandomSource random) {
        // Finds a random position within the specified SpawnRadius
        double x = pos.getZ() + random.nextInt(2 * spawnRadius) - spawnRadius;
        double y = pos.getY();
        double z = pos.getX() + random.nextInt(2 * spawnRadius) - spawnRadius;

        // Checks for a valid spawn position
        for (int retries = 0; retries < 10; retries++) {
            BlockPos currentPos = new BlockPos((int) x, (int) y, (int) z);
            BlockPos belowPos = currentPos.below();

            // If the current position is air and the block below the entity is solid then it'll spawn the mob
            if (level.getBlockState(currentPos).isAir() && level.getBlockState(belowPos).isSolidRender(level, belowPos)) {
                return currentPos;
            }

            /*
             * Otherwise it moves the X and Z position closer to the center where it is guaranteed to almost spawn due to the candle being there
             * and if necessary it'll move the Y position up as well
            */
            x = (x + pos.getX()) / 2;
            z = (z + pos.getZ()) / 2;
            if (!level.getBlockState(currentPos).isAir()) {
                y++;
            }
        }
        return null;
    }

    private void spawnMob(Level level, BlockPos pos, RandomSource random) {
        // picks a random entity from SpawnableMobs and summons it
        EntityType<?> entityType = spawnableMobs.get(random.nextInt(spawnableMobs.size()));
        Mob mob = (Mob) entityType.create(level);
        if (mob != null) {
            level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.3f, 1.0f);
            for (int p = 0; p < 7; ++p) {
                level.addParticle(JNEParticleTypes.TREACHEROUS_FLAME.get(), mob.getRandomX(0.5), mob.getRandomY() - 0.25, mob.getRandomZ(0.5), (random.nextDouble() - 0.5) * 2.0, random.nextDouble(), (random.nextDouble() - 0.5) * 2.0);
            }
            if (level instanceof ServerLevel serverLevel) {
                mob.setPos(pos.getX(), pos.getY(), pos.getZ());
                mob.finalizeSpawn(serverLevel, level.getCurrentDifficultyAt(pos), MobSpawnType.MOB_SUMMONED, null, null);
                level.addFreshEntity(mob);
            }
        }
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
