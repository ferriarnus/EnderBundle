package com.ferri.arnus.enderhopper;

import java.util.concurrent.atomic.AtomicBoolean;

import com.ferri.arnus.enderhopper.blockentities.BlockEntityRegistry;
import com.ferri.arnus.enderhopper.capability.EnderStorageProvider;
import com.ferri.arnus.enderhopper.gui.ContainerRegistry;
import com.ferri.arnus.enderhopper.gui.EnderHopperScreen;
import com.ferri.arnus.enderhopper.items.EnderBundleClientToolTip;
import com.ferri.arnus.enderhopper.items.EnderBundleToolTip;
import com.ferri.arnus.enderhopper.items.ItemRegistry;
import com.ferri.arnus.enderhopper.network.EnderChannel;
import com.ferri.arnus.enderhopper.network.EnderEmptyPacket;
import com.ferri.arnus.enderhopper.renderers.EnderBundleColor;
import com.ferri.arnus.enderhopper.renderers.EnderHopperRenderer;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.MinecraftForgeClient;
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
    	
    	MinecraftForgeClient.registerTooltipComponentFactory(EnderBundleToolTip.class, EnderBundleClientToolTip::new);
    	
    	ItemProperties.register(ItemRegistry.ENDER_BUNDLE.get(), new ResourceLocation(EnderBundleMain.MODID,"filled"), (s,l,e,i) -> {
    		if (e instanceof Player player && (l == null || l.getGameTime() % 5 == 0)) {
    			EnderChannel.INSTANCE.sendToServer(new EnderEmptyPacket(player.containerMenu.getItems().indexOf(s)));
    		}
			AtomicBoolean b = new AtomicBoolean(true);
			s.getCapability(EnderStorageProvider.ENDERSTORAGE).ifPresent(cap -> {
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
		event.getItemColors().register(new EnderBundleColor(), ItemRegistry.ENDER_BUNDLE.get());
	}
}
