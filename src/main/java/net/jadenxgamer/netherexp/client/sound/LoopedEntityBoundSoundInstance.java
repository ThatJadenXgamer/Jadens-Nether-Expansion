package net.jadenxgamer.netherexp.client.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
//TODO: hi-hi, move this to Elysium :3
public class LoopedEntityBoundSoundInstance extends AbstractTickableSoundInstance {
    private final Entity entity;

    public LoopedEntityBoundSoundInstance(SoundEvent soundEvent, SoundSource source, float volume, float pitch, Entity entity, long seed) {
        super(soundEvent, source, RandomSource.create(seed));
        this.volume = volume;
        this.pitch = pitch;
        this.entity = entity;
        this.x = this.entity.getX();
        this.y = this.entity.getY();
        this.z = this.entity.getZ();
        this.looping = true;
        this.delay = 0;
    }

    public boolean canPlaySound() {
        return !this.entity.isSilent();
    }

    public boolean canStartSilent() {
        return true;
    }

    public void tick() {
        if (this.entity.isRemoved()) {
            this.stop();
        } else {
            this.x = this.entity.getX();
            this.y = this.entity.getY();
            this.z = this.entity.getZ();
        }
    }
}