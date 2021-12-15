package com.ferri.arnus.enderbundle.components;

import java.util.UUID;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class EnderStorageComponent implements ComponentV3{
	private BlockPos pos = BlockPos.ZERO;
	private UUID uuid = UUID.randomUUID();
	private String level = "";
	private boolean empty = true;

	@Override
	public void readFromNbt(CompoundTag tag) {
		this.pos = NbtUtils.readBlockPos(tag.getCompound("pos"));
		try {
			this.uuid = tag.getUUID("stack");
		} catch (Exception e) {
			
		}
		this.level = tag.getString("dimension");
		this.empty = tag.getBoolean("empty");
	}

	@Override
	public void writeToNbt(CompoundTag tag) {
		tag.put("pos", NbtUtils.writeBlockPos(pos));
		tag.putUUID("stack", uuid);
		tag.putString("dimension", level);
		tag.putBoolean("empty", empty);
	}

	public BlockPos getPosistion() {
		return this.pos;
	}

	public void setPosistion(BlockPos pos) {
		this.pos = pos;
	}

	public Level getLevel(Level levelin) {
		Level l = levelin.getServer().getLevel(ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(level)));
		return l == null? levelin.getServer().getLevel(Level.OVERWORLD) : l;
	}

	public void setLevel(Level level) {
		this.level = level.dimension().location().toString();
	}

	public UUID getUUID() {
		return this.uuid;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	public boolean isEmpty() {
		return this.empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

}
