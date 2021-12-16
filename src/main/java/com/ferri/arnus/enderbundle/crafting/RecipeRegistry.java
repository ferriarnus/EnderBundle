package com.ferri.arnus.enderbundle.crafting;

import com.ferri.arnus.enderbundle.EnderBundleMain;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class RecipeRegistry {
	
	public static void register() {
		 Registry.register(Registry.RECIPE_SERIALIZER, new ResourceLocation(EnderBundleMain.MODID, "dyeing"), Dyeing.SERIALIZER);
	}
	
}
