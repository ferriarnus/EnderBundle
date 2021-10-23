package com.ferri.arnus.enderhopper.gui;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class EnderHopperScreen extends AbstractContainerScreen<EnderHopperContainer>{

	public EnderHopperScreen(EnderHopperContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
		super(pMenu, pPlayerInventory, pTitle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void renderBg(PoseStack pPoseStack, float pPartialTicks, int pMouseX, int pMouseY) {
		// TODO Auto-generated method stub
		
	}

}
