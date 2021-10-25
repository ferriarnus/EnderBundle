package com.ferri.arnus.enderhopper.crafting;

import com.ferri.arnus.enderhopper.EnderBundleMain;
import com.ferri.arnus.enderhopper.capability.DyeProvider;
import com.ferri.arnus.enderhopper.capability.IDyeable;
import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class DyeRecipe implements CraftingRecipe{
	
	public static final RecipeSerializer<DyeRecipe> SERIALIZER = new Serializer();

	public DyeRecipe() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean matches(CraftingContainer pContainer, Level pLevel) {
		ItemStack item = ItemStack.EMPTY;
		ItemStack dye = ItemStack.EMPTY;
		for (int i =0; i< pContainer.getContainerSize(); i++) {
			if (!pContainer.getItem(i).isEmpty()) {
				LazyOptional<IDyeable> capability = pContainer.getItem(i).getCapability(DyeProvider.DYEABLE);
				if (capability.isPresent() && item.isEmpty()) {
					item = pContainer.getItem(i);
				} else if (capability.isPresent() && !item.isEmpty()) {
					return false;
				}
				for (DyeColor d : DyeColor.values()) {
					if (d.getTag().contains(pContainer.getItem(i).getItem()) && dye.isEmpty() ) {
						dye = pContainer.getItem(i);
					} else if (d.getTag().contains(pContainer.getItem(i).getItem()) && !dye.isEmpty()) {
						return false;
					}
				}
				if (dye != pContainer.getItem(i) && item != pContainer.getItem(i)) {
					return false;
				}
			}
		}
		return (!item.isEmpty() && !dye.isEmpty());
	}

	@Override
	public ItemStack assemble(CraftingContainer pContainer) {
		ItemStack item = ItemStack.EMPTY;
		ItemStack dye = ItemStack.EMPTY;
		for (int i =0; i< pContainer.getContainerSize(); i++) {
			if (!pContainer.getItem(i).isEmpty()) {
				LazyOptional<IDyeable> capability = pContainer.getItem(i).getCapability(DyeProvider.DYEABLE);
				if (capability.isPresent() && item.isEmpty()) {
					item = pContainer.getItem(i);
				} else if (DyeColor.getColor(pContainer.getItem(i)) != null && dye.isEmpty()) {
					dye = pContainer.getItem(i);
				}
			}
		}
		final ItemStack dye2 = dye;
		ItemStack result = item.copy();
		result.getCapability(DyeProvider.DYEABLE).ifPresent(d -> {
			d.setColour(DyeColor.getColor(dye2).getMaterialColor().col);
		});
		return result;
	}

	@Override
	public boolean canCraftInDimensions(int pWidth, int pHeight) {
		return true;
	}

	@Override
	public ItemStack getResultItem() {
		return ItemStack.EMPTY;
	}

	@Override
	public ResourceLocation getId() {
		return new ResourceLocation(EnderBundleMain.MODID, "dyeing");
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}
	
	@Override
	public boolean isSpecial() {
		return true;
	}
	
	static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<DyeRecipe> {

		@Override
		public DyeRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
			// TODO Auto-generated method stub
			return new DyeRecipe();
		}

		@Override
		public DyeRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
			// TODO Auto-generated method stub
			return new DyeRecipe();
		}

		@Override
		public void toNetwork(FriendlyByteBuf pBuffer, DyeRecipe pRecipe) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
