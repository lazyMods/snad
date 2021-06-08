package lazy.snad.block;

import lazy.snad.Snad;
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

        BlockState blockStateAbove = serverLevel.getBlockState(blockPos.above());
        Block blockAbove = blockStateAbove.getBlock();

        int height = 1;
        boolean isSameBlock = true;
        while (isSameBlock) {
            for (int i = 0; i < Snad.configs.getSpeed(); i++) {
                if (i == 0) {
                    serverLevel.getBlockState(blockPos.above(height)).randomTick(serverLevel, blockPos.above(height), random);
                }
            }
            height++;
            isSameBlock = serverLevel.getBlockState(blockPos.above(height)).getBlock().getClass() == blockAbove.getClass();
        }
    }
}
