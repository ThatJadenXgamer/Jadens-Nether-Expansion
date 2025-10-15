package net.jadenxgamer.netherexp.core.misc;

import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;

public class JNESoundType {

    public static final SoundType SOUL_SLATE = new SoundType(1.0f, 1.0f,
            JNESoundEvents.SOUL_SLATE_BREAK.get(), JNESoundEvents.SOUL_SLATE_STEP.get(), JNESoundEvents.SOUL_SLATE_PLACE.get(),
            JNESoundEvents.SOUL_SLATE_HIT.get(), JNESoundEvents.SOUL_SLATE_FALL.get());

    public static final SoundType SOUL_SLATE_BRICKS = new SoundType(1.0f, 1.0f,
            JNESoundEvents.SOUL_SLATE_BRICKS_BREAK.get(), JNESoundEvents.SOUL_SLATE_BRICKS_STEP.get(), JNESoundEvents.SOUL_SLATE_BRICKS_PLACE.get(),
            JNESoundEvents.SOUL_SLATE_BRICKS_HIT.get(), JNESoundEvents.SOUL_SLATE_BRICKS_FALL.get());

    public static final SoundType SOUL_SWIRLS = new SoundType(1.0f, 1.0f,
            JNESoundEvents.SOUL_SWIRLS_BREAK.get(), SoundEvents.NETHER_SPROUTS_STEP, JNESoundEvents.SOUL_SWIRLS_PLACE.get(),
            SoundEvents.NETHER_SPROUTS_HIT, SoundEvents.NETHER_SPROUTS_FALL);

    public static final SoundType SOUL_CANDLE = new SoundType(1.0f, 1.0f,
            JNESoundEvents.SOUL_CANDLE_BREAK.get(), JNESoundEvents.SOUL_CANDLE_STEP.get(), JNESoundEvents.SOUL_CANDLE_PLACE.get(),
            JNESoundEvents.SOUL_CANDLE_HIT.get(), JNESoundEvents.SOUL_CANDLE_FALL.get());

    public static final SoundType SUSPICIOUS_SOUL_SAND = new SoundType(1.0f, 1.0f,
            JNESoundEvents.SUSPICIOUS_SOUL_SAND_BREAK.get(), SoundEvents.SUSPICIOUS_SAND_STEP, JNESoundEvents.SUSPICIOUS_SOUL_SAND_PLACE.get(),
            SoundEvents.SUSPICIOUS_SAND_HIT, SoundEvents.SUSPICIOUS_SAND_FALL);

    public static final SoundType FOSSIL_ORE = new SoundType(1.0f, 1.0f,
            JNESoundEvents.FOSSIL_ORE_BREAK.get(), SoundEvents.SOUL_SOIL_STEP, JNESoundEvents.FOSSIL_ORE_PLACE.get(),
            SoundEvents.SOUL_SOIL_HIT, SoundEvents.SOUL_SOIL_FALL);

    public static final SoundType SOUL_MAGMA_BLOCK = new SoundType(1.0f, 1.0f,
            JNESoundEvents.SOUL_MAGMA_BLOCK_BREAK.get(), SoundEvents.SOUL_SOIL_STEP, JNESoundEvents.SOUL_MAGMA_BLOCK_PLACE.get(),
            SoundEvents.SOUL_SOIL_HIT, SoundEvents.SOUL_SOIL_FALL);

    public static final SoundType NETHERRACK_BRICKS = new SoundType(1.0f, 1.0f,
            JNESoundEvents.NETHERRACK_BRICKS_BREAK.get(), SoundEvents.DEEPSLATE_BRICKS_STEP, JNESoundEvents.NETHERRACK_BRICKS_PLACE.get(),
            SoundEvents.NETHERRACK_HIT, SoundEvents.NETHERRACK_FALL);

    public static final SoundType POLISHED_BASALT_BRICKS = new SoundType(1.0f, 1.0f,
            JNESoundEvents.POLISHED_BASALT_BRICKS_BREAK.get(), SoundEvents.DEEPSLATE_BRICKS_STEP, JNESoundEvents.POLISHED_BASALT_BRICKS_PLACE.get(),
            SoundEvents.DEEPSLATE_BRICKS_HIT, SoundEvents.DEEPSLATE_BRICKS_FALL);

    public static final SoundType CEREBRAGE_STEM = new SoundType(1.0f, 1.0f,
            JNESoundEvents.CEREBRAGE_STEM_BREAK.get(), SoundEvents.NETHER_WOOD_STEP, JNESoundEvents.CEREBRAGE_STEM_PLACE.get(),
            SoundEvents.NETHER_WOOD_HIT, SoundEvents.NETHER_WOOD_FALL);

    public static final SoundType LESION_BLOCK = new SoundType(1.0f, 1.0f,
            JNESoundEvents.LESION_BLOCK_BREAK.get(), SoundEvents.MUD_STEP, JNESoundEvents.LESION_BLOCK_PLACE.get(),
            SoundEvents.MUD_HIT, SoundEvents.MUD_FALL);

