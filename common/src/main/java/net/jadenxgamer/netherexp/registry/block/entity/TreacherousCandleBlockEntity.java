package net.jadenxgamer.netherexp.registry.block.entity;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.block.JNEBlockEntityType;
import net.jadenxgamer.netherexp.registry.block.custom.TreacherousCandleBlock;
import net.jadenxgamer.netherexp.registry.effect.JNEMobEffects;
import net.jadenxgamer.netherexp.registry.entity.ai.AttackTreacherousCandleGoal;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
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
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class TreacherousCandleBlockEntity extends BlockEntity {
    private int fireRewarded = 1;
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
    private int playersNearby = 1;
    private List<EntityType<?>> spawnableMobs = new ArrayList<>();
    private final ServerBossEvent bossEvent;

    public TreacherousCandleBlockEntity(BlockPos pos, BlockState state) {
        super(JNEBlockEntityType.TREACHEROUS_CANDLE.get(), pos, state);
        this.bossEvent = (ServerBossEvent)(new ServerBossEvent(Component.translatable("treacherous_candle.health"), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(false);
    }

    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.fireRewarded = nbt.getInt("FireRewarded"); // Defines how much treacherous fire is given when completed
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
        this.playersNearby = nbt.getInt("PlayersNearby"); // When lit checks for all players in a radius to determine difficulty and rewards

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
        nbt.putInt("FireRewarded", this.fireRewarded);
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
        nbt.putInt("PlayersNearby", this.playersNearby);

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
        boolean isCompleted = state.getValue(TreacherousCandleBlock.COMPLETED);
        boolean isLit = state.getValue(TreacherousCandleBlock.LIT);

        // When completed all players are removed from the bossEvent and starts the cooldown
        if (isCompleted) {
            blockEntity.completionCooldown--;
            clearBossBarPlayers(blockEntity);

            if (blockEntity.completionCooldown <= 0) {
                resetValues(blockEntity);
                level.setBlock(pos, state.setValue(TreacherousCandleBlock.COMPLETED, false).setValue(TreacherousCandleBlock.LIT, false), 2);
                level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.5f, 1.0f);
            }
            return;
        }

        // Handle lit state
        if (isLit) {
            updateHealth(blockEntity);
            List<ServerPlayer> playersInRadius = getPlayersInRadius(level, pos);
            updateBossBarPlayers(blockEntity, playersInRadius, level);

            blockEntity.currentWaveDelay--;
            if (blockEntity.currentWave <= blockEntity.maximumWaves) {
                prepareWaves(level, pos, blockEntity);
            } else if (blockEntity.currentWaveDelay <= 0) {
                dropFire(level, pos.above(), blockEntity);
                level.playSound(null, pos, JNESoundEvents.TREACHEROUS_CANDLE_VICTORY.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                level.setBlock(pos, state.cycle(TreacherousCandleBlock.COMPLETED), 2);
            }
            return;
        }

        // Removes all players from bossEvent if the candle is not lit
        clearBossBarPlayers(blockEntity);
    }

    private static void clearBossBarPlayers(TreacherousCandleBlockEntity blockEntity) {
        // We make a list before clearing the players otherwise it causes a ConcurrentModificationException
        List<ServerPlayer> playersToRemove = new ArrayList<>(blockEntity.bossEvent.getPlayers());
        for (ServerPlayer player : playersToRemove) {
            blockEntity.bossEvent.removePlayer(player);
        }
    }

    private static void updateHealth(TreacherousCandleBlockEntity blockEntity) {
        blockEntity.bossEvent.setProgress((float) blockEntity.health / blockEntity.maximumHealth);
    }

    private static List<ServerPlayer> getPlayersInRadius(Level level, BlockPos pos) {
        return level.getEntitiesOfClass(ServerPlayer.class, new AABB(pos).inflate(16.0, 16.0, 16.0));
    }

    private static void updateBossBarPlayers(TreacherousCandleBlockEntity blockEntity, List<ServerPlayer> playersInRadius, Level level) {
        for (ServerPlayer player : playersInRadius) {
            if (player.isAlive() && player.level() == level) {
                player.addEffect(new MobEffectInstance(JNEMobEffects.BETRAYED.get(), 200, 0));
                if (!blockEntity.bossEvent.getPlayers().contains(player)) {
                    blockEntity.bossEvent.addPlayer(player);
                }
            }
        }

        // Remove players which are no longer in radius, dead, or in another dimension
        List<ServerPlayer> playersToRemove = new ArrayList<>(blockEntity.bossEvent.getPlayers());
        for (ServerPlayer player : playersToRemove) {
            if (!playersInRadius.contains(player) || !player.isAlive() || player.level() != level) {
                blockEntity.bossEvent.removePlayer(player);
            }
        }
    }

    private static void prepareWaves(Level level, BlockPos pos, TreacherousCandleBlockEntity blockEntity) {
        if (blockEntity.currentWaveDelay == 40) {
            level.playSound(null, pos, SoundEvents.ELDER_GUARDIAN_CURSE, SoundSource.BLOCKS, 0.50f, 0.60f);
        }
        // for some reason causes a weird desync if in the (<= 0) check
        if (blockEntity.currentWaveDelay == 1) {
            level.addParticle(JNEParticleTypes.CANDLE_BURST.get(), pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0.0, 0.0, 0.0);
        }
        if (blockEntity.currentWaveDelay <= 0) {
            blockEntity.currentWave++;
            blockEntity.spawnWave(level, pos);
            blockEntity.mobsPerWave += blockEntity.increaseInMobsPerWave;
            blockEntity.currentWaveDelay = blockEntity.maximumWaveDelay;
        }
    }

    private static void dropFire(Level level, BlockPos pos, TreacherousCandleBlockEntity blockEntity) {
        ItemEntity item = new ItemEntity(level, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, new ItemStack(JNEItems.TREACHEROUS_FLAME.get(), blockEntity.fireRewarded * blockEntity.playersNearby));
        item.setDeltaMovement(0.0,0.2,0.0);
        item.setGlowingTag(true);
        level.addFreshEntity(item);
        level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
    }

    public static void resetValues(TreacherousCandleBlockEntity blockEntity) {
        blockEntity.completionCooldown = JNEConfigs.TREACHEROUS_CANDLE_COMPLETION_COOLDOWN.get() * 20;
        blockEntity.currentWave = 1;
        blockEntity.health = blockEntity.maximumHealth;
        blockEntity.mobsPerWave = blockEntity.resetMobsPerWave;
        blockEntity.currentWaveDelay = 80;
        blockEntity.playersNearby = 1;
    }

    public static void findAllNearbyPlayers(TreacherousCandleBlockEntity blockEntity, BlockPos pos, Level level) {
        if (level != null) {
            List<Player> nearbyPlayers = level.getEntitiesOfClass(Player.class, (new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ())).inflate(16.0, 16.0, 16.0));
            blockEntity.playersNearby = nearbyPlayers.size();
        }
    }

    private void spawnWave(Level level, BlockPos pos) {
        RandomSource random = level.random;
        if (!spawnableMobs.isEmpty()) {
            int bonusSpawns = this.playersNearby > 1 ? playersNearby : 0;
            for (int i = 0; i < this.mobsPerWave + bonusSpawns; i++) {
                BlockPos spawnPos = findValidSpawnPosition(level, pos, random);

                EntityType<?> entityType = spawnableMobs.get(random.nextInt(spawnableMobs.size()));
                Mob mob = (Mob) entityType.create(level);
                if (mob != null) {
                    level.playSound(null, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.3f, 1.0f);
                    if (level instanceof ServerLevel serverLevel) {
                        mob.setPos(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
                        mob.finalizeSpawn(serverLevel, level.getCurrentDifficultyAt(pos), MobSpawnType.MOB_SUMMONED, null, null);
                        if (mob instanceof Slime slime) {
                            slime.setSize(2, true);
                        }
                        if (mob instanceof PathfinderMob pathfinder && !pathfinder.getType().is(JNETags.EntityTypes.IGNORES_TREACHEROUS_CANDLE)) {
                            mob.targetSelector.addGoal(2, new AttackTreacherousCandleGoal(pathfinder, 32));
                        }
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
        BlockPos currentPos = new BlockPos((int)x, (int)y, (int)z);
        // Checks if the current position is a valid one, otherwise moves the entity up if space is available
        while (retries < 10) {
            if (level.getBlockState(currentPos).isAir()) {
                return currentPos;
            }
            else {
                y++;
                currentPos = new BlockPos((int)x, (int)y, (int)z);
                retries++;
            }
        }
        return currentPos;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        assert level != null;
        if (!level.isClientSide() && this.bossEvent != null) {
            List<ServerPlayer> playersToRemove = new ArrayList<>(this.bossEvent.getPlayers());
            for (ServerPlayer player : playersToRemove) {
                this.bossEvent.removePlayer(player);
            }
        }
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
