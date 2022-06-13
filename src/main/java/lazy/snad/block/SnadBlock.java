package lazy.snad.block;

import lazy.snad.register.ModConfigs;
import lazy.snad.register.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import javax.annotation.ParametersAreNonnullByDefault;

public class SnadBlock extends FallingBlock {

	private final int dustColor;

	public SnadBlock(int dustColor, MaterialColor color) {
		super(Properties.of(Material.SAND, color).randomTicks().sound(SoundType.SAND).strength(0.5f));
		this.dustColor = dustColor;
	}

	@Override
	@ParametersAreNonnullByDefault
	public int getDustColor(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return this.dustColor;
	}

	@Override
	@ParametersAreNonnullByDefault
	public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
		if (plantable.getPlantType(world, pos) == PlantType.DESERT) return true;
		else if (plantable.getPlantType(world, pos) == PlantType.BEACH) {
			for (Direction direction : Direction.Plane.HORIZONTAL) {
				boolean isWater = world.getFluidState(pos.relative(direction)).is(FluidTags.WATER);
				boolean isFrostedIce = world.getBlockState(pos.relative(direction)).is(Blocks.FROSTED_ICE);
				if (!isWater && !isFrostedIce) continue;
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	@ParametersAreNonnullByDefault
	public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource random) {
		super.tick(blockState, serverLevel, blockPos, random);
		SnadBlock.snadGrow(serverLevel, blockPos, random);
	}

	public static void snadGrow(ServerLevel serverLevel, BlockPos blockPos, RandomSource random) {
		Block blockAbove = serverLevel.getBlockState(blockPos.above()).getBlock();
		if (blockAbove.defaultBlockState().is(ModTags.VALID_PLANT)) {
			boolean isSameBlockType = true;
			int height = 1;

			while (isSameBlockType) {
				if (blockPos.above(height).getY() < 320) {
					Block nextPlantBlock = serverLevel.getBlockState(blockPos.above(height)).getBlock();
					if (nextPlantBlock.getClass() == blockAbove.getClass()) {
						for (int growthAttempts = 0; growthAttempts < ModConfigs.SPEED.get(); growthAttempts++) {
							if (growthAttempts == 0) {
								//noinspection deprecation
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
