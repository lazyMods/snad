package lazy.snad.mixin;

import lazy.snad.register.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(CactusBlock.class)
public class MixinCactusBlock {

    /**
     * @author lazy
     * TODO: Redo this. Overwriting the method may cause mod compability issues
     */
    @Overwrite
    public boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos blockPos) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState blockState2 = levelReader.getBlockState(blockPos.relative(direction));
            Material material = blockState2.getMaterial();
            if (!material.isSolid() && !levelReader.getFluidState(blockPos.relative(direction)).is(FluidTags.LAVA))
                continue;
            return false;
        }
        BlockState blockState3 = levelReader.getBlockState(blockPos.below());

        boolean isSnad = blockState3.is(ModBlocks.RED_SNAD.get()) || blockState3.is(ModBlocks.SNAD.get());

        return (isSnad || blockState3.is(Blocks.CACTUS) || blockState3.is(Blocks.SAND) || blockState3.is(Blocks.RED_SAND)) && !levelReader.getBlockState(blockPos.above()).getMaterial().isLiquid();
    }
}
