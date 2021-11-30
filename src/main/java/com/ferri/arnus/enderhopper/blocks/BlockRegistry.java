package com.ferri.arnus.enderhopper.blocks;

import com.ferri.arnus.enderhopper.EnderBundleMain;
import com.ferri.arnus.enderhopper.items.ItemRegistry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, EnderBundleMain.MODID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EnderBundleMain.MODID);
	
	public static void register() {
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	public static final RegistryObject<EnderHopper> ENDERHOPPER = BLOCKS.register("enderhopper", EnderHopper::new);
	public static final RegistryObject<Item> ENDERHOPPER_ITEM = ITEMS.register("enderhopper", () -> new BlockItem(ENDERHOPPER.get(), new Properties().tab(ItemRegistry.ENDERBUNDLETAB)));
}
