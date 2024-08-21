package net.jadenxgamer.netherexp.registry.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

public class JNEDripParticle extends TextureSheetParticle {
    private final Fluid type;

    JNEDripParticle(ClientLevel clientLevel, double d, double e, double f, Fluid fluid) {
        super(clientLevel, d, e, f);
        this.setSize(0.01F, 0.01F);
        this.gravity = 0.06F;
        this.type = fluid;
    }

    protected Fluid getType() {
        return this.type;
    }

    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.preMoveUpdate();
        if (!this.removed) {
            this.yd -= this.gravity;
            this.move(this.xd, this.yd, this.zd);
            this.postMoveUpdate();
            if (!this.removed) {
                this.xd *= 0.9800000190734863;
                this.yd *= 0.9800000190734863;
                this.zd *= 0.9800000190734863;
                if (this.type != Fluids.EMPTY) {
                    BlockPos blockPos = BlockPos.containing(this.x, this.y, this.z);
                    FluidState fluidState = this.level.getFluidState(blockPos);
                    if (fluidState.getType() == this.type && this.y < (double)((float)blockPos.getY() + fluidState.getHeight(this.level, blockPos))) {
                        this.remove();
                    }

                }
            }
        }
    }

    protected void preMoveUpdate() {
        if (this.lifetime-- <= 0) {
            this.remove();
        }
    }

    protected void postMoveUpdate() {
    }
}
