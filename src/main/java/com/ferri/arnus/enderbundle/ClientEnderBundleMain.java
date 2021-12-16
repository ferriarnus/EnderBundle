package com.ferri.arnus.enderbundle;

import com.ferri.arnus.enderbundle.blockentity.BlockEntityRegistry;
import com.ferri.arnus.enderbundle.blockentity.EnderHopperBE;
import com.ferri.arnus.enderbundle.item.EnderBundleClientToolTip;
import com.ferri.arnus.enderbundle.item.EnderBundleToolTip;
import com.ferri.arnus.enderbundle.item.ItemRegistry;
import com.ferri.arnus.enderbundle.renderer.EnderBundleColor;
import com.ferri.arnus.enderbundle.renderer.EnderHopperRenderer;
import com.ferri.arnus.enderbundle.storage.EnderStorage;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ClientEnderBundleMain implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		FabricModelPredicateProviderRegistry.register(ItemRegistry.ENDERBUNDLE, new ResourceLocation(EnderBundleMain.MODID,"filled"), (stack, level, entity, i) -> {
			if (entity instanceof Player player && player.level != null && player.level.getGameTime() % 8 == 0) {
				FriendlyByteBuf buf = PacketByteBufs.create();
				buf.writeInt(player.containerMenu.getItems().indexOf(stack));
				ClientPlayNetworking.send(new ResourceLocation(EnderBundleMain.MODID, "items"), buf);
				player.inventoryMenu.broadcastChanges();
			}
			return new EnderStorage(stack).isEmpty()? 0 : 1;
		});
		
		BlockEntityRendererRegistry.register(BlockEntityRegistry.ENDERHOPPER, EnderHopperRenderer::new);
		
		ColorProviderRegistry.ITEM.register(new EnderBundleColor(), ItemRegistry.ENDERBUNDLE);
		
		TooltipComponentCallback.EVENT.register(data -> {
			if (data instanceof EnderBundleToolTip bundle) {
				return new EnderBundleClientToolTip(bundle);
			}
			return null;
		});
		
		ClientPlayNetworking.registerGlobalReceiver(new ResourceLocation(EnderBundleMain.MODID, "uuid"), (client, handler, buf, responseSender) -> {
			BlockEntity be = client.level.getBlockEntity(buf.readBlockPos());
			if (be instanceof EnderHopperBE hopper) {
				hopper.setUuid(buf.readUUID());
			}
		});
	}
}
