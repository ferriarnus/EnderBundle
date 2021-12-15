package com.ferri.arnus.enderbundle.item;

import java.util.UUID;

import com.ferri.arnus.enderbundle.blockentity.EnderHopperBE;
import com.ferri.arnus.enderbundle.components.EnderStorageComponent;
import com.ferri.arnus.enderbundle.components.MyComponents;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;

public class EnderBundle extends Item{

	public EnderBundle(Properties settings) {
		super(settings);
	}
	
	@Override
	public InteractionResult useOn(UseOnContext pContext) {
		BlockEntity be = pContext.getLevel().getBlockEntity(pContext.getClickedPos());
		if (be instanceof EnderHopperBE hopper) {
			EnderStorageComponent enderStorage = MyComponents.ENDERSTORAGE.get(pContext.getPlayer().getItemInHand(pContext.getHand()));
			if (!pContext.getLevel().isClientSide && enderStorage.getPosistion() != BlockPos.ZERO) {
				BlockEntity old = enderStorage.getLevel(pContext.getLevel()).getBlockEntity(enderStorage.getPosistion());
				if (old instanceof EnderHopperBE oldhopper) {
					oldhopper.setBound(false);
				}
			}
			enderStorage.setLevel(pContext.getLevel());
			enderStorage.setPosistion(pContext.getClickedPos());
			UUID randomUUID = UUID.randomUUID();
			enderStorage.setUUID(randomUUID);
			hopper.setUuid(randomUUID);
			if (pContext.getItemInHand().hasCustomHoverName()) {
				hopper.setCustomName(pContext.getItemInHand().getHoverName());
			}
			return InteractionResult.SUCCESS;
		}
		return super.useOn(pContext);
	}
	
	@Override
	public int getBarColor(ItemStack p_150901_) {
		return Mth.color(0.4F, 0.4F, 1.0F);
	}
	
	@Override
	public boolean overrideOtherStackedOnMe(ItemStack pStack, ItemStack pOther, Slot pSlot, ClickAction pAction, Player pPlayer, SlotAccess pAccess) {
		if (pAction == ClickAction.SECONDARY && pSlot.allowModification(pPlayer)) {
			EnderStorageComponent enderStorage = MyComponents.ENDERSTORAGE.get(pStack);
			if (pPlayer.level.getServer() == null) {
				return false;
			}
			BlockEntity be = enderStorage.getLevel(pPlayer.level).getBlockEntity(enderStorage.getPosistion());
			if (be == null) return false;
			if (be instanceof EnderHopperBE hopper) {
				if (!hopper.getUuid().equals(enderStorage.getUUID()) && false) {
					return false;
				}
				if (pOther.isEmpty()) {
					ItemStack result = ItemStack.EMPTY;
					for (ItemStack stack : hopper.getItems()) {
						if(!stack.isEmpty()) {
							result = stack.copy();
							stack.shrink(stack.getCount());
							break;
						}
					}
					if (result.isEmpty()) {
						return false;
					}
					pAccess.set(result);
					return true;
				}
				else {
					for (ItemStack stack : hopper.getItems()) {
						if(ItemStack.isSame(stack, pOther)) {
							int i = stack.getMaxStackSize() - stack.getCount() - pOther.getCount();
							if (i >= 0) {
								stack.setCount(stack.getMaxStackSize() - i);
								pOther.setCount(0);
								pOther = ItemStack.EMPTY;
								return true;
							}
							else if (stack.getCount() != stack.getMaxStackSize()){
								stack.setCount(stack.getMaxStackSize());
								pOther.setCount(-i);
								return true;
							}
						}
						if(stack.isEmpty()) {
							hopper.setItem(hopper.getItems().indexOf(stack), pOther.copy());
							pOther.setCount(0);
							pOther = ItemStack.EMPTY;
							return true;
						}
					}
				}
			}
		}
		return false;
	}

}
