package net.jadenxgamer.netherexp.registry.item.custom;

import com.google.common.base.Supplier;

import dev.architectury.registry.registries.RegistrySupplier;
import net.jadenxgamer.netherexp.registry.entity.custom.Bottleable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;

/**
 * MobBottleItem
 */
public class MobBottleItem<T extends Entity> extends Item {

    private RegistrySupplier<EntityType<T>> entityTypeSupplier;
    private Supplier<SoundEvent> soundEventSupplier;

    public MobBottleItem(RegistrySupplier<EntityType<T>> entityTypeSupplier, Properties properties) {
        this(entityTypeSupplier, () -> {
            return SoundEvents.BOTTLE_EMPTY;
        }, properties);
    }

    public MobBottleItem(RegistrySupplier<EntityType<T>> entityTypeSupplier, Supplier<SoundEvent> soundEventSupplier, Properties properties) {
        super(properties);
        this.entityTypeSupplier = entityTypeSupplier;
        this.soundEventSupplier = soundEventSupplier;
    }

    public EntityType<?> getEntityType() {
        return entityTypeSupplier.get();
    }

    public SoundEvent getSoundEvent() {
        return soundEventSupplier.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        BlockHitResult blockHitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
        if (blockHitResult.getType() == Type.MISS) {
            return InteractionResultHolder.pass(itemStack);
        } else if (blockHitResult.getType() != Type.BLOCK) {
            return InteractionResultHolder.pass(itemStack);
        } else {
            BlockPos pos = blockHitResult.getBlockPos();
            Direction direction = blockHitResult.getDirection();
            BlockPos relativePos = pos.relative(direction);
            if (level.mayInteract(player, pos) && player.mayUseItemAt(relativePos, direction, itemStack)) {
                level.playSound(player, relativePos, this.getSoundEvent(), SoundSource.NEUTRAL, 1.0f, 1.0f);
                if (level instanceof ServerLevel) {
                    Entity entity = this.getEntityType().spawn((ServerLevel) level, itemStack, null, relativePos, MobSpawnType.BUCKET, true, false);
                    if (entity instanceof Bottleable bottleable) {
                        bottleable.loadFromBottleTag(itemStack.getOrCreateTag());
                        bottleable.setFromBottle(true);
                    }
                    level.gameEvent(player, GameEvent.ENTITY_PLACE, relativePos);
                }

                player.awardStat(Stats.ITEM_USED.get(this));
                return InteractionResultHolder.sidedSuccess(getEmptySuccessItem(itemStack, player), level.isClientSide());
            } else {
                return InteractionResultHolder.fail(itemStack);
            }
        }
    }

    public static ItemStack getEmptySuccessItem(ItemStack itemStack, Player player) {
        return !player.getAbilities().instabuild ? new ItemStack(Items.GLASS_BOTTLE) : itemStack;
    }
}
