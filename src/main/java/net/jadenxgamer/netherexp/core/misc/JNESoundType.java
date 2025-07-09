package net.jadenxgamer.netherexp.core.misc;

import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.world.level.block.SoundType;

public class JNESoundType {

    public static final SoundType SOUL_SLATE = new SoundType(1.0f, 1.0f,
            JNESoundEvents.Blocks.SOUL_SLATE_BREAK.get(), JNESoundEvents.Blocks.SOUL_SLATE_STEP.get(), JNESoundEvents.Blocks.SOUL_SLATE_PLACE.get(),
            JNESoundEvents.Blocks.SOUL_SLATE_HIT.get(), JNESoundEvents.Blocks.SOUL_SLATE_FALL.get());

    public static final SoundType SOUL_SLATE_BRICKS = new SoundType(1.0f, 1.0f,
            JNESoundEvents.Blocks.SOUL_SLATE_BRICKS_BREAK.get(), JNESoundEvents.Blocks.SOUL_SLATE_BRICKS_STEP.get(), JNESoundEvents.Blocks.SOUL_SLATE_BRICKS_PLACE.get(),
            JNESoundEvents.Blocks.SOUL_SLATE_BRICKS_HIT.get(), JNESoundEvents.Blocks.SOUL_SLATE_BRICKS_FALL.get());

    public static final SoundType SOUL_CANDLE = new SoundType(1.0f, 1.0f,
            JNESoundEvents.Blocks.SOUL_CANDLE_BREAK.get(), JNESoundEvents.Blocks.SOUL_CANDLE_STEP.get(), JNESoundEvents.Blocks.SOUL_CANDLE_PLACE.get(),
            JNESoundEvents.Blocks.SOUL_CANDLE_HIT.get(), JNESoundEvents.Blocks.SOUL_CANDLE_FALL.get());
}
