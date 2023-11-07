package net.jadenxgamer.netherexp.registry.worldgen.feature;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.worldgen.feature.custom.HugeFungusNetherExpFeature;
import net.jadenxgamer.netherexp.registry.worldgen.feature.custom.SmokestalkFeature;
import net.jadenxgamer.netherexp.registry.worldgen.feature.custom.WeepingVinesNetherExpFeature;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;

public abstract class ModFeature<FC extends FeatureConfig> {

    public static final Feature<DefaultFeatureConfig> WEEPING_VINES_NETHEREXP = registerFeature("weeping_vines_netherexp",
            new WeepingVinesNetherExpFeature(DefaultFeatureConfig.CODEC));

    public static final Feature<HugeFungusFeatureConfig> HUGE_FUNGUS_NETHEREXP = registerFeature("huge_fungus_netherexp",
            new HugeFungusNetherExpFeature(HugeFungusFeatureConfig.CODEC));

    public static final Feature<TwistingVinesFeatureConfig> SMOKESTALK = registerFeature("smokestalk",
            new SmokestalkFeature(TwistingVinesFeatureConfig.CODEC));

    private static <C extends FeatureConfig, F extends Feature<C>> F registerFeature(String name, F feature) {
        return Registry.register(Registries.FEATURE, new Identifier(NetherExp.MOD_ID, name), feature);
    }

    public static void registerModFeature() {
        NetherExp.LOGGER.debug("Registering Features for " + NetherExp.MOD_ID);
    }
}
