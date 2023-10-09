package net.jadenxgamer.netherexp.sound;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;


public class ModSoundEvents {

    //// BLOCKS:
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

    // Netherrack Bricks

    public static SoundEvent BLOCK_NETHERRACK_BRICKS_BREAK = registerSoundEvent("block.netherrack_bricks.break");
    public static SoundEvent BLOCK_NETHERRACK_BRICKS_STEP = registerSoundEvent("block.netherrack_bricks.step");
    public static SoundEvent BLOCK_NETHERRACK_BRICKS_PLACE = registerSoundEvent("block.netherrack_bricks.place");
    public static SoundEvent BLOCK_NETHERRACK_BRICKS_HIT = registerSoundEvent("block.netherrack_bricks.hit");
    public static SoundEvent BLOCK_NETHERRACK_BRICKS_FALL = registerSoundEvent("block.netherrack_bricks.fall");

    // Basalt Bricks

    public static SoundEvent BLOCK_BASALT_BRICKS_BREAK = registerSoundEvent("block.basalt_bricks.break");
    public static SoundEvent BLOCK_BASALT_BRICKS_STEP = registerSoundEvent("block.basalt_bricks.step");
    public static SoundEvent BLOCK_BASALT_BRICKS_PLACE = registerSoundEvent("block.basalt_bricks.place");
    public static SoundEvent BLOCK_BASALT_BRICKS_HIT = registerSoundEvent("block.basalt_bricks.hit");
    public static SoundEvent BLOCK_BASALT_BRICKS_FALL = registerSoundEvent("block.basalt_bricks.fall");

    // White Ash

    public static SoundEvent BLOCK_WHITE_ASH_BREAK = registerSoundEvent("block.white_ash.break");
    public static SoundEvent BLOCK_WHITE_ASH_STEP = registerSoundEvent("block.white_ash.step");
    public static SoundEvent BLOCK_WHITE_ASH_PLACE = registerSoundEvent("block.white_ash.place");
    public static SoundEvent BLOCK_WHITE_ASH_HIT = registerSoundEvent("block.white_ash.hit");
    public static SoundEvent BLOCK_WHITE_ASH_FALL = registerSoundEvent("block.white_ash.fall");

    // Quartz Crystal
    public static SoundEvent BLOCK_QUARTZ_CRYSTAL_BREAK = registerSoundEvent("block.quartz_crystal.break");

    // Smokestalk
    public static SoundEvent BLOCK_SMOKESTALK_BREAK = registerSoundEvent("block.smokestalk.break");
    public static SoundEvent BLOCK_SMOKESTALK_PLACE = registerSoundEvent("block.smokestalk.place");

    // Smokestalk Wood

    public static SoundEvent BLOCK_SMOKESTALK_WOOD_BREAK = registerSoundEvent("block.smokestalk_wood.break");
    public static SoundEvent BLOCK_SMOKESTALK_WOOD_STEP = registerSoundEvent("block.smokestalk_wood.step");
    public static SoundEvent BLOCK_SMOKESTALK_WOOD_PLACE = registerSoundEvent("block.smokestalk_wood.place");
    public static SoundEvent BLOCK_SMOKESTALK_WOOD_HIT = registerSoundEvent("block.smokestalk_wood.hit");
    public static SoundEvent BLOCK_SMOKESTALK_WOOD_FALL = registerSoundEvent("block.smokestalk_wood.fall");

    // Soul Candle

    public static SoundEvent BLOCK_SOUL_CANDLE_BREAK = registerSoundEvent("block.soul_candle.break");
    public static SoundEvent BLOCK_SOUL_CANDLE_STEP = registerSoundEvent("block.soul_candle.step");
    public static SoundEvent BLOCK_SOUL_CANDLE_PLACE = registerSoundEvent("block.soul_candle.place");
    public static SoundEvent BLOCK_SOUL_CANDLE_HIT = registerSoundEvent("block.soul_candle.hit");
    public static SoundEvent BLOCK_SOUL_CANDLE_FALL = registerSoundEvent("block.soul_candle.fall");
    public static SoundEvent BLOCK_SOUL_CANDLE_AMBIENT = registerSoundEvent("block.soul_candle.ambient");

    // ITEMS:

    public static SoundEvent GILDING = registerSoundEvent("item.gold_ingot.gilding");
    public static SoundEvent EXPLOSIVE_SCORIA_AMBIENT = registerSoundEvent("block.explosive_scoria.ambient");
    public static SoundEvent SPORESHROOM_TRAMPOLINED = registerSoundEvent("block.sporeshroom.trampolined");
    public static SoundEvent LIGHTSPORES_APPLY = registerSoundEvent("block.lightspores.apply");
    public static SoundEvent LIGHTSPORES_SHEAR = registerSoundEvent("block.lightspores.shear");

    // ENTITIES:

