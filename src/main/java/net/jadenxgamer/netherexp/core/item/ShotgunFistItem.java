package net.jadenxgamer.netherexp.core.item;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.entity.ShotgunPellet;
import net.jadenxgamer.netherexp.core.keys.JNEEnchantments;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.jadenxgamer.netherexp.util.HolderHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

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
        if (level instanceof ServerLevel serverLevel) {
            List<ItemStack> draw = draw(shotgun, projectileStack, player);
            var baseVelocity = 1.5f;
            var baseInaccuracy = 20;
            this.shoot(serverLevel, player, hand, shotgun, draw, baseVelocity, baseInaccuracy, false, null);
        }
        if (!player.getAbilities().instabuild) shotgun.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                JNESoundEvents.SHOTGUN_USE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        return InteractionResultHolder.success(shotgun);
    }

    @Override
    protected void shoot(ServerLevel level, LivingEntity shooter, InteractionHand hand, ItemStack shotgun, List<ItemStack> projectileItems, float velocity, float inaccuracy, boolean isCrit, @Nullable LivingEntity target) {
        super.shoot(level, shooter, hand, shotgun, projectileItems, velocity, inaccuracy, isCrit, target);
        int count = calculateCount(shotgun);

        if (level.isClientSide) {
        } else {
            for (int i = 0; i < count; i++) {
                Vec3 look = shooter.getLookAngle();
                ShotgunPellet pellet = new ShotgunPellet(look, level, shooter);
                pellet.shoot(look.x, look.y, look.z, velocity, inaccuracy);
                level.addFreshEntity(pellet);
            }
        }
    }

    private int calculateCount(ItemStack shotgun) {
        int base = JNEConfigs.SHOTGUN_FIST_BULLETS.get();
        var volley = shotgun.getEnchantmentLevel(HolderHelper.getEnchantmentHolder(JNEEnchantments.VOLLEY));
        var quickCharge = shotgun.getEnchantmentLevel(HolderHelper.getEnchantmentHolder(Enchantments.QUICK_CHARGE));

        if (volley > 0) base += (volley * 5);
        else if (quickCharge > 0) base -= (quickCharge * 5);
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
}
