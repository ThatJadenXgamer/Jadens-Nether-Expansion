package net.jadenxgamer.netherexp.forge;

import dev.architectury.platform.forge.EventBuses;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.NetherExpClient;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.entity.custom.Apparition;
import net.jadenxgamer.netherexp.registry.entity.custom.Vessel;
import net.jadenxgamer.netherexp.registry.entity.custom.Wisp;
import net.jadenxgamer.netherexp.registry.item.brewing.JNEPotionRecipe;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(NetherExp.MOD_ID)
public class NetherExpForge {
    public NetherExpForge() {
        EventBuses.registerModEventBus(NetherExp.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        NetherExp.init();

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> NetherExpClient::init);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> NetherExpForgeClient::init);

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(NetherExpForge::commonSetup);
        MinecraftForge.EVENT_BUS.addListener(NetherExpForge::rightClickBlock);
        eventBus.addListener(NetherExpForge::registerAttributes);
    }

    public static void rightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockState state = level.getBlockState(event.getPos());
        BlockState aboveState = level.getBlockState(event.getPos().above());
        ItemStack stack = event.getItemStack();
        Player player = event.getEntity();
        if (stack.is(ItemTags.SHOVELS) && aboveState.isAir()) {
            event.setUseBlock(Event.Result.ALLOW);
            if (state.is(Blocks.CRIMSON_NYLIUM)) {
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0f, 1.0f);
                level.setBlock(event.getPos(), JNEBlocks.CRIMSON_NYLIUM_PATH.get().defaultBlockState(), 2);
                if (!player.isCreative()) {
                    stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(event.getHand()));
                }
            }
            else if (state.is(Blocks.WARPED_NYLIUM)) {
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0f, 1.0f);
                level.setBlock(event.getPos(), JNEBlocks.WARPED_NYLIUM_PATH.get().defaultBlockState(), 2);
                if (!player.isCreative()) {
                    stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(event.getHand()));
                }
            }
        }
        else if (stack.is(ItemTags.AXES)) {
            event.setUseBlock(Event.Result.ALLOW);
            if (state.is(JNEBlocks.CLARET_STEM.get())) {
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);
                level.setBlock(event.getPos(), JNEBlocks.STRIPPED_CLARET_STEM.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)), 2);
                if (!player.isCreative()) {
                    stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(event.getHand()));
                }
            }
            else if (state.is(JNEBlocks.CLARET_HYPHAE.get())) {
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);
                level.setBlock(event.getPos(), JNEBlocks.STRIPPED_CLARET_HYPHAE.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)), 2);
                if (!player.isCreative()) {
                    stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(event.getHand()));
                }
            }
            else if (state.is(JNEBlocks.SMOKESTALK_BLOCK.get())) {
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);
                level.setBlock(event.getPos(), JNEBlocks.STRIPPED_SMOKESTALK_BLOCK.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)), 2);
                if (!player.isCreative()) {
                    stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(event.getHand()));
                }
            }
        }
    }

    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(JNEPotionRecipe::addInvokerPotionRecipes);
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(JNEEntityType.APPARITION.get(), Apparition.createAttributes().build());
        event.put(JNEEntityType.WISP.get(), Wisp.createAttributes().build());
        event.put(JNEEntityType.VESSEL.get(), Vessel.createAttributes().build());
    }
}
