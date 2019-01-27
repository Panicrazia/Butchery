package net.mackdoodler.primal.items;

import net.mackdoodler.primal.PrimalMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item{
	
	protected String name;
	
	public ItemBase(String name){
		this.name=name;
		
		setTranslationKey(name);
		setRegistryName(name);
		
		setCreativeTab(PrimalMod.creativeTab);
	}
	
	public void registerItemModel(){
		PrimalMod.proxy.registerItemRenderer(this, 0, name);
	}
}
