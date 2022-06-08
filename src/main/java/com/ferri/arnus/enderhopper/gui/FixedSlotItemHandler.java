package com.ferri.arnus.enderhopper.gui;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class FixedSlotItemHandler extends SlotItemHandler
{

    public FixedSlotItemHandler(IItemHandler itemHandler, int slot, int xPosition, int yPosition)
    {
        super(itemHandler, slot, xPosition, yPosition);
    }

    @Override
    public void setChanged() {
    	super.setChanged();
    }
    
    
    @Override
    public void initialize(ItemStack p_219997_) {
    	super.set(p_219997_);
        this.setChanged();
     }
    
}
