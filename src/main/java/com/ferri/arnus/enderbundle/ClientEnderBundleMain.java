package com.ferri.arnus.enderbundle;

import com.ferri.arnus.enderbundle.blockentity.BlockEntityRegistry;
import com.ferri.arnus.enderbundle.blockentity.EnderHopperBE;
import com.ferri.arnus.enderbundle.item.ItemRegistry;
import com.ferri.arnus.enderbundle.renderer.EnderBundleColor;
import com.ferri.arnus.enderbundle.renderer.EnderHopperRenderer;
import com.ferri.arnus.enderbundle.storage.EnderStorage;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ClientEnderBundleMain implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		FabricModelPredicateProviderRegistry.register(ItemRegistry.ENDERBUNDLE, new ResourceLocation(EnderBundleMain.MODID,"filled"), (stack, level, entity, i) -> {
			return new EnderStorage(stack).isEmpty()? 0 : 1;
		});
		
		BlockEntityRendererRegistry.register(BlockEntityRegistry.ENDERHOPPER, EnderHopperRenderer::new);
		
		ColorProviderRegistry.ITEM.register(new EnderBundleColor(), ItemRegistry.ENDERBUNDLE);
		
		ClientPlayNetworking.registerGlobalReceiver(new ResourceLocation(EnderBundleMain.MODID, "uuid"), (client, handler, buf, responseSender) -> {
			BlockEntity be = client.level.getBlockEntity(buf.readBlockPos());
			if (be instanceof EnderHopperBE hopper) {
				hopper.setUuid(buf.readUUID());
				System.out.println(hopper.getUuid());
			}
		});
	}
}
