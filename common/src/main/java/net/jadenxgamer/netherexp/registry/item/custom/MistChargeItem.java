package net.jadenxgamer.netherexp.registry.item.custom;

import net.jadenxgamer.netherexp.registry.entity.custom.MistCharge;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MistChargeItem extends Item {
    public MistChargeItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        level.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!level.isClientSide) {
            MistCharge mistCharge = new MistCharge(user, level);
            mistCharge.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(mistCharge);
        }

        user.awardStat(Stats.ITEM_USED.get(this));
        if (!user.getAbilities().instabuild) {
            itemStack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide);
    }
}
