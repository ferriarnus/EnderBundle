package com.ferri.arnus.enderbundle.components;

import java.util.UUID;

import dev.onyxstudios.cca.api.v3.item.ItemComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EnderStorageComponent extends ItemComponent {
	
	public EnderStorageComponent(ItemStack stack) {
		super(stack);
	}

	public BlockPos getPosistion() {
		if(!hasTag("pos", Tag.TAG_COMPOUND)) {
			putCompound("pos", NbtUtils.writeBlockPos(BlockPos.ZERO));
		}
		return NbtUtils.readBlockPos(getCompound("pos"));
	}

	public void setPosistion(BlockPos pos) {
		putCompound("pos", NbtUtils.writeBlockPos(pos));
	}

	public Level getLevel(Level levelin) {
		Level l = levelin.getServer().getLevel(ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(getString("level"))));
		return l == null? levelin.getServer().getLevel(Level.OVERWORLD) : l;
	}

	public void setLevel(Level level) {
		putString("level", level.dimension().location().toString());
	}

	public UUID getUUID() {
		if(!hasTag("uuid")) {
			putUuid("uuid", UUID.randomUUID());
		}
		return getUuid("uuid") == null? UUID.randomUUID() : getUuid("uuid");
	}

	public void setUUID(UUID uuid) {
		putUuid("uuid", uuid);
	}

	public boolean isEmpty() {
		if(! hasTag("empty")) {
			putBoolean("empty", true);
		}
		return getBoolean("empty");
	}

	public void setEmpty(boolean empty) {
		putBoolean("empty", empty);
	}

}
