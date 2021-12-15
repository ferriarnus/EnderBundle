package com.ferri.arnus.enderbundle.components;

import com.ferri.arnus.enderbundle.EnderBundleMain;
import com.ferri.arnus.enderbundle.item.ItemRegistry;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.item.ItemComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.item.ItemComponentInitializer;
import net.minecraft.resources.ResourceLocation;

public final class MyComponents implements ItemComponentInitializer {
	public static final ComponentKey<EnderStorageComponent> ENDERSTORAGE = ComponentRegistry.getOrCreate(new ResourceLocation(EnderBundleMain.MODID, "enderstorage"), EnderStorageComponent.class);
	
	@Override
	public void registerItemComponentFactories(ItemComponentFactoryRegistry registry) {
		registry.register(ItemRegistry.ENDERBUNDLE, ENDERSTORAGE, EnderStorageComponent::new);
	}
	
}
