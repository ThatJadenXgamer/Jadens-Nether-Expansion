package net.jadenxgamer.netherexp.registry;

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
    public static final Supplier<SoundEvent> LESION_BLOCK_HARVEST = registerSoundEvents("block.lesion_block.harvest");
    public static final Supplier<SoundEvent> LESION_BLOCK_GROWS = registerSoundEvents("block.lesion_block.grows");
    public static final Supplier<SoundEvent> SPORESHROOM_TRAMPOLINED = registerSoundEvents("block.sporeshroom.trampolined");
    public static final Supplier<SoundEvent> GEYSER_STEAM = registerSoundEvents("block.geyser.steam");
    public static final Supplier<SoundEvent> ECTOPLASM_FREEZE = registerSoundEvents("block.ectoplasm.freeze");
    public static final Supplier<SoundEvent> ECTOPLASM_WHISPERING = registerSoundEvents("block.ectoplasm.whispering");
    public static final Supplier<SoundEvent> DRIP_ECTOPLASM_INTO_CAULDRON = registerSoundEvents("block.pointed_dripstone.drip_ectoplasm_into_cauldron");
    public static final Supplier<SoundEvent> THIN_BLACK_ICE_CRACKING = registerSoundEvents("block.thin_black_ice.cracking");
    public static final Supplier<SoundEvent> DISCERNMENT_GLASS_ADD = registerSoundEvents("block.discernment_glass.add");
    public static final Supplier<SoundEvent> DISCERNMENT_GLASS_REMOVE = registerSoundEvents("block.discernment_glass.remove");
    public static final Supplier<SoundEvent> CEREBRAGE_PLANT = registerSoundEvents("block.cerebrage.plant");
    public static final Supplier<SoundEvent> GLOWSPORES_APPLY = registerSoundEvents("block.glowspores.apply");
    public static final Supplier<SoundEvent> GLOWSPORES_SHEAR = registerSoundEvents("block.glowspores.shear");

    public static final Supplier<SoundEvent> ANTIDOTE_NEGATE = registerSoundEvents("item.antidote.negate");
    public static final Supplier<SoundEvent> ANTIDOTE_EXPIRED = registerSoundEvents("item.antidote.expired");
    public static final Supplier<SoundEvent> BUCKET_FILL_ECTOPLASM = registerSoundEvents("item.bucket.fill_ectoplasm");
    public static final Supplier<SoundEvent> BUCKET_EMPTY_ECTOPLASM = registerSoundEvents("item.bucket.empty_ectoplasm");
    public static final Supplier<SoundEvent> SHOTGUN_USE = registerSoundEvents("item.shotgun.use");
    public static final Supplier<SoundEvent> SHOTGUN_LOAD = registerSoundEvents("item.shotgun.load");

    public static final Supplier<SoundEvent> GOLD_GILDING = registerSoundEvents("item.gold.gilding");

    public static final Supplier<SoundEvent> WISP_AMBIENT = registerSoundEvents("entity.wisp.ambient");
    public static final Supplier<SoundEvent> WISP_HURT = registerSoundEvents("entity.wisp.hurt");
    public static final Supplier<SoundEvent> WISP_DEATH = registerSoundEvents("entity.wisp.death");

    public static final Supplier<SoundEvent> APPARITION_AMBIENT = registerSoundEvents("entity.apparition.ambient");
    public static final Supplier<SoundEvent> APPARITION_HURT = registerSoundEvents("entity.apparition.hurt");
    public static final Supplier<SoundEvent> APPARITION_DEATH = registerSoundEvents("entity.apparition.death");
    public static final Supplier<SoundEvent> APPARITION_FLY = registerSoundEvents("entity.apparition.fly");
    public static final Supplier<SoundEvent> APPARITION_ATTACK = registerSoundEvents("entity.apparition.attack");
    public static final Supplier<SoundEvent> APPARITION_POSSESSION = registerSoundEvents("entity.apparition.possession");
    
    public static final Supplier<SoundEvent> VESSEL_AMBIENT = registerSoundEvents("entity.vessel.ambient");
    public static final Supplier<SoundEvent> VESSEL_HURT = registerSoundEvents("entity.vessel.hurt");
    public static final Supplier<SoundEvent> VESSEL_DEATH = registerSoundEvents("entity.vessel.death");
    public static final Supplier<SoundEvent> VESSEL_WARN = registerSoundEvents("entity.vessel.warn");
    public static final Supplier<SoundEvent> VESSEL_FIRE = registerSoundEvents("entity.vessel.fire");
    
    public static final Supplier<SoundEvent> SHOTGUN_GUY_AMBIENT = registerSoundEvents("entity.shotgun_guy.ambient");
    public static final Supplier<SoundEvent> SHOTGUN_GUY_HURT = registerSoundEvents("entity.shotgun_guy.hurt");
    public static final Supplier<SoundEvent> SHOTGUN_GUY_DEATH = registerSoundEvents("entity.shotgun_guy.death");
    public static final Supplier<SoundEvent> SHOTGUN_GUY_FIRE = registerSoundEvents("entity.shotgun_guy.fire");

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

    public static final Supplier<SoundEvent> SUSPICIOUS_SOUL_SAND_PLACE = registerSoundEvents("block.suspicious_soul_sand.place");
    public static final Supplier<SoundEvent> SUSPICIOUS_SOUL_SAND_BREAK = registerSoundEvents("block.suspicious_soul_sand.break");

    public static final Supplier<SoundEvent> FOSSIL_ORE_PLACE = registerSoundEvents("block.fossil_ore.place");
    public static final Supplier<SoundEvent> FOSSIL_ORE_BREAK = registerSoundEvents("block.fossil_ore.break");

    public static final Supplier<SoundEvent> SOUL_MAGMA_BLOCK_PLACE = registerSoundEvents("block.soul_magma_block.place");
    public static final Supplier<SoundEvent> SOUL_MAGMA_BLOCK_BREAK = registerSoundEvents("block.soul_magma_block.break");

    public static final Supplier<SoundEvent> NETHERRACK_BRICKS_PLACE = registerSoundEvents("block.netherrack_bricks.place");
    public static final Supplier<SoundEvent> NETHERRACK_BRICKS_BREAK = registerSoundEvents("block.netherrack_bricks.break");

    public static final Supplier<SoundEvent> POLISHED_BASALT_BRICKS_PLACE = registerSoundEvents("block.polished_basalt_bricks.place");
    public static final Supplier<SoundEvent> POLISHED_BASALT_BRICKS_BREAK = registerSoundEvents("block.polished_basalt_bricks.break");

    public static final Supplier<SoundEvent> CEREBRAGE_STEM_PLACE = registerSoundEvents("block.cerebrage_stem.place");
    public static final Supplier<SoundEvent> CEREBRAGE_STEM_BREAK = registerSoundEvents("block.cerebrage_stem.break");

    public static final Supplier<SoundEvent> LESION_BLOCK_PLACE = registerSoundEvents("block.lesion_block.place");
    public static final Supplier<SoundEvent> LESION_BLOCK_BREAK = registerSoundEvents("block.lesion_block.break");

    public static final Supplier<SoundEvent> SHOTGUN_BARREL_PLACE = registerSoundEvents("block.shotgun_barrel.place");
    public static final Supplier<SoundEvent> SHOTGUN_BARREL_BREAK = registerSoundEvents("block.shotgun_barrel.break");

    public static final Supplier<SoundEvent> FROGMIST_PLACE = registerSoundEvents("block.frogmist.place");
    public static final Supplier<SoundEvent> FROGMIST_BREAK = registerSoundEvents("block.frogmist.break");

    public static final Supplier<SoundEvent> QUARTZ_BLOCK_PLACE = registerSoundEvents("block.quartz_block.place");
    public static final Supplier<SoundEvent> QUARTZ_BLOCK_BREAK = registerSoundEvents("block.quartz_block.break");

    public static final Supplier<SoundEvent> BLACK_ICE_PLACE = registerSoundEvents("block.black_ice.place");
    public static final Supplier<SoundEvent> BLACK_ICE_BREAK = registerSoundEvents("block.black_ice.break");
    public static final Supplier<SoundEvent> BLACK_ICE_STEP = registerSoundEvents("block.black_ice.step");
    public static final Supplier<SoundEvent> BLACK_ICE_HIT = registerSoundEvents("block.black_ice.hit");
    public static final Supplier<SoundEvent> BLACK_ICE_FALL = registerSoundEvents("block.black_ice.fall");

    public static final Supplier<SoundEvent> WITHER_BONE_BLOCK_PLACE = registerSoundEvents("block.wither_bone_block.place");
    public static final Supplier<SoundEvent> WITHER_BONE_BLOCK_BREAK = registerSoundEvents("block.wither_bone_block.break");
    public static final Supplier<SoundEvent> WITHER_BONE_BLOCK_STEP = registerSoundEvents("block.wither_bone_block.step");
    public static final Supplier<SoundEvent> WITHER_BONE_BLOCK_HIT = registerSoundEvents("block.wither_bone_block.hit");
    public static final Supplier<SoundEvent> WITHER_BONE_BLOCK_FALL = registerSoundEvents("block.wither_bone_block.fall");

    public static final Supplier<SoundEvent> BONE_PIKE_PLACE = registerSoundEvents("block.bone_pike.place");
    public static final Supplier<SoundEvent> BONE_PIKE_BREAK = registerSoundEvents("block.bone_pike.break");

    public static final Supplier<SoundEvent> ANCIENT_CANDLE_PLACE = registerSoundEvents("block.ancient_candle.place");
    public static final Supplier<SoundEvent> ANCIENT_CANDLE_BREAK = registerSoundEvents("block.ancient_candle.break");

    public static final Supplier<SoundEvent> ANCIENT_WAX_PLACE = registerSoundEvents("block.ancient_wax.place");
    public static final Supplier<SoundEvent> ANCIENT_WAX_BREAK = registerSoundEvents("block.ancient_wax.break");

    public static final Supplier<SoundEvent> NETHERITE_GRATE_PLACE = registerSoundEvents("block.netherite_grate.place");
    public static final Supplier<SoundEvent> NETHERITE_GRATE_BREAK = registerSoundEvents("block.netherite_grate.break");
    public static final Supplier<SoundEvent> NETHERITE_GRATE_STEP = registerSoundEvents("block.netherite_grate.step");
    public static final Supplier<SoundEvent> NETHERITE_GRATE_HIT = registerSoundEvents("block.netherite_grate.hit");
    public static final Supplier<SoundEvent> NETHERITE_GRATE_FALL = registerSoundEvents("block.netherite_grate.fall");

    public static final Supplier<SoundEvent> RUSTY_NETHERITE_GRATE_PLACE = registerSoundEvents("block.rusty_netherite_grate.place");
    public static final Supplier<SoundEvent> RUSTY_NETHERITE_GRATE_BREAK = registerSoundEvents("block.rusty_netherite_grate.break");
    public static final Supplier<SoundEvent> SOUL_GLASS_BREAK = registerSoundEvents("block.soul_glass.break");

    public static final Supplier<SoundEvent> BLACKSTONE_PLACE = registerSoundEvents("block.blackstone.place");
    public static final Supplier<SoundEvent> BLACKSTONE_BREAK = registerSoundEvents("block.blackstone.break");
    public static final Supplier<SoundEvent> BLACKSTONE_STEP = registerSoundEvents("block.blackstone.step");
    public static final Supplier<SoundEvent> BLACKSTONE_HIT = registerSoundEvents("block.blackstone.hit");
    public static final Supplier<SoundEvent> BLACKSTONE_FALL = registerSoundEvents("block.blackstone.fall");

    public static final Supplier<SoundEvent> POLISHED_BLACKSTONE_PLACE = registerSoundEvents("block.polished_blackstone_bricks.place");
    public static final Supplier<SoundEvent> POLISHED_BLACKSTONE_BREAK = registerSoundEvents("block.polished_blackstone_bricks.break");
    public static final Supplier<SoundEvent> POLISHED_BLACKSTONE_STEP = registerSoundEvents("block.polished_blackstone_bricks.step");
    public static final Supplier<SoundEvent> POLISHED_BLACKSTONE_HIT = registerSoundEvents("block.polished_blackstone_bricks.hit");
    public static final Supplier<SoundEvent> POLISHED_BLACKSTONE_FALL = registerSoundEvents("block.polished_blackstone_bricks.fall");

    public static final Supplier<SoundEvent> GLOWSTONE_PLACE = registerSoundEvents("block.glowstone.place");
    public static final Supplier<SoundEvent> GLOWSTONE_BREAK = registerSoundEvents("block.glowstone.break");

    public static final Supplier<SoundEvent> MAGMA_BLOCK_PLACE = registerSoundEvents("block.magma_block.place");
    public static final Supplier<SoundEvent> MAGMA_BLOCK_BREAK = registerSoundEvents("block.magma_block.break");

    private static Supplier<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(NetherExp.id(name)));
    }

    public static void init(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
