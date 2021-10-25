package com.ferri.arnus.enderhopper.items;

import com.ferri.arnus.enderhopper.EnderBundleMain;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EnderBundleMain.MODID);
	
	public static void register() {
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	public static final RegistryObject<Item> ENDER_BUNDLE = ITEMS.register("enderbundle", EnderBundle::new);
	
	public static final CreativeModeTab ENDERBUNDLETAB = new CreativeModeTab("enderbundle") {
		
		@Override
		public ItemStack makeIcon() {
			ItemStack stack = new ItemStack(ENDER_BUNDLE.get());
			stack.setDamageValue(0);
			return stack;
		}
	};
}
