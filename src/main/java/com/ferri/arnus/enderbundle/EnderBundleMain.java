package com.ferri.arnus.enderbundle;

import com.ferri.arnus.enderbundle.block.BlockRegistry;
import com.ferri.arnus.enderbundle.blockentity.BlockEntityRegistry;
import com.ferri.arnus.enderbundle.blockentity.EnderHopperBE;
import com.ferri.arnus.enderbundle.item.ItemRegistry;
import com.ferri.arnus.enderbundle.storage.EnderStorage;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class EnderBundleMain implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MODID = "enderbundle";

	@Override
	public void onInitialize() {
		BlockRegistry.register();
		ItemRegistry.register();
		BlockEntityRegistry.register();
		
		ServerPlayNetworking.registerGlobalReceiver(new ResourceLocation(MODID, "items"), (server, player, handler, buf, responseSender) -> {
			if (player.level.isClientSide) {
				return;
			}
			int slot = buf.readInt();
			server.execute(() -> {
				ItemStack stack = player.containerMenu.getCarried();
				if (slot >= player.containerMenu.getItems().size()) {
					return;
				}
				if (slot != -1) {
					stack = player.containerMenu.getItems().get(slot);
				}
				EnderStorage enderStorage = new EnderStorage(stack);
				boolean empty = true;
				int damage = 0;
				NonNullList<ItemStack> list = NonNullList.create();
				BlockEntity be = enderStorage.getLevel(player.level).getBlockEntity(enderStorage.getPosistion());
				if (be instanceof EnderHopperBE hopper && hopper.getUuid().equals(enderStorage.getUUID())) {
					for (int i =0; i< hopper.getContainerSize(); i++) {
						ItemStack stackInSlot = hopper.getItem(i);
						list.add(i,stackInSlot);
						if (!stackInSlot.isEmpty()) {
							empty = false;
							damage += (stackInSlot.getCount() * 64) / stackInSlot.getMaxStackSize();
						}
					}
					stack.getOrCreateTagElement("enderitems").putInt("damage", (int) (damage/320F *13));
					enderStorage.setEmpty(empty);
					stack.getOrCreateTagElement("enderitems").put("items", ContainerHelper.saveAllItems(new CompoundTag(), list));
				}else {
					stack.getOrCreateTagElement("enderitems").putInt("damage", 0);
					stack.getOrCreateTagElement("enderitems").put("items", ContainerHelper.saveAllItems(new CompoundTag(), list));
					enderStorage.setEmpty(true);
				}
			});
		});
	}
}
