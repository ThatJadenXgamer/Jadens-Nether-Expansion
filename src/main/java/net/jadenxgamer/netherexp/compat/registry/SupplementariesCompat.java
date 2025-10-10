package net.jadenxgamer.netherexp.compat.registry;

import net.jadenxgamer.elysium_api.api.reflection.ElysiumReflection;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

import static net.jadenxgamer.netherexp.util.RegistryHelper.registerBlockWithoutItem;

public class SupplementariesCompat {

    public static final Supplier<Block> SCONCE_ANCIENT = registerBlockWithoutItem("sconce_ancient", () ->
            ElysiumReflection.createBlock("net.mehvahdjukaar.supplementaries.common.block.blocks.SconceBlock",
                    BlockBehaviour.Properties.of().noCollission().noOcclusion().pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.LANTERN),
                    9, JNEParticleTypes.TREACHEROUS_FLAME.get()));

    public static final Supplier<Block> SCONCE_WALL_ANCIENT = registerBlockWithoutItem("sconce_wall_ancient", () ->
            ElysiumReflection.createBlock("net.mehvahdjukaar.supplementaries.common.block.blocks.SconceWallBlock",
                    BlockBehaviour.Properties.ofLegacyCopy(SCONCE_ANCIENT.get()),
                    9, JNEParticleTypes.TREACHEROUS_FLAME.get()));

    public static final Supplier<Item> SCONCE_ITEM_ANCIENT = JNEItems.ITEMS.register("sconce_ancient", () ->
            new StandingAndWallBlockItem(SCONCE_ANCIENT.get(), SCONCE_WALL_ANCIENT.get(), new Item.Properties(), Direction.DOWN));

    public static void init() {

    }
}
