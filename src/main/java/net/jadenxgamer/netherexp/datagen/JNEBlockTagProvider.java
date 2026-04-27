package net.jadenxgamer.netherexp.datagen;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.datagen.providers.LodestoneBlockTagsProvider;

import java.util.HashSet;
import java.util.concurrent.CompletableFuture;

import static net.jadenxgamer.netherexp.registry.JNEBlocks.BLOCKS;

public class JNEBlockTagProvider extends LodestoneBlockTagsProvider {

    public JNEBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, NetherExp.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        var blocks = new HashSet<>(BLOCKS.getEntries());
        //addTagsFromBlockProperties(blocks);
    }
}
