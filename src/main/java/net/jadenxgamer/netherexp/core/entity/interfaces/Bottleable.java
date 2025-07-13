package net.jadenxgamer.netherexp.core.entity.interfaces;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.function.Supplier;

public interface Bottleable {

    boolean fromBottle();

    void setFromBottle(boolean fromBottle);

    void saveToBottleTag(ItemStack stack);

    void loadFromBottleTag(CompoundTag tag);

    ItemStack getBottleItemStack();

    SoundEvent getPickupSound();

    static void saveDefaultDataToBottleTag(Mob mob, ItemStack bottle) {
        bottle.set(DataComponents.CUSTOM_NAME, mob.getCustomName());
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bottle, (arg2) -> {
            if (mob.isNoAi()) {
                arg2.putBoolean("NoAI", mob.isNoAi());
            }

            if (mob.isSilent()) {
                arg2.putBoolean("Silent", mob.isSilent());
            }

            if (mob.isNoGravity()) {
                arg2.putBoolean("NoGravity", mob.isNoGravity());
            }

            if (mob.hasGlowingTag()) {
                arg2.putBoolean("Glowing", mob.hasGlowingTag());
            }

            if (mob.isInvulnerable()) {
                arg2.putBoolean("Invulnerable", mob.isInvulnerable());
            }

            arg2.putFloat("Health", mob.getHealth());
        });
    }

    static void loadDefaultDataFromBottleTag(Mob mob, CompoundTag nbt) {
        if (nbt.contains("NoAI")) {
            mob.setNoAi(nbt.getBoolean("NoAI"));
        }

        if (nbt.contains("Silent")) {
            mob.setSilent(nbt.getBoolean("Silent"));
        }

        if (nbt.contains("NoGravity")) {
            mob.setNoGravity(nbt.getBoolean("NoGravity"));
        }

        if (nbt.contains("Glowing")) {
            mob.setGlowingTag(nbt.getBoolean("Glowing"));
        }

        if (nbt.contains("Invulnerable")) {
            mob.setInvulnerable(nbt.getBoolean("Invulnerable"));
        }

        if (nbt.contains("Health", 99)) {
            mob.setHealth(nbt.getFloat("Health"));
        }

    }

    static <T extends LivingEntity & Bottleable> Optional<InteractionResult> bottleMobPickup(Player player, InteractionHand hand, T entity, Supplier<Item> bottle) {
        ItemStack stack = player.getItemInHand(hand);
        Level level = entity.level();

        if (stack.is(bottle.get()) && entity.isAlive()) {
            ItemStack bottleStack = entity.getBottleItemStack();
            entity.saveToBottleTag(bottleStack);
            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, bottleStack, false));
            entity.playSound(entity.getPickupSound(), 1.0F, 1.0F);

            entity.discard();
            return Optional.of(InteractionResult.sidedSuccess(level.isClientSide()));
        } else {
            return Optional.empty();
        }
    }
}
