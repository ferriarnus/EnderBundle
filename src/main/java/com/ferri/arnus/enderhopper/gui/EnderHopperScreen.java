package com.ferri.arnus.enderhopper.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class EnderHopperScreen extends AbstractContainerScreen<EnderHopperContainer>{

	private static final ResourceLocation HOPPER = new ResourceLocation("textures/gui/container/hopper.png");

	public EnderHopperScreen(EnderHopperContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
		super(pMenu, pPlayerInventory, pTitle);
		this.passEvents = false;
		this.imageHeight = 133;
		this.inventoryLabelY = this.imageHeight - 94;
	}
	
	@Override
	public void render(PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
		this.renderBg(pMatrixStack, pPartialTicks, pMouseX, pMouseY);
		super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
		this.renderTooltip(pMatrixStack, pMouseX, pMouseY);
	}

	@Override
	protected void renderBg(PoseStack pPoseStack, float pPartialTicks, int pMouseX, int pMouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, HOPPER);
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		this.blit(pPoseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
		
	}

}
