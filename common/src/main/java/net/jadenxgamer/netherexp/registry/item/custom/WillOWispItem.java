package net.jadenxgamer.netherexp.registry.item.custom;

import net.jadenxgamer.netherexp.registry.entity.custom.WillOWisp;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class WillOWispItem extends Item {
    public WillOWispItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        Vec3 raycastStart = user.getEyePosition(1.0F);
        Vec3 raycastEnd = raycastStart.add(user.getViewVector(1.0F).scale(14));
        AABB aabb = new AABB(raycastStart, raycastEnd);
        EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(level, user, raycastStart, raycastEnd, aabb, (entity) -> entity instanceof LivingEntity && entity != user);
        LivingEntity target = null;
        if (entityHitResult != null && entityHitResult.getEntity() instanceof LivingEntity livingEntity && livingEntity.isAlive()) {
            target = livingEntity;
        }
        if (!level.isClientSide() && target != null) {
            WillOWisp willOWisp = new WillOWisp(user, level, target, 0.35f);
            level.addFreshEntity(willOWisp);
            level.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            user.awardStat(Stats.ITEM_USED.get(this));
            if (!user.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
        }
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide);
    }
}
