package com.ferri.arnus.enderhopper.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class DyeProvider implements ICapabilitySerializable<CompoundTag>{
	
	private Dyeable dye = new Dyeable();
	private LazyOptional<Dyeable> lazy = LazyOptional.of(() -> dye);
	
	public static final Capability<IDyeable> DYEABLE = CapabilityManager.get(new CapabilityToken<>(){});

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == DYEABLE) {
			return lazy.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		return dye.serializeNBT();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		this.dye.deserializeNBT(nbt);
	}

}
