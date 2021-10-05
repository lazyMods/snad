package lazy.snad.block;

import lazy.snad.Snad;
import lazy.snad.register.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class SoulSnadBlock extends Block {

    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0);

    public SoulSnadBlock() {
        super(Properties.of(Material.SAND).randomTicks().sound(SoundType.SAND).strength(0.5f));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return SHAPE;
    }

    @Override
    public VoxelShape getVisualShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random random) {
        super.tick(blockState, serverLevel, blockPos, random);

        Block blockAbove = serverLevel.getBlockState(blockPos.above()).getBlock();

        if (blockAbove.defaultBlockState().is(ModTags.VALID_PLANT)) {
            boolean isSameBlockType = true;
            int height = 1;

            while (isSameBlockType) {
                if (serverLevel.getBlockState(blockPos.above(height)).getBlock() != null) {
                    Block nextPlantBlock = serverLevel.getBlockState(blockPos.above(height)).getBlock();
                    if (nextPlantBlock.getClass() == blockAbove.getClass()) {
                        for (int growthAttempts = 0; growthAttempts < Snad.configs.getSpeed();
                             growthAttempts++) {
                            if (growthAttempts == 0) {
                                nextPlantBlock.randomTick(serverLevel.getBlockState(blockPos.above(height)), serverLevel, blockPos.above(height), random);
                            }
                        }
                        height++;
                    } else {
                        isSameBlockType = false;
                    }
                } else {
                    isSameBlockType = false;
                }
            }
        }
    }
}