package net.jadenxgamer.netherexp.forge;

import com.mojang.serialization.Codec;
import dev.architectury.annotations.ForgeEvent;
import dev.architectury.platform.forge.EventBuses;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.NetherExpClient;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.config.JNEForgeConfigs;
import net.jadenxgamer.netherexp.forge.event.JNEBuiltinPacks;
import net.jadenxgamer.netherexp.forge.event.RightClickBlockEvent;
import net.jadenxgamer.netherexp.forge.loot.JNELootModifiers;
import net.jadenxgamer.netherexp.forge.worldgen.SpawnCostsBiomeModifier;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.entity.custom.*;
import net.jadenxgamer.netherexp.registry.fluid.JNEFluids;
import net.jadenxgamer.netherexp.registry.item.brewing.JNEPotionRecipe;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
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
        MinecraftForge.EVENT_BUS.addListener(NetherExpForge::onRightClickBlock);
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
        event.put(JNEEntityType.ECTO_SLAB.get(), EctoSlab.createAttributes().build());
        event.put(JNEEntityType.BANSHEE.get(), Banshee.createAttributes().build());
        event.put(JNEEntityType.STAMPEDE.get(), Stampede.createAttributes().build());
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

    private static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        RightClickBlockEvent.WartBeardGrowerEvent(event);
    }

    private static void addBuiltinPacks(AddPackFindersEvent event) {
        // ResourcePacks
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            JNEBuiltinPacks.rpJNERetextures(event);
            JNEBuiltinPacks.rpConflictingRetextures(event);
            JNEBuiltinPacks.rpUniqueNetherWood(event);
        }
        // Datapacks
        if (event.getPackType() == PackType.SERVER_DATA) {
            if (JNEConfigs.LARGER_NETHER_BIOMES.get()) {
                JNEBuiltinPacks.dpLargerNetherBiomes(event);
            }
            if (NetherExp.compatNethersDelight()) {
                JNEBuiltinPacks.dpNethersDelightCompat(event);
            }
        }
    }
}
