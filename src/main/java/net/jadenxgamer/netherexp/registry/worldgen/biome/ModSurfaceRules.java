package net.jadenxgamer.netherexp.registry.worldgen.biome;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.noise.NoiseParametersKeys;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public class ModSurfaceRules
{
    private static final MaterialRules.MaterialRule NETHERRACK = makeStateRule(Blocks.NETHERRACK);
    private static final MaterialRules.MaterialRule SOUL_SAND = makeStateRule(Blocks.SOUL_SAND);
    private static final MaterialRules.MaterialRule SOUL_SOIL = makeStateRule(Blocks.SOUL_SOIL);
    private static final MaterialRules.MaterialRule GRAVEL = makeStateRule(Blocks.GRAVEL);

    /*
    protected static MaterialRules.MaterialRule makeRules() {
        MaterialRules.MaterialCondition ceiling = MaterialRules.stoneDepth(0, true, 0, VerticalSurfaceType.CEILING);
        MaterialRules.MaterialCondition floor = MaterialRules.stoneDepth(0, true, 0, VerticalSurfaceType.FLOOR);
        MaterialRules.MaterialCondition netherStateSelector = MaterialRules.noiseThreshold(NoiseParametersKeys.NETHER_STATE_SELECTOR, 0, 1.7976931348623157e+308);
        MaterialRules.MaterialCondition patchNoise = MaterialRules.noiseThreshold(NoiseParametersKeys.PATCH, -0.012, 1.7976931348623157e+308);
        MaterialRules.MaterialCondition aboveY30 = MaterialRules.aboveY(YOffset.fixed(30), 0);
        MaterialRules.MaterialCondition belowY35 = MaterialRules.not(MaterialRules.aboveYWithStoneDepth(YOffset.fixed(35), 0));
        MaterialRules.MaterialCondition belowTopOffset5 = MaterialRules.aboveY(YOffset.belowTop(5), 0);
        MaterialRules.MaterialRule netherGravelBeach = MaterialRules.condition(patchNoise, MaterialRules.condition(aboveY30, MaterialRules.condition(belowY35, GRAVEL)));


        return MaterialRules.sequence(
                MaterialRules.condition(belowTopOffset5, NETHERRACK),
                MaterialRules.condition(MaterialRules.biome(ModBiomes.SOUL_SAND_GLACIER),
                        MaterialRules.sequence(
                                MaterialRules.condition(
                                        MaterialRules.STONE_DEPTH_CEILING,
                                        MaterialRules.sequence(
                                                MaterialRules.condition(netherStateSelector, SOUL_SAND),
                                                SOUL_SOIL
                                        )
                                ),
                                MaterialRules.condition(
                                        MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH,
                                        MaterialRules.sequence(
                                                netherGravelBeach,
                                                MaterialRules.condition(netherStateSelector, SOUL_SAND),
                                                SOUL_SOIL
                                        )
                                )
                        )
                ),
                NETHERRACK
        );
    }
    */

    private static MaterialRules.MaterialRule makeStateRule(Block block){
        return MaterialRules.block(block.getDefaultState());
    }
}