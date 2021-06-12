package lazy.snad.mixin;

import lazy.snad.register.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CactusBlock.class)
public class InjectCactusCanSurvive {

    @Inject(method = "canSurvive", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    public void canSurvive(BlockState state, LevelReader levelReader, BlockPos blockPos, CallbackInfoReturnable<Boolean> info) {
        if (levelReader.getBlockState(blockPos.below()).is(ModTags.SNAD)) info.setReturnValue(true);
    }
}
