package net.jadenxgamer.netherexp.registry.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AntidoteItem extends Item {

    public AntidoteItem(Properties properties) {
        super(properties);
    }

    private static MobEffect getAntidoteEffect(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (!nbt.contains("AntidoteEffect") || Objects.equals(nbt.getString("Antidote"), "Awkward")) {
            return null;
        }

        ResourceLocation antidoteLocation = new ResourceLocation(stack.getOrCreateTag().getString("AntidoteEffect"));
        return Objects.requireNonNull(BuiltInRegistries.MOB_EFFECT.get(antidoteLocation));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        Player playerEntity = user instanceof Player ? (Player)user : null;
        CompoundTag nbt = stack.getOrCreateTag();
        if (playerEntity instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)playerEntity, stack);
        }

        if (!level.isClientSide) {
            MobEffect antidoteEffect = getAntidoteEffect(stack);
            if (antidoteEffect != null && !nbt.getBoolean("Inactive")) {
                user.addEffect(new MobEffectInstance(Objects.requireNonNull(getAntidoteEffect(stack)), getDuration(stack), 0));
            }
        }

        if (playerEntity != null) {
            playerEntity.awardStat(Stats.ITEM_USED.get(this));
            if (!playerEntity.getAbilities().instabuild) {
                stack.shrink(1);
                level.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 0.5f, 1.5f);
            }
        }

        user.gameEvent(GameEvent.DRINK);
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
        MobEffect antidoteEffect = getAntidoteEffect(stack);
        CompoundTag nbt = stack.getOrCreateTag();
        if (antidoteEffect != null && nbt.getBoolean("Inactive")) {
            tooltip.add(Component.translatable("effect.none").withStyle(ChatFormatting.GRAY));
            tooltip.add((Component.empty()));
            tooltip.add(Component.translatable("item.netherexp.antidote.inactive_tooltip").withStyle(ChatFormatting.DARK_PURPLE));
            tooltip.add(Component.translatable(antidoteEffect.getDescriptionId()).withStyle(ChatFormatting.BLUE));
        }
        else if (antidoteEffect != null) {
            List<MobEffectInstance> list = new ArrayList<>();
            list.add(new MobEffectInstance(Objects.requireNonNull(getAntidoteEffect(stack)), getDuration(stack), 0));
            PotionUtils.addPotionTooltip(list, tooltip, 1.0f);
        }
        else {
            tooltip.add(Component.translatable("effect.none").withStyle(ChatFormatting.GRAY));
        }
    }

    public static int getColor(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        MobEffect antidoteEffect = getAntidoteEffect(stack);
        if (nbt.contains("CustomAntidoteColor")) {
            return nbt.getInt("CustomAntidoteColor");
        }
        else return antidoteEffect == null ? 16253176 : antidoteEffect.getColor();
    }

    public static int getDuration(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.contains("Duration")) {
            return nbt.getInt("Duration") * 20;
        }
        else return 1800 * 20;
    }

    @Override
    public @NotNull String getDescriptionId(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.getString("Antidote");
        return this.getDescriptionId() + ".effect." + nbt.getString("Antidote");
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 48;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.DRINK;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        return ItemUtils.startUsingInstantly(level, player, interactionHand);
    }
}
