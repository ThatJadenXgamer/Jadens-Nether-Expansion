package net.jadenxgamer.netherexp.client.particle;

import team.lodestar.lodestone.systems.particle.world.behaviors.LodestoneParticleBehavior;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.LodestoneBehaviorComponent;

public class JNERotationBehaviorComponent implements LodestoneBehaviorComponent {



    @Override
    public LodestoneParticleBehavior getBehaviorType() {
        return LodestoneParticleBehavior.DIRECTIONAL;
    }
}
