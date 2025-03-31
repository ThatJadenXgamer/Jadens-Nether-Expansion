package net.jadenxgamer.netherexp.registry.misc_registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JNESoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, NetherExp.MOD_ID);

    //// BLOCKS:
    
    // Soul Slate

    public static RegistryObject<SoundEvent> BLOCK_SOUL_SLATE_BREAK = registerSoundEvents("block.soul_slate.break");
    public static RegistryObject<SoundEvent> BLOCK_SOUL_SLATE_STEP = registerSoundEvents("block.soul_slate.step");
    public static RegistryObject<SoundEvent> BLOCK_SOUL_SLATE_PLACE = registerSoundEvents("block.soul_slate.place");
    public static RegistryObject<SoundEvent> BLOCK_SOUL_SLATE_HIT = registerSoundEvents("block.soul_slate.hit");
    public static RegistryObject<SoundEvent> BLOCK_SOUL_SLATE_FALL = registerSoundEvents("block.soul_slate.fall");

    // Soul Slate Bricks

    public static RegistryObject<SoundEvent> BLOCK_SOUL_SLATE_BRICKS_BREAK = registerSoundEvents("block.soul_slate_bricks.break");
    public static RegistryObject<SoundEvent> BLOCK_SOUL_SLATE_BRICKS_STEP = registerSoundEvents("block.soul_slate_bricks.step");
    public static RegistryObject<SoundEvent> BLOCK_SOUL_SLATE_BRICKS_PLACE = registerSoundEvents("block.soul_slate_bricks.place");
    public static RegistryObject<SoundEvent> BLOCK_SOUL_SLATE_BRICKS_HIT = registerSoundEvents("block.soul_slate_bricks.hit");
    public static RegistryObject<SoundEvent> BLOCK_SOUL_SLATE_BRICKS_FALL = registerSoundEvents("block.soul_slate_bricks.fall");

    // Netherrack Bricks

    public static RegistryObject<SoundEvent> BLOCK_NETHERRACK_BRICKS_BREAK = registerSoundEvents("block.netherrack_bricks.break");
    public static RegistryObject<SoundEvent> BLOCK_NETHERRACK_BRICKS_STEP = registerSoundEvents("block.netherrack_bricks.step");
    public static RegistryObject<SoundEvent> BLOCK_NETHERRACK_BRICKS_PLACE = registerSoundEvents("block.netherrack_bricks.place");
    public static RegistryObject<SoundEvent> BLOCK_NETHERRACK_BRICKS_HIT = registerSoundEvents("block.netherrack_bricks.hit");
    public static RegistryObject<SoundEvent> BLOCK_NETHERRACK_BRICKS_FALL = registerSoundEvents("block.netherrack_bricks.fall");

    // Basalt Bricks

    public static RegistryObject<SoundEvent> BLOCK_BASALT_BRICKS_BREAK = registerSoundEvents("block.basalt_bricks.break");
    public static RegistryObject<SoundEvent> BLOCK_BASALT_BRICKS_STEP = registerSoundEvents("block.basalt_bricks.step");
    public static RegistryObject<SoundEvent> BLOCK_BASALT_BRICKS_PLACE = registerSoundEvents("block.basalt_bricks.place");
    public static RegistryObject<SoundEvent> BLOCK_BASALT_BRICKS_HIT = registerSoundEvents("block.basalt_bricks.hit");
    public static RegistryObject<SoundEvent> BLOCK_BASALT_BRICKS_FALL = registerSoundEvents("block.basalt_bricks.fall");

    // White Ash

    public static RegistryObject<SoundEvent> BLOCK_WHITE_ASH_BREAK = registerSoundEvents("block.white_ash.break");
    public static RegistryObject<SoundEvent> BLOCK_WHITE_ASH_STEP = registerSoundEvents("block.white_ash.step");
    public static RegistryObject<SoundEvent> BLOCK_WHITE_ASH_PLACE = registerSoundEvents("block.white_ash.place");
    public static RegistryObject<SoundEvent> BLOCK_WHITE_ASH_HIT = registerSoundEvents("block.white_ash.hit");
    public static RegistryObject<SoundEvent> BLOCK_WHITE_ASH_FALL = registerSoundEvents("block.white_ash.fall");

    // Smokestalk
    public static RegistryObject<SoundEvent> BLOCK_SMOKESTALK_BREAK = registerSoundEvents("block.smokestalk.break");
    public static RegistryObject<SoundEvent> BLOCK_SMOKESTALK_PLACE = registerSoundEvents("block.smokestalk.place");

    // Smokestalk Wood

    public static RegistryObject<SoundEvent> BLOCK_SMOKESTALK_WOOD_BREAK = registerSoundEvents("block.smokestalk_wood.break");
    public static RegistryObject<SoundEvent> BLOCK_SMOKESTALK_WOOD_STEP = registerSoundEvents("block.smokestalk_wood.step");
    public static RegistryObject<SoundEvent> BLOCK_SMOKESTALK_WOOD_PLACE = registerSoundEvents("block.smokestalk_wood.place");
    public static RegistryObject<SoundEvent> BLOCK_SMOKESTALK_WOOD_HIT = registerSoundEvents("block.smokestalk_wood.hit");
    public static RegistryObject<SoundEvent> BLOCK_SMOKESTALK_WOOD_FALL = registerSoundEvents("block.smokestalk_wood.fall");

    // Soul Candle

    public static RegistryObject<SoundEvent> BLOCK_SOUL_CANDLE_BREAK = registerSoundEvents("block.soul_candle.break");
    public static RegistryObject<SoundEvent> BLOCK_SOUL_CANDLE_STEP = registerSoundEvents("block.soul_candle.step");
    public static RegistryObject<SoundEvent> BLOCK_SOUL_CANDLE_PLACE = registerSoundEvents("block.soul_candle.place");
    public static RegistryObject<SoundEvent> BLOCK_SOUL_CANDLE_HIT = registerSoundEvents("block.soul_candle.hit");
    public static RegistryObject<SoundEvent> BLOCK_SOUL_CANDLE_FALL = registerSoundEvents("block.soul_candle.fall");
    public static RegistryObject<SoundEvent> BLOCK_SOUL_CANDLE_AMBIENT = registerSoundEvents("block.soul_candle.ambient");

    // Blackstone

    public static RegistryObject<SoundEvent> BLOCK_BLACKSTONE_BREAK = registerSoundEvents("block.blackstone.break");
    public static RegistryObject<SoundEvent> BLOCK_BLACKSTONE_STEP = registerSoundEvents("block.blackstone.step");
    public static RegistryObject<SoundEvent> BLOCK_BLACKSTONE_PLACE = registerSoundEvents("block.blackstone.place");
    public static RegistryObject<SoundEvent> BLOCK_BLACKSTONE_HIT = registerSoundEvents("block.blackstone.hit");
    public static RegistryObject<SoundEvent> BLOCK_BLACKSTONE_FALL = registerSoundEvents("block.blackstone.fall");

    // Polished Blackstone Bricks

    public static RegistryObject<SoundEvent> BLOCK_POLISHED_BLACKSTONE_BRICKS_BREAK = registerSoundEvents("block.polished_blackstone_bricks.break");
    public static RegistryObject<SoundEvent> BLOCK_POLISHED_BLACKSTONE_BRICKS_STEP = registerSoundEvents("block.polished_blackstone_bricks.step");
    public static RegistryObject<SoundEvent> BLOCK_POLISHED_BLACKSTONE_BRICKS_PLACE = registerSoundEvents("block.polished_blackstone_bricks.place");
    public static RegistryObject<SoundEvent> BLOCK_POLISHED_BLACKSTONE_BRICKS_HIT = registerSoundEvents("block.polished_blackstone_bricks.hit");
    public static RegistryObject<SoundEvent> BLOCK_POLISHED_BLACKSTONE_BRICKS_FALL = registerSoundEvents("block.polished_blackstone_bricks.fall");

    // Black Ice

    public static RegistryObject<SoundEvent> BLOCK_BLACK_ICE_BREAK = registerSoundEvents("block.black_ice.break");
    public static RegistryObject<SoundEvent> BLOCK_BLACK_ICE_STEP = registerSoundEvents("block.black_ice.step");
    public static RegistryObject<SoundEvent> BLOCK_BLACK_ICE_PLACE = registerSoundEvents("block.black_ice.place");
    public static RegistryObject<SoundEvent> BLOCK_BLACK_ICE_HIT = registerSoundEvents("block.black_ice.hit");
    public static RegistryObject<SoundEvent> BLOCK_BLACK_ICE_FALL = registerSoundEvents("block.black_ice.fall");
    public static RegistryObject<SoundEvent> BLOCK_THIN_BLACK_ICE_CRACKING = registerSoundEvents("block.thin_black_ice.cracking");

    // Magma Block

    public static RegistryObject<SoundEvent> BLOCK_MAGMA_BLOCK_BREAK = registerSoundEvents("block.magma_block.break");
    public static RegistryObject<SoundEvent> BLOCK_MAGMA_BLOCK_PLACE = registerSoundEvents("block.magma_block.place");

    // Soul Magma Block

    public static RegistryObject<SoundEvent> BLOCK_SOUL_MAGMA_BLOCK_BREAK = registerSoundEvents("block.soul_magma_block.break");
    public static RegistryObject<SoundEvent> BLOCK_SOUL_MAGMA_BLOCK_PLACE = registerSoundEvents("block.soul_magma_block.place");

    // Soul Magma Block

    public static RegistryObject<SoundEvent> BLOCK_GLOWSTONE_BREAK = registerSoundEvents("block.glowstone.break");
    public static RegistryObject<SoundEvent> BLOCK_GLOWSTONE_PLACE = registerSoundEvents("block.glowstone.place");

    // Quartz Block

    public static RegistryObject<SoundEvent> BLOCK_QUARTZ_BLOCK_BREAK = registerSoundEvents("block.quartz_block.break");
    public static RegistryObject<SoundEvent> BLOCK_QUARTZ_BLOCK_PLACE = registerSoundEvents("block.quartz_block.place");

    // SUSPICIOUS SOUL SAND

    public static RegistryObject<SoundEvent> BLOCK_SUSPICIOUS_SOUL_SAND_BREAK = registerSoundEvents("block.suspicious_soul_sand.break");

    // HAZE BLOCK
    public static RegistryObject<SoundEvent> BLOCK_HAZE_BLOCK_BREAK = registerSoundEvents("block.haze_block.break");

    // FROGMIST
    public static RegistryObject<SoundEvent> BLOCK_FROGMIST_BREAK = registerSoundEvents("block.frogmist.break");

    // CEREBRAGE
    public static RegistryObject<SoundEvent> BLOCK_CEREBRAGE_STEM_BREAK = registerSoundEvents("block.cerebrage_stem.break");
    public static RegistryObject<SoundEvent> BLOCK_CEREBRAGE_PLANT = registerSoundEvents("block.cerebrage.plant");


    // ITEMS & EVENTS:

    public static RegistryObject<SoundEvent> GILDING = registerSoundEvents("item.gold_ingot.gilding");
    public static RegistryObject<SoundEvent> ECTOPLASM_FREEZE = registerSoundEvents("block.ectoplasm.freeze");
    public static RegistryObject<SoundEvent> ECTOPLASM_WHISPERING = registerSoundEvents("block.ectoplasm.whispering");
    public static RegistryObject<SoundEvent> SOUL_SLATE_SOLIDIFYING = registerSoundEvents("block.soul_slate.solidifying");
    public static RegistryObject<SoundEvent> BUCKET_EMPTY_ECTOPLASM = registerSoundEvents("item.bucket.empty_ectoplasm");
    public static RegistryObject<SoundEvent> BUCKET_FILL_ECTOPLASM = registerSoundEvents("item.bucket.fill_ectoplasm");
    public static RegistryObject<SoundEvent> BRUSH_BRUSHING_SOUL_SAND_COMPLETE = registerSoundEvents("item.brush.brushing_soul_sand_complete");
    public static RegistryObject<SoundEvent> EXPLOSIVE_SCORIA_AMBIENT = registerSoundEvents("block.explosive_scoria.ambient");
    public static RegistryObject<SoundEvent> SPORESHROOM_TRAMPOLINED = registerSoundEvents("block.sporeshroom.trampolined");
    public static RegistryObject<SoundEvent> LIGHTSPORES_APPLY = registerSoundEvents("block.lightspores.apply");
    public static RegistryObject<SoundEvent> LIGHTSPORES_SHEAR = registerSoundEvents("block.lightspores.shear");
    public static RegistryObject<SoundEvent> SOUL_SWIRLS_BOOST = registerSoundEvents("block.soul_swirls.boost");
    public static RegistryObject<SoundEvent> ANTIDOTE_NEGATE = registerSoundEvents("item.antidote.negate");
    public static RegistryObject<SoundEvent> GRENADE_ANTIDOTE_TICK = registerSoundEvents("item.grenade_antidote.tick");
    public static RegistryObject<SoundEvent> GRENADE_ANTIDOTE_EXPLODE = registerSoundEvents("item.grenade_antidote.explode");
    public static RegistryObject<SoundEvent> SHOTGUN_USE = registerSoundEvents("item.shotgun.use");
    public static RegistryObject<SoundEvent> SHOTGUN_LOAD = registerSoundEvents("item.shotgun.load");
    public static RegistryObject<SoundEvent> PUMP_CHARGE_SHOTGUN_ALARM = registerSoundEvents("item.pump_charge_shotgun.alarm");
    public static RegistryObject<SoundEvent> JACKHAMMER_FIST_HIT = registerSoundEvents("item.jackhammer_fist.hit");
    public static RegistryObject<SoundEvent> JACKHAMMER_FIST_PULL = registerSoundEvents("item.jackhammer_fist.pull");
    public static RegistryObject<SoundEvent> JACKHAMMER_FIST_LOOP = registerSoundEvents("item.jackhammer_fist.loop");
    public static RegistryObject<SoundEvent> BRAZIER_CHEST_OPEN = registerSoundEvents("block.brazier_chest.open");
    public static RegistryObject<SoundEvent> BRAZIER_CHEST_CLOSE = registerSoundEvents("block.brazier_chest.close");
    public static RegistryObject<SoundEvent> BRAZIER_CHEST_LIT = registerSoundEvents("block.brazier_chest.lit");
    public static RegistryObject<SoundEvent> TREACHEROUS_CANDLE_VICTORY = registerSoundEvents("block.treacherous_candle.victory");
    public static RegistryObject<SoundEvent> TREACHEROUS_CANDLE_DEFEAT = registerSoundEvents("block.treacherous_candle.defeat");
    public static RegistryObject<SoundEvent> TREACHEROUS_CANDLE_ROUND = registerSoundEvents("block.treacherous_candle.round");
    public static RegistryObject<SoundEvent> TREACHEROUS_CANDLE_SPAWN = registerSoundEvents("block.treacherous_candle.spawn");
    public static RegistryObject<SoundEvent> DISCERNMENT_GLASS_ADD = registerSoundEvents("block.discernment_glass.add");
    public static RegistryObject<SoundEvent> DISCERNMENT_GLASS_REMOVE = registerSoundEvents("block.discernment_glass.remove");
    public static RegistryObject<SoundEvent> COMPASS_TICK = registerSoundEvents("item.compass.tick");

    // ENTITIES:

    public static RegistryObject<SoundEvent> ENTITY_WARPHOPPER_AMBIENT = registerSoundEvents("entity.warphopper.ambient");
    public static RegistryObject<SoundEvent> ENTITY_WARPHOPPER_HURT = registerSoundEvents("entity.warphopper.hurt");
    public static RegistryObject<SoundEvent> ENTITY_WARPHOPPER_DEATH = registerSoundEvents("entity.warphopper.death");
    public static RegistryObject<SoundEvent> ENTITY_WARPHOPPER_CLOAK = registerSoundEvents("entity.warphopper.cloak");

    public static RegistryObject<SoundEvent> ENTITY_WISP_AMBIENT = registerSoundEvents("entity.wisp.ambient");
    public static RegistryObject<SoundEvent> ENTITY_WISP_HURT = registerSoundEvents("entity.wisp.hurt");

    public static RegistryObject<SoundEvent> ENTITY_APPARITION_AMBIENT = registerSoundEvents("entity.apparition.ambient");
    public static RegistryObject<SoundEvent> ENTITY_APPARITION_HURT = registerSoundEvents("entity.apparition.hurt");
    public static RegistryObject<SoundEvent> ENTITY_APPARITION_DEATH = registerSoundEvents("entity.apparition.death");
    public static RegistryObject<SoundEvent> ENTITY_APPARITION_FLY = registerSoundEvents("entity.apparition.fly");
    public static RegistryObject<SoundEvent> ENTITY_APPARITION_ATTACK = registerSoundEvents("entity.apparition.attack");

    public static RegistryObject<SoundEvent> ENTITY_CARCASS_HURT = registerSoundEvents("entity.carcass.hurt");
    public static RegistryObject<SoundEvent> ENTITY_CARCASS_DEATH = registerSoundEvents("entity.carcass.death");
    public static RegistryObject<SoundEvent> ENTITY_CARCASS_REANIMATE = registerSoundEvents("entity.carcass.reanimate");

    public static RegistryObject<SoundEvent> ENTITY_VESSEL_AMBIENT = registerSoundEvents("entity.vessel.ambient");
    public static RegistryObject<SoundEvent> ENTITY_VESSEL_WARN = registerSoundEvents("entity.vessel.warn");
    public static RegistryObject<SoundEvent> ENTITY_VESSEL_FIRE = registerSoundEvents("entity.vessel.fire");
    public static RegistryObject<SoundEvent> ENTITY_VESSEL_HURT = registerSoundEvents("entity.vessel.hurt");
    public static RegistryObject<SoundEvent> ENTITY_VESSEL_DEATH = registerSoundEvents("entity.vessel.death");

    public static RegistryObject<SoundEvent> ENTITY_SHOTGUN_GUY_AMBIENT = registerSoundEvents("entity.shotgun_guy.ambient");
    public static RegistryObject<SoundEvent> ENTITY_SHOTGUN_GUY_HURT = registerSoundEvents("entity.shotgun_guy.hurt");
    public static RegistryObject<SoundEvent> ENTITY_SHOTGUN_GUY_DEATH = registerSoundEvents("entity.shotgun_guy.death");
    public static RegistryObject<SoundEvent> ENTITY_SHOTGUN_GUY_FIRE = registerSoundEvents("entity.shotgun_guy.fire");

    public static RegistryObject<SoundEvent> ENTITY_ECTO_SLAB_JUMP = registerSoundEvents("entity.ecto_slab.jump");
    public static RegistryObject<SoundEvent> ENTITY_ECTO_SLAB_SQUISH = registerSoundEvents("entity.ecto_slab.squish");
    public static RegistryObject<SoundEvent> ENTITY_ECTO_SLAB_SQUISH_SMALL = registerSoundEvents("entity.ecto_slab.squish_small");
    public static RegistryObject<SoundEvent> ENTITY_ECTO_SLAB_WARN = registerSoundEvents("entity.ecto_slab.warn");
    public static RegistryObject<SoundEvent> ENTITY_ECTO_SLAB_WARN_SMALL = registerSoundEvents("entity.ecto_slab.warn_small");

    public static RegistryObject<SoundEvent> ENTITY_BANSHEE_AMBIENT = registerSoundEvents("entity.banshee.ambient");
    public static RegistryObject<SoundEvent> ENTITY_BANSHEE_HURT = registerSoundEvents("entity.banshee.hurt");
    public static RegistryObject<SoundEvent> ENTITY_BANSHEE_DEATH = registerSoundEvents("entity.banshee.death");
    public static RegistryObject<SoundEvent> ENTITY_BANSHEE_TELEPORT = registerSoundEvents("entity.banshee.teleport");
    public static RegistryObject<SoundEvent> ENTITY_BANSHEE_SHOOT = registerSoundEvents("entity.banshee.shoot");

    public static RegistryObject<SoundEvent> ENTITY_STAMPEDE_AMBIENT = registerSoundEvents("entity.stampede.ambient");
    public static RegistryObject<SoundEvent> ENTITY_STAMPEDE_HURT = registerSoundEvents("entity.stampede.hurt");
    public static RegistryObject<SoundEvent> ENTITY_STAMPEDE_DEATH = registerSoundEvents("entity.stampede.death");
    public static RegistryObject<SoundEvent> ENTITY_STAMPEDE_STEP = registerSoundEvents("entity.stampede.step");
    public static RegistryObject<SoundEvent> ENTITY_STAMPEDE_EAT = registerSoundEvents("entity.stampede.eat");
    public static RegistryObject<SoundEvent> ENTITY_STAMPEDE_AGITATED = registerSoundEvents("entity.stampede.agitated");

    public static RegistryObject<SoundEvent> ENTITY_PLAYER_HURT_IGNEOUS_THORNS = registerSoundEvents("entity.player.hurt_igneous_thorns");
    public static RegistryObject<SoundEvent> ENTITY_PLAYER_HURT_SUFFOCATION = registerSoundEvents("entity.player.hurt_suffocation");


    // MUSIC:
    public static RegistryObject<SoundEvent> MUSIC_DISC_CRICKET = registerSoundEvents("music_disc.cricket");
    public static RegistryObject<SoundEvent> MUSIC_DISC_BUCKSHOT_WONDERLAND = registerSoundEvents("music_disc.buckshot_wonderland");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(NetherExp.MOD_ID, name)));
    }

    public static void init(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
