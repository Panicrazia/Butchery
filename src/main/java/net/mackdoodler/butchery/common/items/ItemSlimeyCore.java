package net.mackdoodler.butchery.common.items;

import net.mackdoodler.butchery.ButcheryMod;
import net.mackdoodler.butchery.common.capabilities.CapabilityTranquilizer;
import net.mackdoodler.butchery.common.capabilities.ITranquilizer;
import net.mackdoodler.butchery.common.handlers.SlimeHandler;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSlimeyCore extends Item{
	
	protected String name;
	
	public ItemSlimeyCore(String name){
		this.name=name;
		
		setTranslationKey(name);
		setRegistryName(name);
		
		setCreativeTab(ButcheryMod.creativeTab);
	}
	
	public void registerItemModel(){
		ButcheryMod.proxy.registerItemRenderer(this, 0, name);
	}
	
	/**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
	@Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand){
		if(!playerIn.getEntityWorld().isRemote && target instanceof EntitySlime){
			if(target.hasCapability(CapabilityTranquilizer.TRANQUILIZER_CAPABILITY, null)){
				
				ITranquilizer mai = target.getCapability(CapabilityTranquilizer.TRANQUILIZER_CAPABILITY, null);
				if(mai.getModified()){
					
					SlimeHandler.slimeUnVegitization((EntityLiving) target);
					
					mai.setModified(false);
					
					stack.shrink(1);
					
					return true;
				}
			}
		}
		return false;
    }
}
