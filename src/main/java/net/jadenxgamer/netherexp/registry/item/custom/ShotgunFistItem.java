package net.jadenxgamer.netherexp.registry.item.custom;

import net.jadenxgamer.elysium_api.api.keyframe.NonEntityAnimationState;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.enchantment.JNEEnchantments;
import net.jadenxgamer.netherexp.registry.entity.custom.ShotgunPellet;
import net.jadenxgamer.netherexp.registry.item.JNEItemRenderer;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class ShotgunFistItem extends ProjectileWeaponItem implements Vanishable, IShotgun {
    public final NonEntityAnimationState fireAnimationState = new NonEntityAnimationState();

    private int fireTimeOut;
    private boolean fireFlag;

    public ShotgunFistItem(Properties settings) {
        super(settings);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new JNEItemRenderer();
            }
        });
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
        if (level.isClientSide()) {
            if (this.fireFlag) {
                playFireAnimation(player, player.tickCount);
            }
        }
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
        if (!player.getProjectile(stack).isEmpty() || player.getAbilities().instabuild) {
            if (cartridge > 0 && level.random.nextInt((cartridge * 2)) == 0) {
                useProjectile(stack, player);
            } else {
                useProjectile(stack, player);
            }
            performShooting(level, player, stack);
            stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
            level.playSound(null, player.getX(), player.getY(), player.getZ(), JNESoundEvents.SHOTGUN_USE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
            player.getCooldowns().addCooldown(this, cooldown);
            this.fireFlag = true;
            return InteractionResultHolder.pass(stack);
        }
        return InteractionResultHolder.fail(stack);
    }

    public static void performShooting(Level level, LivingEntity user, ItemStack stack) {
        int recoil = EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.RECOIL.get(), stack);
        int artemis = EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.ARTEMIS.get(), stack);
        int barrage = EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.BARRAGE.get(), stack);
        int quickCharge = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
        // Bonuses
        int artemisBulletDistanceBonus = artemis / 5;
        double recoilPushBonus = (double) recoil / 16;
        // Vectors
        Vec3 look = user.getLookAngle();
        Vec3 pushBack = new Vec3(-look.x, -look.y, -look.z).normalize();
        int count = barrage > 0 ? JNEConfigs.SHOTGUN_FIST_BULLETS.get() + (barrage * 5) : JNEConfigs.SHOTGUN_FIST_BULLETS.get() - (quickCharge * 5);
        if (EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.CARTRIDGE.get(), stack) > 0) {
            count = Mth.nextInt(level.random, 8, 12);
        }
        if (!level.isClientSide) {
            for (int i = 0; i < count; i++) {
                ShotgunPellet soulBullet = new ShotgunPellet(level, user);
                soulBullet.shoot(look.x, look.y, look.z, 1.5F + artemisBulletDistanceBonus, 20);
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
    public @NotNull Predicate<ItemStack> getAllSupportedProjectiles() {
        return (stack) -> stack.is(JNEItems.WRAITHING_FLESH.get());
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }

    private void playFireAnimation(Player player, int tickCount) {
        if (this.fireTimeOut == 20) {
            this.fireAnimationState.startIfStopped(tickCount, player);
        }
        if (this.fireTimeOut > 0) {
            --this.fireTimeOut;
        }
        if (this.fireTimeOut <= 0) {
            this.fireAnimationState.stop(player);
            this.fireTimeOut = 20;
            this.fireFlag = false;
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !oldStack.is(JNEItems.SHOTGUN_FIST.get()) || !newStack.is(JNEItems.SHOTGUN_FIST.get());
    }

    @Override
    public boolean isValidRepairItem(ItemStack pStack, ItemStack pRepair) {
        return pRepair.is(JNEItems.STRIDITE.get()) || super.isValidRepairItem(pStack, pRepair);
    }
}
