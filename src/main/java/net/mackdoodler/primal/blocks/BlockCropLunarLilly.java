package net.mackdoodler.primal.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.Random;

import net.mackdoodler.primal.PrimalMod;
import net.mackdoodler.primal.items.ButcheryItems;

public class BlockCropLunarLilly extends BlockCrops{
	
	protected static final AxisAlignedBB[] LILLY_AABB = new AxisAlignedBB[] {
			new AxisAlignedBB(4/16D, 0/16D, 4/16D, 12/16D, 2/16D, 12/16D), 	//0
			new AxisAlignedBB(4/16D, 0/16D, 4/16D, 12/16D, 4/16D, 12/16D), 	//1
			new AxisAlignedBB(4/16D, 0/16D, 4/16D, 12/16D, 7/16D, 12/16D), 	//2
			new AxisAlignedBB(4/16D, 0/16D, 4/16D, 12/16D, 9/16D, 12/16D), 	//3
			new AxisAlignedBB(4/16D, 0/16D, 4/16D, 12/16D, 11/16D, 12/16D),	//4
			new AxisAlignedBB(4/16D, 0/16D, 4/16D, 12/16D, 14/16D, 12/16D),	//5
			new AxisAlignedBB(4/16D, 0/16D, 4/16D, 12/16D, 15/16D, 12/16D), //6
			new AxisAlignedBB(4/16D, 0/16D, 4/16D, 12/16D, 15/16D, 12/16D)};//7

	public BlockCropLunarLilly() {
		setTranslationKey("crop_lunar_lilly");
		setRegistryName("crop_lunar_lilly");
		setCreativeTab(PrimalMod.creativeTab);
	}

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return LILLY_AABB[((Integer)state.getValue(AGE)).intValue()];
    }
	
	@Override
	protected Item getSeed() {
		return ButcheryItems.lunarSeed;
	}
	
	@Override
	protected Item getCrop() {
		//since itemTranquilizer with a metadata of 0 is the plant we are good
		//otherwise we would have to modify the damageDropped method from minecraft.block.Block
		return ButcheryItems.itemTranquilizer;
	}
}
