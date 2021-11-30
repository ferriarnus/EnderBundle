package com.ferri.arnus.enderhopper.network;

import com.ferri.arnus.enderhopper.EnderBundleMain;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class EnderChannel {

private static final String PROTOCOL_VERSION = "1";
	
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(EnderBundleMain.MODID, "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
			);
	
	public static void register() {
		INSTANCE.registerMessage(0, HopperUUIDPacket.class, HopperUUIDPacket::encode, HopperUUIDPacket::decode, HopperUUIDPacket::handle);
		INSTANCE.registerMessage(1, EnderEmptyPacket.class, EnderEmptyPacket::encode, EnderEmptyPacket::decode, EnderEmptyPacket::handle);
	}
}
