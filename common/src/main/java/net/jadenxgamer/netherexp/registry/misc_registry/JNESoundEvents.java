package net.jadenxgamer.netherexp.registry.misc_registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class JNESoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(NetherExp.MOD_ID, Registries.SOUND_EVENT);

    //// BLOCKS:
    
    // Soul Slate

    public static RegistrySupplier<SoundEvent> BLOCK_SOUL_SLATE_BREAK = registerSoundEvents("block.soul_slate.break");
    public static RegistrySupplier<SoundEvent> BLOCK_SOUL_SLATE_STEP = registerSoundEvents("block.soul_slate.step");
    public static RegistrySupplier<SoundEvent> BLOCK_SOUL_SLATE_PLACE = registerSoundEvents("block.soul_slate.place");
    public static RegistrySupplier<SoundEvent> BLOCK_SOUL_SLATE_HIT = registerSoundEvents("block.soul_slate.hit");
    public static RegistrySupplier<SoundEvent> BLOCK_SOUL_SLATE_FALL = registerSoundEvents("block.soul_slate.fall");

    // Soul Slate Bricks

    public static RegistrySupplier<SoundEvent> BLOCK_SOUL_SLATE_BRICKS_BREAK = registerSoundEvents("block.soul_slate_bricks.break");
    public static RegistrySupplier<SoundEvent> BLOCK_SOUL_SLATE_BRICKS_STEP = registerSoundEvents("block.soul_slate_bricks.step");
    public static RegistrySupplier<SoundEvent> BLOCK_SOUL_SLATE_BRICKS_PLACE = registerSoundEvents("block.soul_slate_bricks.place");
    public static RegistrySupplier<SoundEvent> BLOCK_SOUL_SLATE_BRICKS_HIT = registerSoundEvents("block.soul_slate_bricks.hit");
    public static RegistrySupplier<SoundEvent> BLOCK_SOUL_SLATE_BRICKS_FALL = registerSoundEvents("block.soul_slate_bricks.fall");

    // Netherrack Bricks

    public static RegistrySupplier<SoundEvent> BLOCK_NETHERRACK_BRICKS_BREAK = registerSoundEvents("block.netherrack_bricks.break");
    public static RegistrySupplier<SoundEvent> BLOCK_NETHERRACK_BRICKS_STEP = registerSoundEvents("block.netherrack_bricks.step");
    public static RegistrySupplier<SoundEvent> BLOCK_NETHERRACK_BRICKS_PLACE = registerSoundEvents("block.netherrack_bricks.place");
    public static RegistrySupplier<SoundEvent> BLOCK_NETHERRACK_BRICKS_HIT = registerSoundEvents("block.netherrack_bricks.hit");
    public static RegistrySupplier<SoundEvent> BLOCK_NETHERRACK_BRICKS_FALL = registerSoundEvents("block.netherrack_bricks.fall");

    // Basalt Bricks

    public static RegistrySupplier<SoundEvent> BLOCK_BASALT_BRICKS_BREAK = registerSoundEvents("block.basalt_bricks.break");
    public static RegistrySupplier<SoundEvent> BLOCK_BASALT_BRICKS_STEP = registerSoundEvents("block.basalt_bricks.step");
    public static RegistrySupplier<SoundEvent> BLOCK_BASALT_BRICKS_PLACE = registerSoundEvents("block.basalt_bricks.place");
    public static RegistrySupplier<SoundEvent> BLOCK_BASALT_BRICKS_HIT = registerSoundEvents("block.basalt_bricks.hit");
    public static RegistrySupplier<SoundEvent> BLOCK_BASALT_BRICKS_FALL = registerSoundEvents("block.basalt_bricks.fall");

    // White Ash

    public static RegistrySupplier<SoundEvent> BLOCK_WHITE_ASH_BREAK = registerSoundEvents("block.white_ash.break");
    public static RegistrySupplier<SoundEvent> BLOCK_WHITE_ASH_STEP = registerSoundEvents("block.white_ash.step");
    public static RegistrySupplier<SoundEvent> BLOCK_WHITE_ASH_PLACE = registerSoundEvents("block.white_ash.place");
    public static RegistrySupplier<SoundEvent> BLOCK_WHITE_ASH_HIT = registerSoundEvents("block.white_ash.hit");
    public static RegistrySupplier<SoundEvent> BLOCK_WHITE_ASH_FALL = registerSoundEvents("block.white_ash.fall");

    // Smokestalk
    public static RegistrySupplier<SoundEvent> BLOCK_SMOKESTALK_BREAK = registerSoundEvents("block.smokestalk.break");
    public static RegistrySupplier<SoundEvent> BLOCK_SMOKESTALK_PLACE = registerSoundEvents("block.smokestalk.place");

    // Smokestalk Wood

    public static RegistrySupplier<SoundEvent> BLOCK_SMOKESTALK_WOOD_BREAK = registerSoundEvents("block.smokestalk_wood.break");
    public static RegistrySupplier<SoundEvent> BLOCK_SMOKESTALK_WOOD_STEP = registerSoundEvents("block.smokestalk_wood.step");
    public static RegistrySupplier<SoundEvent> BLOCK_SMOKESTALK_WOOD_PLACE = registerSoundEvents("block.smokestalk_wood.place");
    public static RegistrySupplier<SoundEvent> BLOCK_SMOKESTALK_WOOD_HIT = registerSoundEvents("block.smokestalk_wood.hit");
    public static RegistrySupplier<SoundEvent> BLOCK_SMOKESTALK_WOOD_FALL = registerSoundEvents("block.smokestalk_wood.fall");

    // Soul Candle

    public static RegistrySupplier<SoundEvent> BLOCK_SOUL_CANDLE_BREAK = registerSoundEvents("block.soul_candle.break");
    public static RegistrySupplier<SoundEvent> BLOCK_SOUL_CANDLE_STEP = registerSoundEvents("block.soul_candle.step");
    public static RegistrySupplier<SoundEvent> BLOCK_SOUL_CANDLE_PLACE = registerSoundEvents("block.soul_candle.place");
    public static RegistrySupplier<SoundEvent> BLOCK_SOUL_CANDLE_HIT = registerSoundEvents("block.soul_candle.hit");
    public static RegistrySupplier<SoundEvent> BLOCK_SOUL_CANDLE_FALL = registerSoundEvents("block.soul_candle.fall");
    public static RegistrySupplier<SoundEvent> BLOCK_SOUL_CANDLE_AMBIENT = registerSoundEvents("block.soul_candle.ambient");

    // Blackstone

    public static RegistrySupplier<SoundEvent> BLOCK_BLACKSTONE_BREAK = registerSoundEvents("block.blackstone.break");
    public static RegistrySupplier<SoundEvent> BLOCK_BLACKSTONE_STEP = registerSoundEvents("block.blackstone.step");
    public static RegistrySupplier<SoundEvent> BLOCK_BLACKSTONE_PLACE = registerSoundEvents("block.blackstone.place");
    public static RegistrySupplier<SoundEvent> BLOCK_BLACKSTONE_HIT = registerSoundEvents("block.blackstone.hit");
    public static RegistrySupplier<SoundEvent> BLOCK_BLACKSTONE_FALL = registerSoundEvents("block.blackstone.fall");

    // Polished Blackstone Bricks

    public static RegistrySupplier<SoundEvent> BLOCK_POLISHED_BLACKSTONE_BRICKS_BREAK = registerSoundEvents("block.polished_blackstone_bricks.break");
    public static RegistrySupplier<SoundEvent> BLOCK_POLISHED_BLACKSTONE_BRICKS_STEP = registerSoundEvents("block.polished_blackstone_bricks.step");
    public static RegistrySupplier<SoundEvent> BLOCK_POLISHED_BLACKSTONE_BRICKS_PLACE = registerSoundEvents("block.polished_blackstone_bricks.place");
    public static RegistrySupplier<SoundEvent> BLOCK_POLISHED_BLACKSTONE_BRICKS_HIT = registerSoundEvents("block.polished_blackstone_bricks.hit");
    public static RegistrySupplier<SoundEvent> BLOCK_POLISHED_BLACKSTONE_BRICKS_FALL = registerSoundEvents("block.polished_blackstone_bricks.fall");

    // Black Ice

    public static RegistrySupplier<SoundEvent> BLOCK_BLACK_ICE_BREAK = registerSoundEvents("block.black_ice.break");
    public static RegistrySupplier<SoundEvent> BLOCK_BLACK_ICE_STEP = registerSoundEvents("block.black_ice.step");
    public static RegistrySupplier<SoundEvent> BLOCK_BLACK_ICE_PLACE = registerSoundEvents("block.black_ice.place");
    public static RegistrySupplier<SoundEvent> BLOCK_BLACK_ICE_HIT = registerSoundEvents("block.black_ice.hit");
    public static RegistrySupplier<SoundEvent> BLOCK_BLACK_ICE_FALL = registerSoundEvents("block.black_ice.fall");

    // Magma Block

    public static RegistrySupplier<SoundEvent> BLOCK_MAGMA_BLOCK_BREAK = registerSoundEvents("block.magma_block.break");
    public static RegistrySupplier<SoundEvent> BLOCK_MAGMA_BLOCK_PLACE = registerSoundEvents("block.magma_block.place");

    // Soul Magma Block

    public static RegistrySupplier<SoundEvent> BLOCK_SOUL_MAGMA_BLOCK_BREAK = registerSoundEvents("block.soul_magma_block.break");
    public static RegistrySupplier<SoundEvent> BLOCK_SOUL_MAGMA_BLOCK_PLACE = registerSoundEvents("block.soul_magma_block.place");

    // Soul Magma Block

    public static RegistrySupplier<SoundEvent> BLOCK_GLOWSTONE_BREAK = registerSoundEvents("block.glowstone.break");
    public static RegistrySupplier<SoundEvent> BLOCK_GLOWSTONE_PLACE = registerSoundEvents("block.glowstone.place");

    // Quartz Block

    public static RegistrySupplier<SoundEvent> BLOCK_QUARTZ_BLOCK_BREAK = registerSoundEvents("block.quartz_block.break");
    public static RegistrySupplier<SoundEvent> BLOCK_QUARTZ_BLOCK_PLACE = registerSoundEvents("block.quartz_block.place");

    // SUSPICIOUS SOUL SAND

    public static RegistrySupplier<SoundEvent> BLOCK_SUSPICIOUS_SOUL_SAND_BREAK = registerSoundEvents("block.suspicious_soul_sand.break");
    public static RegistrySupplier<SoundEvent> BLOCK_SUSPICIOUS_SOUL_SAND_STEP = registerSoundEvents("block.suspicious_soul_sand.step");
    public static RegistrySupplier<SoundEvent> BLOCK_SUSPICIOUS_SOUL_SAND_PLACE = registerSoundEvents("block.suspicious_soul_sand.place");
    public static RegistrySupplier<SoundEvent> BLOCK_SUSPICIOUS_SOUL_SAND_HIT = registerSoundEvents("block.suspicious_soul_sand.hit");
    public static RegistrySupplier<SoundEvent> BLOCK_SUSPICIOUS_SOUL_SAND_FALL = registerSoundEvents("block.suspicious_soul_sand.fall");

    // ITEMS & EVENTS:

    public static RegistrySupplier<SoundEvent> GILDING = registerSoundEvents("item.gold_ingot.gilding");
    public static RegistrySupplier<SoundEvent> ECTOPLASM_FREEZE = registerSoundEvents("block.ectoplasm.freeze");
    public static RegistrySupplier<SoundEvent> ECTOPLASM_WHISPERING = registerSoundEvents("block.ectoplasm.whispering");
    public static RegistrySupplier<SoundEvent> SOUL_SLATE_SOLIDIFYING = registerSoundEvents("block.soul_slate.solidifying");
    public static RegistrySupplier<SoundEvent> BUCKET_EMPTY_ECTOPLASM = registerSoundEvents("item.bucket.empty_ectoplasm");
    public static RegistrySupplier<SoundEvent> BUCKET_FILL_ECTOPLASM = registerSoundEvents("item.bucket.fill_ectoplasm");
    public static RegistrySupplier<SoundEvent> BRUSH_BRUSHING_SOUL_SAND_COMPLETE = registerSoundEvents("item.brush.brushing_soul_sand_complete");
    public static RegistrySupplier<SoundEvent> EXPLOSIVE_SCORIA_AMBIENT = registerSoundEvents("block.explosive_scoria.ambient");
    public static RegistrySupplier<SoundEvent> SPORESHROOM_TRAMPOLINED = registerSoundEvents("block.sporeshroom.trampolined");
    public static RegistrySupplier<SoundEvent> LIGHTSPORES_APPLY = registerSoundEvents("block.lightspores.apply");
    public static RegistrySupplier<SoundEvent> LIGHTSPORES_SHEAR = registerSoundEvents("block.lightspores.shear");
    public static RegistrySupplier<SoundEvent> SOUL_SWIRLS_BOOST = registerSoundEvents("block.soul_swirls.boost");
    public static RegistrySupplier<SoundEvent> ANTIDOTE_NEGATE = registerSoundEvents("item.antidote.negate");
    public static RegistrySupplier<SoundEvent> GRENADE_ANTIDOTE_TICK = registerSoundEvents("item.grenade_antidote.tick");
    public static RegistrySupplier<SoundEvent> GRENADE_ANTIDOTE_EXPLODE = registerSoundEvents("item.grenade_antidote.explode");
    public static RegistrySupplier<SoundEvent> SHOTGUN_USE = registerSoundEvents("item.shotgun.use");
    public static RegistrySupplier<SoundEvent> SHOTGUN_LOAD = registerSoundEvents("item.shotgun.load");
    public static RegistrySupplier<SoundEvent> BRAZIER_CHEST_OPEN = registerSoundEvents("block.brazier_chest.open");
    public static RegistrySupplier<SoundEvent> BRAZIER_CHEST_CLOSE = registerSoundEvents("block.brazier_chest.close");
    public static RegistrySupplier<SoundEvent> BRAZIER_CHEST_LIT = registerSoundEvents("block.brazier_chest.lit");

    // ENTITIES:

    public static RegistrySupplier<SoundEvent> ENTITY_WARPHOPPER_AMBIENT = registerSoundEvents("entity.warphopper.ambient");
    public static RegistrySupplier<SoundEvent> ENTITY_WARPHOPPER_HURT = registerSoundEvents("entity.warphopper.hurt");
    public static RegistrySupplier<SoundEvent> ENTITY_WARPHOPPER_DEATH = registerSoundEvents("entity.warphopper.death");
    public static RegistrySupplier<SoundEvent> ENTITY_WARPHOPPER_CLOAK = registerSoundEvents("entity.warphopper.cloak");

    public static RegistrySupplier<SoundEvent> ENTITY_WISP_AMBIENT = registerSoundEvents("entity.wisp.ambient");
    public static RegistrySupplier<SoundEvent> ENTITY_WISP_HURT = registerSoundEvents("entity.wisp.hurt");

    public static RegistrySupplier<SoundEvent> ENTITY_APPARITION_AMBIENT = registerSoundEvents("entity.apparition.ambient");
    public static RegistrySupplier<SoundEvent> ENTITY_APPARITION_HURT = registerSoundEvents("entity.apparition.hurt");
    public static RegistrySupplier<SoundEvent> ENTITY_APPARITION_DEATH = registerSoundEvents("entity.apparition.death");

    public static RegistrySupplier<SoundEvent> ENTITY_VESSEL_AMBIENT = registerSoundEvents("entity.vessel.ambient");
    public static RegistrySupplier<SoundEvent> ENTITY_VESSEL_HURT = registerSoundEvents("entity.vessel.hurt");
    public static RegistrySupplier<SoundEvent> ENTITY_VESSEL_DEATH = registerSoundEvents("entity.vessel.death");

    public static RegistrySupplier<SoundEvent> ENTITY_PLAYER_HURT_IGNEOUS_THORNS = registerSoundEvents("entity.player.hurt_igneous_thorns");
    public static RegistrySupplier<SoundEvent> ENTITY_PLAYER_HURT_SUFFOCATION = registerSoundEvents("entity.player.hurt_suffocation");


    // MUSIC:
    public static RegistrySupplier<SoundEvent> MUSIC_DISC_CRICKET = registerSoundEvents("music_disc.cricket");

    private static RegistrySupplier<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(NetherExp.MOD_ID, name)));
    }

    public static void init() {
        SOUND_EVENTS.register();
    }
}
