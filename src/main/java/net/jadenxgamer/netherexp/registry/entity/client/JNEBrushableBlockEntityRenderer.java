package net.jadenxgamer.netherexp.registry.entity.client;

import net.jadenxgamer.netherexp.registry.block.entity.JNEBrushableBlockEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

public class JNEBrushableBlockEntityRenderer implements BlockEntityRenderer<JNEBrushableBlockEntity> {
    private final ItemRenderer itemRenderer;

    public JNEBrushableBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    // Literally just Vanilla's BrushableBlockEntityRenderer Code

    private float[] getTranslation(Direction direction, int dustedLevel) {
        float[] fs = new float[]{0.5F, 0.0F, 0.5F};
        float f = (float)dustedLevel / 10.0F * 0.75F;
        switch (direction) {
            case EAST:
                fs[0] = 0.73F + f;
                break;
            case WEST:
                fs[0] = 0.25F - f;
                break;
            case UP:
                fs[1] = 0.25F + f;
                break;
            case DOWN:
                fs[1] = -0.23F - f;
                break;
            case NORTH:
                fs[2] = 0.25F - f;
                break;
            case SOUTH:
                fs[2] = 0.73F + f;
        }

        return fs;
    }

    @Override
    public void render(JNEBrushableBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (entity.getWorld() != null) {
            int k = entity.getCachedState().get(Properties.DUSTED);
            if (k > 0) {
                Direction direction = entity.getHitDirection();
                if (direction != null) {
                    ItemStack itemStack = entity.getItem();
                    if (!itemStack.isEmpty()) {
                        matrices.push();
                        matrices.translate(0.0F, 0.5F, 0.0F);
                        float[] fs = this.getTranslation(direction, k);
                        matrices.translate(fs[0], fs[1], fs[2]);
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(75.0F));
                        boolean bl = direction == Direction.EAST || direction == Direction.WEST;
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)((bl ? 90 : 0) + 11)));
                        matrices.scale(0.5F, 0.5F, 0.5F);
                        int l = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getCachedState(), entity.getPos().offset(direction));
                        this.itemRenderer.renderItem(itemStack, ModelTransformationMode.FIXED, l, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 0);
                        matrices.pop();
                    }
                }
            }
        }
    }
}