package com.ferri.arnus.enderhopper.renderers;

import java.util.concurrent.atomic.AtomicInteger;

import com.ferri.arnus.enderhopper.capability.DyeProvider;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;

public class EnderBundleColor implements ItemColor{

	@Override
	public int getColor(ItemStack p_92672_, int p_92673_) {
		if (p_92673_ == 1) {
			AtomicInteger i = new AtomicInteger(0xffffff);
			p_92672_.getCapability(DyeProvider.DYEABLE).ifPresent(cap -> {
				i.set(cap.getColour());
			});
			return i.get();
		}
		return 0xffffff;
	}

}
