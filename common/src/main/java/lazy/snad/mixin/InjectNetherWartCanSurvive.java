package lazy.snad.mixin;

import lazy.snad.register.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NetherWartBlock.class)
public class InjectNetherWartCanSurvive {

    @Inject(method = "mayPlaceOn", at = @At(value = "HEAD"), cancellable = true)
    public void mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CallbackInfoReturnable<Boolean> infoReturnable) {
        if (blockState.is(ModBlocks.SOUL_SNAD.get())) infoReturnable.setReturnValue(true);
    }
}