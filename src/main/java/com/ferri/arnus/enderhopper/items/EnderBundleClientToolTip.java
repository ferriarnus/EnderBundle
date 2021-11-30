package com.ferri.arnus.enderhopper.items;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientBundleTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

public class EnderBundleClientToolTip implements ClientTooltipComponent{
	
	 private final NonNullList<ItemStack> items;

	public EnderBundleClientToolTip(EnderBundleToolTip pBundleTooltip) {
		items = pBundleTooltip.getItems();
	}

	@Override
	public int getHeight() {
		return 26;
	}

	@Override
	public int getWidth(Font pFont) {
		return 92;
	}
	
	@Override
	public void renderImage(Font pFont, int pMouseX, int pMouseY, PoseStack pPoseStack, ItemRenderer pItemRenderer, int pBlitOffset) {
		for (int i1 = 0; i1 < items.size(); ++i1) {
			int j1 = pMouseX + i1 * 18 + 1;
			this.renderSlot(j1, pMouseY +1 , i1, pFont, pPoseStack, pItemRenderer, pBlitOffset);
		}
	}
	
	private void renderSlot(int pX, int pY, int pItemIndex, Font pFont, PoseStack pPoseStack, ItemRenderer pItemRenderer, int pBlitOffset) {
		ItemStack itemstack = this.items.get(pItemIndex);
		this.blit(pPoseStack, pX, pY, pBlitOffset, 0, 0, 18, 20);
		pItemRenderer.renderAndDecorateItem(itemstack, pX + 1, pY + 1, pItemIndex);
		pItemRenderer.renderGuiItemDecorations(pFont, itemstack, pX + 1, pY + 1);
	}
	
	private void blit(PoseStack pPoseStack, int pX, int pY, int pBlitOffset, float x, float y, int w, int h) {
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, ClientBundleTooltip.TEXTURE_LOCATION);
		GuiComponent.blit(pPoseStack, pX, pY, pBlitOffset, x, y, w, h, 128, 128);
	}
}