    public static final SoundType QUARTZ_BLOCK = new SoundType(1.0f, 1.0f,
            JNESoundEvents.QUARTZ_BLOCK_BREAK.get(), SoundEvents.NETHER_ORE_STEP, JNESoundEvents.QUARTZ_BLOCK_PLACE.get(),
            SoundEvents.NETHER_ORE_HIT, SoundEvents.NETHER_ORE_FALL);

    public static final SoundType BLACK_ICE = new SoundType(1.0f, 1.0f,
            JNESoundEvents.BLACK_ICE_BREAK.get(), JNESoundEvents.BLACK_ICE_STEP.get(), JNESoundEvents.BLACK_ICE_PLACE.get(),
            JNESoundEvents.BLACK_ICE_HIT.get(), JNESoundEvents.BLACK_ICE_FALL.get());

    public static final SoundType SHOTGUN_BARREL = new SoundType(1.0f, 1.0f,
            JNESoundEvents.SHOTGUN_BARREL_BREAK.get(), SoundEvents.COPPER_STEP, JNESoundEvents.SHOTGUN_BARREL_PLACE.get(),
            SoundEvents.COPPER_HIT, SoundEvents.COPPER_FALL);

    public static final SoundType FROGMIST = new SoundType(1.0f, 1.0f,
            JNESoundEvents.FROGMIST_BREAK.get(), SoundEvents.SAND_STEP, JNESoundEvents.FROGMIST_PLACE.get(),
            SoundEvents.SAND_HIT, SoundEvents.SAND_FALL);

    public static final SoundType WITHER_BONE_BLOCK = new SoundType(1.0f, 1.0f,
            JNESoundEvents.WITHER_BONE_BLOCK_BREAK.get(), JNESoundEvents.WITHER_BONE_BLOCK_STEP.get(), JNESoundEvents.WITHER_BONE_BLOCK_PLACE.get(),
            JNESoundEvents.WITHER_BONE_BLOCK_HIT.get(), JNESoundEvents.WITHER_BONE_BLOCK_FALL.get());

    public static final SoundType BONE_PIKE = new SoundType(1.0f, 1.0f,
            JNESoundEvents.BONE_PIKE_BREAK.get(), SoundEvents.BONE_BLOCK_STEP, JNESoundEvents.BONE_PIKE_PLACE.get(),
            SoundEvents.BONE_BLOCK_HIT, SoundEvents.BONE_BLOCK_FALL);

    public static final SoundType ANCIENT_CANDLE = new SoundType(1.0f, 1.0f,
            JNESoundEvents.ANCIENT_CANDLE_BREAK.get(), SoundEvents.CANDLE_STEP, JNESoundEvents.ANCIENT_CANDLE_PLACE.get(),
            SoundEvents.CANDLE_HIT, SoundEvents.CANDLE_FALL);

    public static final SoundType ANCIENT_WAX = new SoundType(1.0f, 1.0f,
            JNESoundEvents.ANCIENT_WAX_BREAK.get(), SoundEvents.CORAL_BLOCK_STEP, JNESoundEvents.ANCIENT_WAX_PLACE.get(),
            SoundEvents.CORAL_BLOCK_HIT, SoundEvents.CORAL_BLOCK_FALL);

    public static final SoundType NETHERITE_GRATE = new SoundType(1.0f, 1.0f,
            JNESoundEvents.NETHERITE_GRATE_BREAK.get(), JNESoundEvents.NETHERITE_GRATE_STEP.get(), JNESoundEvents.NETHERITE_GRATE_PLACE.get(),
            JNESoundEvents.NETHERITE_GRATE_HIT.get(), JNESoundEvents.NETHERITE_GRATE_FALL.get());

    public static final SoundType RUSTY_NETHERITE_GRATE = new SoundType(1.0f, 1.0f,
            JNESoundEvents.RUSTY_NETHERITE_GRATE_BREAK.get(), JNESoundEvents.NETHERITE_GRATE_STEP.get(), JNESoundEvents.RUSTY_NETHERITE_GRATE_PLACE.get(),
            JNESoundEvents.NETHERITE_GRATE_HIT.get(), JNESoundEvents.NETHERITE_GRATE_FALL.get());

    public static final SoundType SOUL_GLASS = new SoundType(1.0f, 1.0f,
            JNESoundEvents.SOUL_GLASS_BREAK.get(), SoundEvents.GLASS_STEP, SoundEvents.GLASS_PLACE,
            SoundEvents.GLASS_HIT, SoundEvents.GLASS_FALL);

    public static final SoundType HAZE_BLOCK = new SoundType(1.0f, 1.0f,
            JNESoundEvents.HAZE_BLOCK_BREAK.get(), SoundEvents.SAND_STEP, JNESoundEvents.HAZE_BLOCK_PLACE.get(),
            SoundEvents.SAND_HIT, SoundEvents.SAND_FALL);
}
