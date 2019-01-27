package net.mackdoodler.primal.items;

import net.mackdoodler.primal.PrimalMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class NetherSpore extends ItemBase{
	
	public NetherSpore(){
		//change to be like the others
		super("netherSpore");
	}
	
	public void registerItemModel(){
		PrimalMod.proxy.registerItemRenderer(this, 0, name);
	}
}
