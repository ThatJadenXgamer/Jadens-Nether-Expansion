package net.jadenxgamer.netherexp.core.misc.neoforge;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jadenxgamer.netherexp.config.JNEConfigImpl;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.conditions.ICondition;

public record StartupConfigCondition(String path) implements ICondition {
    public static final MapCodec<StartupConfigCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("path").forGetter(StartupConfigCondition::path)
    ).apply(instance, StartupConfigCondition::new));

    @Override
    public boolean test(IContext iContext) {
        ModConfigSpec.ConfigValue<?> configValue = JNEConfigImpl.STARTUP.getValues().get(this.path);
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
