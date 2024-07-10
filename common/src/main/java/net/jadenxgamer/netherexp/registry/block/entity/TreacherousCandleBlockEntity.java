package net.jadenxgamer.netherexp.registry.block.entity;

import net.jadenxgamer.netherexp.registry.block.JNEBlockEntityType;
import net.jadenxgamer.netherexp.registry.block.custom.TreacherousCandleBlock;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class TreacherousCandleBlockEntity extends BlockEntity {
    private int maximumWaves = 5;
    private int currentWave = 1;
    private int maximumWaveDelay = 1800;
    private int currentWaveDelay = 80;
    private int spawnRadius = 6;
    private int mobsPerWave = 4;
    private int resetMobsPerWave = 4;
    private int increaseInMobsPerWave = 2;
    private int health = 20;
    private List<EntityType<?>> spawnableMobs = new ArrayList<>();

    public TreacherousCandleBlockEntity(BlockPos pos, BlockState state) {
        super(JNEBlockEntityType.TREACHEROUS_CANDLE.get(), pos, state);
    }

    public void load(CompoundTag nbt) {
        super.load(nbt);
        // Maximum Number of Waves
        this.maximumWaves = nbt.getInt("MaximumWaves");
        // Tracks current Wave's number
        this.currentWave = nbt.getInt("CurrentWave");
        // Maximum Time Delay between Waves
        this.maximumWaveDelay = nbt.getInt("MaximumWaveDelay");
        // Tracks current Time Delay between Waves
        this.currentWaveDelay = nbt.getInt("CurrentWaveDelay");
        // Number of Mobs Spawned Per Wave
        this.mobsPerWave = nbt.getInt("MobsPerWave");
        // Resets MobsPerWave once it's been defeated
        this.resetMobsPerWave = nbt.getInt("ResetMobsPerWave");
        // Gradual increase in mob spawns per wave
        this.increaseInMobsPerWave = nbt.getInt("IncreaseInMobsPerWave");
        // Spawn Radius of Mobs
        this.spawnRadius = nbt.getInt("SpawnRadius");
        // Health of the Candle
        this.health = nbt.getInt("Health");

        // List of Spawnable Mobs
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

    public static void clientTick(Level level, BlockPos pos, BlockState state, TreacherousCandleBlockEntity blockEntity) {

    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, TreacherousCandleBlockEntity blockEntity) {
        if (state.getValue(TreacherousCandleBlock.LIT) && !state.getValue(TreacherousCandleBlock.COMPLETED)) {
            if (blockEntity.currentWave <= blockEntity.maximumWaves) {
                --blockEntity.currentWaveDelay;
                if (blockEntity.currentWaveDelay <= 0) {
                    blockEntity.currentWave++;
                    blockEntity.spawnWave((ServerLevel) level, pos);
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

    private void spawnWave(ServerLevel level, BlockPos pos) {
        RandomSource random = level.random;
        if (!spawnableMobs.isEmpty()) {
            for (int i = 0; i < this.mobsPerWave; i++) {
                double distance = spawnRadius * Math.sqrt(random.nextDouble());

                // Makes sure mobs spawn in a donut shape around the candle
                if (distance < (double) spawnRadius / 2) {
                    distance += (double) spawnRadius / 2;
                }

                double x = pos.getX() + distance * Math.cos(random.nextDouble() * 2 * Math.PI);
                double z = pos.getZ() + distance * Math.sin(random.nextDouble() * 2 * Math.PI);
                double y = pos.getY();

                EntityType<?> entityType = spawnableMobs.get(random.nextInt(spawnableMobs.size()));
                Mob mob = (Mob) entityType.create(level);
                if (mob != null) {
                    for (int p = 0; p < 7; ++p) {
                        level.addParticle(JNEParticleTypes.TREACHEROUS_FLAME.get(), mob.getRandomX(0.5), mob.getRandomY() - 0.25, mob.getRandomZ(0.5), (random.nextDouble() - 0.5) * 2.0, random.nextDouble(), (random.nextDouble() - 0.5) * 2.0);
                    }
                    level.playSound(null, x, y, z, SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.3f, 1.0f);
                    mob.setPos(x, y, z);
                    mob.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.MOB_SUMMONED, null, null);
                    level.addFreshEntity(mob);
                }
            }
        }
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
