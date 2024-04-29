package net.jadenxgamer.netherexp.registry.item.custom;

import net.jadenxgamer.netherexp.registry.enchantment.JNEEnchantments;
import net.jadenxgamer.netherexp.registry.entity.custom.SoulBullet;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNEDamageSources;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class PumpChargeShotgunItem extends ProjectileWeaponItem implements Vanishable {
    public PumpChargeShotgunItem(Properties properties) {
        super(properties);
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
        int cartridge = EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.CARTRIDGE.get(), stack) * 10;
        if (player.isShiftKeyDown() && getCharge(stack) <= 3) {
            setCharge(stack, getCharge(stack) + 1);
            player.startUsingItem(interactionHand);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.CROSSBOW_LOADING_END, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        else {
            if (getCharge(stack) <= 3) {
                if (!player.getProjectile(stack).isEmpty() || player.getAbilities().instabuild) {
                    performShooting(level, player, stack);
                    stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), JNESoundEvents.SHOTGUN_USE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    player.getCooldowns().addCooldown(this, 40);
                    if (level.random.nextInt(25) - cartridge != 0) {
                        useProjectile(stack, player);
                    }
                    setCharge(stack, 0);
                    return InteractionResultHolder.success(stack);
                }
            }
            else {
                level.explode(player, player.getX(), player.getY(), player.getZ(), 3, false, Level.ExplosionInteraction.TNT);
                player.getCooldowns().addCooldown(this, 80);
                player.hurt(level.damageSources().source(JNEDamageSources.SHOTGUN_EXPLOSION, player), 10);
                useProjectile(stack, player);
                stack.hurtAndBreak(2, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
                level.playSound(null, player.getX(), player.getY(), player.getZ(), JNESoundEvents.SHOTGUN_USE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                setCharge(stack, 0);
            }
        }
        return InteractionResultHolder.fail(stack);
    }

    public static void performShooting(Level level, LivingEntity livingEntity, ItemStack stack) {
        int chargeCount = getCharge(stack) * 6;
        int chargeInaccuracy = getCharge(stack) * 8;
        int count = Mth.nextInt(level.random, 4, 6) + chargeCount;
        int recoil = EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.RECOIL.get(), stack);
        int rBulletBonus = recoil / 2;
        double rPushBonus = (double) recoil / 10;
        double cPushBonus = (double) getCharge(stack) / 10;
        Vec3 look = livingEntity.getLookAngle();
        Vec3 pushBack = new Vec3(-look.x, -look.y, -look.z).normalize();
        if (!level.isClientSide) {
            for (int i = 0; i < count; i++) {
                SoulBullet soulBullet = new SoulBullet(level, livingEntity);
                soulBullet.shoot(look.x, look.y, look.z, 1.0F + rBulletBonus, 5 + chargeInaccuracy);
                level.addFreshEntity(soulBullet);
            }
        }
        double d = 0.3 + rPushBonus + cPushBonus;
        livingEntity.push(pushBack.x * d, pushBack.y * d, pushBack.z * d);
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
