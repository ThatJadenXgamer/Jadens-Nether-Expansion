package net.jadenxgamer.netherexp.datagen;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class JNEBlockStateProvider extends BlockStateProvider {

    public JNEBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, NetherExp.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // Block
        simpleBlock(JNEBlocks.DAMP_SILTMARRAM.get(),
                models().getExistingFile(NetherExp.netherexpPath("block/damp_siltmarram")));
        simpleBlock(JNEBlocks.MOIST_SILTMARRAM.get(),
                models().getExistingFile(NetherExp.netherexpPath("block/moist_siltmarram")));
        simpleBlock(JNEBlocks.DRY_SILTMARRAM.get(),
                models().getExistingFile(NetherExp.netherexpPath("block/dry_siltmarram")));

//        // Slab
//        slabBlock((SlabBlock) JNEBlocks.PYROCLAST_SLAB.get(),
//                models().getExistingFile(NetherExp.netherexpPath("block/pyroclast_slab")),
//                models().getExistingFile(NetherExp.netherexpPath("block/pyroclast_slab_top")),
//                models().getExistingFile(NetherExp.netherexpPath("block/pyroclast")));
//
//        // Stairs
//        stairsBlock((StairBlock) JNEBlocks.PYROCLAST_STAIRS.get(),
//                models().getExistingFile(NetherExp.netherexpPath("block/pyroclast_stairs")),
//                models().getExistingFile(NetherExp.netherexpPath("block/pyroclast_stairs_inner")),
//                models().getExistingFile(NetherExp.netherexpPath("block/pyroclast_stairs_outer")));
//
//        // Wall
//        ModelFile wallPost = models().getExistingFile(NetherExp.netherexpPath("block/pyroclast_wall_post"));
//        ModelFile wallSide = models().getExistingFile(NetherExp.netherexpPath("block/pyroclast_wall_side"));
//        ModelFile wallSideTall = models().getExistingFile(NetherExp.netherexpPath("block/pyroclast_wall_side_tall"));
//        wallBlock((WallBlock) JNEBlocks.PYROCLAST_WALL.get(), wallPost, wallSide, wallSideTall);
    }
}