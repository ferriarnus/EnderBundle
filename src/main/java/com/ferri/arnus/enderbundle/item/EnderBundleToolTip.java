package com.ferri.arnus.enderbundle.item;

import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

public class EnderBundleToolTip implements TooltipComponent{

	private NonNullList<ItemStack> items;

	public EnderBundleToolTip(NonNullList<ItemStack> pItems) {
		this.items = pItems;
	}
	
	public NonNullList<ItemStack> getItems() {
		return items;
	}

}
