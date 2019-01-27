package net.mackdoodler.primal;

import net.mackdoodler.primal.blocks.ModBlocks;
import net.mackdoodler.primal.items.ButcheryItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class PrimalCreativeTab extends CreativeTabs{
	public PrimalCreativeTab() {
		super(PrimalMod.modId);
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(ButcheryItems.butcherCleaver);
	}
}
