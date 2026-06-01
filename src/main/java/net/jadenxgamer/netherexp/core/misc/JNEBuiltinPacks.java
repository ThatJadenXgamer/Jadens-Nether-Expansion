package net.jadenxgamer.netherexp.core.misc;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.util.CompatUtil;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.AddPackFindersEvent;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static net.jadenxgamer.netherexp.config.JNEConfigs.IMPROVED_NETHER_BIOME_SOURCE;
import static net.jadenxgamer.netherexp.config.JNEConfigs.NETHER_WORLDGEN_OVERHAUL;

public class JNEBuiltinPacks {

    // Retextures for JNE
    public static void rpJNERetextures(AddPackFindersEvent event) {
        Path path = ModList.get().getModFileById(NetherExp.MOD_ID).getFile().findResource("resourcepacks/jne_retextures");
        PackMetadataSection metadata = new PackMetadataSection(Component.literal("Improves various vanilla textures to fit with JNE"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES));
        event.addRepositorySource(source -> source.accept(new Pack(
                new PackLocationInfo("netherexp:jne_retextures", Component.literal("JNE Retextures"), PackSource.BUILT_IN, Optional.empty()),
                new PathPackResources.PathResourcesSupplier(path),
                new Pack.Metadata(metadata.description(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of(), false),
                new PackSelectionConfig(true, Pack.Position.TOP, false)
        )));
    }

    // Nether WorldGen Overhaul
    public static void dpNetherWorldgenOverhaul(AddPackFindersEvent event) {
        if (CompatUtil.AMPLIFIED_NETHER || !NETHER_WORLDGEN_OVERHAUL.get()) return;

        Path path = ModList.get().getModFileById(NetherExp.MOD_ID).getFile().findResource("resourcepacks/nether_worldgen_overhaul");
        PackMetadataSection metadata = new PackMetadataSection(Component.literal("Overhauls the nether world generation"), SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA));
        event.addRepositorySource(source -> source.accept(new Pack(
                new PackLocationInfo("netherexp:nether_worldgen_overhaul", Component.literal("JNE Nether Worldgen Overhaul"), PackSource.BUILT_IN, Optional.empty()),
                new PathPackResources.PathResourcesSupplier(path),
                new Pack.Metadata(metadata.description(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of(), false),
                new PackSelectionConfig(true, Pack.Position.TOP, false)
        )));
    }

    // Nether Mosaic Biome Source
    public static void dpNetherMosaicBiomeSource(AddPackFindersEvent event) {
        if (!IMPROVED_NETHER_BIOME_SOURCE.get()) return;

        Path path = ModList.get().getModFileById(NetherExp.MOD_ID).getFile().findResource("resourcepacks/nether_mosaic_biome_source");
        PackMetadataSection metadata = new PackMetadataSection(Component.literal("Overrides the nether multi-noise biome source"), SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA));
        event.addRepositorySource(source -> source.accept(new Pack(
                new PackLocationInfo("netherexp:nether_mosaic_biome_source", Component.literal("JNE Nether Mosaic Biome Source"), PackSource.BUILT_IN, Optional.empty()),
                new PathPackResources.PathResourcesSupplier(path),
                new Pack.Metadata(metadata.description(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of(), false),
                new PackSelectionConfig(true, Pack.Position.TOP, false)
        )));
    }
}
