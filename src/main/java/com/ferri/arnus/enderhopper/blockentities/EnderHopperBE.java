package com.ferri.arnus.enderhopper.blockentities;

import com.ferri.arnus.enderhopper.blocks.EnderHopper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class EnderHopperBE extends BlockEntity {
	private HopperStachHandler handler = new HopperStachHandler();
	private LazyOptional<ItemStackHandler> lazy = LazyOptional.of(() -> handler);
	
	public EnderHopperBE(BlockPos p_155550_, BlockState p_155551_) {
		super(BlockEntityRegistry.ENDERHOPPER.get(), p_155550_, p_155551_);
	}
	
	public static void tick(Level level, BlockPos pos, BlockState state, EnderHopperBE hopper) {
		pullItem(level, pos, hopper);
		pushItem(level, pos, state.getValue(EnderHopper.FACING), hopper);
	}
	
	private static void pullItem(Level level, BlockPos pos, EnderHopperBE hopper) {
		BlockEntity be =  level.getBlockEntity(pos.above());
		if (be == null) { return;}
		be.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN).ifPresent(cap -> {
			hopper.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).ifPresent(handler -> {
				ItemStack stack = cap.extractItem(0, cap.getStackInSlot(0).getCount(), false);
				handler.insertItem(0, stack, false);
			});
		});
	}
	
	private static void pushItem(Level level, BlockPos pos, Direction side, EnderHopperBE hopper) {
		BlockEntity be =  level.getBlockEntity(pos.relative(side));
		if (be == null) { return;}
		hopper.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side).ifPresent(cap -> {
			be.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite()).ifPresent(handler -> {
				ItemStack stack = cap.extractItem(0, cap.getStackInSlot(0).getCount(), false);
				handler.insertItem(0, stack, false);
			});
		});
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (this.getBlockState().getValue(EnderHopper.FACING).equals(side) && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && this.getBlockState().getValue(EnderHopper.ENABLED)) {
			return this.lazy.cast();
		}
		if (side.equals(Direction.UP) && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return this.lazy.cast();
		}
		return super.getCapability(cap, side);
	}
	
	class HopperStachHandler extends ItemStackHandler {
		
		public HopperStachHandler() {
			stacks = NonNullList.withSize(5, ItemStack.EMPTY);
		}
		
		@Override
		protected void onContentsChanged(int slot) {
			setChanged();
		}
	}


}
