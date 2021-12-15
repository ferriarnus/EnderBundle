package com.ferri.arnus.enderbundle.block;

import com.ferri.arnus.enderbundle.EnderBundleMain;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BlockRegistry {

	public static final EnderHopper ENDERHOPPER = new EnderHopper(Properties.copy(Blocks.HOPPER));
	
	public static void register() {
		Registry.register(Registry.BLOCK, new ResourceLocation(EnderBundleMain.MODID, "enderhopper"), ENDERHOPPER);
		Registry.register(Registry.ITEM, new ResourceLocation(EnderBundleMain.MODID, "enderhopper"), new BlockItem(ENDERHOPPER, new net.minecraft.world.item.Item.Properties()));

	}
}