    public static SoundEvent ENTITY_WARPHOPPER_AMBIENT = registerSoundEvent("entity.warphopper.ambient");
    public static SoundEvent ENTITY_WARPHOPPER_HURT = registerSoundEvent("entity.warphopper.hurt");
    public static SoundEvent ENTITY_WARPHOPPER_DEATH = registerSoundEvent("entity.warphopper.death");
    public static SoundEvent ENTITY_WARPHOPPER_CLOAK = registerSoundEvent("entity.warphopper.cloak");
    public static SoundEvent ENTITY_PLAYER_HURT_IGNEOUS_THORNS = registerSoundEvent("entity.player.hurt_igneous_thorns");

    // MUSIC:
    public static SoundEvent MUSIC_DISC_CRICKET = registerSoundEvent("music_disc.cricket");

    // LIST OF SOUND GROUPS:

    public static final BlockSoundGroup SOUL_SLATE = new BlockSoundGroup(1f, 1f,
            ModSoundEvents.BLOCK_SOUL_SLATE_BREAK, ModSoundEvents.BLOCK_SOUL_SLATE_STEP, ModSoundEvents.BLOCK_SOUL_SLATE_PLACE,
            ModSoundEvents.BLOCK_SOUL_SLATE_HIT, ModSoundEvents.BLOCK_SOUL_SLATE_FALL);

    public static final BlockSoundGroup SOUL_SLATE_BRICKS = new BlockSoundGroup(1f, 1f,
            ModSoundEvents.BLOCK_SOUL_SLATE_BRICKS_BREAK, ModSoundEvents.BLOCK_SOUL_SLATE_BRICKS_STEP, ModSoundEvents.BLOCK_SOUL_SLATE_BRICKS_PLACE,
            ModSoundEvents.BLOCK_SOUL_SLATE_BRICKS_HIT, ModSoundEvents.BLOCK_SOUL_SLATE_BRICKS_FALL);

    public static final BlockSoundGroup NETHERRACK_BRICKS = new BlockSoundGroup(1f, 1f,
            ModSoundEvents.BLOCK_NETHERRACK_BRICKS_BREAK, ModSoundEvents.BLOCK_NETHERRACK_BRICKS_STEP, ModSoundEvents.BLOCK_NETHERRACK_BRICKS_PLACE,
            ModSoundEvents.BLOCK_NETHERRACK_BRICKS_HIT, ModSoundEvents.BLOCK_NETHERRACK_BRICKS_FALL);

    public static final BlockSoundGroup BASALT_BRICKS = new BlockSoundGroup(1f, 1f,
            ModSoundEvents.BLOCK_BASALT_BRICKS_BREAK, ModSoundEvents.BLOCK_BASALT_BRICKS_STEP, ModSoundEvents.BLOCK_BASALT_BRICKS_PLACE,
            ModSoundEvents.BLOCK_BASALT_BRICKS_HIT, ModSoundEvents.BLOCK_BASALT_BRICKS_FALL);

    public static final BlockSoundGroup WHITE_ASH = new BlockSoundGroup(1f, 1f,
            ModSoundEvents.BLOCK_WHITE_ASH_BREAK, ModSoundEvents.BLOCK_WHITE_ASH_STEP, ModSoundEvents.BLOCK_WHITE_ASH_PLACE,
            ModSoundEvents.BLOCK_WHITE_ASH_HIT, ModSoundEvents.BLOCK_WHITE_ASH_FALL);

    public static final BlockSoundGroup QUARTZ_CRYSTAL = new BlockSoundGroup(1f, 1f,
            ModSoundEvents.BLOCK_QUARTZ_CRYSTAL_BREAK, SoundEvents.BLOCK_NETHER_ORE_STEP, SoundEvents.BLOCK_NETHER_ORE_PLACE,
            SoundEvents.BLOCK_NETHER_ORE_HIT, SoundEvents.BLOCK_NETHER_ORE_FALL);

    public static final BlockSoundGroup SMOKESTALK = new BlockSoundGroup(1f, 1f,
            ModSoundEvents.BLOCK_SMOKESTALK_BREAK, SoundEvents.BLOCK_BAMBOO_STEP, ModSoundEvents.BLOCK_SMOKESTALK_PLACE,
            SoundEvents.BLOCK_BAMBOO_HIT, SoundEvents.BLOCK_BAMBOO_FALL);

    public static final BlockSoundGroup SMOKESTALK_WOOD = new BlockSoundGroup(1f, 1f,
            ModSoundEvents.BLOCK_SMOKESTALK_WOOD_BREAK, ModSoundEvents.BLOCK_SMOKESTALK_WOOD_STEP, ModSoundEvents.BLOCK_SMOKESTALK_WOOD_PLACE,
            ModSoundEvents.BLOCK_SMOKESTALK_WOOD_HIT, ModSoundEvents.BLOCK_SMOKESTALK_WOOD_FALL);

    public static final BlockSoundGroup SOUL_CANDLE = new BlockSoundGroup(1f, 1f,
            ModSoundEvents.BLOCK_SOUL_CANDLE_BREAK, ModSoundEvents.BLOCK_SOUL_CANDLE_STEP, ModSoundEvents.BLOCK_SOUL_CANDLE_PLACE,
            ModSoundEvents.BLOCK_SOUL_CANDLE_HIT, ModSoundEvents.BLOCK_SOUL_CANDLE_FALL);

    public static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(NetherExp.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
}
