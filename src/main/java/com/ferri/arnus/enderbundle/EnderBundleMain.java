package com.ferri.arnus.enderbundle;

import com.ferri.arnus.enderbundle.block.BlockRegistry;
import com.ferri.arnus.enderbundle.blockentity.BlockEntityRegistry;
import com.ferri.arnus.enderbundle.item.ItemRegistry;

import net.fabricmc.api.ModInitializer;

public class EnderBundleMain implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MODID = "enderbundle";

	@Override
	public void onInitialize() {
		BlockRegistry.register();
		ItemRegistry.register();
		BlockEntityRegistry.register();

	}
}
