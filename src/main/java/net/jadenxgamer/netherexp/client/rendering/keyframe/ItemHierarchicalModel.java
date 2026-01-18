package net.jadenxgamer.netherexp.client.rendering.keyframe;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.entity.animation.json.AnimationHolder;
import net.neoforged.neoforge.client.entity.animation.json.AnimationLoader;
import org.joml.Vector3f;

import java.util.Optional;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public abstract class ItemHierarchicalModel<I extends Item> extends Model {
    private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

    public ItemHierarchicalModel() {
        this(RenderType::entityCutoutNoCull);
    }

    public ItemHierarchicalModel(Function<ResourceLocation, RenderType> renderType) {
        super(renderType);
    }

    protected static AnimationHolder getAnimation(ResourceLocation key) {
        return AnimationLoader.INSTANCE.getAnimationHolder(key);
    }

    public abstract void setupAnim(Entity entity, I item, ItemStack stack, ItemDisplayContext displayContext, float ageInTicks);

    public abstract ModelPart root();

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        this.root().render(poseStack, buffer, packedLight, packedOverlay, color);
    }

    public Optional<ModelPart> getAnyDescendantWithName(String name) {
        return name.equals("root") ? Optional.of(this.root()) : this.root().getAllParts().filter((arg) -> arg.hasChild(name)).findFirst().map((arg) -> arg.getChild(name));
    }

    protected void animate(Entity entity, ItemAnimationState animationState, AnimationDefinition animationDefinition, float ageInTicks) {
        this.animate(entity, animationState, animationDefinition, ageInTicks, 1.0F);
    }

    protected void animate(Entity entity, ItemAnimationState animationState, AnimationHolder animation, float ageInTicks) {
        this.animate(entity, animationState, animation.get(), ageInTicks);
    }

    protected void animate(Entity entity, ItemAnimationState animationState, AnimationDefinition animationDefinition, float ageInTicks, float speed) {
        animationState.updateTime(ageInTicks, speed);
        animationState.ifStarted((arg2) -> ItemKeyframeAnimations.animate(this, animationDefinition, arg2.getAccumulatedTime(), 1.0F, ANIMATION_VECTOR_CACHE));
    }

    protected void animate(Entity entity, ItemAnimationState animationState, AnimationHolder animation, float ageInTicks, float speed) {
        this.animate(entity, animationState, animation.get(), ageInTicks, speed);
    }

    protected void applyStatic(AnimationDefinition animationDefinition) {
        ItemKeyframeAnimations.animate(this, animationDefinition, 0L, 1.0F, ANIMATION_VECTOR_CACHE);
    }

    protected void applyStatic(AnimationHolder animation) {
        this.applyStatic(animation.get());
    }
}