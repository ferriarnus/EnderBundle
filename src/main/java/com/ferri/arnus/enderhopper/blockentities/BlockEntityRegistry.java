package com.ferri.arnus.enderhopper.blockentities;

import com.ferri.arnus.enderhopper.EnderBundleMain;
import com.ferri.arnus.enderhopper.blocks.BlockRegistry;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegistry {

	public static final DeferredRegister<BlockEntityType<?>> BLOCKENTITYTYPE = DeferredRegister.create(ForgeRegistries.Keys.BLOCK_ENTITY_TYPES, EnderBundleMain.MODID);
	
	public static void register() {
		BLOCKENTITYTYPE.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	public static final RegistryObject<BlockEntityType<EnderHopperBE>> ENDERHOPPER = BLOCKENTITYTYPE.register("enderhopper", () -> BlockEntityType.Builder.of((p,s) -> new EnderHopperBE(p, s), BlockRegistry.ENDERHOPPER.get()).build(null));
}
