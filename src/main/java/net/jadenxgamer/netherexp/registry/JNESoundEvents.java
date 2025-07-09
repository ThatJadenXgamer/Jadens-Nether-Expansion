package net.jadenxgamer.netherexp.registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class JNESoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, NetherExp.MOD_ID);

    public static class Interactions {
        public static final Supplier<SoundEvent> FOSSILIZATION = registerSoundEvents("block.fossilization");
        public static final Supplier<SoundEvent> SOUL_SWIRLS_BOOST = registerSoundEvents("block.soul_swirls.boost");
        public static final Supplier<SoundEvent> SOUL_SWIRLS_DEACTIVATE = registerSoundEvents("block.soul_swirls.deactivate");
    }

    public static class Items {
        public static final Supplier<SoundEvent> ANTIDOTE_NEGATE = registerSoundEvents("item.antidote.negate");
    }

    public static class Blocks {
        public static final Supplier<SoundEvent> SOUL_SLATE_PLACE = registerSoundEvents("block.soul_slate.place");
        public static final Supplier<SoundEvent> SOUL_SLATE_BREAK = registerSoundEvents("block.soul_slate.break");
        public static final Supplier<SoundEvent> SOUL_SLATE_STEP = registerSoundEvents("block.soul_slate.step");
        public static final Supplier<SoundEvent> SOUL_SLATE_HIT = registerSoundEvents("block.soul_slate.hit");
        public static final Supplier<SoundEvent> SOUL_SLATE_FALL = registerSoundEvents("block.soul_slate.fall");

        public static final Supplier<SoundEvent> SOUL_SLATE_BRICKS_PLACE = registerSoundEvents("block.soul_slate_bricks.place");
        public static final Supplier<SoundEvent> SOUL_SLATE_BRICKS_BREAK = registerSoundEvents("block.soul_slate_bricks.break");
        public static final Supplier<SoundEvent> SOUL_SLATE_BRICKS_STEP = registerSoundEvents("block.soul_slate_bricks.step");
        public static final Supplier<SoundEvent> SOUL_SLATE_BRICKS_HIT = registerSoundEvents("block.soul_slate_bricks.hit");
        public static final Supplier<SoundEvent> SOUL_SLATE_BRICKS_FALL = registerSoundEvents("block.soul_slate_bricks.fall");

        public static final Supplier<SoundEvent> SOUL_CANDLE_PLACE = registerSoundEvents("block.soul_candle.place");
        public static final Supplier<SoundEvent> SOUL_CANDLE_BREAK = registerSoundEvents("block.soul_candle.break");
        public static final Supplier<SoundEvent> SOUL_CANDLE_STEP = registerSoundEvents("block.soul_candle.step");
        public static final Supplier<SoundEvent> SOUL_CANDLE_HIT = registerSoundEvents("block.soul_candle.hit");
        public static final Supplier<SoundEvent> SOUL_CANDLE_FALL = registerSoundEvents("block.soul_candle.fall");
        public static final Supplier<SoundEvent> SOUL_CANDLE_AMBIENT = registerSoundEvents("block.soul_candle.ambient");
    }

    private static Supplier<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(NetherExp.id(name)));
    }

    public static void init(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
