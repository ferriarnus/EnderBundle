package com.ferri.arnus.enderhopper;

import java.util.concurrent.atomic.AtomicBoolean;

import com.ferri.arnus.enderhopper.blockentities.BlockEntityRegistry;
import com.ferri.arnus.enderhopper.capability.EnderStorageProvider;
import com.ferri.arnus.enderhopper.gui.ContainerRegistry;
import com.ferri.arnus.enderhopper.gui.EnderHopperScreen;
import com.ferri.arnus.enderhopper.items.ItemRegistry;
import com.ferri.arnus.enderhopper.network.EnderEmptyPacket;
import com.ferri.arnus.enderhopper.network.EnderHopperChannel;
import com.ferri.arnus.enderhopper.renderers.EnderBundleColor;
import com.ferri.arnus.enderhopper.renderers.EnderHopperRenderer;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
	
	@SubscribeEvent
	static void clientSetup(FMLClientSetupEvent event) {
    	MenuScreens.register(ContainerRegistry.ENDER_HOPPER.get(), EnderHopperScreen::new);
    	
    	ItemProperties.register(ItemRegistry.ENDER_BUNDEL.get(), new ResourceLocation(EnderHoppers.MODID,"filled"), (s,l,e,i) -> {
			AtomicBoolean b = new AtomicBoolean(true);
			s.getCapability(EnderStorageProvider.ENDERSTORAGE).ifPresent(cap -> {
				EnderHopperChannel.INSTANCE.sendToServer(new EnderEmptyPacket(cap.serializeNBT()));
				b.set(cap.isEmpty());
			});
			return b.get()? 0F : 1F;
         });
    }
	
	@SubscribeEvent
	static void registerBERS(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityRegistry.ENDERHOPPER.get(), EnderHopperRenderer::new);
    }
	
	@SubscribeEvent
	public static void registerItemColor(ColorHandlerEvent.Item event) {
		event.getItemColors().register(new EnderBundleColor(), ItemRegistry.ENDER_BUNDEL.get());
	}
}
