package net.mackdoodler.butchery;

import net.mackdoodler.butchery.common.blocks.ButcheryBlocks;
import net.mackdoodler.butchery.common.items.ButcheryItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ButcheryCreativeTab extends CreativeTabs{
	public ButcheryCreativeTab() {
		super(ButcheryMod.modId);
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(ButcheryItems.butcherCleaver);
	}
}
