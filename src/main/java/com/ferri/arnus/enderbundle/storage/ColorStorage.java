package com.ferri.arnus.enderbundle.storage;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public class ColorStorage {

	private ItemStack stack;


	public ColorStorage(ItemStack stack) {
		this.stack = stack;
	}
	
	public int getColor() {
		CompoundTag tag = stack.getOrCreateTag().getCompound("colorstorage");
		if (!tag.contains("color")) {
			CompoundTag tag2 = new CompoundTag();
			tag2.putInt("color", DyeColor.LIME.getMaterialColor().col);
			stack.getOrCreateTag().put("colorstorage", tag2);
		}
		return tag.getInt("color");
	}
	
	public void setColor(int color) {
		CompoundTag tag = new CompoundTag();
		tag.putInt("color", color);
		stack.getOrCreateTag().put("colorstorage", tag);
	}

}
