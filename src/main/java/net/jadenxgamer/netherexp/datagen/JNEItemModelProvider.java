package net.jadenxgamer.netherexp.datagen;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class JNEItemModelProvider extends ItemModelProvider {
    public JNEItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, NetherExp.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Block
        withExistingParent("pyroclast", modLoc("block/pyroclast"));

        // Slab
        withExistingParent("pyroclast_slab", modLoc("block/pyroclast_slab"));

        // Stairs
        withExistingParent("pyroclast_stairs", modLoc("block/pyroclast_stairs"));

        // Wall
        withExistingParent("pyroclast_wall", modLoc("block/pyroclast_wall_inventory"));
    }
}
