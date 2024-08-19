package net.jadenxgamer.netherexp.forge.worldgen;

import com.mojang.serialization.Codec;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public record JNESpawnCostsBiomeModifier() implements BiomeModifier {
    private static final RegistryObject<Codec<? extends BiomeModifier>> SERIALIZER = RegistryObject.create(new ResourceLocation(NetherExp.MOD_ID, "jne_spawn_costs"), ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, NetherExp.MOD_ID);

    @Override
    public void modify(Holder<Biome> biomes, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.ADD) {
            if (biomes.is(Biomes.SOUL_SAND_VALLEY)) {
                builder.getMobSpawnSettings().addMobCharge(JNEEntityType.APPARITION.get(), 0.7, 0.15);
                builder.getMobSpawnSettings().addMobCharge(JNEEntityType.VESSEL.get(), 0.7, 0.15);
                builder.getMobSpawnSettings().addMobCharge(JNEEntityType.BANSHEE.get(), 0.7, 0.15);
            }
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return SERIALIZER.get();
    }

    public static Codec<JNESpawnCostsBiomeModifier> createCodec() {
        return Codec.unit(JNESpawnCostsBiomeModifier::new);
    }
}
