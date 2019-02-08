package net.mackdoodler.butchery.common.items;

import net.mackdoodler.butchery.ButcheryMod;
import net.mackdoodler.butchery.api.TranquilizerHandler;
import net.mackdoodler.butchery.common.blocks.ButcheryBlocks;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class ItemLunarSeed extends ItemSeeds{
	
	public ItemLunarSeed(String name){
		super(ButcheryBlocks.cropLunarLilly, Blocks.FARMLAND);
		setTranslationKey(name);
		setRegistryName(name);
		
		setCreativeTab(ButcheryMod.creativeTab);
	}
	
	public void registerItemModel(){
		ButcheryMod.proxy.registerItemRenderer(this, 0, "lunar_seed");
	}
	
	@Override
	/**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand)
    {
		if(target instanceof EntityChicken || target instanceof EntityParrot){
			TranquilizerHandler.applyTranquilizer(target, 1);
			stack.shrink(1);
			return true;
		}
		return false;
    }
}

