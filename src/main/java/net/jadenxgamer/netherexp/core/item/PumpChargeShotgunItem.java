package net.jadenxgamer.netherexp.core.item;

import net.jadenxgamer.elysium_api.api.client.screen_flash.ScreenFlash;
import net.jadenxgamer.elysium_api.api.util.ClientItemData;
import net.jadenxgamer.netherexp.client.rendering.keyframe.ItemAnimationState;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.config.enums.ProfanityConfig;
import net.jadenxgamer.netherexp.core.keys.JNEDamageSources;
import net.jadenxgamer.netherexp.core.keys.JNEEnchantments;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEDataComponents;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.jadenxgamer.netherexp.util.HolderHelper;
import net.jadenxgamer.netherexp.util.VFXHelper;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

import java.awt.*;
import java.util.List;
import java.util.function.Predicate;

import static net.jadenxgamer.netherexp.config.JNEConfigs.COUNTERFORCE_IFRAMES;
import static net.jadenxgamer.netherexp.core.item.ShotgunFistItem.Client.pointBlankParticle;
import static net.jadenxgamer.netherexp.util.CommonParticles.SMOKE_VARIANTS;

public class PumpChargeShotgunItem extends ProjectileWeaponItem {

    public static final ItemAnimationState fire = new ItemAnimationState();
    public static final ItemAnimationState pump = new ItemAnimationState();
    public static final ItemAnimationState overpump = new ItemAnimationState();
    public static final ItemAnimationState explode = new ItemAnimationState();

    public PumpChargeShotgunItem(Properties properties) {
        super(properties.component(JNEDataComponents.PUMPS, 1));
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide()) return;
        if (getPumps(stack) > 3) {
            overpump.startIfStopped(entity.tickCount, entity);
            if (entity.tickCount % 14 == 0) level.playLocalSound(entity, getAlarmSound(), SoundSource.PLAYERS, 0.7f, 1.0f);
        } else overpump.stop(entity);

        if (entity instanceof Player player) {
            if (player.getCooldowns().isOnCooldown(this)) {
                ClientItemData.getOrCreate(stack).put("isSmoking", true);
            } else ClientItemData.getOrCreate(stack).remove("isSmoking");
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    private SoundEvent getAlarmSound() {
        return JNEConfigs.PROFANITY.get() == ProfanityConfig.UNFILTERED ? JNESoundEvents.PUMP_CHARGE_SHOTGUN_ALARM_PROFANITY.get() : JNESoundEvents.PUMP_CHARGE_SHOTGUN_ALARM.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack shotgun = player.getItemInHand(hand);
        if (player.isShiftKeyDown()) {
            setPumps(shotgun, getPumps(shotgun) + 1);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), JNESoundEvents.SHOTGUN_LOAD.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            pump.stop(player);
            pump.startIfStopped(player.tickCount, player);
            return InteractionResultHolder.pass(shotgun);
        }

        ItemStack projectileStack = player.getProjectile(shotgun);
        List<ItemStack> draw = draw(shotgun, projectileStack, player);
        if (projectileStack.isEmpty() && !player.getAbilities().instabuild) return InteractionResultHolder.pass(shotgun);
        if (getPumps(shotgun) > 3) {
            this.overpumpExplosion(level, player, shotgun, draw);
            if (!player.getAbilities().instabuild) shotgun.hurtAndBreak(5, player, LivingEntity.getSlotForHand(hand));
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    JNESoundEvents.SHOTGUN_USE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
            setPumps(shotgun, 1);
            return InteractionResultHolder.pass(shotgun);
        }

        var baseVelocity = 1.5f;
        var baseInaccuracy = 7 + (getPumps(shotgun) * 5);
        this.shoot(level, player, shotgun, draw, baseVelocity, baseInaccuracy);
        if (!player.getAbilities().instabuild) shotgun.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                JNESoundEvents.SHOTGUN_USE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        setPumps(shotgun, 1);
        return InteractionResultHolder.pass(shotgun);
    }

