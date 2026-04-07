package net.jadenxgamer.netherexp.client.shader;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import team.lodestar.lodestone.systems.postprocess.PostProcessor;

public class SoulGlassPostProcessor extends PostProcessor {

    public static final SoulGlassPostProcessor INSTANCE = new SoulGlassPostProcessor();

    @Override
    public ResourceLocation getPostChainLocation() {
        return NetherExp.id("soul_glass");
    }

    @Override
    public void beforeProcess(Matrix4f viewModelMatrix) {
        if (this.postChain == null) return;
        Minecraft client = Minecraft.getInstance();
        if (client.level == null) return;
        float gameTimeTicks = client.level.getGameTime() + client.getTimer().getGameTimeDeltaPartialTick(true);
        float timeInSeconds = gameTimeTicks / 20.0f;
        this.postChain.setUniform("GameTime", timeInSeconds);
    }

    @Override
    public void afterProcess() {

    }
}