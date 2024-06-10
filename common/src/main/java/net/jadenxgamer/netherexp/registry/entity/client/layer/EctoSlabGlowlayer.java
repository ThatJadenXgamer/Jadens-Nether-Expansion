package net.jadenxgamer.netherexp.registry.entity.client.layer;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.client.EctoSlabModel;
import net.jadenxgamer.netherexp.registry.entity.client.VesselModel;
import net.jadenxgamer.netherexp.registry.entity.custom.EctoSlab;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class EctoSlabGlowlayer<T extends LivingEntity> extends EyesLayer<T, EctoSlabModel<T>> {
    public EctoSlabGlowlayer(RenderLayerParent<T, EctoSlabModel<T>> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public @NotNull RenderType renderType() {
        return RenderType.eyes(new ResourceLocation(NetherExp.MOD_ID, "textures/entity/ecto_slab/glow.png"));
    }
}
