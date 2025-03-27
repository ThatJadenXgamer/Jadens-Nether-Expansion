package net.jadenxgamer.netherexp.registry.fluid;

import net.jadenxgamer.elysium_api.api.util.ResourceKeyRegistryHelper;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.util.CompatUtil;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.block.custom.EctoplasmLiquidBlock;
import net.jadenxgamer.netherexp.registry.fluid.custom.EctoplasmFluidType;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JNEFluids {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, NetherExp.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, NetherExp.MOD_ID);

    private static ForgeFlowingFluid.Properties ectoplasmProperties() {
        return new ForgeFlowingFluid.Properties(ECTOPLASM_TYPE, ECTOPLASM_SOURCE, ECTOPLASM_FLOWING).bucket(ECTOPLASM_BUCKET).block(ECTOPLASM).explosionResistance(100.0f);
    }

    public static final RegistryObject<FluidType> ECTOPLASM_TYPE = FLUID_TYPES.register("ectoplasm", () ->
        new EctoplasmFluidType(FluidType.Properties.create().lightLevel(13).density(1).viscosity(0)
                .canPushEntity(false).canSwim(false).canConvertToSource(true).canDrown(false)
                .pathType(BlockPathTypes.WATER).supportsBoating(true).temperature(-196)
                .canExtinguish(false).pathType(BlockPathTypes.DANGER_POWDER_SNOW).adjacentPathType(BlockPathTypes.DANGER_OTHER)
                .canHydrate(false)));

    public static final RegistryObject<FlowingFluid> ECTOPLASM_SOURCE = FLUIDS.register("ectoplasm", () ->
            new ForgeFlowingFluid.Source(ectoplasmProperties()));
    public static final RegistryObject<FlowingFluid> ECTOPLASM_FLOWING = FLUIDS.register("flowing_ectoplasm", () ->
            new ForgeFlowingFluid.Flowing(ectoplasmProperties()));
    public static final RegistryObject<Item> ECTOPLASM_BUCKET = JNEItems.ITEMS.register("ectoplasm_bucket", () ->
            new BucketItem(JNEFluids.ECTOPLASM_SOURCE, new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET)));
    public static final RegistryObject<LiquidBlock> ECTOPLASM = JNEBlocks.BLOCKS.register("ectoplasm", () ->
            new EctoplasmLiquidBlock(ECTOPLASM_SOURCE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).replaceable().noCollission().strength(100.0F).pushReaction(PushReaction.DESTROY).lightLevel((state) -> 7).noLootTable().liquid().sound(SoundType.EMPTY)));


    public static void init(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);
    }

    public static void initFluidInteractions() {
        FluidInteractionRegistry.addInteraction(ECTOPLASM_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                ForgeMod.WATER_TYPE.get(), state -> JNEBlocks.BLACK_ICE.get().defaultBlockState()));
        if (CompatUtil.checkAlexsCaves()) {
            FluidInteractionRegistry.addInteraction(ECTOPLASM_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                ResourceKeyRegistryHelper.getFluidType(new ResourceLocation(CompatUtil.ALEXS_CAVES, "purple_soda")), state -> ResourceKeyRegistryHelper.getBlock(new ResourceLocation(CompatUtil.ALEXS_CAVES, "rock_candy_light_blue")).defaultBlockState()));
        }
    }
}
