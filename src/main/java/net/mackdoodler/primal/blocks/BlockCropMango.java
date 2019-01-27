package net.mackdoodler.primal.blocks;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import java.util.Random;

import net.mackdoodler.primal.PrimalMod;
import net.mackdoodler.primal.items.PrimalItems;

public class BlockCropMango extends BlockCrops{
	
	public BlockCropMango() {
		setTranslationKey("crop_mango");
		setRegistryName("crop_mango");
		setCreativeTab(PrimalMod.creativeTab);
	}
	
	@Override
	protected Item getSeed() {
		return PrimalItems.mangoSeed;
	}
	
	@Override
	protected Item getCrop() {
		return PrimalItems.mango;
	}
}
