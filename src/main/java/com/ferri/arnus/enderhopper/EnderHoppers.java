package com.ferri.arnus.enderhopper;

import com.ferri.arnus.enderhopper.blockentities.BlockEntityRegistry;
import com.ferri.arnus.enderhopper.blocks.BlockRegistry;
import com.ferri.arnus.enderhopper.capability.DyeProvider;
import com.ferri.arnus.enderhopper.capability.EnderStorageProvider;
import com.ferri.arnus.enderhopper.gui.ContainerRegistry;
import com.ferri.arnus.enderhopper.gui.EnderHopperScreen;
import com.ferri.arnus.enderhopper.items.ItemRegistry;
import com.ferri.arnus.enderhopper.network.EnderHopperChannel;
import com.ferri.arnus.enderhopper.renderers.EnderHopperRenderer;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(EnderHoppers.MODID)
@EventBusSubscriber
public class EnderHoppers {
	public static final String MODID = "enderhopper";

    public EnderHoppers() {
    	
    	BlockRegistry.register();
    	ItemRegistry.register();
    	BlockEntityRegistry.register();
    	ContainerRegistry.register();
    	EnderHopperChannel.register();
    	
//    	final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
//		modEventBus.addListener(this::clientSetup);
//		modEventBus.addListener(this::registerBERS);
    }
    
    public void clientSetup(FMLClientSetupEvent event) {
    	MenuScreens.register(ContainerRegistry.ENDER_HOPPER.get(), EnderHopperScreen::new);
    }
    
    @SubscribeEvent
    static void attachcaps(AttachCapabilitiesEvent<ItemStack> event) {
    	if (event.getObject().is(ItemRegistry.ENDER_BUNDEL.get())) {
    		event.addCapability(new ResourceLocation(MODID, "enderhopper"), new EnderStorageProvider());
    		event.addCapability(new ResourceLocation(MODID, "dye"), new DyeProvider());
    	}
    }
    
    public void registerBERS(EntityRenderersEvent.RegisterRenderers event) {
    	System.out.println("hzy");
        event.registerBlockEntityRenderer(BlockEntityRegistry.ENDERHOPPER.get(), EnderHopperRenderer::new);
    }
    
    
}
