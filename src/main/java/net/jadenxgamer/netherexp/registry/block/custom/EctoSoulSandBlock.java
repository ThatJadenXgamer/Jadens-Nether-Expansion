package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.block.entity.JNEBrushableBlockEntity;
import net.jadenxgamer.netherexp.registry.entity.JNEEntityTypes;
import net.jadenxgamer.netherexp.registry.entity.custom.WispEntity;
import net.jadenxgamer.netherexp.registry.misc_registry.JNELootTables;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class EctoSoulSandBlock
extends Block
{
    protected static final VoxelShape COLLISION_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0);

    public static final BooleanProperty SPAWNS_WISPS = BooleanProperty.of("spawns_wisps");

    public EctoSoulSandBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(SPAWNS_WISPS, false));
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(SPAWNS_WISPS);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i = random.nextInt(NetherExp.getConfig().blocks.ectoSoulSandConfigs.wisp_emerging_chances);
        if (i == 0) {
            this.setSoulSand(world, pos, random);
            if (world.getBlockState(pos.up()).isAir() ) {
                this.spawnWisp(world, pos.up(), random);
            }
            else if (world.getBlockState(pos.north()).isAir() ) {
                this.spawnWisp(world, pos.north(), random);
            }
            else if (world.getBlockState(pos.east()).isAir() ) {
                this.spawnWisp(world, pos.east(), random);
            }
            else if (world.getBlockState(pos.south()).isAir() ) {
                this.spawnWisp(world, pos.south(), random);
            }
            else if (world.getBlockState(pos.west()).isAir() ) {
                this.spawnWisp(world, pos.west(), random);
            }
            else if (world.getBlockState(pos.down()).isAir() ) {
                this.spawnWisp(world, pos.down(), random);
            }
        }
    }

    private void setSoulSand(World world, BlockPos pos, Random random) {
        int i = random.nextInt(10);
        world.setBlockState(pos, JNEBlocks.SUSPICIOUS_SOUL_SAND.getDefaultState(), NOTIFY_LISTENERS);
        JNEBrushableBlockEntity.setLootTable(world, random, pos, JNELootTables.ARCHAEOLOGY_SOUL_SAND_VALLEY);
    }

    private void spawnWisp(ServerWorld world, BlockPos pos, Random random) {
        WispEntity wispEntity = JNEEntityTypes.WISP.create(world);
        if (wispEntity != null) {
            wispEntity.setBoredDelay(random.nextInt(1000) + 600);
            wispEntity.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            world.spawnEntity(wispEntity);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SPAWNS_WISPS);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return COLLISION_SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.fullCube();
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.fullCube();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 0.2f;
    }
}
