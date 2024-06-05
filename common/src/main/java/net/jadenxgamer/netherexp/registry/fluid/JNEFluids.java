package net.jadenxgamer.netherexp.registry.fluid;

import dev.architectury.core.fluid.ArchitecturyFlowingFluid;
import dev.architectury.core.fluid.ArchitecturyFluidAttributes;
import dev.architectury.core.fluid.SimpleArchitecturyFluidAttributes;
import dev.architectury.core.item.ArchitecturyBucketItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.block.custom.EctoplasmLiquidBlock;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.*;

public class JNEFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(NetherExp.MOD_ID, Registries.FLUID);

    public static final RegistrySupplier<FlowingFluid> ECTOPLASM = FLUIDS.register("ectoplasm", () ->
            new ArchitecturyFlowingFluid.Source(JNEFluids.ECTOPLASM_ATTRIBUTE){
                @Override
                protected void animateTick(Level level, BlockPos pos, FluidState state, RandomSource random) {
                    ectoplasmParticles(level, pos, random);
                }
            });
    public static final RegistrySupplier<FlowingFluid> FLOWING_ECTOPLASM = FLUIDS.register("flowing_ectoplasm", () ->
            new ArchitecturyFlowingFluid.Flowing(JNEFluids.ECTOPLASM_ATTRIBUTE));

    public static final RegistrySupplier<LiquidBlock> ECTOPLASM_BLOCK = JNEBlocks.BLOCKS.register("ectoplasm", () ->
            new EctoplasmLiquidBlock(JNEFluids.ECTOPLASM, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).replaceable().noCollission().strength(100.0F).pushReaction(PushReaction.DESTROY).lightLevel((state) -> 15).noLootTable().liquid().sound(SoundType.EMPTY)));

    public static final RegistrySupplier<Item> ECTOPLASM_BUCKET = JNEItems.ITEMS.register("ectoplasm_bucket", () ->
            new ArchitecturyBucketItem(JNEFluids.ECTOPLASM, new Item.Properties()));

    public static final ArchitecturyFluidAttributes ECTOPLASM_ATTRIBUTE = SimpleArchitecturyFluidAttributes.ofSupplier(() -> JNEFluids.FLOWING_ECTOPLASM, () -> JNEFluids.ECTOPLASM)
            .convertToSource(true).slopeFindDistance(4).dropOff(1).tickDelay(5).explosionResistance(100.0f)
            .luminosity(15).density(0).temperature(50).viscosity(1000).lighterThanAir(false)
            .sourceTexture(new ResourceLocation("netherexp:block/ectoplasm_still"))
            .flowingTexture(new ResourceLocation("netherexp:block/ectoplasm_flow"))
            .overlayTexture(new ResourceLocation("netherexp:textures/block/ectoplasm_overlay.png"))
            .color(16777215).rarity(Rarity.COMMON)
            .blockSupplier(() -> JNEFluids.ECTOPLASM_BLOCK)
            .bucketItemSupplier(() -> JNEFluids.ECTOPLASM_BUCKET);


    // FLUID BEHAVIORS
    /*
    *  For whatever reason ArchitecturyFlowingFluid cannot be extended
    *  meaning we're forced to use this janky cursed way of adding particles. no clue why
    */
    private static void ectoplasmParticles(Level level, BlockPos pos, RandomSource random) {
        BlockPos abovePos = pos.above();
        if (level.getBlockState(abovePos).isAir() && !level.getBlockState(abovePos).isSolidRender(level, abovePos)) {
            if (random.nextInt(55) == 0) {
                double d = (double) pos.getX() + random.nextDouble();
                double e = (double) pos.getY() + 1.0;
                double f = (double) pos.getZ() + random.nextDouble();
                level.addParticle(JNEParticleTypes.ECTORAYS.get(), d, e, f, 0.0, -0.03, 0.0);
            }
            if (random.nextInt(18) == 0) {
                double d = (double) pos.getX() + random.nextDouble();
                double e = (double) pos.getY() + 1.0;
                double f = (double) pos.getZ() + random.nextDouble();
                level.addParticle(JNEParticleTypes.ECTOPLASMA.get(), d, e, f, 0.0, 0.0, 0.0);
            }
            if (random.nextInt(300) == 0) {
                level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.ECTOPLASM_WHISPERING.get(), SoundSource.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, false);
            }
        }
    }

    public static void init() {
        FLUIDS.register();
    }
}
