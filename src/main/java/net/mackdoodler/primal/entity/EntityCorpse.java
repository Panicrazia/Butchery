package net.mackdoodler.primal.entity;

import net.mackdoodler.primal.items.ButcheryItems;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityCorpse extends EntityLivingBase{

	private boolean unharmedStatus;
	
	public EntityCorpse(World worldIn) {
		super(worldIn);

        this.setSize(.5F, .5F);
		unharmedStatus = true;
	}
	
	public EntityCorpse(World worldIn, double posX, double posY, double posZ) {
		super(worldIn);
        this.setSize(.5F, .5F);
        this.setPosition(posX, posY, posZ);
		unharmedStatus = true;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		
	}
	
	@Override
    public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (itemstack.getItem() == ButcheryItems.butcherCleaver)
        {
        	if(this.getUnharmed()){

            	System.out.println("Choppe");
                this.setHarmed();
                
                //blood splatter effects for clients

        	}
        	else{
        		this.setDead();
        	}
    		return EnumActionResult.SUCCESS;
        }
		return EnumActionResult.FAIL;
    }
	
	/**
	 * Returns if this is unharmed or not, used to see if it should use the original model or the pile of meat
	 * @return true if this is unharmed and should use the seprate model
	 */
	public boolean getUnharmed(){
		
		return unharmedStatus;
	}
	
	/**
	 * Switches unharmedStatus to the other boolean value
	 */
	public void setHarmed(){
		
		unharmedStatus=false;
	}

	@Override
	public Iterable<ItemStack> getArmorInventoryList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
		// TODO Auto-generated method stub
		return ItemStack.EMPTY;
	}

	@Override
	public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EnumHandSide getPrimaryHand() {
		// TODO Auto-generated method stub
		return null;
	}
}
