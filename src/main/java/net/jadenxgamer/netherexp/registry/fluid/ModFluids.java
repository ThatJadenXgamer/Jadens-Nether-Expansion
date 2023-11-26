package net.jadenxgamer.netherexp.registry.fluid;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.block.Block;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

@SuppressWarnings("deprecation")
public class ModFluids {

    public static FlowableFluid ECTOPLASM;
    public static FlowableFluid FLOWING_ECTOPLASM;
    public static Block ECTOPLASM_BLOCK;
    public static Item ECTOPLASM_BUCKET;

    public static void registerModFluids() {
        ECTOPLASM = Registry.register(Registries.FLUID, new Identifier(NetherExp.MOD_ID, "ectoplasm"), new EctoplasmFluid.Still());

        FLOWING_ECTOPLASM = Registry.register(Registries.FLUID, new Identifier(NetherExp.MOD_ID, "flowing_ectoplasm"), new EctoplasmFluid.Flowing());

        ECTOPLASM_BLOCK = Registry.register(Registries.BLOCK, new Identifier(NetherExp.MOD_ID, "ectoplasm"),
                new FluidBlock(ModFluids.ECTOPLASM, FabricBlockSettings.of().mapColor(MapColor.LIGHT_BLUE).replaceable().noCollision().strength(100.0F).pistonBehavior(PistonBehavior.DESTROY).dropsNothing().luminance((state) -> 15).liquid().sounds(BlockSoundGroup.INTENTIONALLY_EMPTY)));

        ECTOPLASM_BUCKET = Registry.register(Registries.ITEM, new Identifier(NetherExp.MOD_ID, "ectoplasm_bucket"), new BucketItem(ModFluids.ECTOPLASM,
                new FabricItemSettings().recipeRemainder(Items.BUCKET).maxCount(1)));

        NetherExp.LOGGER.debug("Registering Fluids for " + NetherExp.MOD_ID);
    }
}
