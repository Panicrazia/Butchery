package net.mackdoodler.butchery.common.items;

import net.mackdoodler.butchery.ButcheryMod;
import net.mackdoodler.butchery.common.blocks.ButcheryBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;

public class ItemLunarSeed extends ItemSeeds{
	
	public ItemLunarSeed(String name){
		super(ButcheryBlocks.cropLunarLilly, Blocks.FARMLAND);
		setTranslationKey(name);
		setRegistryName(name);
		
		setCreativeTab(ButcheryMod.creativeTab);
	}
	
	public void registerItemModel(){
		ButcheryMod.proxy.registerItemRenderer(this, 0, "lunar_seed");
	}
}

