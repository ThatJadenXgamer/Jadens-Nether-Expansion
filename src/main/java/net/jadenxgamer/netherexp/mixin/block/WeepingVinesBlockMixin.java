package net.jadenxgamer.netherexp.mixin.block;

import net.jadenxgamer.netherexp.core.block.interfaces.GlowsporesApplicable;
import net.jadenxgamer.netherexp.core.item.GlowsporesItem;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.WeepingVinesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.ItemAbilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WeepingVinesBlock.class)
public abstract class WeepingVinesBlockMixin extends GrowingPlantHeadBlock implements GlowsporesApplicable {

    @Unique private static final BooleanProperty SPORING = BooleanProperty.create("sporing");

    protected WeepingVinesBlockMixin(Properties properties, Direction growthDirection, VoxelShape shape, boolean scheduleFluidTicks, double growPerTickProbability) {
        super(properties, growthDirection, shape, scheduleFluidTicks, growPerTickProbability);
    }

    @Inject(
            method = "<init>",
            at = @At(value = "TAIL")
    )
    private void netherexp$init(CallbackInfo ci) {
        this.registerDefaultState(this.defaultBlockState().setValue(SPORING, false));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (state.getValue(SPORING) && stack.canPerformAction(ItemAbilities.SHEARS_HARVEST)) {

            level.playSound(null, pos, JNESoundEvents.GLOWSPORES_SHEAR.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            level.setBlock(pos, state.cycle(SPORING), Block.UPDATE_ALL);
            level.gameEvent(player, GameEvent.SHEAR, pos);
            this.dropGlowspores(level, pos, Direction.UP);
            if (!player.getAbilities().instabuild) stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(SPORING) && random.nextInt(5) == 0) {
            double x = pos.getX() + random.nextDouble();
            double y = pos.getY() + 0.5;
            double z = pos.getZ() + random.nextDouble();
            GlowsporesItem.glowsporeParticle(level, random, x, y, z, JNEParticleTypes.NIGHTSPORE.get(), true);
        }
        super.animateTick(state, level, pos, random);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(SPORING);
    }

    @Override
    public Item glowsporeOfBlock() {
        return JNEItems.LIGHTSPORES.get();
    }

    @Override
    public Property<?> affectedProperty() {
        return SPORING;
    }

    @Override
    public boolean canSporesBeApplied(BlockState state) {
        return !state.getValue(SPORING);
    }
}
