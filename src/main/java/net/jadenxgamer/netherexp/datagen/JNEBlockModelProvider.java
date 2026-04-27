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
//        var damp_siltmarram = NetherExp.netherexpPath("block/damp_siltmarram");
//        var moist_siltmarram = NetherExp.netherexpPath("block/moist_siltmarram");
//        var dry_siltmarram = NetherExp.netherexpPath("block/dry_siltmarram");
//
//        cross("damp_siltmarram", damp_siltmarram);
//        cross("moist_siltmarram", moist_siltmarram);
//        cross("dry_siltmarram", dry_siltmarram);
    }
}
