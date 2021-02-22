package com.lazy.snad;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import java.util.Random;

public class SnadBlock extends FallingBlock {

    private final int color;

    public SnadBlock(int color, MaterialColor materialColor) {
        super(Properties.create(Material.SAND, materialColor).tickRandomly().hardnessAndResistance(0.5f).sound(SoundType.SAND));
        this.color = color;
    }

    @Override
    public int getDustColor(BlockState state, IBlockReader blockReader, BlockPos pos) {
        return this.color;
    }


    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.tick(state, world, pos, random);

        BlockState blockStateAbove = world.getBlockState(pos.up());
        Block blockAbove = blockStateAbove.getBlock();

        if (blockAbove.isIn(Setup.CAN_STAND_ON_SNAD)) {
            int height = 1;
            boolean isSameBlock = true;
            while (isSameBlock) {
                for (int i = 0; i < Configs.SPEED_INCREASE_DEFAULT_VALUE.get(); i++) {
                    if(i == 0){
                        world.getBlockState(pos.up(height)).randomTick(world, pos.up(height), random);
                    }
                }
                height++;
                isSameBlock = world.getBlockState(pos.up(height)).getBlock().getClass() == blockAbove.getClass();
            }
        }
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader blockReader, BlockPos pos, Direction direction, IPlantable iPlantable) {
        final BlockPos plantPos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
        final PlantType plantType = iPlantable.getPlantType(blockReader, plantPos);
        if (plantType == PlantType.DESERT) {
            return true;
        } else if (plantType == PlantType.WATER) {
            return blockReader.getBlockState(pos).getMaterial() == Material.WATER && blockReader.getBlockState(pos) == getDefaultState();
        } else if (plantType == PlantType.BEACH) {
            return ((blockReader.getBlockState(pos.east()).getMaterial() == Material.WATER || blockReader.getBlockState(pos.east()).getValues().containsKey(BlockStateProperties.WATERLOGGED))
                    || (blockReader.getBlockState(pos.west()).getMaterial() == Material.WATER || blockReader.getBlockState(pos.west()).getValues().containsKey(BlockStateProperties.WATERLOGGED))
                    || (blockReader.getBlockState(pos.north()).getMaterial() == Material.WATER || blockReader.getBlockState(pos.north()).getValues().containsKey(BlockStateProperties.WATERLOGGED))
                    || (blockReader.getBlockState(pos.south()).getMaterial() == Material.WATER || blockReader.getBlockState(pos.south()).getValues().containsKey(BlockStateProperties.WATERLOGGED)));
        } else return state.isIn(Setup.CAN_STAND_ON_SNAD);
    }
}
