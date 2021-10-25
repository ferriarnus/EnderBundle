package com.ferri.arnus.enderhopper.items;

import java.util.Optional;
import java.util.UUID;

import com.ferri.arnus.enderhopper.blockentities.EnderHopperBE;
import com.ferri.arnus.enderhopper.blocks.EnderHopper;
import com.ferri.arnus.enderhopper.capability.DyeProvider;
import com.ferri.arnus.enderhopper.capability.EnderStorageProvider;
import com.ferri.arnus.enderhopper.network.EnderChannel;
import com.ferri.arnus.enderhopper.network.HopperUUIDPacket;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
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
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;

public class EnderBundle extends Item {

	public EnderBundle() {
		super(new Properties().stacksTo(1).defaultDurability(320).tab(ItemRegistry.ENDERBUNDLETAB));
	}
	
	@Override
	public InteractionResult useOn(UseOnContext pContext) {
		BlockEntity be = pContext.getLevel().getBlockEntity(pContext.getClickedPos());
		if (be instanceof EnderHopperBE hopper) {
			pContext.getItemInHand().getCapability(EnderStorageProvider.ENDERSTORAGE).ifPresent(cap -> {
				if (!pContext.getLevel().isClientSide && cap.getPosistion() != BlockPos.ZERO) {
					BlockEntity old = cap.getLevel(pContext.getLevel()).getBlockEntity(cap.getPosistion());
					if (old instanceof EnderHopperBE oldhopper) {
						oldhopper.setBound(false);
					}
				}
				cap.setLevel(pContext.getLevel());
				cap.setPosistion(pContext.getClickedPos());
				UUID randomUUID = UUID.randomUUID();
				cap.setUUID(randomUUID);
				hopper.setUuid(randomUUID);
				hopper.setBound(true);
				if (!pContext.getLevel().isClientSide) {
					EnderChannel.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> pContext.getLevel().getChunkAt(pContext.getClickedPos())), new HopperUUIDPacket(pContext.getClickedPos(), randomUUID));
				}
				if (pContext.getItemInHand().hasCustomHoverName()) {
					hopper.setCustomName(pContext.getItemInHand().getHoverName());
				}
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
	public int getRGBDurabilityForDisplay(ItemStack stack) {
		return Mth.color(0.4F, 0.4F, 1.0F);
	}
	
	@Override
	public Optional<TooltipComponent> getTooltipImage(ItemStack pStack) {
		NonNullList<ItemStack> list = NonNullList.withSize(5, ItemStack.EMPTY);
		ContainerHelper.loadAllItems(pStack.getOrCreateTag().getCompound("enderbundleitems"), list);
		return Optional.of(new EnderBundleToolTip(list));
	}
	
	@Override
	public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {
		if (pCategory != ItemRegistry.ENDERBUNDLETAB) {
			return;
		}
		for (DyeColor c : DyeColor.values()) {
			ItemStack stack = new ItemStack(this);
			stack.getCapability(DyeProvider.DYEABLE).ifPresent(dye -> {
				dye.setColour(c.getMaterialColor().col);
			});
			pItems.add(stack);
		}
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
