package net.mackdoodler.primal.blocks;

import java.util.Random;

import net.mackdoodler.primal.PrimalMod;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Calidarium extends BlockCauldron{
	
	protected String name = "calidarium";

	public Calidarium() {
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
	
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(this);
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this));
    }
}
