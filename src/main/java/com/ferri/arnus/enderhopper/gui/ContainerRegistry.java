package com.ferri.arnus.enderhopper.gui;

import com.ferri.arnus.enderhopper.EnderBundleMain;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerRegistry {

public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, EnderBundleMain.MODID);
	
	public static void register() {
		CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	public static final RegistryObject<MenuType<EnderHopperContainer>> ENDER_HOPPER = CONTAINERS.register("enderhopper", ()-> IForgeContainerType.create(EnderHopperContainer::new));
}
