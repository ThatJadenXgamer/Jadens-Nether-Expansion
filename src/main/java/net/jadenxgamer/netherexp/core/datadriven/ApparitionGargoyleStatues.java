package net.jadenxgamer.netherexp.core.datadriven;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record ApparitionGargoyleStatues(ResourceLocation gargoyleStatue, List<Integer> preferredByPersonalities, ResourceLocation possessedMob) {

    public static final Codec<ApparitionGargoyleStatues> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("gargoyle_statue").forGetter(ApparitionGargoyleStatues::gargoyleStatue),
            Codec.list(Codec.INT).fieldOf("preferred_by_personalities").forGetter(ApparitionGargoyleStatues::preferredByPersonalities),
            ResourceLocation.CODEC.fieldOf("possessed_mob").forGetter(ApparitionGargoyleStatues::possessedMob)
    ).apply(instance, ApparitionGargoyleStatues::new));
}