    protected void shoot(Level level, LivingEntity shooter, ItemStack shotgun, List<ItemStack> projectileItems, float velocity, float inaccuracy) {
        int count = calculateCount(shotgun);
        int cooldown = calculateCooldown(shotgun);
        double selfRecoil = calculateSelfRecoil(shooter, shotgun);
        boolean isBlank = false;

        if (level.isClientSide()) {
            VFXHelper.shotgunScreenShake(shooter.position(), 8.0f, Easing.LINEAR);
            fire.start(shooter.tickCount, shooter);
            ClientItemData.getOrCreate(shotgun).put("shootFlash", true);
        } else {
            if (!projectileItems.isEmpty()) {
                Item shellItem =  projectileItems.getFirst().getItem();
                isBlank = projectileItems.getFirst().is(JNEItems.BLANK_SHOTGUN_SHELL.get());
                for (int i = 0; i < count; i++) {
                    Vec3 look = shooter.getLookAngle();
                    Projectile pellet;

                    if (shellItem instanceof ShotgunShellItem shotgunShellItem) {
                        pellet = shotgunShellItem.createPellet(level, shooter,
                                shooter.getX(), shooter.getY() + 1.0, shooter.getZ());
                    } else break;

                    pellet.shoot(look.x, look.y, look.z, velocity, inaccuracy);
                    level.addFreshEntity(pellet);
                }
            }
        }

        var counterforce = HolderHelper.getEnchantmentLevel(JNEEnchantments.COUNTERFORCE, shotgun);
        Vec3 pushBack = new Vec3(-shooter.getLookAngle().x, -shooter.getLookAngle().y, -shooter.getLookAngle().z).normalize();
        if (counterforce > 0 && !shooter.onGround() && (shooter.fallDistance <= 0.0 || shooter.isInFluidType())) {
            pushBack = new Vec3(shooter.getLookAngle().x, shooter.getLookAngle().y, shooter.getLookAngle().z).normalize();
            if (!isBlank) cooldown += 30;
            if (COUNTERFORCE_IFRAMES.get() > 0) {
                shooter.invulnerableTime = COUNTERFORCE_IFRAMES.get();
                level.playSound(null, shooter.blockPosition(), JNESoundEvents.SHOTGUN_COUNTERFORCE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                ScreenFlash.triggerScreenFlash(5, COUNTERFORCE_IFRAMES.get() / 4, 5, 0x33FF0000, false, true);
            }
        }
        if (shooter instanceof Player player) player.getCooldowns().addCooldown(this, cooldown);
        shooter.push(pushBack.scale(selfRecoil));
    }

    protected void overpumpExplosion(Level level, LivingEntity shooter, ItemStack shotgun, List<ItemStack> projectileItems) {
        var cooldown = 80;
        var damage = 5;
        boolean isBlank = false;
        if (level.isClientSide()) {
            VFXHelper.explosionScreenShake(shooter.position(), 16.0f, Easing.LINEAR);
            explode.start(shooter.tickCount, shooter);
            ClientItemData.getOrCreate(shotgun).put("shootFlash", true);
        } else {
            if (!projectileItems.isEmpty()) {
                Item shellItem = projectileItems.getFirst().getItem();
                isBlank = projectileItems.getFirst().is(JNEItems.BLANK_SHOTGUN_SHELL.get());
                if (shellItem instanceof ShotgunShellItem) {
                    level.explode(shooter, shooter.getX(), shooter.getY(), shooter.getZ(), 4, Level.ExplosionInteraction.NONE);
                    ((ServerLevel)level).sendParticles(JNEParticleTypes.RED_EXPLOSION_EMITTER.get(), shooter.getX(), shooter.getY(), shooter.getZ(), 1, 0.0, 0.0, 0.0, 0);
                    damage = 10;
                }
            }
        }

        var counterforce = HolderHelper.getEnchantmentLevel(JNEEnchantments.COUNTERFORCE, shotgun);
        Vec3 pushBack = new Vec3(-shooter.getLookAngle().x, -shooter.getLookAngle().y, -shooter.getLookAngle().z).normalize();
        boolean dashCancel = false;
        if (counterforce > 0 && !shooter.onGround() && (shooter.fallDistance <= 0.0 || shooter.isInFluidType())) {
            pushBack = new Vec3(shooter.getLookAngle().x, shooter.getLookAngle().y, shooter.getLookAngle().z).normalize();
            dashCancel = true;
            if (!isBlank) cooldown += 100;
            if (COUNTERFORCE_IFRAMES.get() > 0) {
                level.playSound(null, shooter.blockPosition(), JNESoundEvents.SHOTGUN_COUNTERFORCE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                ScreenFlash.triggerScreenFlash(5, 1, 5, 0x33FF0000, false, true);
            }
        }
        if (!dashCancel) shooter.hurt(level.damageSources().source(JNEDamageSources.SHOTGUN_EXPLOSION, shooter), damage);
        if (shooter instanceof Player player) player.getCooldowns().addCooldown(this, cooldown);
        shooter.push(pushBack.scale(1.5));
    }

    private double calculateSelfRecoil(LivingEntity shooter, ItemStack shotgun) {
        double base = JNEConfigs.SHOTGUN_SELF_RECOIL.get();
        var recoil = HolderHelper.getEnchantmentLevel(JNEEnchantments.RECOIL, shotgun);
        if (recoil > 0) base += ((double) recoil / 10);

        Vec3 raycastStart = shooter.getEyePosition(1.0F);
        Vec3 raycastEnd = raycastStart.add(shooter.getViewVector(1.0F).scale(JNEConfigs.POINT_BLANK_SELF_RECOIL_DISTANCE.get()));
        AABB pointBlankBoundingBox = new AABB(raycastStart, raycastEnd);

        EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(
                shooter.level(), shooter, raycastStart, raycastEnd, pointBlankBoundingBox,
                victim -> victim instanceof LivingEntity && victim != shooter);
        if (entityHitResult != null && entityHitResult.getEntity() instanceof LivingEntity living && living.isAlive()) {
            base += JNEConfigs.POINT_BLANK_SELF_RECOIL_BONUS.get();
            if (shooter.level().isClientSide) pointBlankParticle(shooter.level(), raycastStart.add(shooter.getViewVector(1.0F).scale(1)));
        }

        return base + ((double) getPumps(shotgun) / 10);
    }

    private int calculateCount(ItemStack shotgun) {
        int base = JNEConfigs.PUMP_CHARGE_SHOTGUN_BULLETS.get();
        var volley = HolderHelper.getEnchantmentLevel(JNEEnchantments.VOLLEY, shotgun);
        var quickCharge = HolderHelper.getEnchantmentLevel(Enchantments.QUICK_CHARGE, shotgun);

        if (volley > 0) base += (volley * 2);
        else if (quickCharge > 0) base -= (quickCharge * 2);
        return base * getPumps(shotgun);
    }

    private int calculateCooldown(ItemStack shotgun) {
        int base = JNEConfigs.PUMP_CHARGE_SHOTGUN_COOLDOWN.get();
        var volley = HolderHelper.getEnchantmentLevel(JNEEnchantments.VOLLEY, shotgun);
        var quickCharge = HolderHelper.getEnchantmentLevel(Enchantments.QUICK_CHARGE, shotgun);

        if (volley > 0) base += (volley * 10);
        else if (quickCharge > 0) base -= (quickCharge * 4);
        return base;
    }

    @Override
    protected void shootProjectile(LivingEntity shooter, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity target) {}

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !oldStack.is(JNEItems.PUMP_CHARGE_SHOTGUN.get()) || !newStack.is(JNEItems.PUMP_CHARGE_SHOTGUN.get());
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return (stack -> stack.is(JNETags.Items.SHOTGUN_SHELLS));
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.is(Items.NETHERITE_SCRAP);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return super.supportsEnchantment(stack, enchantment) || enchantment.is(Enchantments.QUICK_CHARGE);
    }

    public static int getPumps(ItemStack stack) {
        return stack.getOrDefault(JNEDataComponents.PUMPS, 1);
    }

    public static void setPumps(ItemStack stack, int pumps) {
        int clamped = Math.max(1, Math.min(4, pumps));
        stack.set(JNEDataComponents.PUMPS, clamped);
    }

    @Override
    public ItemStack getDefaultCreativeAmmo(@Nullable Player player, ItemStack projectileWeaponItem) {
        return JNEItems.SHOTGUN_SHELL.get().getDefaultInstance();
    }

    @OnlyIn(Dist.CLIENT)
    public static class Client {

        public static void shotgunFlashParticle(Level level, RandomSource random, double x, double y, double z) {
            LodestoneWorldParticleType particle = JNEParticleTypes.PUMP_SHOTGUN_FLASH.get();
            WorldParticleBuilder.create(particle)
                    .setFullBrightLighting()
                    .setScaleData(GenericParticleData.create(0.01f, 1.45f, 0.0f).build())
                    .setSpinData(SpinParticleData.createRandomDirection(random, 0.0f, 2.0f)
                            .setCoefficient(0.7f).setEasing(Easing.SINE_IN).build())
                    .setTransparencyData(GenericParticleData.create(1.0f).build())
                    .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                    .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                    .setLifetime(5)
                    .enableNoClip()
                    .addMotion(0.0f, 0.0f, 0.0f)
                    .spawn(level, x, y, z);
        }

        public static void cooldownParticle(LivingEntity user, Level level, RandomSource random, double x, double y, double z) {
            LodestoneWorldParticleType particle = SMOKE_VARIANTS[random.nextInt(SMOKE_VARIANTS.length)];
            var startColor = new Color(0xB21B1B);
            var endColor = new Color(0x231111);
            var look = user.getLookAngle();

            float pushFactor = 0.1f;
            double motionX = (random.nextDouble() / 10) + look.x * pushFactor;
            double motionY = (-0.09 + random.nextDouble() / 64) + look.y * pushFactor;
            double motionZ = (random.nextDouble() / 10) + look.z * pushFactor;

            WorldParticleBuilder.create(particle)
                    .setFullBrightLighting()
                    .setScaleData(GenericParticleData.create(Mth.randomBetween(random, 0.23f, 0.3f), 0.75f).build())
                    .setTransparencyData(GenericParticleData.create(0.75f, 0.4f, 0.0f).setEasing(Easing.BOUNCE_OUT).build())
                    .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                    .setColorData(ColorParticleData.create(startColor, endColor).setEasing(Easing.SINE_IN_OUT).build())
                    .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                    .setLifetime(Mth.randomBetweenInclusive(random, 10, 15))
                    .disableNoClip()
                    .addMotion(motionX, motionY, motionZ)
                    .spawn(level, x, y, z);
        }
    }
}