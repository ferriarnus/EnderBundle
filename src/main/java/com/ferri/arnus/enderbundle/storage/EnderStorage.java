package com.ferri.arnus.enderbundle.storage;

import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EnderStorage {
	
	private ItemStack stack;

	public EnderStorage(ItemStack stack) {
		this.stack = stack;
	}
	
	private CompoundTag getTag() {
		return stack.getOrCreateTagElement("enderstorage");
	}

	public BlockPos getPosistion() {
		if(!getTag().contains("pos")) {
			getTag().put("pos", NbtUtils.writeBlockPos(BlockPos.ZERO));
		}
		return NbtUtils.readBlockPos(getTag().getCompound("pos"));
	}

	public void setPosistion(BlockPos pos) {
		getTag().put("pos", NbtUtils.writeBlockPos(pos));
	}

	public Level getLevel(Level levelin) {
		Level l = levelin.getServer().getLevel(ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(getTag().getString("level"))));
		return l == null? levelin.getServer().getLevel(Level.OVERWORLD) : l;
	}

	public void setLevel(Level level) {
		getTag().putString("level", level.dimension().location().toString());
	}

	public UUID getUUID() {
		if(!getTag().contains("uuid")) {
			getTag().putUUID("uuid", UUID.randomUUID());
		}
		return getTag().getUUID("uuid");
	}

	public void setUUID(UUID uuid) {
		getTag().putUUID("uuid", uuid);
	}

	public boolean isEmpty() {
		if(! getTag().contains("empty")) {
			getTag().putBoolean("empty", true);
		}
		return getTag().getBoolean("empty");
	}

	public void setEmpty(boolean empty) {
		getTag().putBoolean("empty", empty);
	}

}
