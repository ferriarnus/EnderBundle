package com.ferri.arnus.enderhopper.blockentities;

import com.ferri.arnus.enderhopper.EnderHoppers;
import com.ferri.arnus.enderhopper.blocks.BlockRegistry;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockEntityRegistry {

	public static final DeferredRegister<BlockEntityType<? extends BlockEntity>> BLOCKENTITYTYPE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, EnderHoppers.MODID);
	
	public static void register() {
		BLOCKENTITYTYPE.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	public static final RegistryObject<BlockEntityType<EnderHopperBE>> ENDERHOPPER = BLOCKENTITYTYPE.register("enderhopper", () -> BlockEntityType.Builder.of((p,s) -> new EnderHopperBE(p, s), BlockRegistry.ENDERHOPPER.get()).build(null));
}
