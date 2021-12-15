package com.ferri.arnus.enderbundle.renderer;

import java.util.Random;

import com.ferri.arnus.enderbundle.blockentity.EnderHopperBE;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;

public class EnderHopperRenderer implements BlockEntityRenderer<EnderHopperBE>{

	public EnderHopperRenderer(Context ctx) {
	}
	
	@Override
	public void render(EnderHopperBE pBlockEntity, float pPartialTicks, PoseStack pMatrixStack,
			MultiBufferSource pBuffer, int pCombinedLight, int pCombinedOverlay) {
		Matrix4f matrix4f = pMatrixStack.last().pose();
		this.renderFace(pBlockEntity, matrix4f, pBuffer.getBuffer(RenderType.endPortal()), 0.0F, 1.0F, 0.75F, 0.75F, 1.0F, 1.0F, 0.0F, 0.0F);
		Level level = pBlockEntity.getLevel();
		Random pRand = level.random;
		BlockPos pPos = pBlockEntity.getBlockPos();
		if (!EnderHopperBE.playerClose(level, pPos, pBlockEntity)) {
			return;
		}
		int j = pRand.nextInt(2) * 2 - 1;
		int k = pRand.nextInt(2) * 2 - 1;
		double d0 = (double)pPos.getX() + 0.5D + 0.25D * (double)j;
		double d1 = (double)((float)pPos.getY() + pRand.nextFloat());
		double d2 = (double)pPos.getZ() + 0.5D + 0.25D * (double)k;
		double d3 = (double)(pRand.nextFloat() * (float)j);
		double d4 = ((double)pRand.nextFloat() - 0.5D) * 0.125D;
		double d5 = (double)(pRand.nextFloat() * (float)k);
		level.addParticle(ParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);

	}
	
	private void renderFace(EnderHopperBE p_173695_, Matrix4f p_173696_, VertexConsumer p_173697_, float p_173698_, float p_173699_, float p_173700_, float p_173701_, float p_173702_, float p_173703_, float p_173704_, float p_173705_) {
		p_173697_.vertex(p_173696_, p_173698_, p_173700_, p_173702_).endVertex();
		p_173697_.vertex(p_173696_, p_173699_, p_173700_, p_173703_).endVertex();
		p_173697_.vertex(p_173696_, p_173699_, p_173701_, p_173704_).endVertex();
		p_173697_.vertex(p_173696_, p_173698_, p_173701_, p_173705_).endVertex();
	}
}
