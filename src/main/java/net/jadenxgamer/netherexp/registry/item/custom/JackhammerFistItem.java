package net.jadenxgamer.netherexp.registry.item.custom;

import net.jadenxgamer.elysium_api.api.keyframe.NonEntityAnimationState;
import net.jadenxgamer.elysium_api.api.util.ResourceKeyRegistryHelper;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.enchantment.JNEEnchantments;
import net.jadenxgamer.netherexp.registry.item.JNEItemRenderer;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNEDamageSources;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.jadenxgamer.netherexp.util.CompatUtil;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class JackhammerFistItem extends ProjectileWeaponItem implements Vanishable, IShotgun {
    public final NonEntityAnimationState punchAnimationState = new NonEntityAnimationState();
    public final NonEntityAnimationState pullAnimationState = new NonEntityAnimationState();
    public final NonEntityAnimationState pullLoopAnimationState = new NonEntityAnimationState();

    private int pullTimeOut;
    private boolean pullFlag;
    private boolean stopPullFlag;
    private int punchTimeOut;
    private boolean punchFlag;


    public JackhammerFistItem(Properties properties) {
        super(properties);
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
        if (level.isClientSide()) {
            if (this.pullFlag) {
                playPullAnimation(player, player.tickCount);
            }
            if (this.stopPullFlag) {
                pullFlag = false;
                pullTimeOut = 30;
                this.pullAnimationState.stop(player);
                stopPullFlag = false;
            }
            if (this.punchFlag) {
                playFireAnimation(player, player.tickCount);
            }
            if (getPulled(stack)) {
                pullLoopAnimationState.startIfStopped(player.tickCount, player);
            } else {
                pullLoopAnimationState.stop(player);
            }
        }
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
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!player.getProjectile(stack).isEmpty() || player.getAbilities().instabuild) {
            pullFlag = true;
            pullTimeOut = 30;
            player.startUsingItem(hand);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), JNESoundEvents.JACKHAMMER_FIST_PULL.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
            return InteractionResultHolder.consume(stack);
        }
        return super.use(level, player, hand);
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int count) {
        int ticksUsed = pStack.getUseDuration() - count;
        if (ticksUsed >= 31 && !getPulled(pStack)) {
            setPulled(pStack, true);
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity user, int useCount) {
        int ticksUsed = stack.getUseDuration() - useCount;
        int cartridge = EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.CARTRIDGE.get(), stack);

        if (ticksUsed < 30) {
            stopPullFlag = true;
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.getServer().getPlayerList().broadcast(null, user.getX(), user.getY(), user.getZ(), 8, level.dimension(),
                        new ClientboundStopSoundPacket(JNESoundEvents.JACKHAMMER_FIST_PULL.get().getLocation(), SoundSource.PLAYERS));
            }
        } else {
            int recoil = EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.RECOIL.get(), stack);

            setPulled(stack, false);
            this.punchFlag = true;
            if (cartridge > 0 && level.random.nextInt((cartridge * 2)) == 0) {
                useProjectile(stack, user);
            } else {
                useProjectile(stack, user);
            }
            Vec3 look = user.getLookAngle();
            Vec3 pushBack = new Vec3(-look.x, -look.y, -look.z).normalize();
            Vec3 knockBack = new Vec3(look.x, look.y, look.z).normalize();
            double recoilPushBonus = (double) recoil / 16;

            Vec3 velocity = user.getDeltaMovement();
            double horizontalSpeed = Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z);
            float damage = (float) Math.min((8.0f + horizontalSpeed * 80), JNEConfigs.JACKHAMMER_FIST_MAX_DAMAGE.get());
            if (damage >= JNEConfigs.JACKHAMMER_FIST_MAX_DAMAGE.get() / 2 && user instanceof Player player) {
                player.getCooldowns().addCooldown(this, damage == JNEConfigs.JACKHAMMER_FIST_MAX_DAMAGE.get() ? (int) damage * 2 : (int) damage);
            }
            double speedPushBonus = 0.1 + horizontalSpeed;
            if (JNEConfigs.DEV_TEST_MODE.get()) {
                NetherExp.LOGGER.info("Jackhammer-Fist Calculated Damage: {} for Velocity: {}", damage, horizontalSpeed);
            }

            Vec3 raycastStart = user.getEyePosition(1.0F);
            Vec3 raycastEnd = raycastStart.add(user.getViewVector(1.0F).scale(4));
            AABB aabb = new AABB(raycastStart, raycastEnd).inflate(1.3, 1.3, 0);
            List<Entity> entities = level.getEntities(user, aabb, EntitySelector.NO_CREATIVE_OR_SPECTATOR);
            for (Entity entity : entities) {
                if (entity instanceof LivingEntity livingEntity && !level.isClientSide()) {
                    livingEntity.push(knockBack.x * (1.2 + speedPushBonus), pushBack.y * (1.2 + speedPushBonus), pushBack.z * (1.2 + speedPushBonus));
                    livingEntity.hurt(user.level().damageSources().source(JNEDamageSources.JACKHAMMER, user), damage);
                }
                int particleCount = (int) (1 + horizontalSpeed * 16);
                for (int i = 0; i < particleCount; i++) {
                    ParticleType<?> particle = ResourceKeyRegistryHelper.getParticleType(new ResourceLocation(CompatUtil.OREGANIZED, "kinetic_hit"));
                    if (particle instanceof SimpleParticleType simpleParticleType) {
                        level.addParticle(simpleParticleType, entity.getRandomX(0.75), entity.getRandomY(), entity.getRandomZ(0.75), level.random.nextGaussian() * 0.02D, level.random.nextGaussian() * 0.02D, level.random.nextGaussian() * 0.02D);
                    }
                }
            }
            if (!level.isClientSide()) {
                ((ServerLevel) level).sendParticles(JNEParticleTypes.JACKHAMMER.get(), raycastEnd.x, raycastEnd.y, raycastEnd.z, 1, 0.0, 0.0, 0.0, 0.0);
            }
            user.push(pushBack.x * (0.8 + recoilPushBonus + speedPushBonus), pushBack.y * (0.8 + recoilPushBonus + speedPushBonus), pushBack.z * (0.8 + recoilPushBonus + speedPushBonus));
            level.playSound(null, user.getX(), user.getY(), user.getZ(), JNESoundEvents.JACKHAMMER_FIST_HIT.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        }
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 72000;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
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

    private void playPullAnimation(Player player, int tickCount) {
        if (this.pullTimeOut == 30) {
            this.pullAnimationState.startIfStopped(tickCount, player);
        }
        if (this.pullTimeOut > 0) {
            --this.pullTimeOut;
        }
        if (this.pullTimeOut <= 0) {
            this.pullAnimationState.stop(player);
            this.pullTimeOut = 30;
            this.pullFlag = false;
        }
    }

    private void playFireAnimation(Player player, int tickCount) {
        if (this.punchTimeOut == 5) {
            this.punchAnimationState.startIfStopped(tickCount, player);
        }
        if (this.punchTimeOut > 0) {
            --this.punchTimeOut;
        }
        if (this.punchTimeOut <= 0) {
            this.punchAnimationState.stop(player);
            this.punchTimeOut = 5;
            this.punchFlag = false;
        }
    }

    @Override
    public @NotNull Predicate<ItemStack> getAllSupportedProjectiles() {
        return (stack) -> stack.is(JNEItems.WRAITHING_FLESH.get());
    }

    public static boolean getPulled(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        return nbt.getBoolean("Pulled");
    }

    public static void setPulled(ItemStack stack, boolean bl) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putBoolean("Pulled", bl);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !oldStack.is(JNEItems.JACKHAMMER_FIST.get()) && !newStack.is(JNEItems.JACKHAMMER_FIST.get());
    }

    @Override
    public boolean isValidRepairItem(ItemStack pStack, ItemStack pRepair) {
        return pRepair.is(JNEItems.STRIDITE.get()) || super.isValidRepairItem(pStack, pRepair);
    }
}
