package com.ferri.arnus.enderhopper.crafting;

import com.ferri.arnus.enderhopper.EnderBundleMain;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = EnderBundleMain.MODID)
public class CraftingRegistry {
	
	private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, EnderBundleMain.MODID);
	
	public static void registerRecipe() {
		RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	private static RegistryObject<RecipeSerializer<Dyeing>> DYEING = RECIPE_SERIALIZERS.register("dyeing", () -> Dyeing.SERIALIZER);
	
}
