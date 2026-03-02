package net.jadenxgamer.netherexp.core.item;

import net.jadenxgamer.netherexp.client.rendering.keyframe.ItemAnimationState;
import net.jadenxgamer.netherexp.core.entity.WillOWisp;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.Optional;
import java.util.function.Consumer;

import static net.jadenxgamer.netherexp.config.JNEConfigs.WILL_O_WISP_STACK_SIZE;

public class WillOWispItem extends Item {

    public static final ItemAnimationState held = new ItemAnimationState();

    public WillOWispItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        held.startIfStopped(entity.tickCount, entity);
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        Vec3 raycastStart = user.getEyePosition(1.0F);
        Vec3 raycastEnd = raycastStart.add(user.getViewVector(1.0F).scale(64));
        AABB aabb = new AABB(raycastStart, raycastEnd);
        EntityHitResult hitResult = ProjectileUtil.getEntityHitResult(level, user, raycastStart, raycastEnd, aabb,
                entity -> entity instanceof LivingEntity && entity != user);
        Optional<LivingEntity> target = Optional.empty();

        if (hitResult != null && hitResult.getEntity() instanceof LivingEntity livingEntity && livingEntity.isAlive()) target = Optional.of(livingEntity);

        if (!level.isClientSide() && target.isPresent()) {
            WillOWisp willOWisp = new WillOWisp(user, level, target.get());
            level.addFreshEntity(willOWisp);
            level.playSound(null, user.getX(), user.getY(), user.getZ(),
                    JNESoundEvents.BANSHEE_SHOOT.get(), SoundSource.NEUTRAL, 2.0F,
                    (level.random.nextFloat() - level.random.nextFloat()) * 0.2F + 1.0F);
            if (user instanceof Player player && !player.getAbilities().instabuild) stack.shrink(1);
        }
        else if (target.isEmpty()) level.playSound(null, user.getX(), user.getY(), user.getZ(),
                JNESoundEvents.WISP_HURT.get(), SoundSource.NEUTRAL, 0.5F, 0.4F);

        return super.finishUsingItem(stack, level, user);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 15;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return stack.getOrDefault(DataComponents.MAX_STACK_SIZE, WILL_O_WISP_STACK_SIZE.get());
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }
}
