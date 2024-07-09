package net.jadenxgamer.netherexp.registry.block.entity;

import net.jadenxgamer.netherexp.registry.block.JNEBlockEntityType;
import net.jadenxgamer.netherexp.registry.block.custom.TreacherousCandleBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class TreacherousCandleBlockEntity extends BlockEntity {
    private int maximumWaves = 5;
    private int ticksUntilNextWave = 1200;
    private int currentTime = 1200;
    private int spawnRadius = 10;
    private int mobsPerWave = 3;
    private List<EntityType<?>> spawnableMobs = new ArrayList<>();

    public TreacherousCandleBlockEntity(BlockPos pos, BlockState state) {
        super(JNEBlockEntityType.TREACHEROUS_CANDLE.get(), pos, state);
    }

    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.maximumWaves = nbt.getInt("MaximumWaves");
        this.ticksUntilNextWave = nbt.getInt("TicksUntilNextWave");
        this.currentTime = nbt.getInt("CurrentTime");
        this.mobsPerWave = nbt.getInt("MobsPerWave");
        this.spawnRadius = nbt.getInt("SpawnRadius");

        ListTag mobsListTag = nbt.getList("SpawnableMobs", 8);
        for (int i = 0; i < mobsListTag.size(); i++) {
            String mobTypeString = mobsListTag.getString(i);
            EntityType<?> entityType = EntityType.byString(mobTypeString).orElse(EntityType.ZOMBIE);
            spawnableMobs.add(entityType);
        }
    }

    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("MaximumWaves", this.maximumWaves);
        nbt.putInt("TicksUntilNextWave", this.ticksUntilNextWave);
        nbt.putInt("CurrentTime", this.currentTime);
        nbt.putInt("MobsPerWave", this.mobsPerWave);
        nbt.putInt("SpawnRadius", this.spawnRadius);

        ListTag mobsListTag = new ListTag();
        for (EntityType<?> entityType : spawnableMobs) {
            mobsListTag.add(StringTag.valueOf(EntityType.getKey(entityType).toString()));
        }
        nbt.put("SpawnableMobs", mobsListTag);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, TreacherousCandleBlockEntity blockEntity) {

    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, TreacherousCandleBlockEntity blockEntity) {
        if (state.getValue(TreacherousCandleBlock.LIT) && !state.getValue(TreacherousCandleBlock.COMPLETED)) {
            if (blockEntity.maximumWaves > 0) {
                --blockEntity.currentTime;
                if (blockEntity.currentTime <= 0) {
                    blockEntity.maximumWaves--;
                    blockEntity.spawnWave((ServerLevel) level, pos);
                    blockEntity.currentTime = blockEntity.ticksUntilNextWave;
                }
            }
            else {
                level.setBlock(pos, state.cycle(TreacherousCandleBlock.COMPLETED), 2);
            }
        }
    }

    private void spawnWave(ServerLevel level, BlockPos pos) {
        RandomSource random = level.random;
        if (!spawnableMobs.isEmpty()) {
            for (int i = 0; i < this.mobsPerWave; i++) {
                double x = pos.getX() + random.nextInt(2 * spawnRadius) - spawnRadius;
                double y = pos.getY();
                double z = pos.getZ() + random.nextInt(2 * spawnRadius) - spawnRadius;

                EntityType<?> entityType = spawnableMobs.get(random.nextInt(spawnableMobs.size()));
                Mob mob = (Mob) entityType.create(level);
                if (mob != null) {
                    mob.setPos(x, y, z);
                    level.addFreshEntity(mob);
                }
            }
        }
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public boolean onlyOpCanSetNbt() {
        return true;
    }
}
