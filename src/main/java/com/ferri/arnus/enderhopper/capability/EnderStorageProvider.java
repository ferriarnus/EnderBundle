package com.ferri.arnus.enderhopper.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class EnderStorageProvider implements ICapabilitySerializable<CompoundTag>{
	
	private EnderStorage storage = new EnderStorage();
	private LazyOptional<EnderStorage> lazy = LazyOptional.of(() -> storage);
	
	public static final Capability<IEnderStorage> ENDERSTORAGE = CapabilityManager.get(new CapabilityToken<>(){});

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == ENDERSTORAGE) {
			return lazy.cast();
		}
		return null;
	}

	@Override
	public CompoundTag serializeNBT() {
		return storage.serializeNBT();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		storage.deserializeNBT(nbt);
		
	}
	
	@SubscribeEvent
	public static void register(RegisterCapabilitiesEvent event){
		event.register(EnderStorage.class);
	}

}
