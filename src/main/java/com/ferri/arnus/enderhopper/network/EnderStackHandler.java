package com.ferri.arnus.enderhopper.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;

public class EnderStackHandler {

	public static void handlePacket(EnderStackPacket message, Supplier<Context> ctx) {
		ItemStack stack = Minecraft.getInstance().level.getPlayerByUUID(message.getUuid()).getInventory().getSelected();
		System.out.println(stack);
		System.out.println(message.getStack());
		stack.shrink(message.getStack().getCount());
	}
}
