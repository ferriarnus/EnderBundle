package com.ferri.arnus.enderhopper.capability;

import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class EnderStorage implements IEnderStorage{
	
	private BlockPos pos = BlockPos.ZERO;
	private String level = "";
	private UUID uuid = UUID.randomUUID();
	private boolean empty = true;
	
	public EnderStorage() {
	}
	
	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		nbt.put("pos", NbtUtils.writeBlockPos(pos));
		nbt.putUUID("stack", uuid);
		nbt.putString("dimension", level);
		nbt.putBoolean("empty", empty);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		this.pos = NbtUtils.readBlockPos(nbt.getCompound("pos"));
		try {
			this.uuid = nbt.getUUID("stack");
		} catch (Exception e) {
			
		}
		this.level = nbt.getString("dimension");
		this.empty = nbt.getBoolean("empty");
	}

	@Override
	public BlockPos getPosistion() {
		return this.pos;
	}

	@Override
	public void setPosistion(BlockPos pos) {
		this.pos = pos;
	}

	@Override
	public Level getLevel(Level levelin) {
		Level l = levelin.getServer().getLevel(ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(level)));
		return l == null? levelin.getServer().getLevel(Level.OVERWORLD) : l;
	}

	@Override
	public void setLevel(Level level) {
		this.level = level.dimension().location().toString();
	}

	@Override
	public UUID getUUID() {
		return this.uuid;
	}

	@Override
	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	@Override
	public boolean isEmpty() {
		return this.empty;
	}

	@Override
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

}
