package com.ferri.arnus.enderhopper.capability;

import net.minecraft.nbt.CompoundTag;

public class Dyeable implements IDyeable{
	
	private int colour;

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		nbt.putInt("colour", this.colour);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		this.colour = nbt.getInt("colour");
		
	}

	@Override
	public int getColour() {
		return this.colour;
	}

	@Override
	public void setColour(int colour) {
		this.colour = colour;
	}

}
