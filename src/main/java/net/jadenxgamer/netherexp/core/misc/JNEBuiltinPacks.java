package net.jadenxgamer.netherexp.core.misc;

import net.jadenxgamer.netherexp.NetherExp;
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

public class JNEBuiltinPacks {

    // Enables Retextures for JNE
    public static void rpJNERetextures(AddPackFindersEvent event) {
        Path path = ModList.get().getModFileById(NetherExp.MOD_ID).getFile().findResource("resourcepacks/jne_retextures");
        PackMetadataSection metadata = new PackMetadataSection(Component.literal("Built-in JNE Vanilla Retextures"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES));
        event.addRepositorySource(source -> source.accept(new Pack(
                new PackLocationInfo("netherexp:jne_retextures", Component.literal("JNE Retextures"), PackSource.BUILT_IN, Optional.empty()),
                new PathPackResources.PathResourcesSupplier(path),
                new Pack.Metadata(metadata.description(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of(), false),
                new PackSelectionConfig(true, Pack.Position.TOP, false)
        )));
    }
}
