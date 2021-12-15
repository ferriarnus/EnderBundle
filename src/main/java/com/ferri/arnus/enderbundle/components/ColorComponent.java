package com.ferri.arnus.enderbundle.components;

import dev.onyxstudios.cca.api.v3.item.ItemComponent;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public class ColorComponent extends ItemComponent{

	public ColorComponent(ItemStack stack) {
		super(stack);
		setColor(DyeColor.GREEN.getMaterialColor().col);
	}
	
	public int getColor() {
		return getInt("color");
	}
	
	public void setColor(int color) {
		putInt("color", color);
	}

}
