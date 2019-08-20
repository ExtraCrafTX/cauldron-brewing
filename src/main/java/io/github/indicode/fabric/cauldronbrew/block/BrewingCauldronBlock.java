package io.github.indicode.fabric.cauldronbrew.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.BooleanBiFunction;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

/**
 * @author Indigo Amann
 */
public class BrewingCauldronBlock extends BlockWithEntity {
    private static final VoxelShape RAY_TRACE_SHAPE;
    protected static final VoxelShape OUTLINE_SHAPE;
    public BrewingCauldronBlock() {
        super(FabricBlockSettings.of(Material.METAL, MaterialColor.STONE).strength(2.0F, 2.0F).build());
    }

    public VoxelShape getOutlineShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, EntityContext entityContext_1) {
        return OUTLINE_SHAPE;
    }

    public boolean isOpaque(BlockState blockState_1) {
        return false;
    }

    public VoxelShape getRayTraceShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
        return RAY_TRACE_SHAPE;
    }
    public BlockRenderType getRenderType(BlockState blockState_1) {
        return BlockRenderType.MODEL;
    }

    public boolean activate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1) {
        BlockEntity blockEntity_1 = world_1.getBlockEntity(blockPos_1);
        System.out.println("HAS BE: " + (blockEntity_1 instanceof BrewingCauldronBlockEntity));
        if (blockEntity_1 instanceof BrewingCauldronBlockEntity) {
            return ((BrewingCauldronBlockEntity) blockEntity_1).activate(blockState_1, world_1, blockPos_1, playerEntity_1, hand_1, blockHitResult_1);
        }
        return false;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new BrewingCauldronBlockEntity();
    }
    static {
        RAY_TRACE_SHAPE = createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);
        OUTLINE_SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.union(createCuboidShape(0.0D, 0.0D, 4.0D, 16.0D, 3.0D, 12.0D), new VoxelShape[]{createCuboidShape(4.0D, 0.0D, 0.0D, 12.0D, 3.0D, 16.0D), createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D), RAY_TRACE_SHAPE}), BooleanBiFunction.ONLY_FIRST);
    }
}
