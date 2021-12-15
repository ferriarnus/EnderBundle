package com.ferri.arnus.enderbundle.block;

import com.ferri.arnus.enderbundle.blockentity.BlockEntityRegistry;
import com.ferri.arnus.enderbundle.blockentity.EnderHopperBE;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class EnderHopper extends HopperBlock{

	public EnderHopper(Properties settings) {
		super(settings);
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return BlockEntityRegistry.ENDERHOPPER.create(blockPos, blockState);
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState,
			BlockEntityType<T> blockEntityType) {
		return level.isClientSide ? null : (l, pos, state, t) -> EnderHopperBE.pushItemsTick(l, pos, state, (EnderHopperBE) t);

	}

}
