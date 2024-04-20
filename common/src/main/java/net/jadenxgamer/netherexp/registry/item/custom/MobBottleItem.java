package net.jadenxgamer.netherexp.registry.item.custom;

import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.entity.custom.Bottleable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class MobBottleItem extends Item {
    private final SoundEvent emptySound;

    public MobBottleItem(SoundEvent emptySound, Properties properties) {
        super(properties);
        this.emptySound = emptySound;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        ItemStack stack = context.getItemInHand();
        BlockPos pos = context.getClickedPos();
        if (level instanceof ServerLevel) {
            this.spawn((ServerLevel) level, stack, pos);
            this.playEmptySound(level, pos);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
            assert player != null;
            if (!player.isCreative()) {
                player.setItemInHand(context.getHand(), ItemUtils.createFilledResult(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useOn(context);
    }

    protected void playEmptySound(LevelAccessor levelAccessor, BlockPos blockPos) {
        levelAccessor.playSound(null, blockPos, this.emptySound, SoundSource.NEUTRAL, 1.0F, 1.0F);
    }

    private void spawn(ServerLevel serverLevel, ItemStack itemStack, BlockPos blockPos) {
        Entity entity = JNEEntityType.WISP.get().spawn(serverLevel, itemStack, null, blockPos, MobSpawnType.BUCKET, true, false);
        if (entity instanceof Bottleable bottleable) {
            bottleable.loadFromBottleTag(itemStack.getOrCreateTag());
            bottleable.setFromBottle(true);
        }
    }
}
