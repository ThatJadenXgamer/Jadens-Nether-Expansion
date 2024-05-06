package net.jadenxgamer.netherexp.forge;

import dev.architectury.platform.forge.EventBuses;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.NetherExpClient;
import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.entity.custom.Apparition;
import net.jadenxgamer.netherexp.registry.entity.custom.Vessel;
import net.jadenxgamer.netherexp.registry.entity.custom.Wisp;
import net.jadenxgamer.netherexp.registry.item.brewing.JNEPotionRecipe;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.language.IModInfo;

import static org.codehaus.plexus.util.FileUtils.fileExists;

@Mod(NetherExp.MOD_ID)
public class NetherExpForge {
    public NetherExpForge() {
        EventBuses.registerModEventBus(NetherExp.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        NetherExp.init();

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> NetherExpClient::init);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> NetherExpForgeClient::init);

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(NetherExpForge::commonSetup);
        eventBus.addListener(NetherExpForge::registerAttributes);
        eventBus.addListener(NetherExpForge::addBuiltinPacks);
    }

    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(JNEPotionRecipe::addInvokerPotionRecipes);
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(JNEEntityType.APPARITION.get(), Apparition.createAttributes().build());
        event.put(JNEEntityType.WISP.get(), Wisp.createAttributes().build());
        event.put(JNEEntityType.VESSEL.get(), Vessel.createAttributes().build());
    }

    private static void addBuiltinPacks(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            IModFileInfo pathMVR = getPackInfo(new ResourceLocation(NetherExp.MOD_ID, "netherexp_vanilla_retextures"));
            event.addRepositorySource((packConsumer) ->
                    packConsumer.accept(Pack.create(
                            "resourcepacks/" + pathMVR,
                            Component.literal("Nether Expansion Retextures"), false,
                            (path) -> new PathPackResources(path, pathMVR.getFile().findResource("resourcepacks/" + pathMVR.getFile()), true),
                            createInfoForLatest(Component.literal("Optional Revamps for many Vanilla Textures"), false),
                            PackType.CLIENT_RESOURCES, Pack.Position.BOTTOM, false, PackSource.BUILT_IN)));

        }
    }

    private static IModFileInfo getPackInfo(ResourceLocation pack) {
        if (!FMLLoader.isProduction()) {
            for (IModInfo mod : ModList.get().getMods()) {
                if (mod.getModId().startsWith("generated_") && fileExists("resourcepacks/" + pack.getPath())) {
                    return mod.getOwningFile();
                }
            }
        }
        return ModList.get().getModFileById(pack.getNamespace());
    }

    private static Pack.Info createInfoForLatest(Component description, boolean hidden) {
        return new Pack.Info(
                description,
                SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA),
                SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES),
                FeatureFlagSet.of(),
                hidden
        );
    }
}
