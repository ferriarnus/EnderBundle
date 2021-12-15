package com.ferri.arnus.enderbundle.renderer;

import com.ferri.arnus.enderbundle.components.MyComponents;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;

public class EnderBundleColor implements ItemColor{

	@Override
	public int getColor(ItemStack stack, int i) {
		if (i == 1) {
			return MyComponents.COLOR.get(stack).getColor();
		}
		return 0xffffff;
	}

}
