package com.ferri.arnus.enderhopper.network;

import com.ferri.arnus.enderhopper.EnderHoppers;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

public class EnderHopperChannel {

private static final String PROTOCOL_VERSION = "1";
	
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(EnderHoppers.MODID, "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
			);
	
	public static void register() {
		INSTANCE.registerMessage(0, EnderStackPacket.class, EnderStackPacket::encode, EnderStackPacket::decode, EnderStackPacket::handle);
	}
}
