package net.jadenxgamer.netherexp.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.function.Consumer;

public class BurstParticle extends TextureSheetParticle {
    private static final Vector3f TRANSFORM_VECTOR = new Vector3f(-1.0F, -1.0F, 0.0F);

    BurstParticle(ClientLevel level, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet ignoredSpriteSet) {
        super(level, x, y, z, velocityX, velocityY, velocityZ);
        this.quadSize = 4.0F;
        this.lifetime = 30;
        this.gravity = 0.0F;
        this.xd = 0.0;
        this.yd = 0.1;
        this.zd = 0.0;
    }

    @Override
    public float getQuadSize(float f) {
        float size = ((float) this.age + f) / (float) this.lifetime;
        return this.quadSize * size;
    }

    @Override
    public void render(VertexConsumer vertex, Camera camera, float f) {
        this.alpha = 1.0F - Mth.clamp(((float) this.age + f) / (float) this.lifetime, 0.0F, 1.0F);
        this.renderRotatedParticle(vertex, camera, f, (quaternionf) -> quaternionf.mul((new Quaternionf()).rotationX((float) Math.PI / 2f)));
    }

    private void renderRotatedParticle(VertexConsumer vertexConsumer, Camera camera, float f, Consumer<Quaternionf> consumer) {
        Vec3 vec3 = camera.getPosition();
        float g = (float) (Mth.lerp(f, this.xo, this.x) - vec3.x());
        float h = (float) (Mth.lerp(f, this.yo, this.y) - vec3.y());
        float i = (float) (Mth.lerp(f, this.zo, this.z) - vec3.z());
        Quaternionf quaternionf = (new Quaternionf()).setAngleAxis(0.0F, -7.0F, 7.0F, 0.0F);
        consumer.accept(quaternionf);
        quaternionf.transform(TRANSFORM_VECTOR);
        Vector3f[] vector3fs = new Vector3f[]{
                new Vector3f(-1.0F, -1.0F, 0.0F),
                new Vector3f(-1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, -1.0F, 0.0F)
        };
        float j = this.getQuadSize(f);

        for (int k = 0; k < 4; ++k) {
            Vector3f vector3f = vector3fs[k];
            vector3f.rotate(quaternionf);
            vector3f.mul(j);
            vector3f.add(g, h, i);
        }

        int packedLight = this.getLightColor(f);
        this.makeCornerVertex(vertexConsumer, vector3fs[0], this.getU1(), this.getV1(), packedLight);
        this.makeCornerVertex(vertexConsumer, vector3fs[1], this.getU1(), this.getV0(), packedLight);
        this.makeCornerVertex(vertexConsumer, vector3fs[2], this.getU0(), this.getV0(), packedLight);
        this.makeCornerVertex(vertexConsumer, vector3fs[3], this.getU0(), this.getV1(), packedLight);
    }

    private void makeCornerVertex(VertexConsumer vertexConsumer, Vector3f vector3f, float u, float v, int packedLight) {
        vertexConsumer.addVertex(vector3f.x(), vector3f.y(), vector3f.z())
                .setUv(u, v)
                .setColor(this.rCol, this.gCol, this.bCol, this.alpha)
                .setLight(packedLight)
                .setNormal(0.0F, 0.0F, 1.0F);
    }

    @Override
    public int getLightColor(float f) {
        return 240;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Factory(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType particleOptions, ClientLevel clientLevel,
                                       double d, double e, double f, double g, double h, double i) {
            BurstParticle particle = new BurstParticle(clientLevel, d, e, f, 0.0, 0.0, 0.0, this.sprite);
            particle.pickSprite(this.sprite);
            particle.setAlpha(1.0F);
            return particle;
        }
    }
}