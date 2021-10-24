package com.ferri.arnus.enderhopper.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IDyeable extends INBTSerializable<CompoundTag>{

	int getColour();
	void setColour(int colour);
}
