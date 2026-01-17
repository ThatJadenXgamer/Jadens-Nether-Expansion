package net.jadenxgamer.netherexp.client.rendering.keyframe;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ItemKeyframeAnimations {

    public static void animate(ItemHierarchicalModel<?> model, AnimationDefinition animationDefinition, long accumulatedTime, float scale, Vector3f animationVecCache) {
        float f = getElapsedSeconds(animationDefinition, accumulatedTime);

        for(Map.Entry<String, List<AnimationChannel>> entry : animationDefinition.boneAnimations().entrySet()) {
            Optional<ModelPart> optional = model.getAnyDescendantWithName(entry.getKey());
            List<AnimationChannel> list = entry.getValue();
            optional.ifPresent((arg) -> list.forEach((arg2) -> {
                Keyframe[] keyframes = arg2.keyframes();
                int i = Math.max(0, Mth.binarySearch(0, keyframes.length, (ix) -> f <= keyframes[ix].timestamp()) - 1);
                int j = Math.min(keyframes.length - 1, i + 1);
                Keyframe keyframe = keyframes[i];
                Keyframe keyframe2 = keyframes[j];
                float h = f - keyframe.timestamp();
                float k;
                if (j != i) {
                    k = Mth.clamp(h / (keyframe2.timestamp() - keyframe.timestamp()), 0.0F, 1.0F);
                } else {
                    k = 0.0F;
                }

                keyframe2.interpolation().apply(animationVecCache, k, keyframes, i, j, scale);
                arg2.target().apply(arg, animationVecCache);
            }));
        }

    }

    private static float getElapsedSeconds(AnimationDefinition animationDefinition, long accumulatedTime) {
        float f = (float)accumulatedTime / 1000.0F;
        return animationDefinition.looping() ? f % animationDefinition.lengthInSeconds() : f;
    }

    public static Vector3f posVec(float x, float y, float z) {
        return new Vector3f(x, -y, z);
    }

    public static Vector3f degreeVec(float xDegrees, float yDegrees, float zDegrees) {
        return new Vector3f(xDegrees * ((float)Math.PI / 180F), yDegrees * ((float)Math.PI / 180F), zDegrees * ((float)Math.PI / 180F));
    }

    public static Vector3f scaleVec(double xScale, double yScale, double zScale) {
        return new Vector3f((float)(xScale - (double)1.0F), (float)(yScale - (double)1.0F), (float)(zScale - (double)1.0F));
    }
}
