package net.jadenxgamer.netherexp.client.rendering.block_entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.jadenxgamer.netherexp.core.block.JNECampfireBlock;
import net.jadenxgamer.netherexp.core.block.entity.JNECampfireBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class JNECampfireRenderer implements BlockEntityRenderer<JNECampfireBlockEntity> {
    private final ItemRenderer itemRenderer;

    public JNECampfireRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    public void render(JNECampfireBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Direction direction = blockEntity.getBlockState().getValue(JNECampfireBlock.FACING);
        NonNullList<ItemStack> itemsInCampfire = blockEntity.getItems();
        int seed = (int)blockEntity.getBlockPos().asLong();

        for(int j = 0; j < itemsInCampfire.size(); ++j) {
            ItemStack itemStack = itemsInCampfire.get(j);
            if (itemStack != ItemStack.EMPTY) {
                poseStack.pushPose();
                poseStack.translate(0.5F, 0.44921875F, 0.5F);
                Direction direction2 = Direction.from2DDataValue((j + direction.get2DDataValue()) % 4);
                float f = -direction2.toYRot();
                poseStack.mulPose(Axis.YP.rotationDegrees(f));
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                poseStack.translate(-0.3125F, -0.3125F, 0.0F);
                poseStack.scale(0.375F, 0.375F, 0.375F);
                this.itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), seed + j);
                poseStack.popPose();
            }
        }
    }
}
