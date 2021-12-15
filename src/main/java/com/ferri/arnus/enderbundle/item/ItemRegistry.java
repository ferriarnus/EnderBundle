package com.ferri.arnus.enderbundle.item;

import com.ferri.arnus.enderbundle.EnderBundleMain;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;


public class ItemRegistry {
	
	public static final EnderBundle ENDERBUNDLE = new EnderBundle(new FabricItemSettings().group(CreativeModeTab.TAB_MISC));
	
	public static void register() {
		Registry.register(Registry.ITEM, new ResourceLocation(EnderBundleMain.MODID, "enderbundle"), ENDERBUNDLE);
	}
}
