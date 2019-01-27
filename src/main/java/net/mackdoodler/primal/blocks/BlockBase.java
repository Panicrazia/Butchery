package net.mackdoodler.primal.blocks;

import net.mackdoodler.primal.PrimalMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block{
	
	protected String name;
	
	public BlockBase(Material material, String name){
		super(material);
		
		this.name = name;
		
		setTranslationKey(name);
		setRegistryName(name);
		
		setCreativeTab(PrimalMod.creativeTab);
	}
	
	public void registerItemModel(Item itemBlock){
		PrimalMod.proxy.registerItemRenderer(itemBlock, 0, name);
	}
	
	public Item createItemBlock(){
		return new ItemBlock(this).setRegistryName(getRegistryName());
	}
}
