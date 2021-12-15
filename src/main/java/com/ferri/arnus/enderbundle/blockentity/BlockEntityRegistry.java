package com.ferri.arnus.enderbundle.blockentity;

import com.ferri.arnus.enderbundle.EnderBundleMain;
import com.ferri.arnus.enderbundle.block.BlockRegistry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityRegistry {

	public static BlockEntityType<EnderHopperBE> ENDERHOPPER;
	
	public static void register() {
		ENDERHOPPER = Registry.register(Registry.BLOCK_ENTITY_TYPE, new ResourceLocation(EnderBundleMain.MODID, "enderhopper"), FabricBlockEntityTypeBuilder.create(EnderHopperBE::new, new Block[] {BlockRegistry.ENDERHOPPER}).build(null));
	}
}
