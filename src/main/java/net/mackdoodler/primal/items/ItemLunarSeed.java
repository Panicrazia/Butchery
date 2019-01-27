package net.mackdoodler.primal.items;

import net.mackdoodler.primal.PrimalMod;
import net.mackdoodler.primal.blocks.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;

public class ItemLunarSeed extends ItemSeeds{
	
	public ItemLunarSeed(){
		super(ModBlocks.cropLunarLilly, Blocks.FARMLAND);
		setTranslationKey("lunar_seed");
		setRegistryName("lunar_seed");
		
		setCreativeTab(PrimalMod.creativeTab);
	}
	
	public void registerItemModel(){
		PrimalMod.proxy.registerItemRenderer(this, 0, "lunar_seed");
	}
}

