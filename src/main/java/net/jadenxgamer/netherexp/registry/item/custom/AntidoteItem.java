package net.jadenxgamer.netherexp.registry.item.custom;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.PotionUtil;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AntidoteItem extends Item {

    public AntidoteItem(Settings settings) {
        super(settings);
    }

    private static StatusEffect getAntidoteEffect(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (!nbt.contains("AntidoteEffect") || Objects.equals(nbt.getString("Antidote"), "Awkward")) {
            return null;
        }

        Identifier antidoteLocation = new Identifier(stack.getOrCreateNbt().getString("AntidoteEffect"));
        return Objects.requireNonNull(Registries.STATUS_EFFECT.get(antidoteLocation));
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity)user : null;
        NbtCompound nbt = stack.getOrCreateNbt();
        if (playerEntity instanceof ServerPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity)playerEntity, stack);
        }

        if (!world.isClient) {
            StatusEffect antidoteEffect = getAntidoteEffect(stack);
            if (antidoteEffect != null && !nbt.getBoolean("Inactive")) {
                user.addStatusEffect(new StatusEffectInstance(Objects.requireNonNull(getAntidoteEffect(stack)), 18000, 0));
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
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        StatusEffect antidoteEffect = getAntidoteEffect(stack);
        NbtCompound nbt = stack.getOrCreateNbt();
        if (antidoteEffect != null && nbt.getBoolean("Inactive")) {
            tooltip.add(Text.translatable("effect.none").formatted(Formatting.GRAY));
            tooltip.add((Text.empty()));
            tooltip.add(Text.translatable("item.netherexp.antidote.inactive_tooltip").formatted(Formatting.DARK_PURPLE));
            tooltip.add(Text.translatable(antidoteEffect.getTranslationKey()).formatted(Formatting.BLUE));
        }
        else if (antidoteEffect != null) {
            List<StatusEffectInstance> list = new ArrayList<>();
            list.add(new StatusEffectInstance(Objects.requireNonNull(getAntidoteEffect(stack)), 18000, 0));
            PotionUtil.buildTooltip(list, tooltip, 1.0f);
        }
        else {
            tooltip.add(Text.translatable("effect.none").formatted(Formatting.GRAY));
        }
    }

    public static int getColor(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        StatusEffect antidoteEffect = getAntidoteEffect(stack);
        if (nbtCompound != null && nbtCompound.contains("CustomAntidoteColor")) {
            return nbtCompound.getInt("CustomAntidoteColor");
        }
        else return antidoteEffect == null ? 16253176 : antidoteEffect.getColor();
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (nbt.getString("Antidote") != null) {
            return this.getTranslationKey() + ".effect." + nbt.getString("Antidote");
        }
        else return this.getTranslationKey() + ".effect.empty";
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 48;
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
