package com.ferri.arnus.enderhopper.network;

import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;

public class EnderStackPacket {
	
	private BlockPos pos;
	private UUID uuid;
	
	public EnderStackPacket(BlockPos pos, UUID uuid) {
		this.pos = pos;
		this.uuid = uuid;
	}
	
	public BlockPos getPos() {
		return pos;
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public void encode(FriendlyByteBuf buffer) {
		buffer.writeBlockPos(pos);
		buffer.writeUUID(uuid);
	}
	
	public static EnderStackPacket decode(FriendlyByteBuf buffer) {
		return new EnderStackPacket(buffer.readBlockPos(), buffer.readUUID());
	}

	static void handle(final EnderStackPacket message, Supplier<Context> ctx) {
		ctx.get().enqueueWork(() ->
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> EnderStackHandler.handlePacket(message, ctx))
				);
		ctx.get().setPacketHandled(true);
	}

}
