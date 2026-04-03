package net.jadenxgamer.netherexp.registry;

import com.mojang.serialization.Codec;
import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class JNEDataComponents {

    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, NetherExp.MOD_ID);

        public static final Supplier<DataComponentType<Integer>> PUMPS = COMPONENTS.registerComponentType("pumps",
            builder -> builder
                    .persistent(Codec.INT)
                    .networkSynchronized(ByteBufCodecs.INT));

    public static void init(IEventBus eventBus) {
        COMPONENTS.register(eventBus);
    }
}
