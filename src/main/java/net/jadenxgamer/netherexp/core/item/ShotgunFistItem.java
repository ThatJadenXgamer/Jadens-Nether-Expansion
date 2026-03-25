package net.jadenxgamer.netherexp.core.item;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.entity.ShotgunPellet;
import net.jadenxgamer.netherexp.core.keys.JNEEnchantments;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.jadenxgamer.netherexp.util.HolderHelper;
import net.jadenxgamer.netherexp.util.VFXHelper;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

import java.awt.*;
import java.util.List;
import java.util.function.Predicate;

public class ShotgunFistItem extends ProjectileWeaponItem {
    public ShotgunFistItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack shotgun = player.getItemInHand(hand);
        ItemStack projectileStack = player.getProjectile(shotgun);
        if (projectileStack.isEmpty() && !player.getAbilities().instabuild) return InteractionResultHolder.pass(shotgun);

        List<ItemStack> draw = draw(shotgun, projectileStack, player);
        var baseVelocity = 1.5f;
        var baseInaccuracy = 20;
        this.shoot(level, player, hand, shotgun, draw, baseVelocity, baseInaccuracy, false, null);

        if (!player.getAbilities().instabuild) shotgun.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                JNESoundEvents.SHOTGUN_USE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        return InteractionResultHolder.success(shotgun);
    }

    protected void shoot(Level level, LivingEntity shooter, InteractionHand hand, ItemStack shotgun, List<ItemStack> projectileItems, float velocity, float inaccuracy, boolean isCrit, @Nullable LivingEntity target) {
        int count = calculateCount(shotgun);
        int cooldown = calculateCooldown(shotgun);
        double selfRecoil = calculateSelfRecoil(shooter, shotgun);

        if (level.isClientSide()) {
            VFXHelper.shotgunScreenShake(shooter.position(), 8.0f, Easing.LINEAR);
        } else {
            if (shooter instanceof Player player) player.getCooldowns().addCooldown(this, cooldown);
            if (!projectileItems.isEmpty()) {
                ItemStack shellStack = projectileItems.getFirst();
                Item shellItem = shellStack.getItem();
                for (int i = 0; i < count; i++) {
                    Vec3 look = shooter.getLookAngle();
                    Projectile pellet;

                    if (shellItem instanceof ShotgunShellItem shotgunShellItem) {
                        pellet = shotgunShellItem.createPellet(level, shooter,
                                shooter.getX(), shooter.getY() + 1.0, shooter.getZ());
                    } else pellet = new ShotgunPellet(shooter.getX(), shooter.getY() + 1.0, shooter.getZ(), level, shooter);

                    pellet.shoot(look.x, look.y, look.z, velocity, inaccuracy);
                    level.addFreshEntity(pellet);
                }
            }
        }

        Vec3 pushBack = new Vec3(-shooter.getLookAngle().x, -shooter.getLookAngle().y, -shooter.getLookAngle().z).normalize();
        shooter.push(pushBack.scale(selfRecoil));
    }

    private double calculateSelfRecoil(LivingEntity shooter, ItemStack shotgun) {
        double base = JNEConfigs.SHOTGUN_SELF_RECOIL.get();
        var recoil = shotgun.getEnchantmentLevel(HolderHelper.getEnchantmentHolder(JNEEnchantments.RECOIL));
        if (recoil > 0) base += ((double) recoil / 12);

        Vec3 raycastStart = shooter.getEyePosition(1.0F);
        Vec3 raycastEnd = raycastStart.add(shooter.getViewVector(1.0F).scale(JNEConfigs.POINT_BLANK_SELF_RECOIL_DISTANCE.get()));
        AABB pointBlankBoundingBox = new AABB(raycastStart, raycastEnd);

        EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(
                shooter.level(), shooter, raycastStart, raycastEnd, pointBlankBoundingBox,
                victim -> victim instanceof LivingEntity && victim != shooter);
        if (entityHitResult != null && entityHitResult.getEntity() instanceof LivingEntity living && living.isAlive()) {
            base += JNEConfigs.POINT_BLANK_SELF_RECOIL_BONUS.get();
            pointBlankParticle(shooter.level(), raycastStart.add(shooter.getViewVector(1.0F).scale(1)));
        }

        return base;
    }

    private int calculateCount(ItemStack shotgun) {
        int base = JNEConfigs.SHOTGUN_FIST_BULLETS.get();
        var volley = shotgun.getEnchantmentLevel(HolderHelper.getEnchantmentHolder(JNEEnchantments.VOLLEY));
        var quickCharge = shotgun.getEnchantmentLevel(HolderHelper.getEnchantmentHolder(Enchantments.QUICK_CHARGE));

        if (volley > 0) base += (volley * 5);
        else if (quickCharge > 0) base -= (quickCharge * 5);
        return base;
    }

    private int calculateCooldown(ItemStack shotgun) {
        int base = JNEConfigs.SHOTGUN_FIST_COOLDOWN.get();
        var volley = shotgun.getEnchantmentLevel(HolderHelper.getEnchantmentHolder(JNEEnchantments.VOLLEY));
        var quickCharge = shotgun.getEnchantmentLevel(HolderHelper.getEnchantmentHolder(Enchantments.QUICK_CHARGE));

        if (volley > 0) base += (volley * 15);
        else if (quickCharge > 0) base -= (quickCharge * 8);
        return base;
    }

    @Override
    protected void shootProjectile(LivingEntity shooter, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity target) {}

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !oldStack.is(JNEItems.SHOTGUN_FIST.get()) || !newStack.is(JNEItems.SHOTGUN_FIST.get());
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return (stack -> stack.is(JNETags.Items.SHOTGUN_SHELLS));
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return super.isValidRepairItem(stack, repairCandidate);
    }

    private void pointBlankParticle(Level level, Vec3 pos) {
        if (!level.isClientSide()) return;
        WorldParticleBuilder.create(JNEParticleTypes.WIND_TRAIL.get())
                .setFullBrightLighting()
                .setColorData(ColorParticleData.create(new Color(0x00FFFF)).build())
                .setScaleData(GenericParticleData.create(0.1f, 1.5f).setEasing(Easing.SINE_OUT).build())
                .setTransparencyData(GenericParticleData.create(1.0f, 0.0f).build())
                .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                .setLifetime(10)
                .disableNoClip()
                .spawn(level, pos.x, pos.y, pos.z);
    }
}