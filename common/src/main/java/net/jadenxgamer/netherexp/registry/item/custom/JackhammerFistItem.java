package net.jadenxgamer.netherexp.registry.item.custom;

import net.jadenxgamer.netherexp.registry.enchantment.JNEEnchantments;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNEDamageSources;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public class JackhammerFistItem extends ProjectileWeaponItem implements Vanishable {
    public JackhammerFistItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull Predicate<ItemStack> getAllSupportedProjectiles() {
        return (stack) -> stack.is(JNEItems.WRAITHING_FLESH.get());
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!player.getProjectile(stack).isEmpty() || player.getAbilities().instabuild) {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(stack);
        }
        return super.use(level, player, hand);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity user, int i) {
        int recoil = EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.RECOIL.get(), stack);
        int barrage = EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.BARRAGE.get(), stack);

        useProjectile(stack, user);
        Vec3 look = user.getLookAngle();
        Vec3 pushBack = new Vec3(-look.x, -look.y, -look.z).normalize();
        double horizontalSpeed = Math.sqrt(user.getDeltaMovement().x * user.getDeltaMovement().x + user.getDeltaMovement().z * user.getDeltaMovement().z);
        double recoilPushBonus = (double) recoil / 16;
        double speedMultiplier = 16.0 + ((double) barrage / 4);
        float damage = (float) (7.0 + (horizontalSpeed * speedMultiplier));

        Vec3 raycastStart = user.getEyePosition(1.0F);
        Vec3 raycastEnd = raycastStart.add(user.getViewVector(1.0F).scale(5));
        AABB aabb = new AABB(raycastStart, raycastEnd).inflate(2.0);
        List<Entity> entities = level.getEntities(user, aabb, EntitySelector.NO_CREATIVE_OR_SPECTATOR);
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.hurt(user.level().damageSources().source(JNEDamageSources.JACKHAMMER, user), damage);
            }
        }
        user.push(pushBack.x * (1.5 + recoilPushBonus), pushBack.y * (1.5 + recoilPushBonus), pushBack.z * (1.5 + recoilPushBonus));
        level.playSound(null, user.getX(), user.getY(), user.getZ(), JNESoundEvents.SHOTGUN_USE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 72000;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
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
}
