package com.ferri.arnus.enderhopper.network;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import com.ferri.arnus.enderhopper.blockentities.EnderHopperBE;
import com.ferri.arnus.enderhopper.capability.EnderStorageProvider;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent.Context;

public class EnderEmptyPacket {
	
	private int slot;
	
	public EnderEmptyPacket( int slot) {
		this.slot = slot;
	}	
	
	

	public void encode(FriendlyByteBuf buffer) {
		buffer.writeInt(slot);
	}
	
	public static EnderEmptyPacket decode(FriendlyByteBuf buffer) {
		return new EnderEmptyPacket(buffer.readInt());
	}

	static void handle(final EnderEmptyPacket message, Supplier<Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ItemStack stack = ctx.get().getSender().containerMenu.getCarried();
			if (message.slot >= ctx.get().getSender().containerMenu.getItems().size()) {return;}
			if (message.slot != -1) {
				stack = ctx.get().getSender().containerMenu.getItems().get(message.slot);
			}
			AtomicReference<ItemStack> atom = new AtomicReference<>(stack);
			stack.getCapability(EnderStorageProvider.ENDERSTORAGE).ifPresent(cap -> {
				boolean empty = true;
				int damage = 0;
				NonNullList<ItemStack> list = NonNullList.create();
				BlockEntity be = cap.getLevel(ctx.get().getSender().level).getBlockEntity(cap.getPosistion());
				if (be instanceof EnderHopperBE hopper && hopper.getUuid().equals(cap.getUUID())) {
					for (int i =0; i< hopper.getHandler().getSlots(); i++) {
						ItemStack stackInSlot = hopper.getHandler().getStackInSlot(i);
						list.add(i,stackInSlot);
						if (!stackInSlot.isEmpty()) {
							empty = false;
							damage += (stackInSlot.getCount() * 64) / stackInSlot.getMaxStackSize();
						}
					}
					atom.get().setDamageValue(atom.get().getMaxDamage() - damage);
					cap.setEmpty(empty);
					//System.out.println(list +" server");
					atom.get().getOrCreateTag().put("enderbundleitems", ContainerHelper.saveAllItems(new CompoundTag(), list));
					//System.out.println(atom.get().getOrCreateTag());
					return;
				}
				atom.get().setDamageValue(atom.get().getMaxDamage());
				atom.get().getOrCreateTag().put("enderbundleitems", ContainerHelper.saveAllItems(new CompoundTag(), list));
				cap.setEmpty(true);
			});
		});
		ctx.get().setPacketHandled(true);
	}
}
