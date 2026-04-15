package net.jadenxgamer.netherexp.event;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.client.assetdriven.managers.FireParticlesManager;
import net.jadenxgamer.netherexp.core.datadriven.OnDeathGroundConversion;
import net.jadenxgamer.netherexp.core.misc.JNEBuiltinPacks;
import net.jadenxgamer.netherexp.core.misc.JNECauldronInteractions;
import net.jadenxgamer.netherexp.registry.JNECreativeModeTabs;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNERegistries;
import net.jadenxgamer.netherexp.util.BlockCrackTracker;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
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
    public static void onServerTick(ServerTickEvent.Post event) {
        BlockCrackTracker.tick();
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
            JNECauldronInteractions.register();
            JNEItems.setup();
        });
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        JNECreativeModeTabs.addToExistingTabs(event);
    }

//    @SubscribeEvent
//    public static void onInteraction(InputEvent.InteractionKeyMappingTriggered event) {
//        Minecraft client = Minecraft.getInstance();
//        LocalPlayer player = client.player;
//
//        if (player == null) return;
//        ItemStack stack = player.getMainHandItem();
//        if (stack.is(JNEItems.SHOTGUN_FIST.get()) && event.getKeyMapping() == client.options.keyAttack) {
//            client.options.keyAttack.setDown(false);
//            NetherExp.LOGGER.info("SHOTGUN-FIST");
//            event.setCanceled(true);
//            event.setSwingHand(false);
//        }
//        else if (stack.is(JNEItems.PUMP_CHARGE_SHOTGUN.get()) && event.getKeyMapping() == client.options.keyAttack) {
//            client.options.keyAttack.setDown(false);
//            PumpChargeShotgunItem.setPumps(stack, PumpChargeShotgunItem.getPumps(stack) + 1);
//            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), JNESoundEvents.SHOTGUN_LOAD.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
//            event.setCanceled(true);
//            event.setSwingHand(false);
//        }
//    }

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
        if (event.getPackType() == PackType.SERVER_DATA) JNEBuiltinPacks.dpNetherWorldgenOverhaul(event); // Datapacks
    }

    @SubscribeEvent
    public static void registerReloadListener(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new FireParticlesManager());
    }
}
