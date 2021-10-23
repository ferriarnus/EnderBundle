package com.ferri.arnus.enderhopper.gui;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class EnderHopperContainer extends AbstractContainerMenu{

	protected EnderHopperContainer(MenuType<?> pMenuType, int pContainerId) {
		super(pMenuType, pContainerId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean stillValid(Player pPlayer) {
		// TODO Auto-generated method stub
		return false;
	}

}
