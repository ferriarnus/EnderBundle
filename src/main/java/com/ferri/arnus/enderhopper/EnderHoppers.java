package com.ferri.arnus.enderhopper;

import com.ferri.arnus.enderhopper.blockentities.BlockEntityRegistry;
import com.ferri.arnus.enderhopper.blocks.BlockRegistry;
import com.ferri.arnus.enderhopper.capability.EnderStorageProvider;
import com.ferri.arnus.enderhopper.items.ItemRegistry;
import com.ferri.arnus.enderhopper.network.EnderHopperChannel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@Mod(EnderHoppers.MODID)
@EventBusSubscriber
public class EnderHoppers {
	public static final String MODID = "enderhopper";

    public EnderHoppers() {
    	
    	BlockRegistry.register();
    	ItemRegistry.register();
    	BlockEntityRegistry.register();
    	EnderHopperChannel.register();
    }
    
    @SubscribeEvent
    static void attachcaps(AttachCapabilitiesEvent<ItemStack> event) {
    	if (event.getObject().is(ItemRegistry.ENDERBUNDEL.get())) {
    		event.addCapability(new ResourceLocation(MODID, "enderhopper"), new EnderStorageProvider());
    	}
    }
}
