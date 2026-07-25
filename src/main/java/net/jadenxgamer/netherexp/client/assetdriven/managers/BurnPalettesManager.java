package net.jadenxgamer.netherexp.client.assetdriven.managers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.client.assetdriven.BurnPalettes;
import net.jadenxgamer.netherexp.client.rendering.JNERenderType;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEAttachmentTypes;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.util.ColorHelper;
import net.jadenxgamer.netherexp.util.CommonParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

import java.awt.*;
import java.nio.file.Files;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class BurnPalettesManager extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new Gson();

    private static final BurnPalettes DEFAULT_PALETTE = new BurnPalettes(
            Collections.emptySet(),
            Color.decode("#F9EBAB"), Color.decode("#EFCD56"), Color.decode("#DFA21B"),
            Color.decode("#C96C03"), Color.decode("#B13F00"), Color.decode("#A32102"),
            Collections.emptySet()
    );

    private static List<BurnPalettes> paletteList = List.of();
    private static Map<ResourceLocation, Integer> blockRowMap = Map.of();
    private static Map<ResourceLocation, Integer> particleRowMap = Map.of();
    private static Color[] rowColors = new Color[0];
    private static final AtomicReference<DynamicTexture> paletteTextureReference = new AtomicReference<>();

    public BurnPalettesManager() {
        super(GSON, "netherexp/burn_palettes");
    }

    private static DynamicTexture getOrCreateDefaultTexture() {
        DynamicTexture texture = paletteTextureReference.get();
        if (texture == null) {
            synchronized (BurnPalettesManager.class) {
                texture = paletteTextureReference.get();
                if (texture == null) {
                    NativeImage defaultImage = createPaletteImage(List.of(DEFAULT_PALETTE));
                    texture = new DynamicTexture(defaultImage);
                    texture.setFilter(false, false);
                    paletteTextureReference.set(texture);
                }
            }
        }
        return texture;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> elements, ResourceManager manager, ProfilerFiller profiler) {
        List<BurnPalettes> loadedPalettes = new ArrayList<>();
        for (Map.Entry<ResourceLocation, JsonElement> entry : elements.entrySet()) {
            try {
                loadedPalettes.add(BurnPalettes.parseSetting(entry.getValue().getAsJsonObject()));
            } catch (Exception e) {
                NetherExp.LOGGER.error("Failed to parse burn palette {}: {}", entry.getKey(), e.getMessage());
            }
        }

        List<BurnPalettes> allPalettes = new ArrayList<>();
        allPalettes.add(DEFAULT_PALETTE);
        allPalettes.addAll(loadedPalettes);
        allPalettes = allPalettes.stream()
                .map(palette -> new BurnPalettes(
                        Set.copyOf(palette.blocks()),
                        palette.palette1(), palette.palette2(), palette.palette3(),
                        palette.palette4(), palette.palette5(), palette.palette6(),
                        Set.copyOf(palette.replaceParticles())
                )).collect(Collectors.toList());

        Map<ResourceLocation, Integer> newBlockMap = new HashMap<>();
        Map<ResourceLocation, Integer> newParticleMap = new HashMap<>();
        for (int row = 0; row < allPalettes.size(); row++) {
            BurnPalettes palette = allPalettes.get(row);
            if (!palette.blocks().isEmpty()) for (ResourceLocation block : palette.blocks()) newBlockMap.put(block, row);
            if (!palette.replaceParticles().isEmpty()) for (ResourceLocation particle : palette.replaceParticles()) newParticleMap.put(particle, row);
        }

        Color[] newRowColors = new Color[allPalettes.size()];
        for (int i = 0; i < allPalettes.size(); i++) {
            int r = i & 0xFF;
            int g = (i >> 8) & 0xFF;
            int b = (i >> 16) & 0xFF;
            newRowColors[i] = new Color(r, g, b, 255);
        }

        paletteList = List.copyOf(allPalettes);
        blockRowMap = Map.copyOf(newBlockMap);
        particleRowMap = Map.copyOf(newParticleMap);
        rowColors = newRowColors;

        final var finalPalettes = allPalettes;
        RenderSystem.recordRenderCall(() -> {
            NativeImage paletteImage = createPaletteImage(finalPalettes);
            if (JNEConfigs.DEVELOPER_MODE.get()) {
                try {
                    Files.createDirectories(Minecraft.getInstance().gameDirectory.toPath().resolve("netherexp_debug"));
                    paletteImage.writeToFile(Minecraft.getInstance().gameDirectory.toPath().resolve("netherexp_debug/burn_palette.png"));
                } catch (Exception e) {
                    NetherExp.LOGGER.error("Failed to save burn palette texture: {}", e.getMessage());
                }
            }
            DynamicTexture newTexture = new DynamicTexture(paletteImage);
            newTexture.setFilter(false, false);
            DynamicTexture oldTexture = paletteTextureReference.getAndSet(newTexture);
            if (oldTexture != null) oldTexture.close();
        });
    }

    private static NativeImage createPaletteImage(List<BurnPalettes> palettes) {
        int paletteCount = palettes.size();
        int maxPalettesPerRow = 16384 / 6;
        int palettesPerRow = Math.min(maxPalettesPerRow, paletteCount);

        int height = (paletteCount + palettesPerRow - 1) / palettesPerRow;
        int width = palettesPerRow * 6;

        NativeImage image = new NativeImage(width, height, false);
        int black = convertARGBToABGR(0xFF000000);
        for (int x = 0; x < width; x++) for (int y = 0; y < height; y++) image.setPixelRGBA(x, y, black);
        for (int i = 0; i < paletteCount; i++) {
            BurnPalettes palette = palettes.get(i);
            int row = i / palettesPerRow;
            int col = i % palettesPerRow;
            Color[] colors = { palette.palette1(), palette.palette2(), palette.palette3(), palette.palette4(), palette.palette5(), palette.palette6() };
            for (int index = 0; index < 6; index++) {
                int pixelX = col * 6 + index;
                image.setPixelRGBA(pixelX, row, convertARGBToABGR(colors[index].getRGB()));
            }
        }
        return image;
    }

    private static int convertARGBToABGR(int argb) {
        return ((argb >> 24) & 0xFF) << 24 | ((argb & 0xFF) << 16) | (((argb >> 8) & 0xFF) << 8) | ((argb >> 16) & 0xFF);
    }

    public static DynamicTexture getPaletteTexture() {
        return getOrCreateDefaultTexture();
    }

    public static int getRowForBlock(ResourceLocation block) {
        return blockRowMap.getOrDefault(block, 0);
    }

    public static int getRowForParticle(ResourceLocation particle) {
        return particleRowMap.getOrDefault(particle, -1);
    }

    public static Color getPaletteColor(int row, int index) {
        if (row < 0 || row >= paletteList.size()) row = 0;
        BurnPalettes palette = paletteList.get(row);
        Color[] colors = { palette.palette1(), palette.palette2(), palette.palette3(), palette.palette4(),palette.palette5(), palette.palette6() };
        if (index < 0 || index >= colors.length) index = 0;
        return colors[index];
    }

    public static void handleLastFire(Level level, LivingEntity entity) {
        if (level.isClientSide()) return;
        if (entity.displayFireAnimation()) {
            var state = entity.getInBlockState();
            if (state.is(JNETags.Blocks.LAST_FIRE_SUPPORTED_BLOCKS)) entity.setData(JNEAttachmentTypes.LAST_FIRE, state.getBlock().builtInRegistryHolder().key().location());
        } else entity.setData(JNEAttachmentTypes.LAST_FIRE, NetherExp.minecraftPath("fire"));
    }

    public static void burnParticle(Level level, RandomSource random, Entity entity) {
        AABB box = entity.getBoundingBox();
        double volume = box.getXsize() * box.getYsize() * box.getZsize();
        int frequency = Math.max(2, (int)(volume * 0.6 + 0.5f));

        ResourceLocation fireBlock = entity.getData(JNEAttachmentTypes.LAST_FIRE);
        int row = getRowForBlock(fireBlock);
        ColorParticleData colorData = ColorParticleData.create(rowColors[row]).build();

        BurnPalettes palette = paletteList.get(row);
        Color smokeStart = palette.palette3();
        Color smokeEnd = ColorHelper.adjustHSB(palette.palette6()).saturation(0.3f).brightness(0.143f).build();

        for (int i = 0; i < frequency; i++) {
            var x = entity.getRandomX(Mth.nextFloat(random, 0.2f, 1.2f));
            var y = entity.getRandomY() - 0.5;
            var z = entity.getRandomZ(Mth.nextFloat(random, 0.2f, 1.2f));
            var burnVariant = random.nextBoolean() ? JNEParticleTypes.BURN_DROPLET.get() : JNEParticleTypes.BURN_SIDE.get();
            WorldParticleBuilder.create(burnVariant)
                    .setFullBrightLighting()
                    .setScaleData(GenericParticleData.create(Mth.nextFloat(random, 0.18f, 0.42f)).build())
                    .setTransparencyData(GenericParticleData.create(1).build())
                    .setRenderType(JNERenderType.TRANSPARENT_BURN_PALETTE)
                    .setColorData(colorData)
                    .setLifetime(random.nextInt(5, 20))
                    .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                    .disableNoClip()
                    .setGravity(0f)
                    .setMotion(0.0, 0.01, 0.0)
                    .spawn(level, x, y, z);

            if (random.nextInt(4) != 0) continue;
            LodestoneWorldParticleType smokeVariant = CommonParticles.SMOKE_VARIANTS[random.nextInt(CommonParticles.SMOKE_VARIANTS.length)];
            WorldParticleBuilder.create(smokeVariant)
                    .setNaturalLighting()
                    .setScaleData(GenericParticleData.create(Mth.randomBetween(random, 0.03f, 0.49f)).build())
                    .setTransparencyData(GenericParticleData.create(1.0f, 0.0f).build())
                    .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT.withDepthFade())
                    .setColorData(ColorParticleData.create(smokeStart, smokeEnd).setEasing(Easing.SINE_OUT).build())
                    .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                    .setLifetime(Mth.randomBetweenInclusive(random, 12, 27))
                    .enableNoClip()
                    .addMotion(0.0, 0.1, 0.0)
                    .spawn(level, entity.getRandomX(0.5), y, entity.getRandomZ(0.5));
        }
    }

    public static void flameToBurnParticle(Level level, RandomSource random, double x, double y, double z,
                                           double xSpeed, double ySpeed, double zSpeed, boolean small, int row) {
        ColorParticleData colorData = ColorParticleData.create(rowColors[row]).build();
        float minScale = small ? 0.12f : 0.18f;
        float maxScale = small ? 0.22f : 0.32f;

        var burnVariant = random.nextBoolean() ? JNEParticleTypes.BURN_DROPLET.get() : JNEParticleTypes.BURN_SIDE.get();
        WorldParticleBuilder.create(burnVariant)
                .setFullBrightLighting()
                .setScaleData(GenericParticleData.create(Mth.nextFloat(random, minScale, maxScale)).build())
                .setTransparencyData(GenericParticleData.create(1).build())
                .setRenderType(JNERenderType.TRANSPARENT_BURN_PALETTE)
                .setColorData(colorData)
                .setLifetime(random.nextInt(5, 20))
                .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                .disableNoClip()
                .setGravity(0f)
                .setMotion(xSpeed, ySpeed, zSpeed)
                .spawn(level, x, y + 0.05, z);
    }
}