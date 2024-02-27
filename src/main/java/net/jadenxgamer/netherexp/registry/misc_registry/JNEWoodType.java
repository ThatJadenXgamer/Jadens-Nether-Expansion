package net.jadenxgamer.netherexp.registry.misc_registry;

import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.sound.JNESoundEvents;
import net.minecraft.block.WoodType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class JNEWoodType {
    public static final WoodType CLARET = new WoodTypeBuilder().soundGroup(BlockSoundGroup.NETHER_WOOD).hangingSignSoundGroup(BlockSoundGroup.NETHER_WOOD_HANGING_SIGN).fenceGateCloseSound(SoundEvents.BLOCK_NETHER_WOOD_FENCE_GATE_CLOSE).fenceGateOpenSound(SoundEvents.BLOCK_NETHER_WOOD_FENCE_GATE_OPEN).register(new Identifier(NetherExp.MOD_ID, "claret"), JNEBlockSetType.CLARET);
    public static final WoodType SMOKESTALK = new WoodTypeBuilder().soundGroup(JNESoundEvents.SMOKESTALK_WOOD).hangingSignSoundGroup(BlockSoundGroup.BAMBOO_WOOD_HANGING_SIGN).fenceGateCloseSound(SoundEvents.BLOCK_BAMBOO_WOOD_FENCE_GATE_CLOSE).fenceGateOpenSound( SoundEvents.BLOCK_BAMBOO_WOOD_FENCE_GATE_OPEN).register(new Identifier(NetherExp.MOD_ID, "smokestalk"), JNEBlockSetType.SMOKESTALK);

    public static void registerWoodType() {
        NetherExp.LOGGER.debug("Registering WoodType for " + NetherExp.MOD_ID);
    }
}
