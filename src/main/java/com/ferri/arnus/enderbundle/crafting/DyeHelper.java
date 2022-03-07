package com.ferri.arnus.enderbundle.crafting;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DyeHelper {
	public static final TagKey<Item> RED = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("c", "red_dyes"));
	public static final TagKey<Item> BLUE = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("c", "blue_dyes"));
	public static final TagKey<Item> BLACK = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("c", "black_dyes"));
	public static final TagKey<Item> BROWN = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("c", "brown_dyes"));
	public static final TagKey<Item> CYAN = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("c", "cyan_dyes"));
	public static final TagKey<Item> GRAY = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("c", "gray_dyes"));
	public static final TagKey<Item> GREEN = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("c", "green_dyes"));
	public static final TagKey<Item> LIGHT_BLUE = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("c", "light_blue_dyes"));
	public static final TagKey<Item> LIGHT_GRAY = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("c", "light_grey_dyes"));
	public static final TagKey<Item> LIME = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("c", "lime_dyes"));
	public static final TagKey<Item> MAGENTA = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("c", "magenta_dyes"));
	public static final TagKey<Item> ORANGE = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("c", "orange_dyes"));
	public static final TagKey<Item> PINK = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("c", "pink_dyes"));
	public static final TagKey<Item> PURPLE = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("c", "purple_dyes"));
	public static final TagKey<Item> WHITE = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("c", "white_dyes"));
	public static final TagKey<Item> YELLOW = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("c", "yellow_dyes"));
	
	static int getColor(ItemStack item) {
		if (item.is(RED)) {
			return DyeColor.RED.getMaterialColor().col;
		}
		if (item.is(BLUE)) {
			return DyeColor.BLUE.getMaterialColor().col;
		}
		if (item.is(BLACK)) {
			return DyeColor.BLACK.getMaterialColor().col;
		}
		if (item.is(BROWN)) {
			return DyeColor.BROWN.getMaterialColor().col;
		}
		if (item.is(CYAN)) {
			return DyeColor.CYAN.getMaterialColor().col;
		}
		if (item.is(GRAY)) {
			return DyeColor.GRAY.getMaterialColor().col;
		}
		if (item.is(GREEN)) {
			return DyeColor.GREEN.getMaterialColor().col;
		}
		if (item.is(LIGHT_BLUE)) {
			return DyeColor.LIGHT_BLUE.getMaterialColor().col;
		}
		if (item.is(LIGHT_GRAY)) {
			return DyeColor.LIGHT_GRAY.getMaterialColor().col;
		}
		if (item.is(LIME)) {
			return DyeColor.LIME.getMaterialColor().col;
		}
		if (item.is(MAGENTA)) {
			return DyeColor.MAGENTA.getMaterialColor().col;
		}
		if (item.is(ORANGE)) {
			return DyeColor.ORANGE.getMaterialColor().col;
		}
		if (item.is(PINK)) {
			return DyeColor.PINK.getMaterialColor().col;
		}
		if (item.is(PURPLE)) {
			return DyeColor.PURPLE.getMaterialColor().col;
		}
		if (item.is(WHITE)) {
			return DyeColor.WHITE.getMaterialColor().col;
		}
		if (item.is(YELLOW)) {
			return DyeColor.YELLOW.getMaterialColor().col;
		}
		return -1;
	}

}
