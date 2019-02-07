package net.mackdoodler.butchery.common.items;

import net.mackdoodler.butchery.ButcheryMod;
import net.mackdoodler.butchery.common.blocks.ButcheryBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class DivinationRod extends Item{
	/*
	 * its a debug rod ok?
	 */
	protected String name;
	
	public DivinationRod(String name){
		this.name=name;
		
		setTranslationKey(name);
		setRegistryName(name);
		
		setCreativeTab(ButcheryMod.creativeTab);
		this.maxStackSize = 1;
	}
	
	public void registerItemModel(){
		ButcheryMod.proxy.registerItemRenderer(this, 0, name);
	}
	
	@Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft){
		if(worldIn.isRemote){
			entityLiving.sendMessage(new TextComponentString("You have right clicked with a " + this.getRegistryName()));
		}
    }
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		if(worldIn.getBlockState(pos).getBlock().equals(Block.getBlockFromName("sand"))){
			//if(!worldIn.isRemote){
				if(worldIn.isRaining()){
					worldIn.getWorldInfo().setRaining(false);
					worldIn.setRainStrength(0);
					worldIn.getWorldInfo().setThundering(false);
				}
				else{
					worldIn.getWorldInfo().setRaining(true);
					worldIn.setRainStrength(1);
					worldIn.getWorldInfo().setThundering(true);
				}
			//}
		}
		else if(worldIn.getBlockState(pos).getBlock() == ButcheryBlocks.cropLunarLilly){
			if(worldIn.isRemote){
				player.sendMessage(new TextComponentString("grow my small beauty"));
			}
		}
		else if(worldIn.getBlockState(pos).getBlock().equals(Block.getBlockFromName("grass"))){
			if(!worldIn.isRemote){
				worldIn.setWorldTime(1000);
			}
		}
		
        return EnumActionResult.SUCCESS;
    }
}
