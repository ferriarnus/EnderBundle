package com.ferri.arnus.enderbundle.crafting;

import com.ferri.arnus.enderbundle.item.ItemRegistry;
import com.ferri.arnus.enderbundle.storage.ColorStorage;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class Dyeing extends ShapelessRecipe{

	public static final RecipeSerializer<Dyeing> SERIALIZER = new Serializer();

	public Dyeing(ResourceLocation pId, String pGroup, ItemStack pResult, NonNullList<Ingredient> pIngredients) {
		super(pId, pGroup, pResult, pIngredients);
	}
	
	@Override
	public ItemStack assemble(CraftingContainer pInv) {
		ItemStack stack = ItemStack.EMPTY;
		ItemStack dye = ItemStack.EMPTY;
		for (int i = 0; i < pInv.getContainerSize(); i++) {
			if (pInv.getItem(i).is(ItemRegistry.ENDERBUNDLE)) {
				stack = pInv.getItem(i);
			}
			if (DyeHelper.getColor(pInv.getItem(i)) != -1) {
				dye =  pInv.getItem(i);
			}
		}
		final ItemStack dye2 = dye.copy();
		ItemStack result = stack.copy();
		new ColorStorage(result).setColor(DyeHelper.getColor(dye2));
		return result;
	}
	
	@Override
	public ItemStack getResultItem() {
		ItemStack item = getIngredients().get(0).getItems()[0];
		ItemStack dye = getIngredients().get(1).getItems()[0];
		ItemStack result = item.copy();
		new ColorStorage(result).setColor(DyeHelper.getColor(dye));
		return result;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}
	
	static class Serializer implements RecipeSerializer<Dyeing> {

		@Override
		public Dyeing fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
			String s = GsonHelper.getAsString(pSerializedRecipe, "group", "");
			NonNullList<Ingredient> nonnulllist = itemsFromJson(GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients"));
			if (nonnulllist.isEmpty()) {
				throw new JsonParseException("No ingredients for shapeless recipe");
			} else if (nonnulllist.size() > 9) {
				throw new JsonParseException("Too many ingredients for shapeless recipe. The maximum is " +9);
			} else {
				NonNullList<ItemStack> pItems = NonNullList.create();
				for (DyeColor c : DyeColor.values()) {
					ItemStack stack = new ItemStack(ItemRegistry.ENDERBUNDLE);
					new ColorStorage(stack).setColor(c.getMaterialColor().col);
					pItems.add(stack);
				}
				Ingredient n = Ingredient.of(pItems.stream());
				nonnulllist.set(0, n);
				ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));
				return new Dyeing(pRecipeId, s, itemstack, nonnulllist);
			}
		}
			
		private static NonNullList<Ingredient> itemsFromJson(JsonArray pIngredientArray) {
			NonNullList<Ingredient> nonnulllist = NonNullList.create();
			for(int i = 0; i < pIngredientArray.size(); ++i) {
				Ingredient ingredient = Ingredient.fromJson(pIngredientArray.get(i));
				if (!ingredient.isEmpty()) {
					nonnulllist.add(ingredient);
				}
			}
			return nonnulllist;
		}

		@Override
		public Dyeing fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
			String s = pBuffer.readUtf();
			int i = pBuffer.readVarInt();
			NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);
			
			for(int j = 0; j < nonnulllist.size(); ++j) {
				nonnulllist.set(j, Ingredient.fromNetwork(pBuffer));
			}
			
			ItemStack itemstack = pBuffer.readItem();
			return new Dyeing(pRecipeId,s,itemstack,nonnulllist);
		}
		
		@Override
		public void toNetwork(FriendlyByteBuf pBuffer, Dyeing pRecipe) {
			pBuffer.writeUtf(pRecipe.getGroup());
			pBuffer.writeVarInt(pRecipe.getIngredients().size());
			
			for(Ingredient ingredient : pRecipe.getIngredients()) {
				ingredient.toNetwork(pBuffer);
			}
			
			pBuffer.writeItem(pRecipe.getResultItem());
		}
		
	}

}
