package net.jadenxgamer.netherexp.core.block.interfaces;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

//TODO: Move this shiii to ElysiumAPI
public interface IElysiumBlockExtension {
    /**
     * Face-aware version of {@link net.neoforged.neoforge.common.extensions.IBlockExtension#canStickTo(BlockState, BlockState)}.
     *
     * @param selfState   the state of the block being queried
     * @param otherState   the state of the adjacent block
     * @param selfFace    the face on selfState that is touching the other block
     * @param otherFace   the face on otherState that is touching selfState
     */
    default boolean canStickToFace(BlockState selfState, BlockState otherState, Direction selfFace, Direction otherFace) {
        return selfState.canStickTo(otherState);
    }
}
