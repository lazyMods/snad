package lazy.snad.mixin;

import lazy.snad.register.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SugarCaneBlock.class)
public class InjectSugarCaneCanSurvive {

    @Inject(method = "canSurvive", at = @At(value = "HEAD"), cancellable = true)
    public void canSurvive(BlockState state, LevelReader levelReader, BlockPos blockPos, CallbackInfoReturnable<Boolean> infoReturnable) {
        if (levelReader.getBlockState(blockPos.below()).is(ModTags.SNAD)) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                boolean isWater = levelReader.getFluidState(blockPos.below().relative(direction)).is(FluidTags.WATER);
                boolean isFrostedIce = levelReader.getBlockState(blockPos.below().relative(direction)).is(Blocks.FROSTED_ICE);
                if (!isWater && !isFrostedIce) continue;
                infoReturnable.setReturnValue(true);
            }
        }
    }
}
