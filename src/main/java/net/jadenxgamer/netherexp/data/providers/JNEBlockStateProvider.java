package net.jadenxgamer.netherexp.data.providers;

import com.google.common.collect.ImmutableMap;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.block.LightableBlock;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;

public class JNEBlockStateProvider extends BlockStateProvider {

    public JNEBlockStateProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper fileHelper) {
        super(output, NetherExp.MOD_ID, fileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        JNEBlockFamilies.getBlockFamilies().forEach(this::family);

        {
            Block block = JNEBlocks.ETCHED_SOUL_SLATE_BRICKS.get();
            getVariantBuilder(block).forAllStates(state -> {
                boolean lit = state.getValue(LightableBlock.LIT);
                ResourceLocation texture = blockTexture(block);
                ResourceLocation model = ModelLocationUtils.getModelLocation(block);

                Iterator<BlockModelBuilder> models = IntStream.range(0, 6).mapToObj(i -> lit ? models().withExistingParent(model.getPath() + "/lit/" + i, NetherExp.id("block/emissive_block_12")).texture("base", suffix(texture, "/lit/" + i)).texture("glow", suffix(texture, "/lit/" + i + "_glow")) : models().cubeAll(model.getPath() + "/" + i, suffix(texture, "/" + i))).iterator();

                return ConfiguredModel.builder().modelFile(models.next()).weight(5).nextModel().modelFile(models.next()).weight(5).nextModel().modelFile(models.next()).weight(5).nextModel().modelFile(models.next()).weight(3).nextModel().modelFile(models.next()).weight(3).nextModel().modelFile(models.next()).weight(3).build();
            });
        }

        simpleLightable(JNEBlocks.CHISELED_SOUL_SLATE_BRICKS.get());
        simpleLightable(JNEBlocks.ETCHED_SOUL_SLATE_TILES.get());

        {
            Block block = JNEBlocks.CHISELED_SOUL_SLATE_TILES.get();
            getVariantBuilder(block).forAllStates(state -> {

                boolean lit = state.getValue(LightableBlock.LIT);
                ResourceLocation texture = blockTexture(block);
                ResourceLocation model = ModelLocationUtils.getModelLocation(block);

                return ConfiguredModel.builder().modelFile(lit ?
                        models().getExistingFile(suffix(texture, "_lit")) :
                        models().cubeColumn(model.getPath(), texture, suffix(texture, "_top"))).build();
            });
        }
    }

    private void simpleLightable(Block block) {
        getVariantBuilder(block).forAllStates(state -> {
            boolean lit = state.getValue(LightableBlock.LIT);
            ResourceLocation texture = blockTexture(block);
            ResourceLocation model = ModelLocationUtils.getModelLocation(block);

            return ConfiguredModel.builder().modelFile(lit ? models().withExistingParent(model.getPath() + "_lit", NetherExp.id("block/emissive_block_12")).texture("base", texture.getPath() + "_lit").texture("glow", texture.getPath() + "_lit_glow") : cubeAll(block)).build();
        });
    }

    private void family(BlockFamily family) {
        full(family.getBaseBlock(), family);
        family.getVariants()
                .forEach((variant, block) -> Optional.ofNullable(GENERATORS.get(variant))
                        .ifPresent(generator -> generator.accept(block, family)));
    }

    private final Map<BlockFamily.Variant, BiConsumer<Block, BlockFamily>> GENERATORS = ImmutableMap.<BlockFamily.Variant, BiConsumer<Block, BlockFamily>>builder()
            .put(BlockFamily.Variant.BUTTON, this::button)
            .put(BlockFamily.Variant.CHISELED, this::variant)
            .put(BlockFamily.Variant.CRACKED, this::variant)
            .put(BlockFamily.Variant.DOOR, this::door)
            // TODO: FIGURE THIS OUT
            .put(BlockFamily.Variant.CUSTOM_FENCE, this::fence)
            .put(BlockFamily.Variant.FENCE, this::fence)
            // TODO: FIGURE THIS OUT
            .put(BlockFamily.Variant.CUSTOM_FENCE_GATE, this::fenceGate)
            .put(BlockFamily.Variant.FENCE_GATE, this::fenceGate)
            .put(BlockFamily.Variant.SIGN, this::sign)
            .put(BlockFamily.Variant.SLAB, this::slab)
            .put(BlockFamily.Variant.STAIRS, this::stairs)
            .put(BlockFamily.Variant.PRESSURE_PLATE, this::pressurePlate)
            .put(BlockFamily.Variant.TRAPDOOR, this::trapdoor)
            .put(BlockFamily.Variant.WALL, this::wall)
            .build();

    private final Map<Block, ModelFile> VARIANT_GENERATORS = ImmutableMap.<Block, ModelFile>builder()
            .build();

    public void full(Block variant, BlockFamily family) {
        simpleBlockWithItem(variant, cubeAll(variant));
    }

    public void variant(Block variant, BlockFamily family) {
        simpleBlockWithItem(variant, VARIANT_GENERATORS.getOrDefault(variant, cubeAll(variant)));
    }

    public void button(Block button, BlockFamily family) {
        String name = name(button);
        ResourceLocation texture = blockTexture(family.getBaseBlock());
        ModelFile model = models().button(name, texture);
        ModelFile modelPressed = models().button(name + "_pressed", texture);
        ModelFile modelInventory = models().button(name + "_inventory", texture);
        buttonBlock((ButtonBlock) button, model, modelPressed);
        simpleBlockItem(button, modelInventory);
    }

    private void door(Block door, BlockFamily family) {
        ResourceLocation texture = blockTexture(door);
        ResourceLocation bottom = suffix(texture, "_bottom");
        ResourceLocation top = suffix(texture, "_top");
        doorBlock((DoorBlock) door, bottom, top);
        itemModels().basicItem(door.asItem());
    }

    private void fence(Block fence, BlockFamily family) {
        String name = name(fence);
        ResourceLocation texture = blockTexture(family.getBaseBlock());
        ModelFile modelPost = models().fencePost(name + "_post", texture);
        ModelFile modelSide = models().fenceSide(name + "_side", texture);
        ModelFile modelInventory = models().fenceInventory(name + "_inventory", texture);
        fourWayBlock((FenceBlock) fence, modelPost, modelSide);
        simpleBlockItem(fence, modelInventory);
    }

    private void fenceGate(Block fenceGate, BlockFamily family) {
        String name = name(fenceGate);
        ResourceLocation texture = blockTexture(family.getBaseBlock());
        ModelFile model = models().fenceGate(name, texture);
        ModelFile modelOpen = models().fenceGateOpen(name + "_open", texture);
        ModelFile modelWall = models().fenceGateWall(name + "_wall", texture);
        ModelFile modelWallOpen = models().fenceGateWallOpen(name + "_wall_open", texture);
        fenceGateBlock((FenceGateBlock) fenceGate, model, modelOpen, modelWall, modelWallOpen);
        simpleBlockItem(fenceGate, model);
    }

    private void sign(Block sign, BlockFamily family) {
        String name = name(sign);
        ResourceLocation texture = blockTexture(family.getBaseBlock());
        ModelFile model = models().sign(name, texture);
        WallSignBlock wallSign = (WallSignBlock) family.get(BlockFamily.Variant.WALL_SIGN);
        signBlock((StandingSignBlock) sign, wallSign, model);
        itemModels().basicItem(sign.asItem());
    }

    private void slab(Block slab, BlockFamily family) {
        String name = name(slab);
        ResourceLocation texture = blockTexture(family.getBaseBlock());
        ModelFile modelBottom = models().slab(name, texture, texture, texture);
        ModelFile modelTop = models().slabTop(name + "_top", texture, texture, texture);
        ModelFile modelDouble = models().getExistingFile(ModelLocationUtils.getModelLocation(family.getBaseBlock()));
        slabBlock((SlabBlock) slab, modelBottom, modelTop, modelDouble);
        simpleBlockItem(slab, modelBottom);
    }

    private void stairs(Block stairs, BlockFamily family) {
        String name = name(stairs);
        ResourceLocation texture = blockTexture(family.getBaseBlock());
        ModelFile model = models().stairs(name, texture, texture, texture);
        ModelFile modelInner = models().stairsInner(name + "_inner", texture, texture, texture);
        ModelFile modelOuter = models().stairsOuter(name + "_outer", texture, texture, texture);
        stairsBlock((StairBlock) stairs, model, modelInner, modelOuter);
        simpleBlockItem(stairs, model);
    }

    private void pressurePlate(Block pressurePlate, BlockFamily family) {
        String name = name(pressurePlate);
        ResourceLocation texture = blockTexture(family.getBaseBlock());
        ModelFile model = models().pressurePlate(name, texture);
        ModelFile modelDown = models().pressurePlateDown(name + "_down", texture);
        pressurePlateBlock((PressurePlateBlock) pressurePlate, model, modelDown);
        simpleBlockItem(pressurePlate, model);
    }

    private void trapdoor(Block trapdoor, BlockFamily family) {
        String name = name(trapdoor);
        ResourceLocation texture = blockTexture(trapdoor);
        ModelFile modelBottom = models().trapdoorOrientableBottom(name + "_bottom", texture);
        ModelFile modelTop = models().trapdoorOrientableTop(name + "_top", texture);
        ModelFile modelOpen = models().trapdoorOrientableOpen(name + "_open", texture);
        trapdoorBlock((TrapDoorBlock) trapdoor, modelBottom, modelTop, modelOpen, true);
        simpleBlockItem(trapdoor, modelBottom);
    }

    private void wall(Block wall, BlockFamily family) {
        String name = name(wall);
        ResourceLocation texture = blockTexture(family.getBaseBlock());
        ModelFile modelPost = models().wallPost(name + "_post", texture);
        ModelFile modelSide = models().wallSide(name + "_side", texture);
        ModelFile modelSideTall = models().wallSideTall(name + "_side_tall", texture);
        ModelFile modelInventory = models().wallInventory(name + "_inventory", texture);
        wallBlock((WallBlock) wall, modelPost, modelSide, modelSideTall);
        simpleBlockItem(wall, modelInventory);
    }

    private String name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    private ResourceLocation suffix(ResourceLocation location, String suffix) {
        return ResourceLocation.fromNamespaceAndPath(location.getNamespace(), location.getPath() + suffix);
    }

}
