package net.jadenxgamer.netherexp.core.datadriven;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record ApparitionAggressions(List<Integer> preferredByPersonalities, boolean hasPossession, int targetPriority,
                                    ResourceLocation targetMob, ResourceLocation possessedMob) {

    public static final Codec<ApparitionAggressions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(Codec.INT).fieldOf("preferred_by_personalities").forGetter(ApparitionAggressions::preferredByPersonalities),
            Codec.BOOL.fieldOf("has_possession").forGetter(ApparitionAggressions::hasPossession),
            Codec.INT.fieldOf("target_priority").forGetter(ApparitionAggressions::targetPriority),
            ResourceLocation.CODEC.fieldOf("target_mob").forGetter(ApparitionAggressions::targetMob),
            ResourceLocation.CODEC.optionalFieldOf("possessed_mob", NetherExp.minecraftPath("pig")).forGetter(ApparitionAggressions::possessedMob)
    ).apply(instance, ApparitionAggressions::new));
}
