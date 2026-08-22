package net.jadenxgamer.netherexp.registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class JNEDecoratedPotPatterns {

    public static final DeferredRegister<DecoratedPotPattern> POT_PATTERNS = DeferredRegister.create(Registries.DECORATED_POT_PATTERN, NetherExp.MOD_ID);

    public static final Holder<DecoratedPotPattern> SEALED = register("sealed");
    public static final Holder<DecoratedPotPattern> SPECTRE = register("spectre");
    public static final Holder<DecoratedPotPattern> MARIONETTE = register("marionette");
    public static final Holder<DecoratedPotPattern> ELDRITCH = register("eldritch");
    public static final Holder<DecoratedPotPattern> DECEPTION = register("deception");
    public static final Holder<DecoratedPotPattern> FIREARM = register("firearm");
    public static final Holder<DecoratedPotPattern> BOTANICAL = register("botanical");

    private static Holder<DecoratedPotPattern> register(String name) {
        return POT_PATTERNS.register(name + "_pottery_pattern", () -> new DecoratedPotPattern(NetherExp.netherexpPath(name + "_pottery_pattern")));
    }

    public static void init(IEventBus eventBus) {
        POT_PATTERNS.register(eventBus);
    }
}