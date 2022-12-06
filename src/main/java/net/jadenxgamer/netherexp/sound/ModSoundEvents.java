package net.jadenxgamer.netherexp.sound;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class ModSoundEvents {

    // LIST OF SOUNDS EVENTS:

    // Soul Slate

    public static SoundEvent BLOCK_SOUL_SLATE_BREAK = registerSoundEvent("block.soul_slate.break");
    public static SoundEvent BLOCK_SOUL_SLATE_STEP = registerSoundEvent("block.soul_slate.step");
    public static SoundEvent BLOCK_SOUL_SLATE_PLACE = registerSoundEvent("block.soul_slate.place");
    public static SoundEvent BLOCK_SOUL_SLATE_HIT = registerSoundEvent("block.soul_slate.hit");
    public static SoundEvent BLOCK_SOUL_SLATE_FALL = registerSoundEvent("block.soul_slate.fall");

    // Soul Slate Bricks

    public static SoundEvent BLOCK_SOUL_SLATE_BRICKS_BREAK = registerSoundEvent("block.soul_slate_bricks.break");
    public static SoundEvent BLOCK_SOUL_SLATE_BRICKS_STEP = registerSoundEvent("block.soul_slate_bricks.step");
    public static SoundEvent BLOCK_SOUL_SLATE_BRICKS_PLACE = registerSoundEvent("block.soul_slate_bricks.place");
    public static SoundEvent BLOCK_SOUL_SLATE_BRICKS_HIT = registerSoundEvent("block.soul_slate_bricks.hit");
    public static SoundEvent BLOCK_SOUL_SLATE_BRICKS_FALL = registerSoundEvent("block.soul_slate_bricks.fall");


    // Basalt Bricks

    public static SoundEvent BLOCK_BASALT_BRICKS_BREAK = registerSoundEvent("block.basalt_bricks.break");
    public static SoundEvent BLOCK_BASALT_BRICKS_STEP = registerSoundEvent("block.basalt_bricks.step");
    public static SoundEvent BLOCK_BASALT_BRICKS_PLACE = registerSoundEvent("block.basalt_bricks.place");
    public static SoundEvent BLOCK_BASALT_BRICKS_HIT = registerSoundEvent("block.basalt_bricks.hit");
    public static SoundEvent BLOCK_BASALT_BRICKS_FALL = registerSoundEvent("block.basalt_bricks.fall");

    // LIST OF SOUND GROUPS:

    public static final BlockSoundGroup SOUL_SLATE = new BlockSoundGroup(1f, 1f,
            ModSoundEvents.BLOCK_SOUL_SLATE_BREAK, ModSoundEvents.BLOCK_SOUL_SLATE_STEP, ModSoundEvents.BLOCK_SOUL_SLATE_PLACE,
            ModSoundEvents.BLOCK_SOUL_SLATE_HIT, ModSoundEvents.BLOCK_SOUL_SLATE_FALL);

    public static final BlockSoundGroup SOUL_SLATE_BRICKS = new BlockSoundGroup(1f, 1f,
            ModSoundEvents.BLOCK_SOUL_SLATE_BRICKS_BREAK, ModSoundEvents.BLOCK_SOUL_SLATE_BRICKS_STEP, ModSoundEvents.BLOCK_SOUL_SLATE_BRICKS_PLACE,
            ModSoundEvents.BLOCK_SOUL_SLATE_BRICKS_HIT, ModSoundEvents.BLOCK_SOUL_SLATE_BRICKS_FALL);

    public static final BlockSoundGroup BASALT_BRICKS = new BlockSoundGroup(1f, 1f,
            ModSoundEvents.BLOCK_BASALT_BRICKS_BREAK, ModSoundEvents.BLOCK_BASALT_BRICKS_STEP, ModSoundEvents.BLOCK_BASALT_BRICKS_PLACE,
            ModSoundEvents.BLOCK_BASALT_BRICKS_HIT, ModSoundEvents.BLOCK_BASALT_BRICKS_FALL);

    public static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(NetherExp.MOD_ID, name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }
}
