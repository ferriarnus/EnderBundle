package com.ferri.arnus.enderhopper.crafting;

import com.ferri.arnus.enderhopper.EnderBundleMain;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistryEntry;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = EnderBundleMain.MODID)
public class CraftingRegistry {

	@SubscribeEvent
	public static void registerSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
		event.getRegistry().register(name(Dyeing.SERIALIZER, "dyeing"));
	}
	
	private static <T extends IForgeRegistryEntry<? extends T>> T name(T entry, String name) {
		return entry.setRegistryName(new ResourceLocation(EnderBundleMain.MODID, name));
	}
	
	
}
