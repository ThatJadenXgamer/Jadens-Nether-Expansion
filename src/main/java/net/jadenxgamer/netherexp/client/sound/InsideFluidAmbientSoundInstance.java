package net.jadenxgamer.netherexp.client.sound;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.function.Predicate;

//TODO: move this to Elysium API
@OnlyIn(Dist.CLIENT)
public class InsideFluidAmbientSoundInstance extends AbstractTickableSoundInstance {
    private final LocalPlayer player;
    private final Predicate<LocalPlayer> predicate;
    private int fade;

    public InsideFluidAmbientSoundInstance(LocalPlayer player, SoundEvent soundEvent, float volume, Predicate<LocalPlayer> predicate) {
        super(soundEvent, SoundSource.AMBIENT, SoundInstance.createUnseededRandom());
        this.player = player;
        this.predicate = predicate;
        this.looping = true;
        this.delay = 0;
        this.volume = volume;
        this.relative = true;
    }

    @Override
    public void tick() {
        if (!this.player.isRemoved() && this.fade >= 0) {
            if (this.predicate.test(this.player)) this.fade++;
            else this.fade -= 2;
            this.fade = Math.min(this.fade, 40);
            this.volume = Math.clamp((float) this.fade / 40.0F, 0.0F, 1.0F);
        } else this.stop();
    }
}