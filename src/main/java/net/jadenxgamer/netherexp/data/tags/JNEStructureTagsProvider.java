package net.jadenxgamer.netherexp.data.tags;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.StructureTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class JNEStructureTagsProvider extends StructureTagsProvider {

    /**
     * Create a new structure tags provider.
     *
     * @param output             the output location
     * @param registries         a {@linkplain CompletableFuture} supplying the registries
     * @param existingFileHelper a {@linkplain ExistingFileHelper} to find existing files
     */
    public JNEStructureTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, registries, NetherExp.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        tag(JNETags.Structures.SANCTUM_COMPASS_LOCATED)
                // TODO: IMPLEMENT SANCTUM
                // .add(JNEStructures.SANCTUM)
        ;
    }
}
