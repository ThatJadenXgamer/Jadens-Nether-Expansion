package net.jadenxgamer.netherexp.registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.block.entity.DiscernmentGlassBlockEntity;
import net.jadenxgamer.netherexp.core.block.entity.JNECampfireBlockEntity;
import net.jadenxgamer.netherexp.core.block.entity.SuspiciousSoulSandBlockEntity;
import net.jadenxgamer.netherexp.core.entity.ShotgunPellet;
import net.jadenxgamer.netherexp.core.entity.Wisp;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class JNEBlockEntityType {

    public static final DeferredRegister<BlockEntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, NetherExp.MOD_ID);

    public static final Supplier<BlockEntityType<SuspiciousSoulSandBlockEntity>> SUSPICIOUS_SOUL_SAND = ENTITY_TYPES.register("suspicious_soul_sand", () ->
            BlockEntityType.Builder.of(SuspiciousSoulSandBlockEntity::new, JNEBlocks.SUSPICIOUS_SOUL_SAND.get()).build(null));

    public static final Supplier<BlockEntityType<DiscernmentGlassBlockEntity>> DISCERNMENT_GLASS = ENTITY_TYPES.register("discernment_glass", () ->
            BlockEntityType.Builder.of(DiscernmentGlassBlockEntity::new, JNEBlocks.DISCERNMENT_GLASS.get()).build(null));

    public static final Supplier<BlockEntityType<JNECampfireBlockEntity>> JNE_CAMPFIRE = ENTITY_TYPES.register("jne_campfire", () ->
            BlockEntityType.Builder.of(JNECampfireBlockEntity::new, JNEBlocks.ANCIENT_CAMPFIRE.get()).build(null));

    public static void init(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
