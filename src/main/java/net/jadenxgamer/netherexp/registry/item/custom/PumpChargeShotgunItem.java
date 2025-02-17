package net.jadenxgamer.netherexp.registry.item.custom;

import net.jadenxgamer.elysium_api.api.keyframe.NonEntityAnimationState;
import net.jadenxgamer.netherexp.registry.advancements.JNECriteriaTriggers;
import net.jadenxgamer.netherexp.registry.enchantment.JNEEnchantments;
import net.jadenxgamer.netherexp.registry.entity.custom.SoulBullet;
import net.jadenxgamer.netherexp.registry.item.JNEItemRenderer;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNEDamageSources;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
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

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class PumpChargeShotgunItem extends ProjectileWeaponItem implements Vanishable, IShotgun {
    public final NonEntityAnimationState fireAnimationState = new NonEntityAnimationState();
    public final NonEntityAnimationState explodeAnimationState = new NonEntityAnimationState();
    public final NonEntityAnimationState pumpAnimationState = new NonEntityAnimationState();
    public final NonEntityAnimationState overpumpAnimationState = new NonEntityAnimationState();

    private int pumpTimeOut;
    private boolean pumpFlag;

    private int fireTimeOut;
    private boolean fireFlag;

    private int explodeTimeOut;
    private boolean explodeFlag;

    public PumpChargeShotgunItem(Properties properties) {
        super(properties);
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
            if (this.pumpFlag) {
                playPumpAnimation(player, player.tickCount);
            }
            if (this.fireFlag) {
                playFireAnimation(player, player.tickCount);
            }
            if (this.explodeFlag) {
                playExplodeAnimation(player, player.tickCount);
            }
            if (getCharge(stack) >= 4) {
                overpumpAnimationState.startIfStopped(player.tickCount, player);
            } else {
                overpumpAnimationState.stop(player);
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (!pLevel.isClientSide() && pEntity.tickCount % 14 == 0 && pIsSelected && getCharge(pStack) == 4) {
            pLevel.playSound(null, pEntity.getX(), pEntity.getY(), pEntity.getZ(), JNESoundEvents.PUMP_CHARGE_SHOTGUN_ALARM.get(), SoundSource.PLAYERS, 0.7f, 1.0f);
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
        int cooldown = barrage > 0 ? 15 + (barrage * 10) : 15 - (quickCharge * 4);
        if (player.isShiftKeyDown()) {
            if (getCharge(stack) <= 3) {
                setCharge(stack, getCharge(stack) + 1);
            }
            this.pumpFlag = true;
            level.playSound(null, player.getX(), player.getY(), player.getZ(), JNESoundEvents.SHOTGUN_LOAD.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        else {
            if (getCharge(stack) <= 3) {
                if (!player.getProjectile(stack).isEmpty() || player.getAbilities().instabuild) {
                    performShooting(level, player, stack);
                    stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), JNESoundEvents.SHOTGUN_USE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                    player.getCooldowns().addCooldown(this, cooldown);
                    if (cartridge > 0 && level.random.nextInt((cartridge * 2)) == 0) {
                        useProjectile(stack, player);
                    } else {
                        useProjectile(stack, player);
                    }
                    setCharge(stack, 1);
                    this.fireFlag = true;

                    return InteractionResultHolder.pass(stack);
                }
            } else {
                if (!player.getProjectile(stack).isEmpty() || player.getAbilities().instabuild) {
                    level.explode(player, null, null, player.getX(), player.getY(), player.getZ(), 3, false, Level.ExplosionInteraction.NONE, false);
                    if (!level.isClientSide()) {
                        ((ServerLevel)level).sendParticles(JNEParticleTypes.REDSTONE_EXPLOSION_EMITTER.get(), player.getX(), player.getY(), player.getZ(), 1, 0.0, 0.0, 0.0, 0);
                    }
                    player.getCooldowns().addCooldown(this, 100 - (quickCharge * 20));
                    player.hurt(level.damageSources().source(JNEDamageSources.SHOTGUN_EXPLOSION, player), 10);
                    stack.hurtAndBreak(5, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
                    useProjectile(stack, player);
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), JNESoundEvents.SHOTGUN_USE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    setCharge(stack, 1);
                    this.explodeFlag = true;

                    Vec3 look = player.getLookAngle();
                    Vec3 pushBack = new Vec3(-look.x, -look.y, -look.z).normalize();
                    player.push(pushBack.x * (1.75), pushBack.y * (1.75), pushBack.z * (1.75));

                    List<Entity> nearbyEntities = level.getEntities(player, new AABB(player.getOnPos()).inflate(5.0, 5.0, 5.0));
                    if (nearbyEntities.stream().filter(entity -> entity instanceof Mob).filter(entity -> ((Mob) entity).isDeadOrDying()).count() >= 10 && player instanceof ServerPlayer serverPlayer) {
                        JNECriteriaTriggers.KILLED_WITH_PUMP_CHARGE.trigger(serverPlayer);
                    }
                }
            }
        }
        return InteractionResultHolder.fail(stack);
    }

    public void performShooting(Level level, LivingEntity user, ItemStack stack) {
        int chargeCount = getCharge(stack) * 10;
        int chargeInaccuracy = getCharge(stack) * 5;
        int recoil = EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.RECOIL.get(), stack);
        int artemis = EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.ARTEMIS.get(), stack);
        int barrage = EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.BARRAGE.get(), stack);
        int quickCharge = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
        // Bonuses
        int aBulletDistanceBonus = artemis / 5;
        double recoilPushBonus = (double) recoil / 16;
        double chargePushBonus = (double) getCharge(stack) / 10;

        Vec3 look = user.getLookAngle();
        Vec3 pushBack = new Vec3(-look.x, -look.y, -look.z).normalize();
        int count = barrage > 0 ? chargeCount + (barrage * 2) : chargeCount - (quickCharge * 2);
        if (!level.isClientSide) {
            for (int i = 0; i < count; i++) {
                SoulBullet soulBullet = new SoulBullet(level, user);
                soulBullet.shoot(look.x, look.y, look.z, (1.5F + aBulletDistanceBonus), (7 + chargeInaccuracy));
                level.addFreshEntity(soulBullet);
            }
        }
        Vec3 raycastStart = user.getEyePosition(1.0F);
        Vec3 raycastEnd = raycastStart.add(user.getViewVector(1.0F).scale(5));
        AABB aabb = new AABB(raycastStart, raycastEnd);
        EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(level, user, raycastStart, raycastEnd, aabb, (entity) -> entity instanceof LivingEntity && entity != user);
        if (entityHitResult != null && entityHitResult.getEntity() instanceof LivingEntity livingEntity && livingEntity.isAlive()) {
            user.push(pushBack.x * (0.75 + recoilPushBonus + chargePushBonus), pushBack.y * (0.75 + recoilPushBonus + chargePushBonus), pushBack.z * (0.75 + recoilPushBonus + chargePushBonus));
        } else {
            user.push(pushBack.x * (0.3 + recoilPushBonus + chargePushBonus), pushBack.y * (0.3 + recoilPushBonus + chargePushBonus), pushBack.z * (0.3 + recoilPushBonus + chargePushBonus));
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

    public static int getCharge(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        return nbt.getInt("Charge");
    }

    public static void setCharge(ItemStack stack, int i) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putInt("Charge", i);
    }

    private void playPumpAnimation(Player player, int tickCount) {
        if (this.pumpTimeOut == 5) {
            this.pumpAnimationState.startIfStopped(tickCount, player);
        }
        if (this.pumpTimeOut > 0) {
            --this.pumpTimeOut;
        }
        if (this.pumpTimeOut <= 0) {
            this.pumpAnimationState.stop(player);
            this.pumpTimeOut = 5;
            this.pumpFlag = false;
        }
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

    private void playExplodeAnimation(Player player, int tickCount) {
        if (this.explodeTimeOut == 60) {
            this.explodeAnimationState.startIfStopped(tickCount, player);
        }
        if (this.explodeTimeOut > 0) {
            --this.explodeTimeOut;
        }
        if (this.explodeTimeOut <= 0) {
            this.explodeAnimationState.stop(player);
            this.explodeTimeOut = 60;
            this.explodeFlag = false;
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !oldStack.is(JNEItems.PUMP_CHARGE_SHOTGUN.get()) || !newStack.is(JNEItems.PUMP_CHARGE_SHOTGUN.get());
    }

    @Override
    public boolean isValidRepairItem(ItemStack pStack, ItemStack pRepair) {
        return pRepair.is(JNEItems.STRIDITE.get()) || super.isValidRepairItem(pStack, pRepair);
    }
}
