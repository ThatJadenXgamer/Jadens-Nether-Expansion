package net.jadenxgamer.netherexp.core.keys;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.JukeboxSong;

public class JNEJukeboxSongs {
    public static final ResourceKey<JukeboxSong> TEARS = register("minecraft", "tears");
    public static final ResourceKey<JukeboxSong> PATIENCE = register("netherexp", "patience");

    private static ResourceKey<JukeboxSong> register(String namespace, String name) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, NetherExp.idPath(namespace, name));
    }
}