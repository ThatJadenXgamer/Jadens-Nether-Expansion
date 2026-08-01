package net.jadenxgamer.netherexp.core.item;

import net.jadenxgamer.netherexp.core.entity.Stampede;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class SkullOnAStick extends Item {
    public SkullOnAStick(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            if (player.isPassenger() && player.getControlledVehicle() instanceof Stampede stampede) {
                stampede.applySpeedBoost();
                EquipmentSlot slot = LivingEntity.getSlotForHand(hand);
                ItemStack newStack = stack.hurtAndConvertOnBreak(1, Items.FISHING_ROD, player, slot);
                return InteractionResultHolder.success(newStack);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
        }
        return InteractionResultHolder.pass(stack);
    }
}