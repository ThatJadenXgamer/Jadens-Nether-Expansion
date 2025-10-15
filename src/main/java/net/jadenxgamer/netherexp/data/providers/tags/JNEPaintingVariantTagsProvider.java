package net.jadenxgamer.netherexp.data.providers.tags;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.data.JNEDataGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PaintingVariantTagsProvider;
import net.minecraft.tags.PaintingVariantTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public final class JNEPaintingVariantTagsProvider extends PaintingVariantTagsProvider {

    /**
     * Create a new painting variant tags provider.
     *
     * @param output             the output location
     * @param registries         a {@linkplain CompletableFuture} supplying the registries
     * @param existingFileHelper a {@linkplain ExistingFileHelper} to find existing files
     */
    public JNEPaintingVariantTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, registries, NetherExp.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        tag(PaintingVariantTags.PLACEABLE)
        // TODO: IMPLEMENT PAINTINGS
        // .add(JNEPaintingVariants.HOUSE)
        ;
    }
}
