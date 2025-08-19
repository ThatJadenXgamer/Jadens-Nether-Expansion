package net.jadenxgamer.netherexp.core.item;

import net.jadenxgamer.netherexp.core.entity.interfaces.Bottleable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class MobBottleItem<T extends Entity> extends Item {

    private final Supplier<EntityType<T>> type;
    private final Supplier<SoundEvent> emptySound;

    public MobBottleItem(Supplier<EntityType<T>> type, Supplier<SoundEvent> emptySound, Properties properties) {
        super(properties);
        this.type = type;
        this.emptySound = emptySound;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        BlockHitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);

        if (hitResult.getType() == HitResult.Type.MISS) return InteractionResultHolder.pass(stack);

        Direction direction = hitResult.getDirection();
        BlockPos pos = hitResult.getBlockPos().relative(direction);

        if (level.mayInteract(player, hitResult.getBlockPos()) && player.mayUseItemAt(pos, direction, stack)) {
            level.playSound(null, pos, getEmptySound(), SoundSource.NEUTRAL, 1.0f, 1.0f);
            if (level instanceof ServerLevel serverLevel) {
                T entity = getEntityType().spawn(serverLevel, stack, null, pos, MobSpawnType.BUCKET, true, false);
                if (entity instanceof Bottleable bottleable) {
                    CustomData component = stack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
                    bottleable.loadFromBottleTag(component.copyTag());
                    bottleable.setFromBottle(true);
                }
                level.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
                return InteractionResultHolder.sidedSuccess(getEmptySuccessItem(stack, player), level.isClientSide());
            }
        }
        return InteractionResultHolder.fail(stack);
    }

    public EntityType<T> getEntityType() {
        return type.get();
    }

    public SoundEvent getEmptySound() {
        return emptySound.get();
    }

    public static ItemStack getEmptySuccessItem(ItemStack stack, Player player) {
        return !player.getAbilities().instabuild ? new ItemStack(Items.GLASS_BOTTLE) : stack;
    }
}
