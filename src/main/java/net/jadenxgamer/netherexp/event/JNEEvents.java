package net.jadenxgamer.netherexp.event;

import net.jadenxgamer.elysium_api.api.event.BlockOnPlaceEvent;
import net.jadenxgamer.elysium_api.api.surface_rules.SurfaceRulesRegistry;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.client.assetdriven.managers.BurnPalettesManager;
import net.jadenxgamer.netherexp.client.assetdriven.managers.FireParticlesManager;
import net.jadenxgamer.netherexp.core.block.SorrowsquashBlock;
import net.jadenxgamer.netherexp.core.datadriven.OnDeathGroundConversion;
import net.jadenxgamer.netherexp.core.entity.PortalGlow;
import net.jadenxgamer.netherexp.core.entity.Stampede;
import net.jadenxgamer.netherexp.core.misc.JNEBuiltinPacks;
import net.jadenxgamer.netherexp.core.misc.JNECauldronInteractions;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.core.worldgen.JNESurfaceRules;
import net.jadenxgamer.netherexp.registry.*;
import net.jadenxgamer.netherexp.util.BlockCrackTracker;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.CalculateDetachedCameraDistanceEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = NetherExp.MOD_ID)
public class JNEEvents {

    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event) {
        NetherExp.registryAccess = event.getServer().registryAccess();
    }

    @SubscribeEvent
    public static void calculateCameraDistance(CalculateDetachedCameraDistanceEvent event) {
        if (event.getCamera().getEntity().getVehicle() instanceof Stampede) event.setDistance(event.getDistance() * 1.7f);
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        BlockCrackTracker.tick();
    }


    @SubscribeEvent
    public static void onPortalSpawn(BlockEvent.PortalSpawnEvent event) {
        if (event.getLevel() instanceof Level level) {
            BlockPos pos = event.getPos();
            if (level.getServer() == null) return;
            level.getServer().execute(() -> {
                if (level.getBlockState(pos).is(Blocks.NETHER_PORTAL)) {
                    level.playSound(null, pos, JNESoundEvents.NETHER_PORTAL_ACTIVATE.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                    PortalGlow.spawnForPortal(level, pos);
                }
            });
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        BlockState state = event.getState();
        if (state.is(JNEBlocks.FOSSIL_FUEL_ORE.get()) && event.getPlayer() instanceof ServerPlayer serverPlayer) {
            JNECriteriaTriggers.BROKEN_FOSSIL_FUEL_ORE.get().trigger(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onLevelUnload(LevelEvent.Unload event) {
        if (event.getLevel() instanceof Level level) {
            BlockCrackTracker.onLevelUnload(level);
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        OnDeathGroundConversion.onLivingDeath(event);
    }

    @SubscribeEvent
    public static void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            SurfaceRulesRegistry.registerNetherSurfaceRule(JNESurfaceRules.init(), NetherExp.MOD_ID);
            JNECauldronInteractions.register();
            JNEItems.setup();
            JNEFluids.setup();
        });
    }

    @SubscribeEvent
    public static void onPlaceBlock(BlockOnPlaceEvent event) {
        var level = event.getLevel();
        var state = event.getState();
        var pos = event.getPos();
        SorrowsquashBlock.convertPumpkinStem(level, state, pos);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        JNECreativeModeTabs.addToExistingTabs(event);
    }

    @SubscribeEvent
    public static void registerRegistries(NewRegistryEvent event) {
        JNERegistries.registryInit(event);
    }

    @SubscribeEvent
    public static void datapackRegistry(DataPackRegistryEvent.NewRegistry event) {
        JNERegistries.datapackInit(event);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        JNEEntityType.registerAttributes(event);
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        JNEEntityType.registerSpawnPlacements(event);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void registerEvent(RegisterEvent event) {
        JNEItems.backportRegistries(event);
    }

    @SubscribeEvent
    public static void addBuiltinPacks(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) JNEBuiltinPacks.rpJNERetextures(event); // Resource Packs
        if (event.getPackType() == PackType.SERVER_DATA) { // Datapacks
            JNEBuiltinPacks.dpNetherMosaicBiomeSource(event);
            JNEBuiltinPacks.dpNetherWorldgenOverhaul(event);
        }
    }

    @SubscribeEvent
    public static void registerReloadListener(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new FireParticlesManager());
        event.registerReloadListener(new BurnPalettesManager());
    }

    @SubscribeEvent
    public static void onLivingTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof LivingEntity entity)) return;
        if (entity.level().isClientSide()) return;
        if (entity.displayFireAnimation()) {
            var state = entity.getInBlockState();
            if (state.is(JNETags.Blocks.LAST_FIRE_SUPPORTED_BLOCKS)) entity.setData(JNEAttachmentTypes.LAST_FIRE, state.getBlock().builtInRegistryHolder().key().location());
        } else entity.setData(JNEAttachmentTypes.LAST_FIRE, NetherExp.minecraftPath("fire"));
    }
}