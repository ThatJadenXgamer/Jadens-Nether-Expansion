
package net.jadenxgamer.netherexp.registry.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LayerBlock extends Block {

    public static final int MAX_HEIGHT = 8;
    private final int height_impassable;
    public static final IntegerProperty LAYERS = BlockStateProperties.LAYERS; 
    public static final VoxelShape[] SHAPE_BY_LAYER = new VoxelShape[]{
        Shapes.empty(), 
        Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0), 
        Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0), 
        Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0), 
        Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0), 
        Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0), 
        Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0), 
        Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0), 
        Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
    };

    public LayerBlock(Properties properties, int heightImpassable) {
        super(properties);
        this.height_impassable = heightImpassable;
        this.registerDefaultState(this.stateDefinition.any().setValue(LAYERS, 1));
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType pathComputationType) {
        switch (pathComputationType) {
            case LAND:
                return state.getValue(LAYERS) < height_impassable; 
            default:
                return false;
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_LAYER[state.getValue(LAYERS)];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_LAYER[state.getValue(LAYERS)];
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_LAYER[state.getValue(LAYERS)];
    }
    
    @Override
    public VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
        return SHAPE_BY_LAYER[state.getValue(LAYERS)];
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getValue(LAYERS) == 8 ? 0.2F : 1.0F;
    }
   
    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, state, level, pos, neighborPos);
    }
    
    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context
    ) {
        int layers = state.getValue(LAYERS);
        if (context.getItemInHand().is(this.asItem()) && layers < 8) {
            if (context.replacingClickedOnBlock()) {
                return context.getClickedFace() == Direction.UP;
            }
            return true;
        } 
        return layers == 1;
    }
    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());
        if (state.is(this)) {
            int layers = state.getValue(LAYERS);
            return state.setValue(LAYERS, Math.min(8, layers + 1));
        }
        return super.getStateForPlacement(context);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(LAYERS);
    }

}
