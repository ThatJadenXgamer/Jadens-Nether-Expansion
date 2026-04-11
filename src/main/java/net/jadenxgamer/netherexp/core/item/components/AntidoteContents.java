package net.jadenxgamer.netherexp.core.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.datadriven.ApparitionGargoyleStatues;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.PotionContents;

import java.util.List;
import java.util.Optional;

public record AntidoteContents(ResourceLocation name, Optional<Integer> customColor, List<MobEffectInstance> customEffects) {

    public static final Codec<AntidoteContents> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("name").forGetter(AntidoteContents::name),
            Codec.INT.optionalFieldOf("custom_color").forGetter(AntidoteContents::customColor),
            MobEffectInstance.CODEC.listOf().optionalFieldOf("custom_effects", List.of()).forGetter(AntidoteContents::customEffects)
    ).apply(instance, AntidoteContents::new));
}
