package com.ferri.arnus.enderbundle;

import com.ferri.arnus.enderbundle.blockentity.BlockEntityRegistry;
import com.ferri.arnus.enderbundle.components.MyComponents;
import com.ferri.arnus.enderbundle.item.ItemRegistry;
import com.ferri.arnus.enderbundle.renderer.EnderBundleColor;
import com.ferri.arnus.enderbundle.renderer.EnderHopperRenderer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.resources.ResourceLocation;

public class ClientEnderBundleMain implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		FabricModelPredicateProviderRegistry.register(ItemRegistry.ENDERBUNDLE, new ResourceLocation(EnderBundleMain.MODID,"filled"), (stack, level, entity, i) -> {
			return MyComponents.ENDERSTORAGE.get(stack).isEmpty()? 0 : 1;
		});
		
		BlockEntityRendererRegistry.register(BlockEntityRegistry.ENDERHOPPER, EnderHopperRenderer::new);
		
		ColorProviderRegistry.ITEM.register(new EnderBundleColor(), ItemRegistry.ENDERBUNDLE);
	}

}
