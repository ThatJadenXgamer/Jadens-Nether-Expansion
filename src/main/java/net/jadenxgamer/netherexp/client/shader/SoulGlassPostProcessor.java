package net.jadenxgamer.netherexp.client.shader;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.config.JNEConfigImpl;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import team.lodestar.lodestone.systems.postprocess.PostProcessor;

public class SoulGlassPostProcessor extends PostProcessor {

    public static final SoulGlassPostProcessor INSTANCE = new SoulGlassPostProcessor();

    @Override
    public ResourceLocation getPostChainLocation() {
        return NetherExp.netherexpPath("soul_glass");
    }

    @Override
    public void beforeProcess(Matrix4f viewModelMatrix) {
        if (this.postChain == null) return;
        Minecraft client = Minecraft.getInstance();
        if (client.level == null) return;
        float gameTimeTicks = client.level.getGameTime() + client.getTimer().getGameTimeDeltaPartialTick(true);
        float timeInSeconds = gameTimeTicks / 20.0f;
        this.postChain.setUniform("GameTime", timeInSeconds);
        if (JNEConfigImpl.CONFIG.isLoaded() && JNEConfigs.NO_SOUL_GLASS_RIPPLE.get()) {
            this.postChain.setUniform("RippleStrength", 0.0f);
            this.postChain.setUniform("RippleFrequency", 0.0f);
            this.postChain.setUniform("RippleSpeed", 0.0f);
            this.postChain.setUniform("RippleRandomness", 0.0f);
        }
    }

    @Override
    public void afterProcess() {

    }
}