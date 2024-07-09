package net.jadenxgamer.netherexp.registry.block;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.entity.BrazierChestBlockEntity;
import net.jadenxgamer.netherexp.registry.block.entity.JNEBrushableBlockEntity;
import net.jadenxgamer.netherexp.registry.block.entity.TreacherousCandleBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class JNEBlockEntityType {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(NetherExp.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<JNEBrushableBlockEntity>> BRUSHABLE_BLOCK = BLOCK_ENTITY_TYPES.register("brushable_block", () ->
            BlockEntityType.Builder.of(JNEBrushableBlockEntity::new, JNEBlocks.SUSPICIOUS_SOUL_SAND.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<BrazierChestBlockEntity>> BRAZIER_CHEST = BLOCK_ENTITY_TYPES.register("brazier_chest", () ->
            BlockEntityType.Builder.of(BrazierChestBlockEntity::new, JNEBlocks.BRAZIER_CHEST.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<TreacherousCandleBlockEntity>> TREACHEROUS_CANDLE = BLOCK_ENTITY_TYPES.register("treacherous_candle", () ->
            BlockEntityType.Builder.of(TreacherousCandleBlockEntity::new, JNEBlocks.TREACHEROUS_CANDLE.get()).build(null));

    public static void init() {
        BLOCK_ENTITY_TYPES.register();
    }
}
