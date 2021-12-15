package com.ferri.arnus.enderbundle.blockentity;

import java.util.List;
import java.util.UUID;

import com.ferri.arnus.enderbundle.EnderBundleMain;
import com.ferri.arnus.enderbundle.item.ItemRegistry;
import com.ferri.arnus.enderbundle.storage.EnderStorage;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.HopperMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class EnderHopperBE extends HopperBlockEntity{
	private UUID uuid = UUID.randomUUID();
	private boolean bound;
	
	public EnderHopperBE(BlockPos blockPos, BlockState blockState) {
		super(blockPos, blockState);
	}
	
	@Override
	protected Component getDefaultName() {
		return new TranslatableComponent(EnderBundleMain.MODID,"container.enderhopper");
	}
	
	@Override
	protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
		return new HopperMenu(i, inventory, this);
	}
	
	@Override
	public BlockEntityType<?> getType() {
		return  BlockEntityRegistry.ENDERHOPPER;
	}

	public static boolean playerClose(Level level, BlockPos pos, EnderHopperBE hopper) {
		List<Player> players = level.getEntitiesOfClass(Player.class, new AABB(pos).inflate(7, 7, 7), (p) -> {
			ItemStack stack = p.getMainHandItem();
			if (stack.is(ItemRegistry.ENDERBUNDLE)) {
				return new EnderStorage(stack).getUUID().equals(hopper.uuid);
			}
			return false;
		});
		if (!players.isEmpty()) {
			return true;
		}
		return false;
	}
	
	@Override
	protected void saveAdditional(CompoundTag compoundTag) {
		super.saveAdditional(compoundTag);
		compoundTag.putUUID("uuid", uuid);
	}
	
	@Override
	public void load(CompoundTag compoundTag) {
		super.load(compoundTag);
		this.uuid = compoundTag.getUUID("uuid");
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	
	public boolean isBound() {
		return bound;
	}

	public void setBound(boolean b) {
		this.bound = b;
	}
	
	@Override
    public NonNullList<ItemStack> getItems() {
        return super.getItems();
    }
    
}
