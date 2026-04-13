package net.jadenxgamer.netherexp.registry.compat;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.block.BlackIceBlock;
import net.jadenxgamer.netherexp.core.effect.ImmunityEffect;
import net.jadenxgamer.netherexp.core.misc.JNESoundType;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNEMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import team.lodestar.lodestone.systems.block.LodestoneBlockProperties;

import java.util.function.Supplier;

import static net.jadenxgamer.netherexp.util.RegistryHelper.registerBlock;

public class OreganizedCompat {

    public static class Blocks {

        public static final Supplier<Block> GROOVED_BLACK_ICE = registerBlock("grooved_black_ice", () ->
                new BlackIceBlock(LodestoneBlockProperties.of().mapColor(MapColor.COLOR_BLACK).randomTicks().requiresCorrectToolForDrops()
                        .strength(0.3f).lightLevel((state) -> 2).sound(JNESoundType.BLACK_ICE).needsPickaxe()));

        public static void init() {}
    }

    public static class MobEffects {

        public static final Holder<MobEffect> BRAIN_DAMAGE_IMMUNITY = JNEMobEffects.MOB_EFFECTS.register("brain_damage_immunity", () ->
                new ImmunityEffect(MobEffectCategory.BENEFICIAL, NetherExp.idPath("oreganized", "stunning")));


        public static final Holder<MobEffect> TEST = JNEMobEffects.MOB_EFFECTS.register("test", () ->
                new ImmunityEffect(MobEffectCategory.BENEFICIAL, NetherExp.idPath("mod", "test")));

        public static void init() {}
    }
}
