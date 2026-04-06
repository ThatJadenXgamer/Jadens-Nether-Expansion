package net.jadenxgamer.netherexp.util;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Marker;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class BlockCrackTracker {

    private static final Map<Level, Map<BlockPos, CrackEntry>> LEVEL_DATA = new HashMap<>();

    private record CrackEntry(double damage, double neededHits, int markerId, int resetTicks) {}

    public static void onBlockHit(Level level, BlockPos pos, BlockState state, double damageDealt) {
        if (level.isClientSide || !(level instanceof ServerLevel serverLevel)) return;
        if (damageDealt <= 0.0F) return;

        float hardness = state.getDestroySpeed(serverLevel, pos);
        if (hardness < 0 || hardness == Float.POSITIVE_INFINITY) return;

        Map<BlockPos, CrackEntry> blockMap = LEVEL_DATA.computeIfAbsent(level, k -> new HashMap<>());
        CrackEntry entry = blockMap.get(pos);

        if (entry == null) {
            // There is no entry at the moment, make a new one
            Marker marker = new Marker(EntityType.MARKER, level);
            marker.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
            level.addFreshEntity(marker);

            entry = new CrackEntry(damageDealt, hardness, marker.getId(), 80);
            blockMap.put(pos, entry);
        } else {
            // There was an entry so we replace it
            entry = new CrackEntry(entry.damage() + damageDealt, entry.neededHits(), entry.markerId(), 80);
            blockMap.put(pos, entry);
        }

        // Check if the block can be broken
        if (entry.damage() >= entry.neededHits()) {
            blockMap.remove(pos);
            Block.dropResources(state, serverLevel, pos, null, null, ItemStack.EMPTY);
            serverLevel.destroyBlock(pos, false);
            sendCrackPacket(serverLevel, pos, entry.markerId(), -1);
            removeMarker(level, entry.markerId());
        } else {
            int stage = (int) ((entry.damage() / entry.neededHits()) * 9);
            stage = Math.min(9, Math.max(0, stage));
            sendCrackPacket(serverLevel, pos, entry.markerId(), stage);
        }
    }

    public static void tick() {
        Iterator<Map.Entry<Level, Map<BlockPos, CrackEntry>>> worldIt = LEVEL_DATA.entrySet().iterator();
        while (worldIt.hasNext()) {
            Map.Entry<Level, Map<BlockPos, CrackEntry>> worldEntry = worldIt.next();
            Level level = worldEntry.getKey();
            Map<BlockPos, CrackEntry> blockMap = worldEntry.getValue();
            Iterator<Map.Entry<BlockPos, CrackEntry>> entryIt = blockMap.entrySet().iterator();

            while (entryIt.hasNext()) {
                Map.Entry<BlockPos, CrackEntry> entry = entryIt.next();
                CrackEntry crackEntry = entry.getValue();

                if (crackEntry.resetTicks() <= 0) {
                    // Expired: remove crack effect and marker, then delete entry
                    if (level instanceof ServerLevel serverLevel) {
                        sendCrackPacket(serverLevel, entry.getKey(), crackEntry.markerId(), -1);
                        removeMarker(level, crackEntry.markerId());
                    }
                    entryIt.remove();
                } else {
                    CrackEntry updated = new CrackEntry(
                            crackEntry.damage(),
                            crackEntry.neededHits(),
                            crackEntry.markerId(),
                            crackEntry.resetTicks() - 1
                    );
                    entry.setValue(updated);
                }
            }

            if (blockMap.isEmpty()) worldIt.remove(); // Clean up empty level maps
        }
    }

    public static void onLevelUnload(Level level) {
        Map<BlockPos, CrackEntry> blockMap = LEVEL_DATA.remove(level);
        if (blockMap != null) {
            for (CrackEntry entry : blockMap.values()) {
                removeMarker(level, entry.markerId());
            }
        }
    }

    private static void sendCrackPacket(ServerLevel level, BlockPos pos, int markerId, int progress) {
        Entity marker = level.getEntity(markerId);
        if (marker != null && marker.isAlive()) {
            ClientboundBlockDestructionPacket packet = new ClientboundBlockDestructionPacket(markerId, pos, progress);
            // Sends the packet to all players within 64 blocks of the crack (maybe make it a config????)
            for (ServerPlayer player : level.players()) {
                if (player.blockPosition().distSqr(pos) <= 64 * 64) {
                    player.connection.send(packet);
                }
            }
        }
    }

    private static void removeMarker(Level level, int markerId) {
        Entity marker = level.getEntity(markerId);
        if (marker != null && marker.isAlive()) {
            marker.discard();
        }
    }
}