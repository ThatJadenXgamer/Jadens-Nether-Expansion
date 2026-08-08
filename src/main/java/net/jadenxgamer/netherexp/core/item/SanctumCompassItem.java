package net.jadenxgamer.netherexp.core.item;

import net.jadenxgamer.netherexp.core.item.components.LocatorCompass;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNECriteriaTriggers;
import net.jadenxgamer.netherexp.registry.JNEDataComponents;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class SanctumCompassItem extends ProjectileWeaponItem {

    private static final int ACTIVATION_DURATION = 4800;

    public SanctumCompassItem(Properties properties) {
        super(properties.component(JNEDataComponents.LOCATOR_COMPASS, LocatorCompass.DEFAULT));
    }

    @Override
    public @NotNull Predicate<ItemStack> getAllSupportedProjectiles() {
        return stack -> stack.is(JNEItems.WRAITHING_FLESH.get());
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (level.isClientSide()) return;

        LocatorCompass compass = stack.get(JNEDataComponents.LOCATOR_COMPASS);
        if (compass == null || !compass.isActive()) return;
        int newTime = compass.activeTime() - 1;
        if (newTime <= 0) {
            stack.set(JNEDataComponents.LOCATOR_COMPASS, new LocatorCompass(compass.structurePos(), compass.dimension(), compass.bound(), false, 0));
            stack.remove(DataComponents.CUSTOM_MODEL_DATA);
            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.LODESTONE_PLACE, SoundSource.PLAYERS, 0.7f, 1.0f);
        } else {
            stack.set(JNEDataComponents.LOCATOR_COMPASS, new LocatorCompass(compass.structurePos(), compass.dimension(), compass.bound(), true, newTime));
            if (entity.tickCount % 40 == 0 && isSelected) level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), JNESoundEvents.COMPASS_TICK.get(), SoundSource.PLAYERS, 0.7f, 1.0f);
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        LocatorCompass compass = stack.get(JNEDataComponents.LOCATOR_COMPASS);
        if (compass == null || compass.isActive()) return InteractionResultHolder.pass(stack);
        BlockPos pos = findStructure(level, player, stack);
        if (pos == null) return InteractionResultHolder.pass(stack);
        ItemStack ammo = player.getProjectile(stack);
        if (ammo.isEmpty() && !player.getAbilities().instabuild) return InteractionResultHolder.pass(stack);
        if (!player.getAbilities().instabuild) ammo.shrink(1);
        if (!activateCompass(stack, level, player, pos)) return InteractionResultHolder.pass(stack);

        player.swing(hand, true);
        return InteractionResultHolder.success(stack);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        if (stack.getCount() != 1 || action != ClickAction.SECONDARY) return false;
        LocatorCompass compass = stack.get(JNEDataComponents.LOCATOR_COMPASS);
        if (compass == null || compass.isActive()) return false;
        ItemStack other = slot.getItem();
        if (other.isEmpty() || !other.is(JNEItems.WRAITHING_FLESH.get())) return false;
        BlockPos pos = findStructure(player.level(), player, stack);
        if (pos == null) return false;
        other.shrink(1);
        slot.set(other);

        return activateCompass(stack, player.level(), player, pos);
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess slotAccess) {
        if (stack.getCount() != 1 || action != ClickAction.SECONDARY) return false;
        LocatorCompass compass = stack.get(JNEDataComponents.LOCATOR_COMPASS);
        if (compass == null || compass.isActive()) return false;
        if (other.isEmpty() || !other.is(JNEItems.WRAITHING_FLESH.get())) return false;
        BlockPos pos = findStructure(player.level(), player, stack);
        if (pos == null) return false;
        other.shrink(1);
        slotAccess.set(other);

        return activateCompass(stack, player.level(), player, pos);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        LocatorCompass compass = stack.get(JNEDataComponents.LOCATOR_COMPASS);
        if (compass != null && compass.bound()) tooltip.add(Component.translatable("sanctum_compass.tracking").withStyle(ChatFormatting.GRAY));
        if (compass == null || !compass.isActive()) {
            if (!tooltip.isEmpty()) tooltip.add(Component.empty());
            tooltip.add(Component.translatable("sanctum_compass.to_activate").withStyle(ChatFormatting.BLUE));
            tooltip.add(Component.translatable("item.netherexp.wraithing_flesh").withStyle(ChatFormatting.DARK_PURPLE));
        }
    }

    @Nullable
    public static GlobalPos getStructurePosition(ItemStack stack) {
        LocatorCompass compass = stack.get(JNEDataComponents.LOCATOR_COMPASS);
        if (compass == null || !compass.bound()) return null;
        Optional<BlockPos> pos = compass.structurePos();
        Optional<ResourceLocation> dim = compass.dimension();
        return pos.isPresent() && dim.isPresent() ? GlobalPos.of(ResourceKey.create(Registries.DIMENSION, dim.get()), pos.get()) : null;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !oldStack.is(this) || !newStack.is(this);
    }

    @Override
    protected void shootProjectile(LivingEntity shooter, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity target) {}

    @Nullable
    private BlockPos findStructure(Level level, Player player, ItemStack stack) {
        if (!(level instanceof ServerLevel serverLevel)) return null;
        LocatorCompass compass = stack.get(JNEDataComponents.LOCATOR_COMPASS);
        if (compass == null) return null;
        return compass.bound() && compass.structurePos().isPresent() && compass.dimension().isPresent() ? compass.structurePos().get()
                : serverLevel.findNearestMapStructure(JNETags.Structures.SANCTUM_COMPASS_LOCATED, player.blockPosition(), 100, false);
    }

    private boolean activateCompass(ItemStack stack, Level level, Player player, BlockPos pos) {
        if (level.isClientSide()) return false;
        stack.set(JNEDataComponents.LOCATOR_COMPASS, new LocatorCompass(Optional.of(pos), Optional.of(level.dimension().location()), true, true, ACTIVATION_DURATION));
        stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(1));
        if (player instanceof ServerPlayer sp) JNECriteriaTriggers.ACTIVATE_SANCTUM_COMPASS.get().trigger(sp);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS, 0.7f, 1.0f);
        player.awardStat(Stats.ITEM_USED.get(this));
        return true;
    }

    public static void registerProperties() {
        ItemProperties.register(JNEItems.SANCTUM_COMPASS.get(),
                ResourceLocation.withDefaultNamespace("angle"),
                (stack, level, entity, seed) -> {
                    if (!(entity instanceof LivingEntity living)) return 0.0f;
                    LocatorCompass compass = stack.get(JNEDataComponents.LOCATOR_COMPASS);
                    if (compass == null || !compass.isActive() || !compass.bound()) return 0.0f;
                    GlobalPos globalPos = getStructurePosition(stack);
                    if (globalPos == null) return 0.0f;
                    BlockPos target = globalPos.pos();
                    double dx = target.getX() + 0.5 - living.getX();
                    double dz = target.getZ() + 0.5 - living.getZ();
                    double angle = Math.atan2(dz, dx) - Math.toRadians(living.getYRot());
                    return (float) Mth.positiveModulo(angle / (2.0 * Math.PI), 1.0);
                }
        );
    }
}