package net.jadenxgamer.netherexp.client.shader;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.keys.JNEBiomes;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.AABB;
import org.joml.Matrix4f;
import team.lodestar.lodestone.systems.postprocess.PostProcessor;

public class NetherHeatDistortionPostprocessor extends PostProcessor {

    public static final NetherHeatDistortionPostprocessor INSTANCE = new NetherHeatDistortionPostprocessor();

    private static float currentIntensity = 0.0f;
    private static float currentSpeed = 0.0f;
    private static float currentMinDistance = 0.0f;
    private static float currentMaxDistance = 0.0f;

    private static long lastLavaCheckGameTime = -1;
    private static boolean cachedNearLava = false;

    @Override
    public ResourceLocation getPostChainLocation() {
        return NetherExp.netherexpPath("nether_heat_distortion");
    }

    @Override
    public void beforeProcess(Matrix4f viewModelMatrix) {
        if (this.postChain == null) return;
        Minecraft client = Minecraft.getInstance();
        if (client.level == null) return;
        float gameTimeTicks = client.level.getGameTime() + client.getTimer().getGameTimeDeltaPartialTick(true);
        float timeInSeconds = gameTimeTicks / 20.0f;
        this.postChain.setUniform("GameTime", timeInSeconds);
        this.postChain.setUniform("Intensity", currentIntensity);
        this.postChain.setUniform("Speed", currentSpeed);
        this.postChain.setUniform("MinDistance", currentMinDistance);
        this.postChain.setUniform("MaxDistance", currentMaxDistance);
    }

    @Override
    public void afterProcess() {

    }

    public static boolean shouldEnable() {
        return JNEConfigs.ENABLE_HEAT_DISTORTION.get() && currentIntensity > 0.0;
    }

    public static void tick(Minecraft client, Level level, Entity player, Holder<Biome> biome) {
        float targetIntensity = 0.0f;
        float targetSpeed = JNEConfigs.HEAT_DISTORTION_SPEED.get().floatValue();
        float targetMinDistance = JNEConfigs.HEAT_DISTORTION_MIN_DISTANCE.get().floatValue();
        float targetMaxDistance = JNEConfigs.HEAT_DISTORTION_MAX_DISTANCE.get().floatValue();

        boolean nearLava = cachedNearLava;
        long gameTime = level.getGameTime();
        if (JNEConfigs.LAVA_HEAT_DISTORTION.get() &&
                (lastLavaCheckGameTime == -1 || gameTime - lastLavaCheckGameTime >= JNEConfigs.HEAT_DISTORTION_LAVA_CHECK_PERIOD.get())) {
            nearLava = isPlayerNearLava(level, player);
            cachedNearLava = nearLava;
            lastLavaCheckGameTime = gameTime;
        }

        if (JNEConfigs.BIOME_HEAT_DISTORTION.get() && biome.is(JNETags.Biomes.HAS_HEAT_DISTORTION)) {
            targetIntensity = JNEConfigs.HEAT_DISTORTION_INTENSITY.get().floatValue();
        }

        if (nearLava) {
            targetIntensity = JNEConfigs.HEAT_DISTORTION_LAVA_INTENSITY.get().floatValue();
            targetSpeed = JNEConfigs.HEAT_DISTORTION_LAVA_SPEED.get().floatValue();
            targetMinDistance = JNEConfigs.HEAT_DISTORTION_LAVA_MIN_DISTANCE.get().floatValue();
            targetMaxDistance = JNEConfigs.HEAT_DISTORTION_LAVA_MAX_DISTANCE.get().floatValue();
        }

        currentIntensity = Mth.lerp(0.05F, currentIntensity, targetIntensity);
        currentSpeed = Mth.lerp(0.05F, currentSpeed, targetSpeed);
        currentMinDistance = Mth.lerp(0.05F, currentMinDistance, targetMinDistance);
        currentMaxDistance = Mth.lerp(0.05F, currentMaxDistance, targetMaxDistance);

        final float epsilon = 1e-4f;

        if (targetIntensity == 0.0f && Math.abs(currentIntensity) < epsilon) currentIntensity = 0.0f;
        if (targetSpeed == JNEConfigs.HEAT_DISTORTION_SPEED.get().floatValue() && Math.abs(currentSpeed - targetSpeed) < epsilon) currentSpeed = targetSpeed;
        if (targetMinDistance == JNEConfigs.HEAT_DISTORTION_MIN_DISTANCE.get().floatValue() && Math.abs(currentMinDistance - targetMinDistance) < epsilon) currentMinDistance = targetMinDistance;
        if (targetMaxDistance == JNEConfigs.HEAT_DISTORTION_MAX_DISTANCE.get().floatValue() && Math.abs(currentMaxDistance - targetMaxDistance) < epsilon) currentMaxDistance = targetMaxDistance;
    }

    private static boolean isPlayerNearLava(Level level, Entity player) {
        if (level == null || player == null) return false;
        float lavaProximityRadius = JNEConfigs.HEAT_DISTORTION_LAVA_PROXIMITY.get().floatValue();
        int requiredLavaBlocks = JNEConfigs.REQUIRED_SOURCE_BLOCKS_FOR_LAVA_HEAT_DISTORTION.get();
        int foundLavaBlocks = 0;

        AABB searchBox = player.getBoundingBox().inflate(lavaProximityRadius);
        int minX = Mth.floor(searchBox.minX);
        int minY = Mth.floor(searchBox.minY);
        int minZ = Mth.floor(searchBox.minZ);
        int maxX = Mth.floor(searchBox.maxX);
        int maxY = Mth.floor(searchBox.maxY);
        int maxZ = Mth.floor(searchBox.maxZ);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (level.getBlockState(pos).getFluidState().is(FluidTags.LAVA) && level.getFluidState(pos).isSource()) foundLavaBlocks++;
                    if (foundLavaBlocks >= requiredLavaBlocks) return true;
                }
            }
        }
        return false;
    }
}