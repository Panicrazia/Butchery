package net.mackdoodler.butchery.common.items;

import java.util.Random;

import net.mackdoodler.butchery.ButcheryMod;
import net.mackdoodler.butchery.api.TranquilizerHandler;
import net.mackdoodler.butchery.client.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemThrowableDust extends Item{
	protected String name;
	
	public ItemThrowableDust(String name){
		this.name=name;
		
		setTranslationKey(name);
		setRegistryName(name);
		
		setCreativeTab(ButcheryMod.creativeTab);
	}
	
	public void registerItemModel(){
		ButcheryMod.proxy.registerItemRenderer(this, 0, name);
	}
	
	@Override
	/**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
		Random random = worldIn.rand;

		float rotationPitchIn = 1f;
		float rotationYawIn = playerIn.rotationYaw;
		
		double motionX = 0;
		double motionZ = 0;
		
		double x = -MathHelper.sin(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
		double y = -MathHelper.sin((rotationPitchIn) * 0.017453292F);
		double z = MathHelper.cos(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
		
        
        float velocity = .1F;
        float inaccuracy = 50.0F;
        
        float f = MathHelper.sqrt(x * x + y * y + z * z);
        
        double x2 = x / (double)f;
        double z2 = z / (double)f;
        
		if(worldIn.isRemote){
	        for(int i = 0; i<50; i++){
	        	x = x2;
	        	z = z2;
		        x = x + random.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
		        x = x * (double)velocity;
		        motionX = x;
		        motionX += playerIn.motionX;
		        z = z + random.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
		        z = z * (double)velocity;
		        motionZ = z;
		        motionZ += playerIn.motionZ;

				ClientProxy.spawnSleepParticles(worldIn, playerIn.posX, playerIn.posY+.9f+random.nextFloat()*.4, playerIn.posZ, motionX, motionZ);
	        }
		}
		
		for(Entity element : playerIn.getEntityWorld().getEntitiesWithinAABBExcludingEntity(playerIn, new AxisAlignedBB(playerIn.posX-2.0+(x2*2), playerIn.posY-3.0, playerIn.posZ-2.0+(z2*2), playerIn.posX+2.0+(x2*2), playerIn.posY+3.0, playerIn.posZ+2.0+(z2*2)))){
			if(element instanceof EntityLivingBase){
				TranquilizerHandler.applyTranquilizer((EntityLivingBase) element, 50, 1);
			}
		}
		
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
	
	@Override
	/**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand)
    {
        return false;
    }
}
