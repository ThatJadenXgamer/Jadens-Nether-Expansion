package net.jadenxgamer.netherexp.registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.fluid.EctoplasmFluidType;
import net.jadenxgamer.netherexp.core.fluid.EctoplasmLiquidBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

@SuppressWarnings({"deprecation", "unused"})
public class JNEFluids {

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, NetherExp.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, NetherExp.MOD_ID);

    /**
     * Ectoplasm
     */

    public static final Supplier<FluidType> ECTOPLASM_TYPE = FLUID_TYPES.register("ectoplasm", () ->
            new EctoplasmFluidType(FluidType.Properties.create().motionScale(1).lightLevel(12).temperature(-196).viscosity(0).canConvertToSource(true)
                    .canPushEntity(false).supportsBoating(true).canDrown(false).fallDistanceModifier(1.0f).canSwim(false)
                    .canExtinguish(true).addDripstoneDripping(0.3f, JNEParticleTypes.DRIPPING_ECTOPLASM.get(), JNEBlocks.ECTOPLASM_CAULDRON.get(), JNESoundEvents.DRIP_ECTOPLASM_INTO_CAULDRON.get())
                    .descriptionId("fluid.netherexp.ectoplasm").pathType(PathType.DAMAGE_OTHER))
            {
                @Override
                public void setItemMovement(ItemEntity entity) {
                    if (!entity.isNoGravity()) entity.setDeltaMovement(entity.getDeltaMovement().add(0.0d, -0.04d, 0.0d));
                }
            });

    public static final DeferredHolder<Fluid, FlowingFluid> ECTOPLASM_SOURCE = FLUIDS.register("ectoplasm", () -> new BaseFlowingFluid.Source(ectoplasmProperties()));
    public static final DeferredHolder<Fluid, FlowingFluid> ECTOPLASM_FLOWING = FLUIDS.register("flowing_ectoplasm", () -> new BaseFlowingFluid.Flowing(ectoplasmProperties()));
    public static final Supplier<LiquidBlock> ECTOPLASM = JNEBlocks.BLOCKS.register("ectoplasm", () ->
            new EctoplasmLiquidBlock(ECTOPLASM_SOURCE.get(), BlockBehaviour.Properties.ofLegacyCopy(Blocks.WATER).lightLevel((state) -> 12).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final Supplier<Item> ECTOPLASM_BUCKET = JNEItems.ITEMS.register("ectoplasm_bucket", () ->
            new BucketItem(ECTOPLASM_SOURCE.get(), new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET)));

    public static void init(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);
    }

    private static BaseFlowingFluid.Properties ectoplasmProperties() {
        return new BaseFlowingFluid.Properties(ECTOPLASM_TYPE, ECTOPLASM_SOURCE, ECTOPLASM_FLOWING).bucket(ECTOPLASM_BUCKET).block(ECTOPLASM).explosionResistance(100.0f);
    }

    public static void setup() {
        // interactions added this way have a smoke particle and lava extinguish sound, which obviously would sound like shit with black ice
//        FluidInteractionRegistry.addInteraction(ECTOPLASM_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
//                NeoForgeMod.WATER_TYPE.value(), (fluidState) -> JNEBlocks.BLACK_ICE.get().defaultBlockState()
//        ));
    }
}
