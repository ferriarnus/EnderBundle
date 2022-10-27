package com.ferri.arnus.enderbundle.item;

import java.util.Optional;
import java.util.UUID;

import com.ferri.arnus.enderbundle.EnderBundleMain;
import com.ferri.arnus.enderbundle.blockentity.EnderHopperBE;
import com.ferri.arnus.enderbundle.storage.ColorStorage;
import com.ferri.arnus.enderbundle.storage.EnderStorage;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class EnderBundle extends Item{

	public EnderBundle(Properties settings) {
		super(settings);
	}
	
	@Override
	public InteractionResult useOn(UseOnContext pContext) {
		BlockEntity be = pContext.getLevel().getBlockEntity(pContext.getClickedPos());
		if (be instanceof EnderHopperBE hopper) {
			EnderStorage enderStorage = new EnderStorage(pContext.getPlayer().getItemInHand(pContext.getHand()));
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
			if (!pContext.getLevel().isClientSide) {
				for (ServerPlayer player: PlayerLookup.tracking((ServerLevel) pContext.getLevel(), pContext.getLevel().getChunkAt(pContext.getClickedPos()).getPos())) {
					FriendlyByteBuf buf = PacketByteBufs.create();
					buf.writeBlockPos(enderStorage.getPosistion());
					buf.writeUUID(randomUUID);
					ServerPlayNetworking.send(player, new ResourceLocation(EnderBundleMain.MODID, "uuid"), buf);
				}
			}
			if (pContext.getItemInHand().hasCustomHoverName()) {
				hopper.setCustomName(pContext.getItemInHand().getHoverName());
			}
			return InteractionResult.SUCCESS;
		}
		return super.useOn(pContext);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
		EnderStorage enderStorage = new EnderStorage(pPlayer.getItemInHand(pUsedHand));
		if (pLevel.getServer() == null) {
			return super.use(pLevel, pPlayer, pUsedHand);
		}
		BlockEntity be = enderStorage.getLevel(pLevel).getBlockEntity(enderStorage.getPosistion());
		if (be instanceof EnderHopperBE hopper) {
			if (!hopper.getUuid().equals(enderStorage.getUUID())) {
				return super.use(pLevel, pPlayer, pUsedHand);
			}
			for (ItemStack stack : hopper.getItems()) {
				if (pPlayer instanceof ServerPlayer sp) {
					sp.drop(stack.copy(), true);
				}
				stack.setCount(0);
				stack = ItemStack.EMPTY;
			}
		}
		return super.use(pLevel, pPlayer, pUsedHand);
	}
	
	@Override
	public int getBarColor(ItemStack p_150901_) {
		return Mth.color(0.4F, 0.4F, 1.0F);
	}
	
	@Override
	public boolean isBarVisible(ItemStack itemStack) {
		return !new EnderStorage(itemStack).isEmpty();
	}
	
	@Override
	public int getBarWidth(ItemStack itemStack) {
		if (!itemStack.getOrCreateTagElement("enderitems").contains("damage")) {
			return 0;
		}
		return itemStack.getOrCreateTagElement("enderitems").getInt("damage");
	}
	
	@Override
	public Optional<TooltipComponent> getTooltipImage(ItemStack itemStack) {
		NonNullList<ItemStack> nonNullList = NonNullList.withSize(5, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(itemStack.getOrCreateTagElement("enderitems").getCompound("items"), nonNullList);
        return Optional.of(new EnderBundleToolTip(nonNullList));
	}
	
	@Override
	public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {
		if (this.allowedIn(pCategory)) {
			for (DyeColor c : DyeColor.values()) {
				ItemStack stack = new ItemStack(this);
				new ColorStorage(stack).setColor(c.getMaterialColor().col);
				pItems.add(stack);
			}
		}
	}
	
	@Override
	public boolean overrideOtherStackedOnMe(ItemStack pStack, ItemStack pOther, Slot pSlot, ClickAction pAction, Player pPlayer, SlotAccess pAccess) {
		if (pAction == ClickAction.SECONDARY && pSlot.allowModification(pPlayer)) {
			if (!pStack.is(ItemRegistry.ENDERBUNDLE)) {
				return false;
			}
			EnderStorage enderStorage = new EnderStorage(pStack);
			if (pPlayer.level.getServer() == null) {
				return false;
			}
			BlockEntity be = enderStorage.getLevel(pPlayer.level).getBlockEntity(enderStorage.getPosistion());
			if (be == null) return false;
			if (be instanceof EnderHopperBE hopper) {
				if (!hopper.getUuid().equals(enderStorage.getUUID())) {
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
	
	@Override
	public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
		if (pAction != ClickAction.SECONDARY) {
	         return false;
		}
		ItemStack pOther = pSlot.getItem();
		if (!pStack.is(ItemRegistry.ENDERBUNDLE)) {
			return false;
		}
		EnderStorage enderStorage = new EnderStorage(pStack);
		if (pPlayer.level.getServer() == null) {
			return false;
		}
		BlockEntity be = enderStorage.getLevel(pPlayer.level).getBlockEntity(enderStorage.getPosistion());
		if (be == null) return false;
		if (be instanceof EnderHopperBE hopper) {
			if (!hopper.getUuid().equals(enderStorage.getUUID())) {
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
				pSlot.set(result);
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
		return true;
	}
}
