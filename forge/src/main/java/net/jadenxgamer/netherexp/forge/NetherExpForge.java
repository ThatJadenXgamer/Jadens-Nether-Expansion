package net.jadenxgamer.netherexp.forge;

import com.mojang.serialization.Codec;
import dev.architectury.platform.forge.EventBuses;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.NetherExpClient;
import net.jadenxgamer.netherexp.forge.worldgen.JNESpawnCostsBiomeModifier;
import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.entity.custom.Apparition;
import net.jadenxgamer.netherexp.registry.entity.custom.Banshee;
import net.jadenxgamer.netherexp.registry.entity.custom.Vessel;
import net.jadenxgamer.netherexp.registry.entity.custom.Wisp;
import net.jadenxgamer.netherexp.registry.item.brewing.JNEPotionRecipe;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.nio.file.Path;

@Mod(NetherExp.MOD_ID)
public class NetherExpForge {
    public NetherExpForge() {
        EventBuses.registerModEventBus(NetherExp.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        NetherExp.init();

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> NetherExpClient::init);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> NetherExpForgeClient::init);

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, NetherExp.MOD_ID);
        BIOME_MODIFIER_SERIALIZERS.register(eventBus);
        BIOME_MODIFIER_SERIALIZERS.register("jne_spawn_costs", JNESpawnCostsBiomeModifier::createCodec);
        eventBus.addListener(NetherExpForge::commonSetup);
        eventBus.addListener(NetherExpForge::registerAttributes);
        eventBus.addListener(NetherExpForge::registerSpawnPlacements);
        eventBus.addListener(NetherExpForge::addBuiltinPacks);
    }

    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(JNEPotionRecipe::addInvokerPotionRecipes);
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(JNEEntityType.APPARITION.get(), Apparition.createAttributes().build());
        event.put(JNEEntityType.WISP.get(), Wisp.createAttributes().build());
        event.put(JNEEntityType.VESSEL.get(), Vessel.createAttributes().build());
        event.put(JNEEntityType.ECTO_SLAB.get(), Vessel.createAttributes().build());
        event.put(JNEEntityType.BANSHEE.get(), Banshee.createAttributes().build());
        event.put(JNEEntityType.STAMPEDE.get(), Vessel.createAttributes().build());
    }

    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(JNEEntityType.VESSEL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(JNEEntityType.APPARITION.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(JNEEntityType.BANSHEE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }

    private static void addBuiltinPacks(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            rpConflictingRetextures(event);
            rpUniqueNetherWood(event);
        }
    }

    // Adds Re-textures which may cause some Mod Conflicts or Inconsistencies
    private static void rpConflictingRetextures(AddPackFindersEvent event) {
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
    private static void rpUniqueNetherWood(AddPackFindersEvent event) {
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
}
