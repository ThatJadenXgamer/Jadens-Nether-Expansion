package net.jadenxgamer.netherexp.core.worldgen;

import net.jadenxgamer.elysium_api.api.surface_rules.ElysiumSurfaceRules;
import net.jadenxgamer.netherexp.core.keys.JNEBiomes;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class JNESurfaceRules {

    private static final SurfaceRules.RuleSource BEDROCK = SurfaceRules.state(Blocks.BEDROCK.defaultBlockState());
    private static final SurfaceRules.RuleSource NETHERRACK = SurfaceRules.state(Blocks.NETHERRACK.defaultBlockState());
    private static final SurfaceRules.RuleSource SOUL_SAND = SurfaceRules.state(Blocks.SOUL_SAND.defaultBlockState());
    private static final SurfaceRules.RuleSource SOUL_SOIL = SurfaceRules.state(Blocks.SOUL_SOIL.defaultBlockState());
    private static final SurfaceRules.RuleSource SOUL_PERMAFROST = SurfaceRules.state(JNEBlocks.SOUL_PERMAFROST.get().defaultBlockState());
    private static final SurfaceRules.RuleSource GRAVEL = SurfaceRules.state(Blocks.GRAVEL.defaultBlockState());
    private static final SurfaceRules.RuleSource SILT = SurfaceRules.state(JNEBlocks.SILT.get().defaultBlockState());

    public static SurfaceRules.RuleSource init() {
        return SurfaceRules.sequence(
                Global.bedrockFloor(),
                Global.bedrockRoof(),
                Global.siltShoreline(),
                BiomeSpecific.blackIceGlaciers()
        );
    }

    private static class Global {

        private static SurfaceRules.RuleSource bedrockFloor() {
            return SurfaceRules.ifTrue(
                    SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)),
                    BEDROCK
            );
        }

        private static SurfaceRules.RuleSource bedrockRoof() {
            return SurfaceRules.ifTrue(
                    SurfaceRules.not(SurfaceRules.verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())),
                    SurfaceRules.ifTrue(SurfaceRules.yBlockCheck(VerticalAnchor.belowTop(5), 0), BEDROCK)
            );
        }

        /**
         * Silt shoreline, chooses between BEACH, COVE, or NONE based on biome tags<p>
         * - COVE (default) - exactly like the vanilla gravel beaches but with silt.<p>
         * - BEACH - full on proper beaches of silt from Y=27 to40 at a very common frequency.<p>
         * - NONE - no silt shores.
         */
        private static SurfaceRules.RuleSource siltShoreline() {
            return SurfaceRules.sequence(
                    // BEACH
                    SurfaceRules.ifTrue(
                            ElysiumSurfaceRules.isBiomeTag(JNETags.Biomes.HAS_BEACH_SHORELINE),
                            beachShore()
                    ),
                    // COVE & NONE
                    SurfaceRules.ifTrue(
                            SurfaceRules.not(ElysiumSurfaceRules.isBiomeTag(JNETags.Biomes.HAS_BEACH_SHORELINE)),
                            SurfaceRules.ifTrue(
                                    SurfaceRules.not(ElysiumSurfaceRules.isBiomeTag(JNETags.Biomes.HAS_NO_SHORELINE)),
                                    coveShore()
                            )
                    )
            );
        }

        private static SurfaceRules.RuleSource beachShore() {
            return SurfaceRules.ifTrue(
                    SurfaceRules.UNDER_FLOOR,
                    SurfaceRules.ifTrue(
                            SurfaceRules.yStartCheck(VerticalAnchor.absolute(27), 0),
                            SurfaceRules.ifTrue(
                                    SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(38), 1)),
                                    SurfaceRules.ifTrue(
                                            SurfaceRules.noiseCondition(Noises.GRAVEL_LAYER, -1.0, 0.4),
                                            SurfaceRules.ifTrue(
                                                    SurfaceRules.not(SurfaceRules.hole()),
                                                    SurfaceRules.sequence(
                                                            SurfaceRules.ifTrue(
                                                                    SurfaceRules.yStartCheck(VerticalAnchor.absolute(32), 0),
                                                                    SILT
                                                            ),
                                                            SurfaceRules.ifTrue(
                                                                    SurfaceRules.not(SurfaceRules.hole()),
                                                                    SILT
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
            );
        }

        private static SurfaceRules.RuleSource coveShore() {
            return SurfaceRules.ifTrue(
                    SurfaceRules.UNDER_FLOOR,
                    SurfaceRules.ifTrue(
                            SurfaceRules.yStartCheck(VerticalAnchor.absolute(31), 0),
                            SurfaceRules.ifTrue(
                                    SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(35), 1)),
                                    SurfaceRules.ifTrue(
                                            SurfaceRules.noiseCondition(Noises.GRAVEL_LAYER, -1.0, 0.4),
                                            SurfaceRules.ifTrue(
                                                    SurfaceRules.not(SurfaceRules.hole()),
                                                    SurfaceRules.sequence(
                                                            SurfaceRules.ifTrue(
                                                                    SurfaceRules.yStartCheck(VerticalAnchor.absolute(32), 0),
                                                                    SILT
                                                            ),
                                                            SurfaceRules.ifTrue(
                                                                    SurfaceRules.not(SurfaceRules.hole()),
                                                                    SILT
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
            );
        }
    }

    private static class BiomeSpecific {
        private static SurfaceRules.RuleSource blackIceGlaciers() {
            return SurfaceRules.ifTrue(
                    SurfaceRules.isBiome(JNEBiomes.BLACK_ICE_GLACIERS),
                    SurfaceRules.sequence(
                            // Ceiling
                            SurfaceRules.ifTrue(SurfaceRules.UNDER_CEILING,
                                    SurfaceRules.sequence(
                                            SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.NETHER_STATE_SELECTOR, 0, Double.MAX_VALUE), SOUL_SOIL),
                                            SOUL_PERMAFROST
                                    )
                            ),
                            // Floor
                            SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR,
                                    SurfaceRules.sequence(
                                            SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.PATCH, -0.012, Double.MAX_VALUE),
                                                    SurfaceRules.ifTrue(SurfaceRules.yStartCheck(VerticalAnchor.absolute(30), 0),
                                                            SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(35), 0)), SOUL_PERMAFROST)
                                                    )
                                            ),
                                            SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.NETHER_STATE_SELECTOR, 0, 0.7976931348623157e+308), SOUL_PERMAFROST),
                                            SOUL_SOIL
                                    )
                            )
                    )
            );
        }
    }
}