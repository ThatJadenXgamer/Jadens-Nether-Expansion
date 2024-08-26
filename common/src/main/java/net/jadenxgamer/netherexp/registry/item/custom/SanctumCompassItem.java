package net.jadenxgamer.netherexp.registry.item.custom;

import com.mojang.serialization.DataResult;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class SanctumCompassItem extends ProjectileWeaponItem {

    public static final String STRUCTURE_POS = "StructurePos";
    public static final String STRUCTURE_DIMENSION = "StructureDimension";
    public static final String TRACKED_STRUCTURE = "TrackedStructure";
    public static final String IS_ACTIVE = "IsActive";
    private int cooldown;

    public SanctumCompassItem(Properties properties) {
        super(properties);
        cooldown = 4800;
    }

    @Override
    public @NotNull Predicate<ItemStack> getAllSupportedProjectiles() {
        return (stack) -> stack.is(JNEItems.WRAITHING_FLESH.get());
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected) {
        CompoundTag nbt = stack.getOrCreateTag();
        boolean isActive = nbt.getBoolean(IS_ACTIVE);
        if (!level.isClientSide() && isActive) {
            --this.cooldown;
            if (cooldown <= 0) {
                level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.LODESTONE_PLACE, SoundSource.PLAYERS, 0.7f, 1.0f);
                nbt.remove("CustomModelData");
                nbt.putBoolean(IS_ACTIVE, false);
                this.cooldown = 4800;
            }
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag nbt = stack.getOrCreateTag();
        boolean isActive = nbt.getBoolean(IS_ACTIVE);
        boolean trackedStructure = nbt.getBoolean(TRACKED_STRUCTURE);
        if (!isActive) {
            player.startUsingItem(hand);
            if (level instanceof ServerLevel serverLevel) {
                if (!trackedStructure) {
                    BlockPos structurePos = serverLevel.findNearestMapStructure(JNETags.Structures.SANCTUM_COMPASS_LOCATED, player.blockPosition(), 100, false);
                    if (structurePos != null) {
                        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS, 0.7f, 1.0f);
                        player.awardStat(Stats.ITEM_USED.get(this));
                        player.swing(hand, true);

                        nbt.putBoolean(TRACKED_STRUCTURE, true);
                        nbt.put(STRUCTURE_POS, NbtUtils.writeBlockPos(structurePos));
                        DataResult<Tag> structureDimension = Level.RESOURCE_KEY_CODEC.encodeStart(NbtOps.INSTANCE, level.dimension());
                        structureDimension.result().ifPresent((currentDimension) -> nbt.put(STRUCTURE_DIMENSION, currentDimension));
                        this.useWraithingFlesh(stack, player);
                        nbt.putInt("CustomModelData", 1);
                        nbt.putBoolean(IS_ACTIVE, true);
                        return InteractionResultHolder.success(stack);
                    }
                } else {
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS, 0.7f, 1.0f);
                    this.useWraithingFlesh(stack, player);
                    nbt.putInt("CustomModelData", 1);
                    nbt.putBoolean(IS_ACTIVE, true);
                }
            }
        }

        return InteractionResultHolder.pass(stack);
    }

    @Nullable
    public static GlobalPos getStructurePosition(CompoundTag nbt) {
        boolean pos = nbt.contains(STRUCTURE_POS);
        boolean dimension = nbt.contains(STRUCTURE_DIMENSION);
        if (pos && dimension) {
            Optional<ResourceKey<Level>> currentDimension = getStructureDimension(nbt);
            if (currentDimension.isPresent()) {
                BlockPos blockPos = NbtUtils.readBlockPos(nbt.getCompound(STRUCTURE_POS));
                return GlobalPos.of(currentDimension.get(), blockPos);
            }
        }

        return null;
    }

    private static Optional<ResourceKey<Level>> getStructureDimension(CompoundTag arg) {
        return Level.RESOURCE_KEY_CODEC.parse(NbtOps.INSTANCE, arg.get(STRUCTURE_DIMENSION)).result();
    }

    private void useWraithingFlesh(ItemStack stack, LivingEntity user) {
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

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.getBoolean(TRACKED_STRUCTURE)) {
            tooltip.add(Component.translatable("sanctum_compass.tracking").withStyle(ChatFormatting.GRAY));
        }
        if (!nbt.getBoolean(IS_ACTIVE)) {
            tooltip.add((Component.empty()));
            tooltip.add(Component.translatable("sanctum_compass.to_activate").withStyle(ChatFormatting.BLUE));
            tooltip.add(Component.translatable("item.netherexp.wraithing_flesh").withStyle(ChatFormatting.DARK_PURPLE));
        }
    }
}
