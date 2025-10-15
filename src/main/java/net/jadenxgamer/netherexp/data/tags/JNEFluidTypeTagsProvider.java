package net.jadenxgamer.netherexp.data.tags;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEFluids;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public final class JNEFluidTypeTagsProvider extends FluidTagsProvider {

    /**
     * Create a new fluid type tags provider.
     *
     * @param output             the output location
     * @param registries         a {@linkplain CompletableFuture} supplying the registries
     * @param existingFileHelper a {@linkplain ExistingFileHelper} to find existing files
     */
    public JNEFluidTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, registries, NetherExp.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        tag(JNETags.Fluids.ECTOPLASM).add(JNEFluids.ECTOPLASM_SOURCE.get(), JNEFluids.ECTOPLASM_FLOWING.get());
        tag(JNETags.Fluids.TURNS_TO_BLACK_ICE).add(Fluids.WATER, Fluids.FLOWING_WATER);
    }
}
