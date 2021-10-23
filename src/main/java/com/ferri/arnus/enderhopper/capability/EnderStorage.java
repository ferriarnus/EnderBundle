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
	
	public EnderStorage() {
	}
	
	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		nbt.put("pos", NbtUtils.writeBlockPos(pos));
		nbt.putUUID("stack", uuid);
		nbt.putString("dimension", level);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		this.pos = NbtUtils.readBlockPos(nbt.getCompound("pos"));
		this.uuid = nbt.getUUID("stack");
		this.level = nbt.getString("dimension");
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
		return levelin.getServer().getLevel(ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(level)));
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

}
