package net.jadenxgamer.netherexp.registry.item.custom;

import net.jadenxgamer.netherexp.registry.enchantment.JNEEnchantments;
import net.jadenxgamer.netherexp.registry.entity.custom.SoulBullet;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNEDamageSources;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class PumpChargeShotgunItem extends ProjectileWeaponItem implements Vanishable {
    private int cooldown;

    public PumpChargeShotgunItem(Properties properties) {
        super(properties);
        cooldown = 200;
    }

    public static int getCharge(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        return nbt.getInt("Charge");
    }

    public static void setCharge(ItemStack stack, int i) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putInt("Charge", i);
        nbt.putInt("CustomModelData", i);
    }

    public static int getTemperature(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        return nbt.getInt("Temperature");
    }

    public static void setTemperature(ItemStack stack, int i) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putInt("Temperature", i);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (!level.isClientSide && getTemperature(stack) > 0) {
            --this.cooldown;
            if (cooldown <= 0) {
                setTemperature(stack, getTemperature(stack) - 1);
                this.cooldown = 150;
            }
        }
        super.inventoryTick(stack, level, entity, slot, selected);
    }


    private void useProjectile(ItemStack stack, LivingEntity user) {
        if (user instanceof Player player) {
            boolean creative = player.getAbilities().instabuild;
            ItemStack projectileStack = player.getProjectile(stack);
            if (!projectileStack.isEmpty() || creative) {
                if (projectileStack.isEmpty()) {
                    projectileStack = new ItemStack(JNEItems.WRAITHING_FLESH.get());
                }
                boolean bl = projectileStack.getItem() == JNEItems.WRAITHING_FLESH.get();
                if (bl && !creative) {
                    projectileStack.shrink(1);
                    if (projectileStack.isEmpty()) {
                        player.getInventory().removeItem(projectileStack);
                    }
                }
                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack stack = player.getItemInHand(interactionHand);
        int heat = getTemperature(stack);
        int cartridge = EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.CARTRIDGE.get(), stack) * 10;
        if (player.isShiftKeyDown() && getCharge(stack) <= 3) {
            setCharge(stack, getCharge(stack) + 1);
            player.startUsingItem(interactionHand);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), JNESoundEvents.SHOTGUN_LOAD.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        else {
            if (getCharge(stack) <= 3) {
                if (!player.getProjectile(stack).isEmpty() || player.getAbilities().instabuild) {
                    performShooting(level, player, stack);
//                    if (heat < 4 && !player.getAbilities().instabuild) {
//                        setTemperature(stack, heat + 1);
//                    }
                    stack.hurtAndBreak(heat, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), JNESoundEvents.SHOTGUN_USE.get(), SoundSource.PLAYERS, 1.0f - (0.15f * heat), 1.0f + (0.2f * heat));
                    player.getCooldowns().addCooldown(this, 10);
                    useProjectile(stack, player);
                    setCharge(stack, 0);
                    return InteractionResultHolder.success(stack);
                }
            }
            else {
                if (!player.getProjectile(stack).isEmpty() || player.getAbilities().instabuild) {
                    level.explode(player, player.getX(), player.getY(), player.getZ(), 3, false, Level.ExplosionInteraction.TNT);
                    player.getCooldowns().addCooldown(this, 80);
                    player.hurt(level.damageSources().source(JNEDamageSources.SHOTGUN_EXPLOSION, player), 10);
                    stack.hurtAndBreak(5, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
//                    setTemperature(stack, 4);
                    useProjectile(stack, player);
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), JNESoundEvents.SHOTGUN_USE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    setCharge(stack, 0);
                }
            }
        }
        return InteractionResultHolder.fail(stack);
    }

    public static void performShooting(Level level, LivingEntity livingEntity, ItemStack stack) {
        int chargeCount = getCharge(stack) * 6;
        int chargeInaccuracy = getCharge(stack) * 8;
        int recoil = EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.RECOIL.get(), stack);
        int artemis = EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.ARTEMIS.get(), stack);
        int heat = getTemperature(stack);
        // Bonuses
        int aBulletDistanceBonus = artemis / 5;
        double rPushBonus = (double) recoil / 20;
        double cPushBonus = (double) getCharge(stack) / 10;
        int hBulletDistancePenalty = heat / 8;
        int hCountPenalty = heat * 4;
        int hScatterPenalty = heat * 5;

        Vec3 look = livingEntity.getLookAngle();
        Vec3 pushBack = new Vec3(-look.x, -look.y, -look.z).normalize();
        int baseCount = 4 + chargeCount;
        int minCount = Math.max(1, (4 + (getCharge(stack) / 2)));
        int count = Math.max(minCount, baseCount - hCountPenalty);
        if (!level.isClientSide) {
            for (int i = 0; i < count; i++) {
                SoulBullet soulBullet = new SoulBullet(level, livingEntity);
                soulBullet.shoot(look.x, look.y, look.z, (1.0F + aBulletDistanceBonus) - hBulletDistancePenalty, (5 + chargeInaccuracy) + hScatterPenalty);
                level.addFreshEntity(soulBullet);
            }
        }
        double d = 0.3 + rPushBonus + cPushBonus;
        livingEntity.push(pushBack.x * d, pushBack.y * d, pushBack.z * d);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
        tooltip.add(Component.translatable("shotgun_fist.heat").append(CommonComponents.SPACE).append(Component.literal("" + getTemperature(stack))).withStyle(ChatFormatting.RED));
        tooltip.add((Component.empty()));
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public @NotNull Predicate<ItemStack> getAllSupportedProjectiles() {
        return (stack) -> stack.is(JNEItems.WRAITHING_FLESH.get());
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }
}
