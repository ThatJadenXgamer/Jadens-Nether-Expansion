package net.jadenxgamer.netherexp.registry.item.custom;

import net.jadenxgamer.netherexp.registry.entity.custom.Bottleable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;

public class EntityBottleItem extends Item {
    private final EntityType<?> entityType;
    private final SoundEvent emptyingSound;

    public EntityBottleItem(EntityType<?> type, SoundEvent emptyingSound, Settings settings) {
        super(settings);
        this.entityType = type;
        this.emptyingSound = emptyingSound;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        ItemStack stack = context.getStack();
        BlockPos pos = context.getBlockPos();
        if (world instanceof ServerWorld) {
            this.spawnEntity((ServerWorld)world, stack, pos);
            this.playEmptyingSound(world, pos);
            world.emitGameEvent(player, GameEvent.ENTITY_PLACE, pos);
            assert player != null;
            if (!player.isCreative()) {
                stack.decrement(1);
                new ItemStack(Items.GLASS_BOTTLE);
            }
            return ActionResult.success(world.isClient);
        }
        return super.useOnBlock(context);
    }

    protected void playEmptyingSound(WorldAccess world, BlockPos pos) {
        world.playSound(null, pos, this.emptyingSound, SoundCategory.NEUTRAL, 1.0F, 1.0F);
    }

    private void spawnEntity(ServerWorld world, ItemStack stack, BlockPos pos) {
        NbtCompound nbt = stack.getOrCreateNbt();
        Entity entity = this.entityType.spawnFromItemStack(world, stack, null, pos, SpawnReason.BUCKET, true, false);
        if (entity instanceof Bottleable bottleable) {
            bottleable.copyDataFromNbt(stack.getOrCreateNbt());
            bottleable.setFromBottle(true);
        }
    }
}
