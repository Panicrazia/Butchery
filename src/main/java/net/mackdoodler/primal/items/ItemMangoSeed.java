package net.mackdoodler.primal.items;

import net.mackdoodler.primal.PrimalMod;
import net.mackdoodler.primal.blocks.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;

public class ItemMangoSeed extends ItemSeeds{
	
	public ItemMangoSeed(){
		super(ModBlocks.cropMango, Blocks.FARMLAND);
		setTranslationKey("mango_seed");
		setRegistryName("mango_seed");
		
		setCreativeTab(PrimalMod.creativeTab);
	}
	
	public void registerItemModel(){
		PrimalMod.proxy.registerItemRenderer(this, 0, "mango_seed");
	}
}

