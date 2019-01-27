package net.mackdoodler.primal.items;

import net.mackdoodler.primal.PrimalMod;
import net.mackdoodler.primal.blocks.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;

public class ItemLunarSeed extends ItemSeeds{
	
	public ItemLunarSeed(String name){
		super(ModBlocks.cropLunarLilly, Blocks.FARMLAND);
		setTranslationKey(name);
		setRegistryName(name);
		
		setCreativeTab(PrimalMod.creativeTab);
	}
	
	public void registerItemModel(){
		PrimalMod.proxy.registerItemRenderer(this, 0, "lunar_seed");
	}
}

