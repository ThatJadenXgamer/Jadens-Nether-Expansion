package net.jadenxgamer.netherexp.datagen;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Objects;

public class JNEItemModelProvider extends ItemModelProvider {
    public JNEItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, NetherExp.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Block
        basicItemWithBlockPath(JNEBlocks.DAMP_SILTMARRAM.get().asItem());
        basicItemWithBlockPath(JNEBlocks.MOIST_SILTMARRAM.get().asItem());
        basicItemWithBlockPath(JNEBlocks.DRY_SILTMARRAM.get().asItem());
    }

    public ItemModelBuilder basicItemWithBlockPath(Item item) {
        return basicItemWithBlockPath(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)));
    }

    public ItemModelBuilder basicItemWithBlockPath(ResourceLocation item) {
        return getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(item.getNamespace(), "block/" + item.getPath()));
    }
}
