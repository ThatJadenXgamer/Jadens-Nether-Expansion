package net.jadenxgamer.netherexp.registry.particle.custom;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ShriekParticleOption;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.function.Consumer;

public class BurstParticle extends TextureSheetParticle {
    private static final Vector3f TRANSFORM_VECTOR = new Vector3f(-1.0F, -1.0F, 0.0F);

    BurstParticle(ClientLevel level, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteSet) {
        super(level, x, y, z, velocityX, velocityY, velocityZ);
        this.quadSize = 4.0F;
        this.lifetime = 30;
        this.gravity = 0.0F;
        this.xd = 0.0;
        this.yd = 0.1;
        this.zd = 0.0;
    }

    public float getQuadSize(float f) {
        float size = ((float)this.age + f) / (float)this.lifetime;
        return this.quadSize * size;
    }

    public void render(VertexConsumer vertex, Camera camera, float f) {
        this.alpha = 1.0F - Mth.clamp(((float)this.age + f) / (float)this.lifetime, 0.0F, 1.0F);
        this.renderRotatedParticle(vertex, camera, f, (quaternionf) -> quaternionf.mul((new Quaternionf()).rotationX((float)Math.PI / 2f)));
    }

    private void renderRotatedParticle(VertexConsumer vertexConsumer, Camera camera, float f, Consumer<Quaternionf> consumer) {
        Vec3 vec3 = camera.getPosition();
        float g = (float)(Mth.lerp(f, this.xo, this.x) - vec3.x());
        float h = (float)(Mth.lerp(f, this.yo, this.y) - vec3.y());
        float i = (float)(Mth.lerp(f, this.zo, this.z) - vec3.z());
        Quaternionf quaternionf = (new Quaternionf()).setAngleAxis(0.0F, -7.0, 7.0, 0.0);
        consumer.accept(quaternionf);
        quaternionf.transform(TRANSFORM_VECTOR);
        Vector3f[] vector3fs = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float j = this.getQuadSize(f);

        int k;
        for(k = 0; k < 4; ++k) {
            Vector3f vector3f = vector3fs[k];
            vector3f.rotate(quaternionf);
            vector3f.mul(j);
            vector3f.add(g, h, i);
        }

        k = this.getLightColor(f);
        this.makeCornerVertex(vertexConsumer, vector3fs[0], this.getU1(), this.getV1(), k);
        this.makeCornerVertex(vertexConsumer, vector3fs[1], this.getU1(), this.getV0(), k);
        this.makeCornerVertex(vertexConsumer, vector3fs[2], this.getU0(), this.getV0(), k);
        this.makeCornerVertex(vertexConsumer, vector3fs[3], this.getU0(), this.getV1(), k);
    }

    private void makeCornerVertex(VertexConsumer vertexConsumer, Vector3f vector3f, float f, float g, int i) {
        vertexConsumer.vertex(vector3f.x(), vector3f.y(), vector3f.z()).uv(f, g).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(i).endVertex();
    }

    public int getLightColor(float f) {
        return 240;
    }

    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void tick() {
        super.tick();
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Factory(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleOptions, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i)  {
            BurstParticle particle = new BurstParticle(clientLevel, d, e, f, 0.0, 0.0, 0.0, this.sprite);
            particle.pickSprite(this.sprite);
            particle.setAlpha(1.0F);
            return particle;
        }
    }
}
