package net.jadenxgamer.netherexp.core.block.entity;

import net.jadenxgamer.netherexp.core.block.PetrifiedSwirlsBlock;
import net.jadenxgamer.netherexp.registry.JNEBlockEntityType;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.UUID;

public class PetrifiedSwirlsBlockEntity extends BlockEntity {

    private UUID petrifier;
    private BlockPos petrifierPos;
    private ResourceLocation unpetrifiedBlock;
    private long nextCheckTime = 20;
    private boolean pendingUnpetrify = false;

    public PetrifiedSwirlsBlockEntity(BlockPos pos, BlockState blockState) {
        super(JNEBlockEntityType.PETRIFIED_SWIRLS.get(), pos, blockState);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries) {
        super.handleUpdateTag(tag, registries);
        loadAdditional(tag, registries);
    }

    @Override
    public BlockEntityType<?> getType() {
        return JNEBlockEntityType.PETRIFIED_SWIRLS.get();
    }

    @Override
    public boolean triggerEvent(int id, int type) {
        if (this.level != null && this.level.isClientSide() && id == 1) {
            switch (type) {
                case 0 -> PetrifiedSwirlsBlock.Client.petrificationParticle(level, level.random, getBlockPos());
                case 1 -> PetrifiedSwirlsBlock.Client.unpetrifyParticle(level, level.random, getBlockPos());
                case 2 -> PetrifiedSwirlsBlock.Client.attractToPetrifierParticle(level, level.random, getBlockPos());
            }
            return true;
        }
        return super.triggerEvent(id, type);
    }

    public void tick() {
        if (level == null || level.isClientSide()) return;

        if (pendingUnpetrify) {
            performUnpetrify();
            return;
        }

        long gameTime = level.getGameTime();
        if (gameTime >= nextCheckTime || nextCheckTime == 0) {
            if (petrifier != null && level instanceof ServerLevel serverLevel) {
                if (level.random.nextInt(5) == 0) serverLevel.blockEvent(getBlockPos(), getBlockState().getBlock(), 1, 0);
                if (petrifierPos != null && serverLevel.isLoaded(petrifierPos)) {
                    Entity entity = serverLevel.getEntity(petrifier);
                    if (entity == null || !entity.isAlive()) unpetrify();
                }
            }
            nextCheckTime = gameTime + 20 + level.random.nextInt(61);
        }
    }

    private void unpetrify() {
        if (this.level == null || this.unpetrifiedBlock == null) return;
        level.blockEvent(getBlockPos(), this.getBlockState().getBlock(), 1, 1);
        pendingUnpetrify = true;
        level.scheduleTick(getBlockPos(), getBlockState().getBlock(), 2);
    }

    private void performUnpetrify() {
        if (!pendingUnpetrify || level == null || unpetrifiedBlock == null) return;
        pendingUnpetrify = false;

        Block targetBlock = BuiltInRegistries.BLOCK.get(unpetrifiedBlock);
        if (targetBlock == Blocks.AIR) return;

        BlockState currentState = getBlockState();
        BlockState newState = targetBlock.defaultBlockState();
        if (newState.hasProperty(BlockStateProperties.FACING) && currentState.hasProperty(BlockStateProperties.FACING))
            newState = newState.setValue(BlockStateProperties.FACING, currentState.getValue(BlockStateProperties.FACING));
        if (newState.hasProperty(BlockStateProperties.WATERLOGGED) && currentState.hasProperty(BlockStateProperties.WATERLOGGED))
            newState = newState.setValue(BlockStateProperties.WATERLOGGED, currentState.getValue(BlockStateProperties.WATERLOGGED));

        level.setBlock(getBlockPos(), newState, Block.UPDATE_ALL);
        level.playSound(null, getBlockPos(), JNESoundEvents.SOUL_SWIRLS_UNPETRIFY.get(), SoundSource.BLOCKS, 0.5f, 1.0f);
    }

    //////////
    // DATA //
    //////////

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);
        if (petrifier != null) nbt.putUUID("Petrifier", petrifier);
        if (petrifierPos != null) nbt.put("PetrifierPos", NbtUtils.writeBlockPos(petrifierPos));
        if (unpetrifiedBlock != null) nbt.putString("UnpetrifiedBlock", unpetrifiedBlock.toString());
        // Do NOT save pendingUnpetrify – it would cause premature unpetrification on reload
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);
        if (nbt.hasUUID("Petrifier")) this.petrifier = nbt.getUUID("Petrifier");
        if (nbt.contains("PetrifierPos")) this.petrifierPos = NbtUtils.readBlockPos(nbt, "PetrifierPos").orElse(null);
        if (nbt.contains("UnpetrifiedBlock")) this.unpetrifiedBlock = ResourceLocation.tryParse(nbt.getString("UnpetrifiedBlock"));
        // Always reset pending flag on load – never restore from saved data
        this.pendingUnpetrify = false;
    }

    public void setPetrifier(Entity petrifier) {
        this.petrifier = petrifier.getUUID();
        this.petrifierPos = petrifier.blockPosition();
    }

    public void setUnpetrifiedBlock(ResourceLocation unpetrifiedBlock) {
        this.unpetrifiedBlock = unpetrifiedBlock;
    }

    public UUID getPetrifier() {
        return petrifier;
    }

    public BlockPos getPetrifierPos() {
        return petrifierPos;
    }

    public ResourceLocation getUnpetrifiedBlock() {
        return unpetrifiedBlock;
    }

    public boolean hasPendingUnpetrify() {
        return pendingUnpetrify;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}