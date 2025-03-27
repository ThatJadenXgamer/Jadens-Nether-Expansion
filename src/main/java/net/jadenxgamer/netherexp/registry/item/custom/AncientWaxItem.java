package net.jadenxgamer.netherexp.registry.item.custom;

import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.entity.custom.FalseCarcass;
import net.jadenxgamer.netherexp.registry.entity.custom.WillOWisp;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class AncientWaxItem extends Item {
    public AncientWaxItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        Vec3 raycastStart = user.getEyePosition(1.0F);
        Vec3 raycastEnd = raycastStart.add(user.getViewVector(1.0F).scale(5));
        AABB aabb = new AABB(raycastStart, raycastEnd);
        EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(level, user, raycastStart, raycastEnd, aabb, (entity) -> entity instanceof LivingEntity && entity != user);
        LivingEntity target = null;
        if (entityHitResult != null && entityHitResult.getEntity() instanceof LivingEntity livingEntity && livingEntity.isAlive()) {
            target = livingEntity;
        }
        if (!level.isClientSide() && target instanceof Skeleton skeleton) {
            FalseCarcass falseCarcass = skeleton.convertTo(JNEEntityType.FALSE_CARCASS.get(), false);
            if (falseCarcass != null && level instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 32; i++) {
                    serverLevel.sendParticles(JNEParticleTypes.ANCIENT_WAX_CLOUD.get(), skeleton.getRandomX(0.5), skeleton.getRandomY() - 0.25, skeleton.getRandomZ(0.5), 1, 0.0, 0.0, 0.0, 0.0);
                }
                falseCarcass.finalizeSpawn(serverLevel, level.getCurrentDifficultyAt(skeleton.blockPosition()), MobSpawnType.CONVERSION, new Zombie.ZombieGroupData(false, false), null);
                falseCarcass.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
                falseCarcass.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 2));
                falseCarcass.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1));
                if (skeleton.hasCustomName()) {
                    falseCarcass.setCustomName(skeleton.getCustomName());
                }
            }
            level.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.SKELETON_DEATH, SoundSource.NEUTRAL, 2.0F, (level.random.nextFloat() - level.random.nextFloat()) * 0.2F + 1.0F);
            user.awardStat(Stats.ITEM_USED.get(this));
            if (!user.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
            return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide);
        }
        return InteractionResultHolder.pass(user.getItemInHand(hand));
    }
}
