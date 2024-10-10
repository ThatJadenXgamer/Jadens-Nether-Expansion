package net.jadenxgamer.netherexp.forge;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModFileInfo;

import java.nio.file.Path;

public class JNEBuiltinPacks {

    // Adds vanilla retextures, it is separated because forge for some reason has mod resources below vanilla
    public static void rpJNERetextures(AddPackFindersEvent event) {
        IModFileInfo mod = ModList.get().getModFileById(NetherExp.MOD_ID);
        Path file = mod.getFile().findResource("resourcepacks/jne_retextures");
        event.addRepositorySource((packConsumer) ->
                packConsumer.accept(Pack.create(
                        "netherexp:jne_retextures",
                        Component.literal("JNE-Retextures"),
                        true,
                        (path) -> new PathPackResources(path, file, true),
                        new Pack.Info(Component.literal("Built-in JNE Vanilla Retextures"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES), FeatureFlagSet.of()),
                        PackType.CLIENT_RESOURCES, Pack.Position.TOP, false, PackSource.BUILT_IN)));
    }

    // Adds Retextures which may cause some Mod Conflicts or Inconsistencies
    public static void rpConflictingRetextures(AddPackFindersEvent event) {
        IModFileInfo mod = ModList.get().getModFileById(NetherExp.MOD_ID);
        Path file = mod.getFile().findResource("resourcepacks/conflicting_retextures");
        event.addRepositorySource((packConsumer) ->
                packConsumer.accept(Pack.create(
                        "netherexp:conflicting_retextures",
                        Component.literal("Conflicting Retextures"),
                        false,
                        (path) -> new PathPackResources(path, file, true),
                        new Pack.Info(Component.literal("Adds Retextures which may cause Mod Conflicts"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES), FeatureFlagSet.of()),
                        PackType.CLIENT_RESOURCES, Pack.Position.BOTTOM, false, PackSource.BUILT_IN)));
    }

    // Gives all Nether Woodsets a unique design
    public static void rpUniqueNetherWood(AddPackFindersEvent event) {
        IModFileInfo mod = ModList.get().getModFileById(NetherExp.MOD_ID);
        Path file = mod.getFile().findResource("resourcepacks/unique_nether_wood");
        event.addRepositorySource((packConsumer) ->
                packConsumer.accept(Pack.create(
                        "unique_nether_wood",
                        Component.literal("Unique Nether Wood"),
                        false,
                        (path) -> new PathPackResources(path, file, true),
                        new Pack.Info(Component.literal("Gives All Nether Woodsets Unique Designs"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES), FeatureFlagSet.of()),
                        PackType.CLIENT_RESOURCES, Pack.Position.BOTTOM, false, PackSource.BUILT_IN)));
    }

    // Increases the size of all nether biomes
    public static void dpLargerNetherBiomes(AddPackFindersEvent event) {
        IModFileInfo mod = ModList.get().getModFileById(NetherExp.MOD_ID);
        Path file = mod.getFile().findResource("resourcepacks/larger_nether_biomes");
        event.addRepositorySource((packConsumer) ->
                packConsumer.accept(Pack.create(
                        "larger_nether_biomes",
                        Component.literal("Larger Nether Biomes"),
                        true,
                        (path) -> new PathPackResources(path, file, true),
                        new Pack.Info(Component.literal("Increases the size of all nether biomes"), SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA), FeatureFlagSet.of()),
                        PackType.SERVER_DATA, Pack.Position.TOP, true, PackSource.BUILT_IN)));
    }

    // Compatibility for My Nethers Delight
    public static void dpNethersDelightCompat(AddPackFindersEvent event) {
        IModFileInfo mod = ModList.get().getModFileById(NetherExp.MOD_ID);
        Path file = mod.getFile().findResource("resourcepacks/nethers_delight_compat");
        event.addRepositorySource((packConsumer) ->
                packConsumer.accept(
                        Pack.create("nethers_delight_compat",
                                Component.literal("My Nether's Delight Compatibility"),
                                true,
                                (path) -> new PathPackResources(path, file, true),
                                new Pack.Info(Component.literal("Compatibility for My Nether's Delight"), SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA), FeatureFlagSet.of()),
                                PackType.SERVER_DATA, Pack.Position.TOP, true, PackSource.BUILT_IN)));
    }
}
