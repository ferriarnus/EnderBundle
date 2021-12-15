package com.ferri.arnus.enderbundle.blockentity;

import com.ferri.arnus.enderbundle.EnderBundleMain;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.HopperMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EnderHopperBE extends HopperBlockEntity{
	
	public EnderHopperBE(BlockPos blockPos, BlockState blockState) {
		super(blockPos, blockState);
	}
	
	@Override
	protected Component getDefaultName() {
		return new TranslatableComponent(EnderBundleMain.MODID,"container.enderhopper");
	}
	
	@Override
	protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
		return new HopperMenu(i, inventory, this);
	}
	
	@Override
	public BlockEntityType<?> getType() {
		return  BlockEntityRegistry.ENDERHOPPER;
	}
    
}
