package lazy.snad.block;

import lazy.snad.Snad;
import lazy.snad.register.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.Random;

public class SnadBlock extends FallingBlock {

    private final int dustColor;

    public SnadBlock(int dustColor, MaterialColor color) {
        super(Properties.of(Material.SAND, color).randomTicks().sound(SoundType.SAND).strength(0.5f));
        this.dustColor = dustColor;
    }

    @Override
    public int getDustColor(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return this.dustColor;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random random) {
        super.tick(blockState, serverLevel, blockPos, random);

        Block blockAbove = serverLevel.getBlockState(blockPos.above()).getBlock();

        if (blockAbove.is(ModTags.VALID_PLANT)) {
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
