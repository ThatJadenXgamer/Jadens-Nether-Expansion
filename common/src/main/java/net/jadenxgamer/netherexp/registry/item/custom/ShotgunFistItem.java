package net.jadenxgamer.netherexp.registry.item.custom;

import net.jadenxgamer.netherexp.registry.enchantment.JNEEnchantments;
import net.jadenxgamer.netherexp.registry.entity.custom.SoulBullet;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
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

public class ShotgunFistItem extends ProjectileWeaponItem implements Vanishable {
    public ShotgunFistItem(Properties settings) {
        super(settings);
    }

    public static int getAmmo(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        return nbt.getInt("Ammo");
    }

    public static void setAmmo(ItemStack stack, int i) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putInt("Ammo", i);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        useProjectile(stack, user);
        setAmmo(stack, getAmmo(stack) + 1);
        level.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.CROSSBOW_LOADING_END, SoundSource.PLAYERS, 1.0F, 1.0F);
        return stack;
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
        int cartridge = EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.CARTRIDGE.get(), stack);
        if (cartridge > 0) {
            if (getAmmo(stack) >= 1 && !player.isShiftKeyDown()) {
                performShooting(level, player, stack);
                setAmmo(stack, getAmmo(stack) - 1);
                stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
                level.playSound(null, player.getX(), player.getY(), player.getZ(), JNESoundEvents.SHOTGUN_USE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                return InteractionResultHolder.success(stack);
            }
            else if (player.isShiftKeyDown() && getAmmo(stack) <= cartridge) {
                if (!player.getProjectile(stack).isEmpty() || player.getAbilities().instabuild) {
                    player.startUsingItem(interactionHand);
                    return InteractionResultHolder.consume(stack);
                }
            }
        }
        else {
            if (!player.getProjectile(stack).isEmpty() || player.getAbilities().instabuild) {
                useProjectile(stack, player);
                performShooting(level, player, stack);
                stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
                level.playSound(null, player.getX(), player.getY(), player.getZ(), JNESoundEvents.SHOTGUN_USE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                player.getCooldowns().addCooldown(this, 30);
                return InteractionResultHolder.success(stack);
            }
        }
        return InteractionResultHolder.fail(stack);
    }

    public static void performShooting(Level level, LivingEntity livingEntity, ItemStack stack) {
        int count = Mth.nextInt(level.random, 8, 16);
        int recoil = EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.RECOIL.get(), stack);
        // Bonuses
        int rBulletBonus = recoil / 5;
        double rPushBonus = (double) recoil / 16;
        // Vectors
        Vec3 look = livingEntity.getLookAngle();
        Vec3 pushBack = new Vec3(-look.x, -look.y, -look.z).normalize();
        if (!level.isClientSide) {
            for (int i = 0; i < count; i++) {
                SoulBullet soulBullet = new SoulBullet(level, livingEntity);
                soulBullet.shoot(look.x, look.y, look.z, 1.0F + rBulletBonus, 16);
                level.addFreshEntity(soulBullet);
            }
        }
        double d = 0.3 + rPushBonus;
        livingEntity.push(pushBack.x * d, pushBack.y * d, pushBack.z * d);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
        if (EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.CARTRIDGE.get(), stack) > 0) {
            if (getAmmo(stack) >= 1) {
                tooltip.add(Component.translatable("shotgun_fist.ammo").append(CommonComponents.SPACE).append(Component.literal("" + getAmmo(stack))).withStyle(ChatFormatting.DARK_AQUA));
                tooltip.add((Component.empty()));
            }
            else {
                tooltip.add(Component.translatable("shotgun_fist.ammo").append(CommonComponents.SPACE).append(Component.literal("" + getAmmo(stack))).withStyle(ChatFormatting.RED));
                tooltip.add((Component.empty()));
            }
        }
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        int ammo = getAmmo(stack) * 10;
        return 10 + ammo;
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
