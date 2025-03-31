package net.jadenxgamer.netherexp.event;

import net.jadenxgamer.elysium_api.api.biome.ElysiumBiomeRegistry;
import net.jadenxgamer.elysium_api.api.surface_rules.SurfaceRulesRegistry;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.JNERegistries;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.data_driven.wisp_archeology.WispArcheology;
import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.entity.custom.*;
import net.jadenxgamer.netherexp.registry.fluid.JNEFluids;
import net.jadenxgamer.netherexp.registry.item.brewing.JNEPotionRecipe;
import net.jadenxgamer.netherexp.registry.misc_registry.JNEBuiltinPacks;
import net.jadenxgamer.netherexp.registry.worldgen.JNEBiomes;
import net.jadenxgamer.netherexp.registry.worldgen.JNESurfaceRules;
import net.jadenxgamer.netherexp.util.CompatUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.DataPackRegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = NetherExp.MOD_ID)
public class JNEEvents {

    @SubscribeEvent
    public static void fixMissingMappings(MissingMappingsEvent event) {
        event.getAllMappings(ForgeRegistries.Keys.BLOCKS).forEach(missingMapping -> {
            switch (missingMapping.getKey().toString()) {
                case "netherexp:soul_jack_o_lantern" -> missingMapping.remap(Blocks.JACK_O_LANTERN);
                case "netherexp:soul_ghoul_o_lantern" -> missingMapping.remap(JNEBlocks.GHOUL_O_LANTERN.get());
            }
        });
    }

    @SubscribeEvent
    public static void onServerStart(ServerAboutToStartEvent event) {
        NetherExp.registryAccess = event.getServer().registryAccess();
        if (JNEConfigs.ENABLE_SUB_BIOMES.get()) {
            //ElysiumBiomeRegistry.replaceNetherBiome(Biomes.SOUL_SAND_VALLEY, JNEBiomes.SORROWSQUASH_PASTURES, 0.15, 64, new ResourceLocation(NetherExp.MOD_ID, "ssv_2"), NetherExp.registryAccess);
            if (JNEConfigs.BLACK_ICE_GLACIERS.get()) {
                ElysiumBiomeRegistry.replaceNetherBiome(Biomes.SOUL_SAND_VALLEY, JNEBiomes.BLACK_ICE_GLACIERS, JNEConfigs.BLACK_ICE_GLACIERS_RARITY.get(), JNEConfigs.BLACK_ICE_GLACIERS_SIZE.get(), new ResourceLocation(NetherExp.MOD_ID, "ssv_3"), NetherExp.registryAccess);
            }
        }
    }

    @SubscribeEvent
    public static void livingDie(LivingDeathEvent event) {
        if (CompatUtil.checkAlexsCaves() && event.getEntity().getType() == JNEEntityType.ECTO_SLAB.get() && event.getSource() != null && event.getSource().getEntity() instanceof Frog frog) {
            if (frog.getVariant() == BuiltInRegistries.FROG_VARIANT.get(new ResourceLocation("alexscaves", "primordial"))) {
                event.getEntity().spawnAtLocation(new ItemStack(JNEBlocks.CARMINE_FROGMIST.get()));
            }
        }
    }

    @Mod.EventBusSubscriber(modid = NetherExp.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {

        @SubscribeEvent
        public static void dataDrivenRegistries(DataPackRegistryEvent.NewRegistry event) {
            event.dataPackRegistry(JNERegistries.WISP_ARCHEOLOGY, WispArcheology.CODEC);
        }

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(JNEEntityType.APPARITION.get(), Apparition.createAttributes().build());
            event.put(JNEEntityType.WISP.get(), Wisp.createAttributes().build());
            event.put(JNEEntityType.VESSEL.get(), Vessel.createAttributes().build());
            event.put(JNEEntityType.ECTO_SLAB.get(), EctoSlab.createAttributes().build());
            event.put(JNEEntityType.BANSHEE.get(), Banshee.createAttributes().build());
            event.put(JNEEntityType.STAMPEDE.get(), Stampede.createAttributes().build());
            event.put(JNEEntityType.CARCASS.get(), Carcass.createAttributes().build());
            event.put(JNEEntityType.FALSE_CARCASS.get(), FalseCarcass.createAttributes().build());
        }

        @SubscribeEvent
        public static void commonSetup(final FMLCommonSetupEvent event) {
            event.enqueueWork(() -> {
                SurfaceRulesRegistry.registerSurfaceRule(JNESurfaceRules.init());
                JNEPotionRecipe.addInvokerPotionRecipes();
            });
        }

        @SubscribeEvent
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

        @SubscribeEvent
        public static void loadComplete(FMLLoadCompleteEvent event) {
            event.enqueueWork(JNEFluids::initFluidInteractions);
        }

        @SubscribeEvent
        public static void addBuiltinPacks(AddPackFindersEvent event) {
            // ResourcePacks
            if (event.getPackType() == PackType.CLIENT_RESOURCES) {
                JNEBuiltinPacks.rpJNEEmissives(event);
                JNEBuiltinPacks.rpJNERetextures(event);
                JNEBuiltinPacks.rpConflictingRetextures(event);
                JNEBuiltinPacks.rpUniqueNetherWood(event);
            }
            // Datapacks
            if (event.getPackType() == PackType.SERVER_DATA) {
                if (JNEConfigs.LARGER_NETHER_BIOMES.get()) {
                    JNEBuiltinPacks.dpLargerNetherBiomes(event);
                }
                if (CompatUtil.checkNethersDelight()) {
                    JNEBuiltinPacks.dpNethersDelightCompat(event);
                }
                if (CompatUtil.checkAlexsCaves()) {
                    JNEBuiltinPacks.dpAlexsCavesCompat(event);
                }
                if (CompatUtil.checkAlexsMobs()) {
                    JNEBuiltinPacks.dpAlexsMobsCompat(event);
                }
                if (CompatUtil.checkGardensOfTheDead()) {
                    JNEBuiltinPacks.dpGardensOfTheDeadCompat(event);
                }
                if (CompatUtil.checkRubinatedNether()) {
                    JNEBuiltinPacks.dpRubinatedNetherCompat(event);
                }
                if (CompatUtil.checkCavernsAndChasms()) {
                    JNEBuiltinPacks.dpCavernsAndChasmsCompat(event);
                }
                if (CompatUtil.checkOreganized()) {
                    JNEBuiltinPacks.dpOreganizedCompat(event);
                }
            }
        }
    }
}
