package net.jadenxgamer.netherexp.datagen;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class JNEBlockModelProvider extends BlockModelProvider {
    public JNEBlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, NetherExp.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        var tex = NetherExp.netherexpPath("block/pyroclast");

        // Block
        cubeAll("pyroclast", tex);

        // Slab
        slab("pyroclast_slab", tex, tex, tex);
        slabTop("pyroclast_slab_top", tex, tex, tex);

        // Stairs
        stairs("pyroclast_stairs", tex, tex, tex);
        stairsInner("pyroclast_stairs_inner", tex, tex, tex);
        stairsOuter("pyroclast_stairs_outer", tex, tex, tex);

        // Wall
        wallPost("pyroclast_wall_post", tex);
        wallSide("pyroclast_wall_side", tex);
        wallSideTall("pyroclast_wall_side_tall", tex);
        wallInventory("pyroclast_wall_inventory", tex);
    }
}
