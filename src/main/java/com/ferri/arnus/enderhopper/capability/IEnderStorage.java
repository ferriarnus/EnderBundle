package com.ferri.arnus.enderhopper.capability;

import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;

public interface IEnderStorage extends INBTSerializable<CompoundTag>{

	BlockPos getPosistion();
	void setPosistion(BlockPos pos);
	
	Level getLevel(Level levelin);
	void setLevel(Level level);
	
	UUID getUUID();
	void setUUID(UUID uuid);
	
	boolean isEmpty();
	void setEmpty(boolean empty);
}
