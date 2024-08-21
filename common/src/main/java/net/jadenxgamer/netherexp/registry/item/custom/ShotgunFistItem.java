package net.jadenxgamer.netherexp.registry.item.custom;

import net.jadenxgamer.netherexp.registry.enchantment.JNEEnchantments;
import net.jadenxgamer.netherexp.registry.entity.custom.SoulBullet;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
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

    // Unused, left it here, so I can salvage the hud element code for a later date
    public static int getTemperature(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        return nbt.getInt("Temprature");
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        useProjectile(stack, user);
        setAmmo(stack, getAmmo(stack) + 1);
        level.playSound(null, user.getX(), user.getY(), user.getZ(), JNESoundEvents.SHOTGUN_LOAD.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
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
        int barrage = EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.BARRAGE.get(), stack);
        int quickCharge = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
        int cooldown = barrage > 0 ? 40 + (barrage * 15) : 40 - (quickCharge * 8);
        if (cartridge > 0) {
            if (getAmmo(stack) >= 1 && !player.isShiftKeyDown()) {
                performShooting(level, player, stack);
                player.getCooldowns().addCooldown(this, 10);
                setAmmo(stack, getAmmo(stack) - 1);
                stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
                level.playSound(null, player.getX(), player.getY(), player.getZ(), JNESoundEvents.SHOTGUN_USE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
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
                level.playSound(null, player.getX(), player.getY(), player.getZ(), JNESoundEvents.SHOTGUN_USE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                player.getCooldowns().addCooldown(this, cooldown);
                return InteractionResultHolder.success(stack);
            }
        }
        return InteractionResultHolder.fail(stack);
    }

    public static void performShooting(Level level, LivingEntity user, ItemStack stack) {
        int recoil = EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.RECOIL.get(), stack);
        int artemis = EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.ARTEMIS.get(), stack);
        int barrage = EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.BARRAGE.get(), stack);
        int quickCharge = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
        // Bonuses
        int recoilBulletDistanceBonus = artemis / 5;
        double recoilPushBonus = (double) recoil / 16;
        // Vectors
        Vec3 look = user.getLookAngle();
        Vec3 pushBack = new Vec3(-look.x, -look.y, -look.z).normalize();
        int count = barrage > 0 ? 25 + (barrage * 5) : 25 - (quickCharge * 5);
        if (EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.CARTRIDGE.get(), stack) > 0) {
            count = Mth.nextInt(level.random, 8, 12);
        }
        if (!level.isClientSide) {
            for (int i = 0; i < count; i++) {
                SoulBullet soulBullet = new SoulBullet(level, user);
                soulBullet.shoot(look.x, look.y, look.z, 1.0F + recoilBulletDistanceBonus, 20);
                level.addFreshEntity(soulBullet);
            }
        }
        Vec3 raycastStart = user.getEyePosition(1.0F);
        Vec3 raycastEnd = raycastStart.add(user.getViewVector(1.0F).scale(5));
        AABB aabb = new AABB(raycastStart, raycastEnd);
        EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(level, user, raycastStart, raycastEnd, aabb, (entity) -> entity instanceof LivingEntity && entity != user);
        if (entityHitResult != null && entityHitResult.getEntity() instanceof LivingEntity livingEntity && livingEntity.isAlive()) {
            user.push(pushBack.x * (0.75 + recoilPushBonus), pushBack.y * (0.75 + recoilPushBonus), pushBack.z * (0.75 + recoilPushBonus));
        } else {
            user.push(pushBack.x * (0.3 + recoilPushBonus), pushBack.y * (0.3 + recoilPushBonus), pushBack.z * (0.3 + recoilPushBonus));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
        if (EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.CARTRIDGE.get(), stack) > 0) {
            tooltip.add(Component.translatable("shotgun_fist.ammo").append(CommonComponents.SPACE).append(Component.literal("" + getAmmo(stack))).withStyle(ChatFormatting.DARK_AQUA));
            tooltip.add((Component.empty()));
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
