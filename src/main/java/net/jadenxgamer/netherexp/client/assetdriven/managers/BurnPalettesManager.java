package net.jadenxgamer.netherexp.client.assetdriven.managers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.client.assetdriven.BurnPalettes;
import net.jadenxgamer.netherexp.client.rendering.JNERenderType;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.JNEAttachmentTypes;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.util.ColorHelper;
import net.jadenxgamer.netherexp.util.CommonParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.tags.BlockTags;
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

public class BurnPalettesManager extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new Gson();

    private static final Color[] DEFAULT_PALETTE_COLORS = {
            Color.decode("#F9EBAB"), Color.decode("#EFCD56"), Color.decode("#DFA21B"),
            Color.decode("#C96C03"), Color.decode("#B13F00"), Color.decode("#A32102")
    };

    private static final Map<ResourceLocation, Integer> BLOCK_PALETTE_ROW_MAP = new HashMap<>();
    private static int totalPaletteRows = 1;
    private static final AtomicReference<DynamicTexture> paletteTextureRef = new AtomicReference<>();
    private static List<BurnPalettes> paletteList = new ArrayList<>();

    public BurnPalettesManager() {
        super(GSON, "netherexp/burn_palettes");
    }

    private static DynamicTexture getOrCreateDefaultTexture() {
        DynamicTexture texture = paletteTextureRef.get();
        if (texture == null) {
            synchronized (BurnPalettesManager.class) {
                texture = paletteTextureRef.get();
                if (texture == null) {
                    NativeImage defaultImage = createPaletteImage(
                            List.of(new BurnPalettes(
                                    Collections.emptySet(),
                                    DEFAULT_PALETTE_COLORS[0], DEFAULT_PALETTE_COLORS[1], DEFAULT_PALETTE_COLORS[2],
                                    DEFAULT_PALETTE_COLORS[3], DEFAULT_PALETTE_COLORS[4], DEFAULT_PALETTE_COLORS[5]
                            ))
                    );
                    texture = new DynamicTexture(defaultImage);
                    texture.setFilter(false, false);
                    paletteTextureRef.set(texture);
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
            } catch (Exception e) { NetherExp.LOGGER.error("Failed to parse burn palette {}: {}", entry.getKey(), e.getMessage()); }
        }

        List<BurnPalettes> allPalettes = new ArrayList<>();
        allPalettes.add(new BurnPalettes(
                Collections.emptySet(),
                DEFAULT_PALETTE_COLORS[0], DEFAULT_PALETTE_COLORS[1], DEFAULT_PALETTE_COLORS[2],
                DEFAULT_PALETTE_COLORS[3], DEFAULT_PALETTE_COLORS[4], DEFAULT_PALETTE_COLORS[5]
        ));
        allPalettes.addAll(loadedPalettes);
        paletteList = allPalettes;

        BLOCK_PALETTE_ROW_MAP.clear();
        for (int row = 0; row < allPalettes.size(); row++) {
            BurnPalettes palette = allPalettes.get(row);
            if (!palette.blocks().isEmpty()) for (ResourceLocation block : palette.blocks()) BLOCK_PALETTE_ROW_MAP.put(block, row);
        }
        totalPaletteRows = allPalettes.size();

        RenderSystem.recordRenderCall(() -> {
            NativeImage paletteImage = createPaletteImage(allPalettes);
            if (JNEConfigs.DEVELOPER_MODE.get()) {
                try {
                    Files.createDirectories(Minecraft.getInstance().gameDirectory.toPath().resolve("netherexp_debug"));
                    paletteImage.writeToFile(Minecraft.getInstance().gameDirectory.toPath().resolve("netherexp_debug/burn_palette.png"));
                } catch (Exception e) { NetherExp.LOGGER.error("Failed to save burn palette texture: {}", e.getMessage()); }
            }
            DynamicTexture newTexture = new DynamicTexture(paletteImage);
            newTexture.setFilter(false, false);
            DynamicTexture old = paletteTextureRef.getAndSet(newTexture);
            if (old != null) {
                old.close();
            }
        });
    }

    private static NativeImage createPaletteImage(List<BurnPalettes> palettes) {
        NativeImage image = new NativeImage(6, palettes.size(), false);
        for (int row = 0; row < palettes.size(); row++) {
            BurnPalettes palette = palettes.get(row);
            image.setPixelRGBA(0, row, argbToAbgr(palette.palette1().getRGB()));
            image.setPixelRGBA(1, row, argbToAbgr(palette.palette2().getRGB()));
            image.setPixelRGBA(2, row, argbToAbgr(palette.palette3().getRGB()));
            image.setPixelRGBA(3, row, argbToAbgr(palette.palette4().getRGB()));
            image.setPixelRGBA(4, row, argbToAbgr(palette.palette5().getRGB()));
            image.setPixelRGBA(5, row, argbToAbgr(palette.palette6().getRGB()));
        }
        return image;
    }

    private static int argbToAbgr(int argb) {
        return ((argb >> 24) & 0xFF) << 24 | ((argb & 0xFF) << 16) | (((argb >> 8) & 0xFF) << 8) | ((argb >> 16) & 0xFF);
    }

    public static DynamicTexture getPaletteTexture() {
        return getOrCreateDefaultTexture();
    }

    public static int getPaletteRows() {
        return totalPaletteRows;
    }

    public static int getRowForBlock(ResourceLocation block) {
        return BLOCK_PALETTE_ROW_MAP.getOrDefault(block, 0);
    }

    public static Color getPaletteColor(int row, int index) {
        if (row < 0 || row >= paletteList.size()) row = 0;
        BurnPalettes palette = paletteList.get(row);
        return switch (index) {
            case 0 -> palette.palette1();
            case 1 -> palette.palette2();
            case 2 -> palette.palette3();
            case 3 -> palette.palette4();
            case 4 -> palette.palette5();
            case 5 -> palette.palette6();
            default -> throw new IllegalArgumentException("Invalid palette index: " + index);
        };
    }

    public static void handleLastFire(Level level, LivingEntity entity) {
        if (level.isClientSide()) return;
        if (entity.displayFireAnimation()) {
            var state = entity.getInBlockState();
            if (state.is(BlockTags.FIRE)) {
                var block = state.getBlock();
                entity.setData(JNEAttachmentTypes.LAST_FIRE, block.builtInRegistryHolder().key().location());
            }
        } else entity.setData(JNEAttachmentTypes.LAST_FIRE, NetherExp.minecraftPath("fire"));
    }

    public static void burnParticle(Level level, RandomSource random, Entity entity) {
        AABB box = entity.getBoundingBox();
        double volume = box.getXsize() * box.getYsize() * box.getZsize();
        int frequency = Math.max(2, (int)(volume * 0.6 + 0.5f));

        ResourceLocation fireBlock = entity.getData(JNEAttachmentTypes.LAST_FIRE);
        int row = getRowForBlock(fireBlock);
        ColorParticleData colorData = ColorParticleData.create(new Color((int)((row + 0.5f) / getPaletteRows() * 255), 0, 0)).build();

        Color smokeStart = getPaletteColor(row, 2);
        Color smokeEnd = ColorHelper.adjustHSB(getPaletteColor(row, 5)).saturation(0.3f).brightness(0.143f).build();

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
}