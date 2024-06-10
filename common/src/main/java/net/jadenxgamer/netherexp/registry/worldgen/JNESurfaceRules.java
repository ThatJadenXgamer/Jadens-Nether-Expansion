package net.jadenxgamer.netherexp.registry.worldgen;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class JNESurfaceRules {

    private static final SurfaceRules.RuleSource BEDROCK = SurfaceRules.state(Blocks.BEDROCK.defaultBlockState());
    private static final SurfaceRules.RuleSource NETHERRACK = SurfaceRules.state(Blocks.NETHERRACK.defaultBlockState());
    private static final SurfaceRules.RuleSource SOUL_SAND = SurfaceRules.state(Blocks.SOUL_SAND.defaultBlockState());
    private static final SurfaceRules.RuleSource SOUL_SOIL = SurfaceRules.state(Blocks.SOUL_SOIL.defaultBlockState());
    private static final SurfaceRules.RuleSource GRAVEL = SurfaceRules.state(Blocks.GRAVEL.defaultBlockState());

//    public static SurfaceRules.RuleSource init() {
//        return SurfaceRules.sequence(
//                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK),
//                SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())),
//                        SurfaceRules.ifTrue(SurfaceRules.yBlockCheck(VerticalAnchor.belowTop(5), 0), NETHERRACK)),
//                SurfaceRules.ifTrue(SurfaceRules.isBiome(JNEBiomes.SORROWSQUASH_PASTURES), SurfaceRules.sequence(
//                        SurfaceRules.ifTrue(SurfaceRules.UNDER_CEILING, SurfaceRules.sequence(
//                                        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.NETHER_STATE_SELECTOR, 0, 1.7976931348623157e+308), SOUL_SAND),
//                                        SOUL_SOIL)
//                        ),
//                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.sequence(
//                                        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.PATCH, -0.012, 1.7976931348623157e+30),
//                                                SurfaceRules.ifTrue(SurfaceRules.yStartCheck(VerticalAnchor.absolute(30), 0),
//                                                        SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(35), 0)), GRAVEL))),
//                                        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.NETHER_STATE_SELECTOR, 0, 1.7976931348623157e+308), SOUL_SAND),
//                                        SOUL_SOIL)
//                        ))
//                ),
//                SurfaceRules.ifTrue(SurfaceRules.isBiome(JNEBiomes.BLACK_ICE_GLACIERS), SurfaceRules.sequence(
//                        SurfaceRules.ifTrue(SurfaceRules.UNDER_CEILING, SurfaceRules.sequence(
//                                        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.NETHER_STATE_SELECTOR, 0, 1.7976931348623157e+308), SOUL_SAND),
//                                        SOUL_SOIL)
//                        ),
//                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.sequence(
//                                        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.PATCH, -0.012, 1.7976931348623157e+30),
//                                                SurfaceRules.ifTrue(SurfaceRules.yStartCheck(VerticalAnchor.absolute(30), 0),
//                                                        SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(35), 0)), GRAVEL))),
//                                        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.NETHER_STATE_SELECTOR, 0, 1.7976931348623157e+308), SOUL_SAND),
//                                        SOUL_SOIL)
//                        ))
//                ),
//                NETHERRACK
//        );
//    }
}
