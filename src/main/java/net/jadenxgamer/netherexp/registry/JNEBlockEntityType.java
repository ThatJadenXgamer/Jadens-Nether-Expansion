package net.jadenxgamer.netherexp.registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.block.entity.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class JNEBlockEntityType {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, NetherExp.MOD_ID);

    public static final Supplier<BlockEntityType<SuspiciousSoulSandBlockEntity>> SUSPICIOUS_SOUL_SAND = BLOCK_ENTITY_TYPES.register("suspicious_soul_sand", () ->
            BlockEntityType.Builder.of(SuspiciousSoulSandBlockEntity::new, JNEBlocks.SUSPICIOUS_SOUL_SAND.get()).build(null));

    public static final Supplier<BlockEntityType<DiscernmentGlassBlockEntity>> DISCERNMENT_GLASS = BLOCK_ENTITY_TYPES.register("discernment_glass", () ->
            BlockEntityType.Builder.of(DiscernmentGlassBlockEntity::new, JNEBlocks.DISCERNMENT_GLASS.get()).build(null));

    public static final Supplier<BlockEntityType<JNECampfireBlockEntity>> JNE_CAMPFIRE = BLOCK_ENTITY_TYPES.register("jne_campfire", () ->
            BlockEntityType.Builder.of(JNECampfireBlockEntity::new, JNEBlocks.TREACHEROUS_CAMPFIRE.get()).build(null));

    public static final Supplier<BlockEntityType<DriftingSoulsBlockEntity>> DRIFTING_SOULS = BLOCK_ENTITY_TYPES.register("drifting_souls", () ->
            BlockEntityType.Builder.of(DriftingSoulsBlockEntity::new, JNEBlocks.DRIFTING_SOULS.get()).build(null));

    public static final Supplier<BlockEntityType<PetrifiedSwirlsBlockEntity>> PETRIFIED_SWIRLS = BLOCK_ENTITY_TYPES.register("petrified_swirls", () ->
            BlockEntityType.Builder.of(PetrifiedSwirlsBlockEntity::new, JNEBlocks.PETRIFIED_SWIRLS.get()).build(null));

    public static final Supplier<BlockEntityType<CiergeOfTreacheryBlockEntity>> CIERGE_OF_TREACHERY = BLOCK_ENTITY_TYPES.register("cierge_of_treachery", () ->
            BlockEntityType.Builder.of(CiergeOfTreacheryBlockEntity::new, JNEBlocks.CIERGE_OF_TREACHERY.get()).build(null));

    public static final Supplier<BlockEntityType<BrazierChestBlockEntity>> BRAZIER_CHEST = BLOCK_ENTITY_TYPES.register("brazier_chest", () ->
            BlockEntityType.Builder.of(BrazierChestBlockEntity::new, JNEBlocks.BRAZIER_CHEST.get()).build(null));

    public static void init(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
