package net.jadenxgamer.netherexp.registry;

import io.netty.util.Attribute;
import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class JNESoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, NetherExp.MOD_ID);

    public static final Supplier<SoundEvent> FOSSILIZATION = registerSoundEvents("block.fossilization");
    public static final Supplier<SoundEvent> SOUL_SWIRLS_BOOST = registerSoundEvents("block.soul_swirls.boost");
    public static final Supplier<SoundEvent> SOUL_SWIRLS_DEACTIVATE = registerSoundEvents("block.soul_swirls.deactivate");

    public static final Supplier<SoundEvent> ANTIDOTE_NEGATE = registerSoundEvents("item.antidote.negate");

    public static final Supplier<SoundEvent> GOLD_GILDING = registerSoundEvents("item.gold.gilding");

    public static final Supplier<SoundEvent> WISP_AMBIENT = registerSoundEvents("entity.wisp.ambient");
    public static final Supplier<SoundEvent> WISP_HURT = registerSoundEvents("entity.wisp.hurt");
    public static final Supplier<SoundEvent> WISP_DEATH = registerSoundEvents("entity.wisp.death");
    public static final Supplier<SoundEvent> WISP_BOTTLE_FILL = registerSoundEvents("entity.wisp.bottle_fill");
    public static final Supplier<SoundEvent> WISP_BOTTLE_EMPTY = registerSoundEvents("entity.wisp.bottle_empty");

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

    public static final Supplier<SoundEvent> SOUL_SWIRLS_PLACE = registerSoundEvents("block.soul_swirls.place");
    public static final Supplier<SoundEvent> SOUL_SWIRLS_BREAK = registerSoundEvents("block.soul_swirls.break");

    public static final Supplier<SoundEvent> SOUL_CANDLE_PLACE = registerSoundEvents("block.soul_candle.place");
    public static final Supplier<SoundEvent> SOUL_CANDLE_BREAK = registerSoundEvents("block.soul_candle.break");
    public static final Supplier<SoundEvent> SOUL_CANDLE_STEP = registerSoundEvents("block.soul_candle.step");
    public static final Supplier<SoundEvent> SOUL_CANDLE_HIT = registerSoundEvents("block.soul_candle.hit");
    public static final Supplier<SoundEvent> SOUL_CANDLE_FALL = registerSoundEvents("block.soul_candle.fall");
    public static final Supplier<SoundEvent> SOUL_CANDLE_AMBIENT = registerSoundEvents("block.soul_candle.ambient");

    public static final Supplier<SoundEvent> SUSPICIOUS_SOUL_SAND_PLACE = registerSoundEvents("block.suspicious_soul_sand.break");
    public static final Supplier<SoundEvent> SUSPICIOUS_SOUL_SAND_BREAK = registerSoundEvents("block.suspicious_soul_sand.break");

    public static final Supplier<SoundEvent> FOSSIL_ORE_PLACE = registerSoundEvents("block.fossil_ore.break");
    public static final Supplier<SoundEvent> FOSSIL_ORE_BREAK = registerSoundEvents("block.fossil_ore.break");

    public static final Supplier<SoundEvent> SOUL_MAGMA_BLOCK_PLACE = registerSoundEvents("block.soul_magma_block.break");
    public static final Supplier<SoundEvent> SOUL_MAGMA_BLOCK_BREAK = registerSoundEvents("block.soul_magma_block.break");

    public static final Supplier<SoundEvent> NETHERRACK_BRICKS_PLACE = registerSoundEvents("block.netherrack_bricks.place");
    public static final Supplier<SoundEvent> NETHERRACK_BRICKS_BREAK = registerSoundEvents("block.netherrack_bricks.break");

    public static final Supplier<SoundEvent> POLISHED_BASALT_BRICKS_PLACE = registerSoundEvents("block.polished_basalt_bricks.place");
    public static final Supplier<SoundEvent> POLISHED_BASALT_BRICKS_BREAK = registerSoundEvents("block.polished_basalt_bricks.break");

    public static final Supplier<SoundEvent> CEREBRAGE_STEM_PLACE = registerSoundEvents("block.cerebrage_stem.place");
    public static final Supplier<SoundEvent> CEREBRAGE_STEM_BREAK = registerSoundEvents("block.cerebrage_stem.break");

    private static Supplier<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(NetherExp.id(name)));
    }

    public static void init(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
