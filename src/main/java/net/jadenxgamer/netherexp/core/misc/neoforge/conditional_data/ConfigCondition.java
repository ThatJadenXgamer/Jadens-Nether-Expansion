package net.jadenxgamer.netherexp.core.misc.neoforge.conditional_data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jadenxgamer.netherexp.config.JNEConfigImpl;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.conditions.ICondition;

public record ConfigCondition(String path) implements ICondition {
    public static final MapCodec<ConfigCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("path").forGetter(ConfigCondition::path)
    ).apply(instance, ConfigCondition::new));

    @Override
    public boolean test(IContext iContext) {
        ModConfigSpec.ConfigValue<?> configValue = JNEConfigImpl.COMMON.getValues().get(this.path);
        if (configValue instanceof ModConfigSpec.BooleanValue booleanValue) {
            return booleanValue.get();
        }
        return false;
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}