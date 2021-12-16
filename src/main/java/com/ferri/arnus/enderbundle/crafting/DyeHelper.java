package com.ferri.arnus.enderbundle.crafting;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;

public class DyeHelper {
	
	public static final Tag<Item> RED = TagFactory.ITEM.create(new ResourceLocation("c", "red_dyes"));
	public static final Tag<Item> BLUE = TagFactory.ITEM.create(new ResourceLocation("c", "blue_dyes"));
	public static final Tag<Item> BLACK = TagFactory.ITEM.create(new ResourceLocation("c", "black_dyes"));
	public static final Tag<Item> BROWN = TagFactory.ITEM.create(new ResourceLocation("c", "brown_dyes"));
	public static final Tag<Item> CYAN = TagFactory.ITEM.create(new ResourceLocation("c", "cyan_dyes"));
	public static final Tag<Item> GRAY = TagFactory.ITEM.create(new ResourceLocation("c", "gray_dyes"));
	public static final Tag<Item> GREEN = TagFactory.ITEM.create(new ResourceLocation("c", "green_dyes"));
	public static final Tag<Item> LIGHT_BLUE = TagFactory.ITEM.create(new ResourceLocation("c", "light_blue_dyes"));
	public static final Tag<Item> LIGHT_GRAY = TagFactory.ITEM.create(new ResourceLocation("c", "light_grey_dyes"));
	public static final Tag<Item> LIME = TagFactory.ITEM.create(new ResourceLocation("c", "lime_dyes"));
	public static final Tag<Item> MAGENTA = TagFactory.ITEM.create(new ResourceLocation("c", "magenta_dyes"));
	public static final Tag<Item> ORANGE = TagFactory.ITEM.create(new ResourceLocation("c", "orange_dyes"));
	public static final Tag<Item> PINK = TagFactory.ITEM.create(new ResourceLocation("c", "pink_dyes"));
	public static final Tag<Item> PURPLE = TagFactory.ITEM.create(new ResourceLocation("c", "purple_dyes"));
	public static final Tag<Item> WHITE = TagFactory.ITEM.create(new ResourceLocation("c", "white_dyes"));
	public static final Tag<Item> YELLOW = TagFactory.ITEM.create(new ResourceLocation("c", "yellow_dyes"));
	
	static int getColor(Item item) {
		if (RED.contains(item)) {
			return DyeColor.RED.getMaterialColor().col;
		}
		if (BLUE.contains(item)) {
			return DyeColor.BLUE.getMaterialColor().col;
		}
		if (BLACK.contains(item)) {
			return DyeColor.BLACK.getMaterialColor().col;
		}
		if (BROWN.contains(item)) {
			return DyeColor.BROWN.getMaterialColor().col;
		}
		if (CYAN.contains(item)) {
			return DyeColor.CYAN.getMaterialColor().col;
		}
		if (GRAY.contains(item)) {
			return DyeColor.GRAY.getMaterialColor().col;
		}
		if (GREEN.contains(item)) {
			return DyeColor.GREEN.getMaterialColor().col;
		}
		if (LIGHT_BLUE.contains(item)) {
			return DyeColor.LIGHT_BLUE.getMaterialColor().col;
		}
		if (LIGHT_GRAY.contains(item)) {
			return DyeColor.LIGHT_GRAY.getMaterialColor().col;
		}
		if (LIME.contains(item)) {
			return DyeColor.LIME.getMaterialColor().col;
		}
		if (MAGENTA.contains(item)) {
			return DyeColor.MAGENTA.getMaterialColor().col;
		}
		if (ORANGE.contains(item)) {
			return DyeColor.ORANGE.getMaterialColor().col;
		}
		if (PINK.contains(item)) {
			return DyeColor.PINK.getMaterialColor().col;
		}
		if (PURPLE.contains(item)) {
			return DyeColor.PURPLE.getMaterialColor().col;
		}
		if (WHITE.contains(item)) {
			return DyeColor.WHITE.getMaterialColor().col;
		}
		if (YELLOW.contains(item)) {
			return DyeColor.YELLOW.getMaterialColor().col;
		}
		return 0;
	}

}
