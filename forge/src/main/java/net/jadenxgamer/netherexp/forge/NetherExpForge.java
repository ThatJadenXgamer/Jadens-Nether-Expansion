package net.jadenxgamer.netherexp.forge;

import com.mojang.serialization.Codec;
import dev.architectury.platform.forge.EventBuses;
import java.nio.file.Path;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.NetherExpClient;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.config.JNEForgeConfigs;
import net.jadenxgamer.netherexp.forge.loot.JNELootModifiers;
import net.jadenxgamer.netherexp.forge.worldgen.SpawnCostsBiomeModifier;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.entity.custom.*;
import net.jadenxgamer.netherexp.registry.fluid.JNEFluids;
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
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(NetherExp.MOD_ID)
public class NetherExpForge {
    public NetherExpForge() {
        EventBuses.registerModEventBus(
            NetherExp.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, JNEForgeConfigs.COMMON);
        NetherExp.init();

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> NetherExpClient::init);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> NetherExpForgeClient::init);

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Forge-specific Registries
        JNELootModifiers.LOOT_MODIFIERS.register(eventBus);
        final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, NetherExp.MOD_ID);
        BIOME_MODIFIER_SERIALIZERS.register(eventBus);
        BIOME_MODIFIER_SERIALIZERS.register("spawn_costs", SpawnCostsBiomeModifier::createCodec);

        // Events
        eventBus.addListener(NetherExpForge::commonSetup);
        eventBus.addListener(NetherExpForge::registerAttributes);
        eventBus.addListener(NetherExpForge::registerSpawnPlacements);
        eventBus.addListener(NetherExpForge::loadComplete);
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
        event.put(JNEEntityType.CARCASS.get(), Carcass.createAttributes().build());
    }

    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(JNEEntityType.VESSEL.get(), SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules,
            SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(JNEEntityType.APPARITION.get(), SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules,
            SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(JNEEntityType.BANSHEE.get(), SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules,
            SpawnPlacementRegisterEvent.Operation.REPLACE);
    }

    public static void loadComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(NetherExpForge::registerFluidInteractions);
    }

    private static void registerFluidInteractions() {
        FluidInteractionRegistry.addInteraction(JNEFluids.ECTOPLASM.get().getFluidType(),
            new FluidInteractionRegistry.InteractionInformation(ForgeMod.WATER_TYPE.get(),
                fluidState -> JNEBlocks.BLACK_ICE.get().defaultBlockState()));
    }

    private static void addBuiltinPacks(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            rpJNERetextures(event);
            rpConflictingRetextures(event);
            rpUniqueNetherWood(event);
        }
        if (event.getPackType() == PackType.SERVER_DATA) {
            if (JNEConfigs.LARGER_NETHER_BIOMES.get()) {
                dpLargerNetherBiomes(event);
            }
            if (NetherExp.compatNethersDelight()) {
                dpNethersDelightCompat(event);
            }
        }
    }

    // Adds vanilla retextures, it is separated because forge for some reason has mod resources
    // below vanilla
    private static void rpJNERetextures(AddPackFindersEvent event) {
        IModFileInfo mod = ModList.get().getModFileById(NetherExp.MOD_ID);
        Path file = mod.getFile().findResource("resourcepacks/jne_retextures");
        event.addRepositorySource(
            (packConsumer)
                -> packConsumer.accept(Pack.create("netherexp:jne_retextures",
                    Component.literal("JNE-Retextures"), true,
                    (path)
                        -> new PathPackResources(path, file, true),
                    new Pack.Info(Component.literal("Built-in JNE Vanilla Retextures"),
                        SharedConstants.getCurrentVersion().getPackVersion(
                            PackType.CLIENT_RESOURCES),
                        FeatureFlagSet.of()),
                    PackType.CLIENT_RESOURCES, Pack.Position.TOP, false, PackSource.BUILT_IN)));
    }

    // Adds Retextures which may cause some Mod Conflicts or Inconsistencies
    private static void rpConflictingRetextures(AddPackFindersEvent event) {
        IModFileInfo mod = ModList.get().getModFileById(NetherExp.MOD_ID);
        Path file = mod.getFile().findResource("resourcepacks/conflicting_retextures");
        event.addRepositorySource(
            (packConsumer)
                -> packConsumer.accept(Pack.create("netherexp:conflicting_retextures",
                    Component.literal("Conflicting Retextures"), false,
                    (path)
                        -> new PathPackResources(path, file, true),
                    new Pack.Info(
                        Component.literal("Adds Retextures which may cause Mod Conflicts"),
                        SharedConstants.getCurrentVersion().getPackVersion(
                            PackType.CLIENT_RESOURCES),
                        FeatureFlagSet.of()),
                    PackType.CLIENT_RESOURCES, Pack.Position.BOTTOM, false, PackSource.BUILT_IN)));
    }

    // Gives all Nether Woodsets a unique design
    private static void rpUniqueNetherWood(AddPackFindersEvent event) {
        IModFileInfo mod = ModList.get().getModFileById(NetherExp.MOD_ID);
        Path file = mod.getFile().findResource("resourcepacks/unique_nether_wood");
        event.addRepositorySource(
            (packConsumer)
                -> packConsumer.accept(Pack.create("unique_nether_wood",
                    Component.literal("Unique Nether Wood"), false,
                    (path)
                        -> new PathPackResources(path, file, true),
                    new Pack.Info(Component.literal("Gives All Nether Woodsets Unique Designs"),
                        SharedConstants.getCurrentVersion().getPackVersion(
                            PackType.CLIENT_RESOURCES),
                        FeatureFlagSet.of()),
                    PackType.CLIENT_RESOURCES, Pack.Position.BOTTOM, false, PackSource.BUILT_IN)));
    }

    // Increases the size of all nether biomes
    private static void dpLargerNetherBiomes(AddPackFindersEvent event) {
        IModFileInfo mod = ModList.get().getModFileById(NetherExp.MOD_ID);
        Path file = mod.getFile().findResource("resourcepacks/larger_nether_biomes");
        event.addRepositorySource(
            (packConsumer)
                -> packConsumer.accept(Pack.create("larger_nether_biomes",
                    Component.literal("Larger Nether Biomes"), false,
                    (path)
                        -> new PathPackResources(path, file, true),
                    new Pack.Info(Component.literal("Increases the size of all nether biomes"),
                        SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA),
                        FeatureFlagSet.of()),
                    PackType.SERVER_DATA, Pack.Position.TOP, true, PackSource.BUILT_IN)));
    }

    // Compatibility for My Nethers Delight
    private static void dpNethersDelightCompat(AddPackFindersEvent event) {
        IModFileInfo mod = ModList.get().getModFileById(NetherExp.MOD_ID);
        Path file = mod.getFile().findResource("resourcepacks/nethers_delight_compat");
        event.addRepositorySource(
            (packConsumer)
                -> packConsumer.accept(Pack.create("nethers_delight_compat",
                    Component.literal("My Nether's Delight Compatibility"), false,
                    (path)
                        -> new PathPackResources(path, file, true),
                    new Pack.Info(Component.literal("Compatibility for My Nether's Delight"),
                        SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA),
                        FeatureFlagSet.of()),
                    PackType.SERVER_DATA, Pack.Position.TOP, true, PackSource.BUILT_IN)));
    }
}
