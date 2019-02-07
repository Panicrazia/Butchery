package net.mackdoodler.butchery.common.items;

import net.mackdoodler.butchery.ButcheryMod;
import net.mackdoodler.butchery.api.TranquilizerHandler;
import net.mackdoodler.butchery.common.blocks.BlockCorpse;
import net.mackdoodler.butchery.common.blocks.ButcheryBlocks;
import net.mackdoodler.butchery.common.capabilities.CapabilityTranquilizer;
import net.mackdoodler.butchery.common.capabilities.ITranquilizer;
import net.mackdoodler.butchery.common.handlers.SlimeHandler;
import net.mackdoodler.butchery.common.potions.ButcheryPotions;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ButcherCleaver extends Item{
	
	protected String name;
	
	public ButcherCleaver(String name){
		this.name=name;
		
		setTranslationKey(name);
		setRegistryName(name);
		setCreativeTab(ButcheryMod.creativeTab);
		
		this.maxStackSize = 1;
	}
	
	public void registerItemModel(){
		ButcheryMod.proxy.registerItemRenderer(this, 0, name);
	}
	
	/**
     * Called when a Block is right-clicked with this Item
     */
	@Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	if(worldIn.getBlockState(pos).getBlock() == ButcheryBlocks.blockCorpse){
    		((BlockCorpse) worldIn.getBlockState(pos).getBlock()).carveItUp(player,worldIn,pos);
    	}
    	
        return EnumActionResult.PASS;
    }
	
	/**
     * Chops a slimes brain out
     */
	@Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand){
		if(!playerIn.getEntityWorld().isRemote && target instanceof EntitySlime){
			if(target.hasCapability(CapabilityTranquilizer.TRANQUILIZER_CAPABILITY, null)){
				
				ITranquilizer tranquilizerCap = target.getCapability(CapabilityTranquilizer.TRANQUILIZER_CAPABILITY, null);
				if(!tranquilizerCap.getModified()){
					
					if(SlimeHandler.slimeVegitization((EntityLiving)target)){
						
						tranquilizerCap.setModified(true);
						
						EntityItem entityitem = new EntityItem(target.getEntityWorld(), target.posX, target.posY, target.posZ, new ItemStack(ButcheryItems.itemSlimeyCore, 1));
						
						target.getEntityWorld().spawnEntity(entityitem);
						return true;
						
					}
				}
			}
		}
		
		return false;
    }
	
	/**
	 * for testing purposes
	 *//*
	@Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
		TranquilizerHandler.applyTranquilizer(target, 10, 10);
        return true;
    }*/
    
}
