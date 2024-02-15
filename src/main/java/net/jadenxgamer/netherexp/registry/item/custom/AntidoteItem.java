package net.jadenxgamer.netherexp.registry.item.custom;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AntidoteItem extends Item {
    boolean inactive;
    StatusEffectInstance statusEffect;

    public AntidoteItem(Settings settings, StatusEffectInstance statusEffect, boolean inactive) {
        super(settings);
        this.statusEffect = statusEffect;
        this.inactive = inactive;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (this.inactive) {
            tooltip.add(Text.translatable("effect.none").formatted(Formatting.GRAY));
            tooltip.add((Text.empty()));
            tooltip.add(Text.translatable("item.netherexp.antidote.inactive_tooltip").formatted(Formatting.DARK_PURPLE));
            tooltip.add(Text.translatable(this.statusEffect.getTranslationKey()).formatted(Formatting.BLUE));
        }
        else if (this.statusEffect != null) {
            List<StatusEffectInstance> list = new ArrayList<>();
            list.add(this.statusEffect);
            PotionUtil.buildTooltip(list, tooltip, 1.0F);
        }
        else {
            tooltip.add(Text.translatable("effect.none").formatted(Formatting.GRAY));
        }
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity)user : null;
        if (playerEntity instanceof ServerPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity)playerEntity, stack);
        }

        if (!world.isClient) {
            if (!this.inactive && this.statusEffect != null) {
                user.addStatusEffect(this.statusEffect);
            }
        }

        if (playerEntity != null) {
            playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!playerEntity.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }

        if (playerEntity == null || !playerEntity.getAbilities().creativeMode) {
            if (stack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }

            if (playerEntity != null) {
                playerEntity.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        user.emitGameEvent(GameEvent.DRINK);
        return stack;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 64;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }
}
