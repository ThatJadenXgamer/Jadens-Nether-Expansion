package net.jadenxgamer.netherexp.fabric.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;

public class EntityGeneration {
    public static void generateEntities() {
        BiomeModifications.create(new ResourceLocation(NetherExp.MOD_ID, "spawn_costs"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(Biomes.SOUL_SAND_VALLEY),
                (biomeSelectionContext, biomeModificationContext) -> biomeModificationContext.getSpawnSettings().setSpawnCost(JNEEntityType.VESSEL.get(), 0.15, 0.7))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(Biomes.SOUL_SAND_VALLEY),
                (biomeSelectionContext, biomeModificationContext) -> biomeModificationContext.getSpawnSettings().setSpawnCost(JNEEntityType.APPARITION.get(), 0.15, 0.7));

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.SOUL_SAND_VALLEY), MobCategory.MONSTER,
                JNEEntityType.VESSEL.get(), 3, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.SOUL_SAND_VALLEY), MobCategory.MONSTER,
                JNEEntityType.APPARITION.get(), 5, 1, 1);

        SpawnPlacements.register(JNEEntityType.VESSEL.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(JNEEntityType.APPARITION.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
    }
}
