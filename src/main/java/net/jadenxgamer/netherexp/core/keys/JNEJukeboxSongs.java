package net.jadenxgamer.netherexp.core.keys;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.JukeboxSong;

public class JNEJukeboxSongs {
    public static final ResourceKey<JukeboxSong> TEARS = registerKey("minecraft", "tears");

    private static ResourceKey<JukeboxSong> registerKey(String namespace, String name) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, NetherExp.idPath(namespace, name));
    }

    public static void bootstrap(BootstrapContext<JukeboxSong> context) {
        /*
        TODO: IMPLEMENT SOUND EVENT

        context.register(TEARS, new JukeboxSong(
                JNESoundEvents.MUSIC_DISC_TEARS,
                Component.translatable("jukebox_song.minecraft.tears"),
                175,
                10));
         */
    }
}
