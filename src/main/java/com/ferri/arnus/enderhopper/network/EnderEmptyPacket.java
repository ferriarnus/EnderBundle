package com.ferri.arnus.enderhopper.network;

import java.util.function.Supplier;

import com.ferri.arnus.enderhopper.blockentities.EnderHopperBE;
import com.ferri.arnus.enderhopper.capability.EnderStorage;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;

public class EnderEmptyPacket {
	
	private CompoundTag stack;
	
	public EnderEmptyPacket(CompoundTag stack) {
		this.stack = stack;
	}	
	
	public CompoundTag getStack() {
		return stack;
	}

	public void encode(FriendlyByteBuf buffer) {
		buffer.writeNbt(stack);
	}
	
	public static EnderEmptyPacket decode(FriendlyByteBuf buffer) {
		return new EnderEmptyPacket(buffer.readNbt());
	}

	static void handle(final EnderEmptyPacket message, Supplier<Context> ctx) {
		ctx.get().enqueueWork(() -> {
			EnderStorage cap = new EnderStorage();
			cap.deserializeNBT(message.getStack());
			BlockEntity be = cap.getLevel(ctx.get().getSender().level).getBlockEntity(cap.getPosistion());
			if (be instanceof EnderHopperBE hopper && hopper.getUuid().equals(cap.getUUID())) {
				for (int i = 0; i < hopper.getHandler().getSlots(); i++) {
					if (!hopper.getHandler().getStackInSlot(i).isEmpty()) {
						cap.setEmpty(false);
						return;
					}
				}
				cap.setEmpty(true);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
