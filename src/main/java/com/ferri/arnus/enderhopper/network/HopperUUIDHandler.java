package com.ferri.arnus.enderhopper.network;

import java.util.function.Supplier;

import com.ferri.arnus.enderhopper.blockentities.EnderHopperBE;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;

public class HopperUUIDHandler {

	public static void handlePacket(HopperUUIDPacket message, Supplier<Context> ctx) {
		BlockEntity be = Minecraft.getInstance().level.getBlockEntity(message.getPos());
		if (be instanceof EnderHopperBE hopper) {
			hopper.setUuid(message.getUuid());
		}
	}
}
