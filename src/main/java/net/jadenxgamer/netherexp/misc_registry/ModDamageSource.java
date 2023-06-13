package net.jadenxgamer.netherexp.misc_registry;

import net.minecraft.entity.damage.DamageSource;

public class ModDamageSource extends DamageSource {
    /**
     * Access widened by fabric-transitive-access-wideners-v1 to accessible
     *
     * @param name
     */
    public ModDamageSource(String name) {
        super(name);
    }
    public static final DamageSource IGNEOUS_VINES = new DamageSource("igneousVines");
    public static final DamageSource EXPLOSIVE_SCORIA = new DamageSource("explosiveScoria");
}
