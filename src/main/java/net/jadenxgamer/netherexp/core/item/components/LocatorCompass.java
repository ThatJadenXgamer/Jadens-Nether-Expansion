package net.jadenxgamer.netherexp.core.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public record LocatorCompass(Optional<BlockPos> structurePos, Optional<ResourceLocation> dimension, boolean bound, boolean isActive, int activeTime) {

    public static final LocatorCompass DEFAULT = new LocatorCompass(Optional.empty(), Optional.empty(), false, false, 0);

    public static final Codec<LocatorCompass> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockPos.CODEC.optionalFieldOf("structure_pos").forGetter(LocatorCompass::structurePos),
            ResourceLocation.CODEC.optionalFieldOf("dimension").forGetter(LocatorCompass::dimension),
            Codec.BOOL.optionalFieldOf("bound", false).forGetter(LocatorCompass::bound),
            Codec.BOOL.optionalFieldOf("is_active", false).forGetter(LocatorCompass::isActive),
            Codec.INT.optionalFieldOf("active_time", 0).forGetter(LocatorCompass::activeTime)
    ).apply(instance, LocatorCompass::new));
}