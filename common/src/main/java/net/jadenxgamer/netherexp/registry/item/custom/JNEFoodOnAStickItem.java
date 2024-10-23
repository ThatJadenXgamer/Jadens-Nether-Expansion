package net.jadenxgamer.netherexp.registry.item.custom;

import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ItemSteerable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class JNEFoodOnAStickItem extends Item {

    public JNEFoodOnAStickItem(Item.Properties properties) {
        super(properties);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (level.isClientSide) {
            return InteractionResultHolder.pass(itemStack);
        } else {
            Entity entity = player.getControlledVehicle();
            if (player.isPassenger() && entity instanceof ItemSteerable) {
                ItemSteerable itemSteerable = (ItemSteerable)entity;
                if (entity.getType() == JNEEntityType.STAMPEDE.get() && itemSteerable.boost()) {
                    int consumeItemDamage = 1;
                    itemStack.hurtAndBreak(consumeItemDamage, player, (playerx) -> playerx.broadcastBreakEvent(interactionHand));
                    if (itemStack.isEmpty()) {
                        ItemStack itemStack2 = new ItemStack(Items.FISHING_ROD);
                        itemStack2.setTag(itemStack.getTag());
                        return InteractionResultHolder.success(itemStack2);
                    }

                    return InteractionResultHolder.success(itemStack);
                }
            }

            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.pass(itemStack);
        }
    }
}