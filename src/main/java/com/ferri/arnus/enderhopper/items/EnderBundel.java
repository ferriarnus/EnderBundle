package com.ferri.arnus.enderhopper.items;

import com.ferri.arnus.enderhopper.blockentities.EnderHopperBE;
import com.ferri.arnus.enderhopper.blocks.EnderHopper;
import com.ferri.arnus.enderhopper.capability.EnderStorageProvider;

import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;

public class EnderBundel extends Item{

	public EnderBundel() {
		super(new Properties().stacksTo(1));
	}
	
	@Override
	public InteractionResult useOn(UseOnContext pContext) {
		BlockEntity be = pContext.getLevel().getBlockEntity(pContext.getClickedPos());
		if (be instanceof EnderHopperBE hopper) {
			pContext.getItemInHand().getCapability(EnderStorageProvider.ENDERSTORAGE).ifPresent(cap -> {
				cap.setLevel(pContext.getLevel());
				cap.setPosistion(pContext.getClickedPos());
				hopper.setUuid(cap.getUUID());
			});
			return InteractionResult.SUCCESS;
		}
		return super.useOn(pContext);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
		pPlayer.getItemInHand(pUsedHand).getCapability(EnderStorageProvider.ENDERSTORAGE).ifPresent(cap -> {
			if (pLevel.getServer() == null) {return; }
			BlockEntity be = cap.getLevel(pLevel).getBlockEntity(cap.getPosistion());
			if (be instanceof EnderHopperBE hopper) {
				if (!hopper.getUuid().equals(cap.getUUID())) {
					return;
				}
				hopper.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, hopper.getBlockState().getValue(EnderHopper.FACING)).ifPresent(h -> {
					for (int i = 0; i< h.getSlots(); i++) {
						ItemStack stack = h.extractItem(i, h.getStackInSlot(i).getCount(), false);
						if (pPlayer instanceof ServerPlayer sp) {
							sp.drop(stack, true);
						}
					}
				});
			}
		});
		return super.use(pLevel, pPlayer, pUsedHand);
	}
	
	@Override
	public boolean overrideOtherStackedOnMe(ItemStack pStack, ItemStack pOther, Slot pSlot, ClickAction pAction, Player pPlayer, SlotAccess pAccess) {
		if (pAction == ClickAction.SECONDARY && pSlot.allowModification(pPlayer)) {
			pStack.getCapability(EnderStorageProvider.ENDERSTORAGE).ifPresent(cap -> {
				if (pPlayer.level.getServer() == null) {return;}
				BlockEntity be = cap.getLevel(pPlayer.level).getBlockEntity(cap.getPosistion());
				if (be instanceof EnderHopperBE hopper) {
					if (!hopper.getUuid().equals(cap.getUUID())) {
						return;
					}
					if (pOther.isEmpty()) {
						hopper.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, hopper.getBlockState().getValue(EnderHopper.FACING)).ifPresent(h -> {
							for (int i = 0; i < h.getSlots(); i++) {
								if (h.extractItem(i, h.getStackInSlot(i).getCount(), true) != ItemStack.EMPTY) {
									ItemStack stack = h.extractItem(i, h.getStackInSlot(i).getCount(), false);
									pAccess.set(stack);
									break;
								}
							}
						});
					} else {
						hopper.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).ifPresent(h -> {
							for (int i = 0; i < h.getSlots(); i++) {
								if (!ItemStack.matches(h.insertItem(i, pOther, true), pOther)) {
									ItemStack stack = h.insertItem(i, pOther.copy(), false);
									pOther.shrink(pOther.getCount() - stack.getCount());
									if (stack.isEmpty()) {
										break;
									}
								}
							}
						});
					}
				}
			});
			return true;
		} 
		return false;
	}
	
	@Override
	public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
		if (pAction != ClickAction.SECONDARY) {
	         return false;
		}
		ItemStack pOther = pSlot.getItem();
		pStack.getCapability(EnderStorageProvider.ENDERSTORAGE).ifPresent(cap -> {
			if (pPlayer.level.getServer() == null) {return;}
			BlockEntity be = cap.getLevel(pPlayer.level).getBlockEntity(cap.getPosistion());
			if (be instanceof EnderHopperBE hopper) {
				if (!hopper.getUuid().equals(cap.getUUID())) {
					return;
				}
				if (pOther.isEmpty()) {
					hopper.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, hopper.getBlockState().getValue(EnderHopper.FACING)).ifPresent(h -> {
						for (int i = 0; i < h.getSlots(); i++) {
							if (h.extractItem(i, h.getStackInSlot(i).getCount(), true) != ItemStack.EMPTY) {
								ItemStack stack = h.extractItem(i, h.getStackInSlot(i).getCount(), false);
								pSlot.set(stack);
								break;
							}
						}
					});
				} else {
					hopper.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).ifPresent(h -> {
						for (int i = 0; i < h.getSlots(); i++) {
							if (!ItemStack.matches(h.insertItem(i, pOther, true), pOther)) {
								ItemStack stack = h.insertItem(i, pOther.copy(), false);
								pOther.shrink(pOther.getCount() - stack.getCount());
								if (stack.isEmpty()) {
									break;
								}
							}
						}
					});
				}
			}
		});
		return true;
	}


}
