package net.jadenxgamer.netherexp.core.datadriven;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record ApparitionAggression(List<Integer> preferredByPersonalities, boolean hasPossession, int targetPriority,
                                   ResourceLocation targetMob, ResourceLocation possessedMob) {

    public static final Codec<ApparitionAggression> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(Codec.INT).fieldOf("preferred_by_personalities").forGetter(ApparitionAggression::preferredByPersonalities),
            Codec.BOOL.fieldOf("has_possession").forGetter(ApparitionAggression::hasPossession),
            Codec.INT.fieldOf("target_priority").forGetter(ApparitionAggression::targetPriority),
            ResourceLocation.CODEC.fieldOf("target_mob").forGetter(ApparitionAggression::targetMob),
            ResourceLocation.CODEC.optionalFieldOf("possessed_mob", NetherExp.idVanilla("pig")).forGetter(ApparitionAggression::possessedMob)
    ).apply(instance, ApparitionAggression::new));
}
