package net.jadenxgamer.netherexp.registry.particle.custom;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.VertexConsumer;

public class EctoraysParticle
extends TextureSheetParticle {
    
    private static final Quaternionf QUATERNION = new Quaternionf(0F, -0.7F, 0.7F, 0F);

    EctoraysParticle(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z);
        this.setSize(0.25f, 1.0f);
        this.quadSize = 8.0f;
        this.lifetime = 130;
        this.hasPhysics = false;
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float ticks) {

        // Get camera position for rendering the particle
        Vec3 vec3 = camera.getPosition();
        float x = (float) (Mth.lerp(ticks, this.xo, this.x) - vec3.x());
        float y = (float) (Mth.lerp(ticks, this.yo, this.y) - vec3.y());
        float z = (float) (Mth.lerp(ticks, this.zo, this.z) - vec3.z());
        Vector3f offsetPos = new Vector3f(x,0,z);

        // Get the y rotation to face the camera
        @SuppressWarnings("unused")
        float faceRot = offsetPos.angleSigned(camera.getPosition().toVector3f(), new Vector3f(0, 1, 0));
        
        // Get the opposite y rotation of the camera
        @SuppressWarnings("unused")
        float cameraRot = (float) -Math.toRadians(camera.getYRot())-45;

        // Create the face of the particle
        Vector3f[] face = new Vector3f[]{new Vector3f(1.0F, 1.0F, 0.5F), new Vector3f(1.0F, 1.0F, -0.5F), new Vector3f(-1.0F, -1.0F, -0.5F), new Vector3f(-1.0F, -1.0F, 0.5F)};

        float f4 = this.getQuadSize(ticks);

        // Transform the particle
        for (int i = 0; i < 4; ++i) {
            Vector3f face1 = face[i];
            face1.rotate(QUATERNION);
            // faceRot emulates bedrock particles--they will face the player's camera
            // cameraRot is default particles--they rotate with the camera. Looks worse from above
            face1.rotateY(faceRot);
            face1.mul(f4);
            face1.add(x, y, z);
        }

        // particle variables
        float f7 = this.getU0();
        float f8 = this.getU1();
        float f5 = this.getV0();
        float f6 = this.getV1();
        int light = 15728880;

        // Apply the face to the renderer buffer
        buffer.vertex(face[0].x(), face[0].y(), face[0].z()).uv(f8, f6).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        buffer.vertex(face[1].x(), face[1].y(), face[1].z()).uv(f8, f5).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        buffer.vertex(face[2].x(), face[2].y(), face[2].z()).uv(f7, f5).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        buffer.vertex(face[3].x(), face[3].y(), face[3].z()).uv(f7, f6).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
    }

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime || this.alpha <= 0.0f) {
            this.remove();
            return;
        }
        if (this.age <= this.lifetime / 2 && this.alpha < 1.0f) {
            this.alpha += 0.015f;
        }
        if (this.age >= this.lifetime / 2 && this.alpha > 0.01f) {
            this.alpha -= 0.015f;
        }
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }


    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType particleOptions, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            EctoraysParticle ectoraysParticle = new EctoraysParticle(clientLevel, d, e, f);
            ectoraysParticle.pickSprite(this.spriteSet);
            ectoraysParticle.setAlpha(0.01f);
            return ectoraysParticle;
        }
    }
}