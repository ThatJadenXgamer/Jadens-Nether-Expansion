package net.jadenxgamer.netherexp.registry.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class JNESkullBlockEntity extends BlockEntity {
    private int poweredTicks;
    private boolean powered;

    // TODO Add Skeletal Heads
    public JNESkullBlockEntity(BlockPos pos, BlockState state) {
        super(null, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, JNESkullBlockEntity blockEntity) {
        if (world.isReceivingRedstonePower(pos)) {
            blockEntity.powered = true;
            ++blockEntity.poweredTicks;
        } else {
            blockEntity.powered = false;
        }
    }

    public float getPoweredTicks(float tickDelta) {
        return this.powered ? (float)this.poweredTicks + tickDelta : (float)this.poweredTicks;
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }
}
