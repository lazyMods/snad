package lazy.snad.mixin;

import lazy.snad.register.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(SugarCaneBlock.class)
public class MixinSugarCaneBlock {

    /**
     * @author lazy
     * TODO: Redo this. Overwriting the method may cause mod compability issues
     */
    @Overwrite
    public boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos blockPos) {
        BlockState blockState2 = levelReader.getBlockState(blockPos.below());
        if (blockState2.getBlock() == Blocks.SUGAR_CANE) {
            return true;
        }

        boolean isSnad = blockState2.is(ModBlocks.RED_SNAD.get()) || blockState2.is(ModBlocks.SNAD.get());

        if (isSnad || blockState2.is(Blocks.GRASS_BLOCK) || blockState2.is(Blocks.DIRT) || blockState2.is(Blocks.COARSE_DIRT) || blockState2.is(Blocks.PODZOL) || blockState2.is(Blocks.SAND) || blockState2.is(Blocks.RED_SAND)) {
            BlockPos blockPos2 = blockPos.below();
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockState blockState3 = levelReader.getBlockState(blockPos2.relative(direction));
                FluidState fluidState = levelReader.getFluidState(blockPos2.relative(direction));
                if (!fluidState.is(FluidTags.WATER) && !blockState3.is(Blocks.FROSTED_ICE)) continue;
                return true;
            }
        }
        return false;
    }
}
