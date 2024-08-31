package net.jadenxgamer.netherexp.registry.block.entity;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.block.JNEBlockEntityType;
import net.jadenxgamer.netherexp.registry.block.custom.TreacherousCandleBlock;
import net.jadenxgamer.netherexp.registry.effect.JNEMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
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
    private int maximumHealth = 20;
    private int completionCooldown = JNEConfigs.TREACHEROUS_CANDLE_COMPLETION_COOLDOWN.get() * 20;
    private List<EntityType<?>> spawnableMobs = new ArrayList<>();
    private final ServerBossEvent bossEvent;

    public TreacherousCandleBlockEntity(BlockPos pos, BlockState state) {
        super(JNEBlockEntityType.TREACHEROUS_CANDLE.get(), pos, state);
        this.bossEvent = (ServerBossEvent)(new ServerBossEvent(Component.translatable("treacherous_candle.health"), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(false);
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
        this.maximumHealth = nbt.getInt("MaximumHealth"); // Maximum Health of the Candle
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
        nbt.putInt("MaximumHealth", this.maximumHealth);
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
            for (ServerPlayer player : blockEntity.bossEvent.getPlayers()) {
                blockEntity.bossEvent.removePlayer(player);
            }
            if (blockEntity.completionCooldown <= 0) {
                resetValues(blockEntity);
                level.setBlock(pos, state.setValue(TreacherousCandleBlock.COMPLETED, false).setValue(TreacherousCandleBlock.LIT, false), 2);
                level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.5f, 1.0f);
            }
        }
        else if (state.getValue(TreacherousCandleBlock.LIT)) {
            blockEntity.bossEvent.setProgress((float) blockEntity.health / blockEntity.maximumHealth);
            if (blockEntity.currentWave <= blockEntity.maximumWaves) {
                --blockEntity.currentWaveDelay;
                for (Player player : level.getEntitiesOfClass(Player.class, (new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ())).inflate(16.0, 16.0, 16.0))) {
                    player.addEffect(new MobEffectInstance(JNEMobEffects.BETRAYED.get(), 200, 0));
                    if (player instanceof ServerPlayer serverPlayer) {
                        blockEntity.bossEvent.addPlayer(serverPlayer);
                    }
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
        else if (!state.getValue(TreacherousCandleBlock.LIT)) {
            for (ServerPlayer player : blockEntity.bossEvent.getPlayers()) {
                blockEntity.bossEvent.removePlayer(player);
            }
        }
    }

    public static void resetValues(TreacherousCandleBlockEntity blockEntity) {
        blockEntity.completionCooldown = JNEConfigs.TREACHEROUS_CANDLE_COMPLETION_COOLDOWN.get() * 20;
        blockEntity.currentWave = 1;
        blockEntity.health = blockEntity.maximumHealth;
        blockEntity.mobsPerWave = blockEntity.resetMobsPerWave;
        blockEntity.currentWaveDelay = 80;
    }

    private void spawnWave(Level level, BlockPos pos) {
        RandomSource random = level.random;
        if (!spawnableMobs.isEmpty()) {
            for (int i = 0; i < this.mobsPerWave; i++) {
                BlockPos spawnPos = findValidSpawnPosition(level, pos, random);

                EntityType<?> entityType = spawnableMobs.get(random.nextInt(spawnableMobs.size()));
                Mob mob = (Mob) entityType.create(level);
                if (mob != null) {
                    level.playSound(null, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.3f, 1.0f);
                    if (level instanceof ServerLevel serverLevel) {
                        mob.setPos(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
                        mob.finalizeSpawn(serverLevel, level.getCurrentDifficultyAt(pos), MobSpawnType.MOB_SUMMONED, null, null);
                        level.addFreshEntity(mob);
                    }
                }
            }
        }
    }

    private BlockPos findValidSpawnPosition(Level level, BlockPos pos, RandomSource random) {
        double x = pos.getX() + random.nextInt(spawnRadius);
        double y = pos.getY();
        double z = pos.getZ() + random.nextInt(spawnRadius);
        int retries = 0;

        // Checks if the current position is a valid one, otherwise moves the entity up
        while (retries < 10) {
            BlockPos currentPos = new BlockPos((int)x, (int)y, (int)z);
            if (level.getBlockState(currentPos).isAir() && level.getBlockState(currentPos.below()).isSolid() || level.getBlockState(currentPos.below().below()).isSolid()) {
                return currentPos;
            }
            else {
                y++;
                retries++;
            }
        }
        return pos.above();
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        assert level != null;
        if (!level.isClientSide() && this.bossEvent != null) {
            for (ServerPlayer player : this.bossEvent.getPlayers()) {
                this.bossEvent.removePlayer(player);
            }
        }
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
