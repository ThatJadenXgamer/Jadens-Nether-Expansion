package net.jadenxgamer.netherexp.core.effect;

import net.jadenxgamer.elysium_api.impl.client.assetdriven.lightmap_settings.LightmapSettingsManager;
import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import static net.jadenxgamer.netherexp.config.JNEConfigs.CIERGE_OF_TREACHERY_RED_LIGHTS;

public class BetrayedEffect extends IncurableEffect {

    public BetrayedEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onEffectAdded(LivingEntity entity, int amplifier) {
        if (CIERGE_OF_TREACHERY_RED_LIGHTS.get()) LightmapSettingsManager.enableEventFlag(NetherExp.netherexpPath("betrayed"));
        super.onEffectAdded(entity, amplifier);
    }

    @Override
    public void onEffectRemoved(LivingEntity entity, int amplifier) {
        LightmapSettingsManager.disableEventFlag(NetherExp.netherexpPath("betrayed"));
        super.onEffectRemoved(entity, amplifier);
    }
}
