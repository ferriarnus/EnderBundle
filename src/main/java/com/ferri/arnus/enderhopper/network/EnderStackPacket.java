package com.ferri.arnus.enderhopper.network;

import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;

public class EnderStackPacket {
	
	private ItemStack stack;
	private UUID uuid;
	
	public EnderStackPacket(ItemStack stack, UUID uuid) {
		this.stack = stack;
		this.uuid = uuid;
	}
	
	public ItemStack getStack() {
		return stack;
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public void encode(FriendlyByteBuf buffer) {
		buffer.writeItemStack(stack, true);
		buffer.writeUUID(uuid);
	}
	
	public static EnderStackPacket decode(FriendlyByteBuf buffer) {
		return new EnderStackPacket(buffer.readItem(), buffer.readUUID());
	}

	static void handle(final EnderStackPacket message, Supplier<Context> ctx) {
		ctx.get().enqueueWork(() ->
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> EnderStackHandler.handlePacket(message, ctx))
				);
		ctx.get().setPacketHandled(true);
	}

}
