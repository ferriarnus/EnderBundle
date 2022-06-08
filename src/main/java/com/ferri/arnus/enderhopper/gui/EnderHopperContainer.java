package com.ferri.arnus.enderhopper.gui;

import com.ferri.arnus.enderhopper.blockentities.EnderHopperBE;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class EnderHopperContainer extends AbstractContainerMenu{

	
	public EnderHopperContainer(final int windowId, Inventory playerInventory, final FriendlyByteBuf data) {
		this(windowId, playerInventory.player.level, data.readBlockPos() ,playerInventory);
	}
	

	public EnderHopperContainer(final int windowId, Level world, BlockPos pos, Inventory playerInventory) {
		super(ContainerRegistry.ENDER_HOPPER.get(), windowId);
		EnderHopperBE hopper = (EnderHopperBE) world.getBlockEntity(pos);
		addSlot(new FixedSlotItemHandler(hopper.getHandler(), 0, 44, 20));
		addSlot(new FixedSlotItemHandler(hopper.getHandler(), 1, 62, 20));
		addSlot(new FixedSlotItemHandler(hopper.getHandler(), 2, 80, 20));
		addSlot(new FixedSlotItemHandler(hopper.getHandler(), 3, 98, 20));
		addSlot(new FixedSlotItemHandler(hopper.getHandler(), 4, 116, 20));
		
		this.bindPlayerInventory(new InvWrapper(playerInventory));
		
	}

	@Override
	public boolean stillValid(Player pPlayer) {
		return true;
	}
	
	@Override
	public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(pIndex);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (pIndex < 5) {
				if (!this.moveItemStackTo(itemstack1, 5, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemstack1, 0, 5, false)) {
				return ItemStack.EMPTY;
			}
			
			if (itemstack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
		}
		
		return itemstack;
	}
	
	private void bindPlayerInventory(IItemHandler inventory) {
		for(int l = 0; l < 3; ++l) {
			for(int k = 0; k < 9; ++k) {
				this.addSlot(new FixedSlotItemHandler(inventory, k + l * 9 + 9, 8 + k * 18, l * 18 + 51));
			}
		}
		
		for(int i1 = 0; i1 < 9; ++i1) {
			this.addSlot(new FixedSlotItemHandler(inventory, i1, 8 + i1 * 18, 109));
		}
	}

}
