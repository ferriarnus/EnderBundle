package com.ferri.arnus.enderhopper.integration.jei;

import java.util.concurrent.atomic.AtomicReference;

import com.ferri.arnus.enderhopper.EnderBundleMain;
import com.ferri.arnus.enderhopper.capability.DyeProvider;
import com.ferri.arnus.enderhopper.items.ItemRegistry;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class EnderBundleJEI implements IModPlugin{

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(EnderBundleMain.MODID, "main");
	}
	
	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) {
		registration.registerSubtypeInterpreter(ItemRegistry.ENDER_BUNDLE.get(), (s,c) -> {
			AtomicReference<String> a = new AtomicReference<>("");
			s.getCapability(DyeProvider.DYEABLE).ifPresent(cap ->{
				a.set(cap.serializeNBT().toString());
			});
			return a.get();
		});
	}
}
