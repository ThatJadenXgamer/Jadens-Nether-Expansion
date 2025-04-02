package net.jadenxgamer.netherexp.registry.block;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.entity.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JNEBlockEntityType {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, NetherExp.MOD_ID);

    public static final RegistryObject<BlockEntityType<JNEBrushableBlockEntity>> BRUSHABLE_BLOCK = BLOCK_ENTITY_TYPES.register("brushable_block", () ->
            BlockEntityType.Builder.of(JNEBrushableBlockEntity::new, JNEBlocks.SUSPICIOUS_SOUL_SAND.get()).build(null));

    public static final RegistryObject<BlockEntityType<BrazierChestBlockEntity>> BRAZIER_CHEST = BLOCK_ENTITY_TYPES.register("brazier_chest", () ->
            BlockEntityType.Builder.of(BrazierChestBlockEntity::new, JNEBlocks.BRAZIER_CHEST.get()).build(null));

    public static final RegistryObject<BlockEntityType<TreacherousCandleBlockEntity>> TREACHEROUS_CANDLE = BLOCK_ENTITY_TYPES.register("treacherous_candle", () ->
            BlockEntityType.Builder.of(TreacherousCandleBlockEntity::new, JNEBlocks.TREACHEROUS_CANDLE.get()).build(null));

    public static final RegistryObject<BlockEntityType<DiscernmentGlassBlockEntity>> DISCERNMENT_GLASS = BLOCK_ENTITY_TYPES.register("discernment_glass", () ->
            BlockEntityType.Builder.of(DiscernmentGlassBlockEntity::new, JNEBlocks.DISCERNMENT_GLASS.get()).build(null));

    public static final RegistryObject<BlockEntityType<AncientCampfireBlockEntity>> ANCIENT_CAMPFIRE = BLOCK_ENTITY_TYPES.register("ancient_campfire", () ->
            BlockEntityType.Builder.of(AncientCampfireBlockEntity::new, JNEBlocks.ANCIENT_CAMPFIRE.get()).build(null));

    public static void init(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
