package net.jadenxgamer.netherexp.registry.item.custom;

import net.jadenxgamer.netherexp.registry.entity.custom.SoulBulletEntity;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.Vanishable;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class ShotgunFistItem extends RangedWeaponItem implements Vanishable {
    public ShotgunFistItem(Settings settings) {
        super(settings);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity) {
            boolean creative = playerEntity.getAbilities().creativeMode;
            ItemStack projectileStack = playerEntity.getProjectileType(stack);
            if (!projectileStack.isEmpty() || creative) {
                if (projectileStack.isEmpty()) {
                    projectileStack = new ItemStack(JNEItems.WRAITHING_FLESH);
                }
                boolean bl = creative && projectileStack.isOf(JNEItems.WRAITHING_FLESH);
                if (!world.isClient) {
                    int count = MathHelper.nextInt(world.random, 8, 16);
                    for (int i = 0; i < count; i++) {
                        SoulBulletEntity soulBullet = new SoulBulletEntity(world, user);
                        soulBullet.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, 1.0F, 16);
                        world.spawnEntity(soulBullet);
                    }
                }
                if (!bl && !playerEntity.getAbilities().creativeMode) {
                    projectileStack.decrement(1);
                    if (projectileStack.isEmpty()) {
                        playerEntity.getInventory().removeOne(projectileStack);
                        stack.damage(1, user, stackUser -> stackUser.sendToolBreakStatus(user.preferredHand));
                    }
                }

                world.playSound(null, user.getBlockPos(), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.PLAYERS, 1.0f, 1.0f);
                playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            }
        }
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        boolean bl = !user.getProjectileType(itemStack).isEmpty();
        if (!user.getAbilities().creativeMode && !bl) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return stack -> stack.isOf(JNEItems.WRAITHING_FLESH);
    }

    @Override
    public int getRange() {
        return 15;
    }
}
